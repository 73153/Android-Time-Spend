package com.ijoomer.topcarlondon.src;

import java.util.ArrayList;
import java.util.Calendar;
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

/**
 * This Class Contains All Method Related To JomAdvancedSearchDataProvider.
 * 
 * @author tasol
 * 
 */

public class BookCarDataProvider extends IjoomerPagingProvider {
	private Context mContext;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            represented {@link Context}
	 */

	public BookCarDataProvider(Context context) {
		super(context);
		mContext = context;
	}

	private final String PICKUP = "pickup";
	private final String PICKPOINT = "pickPoint";

	private final String AREACOVERD = "areaCoverd";

	private final String DROPOFF = "dropoff";
	private final String DROPPOINT = "dropPoint";

	private final String FLIGHTNO = "flightNo";
	private final String BABYSEAT = "babySeat";
	private final String EXTRAOPTION1 = "extraOption1";
	private final String PASSENGER = "passenger";
	private final String TOTCOST = "totcost";

	private final String FIRSTNAME = "firstName";
	private final String LASTNAME = "lastName";
	private final String ADDRESS = "address";
	private final String CONTACTNO = "contactNo";
	private final String EMAILID = "emailId";
	private final String MYBOOKING = "myBooking";

	private final String VEHICLE = "vehicle";
	private final String PREFERREDVEHICLE = "preferredVehicle";

	private final String PAYMETHOD = "paymethod";
	private final String PAYMENTMETHOD = "paymentMethod";
	private final String PTYPE = "ptype";
	private final String DTYPE = "dtype";
	private final String DISTANCE = "distance";
	private final String PICKUPCODE = "pickupCode";
	private final String DROPOFFCODE = "dropoffCode";
	private final String SEARCHTYPE = "searchType";
	private final String TYPESEARCH = "typeSearch";
	private final String BCID = "bcID";
	private final String NAME = "name";
	private final String EMAIL = "email";
	private final String PHONENO = "phoneNo";
	private final String MESSAGE = "message";
	private final String TIMESTAMP = "timeStamp";

	private final String PAYPALID = "payPalId";

	private final String CARDPAYMENT = "cardPayment";
	private final String JOURNEYDATE = "journeyDate";

