package com.ijoomer.library.intafy;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;

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
public class IntafyUserDataProvider extends IjoomerPagingProvider {
    private Context mContext;

    private final String PROFILE = "profile";
    private final String UPDATEPROFILE = "updateProfile";
    private final String FRIEND = "friend";
    private final String ADDFRIEND = "addFriend";
    private final String REMOVEFRIEND = "removeFriend";
    private final String MEMBERID = "memberID";
    private final String MESSAGE = "message";
    private final String USER = "user";
    private final String USERDETAIL = "userDetail";
    private final String TABLENAMEUSERDETAIL = "userdetails";
    private final String TABLENAMEUSERPROFILE = "userProfile";
    private final String GROUP_NAME = "group_name";
    private final String FORM = "form";
    private final String VALUE = "value";
    private final String FORMDATA = "formData";
    private final String FIRSTNAME = "firstName";
    private final String LASTNAME = "lastName";
    private final String STATUS = "status";
    private final String EMAIL = "email";
    private final String PINCODE = "pinCode";
    /**
     * Constructor
     *
     * @param context
     *            represented {@link android.content.Context}
     */
    public IntafyUserDataProvider(Context context) {
        super(context);
        mContext = context;
    }


    /**
     * This method used to update user profile.
     *
     * @param filePath
     *            represented avatar path user (optional - leave blank if not
     *            required)
     * @param target
     *            represented {@link WebCallListener}
     */
    public void updateUserProfile(final String filePath,final String orignalFilePath,final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, USER);
                iw.addWSParam(EXTTASK, UPDATEPROFILE);

