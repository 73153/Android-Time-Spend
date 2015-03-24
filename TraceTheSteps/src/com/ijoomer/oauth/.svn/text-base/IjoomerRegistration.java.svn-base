package com.ijoomer.oauth;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerResponseValidator;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;

/**
 * This Class Contains All Methods Related To IjoomerRegistration.
 * 
 * @author tasol
 * 
 */
public class IjoomerRegistration extends IjoomerResponseValidator {

	private Context mContext;
	
	private final String REGISTRATION = "registration";
	private final String NAME = "name";
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String EMAIL = "email";
	private final String FULL = "full";
	private final String FORM = "form";
	private final String TYPE = "type";
	private final String CODE = "code";
	private final String GROUP_NAME = "group_name";
	private final String TABLENAME = "registration";
	private final String VALUE = "value";
	private final String JOMSOCIAL = "jomsocial";
	private final String USER = "user";
	private final String PROFILETYPES = "profileTypes";
	public static String userRegistrationType = "0";
	public static String imagerPath = "";
	public static String name = "";
	public static String userName = "";
	public static String passWord = "";
	public static String email = "";

	/**
	 * This method used to get user avatar path.
	 * 
	 * @return
	 */
	public static String getImagerPath() {
		return imagerPath;
	}

	/**
	 * This method used to get name.
	 * 
	 * @return
	 */
	public static String getName() {
		return name;
	}

	/**
	 * This method used to get user name.
	 * 
	 * @return
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * This method used to get user password.
	 * 
	 * @return
	 */
	public static String getPassWord() {
		return passWord;
	}

	/**
	 * This method used to get user email.
	 * 
	 * @return
	 */
	public static String getEmail() {
		return email;
	}

	/**
	 * This method used to get user registering profile type.
	 * 
	 * @return
	 */
	public static String getUserRegistrationType() {
		return userRegistrationType;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public IjoomerRegistration(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * This method used to send new user sign up details.
	 * 
	 * @param name
	 *            represented name of user
	 * @param userName
	 *            represented user name
	 * @param passWord
	 *            represented user password
	 * @param email
	 *            represented user email
	 * @param userRegistrationType
	 *            represented user registering profile type
	 * @param target
	 *            represented {@link WebCallListener}
	 */
	public void signUpNewUser(final String imagePath, final String name, final String userName, final String passWord, final String email, final String userRegistrationType,
			final WebCallListener target) {
		IjoomerRegistration.imagerPath = imagePath;
		IjoomerRegistration.name = name;
		IjoomerRegistration.userName = userName;
		IjoomerRegistration.passWord = passWord;
		IjoomerRegistration.email = email;
		IjoomerRegistration.userRegistrationType = userRegistrationType;

		final IjoomerCaching ic = new IjoomerCaching(mContext);
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				iw.addWSParam(TASK, REGISTRATION);
				JSONObject taskData = new JSONObject();
				try {
					taskData.put(NAME, name);
					taskData.put(USERNAME, userName);
					taskData.put(PASSWORD, passWord);
					taskData.put(EMAIL, email);
					taskData.put(FULL, 0);
					taskData.put(TYPE, userRegistrationType);

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
					iw.getJsonObject().remove(CODE);
					iw.getJsonObject().remove(FULL);
					return ic.cacheData(iw.getJsonObject(), true, TABLENAME);
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
	 * This method used to get registration field list for group.
	 * 
	 * @param groupName
	 *            represented registering field group name
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getFields(String groupName) {
		try {
			IjoomerCaching ic = new IjoomerCaching(mContext);
			return ic.getDataFromCache(TABLENAME, "SELECT * FROM " + TABLENAME + " where " + GROUP_NAME + "='" + groupName + "'");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method used to get registration field group list.
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getFieldGroups() {
		try {
			IjoomerCaching ic = new IjoomerCaching(mContext);
			return ic.getDataFromCache(TABLENAME, "SELECT " + GROUP_NAME + " FROM " + TABLENAME + " group by " + GROUP_NAME);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method used to submit registering data.
	 * 
	 * @param signUpFields
	 *            represented registration fields list
	 * @param target
	 *            represented {@link WebCallListener}
	 */
	public void submitNewUser(final ArrayList<HashMap<String, String>> signUpFields, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				iw.addWSParam(TASK, REGISTRATION);
				JSONObject taskData = new JSONObject();
				try {
					taskData.put(FORM, "1");

					for (HashMap<String, String> hashMap : signUpFields) {
						taskData.put(hashMap.get(NAME),hashMap.get(VALUE));
					}

				} catch (Throwable e) {
				}
				iw.addWSParam(TASKDATA, taskData);

				iw.WSCall(getImagerPath(), new ProgressListener() {

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
	
	public void getNewUser(final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(TASK, REGISTRATION);
				try {
					JSONObject taskData = new JSONObject();
					taskData.put(FORM, "0");
					ws.addWSParam(TASKDATA, taskData);
					ws.WSCall(new ProgressListener() {

						@Override
						public void transferred(long num) {
							if (num >= 100) {
								target.onProgressUpdate(95);
							} else {
								target.onProgressUpdate((int) num);
							}
						}
					});
					if (validateResponse(ws.getJsonObject()))
						return ic.cacheData(ws.getJsonObject(), true, TABLENAME);
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, Boolean.valueOf(false));
			}

			protected void onPreExecute() {
				if ((ic.getDataFromCache(TABLENAME) != null) && (ic.getDataFromCache(TABLENAME).size() > 0)) {
					target.onProgressUpdate(100);
					target.onCallComplete(200, null, ic.getDataFromCache(TABLENAME),null);
				}
			}
		}.execute();
	}

	/**
	 * This method used to get registering user profile type.
	 * 
	 * @param target
	 *            represented {@link WebCallListener}
	 */
	public void getProfileTypes(final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				iw.addWSParam(EXTNAME, JOMSOCIAL);
				iw.addWSParam(EXTVIEW, USER);
				iw.addWSParam(EXTTASK, PROFILETYPES);

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
					iw.getJsonObject().remove(CODE);
					return new IjoomerCaching(mContext).parseData(iw.getJsonObject());
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