	public void getQuote(final String pickUp, final String dropOff, final String vehicle, final String babySeat, final String pickUpType, final String dropOffType, final String distance, final String pickUpCode, final String dropOffCode,
			final String searchType, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();

				JSONObject taskData = new JSONObject();

				try {
					taskData.put(PICKUP, pickUp);
					taskData.put(DROPOFF, dropOff);
					taskData.put(VEHICLE, vehicle);
					taskData.put(PAYMETHOD, CARDPAYMENT);
					if (babySeat.equals("0")) {
						taskData.put(BABYSEAT, "");
					} else {
						taskData.put(BABYSEAT, babySeat);
					}
					taskData.put(PTYPE, pickUpType);
					taskData.put(DTYPE, dropOffType);
					taskData.put(DISTANCE, distance);
					taskData.put(PICKUPCODE, pickUpCode);
					taskData.put(DROPOFFCODE, dropOffCode);
					taskData.put(SEARCHTYPE, searchType);

				} catch (Throwable e) {
				}
				iw.addWSParam(taskData);
				iw.setDomainTial("home/BookNow");
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

	public void submitBookNow(final String journeyDateTime, final String pickUp, final String pickUpPostCode, final String dropOff, final String dropOffPostCode, final String flightNo, final String vehicle, final String babySeat,
			final String passenger, final String distanceInMiles, final String cost, final String firstName, final String lastName, final String address, final String contactNo, final String emailId, final String searchType, final String pickType,
			final String dropoffType, final WebCallListener target) {
		final String timeStamp = "" + Calendar.getInstance().getTimeInMillis();
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();

				JSONObject taskData = new JSONObject();

				try {
					taskData.put(PICKPOINT, pickUp);
					taskData.put(DROPPOINT, dropOff);
					taskData.put(TYPESEARCH, searchType);
					taskData.put(FIRSTNAME, firstName);
					taskData.put(LASTNAME, lastName);
					taskData.put(ADDRESS, address);
					taskData.put(CONTACTNO, contactNo);
					taskData.put(EMAILID, emailId);
					taskData.put(PAYMENTMETHOD, CARDPAYMENT);
					taskData.put(JOURNEYDATE, journeyDateTime);
					taskData.put(PTYPE, pickType);
					taskData.put(DTYPE, dropoffType);
					taskData.put(FLIGHTNO, flightNo != null && flightNo.length() > 0 ? flightNo : "");
					taskData.put(PREFERREDVEHICLE, vehicle);
					taskData.put(DISTANCE, distanceInMiles);
					taskData.put(PASSENGER, passenger);
					taskData.put(EXTRAOPTION1, babySeat);
					taskData.put(TOTCOST, cost);
					taskData.put(PICKUPCODE, pickUpPostCode);
					taskData.put(DROPOFFCODE, dropOffPostCode);
					taskData.put(TIMESTAMP, timeStamp);
					taskData.put(BCID, "");
					taskData.put(PAYPALID, "");

					new IjoomerCaching(mContext).cacheData(taskData, false, MYBOOKING);

					taskData.remove(PICKUPCODE);
					taskData.remove(DROPOFFCODE);
					taskData.remove(TIMESTAMP);
					taskData.remove(BCID);
					taskData.remove(PAYPALID);
					taskData.put(TOTCOST,cost.replace("\u00a3", ""));
				} catch (Throwable e) {
					e.printStackTrace();
				}
				iw.addWSParam(taskData);
				iw.setDomainTial("home/BookNowNext");
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
						new IjoomerCaching(mContext).updateTable("update " + MYBOOKING + " set " + BCID + "='" + iw.getJsonObject().getString(BCID) + "' where timeStamp='" + timeStamp + "'");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return new IjoomerCaching(mContext).getDataFromCache(MYBOOKING, "select * from " + MYBOOKING + " where timeStamp=" + timeStamp);
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
				super.onPostExecute(result);
			}
		}.execute();

	}

	public boolean updatePaypalId(String bcID, String payPalId) {
		try {
			new IjoomerCaching(mContext).updateTable("update " + MYBOOKING + " set " + PAYPALID + "='" + payPalId + "' where " + BCID + "='" + bcID + "'");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void sendMessage(final String name, final String email, final String phoneNo, final String message, final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();

				JSONObject taskData = new JSONObject();

				try {
					taskData.put(NAME, name);
					taskData.put(EMAIL, email);
					taskData.put(PHONENO, phoneNo);
					taskData.put(MESSAGE, message);

				} catch (Throwable e) {
					e.printStackTrace();
				}
				iw.addWSParam(taskData);
				iw.setDomainTial("contact/ContactUs");
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
					return new IjoomerCaching(mContext).parseData(iw.getJsonObject());
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				target.onProgressUpdate(100);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
				super.onPostExecute(result);
			}
		}.execute();

	}

	public void getTermsAcceptance(final WebCallListener target) {
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();

				iw.setDomainTial("terms/info");
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

	public void getAreaCoverd(final WebCallListener target) {
		final IjoomerCaching caching = new IjoomerCaching(mContext);
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			protected void onPreExecute() {
				if (caching.getDataFromCache(AREACOVERD) != null && caching.getDataFromCache(AREACOVERD).size() > 0) {
					target.onProgressUpdate(100);
					target.onCallComplete(200, getErrorMessage(), caching.getDataFromCache(AREACOVERD), null);
				}
			};

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();

				iw.setDomainTial("areaCoverd/areaInfo");
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
					return caching.cacheData(iw.getJsonObject(), true, AREACOVERD);
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

	public ArrayList<HashMap<String, String>> getMyBookingList() {
		try {
			return new IjoomerCaching(mContext).getDataFromCache(MYBOOKING);
		} catch (Exception e) {
			return new ArrayList<HashMap<String, String>>();
		}
	}
}