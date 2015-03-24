package com.ijoomer.library.iproperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;

public class IPropertySearchDataProvider extends IjoomerPagingProvider {
	private final String CATEGORYRESULTLIMIT = "categoryResultLimit";
	private final String CATID = "catId";
	private final String FORM = "form";
	private final String LATITUDE = "latitude";
	private final String LONGITUDE = "longitude";
	private final String GETSEARCHCATEGORYPROPERTIES = "getSearchCategoryProperties";
	private final String MENUID = "menuId";
	private final String NAME = "name";
	private final String PAGENO = "pageNo";
	private final String PROPERTYID = "propertyId";
	private final String SEARCH = "search";
	private final String SEARCHFIELDTABLENAME = "searchField";
	private final String VALUE = "value";
	private final String SHARELINK = "shareLink";

	private boolean isCalling = false;
	private Context mContext;

	public IPropertySearchDataProvider(Context paramContext) {
		super(paramContext);
		mContext = paramContext;
	}

	public void getCategoryProperties(final String resultLimit, final String categoryId, final String propertyId, final WebCallListener target) {
		if (hasNextPage()) {
			isCalling = true;
			new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
				IjoomerCaching ic = new IjoomerCaching(mContext);

				protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
					IjoomerWebService ws = new IjoomerWebService();
					ws.reset();
					ws.addWSParam(EXTNAME, IPROPERTY);
					ws.addWSParam(EXTVIEW, SEARCH);
					ws.addWSParam(EXTTASK, GETSEARCHCATEGORYPROPERTIES);
					JSONObject localJSONObject = new JSONObject();
					ws.addWSParam(TASKDATA, localJSONObject);
					try {
						localJSONObject.put(CATEGORYRESULTLIMIT, resultLimit);
						localJSONObject.put(CATID, categoryId);
						localJSONObject.put(PROPERTYID, propertyId);
						localJSONObject.put(PAGENO, getPageNo());
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
						if (validateResponse(ws.getJsonObject())) {
							ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
							setPagingParams(Integer.parseInt(ws.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(ws.getJsonObject().getString(TOTAL)));
							ws.getJsonObject().remove(PAGELIMIT);
							ws.getJsonObject().remove(TOTAL);
							HashMap<String, String> localHashMap = new HashMap<String, String>();
							localHashMap.put(SHARELINK, ws.getJsonObject().getString(SHARELINK));
							list.add(localHashMap);
							ws.getJsonObject().remove(SHARELINK);
							list.addAll(ic.parseData(ws.getJsonObject()));
							return list;
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
					return null;
				}

				protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
					super.onPostExecute(result);
					isCalling = false;
					target.onProgressUpdate(100);
					String shareLink = (String) ((HashMap<String, String>) result.get(0)).get(SHARELINK);
					result.remove(0);
					target.onCallComplete(getResponseCode(), getErrorMessage(), result, shareLink);
				}
			}.execute();
		} else {
			target.onProgressUpdate(100);
			target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
		}
	}

	public void getSearchForm(final String menuId, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, FORM);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(MENUID, menuId);
					localJSONObject.put(FORM, "0");
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
						return ic.cacheData(ws.getJsonObject(), true, SEARCHFIELDTABLENAME);
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
				if ((ic.getDataFromCache(SEARCHFIELDTABLENAME) != null) && (ic.getDataFromCache(SEARCHFIELDTABLENAME).size() > 0)) {
					target.onProgressUpdate(100);
					target.onCallComplete(200, null, ic.getDataFromCache(SEARCHFIELDTABLENAME),null);
				}
			}
		}.execute();
	}

	public boolean isCalling() {
		return isCalling;
	}

	public void submitSearchForm(final String menuId, final ArrayList<HashMap<String, String>> searchField, final WebCallListener target) {
		if (hasNextPage()) {
			isCalling = true;
			new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
				IjoomerCaching ic = new IjoomerCaching(mContext);

				protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
					IjoomerWebService ws = new IjoomerWebService();
					ws.reset();
					ws.addWSParam(EXTNAME, IPROPERTY);
					ws.addWSParam(EXTVIEW, SEARCH);
					ws.addWSParam(EXTTASK, FORM);
					try {
						JSONObject fieldJson = new JSONObject();
						fieldJson.put(MENUID, menuId);
						fieldJson.put(PAGENO, ""+getPageNo());
						fieldJson.put(FORM, "1");
						Iterator<HashMap<String, String>> searchIterator = searchField.iterator();
						while (searchIterator.hasNext()) {
							HashMap<String, String> field = (HashMap<String, String>) searchIterator.next();
							fieldJson.put(field.get(NAME), field.get(VALUE));
						}
						ws.addWSParam(TASKDATA, fieldJson);
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

						if (validateResponse(ws.getJsonObject())) {
							setPagingParams(Integer.parseInt(ws.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(ws.getJsonObject().getString(TOTAL)));
							ws.getJsonObject().remove(PAGELIMIT);
							ws.getJsonObject().remove(TOTAL);
							return ic.parseData(ws.getJsonObject());
						}
					} catch (Throwable e) {
						e.printStackTrace();

					}
					return null;
				}

				protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
					super.onPostExecute(result);
					isCalling = false;
					target.onProgressUpdate(100);
					target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
				}
			}.execute();
		} else {
			target.onProgressUpdate(100);
			target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
		}
	}
	
	public void submitSearchForm(final String menuId, final String latitude,final String longitude, final WebCallListener target) {
		if (hasNextPage()) {
			isCalling = true;
			new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
				IjoomerCaching ic = new IjoomerCaching(mContext);

				protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
					IjoomerWebService ws = new IjoomerWebService();
					ws.reset();
					ws.addWSParam(EXTNAME, IPROPERTY);
					ws.addWSParam(EXTVIEW, SEARCH);
					ws.addWSParam(EXTTASK, FORM);
					try {
						JSONObject fieldJson = new JSONObject();
						fieldJson.put(MENUID, menuId);
						fieldJson.put(PAGENO, ""+getPageNo());
						fieldJson.put(FORM, "1");
						fieldJson.put(LATITUDE, latitude);
						fieldJson.put(LONGITUDE, longitude);
						ws.addWSParam(TASKDATA, fieldJson);
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

						if (validateResponse(ws.getJsonObject())) {
							setPagingParams(Integer.parseInt(ws.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(ws.getJsonObject().getString(TOTAL)));
							ws.getJsonObject().remove(PAGELIMIT);
							ws.getJsonObject().remove(TOTAL);
							return ic.parseData(ws.getJsonObject());
						}
					} catch (Throwable e) {
						e.printStackTrace();

					}
					return null;
				}

				protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
					super.onPostExecute(result);
					isCalling = false;
					target.onProgressUpdate(100);
					target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
				}
			}.execute();
		} else {
			target.onProgressUpdate(100);
			target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
		}
	}
}