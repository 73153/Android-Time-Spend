package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.oauth.IjoomerOauth;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This Fragment Contains All Method Related To JomAlbumAllFragment.
 *
 * @author tasol
 *
 */
@SuppressLint("ValidFragment")
public class IjoomerIntafyNetwoekListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private ListView lstNetwork;
    private SmartListAdapterWithHolder adapterWithHolder;
    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private AQuery androidQuery;

    private IntafyNetworkDataProvider intafyNetworkDataProvider;
    private ArrayList<HashMap<String,String>> networkData;
    public boolean isRemoveActive;
    public String selectedNetworkId ="";
    public String selectedNetworkUserId ="";
    public HashMap<String,String> selectedNetworMap;

    public IjoomerIntafyNetwoekListFragment() {
    }

    /**
     * Overrides method
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_network_list_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        lstNetwork = (ListView) currentView.findViewById(R.id.lstNetwork);
        intafyNetworkDataProvider =  new IntafyNetworkDataProvider(getActivity());
        androidQuery = new AQuery(getActivity());
        selectedNetworMap = new HashMap<String, String>();

    }

    @Override
    public void prepareViews(View currentView) {
        getNetworkList(true);
    }

    @Override
    public void setActionListeners(View currentView) {
        lstNetwork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                ViewHolder holder = (ViewHolder) view.getTag();
                if (position % 2 == 0) {
                    if(isRemoveActive){
                        if (selectedNetworMap.containsKey(networkData.get(position).get(NETWORKID)+","+networkData.get(position).get(USERID))) {
                            selectedNetworMap.remove(networkData.get(position).get(NETWORKID)+","+networkData.get(position).get(USERID));
                            holder.txtLeftNetworkTitle.setTextColor(getResources().getColor(R.color.blue));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                        } else {
                            holder.txtLeftNetworkTitle.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                            selectedNetworMap.put(networkData.get(position).get(NETWORKID)+","+networkData.get(position).get(USERID), networkData.get(position).get(USERID));
                        }
                    }
                } else {
                    if (isRemoveActive) {
                        if (selectedNetworMap.containsKey(networkData.get(position).get(NETWORKID)+","+networkData.get(position).get(USERID))) {
                            selectedNetworMap.remove(networkData.get(position).get(NETWORKID)+","+networkData.get(position).get(USERID));
                            holder.txtRightNetworkTitle.setTextColor(getResources().getColor(R.color.blue));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                        } else {
                            holder.txtRightNetworkTitle.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                            selectedNetworMap.put(networkData.get(position).get(NETWORKID)+","+networkData.get(position).get(USERID), networkData.get(position).get(USERID));
                        }
                    }
                }
            }
        });
    }


    public String getSelectedNetworkId(){
        selectedNetworkId = "";
        Iterator<String> itr = selectedNetworMap.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            String[] keyFirst = key.split(",");
            selectedNetworkId += keyFirst[0]+",";
        }
        return selectedNetworkId.substring(0, selectedNetworkId.length()-1);
    }
    public String getSelectedNetworkUserId(){
        selectedNetworkUserId = "";
        Iterator<String> itr = selectedNetworMap.keySet().iterator();
        while (itr.hasNext()) {
            selectedNetworkUserId += selectedNetworMap.get(itr.next())+",";
        }
        return selectedNetworkUserId.substring(0, selectedNetworkUserId.length()-1);
    }

    /**
     * Class methods
     */

    /**
     * This method used to update fragment.
     */
    public void update(boolean isShowNoNetworkDialog) {
        selectedNetworMap = new HashMap<String, String>();
        getNetworkList(isShowNoNetworkDialog);
    }


    private void getNetworkList(boolean isShowNoNetworkDialog){
        networkData = intafyNetworkDataProvider.getNetworkListFromDB();
        if(networkData!=null && networkData.size()>0){
            prepareList(networkData);
            adapterWithHolder = getListAdapter();
            lstNetwork.setAdapter(adapterWithHolder);
        }else{
            if(isShowNoNetworkDialog) {
                responseErrorMessageHandler();
            }
            lstNetwork.setAdapter(null);
        }
    }

    /**
     * This method used to prepare list for all album data.
     * @param data represented all album data list
     */
    private void prepareList(ArrayList<HashMap<String, String>> data) {
        if (data != null) {
            listData.clear();
            for (HashMap<String, String> hashMap : data) {
                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.ijoomer_intafy_network_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);
                item.setValues(obj);
                listData.add(item);
            }
        }

    }

    /**
     * This method used to shown response message.
     */
    private void responseErrorMessageHandler() {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_networks), getString(R.string.intafy_no_network), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    /**
     * List adapter for all album.
     */
    private SmartListAdapterWithHolder getListAdapter() {

        SmartListAdapterWithHolder adapterNetwork = new SmartListAdapterWithHolder(getActivity(), R.layout.ijoomer_intafy_network_list_item, listData, new ItemView() {

            @SuppressWarnings("unchecked")
            @Override
            public View setItemView(final int position, View v, SmartListItem item, ViewHolder holder) {

                holder.lnrLeftNetwork = (LinearLayout) v.findViewById(R.id.lnrLeftNetwork);
                holder.lnrLeftClickable = (LinearLayout) v.findViewById(R.id.lnrLeftClickable);
                holder.pbrLeftImageLoad = (ProgressBar) v.findViewById(R.id.pbrLeftImageLoad);
                holder.imgLeftNetworkIcon = (RoundedImageView) v.findViewById(R.id.imgLeftNetworkIcon);
                holder.txtLeftNetworkTitle = (IjoomerTextView) v.findViewById(R.id.txtLeftNetworkTitle);

                holder.lnrRightNetwork = (LinearLayout) v.findViewById(R.id.lnrRightNetwork);
                holder.lnrRightClickable = (LinearLayout) v.findViewById(R.id.lnrRightClickable);
                holder.pbrRightImageLoad = (ProgressBar) v.findViewById(R.id.pbrRightImageLoad);
                holder.imgRightNetworkIcon = (RoundedImageView) v.findViewById(R.id.imgRightNetworkIcon);
                holder.txtRightNetworkTitle = (IjoomerTextView) v.findViewById(R.id.txtRightNetworkTitle);

                final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

                if(position%2==0){
                    holder.lnrLeftNetwork.setVisibility(View.VISIBLE);
                    holder.lnrRightNetwork.setVisibility(View.GONE);
                    androidQuery.id(holder.imgLeftNetworkIcon).progress(holder.pbrLeftImageLoad).image(row.get(NETWORKICON),true,true,70,R.drawable.ic_launcher);
                    holder.txtLeftNetworkTitle.setText(row.get(TITLE));
                }else{
                    holder.lnrRightNetwork.setVisibility(View.VISIBLE);
                    holder.lnrLeftNetwork.setVisibility(View.GONE);
                    androidQuery.id(holder.imgRightNetworkIcon).progress(holder.pbrRightImageLoad).image(row.get(NETWORKICON),true,true,70,R.drawable.ic_launcher);
                    holder.txtRightNetworkTitle.setText(row.get(TITLE));
                }

                v.setTag(holder);
                return v;
            }

            @Override
            public View setItemView(int position, View v, SmartListItem item) {
                return null;
            }
        });
        return adapterNetwork;
    }

}
