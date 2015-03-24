package com.ijoomer.oauth;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerResponseValidator;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Class Contains All Methods Related To IjoomerOauth.
 * 
 * @author tasol
 * 
 */
public final class IjoomerOauth extends IjoomerResponseValidator implements IjoomerSharedPreferences {

	private static Context context;

	private final String LOGIN = "login";
	private final String USERNAME = "username";
    private final String USERID = "userid";
	private final String PASSWORD = "password";
	private final String LAT = "lat";
	private final String LONG = "long";
	private final String DEVICETOKEN = "devicetoken";
	private final String TYPE = "type";
	private final String ANDROID = "android";
	private final String LOGOUT = "logout";
	private final String FBLOGIN = "fblogin";
	private final String NAME = "name";
	private final String EMAIL = "email";
	private final String BIGPIC = "bigpic";
	private final String PIC_BIG = "pic_big";
	private final String REGOPT = "regopt";
	private final String FBID = "fbid";
	private final String UID = "uid";
	private final String RESETPASSWORD = "resetPassword";
	private final String STEP = "step";
	private final String RETRIVEUSERNAME = "retriveUsername";
	private final String TOKEN = "token";
	private final String CRYPT = "crypt";
	private String resetPasswordCrypt = "";
	private String resetPasswordUserId = "";
	

	/**
	 * This method used to get password crypt used for password reset.
	 * 
	 * @return
	 */
	public String getResetPasswordCrypt() {
		return resetPasswordCrypt;
	}

	/**
	 * This method used to get user id used for password reset.
	 * 
	 * @return
	 */
	public String getResetPasswordUserId() {
		return resetPasswordUserId;
	}

	public static IjoomerOauth ijoomerOauth = null;

	/**
	 * Constructor.
	 * 
	 * @param mContext
	 */
	private IjoomerOauth(Context mContext) {
		super(mContext);
	}

	/**
	 * This method used to get class instance.
	 * 
	 * @param mContext
	 * @return
	 */
	public static IjoomerOauth getInstance(Context mContext) {

		if (ijoomerOauth == null) {
			ijoomerOauth = new IjoomerOauth(mContext);
		}
		context = mContext;
		return ijoomerOauth;
	}

	/**
	 * This method used to authenticate user.
	 * 
	 * @param userName
	 *            represented user name
	 * @param passWord
	 *            represented password
	 * @param target
	 *            represented {@link WebCallListener}
	 */
	public void authenticateUser(final String userName, final String passWord, final WebCallListener target) {

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				iw.addWSParam(TASK, LOGIN);
				JSONObject taskData = new JSONObject();
				try {
					taskData.put(USERNAME, userName);
					taskData.put(PASSWORD, passWord);
					taskData.put(LAT, getLatitude());
					taskData.put(LONG, getLongitude());
					taskData.put(DEVICETOKEN, getDeviceUDID(context));
					taskData.put(TYPE, ANDROID);
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
						JSONObject loginParams = new JSONObject();
						loginParams.put(TASK, LOGIN);
						loginParams.put(TASKDATA, taskData);
						((SmartActivity) context).getSmartApplication().writeSharedPreferences(SP_LOGIN_REQ_OBJECT, loginParams.toString());
					} catch (Exception e) {
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
			}
		}.execute();

	}

    public void authenticateUser(final Context context) {

        IntafyNetworkDataProvider provider = new IntafyNetworkDataProvider(context);
        final ArrayList<HashMap<String,String>> networkDataList = provider.getNetworkListFromDB();
        if(networkDataList!=null && networkDataList.size()>0) {
            SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_NETWORKID, networkDataList.get(0).get("networkId"));
            SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_CONNECTEDUSERID, networkDataList.get(0).get("userId"));
            SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_CONNECTEDUSERTYPE, networkDataList.get(0).get("userType"));
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(TASK, LOGIN);
                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(USERNAME, networkDataList.get(0).get("userName"));
                        taskData.put(PASSWORD, "");
                        taskData.put(USERID, networkDataList.get(0).get("userId"));
                        taskData.put(LAT, getLatitude());
                        taskData.put(LONG, getLongitude());
                        taskData.put(DEVICETOKEN, getDeviceUDID(context));
                        taskData.put(TYPE, ANDROID);
                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);
                    iw.WSCall(new ProgressListener() {

                        @Override
                        public void transferred(long num) {
                        }
                    });

                    if (validateResponse(iw.getJsonObject())) {
                        try {
                            JSONObject loginParams = new JSONObject();
                            loginParams.put(TASK, LOGIN);
                            loginParams.put(TASKDATA, taskData);
                            ((SmartActivity) context).getSmartApplication().writeSharedPreferences(SP_LOGIN_REQ_OBJECT, loginParams.toString());
                        } catch (Exception e) {
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }.execute();
        }else{
            SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_NETWORKID, "0");
            SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_CONNECTEDUSERID, "0");
            SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_CONNECTEDUSERTYPE, "");
        }

    }

    public void authenticateUser(final HashMap<String,String> network, final Context context,final WebCallListener target) {
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_NETWORKID,network.get("networkId"));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_CONNECTEDUSERID,network.get("userId"));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_CONNECTEDUSERTYPE,network.get("userType"));
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();
                iw.addWSParam(TASK, LOGIN);
                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(USERNAME, network.get("userName"));
                    taskData.put(PASSWORD, "");
                    taskData.put(USERID, network.get("userId"));
                    taskData.put(LAT, getLatitude());
                    taskData.put(LONG, getLongitude());
                    taskData.put(DEVICETOKEN, getDeviceUDID(context));
                    taskData.put(TYPE, ANDROID);
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
                        JSONObject loginParams = new JSONObject();
                        loginParams.put(TASK, LOGIN);
                        loginParams.put(TASKDATA, taskData);
                        ((SmartActivity) context).getSmartApplication().writeSharedPreferences(SP_LOGIN_REQ_OBJECT, loginParams.toString());
                    } catch (Exception e) {
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                target.onProgressUpdate(100);
                target.onCallComplete(getResponseCode(), getErrorMessage(), null, false);
            }
        }.execute();

    }

	/**
	 * This method used to logout from application.
	 * 
	 * @param target
	 *            represented {@link WebCallListener}
	 */
	public void logout(final WebCallListener target) {

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				iw.addWSParam(TASK, LOGOUT);
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
					new IjoomerCaching(context).resetDataBase();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
			}
		}.execute();
	}



	public void autoLogin(final String reqObject, final WebCallListener target) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				try {
					JSONObject taskData = new JSONObject(reqObject);
					iw.addWSParam(taskData);
				} catch (Throwable e) {
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
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
			}
		}.execute();
	}

}
