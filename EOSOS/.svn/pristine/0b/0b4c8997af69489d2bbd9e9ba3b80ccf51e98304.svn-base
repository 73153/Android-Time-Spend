package com.eosos.library;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.eosos.caching.IjoomerCaching;
import com.eosos.common.classes.IjoomerResponseValidator;
import com.eosos.components.main.EososTagHolder;
import com.eosos.weservice.IjoomerWebService;
import com.eosos.weservice.ProgressListener;
import com.eosos.weservice.WebCallListener;

public class EososReviewDataProvider extends IjoomerResponseValidator implements EososTagHolder {
	private Context mContext;
	private String TITLE = "title";
	private String SECTION = "section";
	private String SID = "sid";
	private String VISITOR = "visitor";
	private String REVIEW = "review";
	private String RATING = "rating";
    private String DEVICEID = "deviceID";
    private String CLUBS = "Clubs";

	public EososReviewDataProvider(Context mContext) {
		super(mContext);
		this.mContext = mContext;
	}


    public void addRate(String entryId){
        try{
            new IjoomerCaching(mContext).updateTable("update "+CLUBS+" set isRated='1' where id='"+entryId+"'");
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

	public void addReview(final String deviceId,final String entryID, final String sectionID, final String name, final String comment, final String rating, final WebCallListener target) {
		final JSONObject taskData = new JSONObject();
		new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
				IjoomerWebService iw = new IjoomerWebService();
				iw.reset();
				iw.addWSParam(EXTNAME, SOBIPRO);
				iw.addWSParam(EXTVIEW, "isobipro");
				iw.addWSParam(EXTTASK, "addreview");

				try{
                    taskData.put(DEVICEID, deviceId);
					taskData.put(RATING, rating);
					taskData.put(TITLE, name);
					taskData.put(REVIEW, comment);
					taskData.put(VISITOR, name);
					taskData.put(SID, entryID);
					taskData.put(SECTION, sectionID);
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
					return new IjoomerCaching(mContext).parseData(iw.getJsonObject());
				}

				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				super.onPostExecute(result);
				target.onProgressUpdate(100);
				if (getResponseCode() == 200)
                    addRate(entryID);
				target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
			}
		}.execute();

	}

}
