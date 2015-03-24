package com.mycadiz.library.sobipro;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.mycadiz.caching.IjoomerCaching;
import com.mycadiz.common.classes.IjoomerPagingProvider;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.weservice.IjoomerWebService;
import com.mycadiz.weservice.ProgressListener;
import com.mycadiz.weservice.WebCallListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class Contains All Method Related To
 * SobiproAdvanceSearchFieldsDataProvider.
 *
 * @author tasol
 *
 */

public class SobiproAdvanceSearchFieldsDataProvider extends IjoomerPagingProvider {

    private Context mContext;
    private final String ISOBIPRO = "isobipro";
    private final String GETSEARCH = "getsearch";
    private final String GETSEARCHFIELD = "getsearchField";
    private final String SECTIONID = "sectionID";
    private final String KEYWORD = "keyword";
    private final String LATITUDE = "latitude";
    private final String LONGITUDE = "longitude";
    private final String FIELDTOWN = "field_town";
    private final String FIELDCATEGORY = "field_category";
    private final String CATID = "catid";
    private final String FAVOURITE = "favourite";
    private final String ID = "id";
    private boolean isCalling = false;
    private JSONArray treeViseCategory;


    public boolean isCalling() {
        return isCalling;
    }
    /**
     * Constructor
     *
     * @param mContext
     *            represented {@link Context}
     */

    public SobiproAdvanceSearchFieldsDataProvider(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


    public void getSearchCategory(final String sid, final WebCallListener target) {
        new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                IjoomerWebService iw = new IjoomerWebService();
                iw.reset();

                iw.addWSParam(EXTNAME, SOBIPRO);
                iw.addWSParam(EXTVIEW, ISOBIPRO);
                iw.addWSParam(EXTTASK, GETSEARCHFIELD);
                JSONObject taskData = new JSONObject();
                try {
                    taskData.put(SECTIONID, sid);
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
                        return new IjoomerCaching(mContext).parseData(iw.getJsonObject());

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

    public ArrayList<HashMap<String,String>> getSearchEntriesCategoryTownFromDB(String latitude,String longitude,String catid,String town,String sectionId){
        try{
            IjoomerCaching caching = new IjoomerCaching(mContext);
            if(catid.trim().length()>0 && town.trim().length()>0){
                ArrayList<HashMap<String,String>> data = caching.getDataFromCache("entries","select * from entries where catids like '%"+catid.trim()+"%' and sectionid='"+sectionId+"'");
                for (HashMap<String,String> row : data){
                    if(row.containsKey(LATITUDE) && row.containsKey(LONGITUDE) && row.get(LATITUDE).trim().length()>0 && row.get(LONGITUDE).trim().length()>0) {
                        LatLng fromLatlng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                        row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng, toLatLng)));
                    }else{
                        row.put("distance", "0");
                    }
                }

                Collections.sort(data, new MapComparator("distance"));
                return data;
            }else if(town.trim().length()>0){
                ArrayList<HashMap<String,String>> data = caching.getDataFromCache("entries","select * from entries where sectionid='"+sectionId+"'");
                for (HashMap<String,String> row : data){
                    if(row.containsKey(LATITUDE) && row.containsKey(LONGITUDE) && row.get(LATITUDE).trim().length()>0 && row.get(LONGITUDE).trim().length()>0) {
                        LatLng fromLatlng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                        row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng, toLatLng)));
                    }else{
                        row.put("distance", "0");
                    }
                }

