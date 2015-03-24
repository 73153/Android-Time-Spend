package com.ijoomer.library.intafy;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.SmartActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This Class Contains All Method Related To IntafyNetworkDataProvider.
 *
 * @author tasol
 *
 */
public class IntafyNetworkDataProvider extends IjoomerPagingProvider {
    private Context mContext;

    private final String ADDNETWORK = "addNetwork";
    private final String FIRSTNAME = "FirstName";
    private final String LASTNAME = "lastname";
    private final String EMAIL = "email";
    private String PUSHTABLE="pushTable";
    private final String AUTHENTICATIONCODE = "authentication_code";
    private final String NETWORKAPP = "networkapp";
    private final String TABLENAMENETWORK = "network";

    /**
     * Constructor
     *
     * @param context
     *            represented {@link android.content.Context}
     */
    public IntafyNetworkDataProvider(Context context) {
        super(context);
        mContext = context;
    }


    public void addNetwork(final String firstName, final String lastName,final String email,final String authenticationCode, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, NETWORK);
                iw.addWSParam(EXTVIEW, NETWORKAPP);
                iw.addWSParam(EXTTASK, ADDNETWORK);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(FIRSTNAME, firstName);
                    taskData.put(LASTNAME, lastName);
                    taskData.put(EMAIL, email);
                    taskData.put(AUTHENTICATIONCODE, authenticationCode);
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
                    return ic.cacheData(iw.getJsonObject(), false, TABLENAMENETWORK);
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

    public ArrayList<HashMap<String,String>> getNetworkListFromDB(){
        return new IjoomerCaching(mContext).getDataFromCache(TABLENAMENETWORK,"select * from "+TABLENAMENETWORK+" order by rowid desc");
    }

    public boolean removeNetwork(String networkIds,String userIds){
        String [] networkIdArray = networkIds.split(",");
        String [] userIdArray = userIds.split(",");
        String whereQuery="";
        for (int i=0;i<networkIdArray.length;i++){
            whereQuery+=" networkId='"+networkIdArray[i]+"' and userId='"+userIdArray[i]+"' or";
        }
        if(whereQuery.length()>0){
            whereQuery = whereQuery.substring(0,whereQuery.length()-3);
        }
        return new IjoomerCaching(mContext).deleteDataFromCache("delete from "+TABLENAMENETWORK+" where "+whereQuery);
    }

    public String getNetworkType(String networkId){
        return new IjoomerCaching(mContext).getDataFromCache(TABLENAMENETWORK,"select networkType from "+TABLENAMENETWORK+" where networkId='"+networkId+"'").get(0).get("networkType");
    }

    public HashMap<String,String> getNetwork(String networkId,String connectedUserId){
        try {
            return new IjoomerCaching(mContext).getDataFromCache(TABLENAMENETWORK, "select * from " + TABLENAMENETWORK + " where networkId='" + networkId + "' and userId='"+connectedUserId+"'").get(0);
        }catch (Throwable e){
            e.printStackTrace();
            return new HashMap<String, String>();
        }
    }
    public ArrayList<HashMap<String,String>> getPushNotificationData(String networkId,String connectedUserId,String type){
        return new IjoomerCaching(mContext).getDataFromCache(PUSHTABLE,"select * from "+PUSHTABLE+" where networkId='" + networkId + "' and connectedUserId='"+connectedUserId+"' and type='"+type+"'");
    }

    public void deletePushNotificationData(String networkId,String connectedUserId,String type){
        new IjoomerCaching(mContext).deleteDataFromCache("delete from "+PUSHTABLE+" where networkId='" + networkId + "' and connectedUserId='"+connectedUserId+"' and type='"+type+"'");
    }

    public void deletePushNotificationData(String networkId,String connectedUserId,String type,String pushId){
        new IjoomerCaching(mContext).deleteDataFromCache("delete from "+PUSHTABLE+" where networkId='" + networkId + "' and connectedUserId='"+connectedUserId+"' and pushId='"+pushId+"' and type='"+type+"'");
    }

}
