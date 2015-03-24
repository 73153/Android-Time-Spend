package com.mycadiz.library.sobipro;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.maps.model.LatLng;
import com.mycadiz.caching.IjoomerCaching;
import com.mycadiz.common.classes.IjoomerPagingProvider;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.common.configuration.IjoomerApplicationConfiguration;
import com.mycadiz.weservice.IjoomerWebService;
import com.mycadiz.weservice.ProgressListener;
import com.mycadiz.weservice.WebCallListener;
import com.mycadiz.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.SmartActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This Class Contains All Method Related To SobiproCategoriesDataProvider.
 *
 * @author tasol
 *
 */
public class SobiproCategoriesDataProvider extends IjoomerPagingProvider {

    private Context mContext;
    private final String ISOBIPRO = "isobipro";
    private final String CATEGORIES = "categories";
    private final String SECTON_CATEGORIES = "sectionCategories";
    private final String SEARCHRESULTS = "searchResults";
    private final String SECTION_ID = "sectionID";
    private final String CAT_ID = "categoryID";
    private final String KEYWORDS = "keywords";
    private final String PAGENO = "pageNO";
    private final String SECTIONID = "sectionid";
    private final String CATID = "catid";
    private final String SORTBY = "sortBy";
    private final String SORTORDER = "sortOrder";
    private final String FEATUREDFIRST = "featuredFirst";
    private final String FILTERBY = "filterBy";
    private final String TABLE_ENTRIES_FILTER = "SobiproEntriesFilter";
    private final String TABLE_FAVOURITE = "SobiproFavouriteEntries";
    private final String FAVOURITE = "favourite";
    private final String TABLE_SECTION_CATEGORIES = "sobipro_section_categories";
    private final String ID = "id";
    private boolean isCalling = false;
    private String LATITUDE = "latitude";
    private String LONGITUDE = "longitude";
    private String COSLAT = "coslat";
    private String SINLAT = "sinlat";
    private String COSLNG = "coslng";
    private String SINLNG = "sinlng";
    private AQuery aQuery;
    int count = 0;
    boolean imageLoadingProblem = false;

    public interface ImageDownloadListener {
        void onImgeDownload(int total, int countProgress);

        void onTaskComplete();
    }

    /**
     * This method used to check provider execute any request call.
     *
     * @return {@link boolean}
     */
    public boolean isCalling() {
        return isCalling;
    }

    /**
     * Constructor
     *
     * @param mContext
     *            represented {@link Context}
     */

    public SobiproCategoriesDataProvider(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        aQuery = new AQuery(mContext);
    }

    /**
     * This Method is used to get Categories of specifies Section
     *
     * @param sectionID
     *            represented section id
     * @param catID
     *            represented category id
     * @param target
     *            represented {@link WebCallListener}
     */
    public void getSectionCategories(final String sectionID, final String catID, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();

                iw.addWSParam(EXTNAME, SOBIPRO);
                iw.addWSParam(EXTVIEW, ISOBIPRO);
                iw.addWSParam(EXTTASK, SECTON_CATEGORIES);
                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(SECTION_ID, sectionID);
                    taskData.put(CAT_ID, catID);
                } catch (Throwable e) {
                }

                iw.addWSParam(TASKDATA, taskData);

