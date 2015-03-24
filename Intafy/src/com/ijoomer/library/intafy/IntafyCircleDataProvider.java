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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Class Contains All Method Related To IntafyNetworkDataProvider.
 *
 * @author tasol
 *
 */
public class IntafyCircleDataProvider extends IjoomerPagingProvider {
    private Context mContext;

    private final String UNIQUEID = "uniqueID";
    private final String NAME = "name";
    private final String USERIDS = "userIds";
    private final String GROUP = "group";
    private final String GROUPS = "groups";
    private final String GROUPMEMBER = "groupMember";
    private final String GROUPMESSAGE = "groupMessage";
    private final String SAVECIRCLE = "saveCircle";
    private final String TYPE = "type";
    private final String MY = "my";
    private final String SORT = "sort";
    private final String DELETE = "delete";
    private final String WAITING = "waiting";
    private final String WALL = "wall";
    private final String COMMENT = "comment";
    private final String MESSAGE = "message";
    private final String ADDWALL = "addWall";
    private final String MEMBERS = "members";
    private final String JOIN = "join";


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
    public IntafyCircleDataProvider(Context context) {
        super(context);
        mContext = context;
    }


    public void addOrEditCircle(final String id,final String name, final String filePath,final String userIds,final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, GROUP);
                iw.addWSParam(EXTTASK, SAVECIRCLE);

                JSONObject taskData = new JSONObject();
                try {
                    if(!id.equals("0")){
                        taskData.put(UNIQUEID,id);
                    }
                    taskData.put(NAME,name);
                    taskData.put(USERIDS,userIds);
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);

