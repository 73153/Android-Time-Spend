package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerIndexableListView;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyDirectoriesDataProvider;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterIndexScrolling;
import com.smart.framework.SmartListItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Fragment Contains All Method Related To JomAlbumAllFragment.
 *
 * @author tasol
 *
 */
@SuppressLint("ValidFragment")
public class IjoomerIntafySelectUserForMessageListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private IjoomerIndexableListView lstDirectory;
    private LinearLayout listFooter;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    public ArrayList<HashMap<String,String>> memberData = new ArrayList<HashMap<String, String>>();
    private AQuery androidQuery;
    private SmartListAdapterIndexScrolling listAdapterWithHolder;
    private String[] sections;
    private HashMap<String, Integer> alphaIndexer;
    private ArrayList<String> sectionArray;

    private IntafyDirectoriesDataProvider intafyDirectoriesDataProvider;
    private IntafyNetworkDataProvider intafyNetworkDataProvider;
    public HashMap<String,String> selectedUserMap;
    private SeekBar seekBar;
    private int getDataCount=0;
    public int lastCount;
    public IjoomerIntafySelectUserForMessageListFragment(HashMap<String, String> selectedUserMap) {
       this.selectedUserMap = selectedUserMap;
    }

    /**
     * Overrides method
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_directory_list_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        listFooter = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.ijoomer_list_footer, null);
        lstDirectory = (IjoomerIndexableListView) currentView.findViewById(R.id.lstDirectory);
        lstDirectory.addFooterView(listFooter, null, false);
        lstDirectory.setAdapter(null);
        alphaIndexer = new HashMap<String, Integer>();
        sectionArray = new ArrayList<String>();
        androidQuery = new AQuery(getActivity());
        intafyDirectoriesDataProvider = new IntafyDirectoriesDataProvider(getActivity());
        intafyNetworkDataProvider = new IntafyNetworkDataProvider(getActivity());
        lstDirectory.setFastScrollEnabled(true);
        lstDirectory.setDivider(null);
        lstDirectory.setDividerHeight(0);
        if(selectedUserMap==null) {
            selectedUserMap = new HashMap<String, String>();
        }
    }

    @Override
    public void prepareViews(View currentView) {
        getMemberList(true);
    }

    @Override
    public void setActionListeners(View currentView) {

        lstDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ViewHolder holder = (ViewHolder) view.getTag();
                if (position % 2 == 0) {
                    if (selectedUserMap.containsKey(memberData.get(position).get(USER_ID))) {
                        selectedUserMap.remove(memberData.get(position).get(USER_ID));
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                    } else {
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                        selectedUserMap.put(memberData.get(position).get(USER_ID), memberData.get(position).get(USER_NAME));
                    }
                } else {
                    if (selectedUserMap.containsKey(memberData.get(position).get(USER_ID))) {
                        selectedUserMap.remove(memberData.get(position).get(USER_ID));
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                    } else {
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                        selectedUserMap.put(memberData.get(position).get(USER_ID), memberData.get(position).get(USER_NAME));
                    }
                }
            }
        });
        lstDirectory.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(getDataCount>1) {
                    if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
                        if (!intafyDirectoriesDataProvider.isCalling() && intafyDirectoriesDataProvider.hasNextPage()) {
                            listFooterVisible();
                            String networkType = intafyNetworkDataProvider.getNetworkType(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0"));
                            String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                            String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                            String connectedUserType = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERTYPE, "");
                            getDataCount = 0;
                            intafyDirectoriesDataProvider.getMembersForMessageList(networkId, networkType, connectedUserId, connectedUserType, new WebCallListenerWithCacheInfo() {

                                @Override
                                public void onProgressUpdate(int progressCount) {
                                }

                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit,
                                                           boolean fromCache) {
                                    try {
                                        listFooterInvisible();
                                        if (responseCode == 200) {
                                            prepareList(data1, true, fromCache, pageNo, pageLimit);
                                            if(fromCache){
                                                getDataCount++;
                                            }else{
                                                lastCount=data1.size();
                                                getDataCount+=2;
                                            }
                                        } else {
                                            responseErrorMessageHandler(responseCode);
                                        }
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }



    public HashMap<String ,String> getInvitedUsers(){
        return selectedUserMap;
    }

    private void getMemberList(boolean isShowProgress){
        getDataCount=0;
        intafyDirectoriesDataProvider.restorePagingSettings();
        if(isShowProgress){
            seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        }
        String networkType  = intafyNetworkDataProvider.getNetworkType(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0"));
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        String connectedUserType = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERTYPE,"");
        intafyDirectoriesDataProvider.getMembersForMessageList(networkId,networkType,connectedUserId,connectedUserType,new WebCallListenerWithCacheInfo() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit, boolean fromCache) {
                try{
                if (responseCode == 200) {
                    if (data1 != null) {
                        prepareList(data1, false, fromCache, pageNo, pageLimit);
                        listAdapterWithHolder = getListAdapter();
                        lstDirectory.setAdapter(listAdapterWithHolder);
                        if(fromCache){
                            getDataCount++;
                        }else{
                            lastCount=data1.size();
                            getDataCount+=2;
                        }
                    }
                } else {
                    responseErrorMessageHandler(responseCode);
                }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgressUpdate(int progressCount) {
                seekBar.setProgress(progressCount);
            }
        });
    }

    /**
     * Class methods
     */

    /**
     * This method used to update fragment.
     */
    public void update() {
        getMemberList(false);
    }

    /**
     * This method used to visible list footer
     */
    public void listFooterVisible() {
        listFooter.setVisibility(View.VISIBLE);
    }

    /**
     * This method used to gone list footer
     */
    public void listFooterInvisible() {
        listFooter.setVisibility(View.GONE);
    }

    public void prepareList(ArrayList<HashMap<String, String>> data, boolean append, boolean fromCache, int pageno, int pagelimit) {
        sectionArray.clear();
        if (data != null) {
            if (!append) {
                alphaIndexer = new HashMap<String, Integer>();
                sectionArray = new ArrayList<String>();
                listData.clear();
                memberData.clear();
                memberData.addAll(data);
            } else {
                if (!fromCache) {
                    memberData.addAll(data);
                    int startIndex = ((pageno - 1) * pagelimit);
                    int endIndex = listAdapterWithHolder.getCount();
                    if (startIndex > 0 && lastCount>=startIndex) {
                        for (int i = endIndex; i >= startIndex; i--) {
                            try {
                                listAdapterWithHolder.remove(listAdapterWithHolder.getItem(i));
                                listData.remove(i);
                            } catch (Exception e) {
                            }
                        }
                    }else{
                        for (int i = endIndex; i >= lastCount; i--) {
                            try {
                                listAdapterWithHolder.remove(listAdapterWithHolder.getItem(i));
                                listData.remove(i);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }

            for (int i=0;i<data.size();i++){
                HashMap<String, String> hashMap = data.get(i);
                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.ijoomer_intafy_directory_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);
                if (!alphaIndexer.containsKey(hashMap.get(USER_NAME))) {
                    alphaIndexer.put(hashMap.get(USER_NAME), i);
                    sectionArray.add(hashMap.get(USER_NAME));
                    obj.add(hashMap.get(USER_NAME));
                }
                item.setValues(obj);
                if (append) {
                    listAdapterWithHolder.add(item);
                } else {
                    listData.add(item);
                }
            }
            setSections();
        }
    }

    /**
     * This method used to shown response message.
     * @param responseCode represented response code
     */
    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_directory), getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    public void setSections() {

        sections = new String[sectionArray.size()];
        for (int i = 0; i < sectionArray.size(); i++) {
            String ch = sectionArray.get(i).substring(0, 1);
            ch = ch.toUpperCase();
            sections[i] = ch;
        }

    }

    /**
     * List adapter for all album.
     */
    private SmartListAdapterIndexScrolling getListAdapter() {

        SmartListAdapterIndexScrolling adapterNetwork = new SmartListAdapterIndexScrolling(getActivity(), R.layout.ijoomer_intafy_directory_list_item, listData,sections, alphaIndexer,
                sectionArray, new ItemView() {

            @SuppressWarnings("unchecked")
            @Override
            public View setItemView(final int position, View v, SmartListItem item, ViewHolder holder) {

                holder.frameLeftMain = (FrameLayout) v.findViewById(R.id.frameLeftMain);
                holder.lnrLeftBlockUser = (LinearLayout) v.findViewById(R.id.lnrLeftBlockUser);
                holder.lnrLeftUser = (LinearLayout) v.findViewById(R.id.lnrLeftUser);
                holder.lnrLeftClickable = (LinearLayout) v.findViewById(R.id.lnrLeftClickable);
                holder.pbrLeftImageLoad = (ProgressBar) v.findViewById(R.id.pbrLeftImageLoad);
                holder.imgLeftUserIcon = (RoundedImageView) v.findViewById(R.id.imgLeftUserIcon);
                holder.txtLeftUserName = (IjoomerTextView) v.findViewById(R.id.txtLeftUserName);
                holder.txtLeftUserStatus = (IjoomerTextView) v.findViewById(R.id.txtLeftUserStatus);

                holder.frameRightMain = (FrameLayout) v.findViewById(R.id.frameRightMain);
                holder.lnrRightBlockUser = (LinearLayout) v.findViewById(R.id.lnrRightBlockUser);
                holder.lnrRightUser = (LinearLayout) v.findViewById(R.id.lnrRightUser);
                holder.lnrRightClickable = (LinearLayout) v.findViewById(R.id.lnrRightClickable);
                holder.pbrRightImageLoad = (ProgressBar) v.findViewById(R.id.pbrRightImageLoad);
                holder.imgRightUserIcon = (RoundedImageView) v.findViewById(R.id.imgRightUserIcon);
                holder.txtRightUserName = (IjoomerTextView) v.findViewById(R.id.txtRightUserName);
                holder.txtRightUserStatus = (IjoomerTextView) v.findViewById(R.id.txtRightUserStatus);


                final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

                if(position%2==0){
                    holder.frameLeftMain.setVisibility(View.VISIBLE);
                    holder.frameRightMain.setVisibility(View.GONE);
                    androidQuery.id(holder.imgLeftUserIcon).progress(holder.pbrLeftImageLoad).image(row.get(USER_AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtLeftUserName.setText(row.get(USER_NAME));
                    holder.txtLeftUserStatus.setText(row.get(USER_STATUS));
                    if(selectedUserMap!=null && selectedUserMap.containsKey(row.get(USER_ID))){
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                    }else{
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    holder.frameRightMain.setVisibility(View.VISIBLE);
                    holder.frameLeftMain.setVisibility(View.GONE);
                    androidQuery.id(holder.imgRightUserIcon).progress(holder.pbrRightImageLoad).image(row.get(USER_AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtRightUserName.setText(row.get(USER_NAME));
                    holder.txtRightUserStatus.setText(row.get(USER_STATUS));
                    if(selectedUserMap!=null && selectedUserMap.containsKey(row.get(USER_ID))){
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                    }else{
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                    }
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
