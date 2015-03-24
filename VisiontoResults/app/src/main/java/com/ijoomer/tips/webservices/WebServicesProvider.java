package com.ijoomer.tips.webservices;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by tasol on 22/12/14.
 */
public class WebServicesProvider {

    private Context context;

    public WebServicesProvider(Context context){
        this.context=context;
    }
    public void suggestTip(final String name,final String email,final String tip,final String twitterHandler,final WebServiceListener listener){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {
                WebServices webServices = new WebServices(context);
                return webServices.wsSuggestTip(name,email,tip,twitterHandler);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null && s.equals("200")){
                    listener.onComplete(200);
                }else{
                    listener.onComplete(500);
                }
            }
        }.execute();
    }

    public void callMeBack(final String name,final String contactNo,final String country,final String preferredTime,final WebServiceListener listener){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {
                WebServices webServices = new WebServices(context);
                return webServices.wsCallMeBack(name, contactNo, country, preferredTime);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null && s.equals("200")){
                    listener.onComplete(200);
                }else{
                    listener.onComplete(500);
                }
            }
        }.execute();
    }

    public void vtrEBook(final String email,final WebServiceListener listener){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {
                WebServices webServices = new WebServices(context);
                return webServices.wsVtreBook(email);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null && s.equals("200")){
                    listener.onComplete(200);
                }else{
                    listener.onComplete(500);
                }
            }
        }.execute();
    }
}