                if(filePath.trim().length()>0){
                    iw.WSCall(filePath,new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            if (num >= 100) {
                                target.onProgressUpdate(95);
                            } else {
                                target.onProgressUpdate((int) num);
                            }
                        }
                    });

                }else {
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

    public void joinGroup(final String userID,final String circleId, final WebCallListener target) {

        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, GROUP);
                iw.addWSParam(EXTTASK, JOIN);

                JSONObject taskData = new JSONObject();

                try {
                    taskData.put(UNIQUEID, circleId);
                    taskData.put(USERID, userID);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    public void removeCircle(final String table,final String id, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, GROUP);
                iw.addWSParam(EXTTASK, DELETE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID,id);
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
                    if(getResponseCode()==200){
                        removeCircle(table,id);
                    }
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

    public void addMessage(final String groupID, final String message, final String filePath,final String type, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            IjoomerWebService iw = new IjoomerWebService();

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, GROUP);
                iw.addWSParam(EXTTASK, ADDWALL);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID, groupID);
                    taskData.put(MESSAGE, message);
                    taskData.put(TYPE, type);
                    taskData.put(COMMENT, "0");
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);
                if (filePath != null) {
                    iw.WSCall(filePath,new ProgressListener() {

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
                target.onCallComplete(getResponseCode(), getErrorMessage(), result, iw.getJsonObject());
            }
        }.execute();
    }

    public void getCircleMessageList(final String uniqueID,final String networkId, final String connectedUserId, final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
                IjoomerWebService iw = new IjoomerWebService();

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, WALL);
                    iw.addWSParam(EXTTASK, WALL);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(PAGENO, "" + getPageNo());
                        taskData.put(TYPE, "group");
                        taskData.put(UNIQUEID, uniqueID);
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getCircleMessageFromDB(iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, uniqueID);
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
                            cacheRowData.addExtraColumn("pageNo", "" + (getPageNo() - 1));
                            cacheRowData.addExtraColumn("pageLimit", "" + iw.getJsonObject().getString(PAGELIMIT));
                            iw.getJsonObject().remove(PAGELIMIT);
                            cacheRowData.setReqObject(iw.getWSParameter().toString());
                            cacheRowData.addExtraColumn("groupId",uniqueID);
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("update"), false, GROUPMESSAGE);
                            return getCircleMessageFromDB(iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId, uniqueID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(getResponseCode()==204){
                            if(getPageNo()==1){
                                deleteCircleMessage(true, iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId,uniqueID);
                            }else{
                                deleteCircleMessage(false, iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId,uniqueID);
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
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null, getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }
    public void getGroupMemberList(final String groupID, final String networkId, final String connectedUserId, final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String,String>>>() {


                @Override
                protected ArrayList<HashMap<String,String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, GROUP);
                    iw.addWSParam(EXTTASK, MEMBERS);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(UNIQUEID, groupID);
                        taskData.put(WAITING, "0");
                        taskData.put(PAGENO, "" + getPageNo());
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getCircleMemberFromDB(iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, groupID);
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
                            cacheRowData.addExtraColumn("pageLimit", "" + iw.getJsonObject().getString(PAGELIMIT));
                            iw.getJsonObject().remove(PAGELIMIT);
                            cacheRowData.setReqObject(iw.getWSParameter().toString());
                            cacheRowData.addExtraColumn("groupId",groupID);
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("members"), false, GROUPMEMBER);
                            return getCircleMemberFromDB(iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId, groupID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(getResponseCode()==204){
                            if(getPageNo()==1){
                                deleteCircleMember(true,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, groupID);
                            }else{
                                deleteCircleMember(false,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, groupID);
                            }

                        }
                        setHasNextPage(false);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String,String>> result) {
                    super.onPostExecute(result);
                    isCalling = false;
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(),result,null, getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }

    public void getGroups(final String invitedCircle,final String table,final boolean isDelete,final boolean isJoin,final String joiningUserId, final String networkId, final String connectedUserId, final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String,String>>>() {


                @Override
                protected ArrayList<HashMap<String,String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, GROUP);
                    iw.addWSParam(EXTTASK, GROUPS);

                    JSONObject taskData = new JSONObject();
                    try {
                        if(table.equals("CircleDateWise")){
                            taskData.put(SORT, "latest");
                        }else{
                            taskData.put(SORT, "alphabetical");
                        }
                        taskData.put(TYPE, MY);
                        taskData.put(PAGENO, "" + getPageNo());
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getCircleFromDB(invitedCircle,table,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId,isDelete,isJoin,joiningUserId);
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
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("groups"), false, table);
                            return getCircleFromDB(invitedCircle,table,iw.getWSParameter().toString(), "" + (getPageNo() - 1), networkId, connectedUserId,isDelete,isJoin,joiningUserId);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(getResponseCode()==204){
                            deleteCircle(table,iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId);
                        }
                        setHasNextPage(false);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String,String>> result) {
                    super.onPostExecute(result);
                    isCalling = false;
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(),result,null, getPageNo() - 1, getPageLimit(), false);
                }
            }.execute();
        } else {
            target.onProgressUpdate(100);
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
        }

    }

    private void deleteCircle(String table,String reqObject, String pageNo, String networkId, String connectedUserId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        caching.deleteDataFromCache("DELETE FROM "+table+" where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"'");
    }


    private ArrayList<HashMap<String,String>> getCircleFromDB(String invitedCircle,String table,String reqObject, String pageNo, String networkId, String connectedUserId,boolean isDelete,boolean isJoin,String joiningUserId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        ArrayList<HashMap<String,String>> groups;
            if(isDelete){
                return caching.getDataFromCache(table, "select * from " + table + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and isEditDelete='1' order by rowid");
            }else if(isJoin){
                groups = caching.getDataFromCache(table, "select * from " + table + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and isEditDelete='1' order by rowid");
                ArrayList<HashMap<String,String>> unJoinGroupList = new ArrayList<HashMap<String, String>>();
                for (HashMap<String,String> group : groups){
                    String[] joinedUserIdArray = group.get("memberIds").split(",");
                    boolean isJoined=false;
                    for (String userId : joinedUserIdArray){
                        if(userId.split(":")[0].equals(joiningUserId)){
                            isJoined=true;
                            break;
                        }
                    }
                    if(!isJoined){
                        unJoinGroupList.add(group);
                    }
                }
                return unJoinGroupList;
            }else{
                String[] invitedCircleArray = invitedCircle.split(",");
                String whereQuery="";
                for (String invitedUser : invitedCircleArray){
                    if(invitedUser.trim().length()>0) {
                        whereQuery += " id!='" + invitedUser.split(":")[0] + "' and";
                    }
                }
                if(whereQuery.length()>4) {
                    whereQuery = whereQuery.substring(0, whereQuery.length() - 4);
                    return caching.getDataFromCache(table, "select * from " + table + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and " + whereQuery + " order by rowid");
                }else{
                    return caching.getDataFromCache(table, "select * from " + table + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId +"' order by rowid");
                }
            }
    }


    private void removeCircle(String table,String circleIds){
        String whereQuery = "";
        String[] idArray = circleIds.split(",");
        for (int i=0;i<idArray.length;i++){
            whereQuery+=" id='"+idArray[i]+"' or";
        }
        if(whereQuery.length()>4){
            whereQuery = whereQuery.substring(0,whereQuery.length()-3);
        }
        IjoomerCaching caching = new IjoomerCaching(mContext);
        caching.deleteDataFromCache("delete from " + table + " where "+whereQuery);
    }
    public ArrayList<HashMap<String,String>> getCircleDetails(String table,String id, String networkId, String connectedUserId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        return caching.getDataFromCache(table, "select * from " + table + " where id='" + id + "' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"'");
    }

    private void deleteCircleMember(boolean isClearAll,String reqObject, String pageNo, String networkId, String connectedUserId, String groupId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        if(isClearAll){
            caching.deleteDataFromCache("delete from "+GROUPMEMBER);
        }else {
            caching.deleteDataFromCache("delete from " + GROUPMEMBER + " where reqobject='" + reqObject + "' and pageNo='" + pageNo + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "' and groupId='" + groupId + "'");
        }
    }
    private ArrayList<HashMap<String,String>> getCircleMemberFromDB(String reqObject, String pageNo, String networkId, String connectedUserId, String groupId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        return caching.getDataFromCache(GROUPMEMBER, "select * from " + GROUPMEMBER + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and groupId='"+groupId+"' order by LOWER(user_name)");
    }

    private void deleteCircleMessage(boolean isClearAll,String reqObject, String pageNo, String networkId, String connectedUserId, String groupId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        if(isClearAll){
            caching.deleteDataFromCache("delete from "+GROUPMESSAGE);
        }else{
            caching.deleteDataFromCache("delete from "+GROUPMESSAGE+" where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and groupId='"+groupId+"'");
        }
    }

    private ArrayList<HashMap<String,String>> getCircleMessageFromDB(String reqObject, String pageNo, String networkId, String connectedUserId, String groupId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        if(!pageNo.equals("1")){
            return caching.getDataFromCache(GROUPMESSAGE, "select * from " + GROUPMESSAGE + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and groupId='"+groupId+"' order by rowid");
        }else{
            return caching.getDataFromCache(GROUPMESSAGE, "select * from " + GROUPMESSAGE + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and groupId='"+groupId+"' order by rowid desc");
        }
    }
}
