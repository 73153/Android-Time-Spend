package com.ijoomer.library.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;

public class IPropertyFavouriteDataProvider extends IjoomerPagingProvider {
	private final String ADDFAVOURITE = "addFavourite";
	private final String CATEGORYID = "categoryId";
	private final String DELETEFAVOURITE = "deleteFavourite";
	private final String FAVOURITE = "favourite";
	private final String GETFAVOURITES = "getFavourites";
	private final String IPROPERTYFAVOURITETABLENAME = "ipropertyFavourite";
	private final String IPROPERTYFAVOURITEUSERTABLENAME = "ipropertyFavouriteUser";
	private final String PAGENO = "pageNo";
	private final String PROPERTYFAVOURITESLIMIT = "propertyFavouritesLimit";
	private final String PROPERTYID = "propertyId";
	private boolean isCalling = false;
	private Context mContext;

	public IPropertyFavouriteDataProvider(Context paramContext) {
		super(paramContext);
		mContext = paramContext;
	}

	public void addCategoryToFavourite(final String paramString, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, FAVOURITE);
				ws.addWSParam(EXTTASK, ADDFAVOURITE);
				JSONObject localJSONObject = new JSONObject();
				try {
					localJSONObject.put(CATEGORYID, paramString);
					ws.addWSParam(TASKDATA, localJSONObject);
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
						return ic.parseData(ws.getJsonObject());
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
			}
		}.execute();
	}

	public void addPropertyToFavourite(final String propertyId, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, FAVOURITE);
				ws.addWSParam(EXTTASK, ADDFAVOURITE);
				JSONObject localJSONObject = new JSONObject();
				try {
					localJSONObject.put(PROPERTYID, propertyId);
					ws.addWSParam(TASKDATA, localJSONObject);
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
						return ic.parseData(ws.getJsonObject());
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
			}
		}.execute();
	}

	public boolean addToFavouriteList(ArrayList<HashMap<String, String>> data) {
        ArrayList<HashMap<String,String>> newData = new ArrayList<HashMap<String, String>>();

        for(HashMap<String,String> row:data){
            HashMap<String,String> rowData =  new HashMap<String, String>();
            rowData.put(IMAGE,row.get(IMAGE));
            rowData.put(TITLE,row.get(TITLE));
            rowData.put(STREET,row.get(STREET));
            rowData.put(STREET2,row.get(STREET2));
            rowData.put(STREET_NUM,row.get(STREET_NUM));
            rowData.put(ID,row.get(ID));
            newData.add(rowData);
        }
		IjoomerCaching ic = new IjoomerCaching(mContext);
		try {
			ic.createTable(newData, IPROPERTYFAVOURITETABLENAME);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void deleteToFavourite(final String propertyId, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, FAVOURITE);
				ws.addWSParam(EXTTASK, DELETEFAVOURITE);
				JSONObject localJSONObject = new JSONObject();
				try {
					localJSONObject.put(PROPERTYID, propertyId);
					ws.addWSParam(TASKDATA, localJSONObject);
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
						return ic.parseData(ws.getJsonObject());
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
			}
		}.execute();
	}

	public void getFavouriteList(final String favouriteLimit, final WebCallListener target) {
		if (hasNextPage()) {
			isCalling = true;
			new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
				IjoomerCaching ic = new IjoomerCaching(mContext);
				IjoomerWebService ws = new IjoomerWebService();
				JSONObject taskData = new JSONObject();

				protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
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
						try {
							if (getResponseCode() == 204) {
								ic.droapTable(IPROPERTYFAVOURITEUSERTABLENAME);
								return null;
							} else {
								setPagingParams(Integer.parseInt(ws.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(ws.getJsonObject().getString(TOTAL)));
								ws.getJsonObject().remove(PAGELIMIT);
								ws.getJsonObject().remove(TOTAL);
								ic.setReqObject(ws.getWSParameter().toString());
								return ic.cacheData(ws.getJsonObject(), false, IPROPERTYFAVOURITEUSERTABLENAME);
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

				protected void onPreExecute() {
					ws.reset();
					ws.addWSParam(EXTNAME, IPROPERTY);
					ws.addWSParam(EXTVIEW, FAVOURITE);
					ws.addWSParam(EXTTASK, GETFAVOURITES);
					try {
						taskData.put(PAGENO, getPageNo());
						taskData.put(PROPERTYFAVOURITESLIMIT, favouriteLimit);
						ws.addWSParam(TASKDATA, taskData);
						if ((ic.getDataFromCache(IPROPERTYFAVOURITEUSERTABLENAME, "select * from " + IPROPERTYFAVOURITEUSERTABLENAME + " where reqObject ='"
								+ ws.getWSParameter().toString() + "'") != null)
								&& (ic.getDataFromCache(IPROPERTYFAVOURITEUSERTABLENAME,
										"select * from " + IPROPERTYFAVOURITEUSERTABLENAME + " where reqObject ='" + ws.getWSParameter().toString() + "'").size() > 0)) {
							target.onProgressUpdate(100);
							target.onCallComplete(
									200,
									null,
									ic.getDataFromCache(IPROPERTYFAVOURITEUSERTABLENAME, "select * from " + IPROPERTYFAVOURITEUSERTABLENAME + " where reqObject ='"
											+ ws.getWSParameter().toString() + "'"), null);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.execute();
		} else {
			target.onProgressUpdate(100);
			target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
		}
	}

	public ArrayList<HashMap<String, String>> getFavouriteListDB() {
		IjoomerCaching ic = new IjoomerCaching(mContext);
		try {
			return ic.getDataFromCache(IPROPERTYFAVOURITETABLENAME);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isCalling() {
		return isCalling;
	}

	public boolean removeToFavouriteList(String propertyId) {
		IjoomerCaching ic = new IjoomerCaching(mContext);
		try {
			ic.deleteDataFromCache("delete from " + IPROPERTYFAVOURITETABLENAME + " where id='" + propertyId + "'");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}