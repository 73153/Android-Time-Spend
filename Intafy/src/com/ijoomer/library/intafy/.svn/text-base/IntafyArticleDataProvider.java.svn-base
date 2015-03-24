package com.ijoomer.library.intafy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Class Contains All Method Related To IntafyNetworkDataProvider.
 *
 * @author tasol
 *
 */
public class IntafyArticleDataProvider extends IjoomerPagingProvider {
    private Context mContext;

    private final String ID = "id";
    private final String TITLE = "title";
    private final String DESCRIPTION = "description";
    private final String ARTICLES = "articles";
    private final String GETARTICLES = "getArticles";
    private final String REMOVEARTICLE = "removeArticle";
    private final String ADDARTICLE = "addArticle";
    private final String SORT = "sort";

    private boolean isCalling = false;

    public boolean isCalling() {
        return isCalling;
    }

    /**
     * Constructor
     *
     * @param context
     *            represented {@link android.content.Context}
     */
    public IntafyArticleDataProvider(Context context) {
        super(context);
        mContext = context;
    }


    public void addOrEditArticle(final String id,final String title, final String description,final String filePath,final String orignalFilePath, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, ICMS);
                iw.addWSParam(EXTVIEW, ARTICLES);
                iw.addWSParam(EXTTASK, ADDARTICLE);

                JSONObject taskData = new JSONObject();
                try {
                    if(!id.equals("0")){
                        taskData.put(ID,id);
                    }
                    taskData.put(TITLE,title);
                    taskData.put(DESCRIPTION,description);
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);

                if(filePath.trim().length()>0 && orignalFilePath.trim().length()>0){
                    ArrayList<HashMap<String,String>> fileList = new ArrayList<HashMap<String, String>>();
                    HashMap<String,String> filesMap = new HashMap<String, String>();
                    filesMap.put("image",filePath);
                    filesMap.put("orignleImage",orignalFilePath);
                    fileList.add(filesMap);
                    iw.WSCall(fileList,new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            if (num >= 100) {
                                target.onProgressUpdate(95);
                            } else {
                                target.onProgressUpdate((int) num);
                            }
                        }
                    });
                }else{
                    iw.WSCall(new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            if (num >= 100) {
                                target.onProgressUpdate(95);
                            } else {
                                target.onProgressUpdate((int) num);
                            }
                        }
                    });
                }

                if (validateResponse(iw.getJsonObject())) {
                    final IjoomerCaching ic = new IjoomerCaching(mContext);
                    return ic.parseData(iw.getJsonObject());
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                super.onPostExecute(result);
                target.onProgressUpdate(100);
                target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
            }
        }.execute();
    }

    public void removeArticle(final String id, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, ICMS);
                iw.addWSParam(EXTVIEW, ARTICLES);
                iw.addWSParam(EXTTASK, REMOVEARTICLE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(ID,id);
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);

                iw.WSCall(new ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        if (num >= 100) {
                            target.onProgressUpdate(95);
                        } else {
                            target.onProgressUpdate((int) num);
                        }
                    }
                });



                if (validateResponse(iw.getJsonObject())) {
                    final IjoomerCaching ic = new IjoomerCaching(mContext);
                    return ic.parseData(iw.getJsonObject());
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                super.onPostExecute(result);
                target.onProgressUpdate(100);
                target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
            }
        }.execute();
    }

    public void getArticle(final String table,final String networkId,final String connectedUserId,final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<Object>>() {


                @Override
                protected ArrayList<Object> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, ICMS);
                    iw.addWSParam(EXTVIEW, ARTICLES);
                    iw.addWSParam(EXTTASK, GETARTICLES);

                    JSONObject taskData = new JSONObject();
                    try {
                        if(table.equals("ArticleTitleWise")){
                            taskData.put(SORT, "alphabetical");
                        }else{
                            taskData.put(SORT, "latest");
                        }
                        taskData.put(PAGENO, "" + getPageNo());
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getArticleFromDB(table,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
                        if(dataFromCache!=null && dataFromCache.size()>0) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    target.onProgressUpdate(100);
                                    target.onCallComplete(200, "", dataFromCache, null, getPageNo(), Integer.parseInt(dataFromCache.get(0).get(PAGELIMIT)), true);
                                }
                            });
                        }
                    }catch (Throwable e){
                        e.printStackTrace();
                    }

                    iw.WSCall(new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            if (num >= 100) {
                                target.onProgressUpdate(95);
                            } else {
                                target.onProgressUpdate((int) num);
                            }
                        }
                    });

                    ArrayList<Object> response =new ArrayList<Object>();
                    if (validateResponse(iw.getJsonObject())) {
                        try {
                            setPagingParams(Integer.parseInt(iw.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(iw.getJsonObject().getString(TOTAL)));
                            iw.getJsonObject().remove(TOTAL);
                            IjoomerCaching cacheRowData = new IjoomerCaching(mContext);
                            cacheRowData.addExtraColumn("networkId",networkId);
                            cacheRowData.addExtraColumn("connectedUserId",connectedUserId);
                            cacheRowData.addExtraColumn("pageNo",""+(getPageNo()-1));
                            cacheRowData.addExtraColumn("pageLimit",""+ iw.getJsonObject().getString(PAGELIMIT));
                            iw.getJsonObject().remove(PAGELIMIT);
                            cacheRowData.setReqObject(iw.getWSParameter().toString());
                            cacheRowData.setDateField(true);
                            response.add(iw.getJsonObject().getString("allowAddArticle"));
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("articles"), false, table);
                            response.add(getArticleFromDB(table,iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId));
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        return response;
                    }else{
                        if(getResponseCode()==204){
                            try {
                                response.add(iw.getJsonObject().getString("allowAddArticle"));
                                deleteArticle(table, iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
                                return response;
                            }catch (Throwable e){
                                e.printStackTrace();
                            }
                        }
                        setHasNextPage(false);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<Object> result) {
                    super.onPostExecute(result);
                    isCalling = false;
                    target.onProgressUpdate(100);
                    if(result!=null && result.size()>1) {
                        target.onCallComplete(getResponseCode(), getErrorMessage(), (ArrayList<HashMap<String, String>>) result.get(1), result.get(0).toString(), getPageNo() - 1, getPageLimit(), false);
                    }else{
                        target.onCallComplete(getResponseCode(), getErrorMessage(), null, result.get(0).toString(), getPageNo() - 1, getPageLimit(), false);
                    }
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }

    private ArrayList<HashMap<String,String>> getArticleFromDB(String table,String reqObject, String pageNo, String networkId, String connectedUserId){
        try {
            IjoomerCaching caching = new IjoomerCaching(mContext);
            return caching.getDataFromCache(table, "select * from " + table + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' order by rowid");
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    private void deleteArticle(String table,String reqObject, String pageNo, String networkId, String connectedUserId){
        try{
            IjoomerCaching caching = new IjoomerCaching(mContext);
            caching.deleteDataFromCache("delete from "+table+" where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"'");
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public HashMap<String,String> getArticleDetails(String table,String id){
        try{
            IjoomerCaching caching = new IjoomerCaching(mContext);
            return caching.getDataFromCache(table, "select * from " + table + " where id='" + id + "'").get(0);
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
}