                JSONObject taskData = new JSONObject();
                iw.addWSParam(TASKDATA, taskData);
                if (filePath != null && filePath.length()>0 && orignalFilePath != null && orignalFilePath.length()>0) {
                    ArrayList<HashMap<String,String>> fileList = new ArrayList<HashMap<String, String>>();
                    HashMap<String,String> filesMap = new HashMap<String, String>();
                    filesMap.put("image",filePath);
                    filesMap.put("orignleImage",orignalFilePath);
                    fileList.add(filesMap);
                    iw.WSCall(fileList, new ProgressListener() {

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

    /**
     * This method used to send friend request.
     *
     * @param memberID
     *            represented requested user id
     * @param message
     *            represented message
     * @param target
     *            represented {@link WebCallListener}
     */
    public void addFriend(final String memberID, final String message, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, FRIEND);
                iw.addWSParam(EXTTASK, ADDFRIEND);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(MEMBERID, memberID);
                    taskData.put(MESSAGE, message);
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

    /**
     * This method is used to remove friend
     *
     * @param memberID
     *            represented member id
     * @param target
     *            represented {@link WebCallListener}
     */
    public void removeFriend(final String memberID, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, FRIEND);
                iw.addWSParam(EXTTASK, REMOVEFRIEND);

                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(MEMBERID, memberID);
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

    /**
     * This method used to get user details.
     *
     * @param userId
     *            represented user id (0- for login user,userID- for other user)
     * @param target
     *            represented {@link WebCallListener}
     */
    public void getUserDetails(final String userId,final String connectedUserId,final String networkId ,final WebCallListener target) {
        final IjoomerWebService iw = new IjoomerWebService();
        final IjoomerCaching ic = new IjoomerCaching(mContext);
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            protected void onPreExecute() {
                ArrayList<HashMap<String, String>> profileData = getFields(userId.equals("0")?connectedUserId:userId,connectedUserId,networkId);
                if (profileData != null  && profileData.size()>0) {
                    target.onProgressUpdate(100);
                    target.onCallComplete(200, getErrorMessage(), profileData, null);
                }
            };

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, USER);
                iw.addWSParam(EXTTASK, USERDETAIL);
                JSONObject taskData = new JSONObject();
                try {
                    if (!userId.equals("0")) {
                        taskData.put(USERID, userId);
                    }
                    taskData.put(FORM, "1");

                } catch (Throwable e) {
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
                    ic.addExtraColumn("connectedUserId",connectedUserId);
                    ic.addExtraColumn("userId",userId.equals("0")?connectedUserId:userId);
                    ic.addExtraColumn("networkId",networkId);
                    try{
                        ic.cacheData(iw.getJsonObject(), false, TABLENAMEUSERDETAIL);
                        return  getFields(userId.equals("0")?connectedUserId:userId,connectedUserId,networkId);
                    }catch (Throwable e){
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                super.onPostExecute(result);
                target.onProgressUpdate(100);
                target.onCallComplete(getResponseCode(), getErrorMessage(), result, false);
            }
        }.execute();

    }

    /**
     * This method used to get user profile.
     *
     * @param userId
     *            represented user id (0- for login user,userID- for other user)
     * @param target
     *            represented {@link WebCallListener}
     */
    public void getUserProfile(final String userId,final String connectedUserId,final String networkId, final WebCallListener target) {
        final IjoomerWebService iw = new IjoomerWebService();


        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            protected void onPreExecute() {
                ArrayList<HashMap<String, String>> profileData = getUserProfileDB(userId.equals("0")?connectedUserId:userId,connectedUserId,networkId);
                if (profileData != null && profileData.size()>0) {
                    target.onProgressUpdate(100);
                    target.onCallComplete(200, getErrorMessage(), profileData, "0");
                }
            };

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, USER);
                iw.addWSParam(EXTTASK, PROFILE);
                JSONObject taskData = new JSONObject();
                try {
                    if (!userId.equals("0")) {
                        taskData.put(USERID, userId);
                    }
                } catch (Throwable e) {
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
                try{
                    final IjoomerCaching ic = new IjoomerCaching(mContext);
                    if (validateResponse(iw.getJsonObject())) {
                        ic.addExtraColumn("connectedUserId",connectedUserId);
                        ic.addExtraColumn("userId",userId.equals("0")?connectedUserId:userId);
                        ic.addExtraColumn("networkId",networkId);
                        ic.cacheData(iw.getJsonObject(), false, TABLENAMEUSERPROFILE);

                        return getUserProfileDB(userId.equals("0")?connectedUserId:userId,connectedUserId,networkId);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                super.onPostExecute(result);
                target.onProgressUpdate(100);
                target.onCallComplete(getResponseCode(), getErrorMessage(), result, "1");
            }
        }.execute();

    }

    /**
     * This method is used to get UserProfiles from database
     *
     * @return {@link ArrayList<HashMap<String, String>>}
     */
    public ArrayList<HashMap<String, String>> getUserProfileDB(String userId,String connectedUserId,String networkId) {
        try {
            IjoomerCaching ic = new IjoomerCaching(mContext);
            return ic.getDataFromCache(TABLENAMEUSERPROFILE, "SELECT * FROM " + TABLENAMEUSERPROFILE+" where userId='"+userId+"' and connectedUserId='"+connectedUserId+"' and networkId='"+networkId+"'");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HashMap<String, String>> getFields(String userId,String connectedUserId,String networkId) {
        try {
            IjoomerCaching ic = new IjoomerCaching(mContext);
            return ic.getDataFromCache(TABLENAMEUSERDETAIL, "SELECT * FROM " + TABLENAMEUSERDETAIL+" where userId='"+userId+"' and connectedUserId='"+connectedUserId+"' and networkId='"+networkId+"'");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * This method used to get user details field list.
     *
     * @return {@link ArrayList<HashMap<String, String>>}
     */
    public ArrayList<HashMap<String, String>> getFields() {
        try {
            IjoomerCaching ic = new IjoomerCaching(mContext);
            return ic.getDataFromCache(TABLENAMEUSERDETAIL, "SELECT * FROM " + TABLENAMEUSERDETAIL);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method used to get user details field group list.
     *
     * @return {@link ArrayList<HashMap<String, String>>}
     */
    public ArrayList<HashMap<String, String>> getFieldGroups() {
        try {
            IjoomerCaching ic = new IjoomerCaching(mContext);
            return ic.getDataFromCache(TABLENAMEUSERDETAIL, "SELECT " + GROUP_NAME + " FROM " + TABLENAMEUSERDETAIL + " group by " + GROUP_NAME);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method used to update user details.
     *
     * @param userDetailFields
     *            represented user details field list
     * @param target
     *            represented {@link WebCallListener}
     */
    public void updateUserDetails(final String FirstName,final String LasttName,final String status,final String email,final ArrayList<JSONObject> userDetailFields, final WebCallListener target) {
        final IjoomerWebService iw = new IjoomerWebService();
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

                iw.reset();
                iw.addWSParam(EXTNAME, JOMSOCIAL);
                iw.addWSParam(EXTVIEW, USER);
                iw.addWSParam(EXTTASK, USERDETAIL);
                JSONObject taskData = new JSONObject();
                JSONObject formData = new JSONObject();
                try {
                    taskData.put(FORM, "0");
                    for (JSONObject jsonObject : userDetailFields) {
                        formData.put("f" + jsonObject.getString("id"),jsonObject.getString(VALUE));
                    }
                    taskData.put(FORMDATA, formData);
                    taskData.put(FIRSTNAME, FirstName);
                    taskData.put(LASTNAME, LasttName);
                    taskData.put(STATUS, status);
                    taskData.put(EMAIL, email);

                } catch (Throwable e) {
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
                validateResponse(iw.getJsonObject());
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
