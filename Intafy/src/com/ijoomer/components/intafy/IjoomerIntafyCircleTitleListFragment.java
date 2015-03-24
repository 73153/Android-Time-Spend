package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
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
import com.ijoomer.library.intafy.IntafyCircleDataProvider;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterIndexScrolling;
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
public class IjoomerIntafyCircleTitleListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private IjoomerIndexableListView lstDirectory;
    private LinearLayout listFooter;
    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    public ArrayList<HashMap<String,String>> circleData = new ArrayList<HashMap<String, String>>();
    private AQuery androidQuery;
    private SmartListAdapterIndexScrolling listAdapterWithHolder;
    private String[] sections;
    private HashMap<String, Integer> alphaIndexer;
    private ArrayList<String> sectionArray;
    private String TABLENAME ="CircleTitleWise";
    private IntafyCircleDataProvider circleDataProvider;
    public boolean isSelectGroupActive =false;
    public boolean isDeleteGroup =false;
    public boolean isJoinGroup =false;
    private SeekBar seekBar;
    public String selectedCircleId ="";
    public HashMap<String,String> selectedCircleMap;
    public String joiningUserId;
    private int getDataCount=0;
    public int lastCount;
    public IjoomerIntafyCircleTitleListFragment(String joiningUserId,boolean isDeleteGroup,boolean isSelectGroupActive,boolean isJoinGroup) {
        this.joiningUserId= joiningUserId;
        this.isJoinGroup =isJoinGroup;
        this.isSelectGroupActive =isSelectGroupActive;
        this.isDeleteGroup =isDeleteGroup;
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
        circleDataProvider = new IntafyCircleDataProvider(getActivity());
        lstDirectory.setFastScrollEnabled(true);
        lstDirectory.setDivider(null);
        lstDirectory.setDividerHeight(0);
        selectedCircleMap = new HashMap<String, String>();
    }

    @Override
    public void prepareViews(View currentView) {
        getCircleList(true);
    }

    @Override
    public void setActionListeners(View currentView) {

        lstDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                final ViewHolder holder = (ViewHolder) view.getTag();
                if (position % 2 == 0) {
                    if(isSelectGroupActive){
                        if (selectedCircleMap.containsKey(circleData.get(position).get(ID))) {
                            selectedCircleMap.remove(circleData.get(position).get(ID));
                            holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.blue));
                            holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                        } else {
                            holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.white));
                            holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                            selectedCircleMap.put(circleData.get(position).get(ID), circleData.get(position).get(TITLE));
                        }
                    }else{
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.blue));
                                holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.white));
                                holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                                try{
                                    ((SmartActivity)getActivity()).loadNew(IjoomerIntafyCircleMessageListActivity.class,getActivity(),false,"IN_CIRCLE_ID",circleData.get(position).get(ID),"IN_CIRCLE_NAME",circleData.get(position).get(TITLE),"IN_CIRCLE_ISEDIT",circleData.get(position).get(ISEDITDELETE),"IN_TABLENAME",TABLENAME);
                                }catch (Throwable e){
                                    e.printStackTrace();
                                }
                            }
                        },1000);


                    }
                } else {
                    if (isSelectGroupActive) {
                        if (selectedCircleMap.containsKey(circleData.get(position).get(ID))) {
                            selectedCircleMap.remove(circleData.get(position).get(ID));
                            holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                            holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                        } else {
                            holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                            holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                            selectedCircleMap.put(circleData.get(position).get(ID), circleData.get(position).get(TITLE));
                        }
                    } else {
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                                holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                                holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                                try{
                                    ((SmartActivity)getActivity()).loadNew(IjoomerIntafyCircleMessageListActivity.class,getActivity(),false,"IN_CIRCLE_ID",circleData.get(position).get(ID),"IN_CIRCLE_NAME",circleData.get(position).get(TITLE),"IN_CIRCLE_ISEDIT",circleData.get(position).get(ISEDITDELETE),"IN_TABLENAME",TABLENAME);
                                }catch (Throwable e){
                                    e.printStackTrace();
                                }
                            }
                        },1000);
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
                        if (!circleDataProvider.isCalling() && circleDataProvider.hasNextPage()) {
                            listFooterVisible();
                            String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                            String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                            getDataCount = 0;
                            circleDataProvider.getGroups("",TABLENAME, isDeleteGroup, isJoinGroup, joiningUserId, networkId, connectedUserId, new WebCallListenerWithCacheInfo() {

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

    public String getSelectedCircleId(){
        selectedCircleId = "";
        Iterator<String> itr = selectedCircleMap.keySet().iterator();
        while (itr.hasNext()) {
            selectedCircleId += itr.next()+",";
        }
        return selectedCircleId.length()>1 ? selectedCircleId.substring(0, selectedCircleId.length()-1) : "";
    }

    private void getCircleList(boolean isShowProgress){
        getDataCount=0;
        circleDataProvider.restorePagingSettings();
        if(isShowProgress){
            seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        }
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        circleDataProvider.getGroups("",TABLENAME,isDeleteGroup,isJoinGroup,joiningUserId,networkId, connectedUserId, new WebCallListenerWithCacheInfo() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit, boolean fromCache) {
                try {
                    if (responseCode == 200) {
                        if (data1 != null && data1.size()>0) {
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
                        lstDirectory.setAdapter(null);
                        responseErrorMessageHandler(responseCode);
                    }
                } catch (Throwable e) {
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
        selectedCircleMap = new HashMap<String, String>();
        getCircleList(false);
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
                listData.clear();
                circleData.clear();
                circleData.addAll(data);
            } else {
                if (!fromCache) {
                    circleData.addAll(data);
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
                if (!alphaIndexer.containsKey(hashMap.get(TITLE))) {
                    alphaIndexer.put(hashMap.get(TITLE), i);
                    sectionArray.add(hashMap.get(TITLE));
                    obj.add(hashMap.get(TITLE));
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
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_circle), getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

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
                    androidQuery.id(holder.imgLeftUserIcon).progress(holder.pbrLeftImageLoad).image(row.get(AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtLeftUserName.setText(row.get(TITLE));
                    if(row.get(MEMBERCOUNT).equals("0")){
                        holder.txtLeftUserStatus.setText(getString(R.string.intafy_circle_member_only_me));
                    }else {
                        holder.txtLeftUserStatus.setText(String.format(getString(R.string.intafy_circle_member), row.get(MEMBERCOUNT)));
                    }
                    if(selectedCircleMap!=null && selectedCircleMap.containsKey(row.get(ID))){
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                    }else{
                        holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                    }
                }else{
                    holder.frameRightMain.setVisibility(View.VISIBLE);
                    holder.frameLeftMain.setVisibility(View.GONE);
                    androidQuery.id(holder.imgRightUserIcon).progress(holder.pbrRightImageLoad).image(row.get(AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtRightUserName.setText(row.get(TITLE));
                    if(row.get(MEMBERCOUNT).equals("0")){
                        holder.txtRightUserStatus.setText(getString(R.string.intafy_circle_member_only_me));
                    }else {
                        holder.txtRightUserStatus.setText(String.format(getString(R.string.intafy_circle_member), row.get(MEMBERCOUNT)));
                    }
                    if(selectedCircleMap!=null && selectedCircleMap.containsKey(row.get(ID))){
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                    }else{
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
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
