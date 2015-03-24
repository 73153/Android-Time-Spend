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
public class IntafyDirectoriesDataProvider extends IjoomerPagingProvider {
    private Context mContext;

    private final String FRIEND = "friend";
    private final String MEMBERS = "members";
    private final String BLOCKMEMBER = "blockMember";
    private final String REMOVEMEMBER = "removeMember";
    private final String ID = "id";
    private final String TYPE = "type";
    private final String NETWORKTYPE = "networkType";
    private final String SCHOOL = "school";
    private final String UNIVERSITY = "university";
    private final String ALUMNI = "alumni";


    /**
     * This method used to check provider execute any request call.
     *
     * @return {@link boolean}
     */
    private boolean isCalling = false;

    public boolean isCalling() {
        return isCalling;
    }

    /**
     * Constructor
     *
     * @param context
     *            represented {@link Context}
     */
    public IntafyDirectoriesDataProvider(Context context) {
        super(context);
        mContext = context;
    }

    public void getMembersList(final String connectedUserType,final String invitedUser,final String networkId,final String networkType,final String connectedUserId,final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {


                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, FRIEND);
                    iw.addWSParam(EXTTASK, MEMBERS);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(PAGENO, "" + getPageNo());
                        taskData.put(NETWORKTYPE, networkType);
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getMemberFromDB(networkType,connectedUserType,invitedUser,iw.getWSParameter().toString(),""+getPageNo(),networkId,connectedUserId);
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
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("member"), false, MEMBERS);
                            return getMemberFromDB(networkType,connectedUserType,invitedUser,iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId,connectedUserId);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(getResponseCode()==204){
                            deleteMember(iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId);
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
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null, getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }

    public void getMembersForCircleList(final String invitedUser,final String networkId,final String networkType,final String connectedUserId,final String connectedUserType,final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {


                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, FRIEND);
                    iw.addWSParam(EXTTASK, MEMBERS);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(PAGENO, "" + getPageNo());
                        taskData.put(NETWORKTYPE, networkType);
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getMemberForCircleFromDB(invitedUser,networkType,iw.getWSParameter().toString(),""+getPageNo(),networkId,connectedUserId,connectedUserType);
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
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("member"), false, MEMBERS);
                            return getMemberForCircleFromDB(invitedUser,networkType,iw.getWSParameter().toString(), "" + (getPageNo() - 1),networkId,connectedUserId,connectedUserType);
                        } catch (Throwable e) {
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
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null, getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }

    public void getMembersForMessageList(final String networkId,final String networkType,final String connectedUserId,final String connectedUserType,final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {


                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, FRIEND);
                    iw.addWSParam(EXTTASK, MEMBERS);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(PAGENO, "" + getPageNo());
                        taskData.put(NETWORKTYPE, networkType);
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getMemberForMessageFromDB(networkType,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, connectedUserType);
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
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("member"), false, MEMBERS);
                            return getMemberForMessageFromDB(networkType,iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId, connectedUserType);
                        } catch (Throwable e) {
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
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null, getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }

    private void deleteMember(String reqObject, String pageNo, String networkId, String connectedUserId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        caching.deleteDataFromCache("DELETE FROM "+MEMBERS+" where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and user_id!='"+connectedUserId+"'");
    }
    private ArrayList<HashMap<String,String>> getMemberFromDB(String networkType,String connectedUserType,String invitedUserId,String reqObject,String pageNo,String networkId,String connectedUserId){
        String[] invitedUserArray = invitedUserId.split(",");
        String whereQuery="";
        for (String invitedUser : invitedUserArray){
            if(invitedUser.trim().length()>0) {
                whereQuery += " user_id!='" + invitedUser.split(":")[0] + "' and";
            }
        }
        if(whereQuery.length()>4) {
            whereQuery = whereQuery.substring(0, whereQuery.length() - 4);
            if(networkType.equalsIgnoreCase(SCHOOL)) {
                if (connectedUserType.equalsIgnoreCase("students") || connectedUserType.equalsIgnoreCase("parents")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' and "+whereQuery +" order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' and "+ whereQuery + " order by LOWER(user_name)");
                }
            }else if(networkType.equalsIgnoreCase(ALUMNI)) {
                if (connectedUserType.equalsIgnoreCase("alumni")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' and "+whereQuery + " order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' and "+ whereQuery +" order by LOWER(user_name)");
                }
            }else {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' and " + whereQuery + " order by LOWER(user_name)");
            }
        }else{
            if(networkType.equalsIgnoreCase(SCHOOL)) {
                if (connectedUserType.equalsIgnoreCase("students") || connectedUserType.equalsIgnoreCase("parents")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId +"' order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' order by LOWER(user_name)");
                }
            }else if(networkType.equalsIgnoreCase(ALUMNI)) {
                if (connectedUserType.equalsIgnoreCase("alumni")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' order by LOWER(user_name)");
                }
            }else {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and user_id!='"+connectedUserId+"' order by LOWER(user_name)");
            }

        }
    }

    private ArrayList<HashMap<String,String>> getMemberForCircleFromDB(String invitedUserId,String networkType,String reqObject,String pageNo,String networkId,String connectedUserId,String connectedUserType){
        String[] invitedUserArray = invitedUserId.split(",");
        String whereQuery="";
        for (String invitedUser : invitedUserArray){
            if(invitedUser.trim().length()>0) {
                whereQuery += " user_id!='" + invitedUser.split(":")[0] + "' and";
            }
        }
        if(whereQuery.length()>4) {
            whereQuery = whereQuery.substring(0, whereQuery.length() - 4);
            if(networkType.equalsIgnoreCase(SCHOOL)) {
                if (connectedUserType.equalsIgnoreCase("students") || connectedUserType.equalsIgnoreCase("parents")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' and "+whereQuery +" order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' and "+ whereQuery + " order by LOWER(user_name)");
                }
            }else if(networkType.equalsIgnoreCase(UNIVERSITY)) {
                if (connectedUserType.equalsIgnoreCase("students")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' and "+whereQuery + " order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' and "+ whereQuery + " order by LOWER(user_name)");
                }
            }else if(networkType.equalsIgnoreCase(ALUMNI)) {
                if (connectedUserType.equalsIgnoreCase("alumni")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' and "+whereQuery + " order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' and "+ whereQuery +" order by LOWER(user_name)");
                }
            }else{
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' and "+ whereQuery + " order by LOWER(user_name)");
            }
        }else{
            if(networkType.equalsIgnoreCase(SCHOOL)) {
                if (connectedUserType.equalsIgnoreCase("students") || connectedUserType.equalsIgnoreCase("parents")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId +"' order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' order by LOWER(user_name)");
                }
            }else if(networkType.equalsIgnoreCase(UNIVERSITY)) {
                if (connectedUserType.equalsIgnoreCase("students")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
                }
            }else if(networkType.equalsIgnoreCase(ALUMNI)) {
                if (connectedUserType.equalsIgnoreCase("alumni")) {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='" + connectedUserType.toLowerCase() + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
                } else {
                    return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId +"' order by LOWER(user_name)");
                }
            }else{
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
            }
        }

    }

    private ArrayList<HashMap<String,String>> getMemberForMessageFromDB(String networkType,String reqObject,String pageNo,String networkId,String connectedUserId,String connectedUserType){
        if(networkType.equalsIgnoreCase(SCHOOL)) {
            if (connectedUserType.equalsIgnoreCase("students")) {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)!='parents' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
            } else if (connectedUserType.equalsIgnoreCase("parents")) {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)!='students' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
            } else {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!=" + connectedUserId + " order by LOWER(user_name)");
            }
        }else if(networkType.equalsIgnoreCase(ALUMNI)) {
            if (connectedUserType.equalsIgnoreCase("alumni")) {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and LOWER(user_profiletype)='"+connectedUserType.toLowerCase()+"' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
            } else {
                return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
            }
        }else{
            return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and user_id!='" + connectedUserId + "' order by LOWER(user_name)");
        }

    }

    /**
     * This method used to get searched member list.
     *
     * @param searchChar
     *            represented search key word
     * @return
     */
    public ArrayList<HashMap<String, String>> searchMember(String searchChar,String networkId,String connectedUserId) {
        return new IjoomerCaching(mContext).getDataFromCache(MEMBERS, "select * from " + MEMBERS + " where " + USER_NAME + " like '%" + searchChar + "%' and networkId='"+networkId+"' and user_id!='"+connectedUserId+"' and connectedUserId='"+connectedUserId+"' order by user_name");
    }

    public void updateBlockUser(String userIdQuery,String networkId,String connectedUserId) {
        new IjoomerCaching(mContext).updateTable("UPDATE "+MEMBERS+" SET user_block='1' where "+userIdQuery+" and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"'");
    }
    public void updateUnblockUser(String userIdQuery,String networkId,String connectedUserId) {
        new IjoomerCaching(mContext).updateTable("UPDATE "+MEMBERS+" SET user_block='0' where "+userIdQuery+" and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"'");
    }
    public void blockUser(final String userId, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, FRIEND);
                iw.addWSParam(EXTTASK, BLOCKMEMBER);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(ID,userId);
                    taskData.put(TYPE,"block");
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
                    try {
                        return new IjoomerCaching(mContext).parseData(iw.getJsonObject());
                    } catch (Throwable e) {
                    }
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

    public void unBlockUser(final String userId, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, FRIEND);
                iw.addWSParam(EXTTASK, BLOCKMEMBER);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(ID,userId);
                    taskData.put(TYPE,"unblock");
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
                    try {
                        return new IjoomerCaching(mContext).parseData(iw.getJsonObject());
                    } catch (Throwable e) {
                    }
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

    public void deleteUser(final String userId, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, FRIEND);
                iw.addWSParam(EXTTASK, REMOVEMEMBER);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(ID,userId);
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
                    try {
                        return new IjoomerCaching(mContext).parseData(iw.getJsonObject());
                    } catch (Throwable e) {
                    }
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
}