                Collections.sort(data, new MapComparator("distance"));
                return data;
            }else{
                ArrayList<HashMap<String,String>> data = caching.getDataFromCache("entries","select * from entries where catids like '%"+catid.trim()+"%' and sectionid='"+sectionId+"'");
                for (HashMap<String,String> row : data){
                    if(row.containsKey(LATITUDE) && row.containsKey(LONGITUDE) && row.get(LATITUDE).trim().length()>0 && row.get(LONGITUDE).trim().length()>0) {
                        LatLng fromLatlng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                        row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng, toLatLng)));
                    }else{
                        row.put("distance", "0");
                    }
                }

                Collections.sort(data, new MapComparator("distance"));
                return data;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<HashMap<String,String>> getPreSearchEntriesFromDB(String latitude,String longitude,String keyword,String sectionId){
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
                LatLng fromLatlng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                LatLng toLatLng = new LatLng(Double.parseDouble(row.get(LATITUDE)), Double.parseDouble(row.get(LONGITUDE)));
                row.put("distance", String.valueOf(IjoomerUtilities.getDistanceBetweenLatLongsKilometers(fromLatlng, toLatLng)));
            }else{
                row.put("distance", "0");
            }
        }

        Collections.sort(data, new MapComparator("distance"));
        return data;
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

    public JSONArray getTreeViseCategory(String parentId,int level){
        if(level==0){
            treeViseCategory = new JSONArray();
        }
        IjoomerCaching caching = new IjoomerCaching(mContext);
        String sql;
        if(mContext.getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
            sql = "select * from categories where parent='"+parentId+"' order by name";
        }else{
            sql = "select * from categories where parent='"+parentId+"' order by name_spanish";
        }
        ArrayList<HashMap<String,String>> data = caching.getDataFromCache("categories",sql);
        try{
            for (HashMap<String,String> row :data){
                JSONObject category = new JSONObject();
                category.put("name",getLevel(level)+row.get("name"));
                category.put("name_spanish",getLevel(level)+row.get("name_spanish"));
                category.put("value",row.get("id"));
                String sql2;
                if(mContext.getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                    sql2 = "select * from categories where parent='"+row.get("id")+"' order by name";
                }else{
                    sql2 = "select * from categories where parent='"+row.get("id")+"' order by name_spanish";
                }
                ArrayList<HashMap<String,String>> data2 = caching.getDataFromCache("categories",sql2);
                if(data2!=null && data2.size()>0){
                    category.put("hasChild","1");
                }else{
                    category.put("hasChild","0");
                }
                treeViseCategory.put(category);
                if(data2!=null && data2.size()>0){
                    getTreeViseCategory(row.get("id"),level+1);
                }
            }
            return treeViseCategory;
        }catch (Throwable e){
            return new JSONArray();
        }
    }

    public JSONArray getTown(){
        JSONArray townArray = new JSONArray();
        IjoomerCaching caching = new IjoomerCaching(mContext);
        ArrayList<HashMap<String,String>> data = caching.getDataFromCache("entries","select distinct(town) from entries where [town] is not ''  order by lower(town)");
        try{
            for (HashMap<String,String> row :data){
                JSONObject town = new JSONObject();
                town.put("name",row.get("town"));
                town.put("value",row.get("town"));
                townArray.put(town);
            }
            return townArray;
        }catch (Throwable e){
            return new JSONArray();
        }
    }
    private String getLevel(int level){
        String levelString="";
        if(level>0){
            levelString="    ";
        }
        for (int i=0;i<level;i++){
            levelString+="> ";
        }
        return levelString;
    }
    public void search(final String table,final String sectionId,final String keyword,final String latitude,final String longitude,final String catgory,final String town ,final WebCallListener target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();

                    iw.addWSParam(EXTNAME, SOBIPRO);
                    iw.addWSParam(EXTVIEW, ISOBIPRO);
                    iw.addWSParam(EXTTASK, GETSEARCH);
                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(SECTIONID, sectionId);
                        taskData.put(KEYWORD, keyword);
                        taskData.put(LATITUDE, latitude);
                        taskData.put(LONGITUDE, longitude);
                        taskData.put(FIELDCATEGORY, catgory);
                        taskData.put(FIELDTOWN, town);
                        taskData.put(PAGENO, getPageNo());
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
                                caching.addExtraColumn("sectionid", iw.getJsonObject().get("sectionid").toString());
                                caching.addExtraColumn(CATID, iw.getJsonObject().get(CATID).toString());
                                caching.addExtraColumn(FAVOURITE, "0");
                                iw.getJsonObject().remove(PAGELIMIT);
                                iw.getJsonObject().remove("sectionid");
                                iw.getJsonObject().remove(CATID);
                                caching.setReqObject(iw.getWSParameter().toString());
                                caching.cacheData(iw.getJsonObject(), false, table);
                                return getEntriesFromCacheByReqObject(table, iw.getWSParameter().toString());
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

    public void searchLocal(final String table,final String sectionId,final String keyword,final String latitude,final String longitude,final String catgory,final String town ,final WebCallListener target) {
        if (hasNextPage()) {
            isCalling = true;
            new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

                @Override
                protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                    IjoomerWebService iw = new IjoomerWebService();
                    iw.reset();

                    iw.addWSParam(EXTNAME, SOBIPRO);
                    iw.addWSParam(EXTVIEW, ISOBIPRO);
                    iw.addWSParam(EXTTASK, GETSEARCH);
                    JSONObject taskData = new JSONObject();
                    try {
                        taskData.put(SECTIONID, sectionId);
                        taskData.put(KEYWORD, keyword);
                        taskData.put(LATITUDE, latitude);
                        taskData.put(LONGITUDE, longitude);
                        taskData.put(FIELDCATEGORY, catgory);
                        taskData.put(FIELDTOWN, town);
                        taskData.put(PAGENO, getPageNo());
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
                            if (iw.getJsonObject().getJSONArray("entries") != null) {
                                IjoomerCaching caching = new IjoomerCaching(mContext);
                                caching.cacheData(iw.getJsonObject().getJSONArray("entries"), false, table);
                                return null;
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
     * This method is used to get entries in sequence
     *
     * @param table
     *            represented table name
     * @param reqObject
     *            represented request call object of getting entries
     * @return {@link java.util.List <ArrayList<HashMap<String,String>>>}
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
            return fieldList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
