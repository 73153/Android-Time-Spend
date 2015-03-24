package com.ijoomer.tips;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.database.DataBaseProvider;
import com.ijoomer.tips.utilities.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class TipsListFragment extends ListFragment {

    private View view;
    private CustomBoldTextView txtSuiteDriverName;
    private ImageView imgSuiteIcon;
    private DataBaseProvider dataBaseProvider;
    private int suiteId;
    private int driverId;
    private String suiteName;
    private String driverName;

    private ArrayList<HashMap<String,String>> tipsList;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tips_fragment,null);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f));
        txtSuiteDriverName =(CustomBoldTextView) view.findViewById(R.id.txtSuiteDriverName);
        imgSuiteIcon =(ImageView) view.findViewById(R.id.imgSuiteIcon);

        dataBaseProvider = new DataBaseProvider(getActivity());

        txtSuiteDriverName.setLineSpacing(1f,1f);

        suiteId = getArguments().getInt(Utility.SUITEID);
        driverId = getArguments().getInt(Utility.DRIVERID);
        suiteName = getArguments().getString(Utility.SUITENAME);
        driverName = getArguments().getString(Utility.DRIVERNAME);

        if(suiteId== 1){
            imgSuiteIcon.setImageResource(R.drawable.suite_one_corner);
            txtSuiteDriverName.setText(Html.fromHtml("<font color='#ee2b7b'>" + suiteName + " / </font> <br/>" + "<font color='#60605b'>" + driverName + "</font>"));
        }else if(suiteId == 2){
            imgSuiteIcon.setImageResource(R.drawable.suite_two_corner);
            txtSuiteDriverName.setText(Html.fromHtml("<font color='#009bdf'>" + suiteName + " / </font> <br/>" + "<font color='#60605b'>" + driverName + "</font>"));
        }else if(suiteId == 3){
            imgSuiteIcon.setImageResource(R.drawable.suite_three_corner);
            txtSuiteDriverName.setText(Html.fromHtml("<font color='#3daf2c'>" + suiteName + " / </font> <br/>" + "<font color='#60605b'>" + driverName + "</font>"));
        }else{
            imgSuiteIcon.setImageResource(R.drawable.suite_four_corner);
            txtSuiteDriverName.setText(Html.fromHtml("<font color='#f7931e'>" + suiteName + " / </font> <br/>" + "<font color='#60605b'>" + driverName + "</font>"));
        }
        getTips(driverId);


        return view;
    }


    private void getTips(final int id){
        ((TipsCollectionActivity)getActivity()).showProgressBar();
        new AsyncTask<Void, Void, ArrayList<HashMap<String,String>>>() {
            @Override
            protected ArrayList<HashMap<String,String>> doInBackground(Void... params) {
                ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
                dataBaseProvider.open();
                try {
                    data = dataBaseProvider.getTips(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String,String>> response) {
                super.onPostExecute(response);
                tipsList = response;
                dataBaseProvider.close();
                setListAdapter(new DriversAdapter(getActivity(),response));
                ((TipsCollectionActivity)getActivity()).hideProgressBar();
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ((TipsCollectionActivity)getActivity()).gotoTipDetail(suiteId, driverId, suiteName, driverName, Integer.parseInt(tipsList.get(position).get(Utility.ID)), tipsList.get(position).get(Utility.NAME), tipsList.get(position).get(Utility.NOTE),Integer.parseInt(tipsList.get(position).get(Utility.FAVOURITE)),tipsList.get(position).get(Utility.QUESTIONONE),tipsList.get(position).get(Utility.QUESTIONTWO),tipsList.get(position).get(Utility.QUESTIONTHREE));
                    }
                });
            }
        }.execute();

    }

    class DriversAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<HashMap<String,String>> tipsList;

        public DriversAdapter(Context context,ArrayList<HashMap<String,String>> tipsList){
            this.context=context;
            this.tipsList =tipsList;
        }

        @Override
        public int getCount() {
            return tipsList.size();
        }

        @Override
        public Object getItem(int position) {
            return tipsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.tips_fragment_list_item,null);
                holder.txtTipName = (CustomTextView) convertView.findViewById(R.id.txtTipName);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtTipName.setText(tipsList.get(position).get(Utility.NAME));

            return convertView;
        }

        class ViewHolder{
            CustomTextView txtTipName;
        }
    }

}
