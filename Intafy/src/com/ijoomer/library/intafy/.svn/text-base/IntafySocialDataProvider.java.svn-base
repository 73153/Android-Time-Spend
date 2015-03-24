package com.ijoomer.library.intafy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerPagingProvider;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.ProgressListener;
import com.ijoomer.weservice.WebCallListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This Class Contains All Method Related To IntafyNetworkDataProvider.
 *
 * @author tasol
 *
 */
public class IntafySocialDataProvider extends IjoomerPagingProvider {
    private Context mContext;
    private String facebookPageUrl="https://www.facebook.com/feeds/page.php?format=json&id=";
    private String SOCIALTABLE="social";
    private Twitter twitter;

    /**
     * Constructor
     *
     * @param context
     *            represented {@link android.content.Context}
     */
    public IntafySocialDataProvider(Context context) {
        super(context);
        mContext = context;
    }



    public void getNetworkFeed(final String facebookPageId,final String twitterPageName,final String networkId,final String connectedUserId,final WebCallListener target) {

        AQuery aQuery = new AQuery(mContext);
        ArrayList<HashMap<String,String>> data =getSocialFeed(networkId,connectedUserId);
        if(data!=null && data.size()>0){
            target.onCallComplete(200,"",data,null);
        }
        aQuery.ajax(facebookPageUrl+facebookPageId,String.class,new AjaxCallback<String>(){
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                super.callback(url, object, status);
                IjoomerCaching ijoomerCaching = new IjoomerCaching(mContext);
                try{
                    JSONObject response = new JSONObject(object);
                    ijoomerCaching.addExtraColumn("networkId",networkId);
                    ijoomerCaching.addExtraColumn("connectedUserId",connectedUserId);

                    JSONArray responeArray = response.getJSONArray("entries");
                    if(responeArray.length()>0) {
                        JSONArray cacheArray = new JSONArray();
                        for (int i = 0; i < responeArray.length(); i++) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("user", "");
                            try {
                                jsonObject.put("id", responeArray.getJSONObject(i).getString("id"));
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObject.put("userName", responeArray.getJSONObject(i).getJSONObject("author").getString("name"));
                            } catch (Throwable e) {
                                e.printStackTrace();
                                jsonObject.put("userName", "");
                            }
                            try {
                                jsonObject.put("userAvatar", "");
                            } catch (Throwable e) {
                                e.printStackTrace();
                                jsonObject.put("userAvatar", "");
                            }
                            try {
                                jsonObject.put("title", responeArray.getJSONObject(i).getString("title"));
                            } catch (Throwable e) {
                                e.printStackTrace();
                                jsonObject.put("title", "");
                            }
                            try {
                                jsonObject.put("content", responeArray.getJSONObject(i).getString("content"));
                            } catch (Throwable e) {
                                e.printStackTrace();
                                jsonObject.put("content", "");
                            }
                            try {
                                jsonObject.put("date", IjoomerUtilities.getTimeStampFormatedFromDate(responeArray.getJSONObject(i).getString("updated"), "yyyy-MM-dd'T'HH:mm:ssZ"));
                            } catch (Throwable e) {
                                e.printStackTrace();
                                jsonObject.put("date", "");
                            }
                            jsonObject.put("type", "facebook");
                            cacheArray.put(jsonObject);
                        }
                        ijoomerCaching.cacheData(cacheArray, false, SOCIALTABLE);
                    }
                    getTwitterFeed(twitterPageName, networkId, connectedUserId, target);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

    }

    public void getTwitterFeed(final String pageName,final String networkId,final String connectedUserId,final WebCallListener target) {

        new AsyncTask<Void,Void,ArrayList<HashMap<String,String>>>(){

            @Override
            protected ArrayList<HashMap<String,String>> doInBackground(Void... params) {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("ACGuGZRQI4rASvX4uHgDw")
                        .setOAuthConsumerSecret(
                                "n2zv5dXGbvav3FCb63sk3rIYH8zz74is69dUkINlsgg")
                        .setOAuthAccessToken(
                                "1263785803-BCjJONAR12YmcEeZ2DFbWBilUqGiXZ8FfRn6DKR")
                        .setOAuthAccessTokenSecret(
                                "oa8SOkiHaDdqsDRzDDklrKxhOBXeF7iWtzIpdG94eI");
                TwitterFactory tf = new TwitterFactory(cb.build());
                twitter = tf.getInstance();
                Paging paging = new Paging(1,20);
                try {
                    List<twitter4j.Status> statuses = twitter.getUserTimeline(pageName, paging);
                    if (statuses != null && statuses.size() > 0) {
                        JSONArray feedList = new JSONArray();
                        for(twitter4j.Status status : statuses){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("user",""+status.getUser().getId());
                            jsonObject.put("id",""+status.getId());
                            jsonObject.put("userName",status.getUser().getName());
                            jsonObject.put("userAvatar",status.getUser().getProfileImageURL());
                            jsonObject.put("date",IjoomerUtilities.getTimeStampFormatedFromDate(status.getCreatedAt().toString(), "EEE MMM dd HH:mm:ss Z yyyy"));
                            jsonObject.put("title", status.getText());
                            jsonObject.put("content", "");
                            jsonObject.put("type", "twitter");
                            feedList.put(jsonObject);
                        }
                        IjoomerCaching ijoomerCaching = new IjoomerCaching(mContext);
                        ijoomerCaching.addExtraColumn("networkId",networkId);
                        ijoomerCaching.addExtraColumn("connectedUserId", connectedUserId);
                        ijoomerCaching.cacheData(feedList, false, SOCIALTABLE);
                        return getSocialFeed(networkId,connectedUserId);
                    }else{
                        return new ArrayList<HashMap<String, String>>();
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                    target.onCallComplete(200,"",null,null);
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String,String>> result) {
                super.onPostExecute(result);
                target.onCallComplete(200,"",result,null);

            }
        }.execute();

    }

    public ArrayList<HashMap<String,String>> getSocialFeed(String networkId,String connectedUserId){
        try {
            IjoomerCaching caching = new IjoomerCaching(mContext);
            return caching.getDataFromCache(SOCIALTABLE,"select * from "+SOCIALTABLE+" where networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' order by date desc");
        }catch (Throwable e){
            e.printStackTrace();
            return new ArrayList<HashMap<String, String>>();
        }
    }

    public ArrayList<HashMap<String,String>> getFacebookFeedFromDB(String networkId,String connectedUserId){
        try {
            IjoomerCaching caching = new IjoomerCaching(mContext);
            return caching.getDataFromCache(SOCIALTABLE,"select * from "+SOCIALTABLE+" where networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and type='facebook' order by date desc");
        }catch (Throwable e){
            e.printStackTrace();
            return new ArrayList<HashMap<String, String>>();
        }
    }
    public ArrayList<HashMap<String,String>> getTwitterFeedFromDB(String networkId,String connectedUserId){
        try {
            IjoomerCaching caching = new IjoomerCaching(mContext);
            return caching.getDataFromCache(SOCIALTABLE,"select * from "+SOCIALTABLE+" where networkId='"+networkId+"' and connectedUserId='"+connectedUserId+"' and type='twitter' order by date desc");
        }catch (Throwable e){
            e.printStackTrace();
            return new ArrayList<HashMap<String, String>>();
        }
    }

}
