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
public class IntafyEventDataProvider extends IjoomerPagingProvider {

    private Context mContext;
    private final String TITLE = "title";
    private final String LOCATION = "location";
    private final String DESCRIPTION = "description";
    private final String STARTDATE = "startdate";
    private final String ENDDATE = "enddate";
    private final String USERIDS = "userIds";
    private final String TYPE = "type";
    private final String SAVEEVENT = "saveEvent";
    private final String EVENT = "event";
    private final String EVENTS = "events";
    private final String EVENTDETAIL = "eventDetail";
    private final String SORTING = "sorting";
    private final String DETAIL = "detail";
    private final String UNIQUEID = "uniqueID";
    private final String DELETE = "delete";
    private final String RESPONSE = "response";
    private final String STATUS = "status";
    private final String INVITE = "invite";
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
    public IntafyEventDataProvider(Context context) {
        super(context);
        mContext = context;
    }


    public void addOrEditEvent(final String id,final String filePath,final String title,final String location,final String startDate,final String endDate,final String eventType,final String selectedUserIDs, final String description, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW,EVENT);
                iw.addWSParam(EXTTASK,SAVEEVENT);

                JSONObject taskData = new JSONObject();
                try {
                    if(!id.equals("0")){
                        taskData.put(UNIQUEID,id);
                    }
                    taskData.put(TITLE,title);
                    taskData.put(LOCATION,location);
                    taskData.put(DESCRIPTION,description);
                    taskData.put(STARTDATE,startDate);
                    taskData.put(ENDDATE,endDate);
                    taskData.put(TYPE,eventType);
                    taskData.put(USERIDS,selectedUserIDs);
                } catch (Throwable e) {
                }
                iw.addWSParam(TASKDATA, taskData);

                if(filePath.trim().length()>0 && !filePath.contains("http")){
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

    public void eventResponse(final String eventID,final String status, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, EVENT);
                iw.addWSParam(EXTTASK, RESPONSE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID, eventID);
                    taskData.put(STATUS, status);
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
    public void removeEvent(final String eventID, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            IjoomerWebService iw = new IjoomerWebService();

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, EVENT);
                iw.addWSParam(EXTTASK, DELETE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID, eventID);
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
                target.onCallComplete(getResponseCode(), getErrorMessage(), result, iw.getJsonObject());
            }
        }.execute();
    }

    public void inviteFriend(final String type,final String eventID, final String userIDS, final WebCallListener target) {

        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, EVENT);
                iw.addWSParam(EXTTASK, INVITE);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID, eventID);
                    taskData.put(USERID, userIDS);
                    taskData.put(TYPE, type);
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


    public void getEvent(final String type,final String networkId,final String connectedUserId,final WebCallListenerWithCacheInfo target) {

        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String,String>>>() {


                @Override
                protected ArrayList<HashMap<String,String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, JOMSOCIAL);
                    iw.addWSParam(EXTVIEW, EVENT);
                    iw.addWSParam(EXTTASK, EVENTS);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(TYPE, type);
                        taskData.put(SORTING, "latest");
                        taskData.put(PAGENO, getPageNo());
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    try {
                        final ArrayList<HashMap<String, String>> dataFromCache = getEventsFromDB(iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, type);
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
                            if((getPageNo() - 1)==1){
                                setPagingParams(Integer.parseInt(iw.getJsonObject().getString(PAGELIMIT))-5, Integer.parseInt(iw.getJsonObject().getString(TOTAL))-5);
                            }else{
                                setPagingParams(Integer.parseInt(iw.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(iw.getJsonObject().getString(TOTAL)));
                            }
                            iw.getJsonObject().remove(TOTAL);
                            IjoomerCaching cacheRowData = new IjoomerCaching(mContext);
                            cacheRowData.addExtraColumn("networkId",networkId);
                            cacheRowData.addExtraColumn("connectedUserId",connectedUserId);
                            cacheRowData.addExtraColumn("pageNo",""+(getPageNo()-1));
                            cacheRowData.addExtraColumn("pageLimit",""+ iw.getJsonObject().getString(PAGELIMIT));
                            cacheRowData.addExtraColumn("eventType",type);
                            iw.getJsonObject().remove(PAGELIMIT);
                            cacheRowData.setReqObject(iw.getWSParameter().toString());
                            cacheRowData.setDateField(true);
                            cacheRowData.cacheData(iw.getJsonObject().getJSONArray("events"), false, EVENTS);
                            return getEventsFromDB(iw.getWSParameter().toString(), "" + (getPageNo() - 1),networkId, connectedUserId, type);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else{
                        if(getResponseCode()==204){
                            deleteEvents(iw.getWSParameter().toString(), "" + getPageNo(), networkId, connectedUserId, type);
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

    private void deleteEvents(String reqObject, String pageNo, String networkId, String connectedUserId, String type){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        caching.deleteDataFromCache("DELETE FROM "+EVENTS+" where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and eventType='"+type+"'");
    }
    private ArrayList<HashMap<String,String>> getEventsFromDB(String reqObject, String pageNo, String networkId, String connectedUserId, String type){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        return caching.getDataFromCache(EVENTS, "select * from " + EVENTS + " where reqobject='" + reqObject + "' and pageNo='"+pageNo+"' and networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and eventType='"+type+"' order by rowid");
    }


    public ArrayList<HashMap<String,String>> getEventDetails(String id,String networkId, String connectedUserId){
        try {
            IjoomerCaching caching = new IjoomerCaching(mContext);
            return caching.getDataFromCache(EVENTDETAIL, "select * from " + EVENTDETAIL + " where id='" + id + "' and networkId='" + networkId + "' and connectedUserId='" + connectedUserId + "'");
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    public void getEventDetails(final String eventID,final String networkId,final String connectedUserId, final WebCallListener target) {

        new AsyncTask<Void, Void, HashMap<String, String>>() {

            @Override
            protected HashMap<String, String> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, EVENT);
                iw.addWSParam(EXTTASK, DETAIL);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(UNIQUEID, eventID);
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                try {
                    final ArrayList<HashMap<String,String>> dataFromCache = getEventDetails(eventID,networkId, connectedUserId);
                    if(dataFromCache!=null && dataFromCache.size()>0) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                target.onProgressUpdate(100);
                                target.onCallComplete(200, "", null, dataFromCache.get(0));
                            }
                        });
                    }
                }catch (Throwable e){
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
                    try {
                        IjoomerCaching cacheRowData = new IjoomerCaching(mContext);
                        cacheRowData.addExtraColumn("networkId", networkId);
                        cacheRowData.addExtraColumn("connectedUserId", connectedUserId);
                        cacheRowData.cacheData(iw.getJsonObject().getJSONObject("event"), false, EVENTDETAIL);
                        ArrayList<HashMap<String,String>> dataFromCache = getEventDetails(eventID, networkId, connectedUserId);
                        return dataFromCache.get(0);
                    }catch (Throwable e){
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(HashMap<String, String> result) {
                super.onPostExecute(result);
                target.onProgressUpdate(100);
                target.onCallComplete(getResponseCode(), getErrorMessage(), null, result);
            }
        }.execute();

    }


}
