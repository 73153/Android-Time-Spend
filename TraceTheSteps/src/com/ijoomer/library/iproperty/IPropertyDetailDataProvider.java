package com.ijoomer.library.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;

public class IPropertyDetailDataProvider extends IjoomerPagingProvider {
	private final String ADDCOMMENT = "addComment";
	private final String COMMENT = "comment";
	private final String COMMENTID = "commentId";
	private final String DELETECOMMENT = "deleteComment";
	private final String EDITCOMMENT = "editComment";
	private final String GETCOMMENTS = "getComments";
	private final String GETPROPERTYDETAILS = "getPropertyDetails";
	private final String GETPROPERTYGALLERYIMAGES = "getPropertyGalleryImages";
	private final String LIKE = "like";
	private final String PAGENO = "pageNo";
	private final String PROPERTYCOMMENTSLIMIT = "propertyCommentsLimit";
	private final String PROPERTYGALLERYIMAGESLIMIT = "propertyGalleryImagesLimit";
	private final String PROPERTYID = "propertyId";
	private final String SEARCH = "search";
	private final String SETLIKE = "setLike";
	private final String UPLOADPROPERTYIMAGE = "uploadPropertyImage";
	private final String USEREMAIL = "useremail";
	private final String USERNAME = "username";
	private boolean isCalling = false;
	private Context mContext;

	public IPropertyDetailDataProvider(Context paramContext) {
		super(paramContext);
		mContext = paramContext;
	}

	public void addComment(final String propertyId, final String comment, final WebCallListener target) {

		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, ADDCOMMENT);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(PROPERTYID, propertyId);
					localJSONObject.put(COMMENT, comment);
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

	public void addComment(final String propertyId, final String comment, final String userName, final String userEmail, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, ADDCOMMENT);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(PROPERTYID, propertyId);
					localJSONObject.put(COMMENT, comment);
					localJSONObject.put(USERNAME, userName);
					localJSONObject.put(USEREMAIL, userEmail);
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

	public void addPicture(final String propertyId, final String filePath, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, UPLOADPROPERTYIMAGE);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(PROPERTYID, propertyId);
					ws.WSCall(filePath, new ProgressListener() {

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
				} catch (Throwable localThrowable) {
					localThrowable.printStackTrace();
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

	public void editComment(final String commentId, final String comment, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, EDITCOMMENT);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(COMMENTID, commentId);
					localJSONObject.put(COMMENT, comment);
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

	public void getComments(final String propertyId, final String commentLimit, final WebCallListener target) {
		if (hasNextPage()) {
			isCalling = true;
			new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
				IjoomerCaching ic = new IjoomerCaching(mContext);

				protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
					IjoomerWebService ws = new IjoomerWebService();
					ws.reset();
					ws.addWSParam(EXTNAME, IPROPERTY);
					ws.addWSParam(EXTVIEW, SEARCH);
					ws.addWSParam(EXTTASK, GETCOMMENTS);
					JSONObject localJSONObject = new JSONObject();
					ws.addWSParam(TASKDATA, localJSONObject);
					try {
						localJSONObject.put(PROPERTYCOMMENTSLIMIT, commentLimit);
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

	public void getPictures(final String propertyId, final String imageLimit, final WebCallListener target) {
		if (hasNextPage()) {
			isCalling = true;
			new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
				IjoomerCaching ic = new IjoomerCaching(mContext);

				protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
					IjoomerWebService ws = new IjoomerWebService();
					ws.reset();
					ws.addWSParam(EXTNAME, IPROPERTY);
					ws.addWSParam(EXTVIEW, SEARCH);
					ws.addWSParam(EXTTASK, GETPROPERTYGALLERYIMAGES);
					JSONObject localJSONObject = new JSONObject();
					ws.addWSParam(TASKDATA, localJSONObject);
					try {
						localJSONObject.put(PROPERTYGALLERYIMAGESLIMIT, imageLimit);
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

	public void getPropertyDetail(final String propertyId, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, GETPROPERTYDETAILS);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(PROPERTYID, propertyId);
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
						return ic.parseData(ws.getJsonObject());
					}
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

	public boolean isCalling() {
		return isCalling;
	}

	public void likeDislike(final String commentId, final String isLike, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, SETLIKE);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(COMMENTID, commentId);
					localJSONObject.put(LIKE, isLike);
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
						return ic.parseData(ws.getJsonObject());
					}
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

	public void removeComment(final String commentId, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
			IjoomerCaching ic = new IjoomerCaching(mContext);

			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService ws = new IjoomerWebService();
				ws.reset();
				ws.addWSParam(EXTNAME, IPROPERTY);
				ws.addWSParam(EXTVIEW, SEARCH);
				ws.addWSParam(EXTTASK, DELETECOMMENT);
				JSONObject localJSONObject = new JSONObject();
				ws.addWSParam(TASKDATA, localJSONObject);
				try {
					localJSONObject.put(COMMENTID, commentId);
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
						return ic.parseData(ws.getJsonObject());
					}
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
}