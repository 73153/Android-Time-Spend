package com.ijoomer.tips;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.database.DataBaseProvider;
import com.ijoomer.tips.utilities.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class DriversListFragment extends ListFragment {

    private View view;
    private CustomBoldTextView txtSuiteName;
    private ImageView imgSuiteIcon;
    private DataBaseProvider dataBaseProvider;
    private String suiteName;
    private int suiteId;
    private ArrayList<HashMap<String,String>> driverList;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drivers_fragment,null);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f));
        txtSuiteName =(CustomBoldTextView) view.findViewById(R.id.txtSuiteName);
        imgSuiteIcon =(ImageView) view.findViewById(R.id.imgSuiteIcon);
        dataBaseProvider = new DataBaseProvider(getActivity());

        suiteId = getArguments().getInt(Utility.SUITEID);
        suiteName = getArguments().getString(Utility.SUITENAME);
        txtSuiteName.setText(suiteName);

        if(suiteId== 1){
            txtSuiteName.setTextColor(getResources().getColor(R.color.pink));
            imgSuiteIcon.setImageResource(R.drawable.suite_one_corner);
        }else if(suiteId == 2){
            txtSuiteName.setTextColor(getResources().getColor(R.color.blue));
            imgSuiteIcon.setImageResource(R.drawable.suite_two_corner);
        }else if(suiteId == 3){
            txtSuiteName.setTextColor(getResources().getColor(R.color.green));
            imgSuiteIcon.setImageResource(R.drawable.suite_three_corner);
        }else{
            txtSuiteName.setTextColor(getResources().getColor(R.color.orange));
            imgSuiteIcon.setImageResource(R.drawable.suite_four_corner);
        }
        getDrivers(suiteId);




        return view;
    }


    private void getDrivers(final int id){
        new AsyncTask<Void, Void, ArrayList<HashMap<String,String>>>() {
            @Override
            protected ArrayList<HashMap<String,String>> doInBackground(Void... params) {
                ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
              dataBaseProvider.open();
                try {
                    data = dataBaseProvider.getDrivers(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String,String>> response) {
                super.onPostExecute(response);
                driverList = response;
                dataBaseProvider.close();
                setListAdapter(new DriversAdapter(getActivity(),driverList));
                ((TipsCollectionActivity)getActivity()).hideProgressBar();
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ((TipsCollectionActivity)getActivity()).gotoTips(suiteId,Integer.parseInt(driverList.get(position).get(Utility.ID)),suiteName,driverList.get(position).get(Utility.NAME));
                    }
                });
            }
        }.execute();

    }

    class DriversAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<HashMap<String,String>> driverList;

        public DriversAdapter(Context context,ArrayList<HashMap<String,String>> driverList){
            this.context=context;
            this.driverList =driverList;
        }

        @Override
        public int getCount() {
            return driverList.size();
        }

        @Override
        public Object getItem(int position) {
            return driverList.get(position);
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
                convertView = LayoutInflater.from(context).inflate(R.layout.drivers_fragment_list_item,null);
                holder.txtDriverName = (CustomBoldTextView) convertView.findViewById(R.id.txtDriverName);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtDriverName.setText(driverList.get(position).get(Utility.NAME));

            return convertView;
        }

        class ViewHolder{
            CustomBoldTextView txtDriverName;
        }
    }

}