                if (IjoomerApplicationConfiguration.isCachEnable) {
                    try {
                        final ArrayList<HashMap<String, String>> data1 = new IjoomerCaching(mContext).getDataFromCache(TABLE_SECTION_CATEGORIES, "select * from'"
                                + TABLE_SECTION_CATEGORIES + "'where reqObject='" + iw.getWSParameter() + "' order by rowid");
                        if (data1 != null && data1.size() > 0) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    target.onProgressUpdate(100);
                                    target.onCallComplete(200, "", data1, null);

                                }
                            });

                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
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
                        if (IjoomerApplicationConfiguration.isCachEnable) {
                            IjoomerCaching caching = new IjoomerCaching(mContext);
                            caching.setReqObject(iw.getWSParameter().toString());
                            caching.cacheData(iw.getJsonObject().getJSONArray(CATEGORIES), false, TABLE_SECTION_CATEGORIES);

                            return new IjoomerCaching(mContext).getDataFromCache(TABLE_SECTION_CATEGORIES,
                                    "select * from'" + TABLE_SECTION_CATEGORIES + "'where reqObject='" + iw.getWSParameter() + "' order by rowid");
                        } else {
                            return new IjoomerCaching(mContext).parseData(iw.getJsonObject().getJSONArray(CATEGORIES));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    public ArrayList<HashMap<String, String>> getSectionCategoryFromDB(String sectionId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        String sql;
        if(!sectionId.equals("1")){
            if(mContext.getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                sql = "select * from categories where parent='"+sectionId+"' order by LOWER(name)";
            }else{
                String value = caching.getDataFromCache("section","select name_spanish from categories where id='"+sectionId+"'").get(0).get("name_spanish");
                if(value.equalsIgnoreCase("Seguros") || value.equalsIgnoreCase("Coche") || value.equalsIgnoreCase("Doctores / Salud")){
                    sql = "select * from categories where parent='"+sectionId+"' order by LOWER(name)";
                }else{
                    sql = "select * from categories where parent='"+sectionId+"' order by LOWER(name_spanish)";
                }
            }

        }else{
            if(mContext.getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                sql = "select * from categories where parent='"+sectionId+"' order by LOWER(name)";
            }else{
                sql = "select * from categories where parent='"+sectionId+"' order by LOWER(name_spanish)";
            }
        }
        return caching.getDataFromCache("section",sql);
    }


    /**
     * This method is used to get entries by sorting and filtering parameters
     *
     * @param sectionID
     *            represented section id
     * @param catID
     *            represented category id
     * @param sortBy
     *            represented sorting options(rating,title)
     * @param sortOrder
     *            represented sorting order.(asc - for Ascending order ,desc -
     *            fore descending order)
     * @param filterBy
     *            represented filtering options.
     * @param target
     *            {@link WebCallListener}
     */
    public void getEntries(final String sectionID, final String catID, final String sortBy, final String sortOrder, final String filterBy, final String featuredFirst,
                           final WebCallListener target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();
                    iw.addWSParam(EXTNAME, SOBIPRO);
                    iw.addWSParam(EXTVIEW, ISOBIPRO);
                    iw.addWSParam(EXTTASK, SECTON_CATEGORIES);
                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(SECTION_ID, sectionID);
                        taskData.put(CAT_ID, catID);
                        taskData.put(PAGENO, getPageNo());
                        taskData.put(SORTBY, sortBy);
                        taskData.put(SORTORDER, sortOrder);
                        taskData.put(FILTERBY, filterBy);
                        taskData.put(FEATUREDFIRST, featuredFirst);

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

                            setPagingParams(Integer.parseInt(iw.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(iw.getJsonObject().getString(TOTAL)));
                            iw.getJsonObject().remove(TOTAL);
                            if (iw.getJsonObject().getJSONArray("entries") != null) {
                                IjoomerCaching caching = new IjoomerCaching(mContext);
                                caching.addExtraColumn(PAGENO, (getPageNo() - 1) + "");
                                caching.addExtraColumn(PAGELIMIT, iw.getJsonObject().get(PAGELIMIT).toString());
                                caching.addExtraColumn(SECTIONID, iw.getJsonObject().get(SECTIONID).toString());
                                caching.addExtraColumn(CATID, iw.getJsonObject().get(CATID).toString());
                                caching.addExtraColumn(FAVOURITE, "0");
                                iw.getJsonObject().remove(PAGELIMIT);
                                iw.getJsonObject().remove(SECTIONID);
                                iw.getJsonObject().remove(CATID);
                                caching.setReqObject(iw.getWSParameter().toString());
                                if ((getPageNo() - 1) == 1) {
                                    caching.droapTable(TABLE_ENTRIES_FILTER);
                                }

                                caching.cacheData(iw.getJsonObject(), false, TABLE_ENTRIES_FILTER);

                                return getEntriesFromCacheByReqObject(TABLE_ENTRIES_FILTER, iw.getWSParameter().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                    super.onPostExecute(result);
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null);
                    isCalling = false;
                }

            }.execute();
        } else {
            isCalling = false;
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null);
            target.onProgressUpdate(100);
        }
    }


    /**
     * This method is used to get entries
     *
     * @param sectionID
     *            represented section id
     * @param catID
     *            represented Category id
     * @param target
     *            {@link WebCallListener}
     */
    public void getEntries(final String tableName,final String sectionID, final String catID, final String featuredFirst, final WebCallListenerWithCacheInfo target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

                IjoomerWebService iw = new IjoomerWebService();

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    iw.reset();

                    iw.addWSParam(EXTNAME, SOBIPRO);
                    iw.addWSParam(EXTVIEW, ISOBIPRO);
                    iw.addWSParam(EXTTASK, SECTON_CATEGORIES);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(SECTION_ID, sectionID);
                        taskData.put(CAT_ID, catID);
                        taskData.put(PAGENO, getPageNo());
                        taskData.put(FEATUREDFIRST, featuredFirst);

                    } catch (Throwable e) {
                    }

                    iw.addWSParam(TASKDATA, taskData);

                    if (IjoomerApplicationConfiguration.isCachEnable && !IjoomerApplicationConfiguration.isReloadRequired) {
                        try {
                            final ArrayList<HashMap<String, String>> data = getEntriesFromCacheByReqObject(tableName, iw.getWSParameter().toString());
                            if (data != null && data.size() > 0) {
                                ((Activity) mContext).runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        target.onProgressUpdate(100);
                                        target.onCallComplete(200, "", data, null, getPageNo(), Integer.parseInt(data.get(0).get(PAGELIMIT)), true);

                                    }
                                });
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
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
                            setPagingParams(Integer.parseInt(iw.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(iw.getJsonObject().getString(TOTAL)));
                            iw.getJsonObject().remove(TOTAL);
                            if (iw.getJsonObject().getJSONArray("entries") != null) {
                                IjoomerCaching caching = new IjoomerCaching(mContext);
                                caching.addExtraColumn(PAGENO, (getPageNo() - 1) + "");
                                caching.addExtraColumn(PAGELIMIT, iw.getJsonObject().get(PAGELIMIT).toString());
                                caching.addExtraColumn(SECTIONID, iw.getJsonObject().get(SECTIONID).toString());
                                caching.addExtraColumn(CATID, iw.getJsonObject().get(CATID).toString());
                                caching.addExtraColumn(FAVOURITE, "0");
                                iw.getJsonObject().remove(PAGELIMIT);
                                iw.getJsonObject().remove(SECTIONID);
                                iw.getJsonObject().remove(CATID);
                                caching.setReqObject(iw.getWSParameter().toString());
                                caching.cacheData(iw.getJsonObject(), false, tableName);
                                return getEntriesFromCacheByReqObject(tableName, iw.getWSParameter().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                    super.onPostExecute(result);
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null, getPageNo() - 1, getPageLimit(), false);
                    isCalling = false;
                }

            }.execute();
        } else {
            isCalling = false;
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
            target.onProgressUpdate(100);
        }
    }

    public ArrayList<HashMap<String,String>> getEntriesFromDB(String catId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        ArrayList<HashMap<String,String>> data = caching.getDataFromCache("entries","select * from entries where catids like '%"+catId+"%'");
        if(data.size()>0){
            if(data.get(0).get("section").equals("Things To Do") ){
                if(mContext.getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")) {
                    for (HashMap<String, String> row : data) {
                        try {
                            JSONArray fields = new JSONArray(row.get("field"));
                            for (int i = 0; i < fields.length(); i++) {
                                JSONObject field = fields.getJSONObject(i);
                                if (field.getString("labelid").equalsIgnoreCase("field_name_spanish")) {
                                    row.put("spanish_title", field.getString("value"));
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    for (HashMap<String, String> row : data) {
                        row.put("spanish_title", row.get("title"));
                    }
                }
                Collections.sort(data, new StringMapComparator("spanish_title"));
            }else{
                for (HashMap<String,String> row : data){
                    if(row.containsKey(LATITUDE) && row.containsKey(LONGITUDE) && row.get(LATITUDE).trim().length()>0 && row.get(LONGITUDE).trim().length()>0) {
                        LatLng fromLatlng = new LatLng(Double.parseDouble(((SmartActivity)mContext).getLatitude()),Double.parseDouble(((SmartActivity)mContext).getLongitude()));
                        LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                        row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng,toLatLng)));
                    }else{
                        row.put("distance", "0");
                    }
                }
                Collections.sort(data, new MapComparator("distance"));
            }
        }

        return data;
    }
    public ArrayList<HashMap<String,String>> getPreSearchEntriesFromDB(String keyword,String sectionId){
        IjoomerCaching caching = new IjoomerCaching(mContext);
        String query="";
        String[] key = keyword.split(",");
        for(int i=0;i<key.length;i++){
            String[] finalKey = key[i].trim().split(" ");
            if(finalKey.length>1){
                for (int j=0;j<finalKey.length;j++){
                    query+= "title like '%"+finalKey[j].trim()+"%' or field like '%"+finalKey[j].trim()+"%' or ";
                }
                query+= "title like '%"+key[i].trim()+"%' or field like '%"+key[i].trim()+"%' or ";
            }else{
                query+= "title like '%"+key[i].trim()+"%' or field like '%"+key[i].trim()+"%' or ";
            }
        }
        query = query.substring(0,query.length()-4);
        ArrayList<HashMap<String,String>> data = caching.getDataFromCache("entries","select * from entries where ("+query+") and sectionid='"+sectionId+"'");
        for (HashMap<String,String> row : data){
            if(row.containsKey(LATITUDE) && row.containsKey(LONGITUDE) && row.get(LATITUDE).trim().length()>0 && row.get(LONGITUDE).trim().length()>0) {
                LatLng fromLatlng = new LatLng(Double.parseDouble(((SmartActivity)mContext).getLatitude()),Double.parseDouble(((SmartActivity)mContext).getLongitude()));
                LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng,toLatLng)));
            }else{
                row.put("distance", "0");
            }
        }
        Collections.sort(data, new MapComparator("distance"));
        return data;

    }


    public void getPreSearchEntries(final String tableName,final String sectionID, final String keyword,final String latitude,final String longitude, final WebCallListenerWithCacheInfo target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

                IjoomerWebService iw = new IjoomerWebService();

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    iw.reset();

                    iw.addWSParam(EXTNAME, SOBIPRO);
                    iw.addWSParam(EXTVIEW, ISOBIPRO);
                    iw.addWSParam(EXTTASK, SEARCHRESULTS);

                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(SECTION_ID, sectionID);
                        taskData.put(KEYWORDS, keyword);
                        taskData.put(PAGENO, getPageNo());
                        taskData.put(LATITUDE, latitude);
                        taskData.put(LONGITUDE, longitude);

                    } catch (Throwable e) {
                    }
                    iw.addWSParam(TASKDATA, taskData);

                    if (IjoomerApplicationConfiguration.isCachEnable && !IjoomerApplicationConfiguration.isReloadRequired) {
                        try {
                            final ArrayList<HashMap<String, String>> data = getEntriesFromCacheByReqObject(tableName, iw.getWSParameter().toString());
                            if (data != null && data.size() > 0) {
                                ((Activity) mContext).runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        target.onProgressUpdate(100);
                                        target.onCallComplete(200, "", data, null, getPageNo(), Integer.parseInt(data.get(0).get(PAGELIMIT)), true);

                                    }
                                });
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
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
                            setPagingParams(Integer.parseInt(iw.getJsonObject().getString(PAGELIMIT)), Integer.parseInt(iw.getJsonObject().getString(TOTAL)));
                            iw.getJsonObject().remove(TOTAL);
                            if (iw.getJsonObject().getJSONArray("entries") != null) {
                                IjoomerCaching caching = new IjoomerCaching(mContext);
                                caching.addExtraColumn(PAGENO, (getPageNo() - 1) + "");
                                caching.addExtraColumn(PAGELIMIT, iw.getJsonObject().get(PAGELIMIT).toString());
                                caching.addExtraColumn(SECTIONID, iw.getJsonObject().get(SECTIONID).toString());
                                caching.addExtraColumn(CATID, iw.getJsonObject().get(CATID).toString());
                                caching.addExtraColumn(FAVOURITE, "0");
                                iw.getJsonObject().remove(PAGELIMIT);
                                iw.getJsonObject().remove(SECTIONID);
                                iw.getJsonObject().remove(CATID);
                                caching.setReqObject(iw.getWSParameter().toString());
                                caching.cacheData(iw.getJsonObject(), false, tableName);
                                return getEntriesFromCacheByReqObject(tableName, iw.getWSParameter().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
                    super.onPostExecute(result);
                    target.onProgressUpdate(100);
                    target.onCallComplete(getResponseCode(), getErrorMessage(), result, null, getPageNo() - 1, getPageLimit(), false);
                    isCalling = false;
                }

            }.execute();
        } else {
            isCalling = false;
            target.onCallComplete(getResponseCode(), getErrorMessage(), null, null, 0, 0, false);
            target.onProgressUpdate(100);
        }
    }

    /**
     * This method used to get entries from cached database
     *
     * @param table
     *            represented table name
     * @param entryID
     *            represented entry id
     * @return {@link ArrayList<HashMap<String, String>>}
     */
    public ArrayList<HashMap<String, String>> getEntriesFromCache(String table, String entryID) {

        try {

            ArrayList<HashMap<String, String>> fields = new IjoomerCaching(mContext).getDataFromCache(table, "select * from " + table + " where id='" + entryID + "' order by rowid");

            return fields;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to get entries in sequence
     *
     * @param table
     *            represented table name
     * @param reqObject
     *            represented request call object of getting entries
     * @return {@link List<ArrayList<HashMap<String,String>>>}
     */
    public ArrayList<HashMap<String, String>> getEntriesFromCacheByReqObject(String table, String reqObject) {

        try {
            ArrayList<HashMap<String, String>> fieldList = new ArrayList<HashMap<String, String>>();

            ArrayList<HashMap<String, String>> ids = new IjoomerCaching(mContext).getDataFromCache(table, "select id from '" + table + "'group by " + ID + " having reqObject='"
                    + reqObject + "' order by rowid");

            for (HashMap<String, String> hashMap : ids) {
                String id = hashMap.get(ID);

                ArrayList<HashMap<String, String>> fields = new IjoomerCaching(mContext).getDataFromCache(table, "select * from '" + table + "' where id= " + id
                        + " order by rowid");
                fieldList.add(fields.get(0));
            }
            for (HashMap<String,String> row : fieldList){
                if(row.containsKey(LATITUDE) && row.containsKey(LONGITUDE) && row.get(LATITUDE).trim().length()>0 && row.get(LONGITUDE).trim().length()>0) {
                    LatLng fromLatlng = new LatLng(Double.parseDouble(((SmartActivity)mContext).getLatitude()),Double.parseDouble(((SmartActivity)mContext).getLongitude()));
                    LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                    row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng,toLatLng)));
                }else{
                    row.put("distance", "0");
                }
            }
            Collections.sort(fieldList, new MapComparator("distance"));
            return fieldList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class StringMapComparator implements Comparator<Map<String, String>> {
        private final String key;

        public StringMapComparator(String key){
            this.key = key;
        }

        public int compare(Map<String, String> first,Map<String, String> second){
            return first.get(key).compareToIgnoreCase(second.get(key));
        }
    }
    class MapComparator implements Comparator<Map<String, String>> {
        private final String key;

        public MapComparator(String key){
            this.key = key;
        }

        public int compare(Map<String, String> first,
                           Map<String, String> second){
            double firstValue = Double.parseDouble(first.get(key));
            double secondValue = Double.parseDouble(second.get(key));
            return firstValue < secondValue ? -1 : firstValue > secondValue ? 1 : 0;
        }
    }

    /**
     * This method is used to add entry in favourite entry table
     *
     * @param entry
     *            represented entry data
     * @param pageLayout
     *            represented pageLayout
     */
    public void addToFavourite(ArrayList<HashMap<String, String>> entry, String pageLayout) {
        try {
            for (int i = 0; i < entry.size(); i++) {
                entry.get(i).put("pageLayout", pageLayout);
                entry.get(i).remove(COSLAT);
                entry.get(i).remove(COSLNG);
                entry.get(i).remove(SINLAT);
                entry.get(i).remove(SINLNG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new IjoomerCaching(mContext).createTable(entry, TABLE_FAVOURITE);
    }

    public void startDownloadEntryImages(final ImageDownloadListener listener) {

        if (IjoomerUtilities.isNetwokReachable()) {

            IjoomerCaching caching = new IjoomerCaching(mContext);
            ArrayList<HashMap<String, String>> entries = caching.getDataFromCache("entryFields",
                    "SELECT value FROM entryFields where labelid like 'field_file' or labelid like 'field_logo' or labelid like 'field_image_1' or labelid like 'field_image_2' or labelid like 'field_image_3' or labelid like 'field_image_4' or labelid like 'field_image_5' or labelid like 'field_image_6'");
            final List<String> imagesList = new LinkedList<String>();

            for (HashMap<String, String> row : entries) {

                if (row.get("value").trim().length() > 0) {
                    imagesList.add(row.get("value").trim());
                }
            }

            for (String url : imagesList) {
                aQuery.ajax(url.trim(), Bitmap.class, 0, new AjaxCallback<Bitmap>() {
                    @Override
                    public void callback(String url, Bitmap object, AjaxStatus status) {
                        super.callback(url, object, status);
                        if (status.getCode()!=404 && status.getCode() != 200 && (!imageLoadingProblem)) {
                            count++;
                            imageLoadingProblem = true;
                            listener.onImgeDownload(imagesList.size(), -1);
                            if (count == imagesList.size()) {
                                listener.onTaskComplete();
                            }
                        } else {
                            count++;
                            System.out.println("ImageDownload : " + count);
                            listener.onImgeDownload(imagesList.size(), count);
                            if (count == imagesList.size()) {
                                listener.onTaskComplete();
                            }
                            if (object != null)
                                object.recycle();
                        }

                    }
                });
            }

        } else {
            listener.onImgeDownload(0, -1);
        }
    }
}
