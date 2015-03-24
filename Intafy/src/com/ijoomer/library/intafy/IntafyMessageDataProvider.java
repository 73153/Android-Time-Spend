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
public class IntafyMessageDataProvider extends IjoomerPagingProvider {
    private Context mContext;

    private final String MESSAGE = "message";
    private final String MESSAGES = "messages";
    private final String CONVERSATION = "conversation";
    private final String DETAIL = "detail";
    private final String UNIQUEID = "uniqueID";
    private final String BODY = "body";
    private final String WRITE = "write";
    private final String SUBJECT = "subject";
    private final String REMOVE = "remove";
    private final String FULL = "full";
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
    public IntafyMessageDataProvider(Context context) {
        super(context);
        mContext = context;
    }


    public void getMessageList(final String table,final String networkId,final String connectedUserId,final WebCallListenerWithCacheInfo target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, MESSAGE);
                    iw.addWSParam(EXTTASK, CONVERSATION);

                    JSONObject taskData = new JSONObject();
                    try {
                        if(table.equals("MessageDateWise")){
                            taskData.put(SORT, "latest");
                        }else{
                            taskData.put(SORT, "alphabetical");
                        }
                        taskData.put(PAGENO, "" + getPageNo());
                    } catch (Throwable e) {
                    }

                    iw.addWSParam(TASKDATA, taskData);
                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getMessageListFromDB(table,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
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
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("messages"), false, table);
                            return getMessageListFromDB(table,iw.getWSParameter().toString(), "" + (getPageNo() - 1),networkId, connectedUserId);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(getResponseCode()==204){
                            if(getPageNo()==1){
                                deleteMessage(table,true,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
                            }else{
                                deleteMessage(table,false,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
                            }
                        }
                        setHasNextPage(false);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                    super.onPostExecute(result);
                    isCalling = false;
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null,getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }
    }

    public void removeMessage(final String messageID, final boolean entire, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, MESSAGE);
                iw.addWSParam(EXTTASK, REMOVE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID, messageID);
                    taskData.put(FULL, entire ? "1" : "0");
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

    private ArrayList<HashMap<String,String>> getMessageListFromDB(String table,String reqObject, String pageNo, String networkId, String connectedUserId){
        try {
            IjoomerCaching caching = new IjoomerCaching(mContext);
                return caching.getDataFromCache(table, "select * from " + table + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' order by rowid");
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    private void deleteMessage(String table,boolean isClearAll,String reqObject, String pageNo, String networkId, String connectedUserId){

        IjoomerCaching caching = new IjoomerCaching(mContext);
        if(isClearAll){
            caching.deleteDataFromCache("delete from "+table);
        }else{
            caching.deleteDataFromCache("delete from "+table+" where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"'");
        }
    }


    public void getMessageDetailsList(final String messageID, final String userID,final String networkId,final String connectedUserId,final WebCallListenerWithCacheInfo target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, MESSAGE);
                    iw.addWSParam(EXTTASK, DETAIL);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(UNIQUEID, messageID);
                        taskData.put(USERID, userID);
                        taskData.put(PAGENO, "" + getPageNo());
                    } catch (Throwable e) {
                    }

                    iw.addWSParam(TASKDATA, taskData);
                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getMessageDeteailFromDB(messageID,userID,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
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
                            cacheRowData.addExtraColumn(UNIQUEID,messageID);
                            cacheRowData.addExtraColumn(USERID,userID);
                            iw.getJsonObject().remove(PAGELIMIT);
                            cacheRowData.setReqObject(iw.getWSParameter().toString());
                            cacheRowData.setDateField(true);
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("messages"), false, MESSAGE);
                            return getMessageDeteailFromDB(messageID,userID,iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        setHasNextPage(false);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                    super.onPostExecute(result);
                    isCalling = false;
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null,getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }
    }

    public void replyMessage(final String messageID, final String body, final String filePath, final WebCallListener target) {

        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, MESSAGE);
                iw.addWSParam(EXTTASK, WRITE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(BODY, body);
                    taskData.put(UNIQUEID, messageID);
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);

                if (filePath != null) {
                    iw.WSCall(filePath, new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            if (num >= 100) {
                                target.onProgressUpdate(95);
                            } else {
                                target.onProgressUpdate((int) num);
                            }
                        }
                    });
                } else {
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

    public void sendMessage(final String userIDS, final String body, final String voiceFilePath, final WebCallListener target) {

        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, MESSAGE);
                iw.addWSParam(EXTTASK, WRITE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(BODY, body);
                    taskData.put(SUBJECT, "message");
                    taskData.put(USERID, userIDS);
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);

                if (voiceFilePath != null) {
                    iw.WSCall(voiceFilePath, new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            if (num >= 100) {
                                target.onProgressUpdate(95);
                            } else {
                                target.onProgressUpdate((int) num);
                            }
                        }
                    });
                } else {
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

    private ArrayList<HashMap<String,String>> getMessageDeteailFromDB(String messageId,String userId,String reqObject, String pageNo, String networkId, String connectedUserId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        if(!pageNo.equals("1")){
            return caching.getDataFromCache(MESSAGE, "select * from " + MESSAGE + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and uniqueID='"+messageId+"' and userID='"+userId+"' order by rowid");
        }else{
            return caching.getDataFromCache(MESSAGE, "select * from " + MESSAGE + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and uniqueID='"+messageId+"' and userID='"+userId+"' order by rowid desc");
        }
    }


}
