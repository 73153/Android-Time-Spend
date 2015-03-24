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
import com.ijoomer.library.intafy.IntafyCircleDataProvider;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterIndexScrolling;
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
public class IjoomerIntafyInviteCircleDateListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private IjoomerIndexableListView lstDirectory;
    private LinearLayout listFooter;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    public ArrayList<HashMap<String,String>> groupData = new ArrayList<HashMap<String, String>>();
    private AQuery androidQuery;
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private String TABLENAME ="CircleDateWise";
    private IntafyCircleDataProvider circleDataProvider;
    public HashMap<String,String> selectedGroupMap;
    public HashMap<String,String> newSelectedGroupMap;
    private SeekBar seekBar;
    private int getDataCount=0;
    private String IN_EVENT_ID;
    public int lastCount;
    public IjoomerIntafyInviteCircleDateListFragment(HashMap<String, String> selectedGroupMap,HashMap<String, String> newSelectedGroupMap,String IN_EVENT_ID) {
       this.selectedGroupMap = selectedGroupMap;
        this.newSelectedGroupMap = newSelectedGroupMap;
        this.IN_EVENT_ID=IN_EVENT_ID;
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
        androidQuery = new AQuery(getActivity());
        circleDataProvider = new IntafyCircleDataProvider(getActivity());
        lstDirectory.setFastScrollEnabled(true);
        lstDirectory.setDivider(null);
        lstDirectory.setDividerHeight(0);
        if(selectedGroupMap==null) {
            selectedGroupMap = new HashMap<String, String>();
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
                    if (selectedGroupMap.containsKey(groupData.get(position).get(ID))) {
                        selectedGroupMap.remove(groupData.get(position).get(ID));
                        holder.txtLeftArticleTitle.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtLeftArticleDate.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                    } else {
                        holder.txtLeftArticleTitle.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftArticleDate.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                        selectedGroupMap.put(groupData.get(position).get(ID), groupData.get(position).get(TITLE));
                    }
                } else {
                    if (selectedGroupMap.containsKey(groupData.get(position).get(ID))) {
                        selectedGroupMap.remove(groupData.get(position).get(ID));
                        holder.txtRightArticleTitle.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightArticleDate.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                    } else {
                        holder.txtRightArticleTitle.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightArticleDate.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                        selectedGroupMap.put(groupData.get(position).get(ID), groupData.get(position).get(TITLE));
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
                            String inviteCircle="";
                            if(!IN_EVENT_ID.equals("0")) {
                                Iterator<String> itr = selectedGroupMap.keySet().iterator();
                                while (itr.hasNext()) {
                                    String key = itr.next();
                                    inviteCircle += key + ",";
                                }
                                if(inviteCircle.length()>0){
                                    inviteCircle = inviteCircle.substring(0,inviteCircle.length()-1);
                                }
                            }
                            circleDataProvider.getGroups(inviteCircle,TABLENAME, false, false, "", networkId, connectedUserId, new WebCallListenerWithCacheInfo() {

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
        return selectedGroupMap;
    }

    private void getMemberList(boolean isShowProgress){
        getDataCount=0;
        circleDataProvider.restorePagingSettings();
        if(isShowProgress){
            seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        }
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        String inviteCircle="";
        if(!IN_EVENT_ID.equals("0")) {
            Iterator<String> itr = selectedGroupMap.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                inviteCircle += key + ",";
            }
            if(inviteCircle.length()>0){
                inviteCircle = inviteCircle.substring(0,inviteCircle.length()-1);
            }
        }
        circleDataProvider.getGroups(inviteCircle,TABLENAME,false,false,"", networkId,connectedUserId, new WebCallListenerWithCacheInfo() {
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
                        }else{
                            responseErrorMessageHandler(204);
                        }
                    } else {
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
        if (data != null) {
            if (!append) {
                listData.clear();
                groupData.clear();
                groupData.addAll(data);
            } else {
                if (!fromCache) {
                    groupData.addAll(data);
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
                item.setItemLayout(R.layout.ijoomer_intafy_articel_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);
                item.setValues(obj);
                if (append) {
                    listAdapterWithHolder.add(item);
                } else {
                    listData.add(item);
                }
            }
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


    /**
     * List adapter for all album.
     */
    private SmartListAdapterWithHolder getListAdapter() {

        SmartListAdapterWithHolder adapterNetwork = new SmartListAdapterWithHolder(getActivity(), R.layout.ijoomer_intafy_articel_list_item, listData, new ItemView() {

            @SuppressWarnings("unchecked")
            @Override
            public View setItemView(final int position, View v, SmartListItem item, ViewHolder holder) {

                holder.lnrLeftArticle = (LinearLayout) v.findViewById(R.id.lnrLeftArticle);
                holder.lnrLeftClickable = (LinearLayout) v.findViewById(R.id.lnrLeftClickable);
                holder.pbrLeftImageLoad = (ProgressBar) v.findViewById(R.id.pbrLeftImageLoad);
                holder.imgLeftArticleIcon = (RoundedImageView) v.findViewById(R.id.imgLeftArticleIcon);
                holder.txtLeftArticleTitle = (IjoomerTextView) v.findViewById(R.id.txtLeftArticleTitle);
                holder.txtLeftArticleDate = (IjoomerTextView) v.findViewById(R.id.txtLeftArticleDate);

                holder.lnrRightArticle = (LinearLayout) v.findViewById(R.id.lnrRightArticle);
                holder.lnrRightClickable = (LinearLayout) v.findViewById(R.id.lnrRightClickable);
                holder.pbrRightImageLoad = (ProgressBar) v.findViewById(R.id.pbrRightImageLoad);
                holder.imgRightArticleIcon = (RoundedImageView) v.findViewById(R.id.imgRightArticleIcon);
                holder.txtRightArticleTitle = (IjoomerTextView) v.findViewById(R.id.txtRightArticleTitle);
                holder.txtRightArticleDate = (IjoomerTextView) v.findViewById(R.id.txtRightArticleDate);


                final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

                if(position%2==0){
                    holder.lnrLeftArticle.setVisibility(View.VISIBLE);
                    holder.lnrRightArticle.setVisibility(View.GONE);
                    androidQuery.id(holder.imgLeftArticleIcon).progress(holder.pbrLeftImageLoad).image(row.get(AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtLeftArticleTitle.setText(row.get(TITLE));
                    holder.txtLeftArticleDate.setText(String.format(getString(R.string.intafy_circle_member),row.get(MEMBERCOUNT)));
                    if(newSelectedGroupMap !=null && newSelectedGroupMap.containsKey(row.get(ID))){
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                        holder.txtLeftArticleTitle.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftArticleDate.setTextColor(getResources().getColor(R.color.txt_color));
                    }else{
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                        holder.txtLeftArticleTitle.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtLeftArticleDate.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    holder.lnrRightArticle.setVisibility(View.VISIBLE);
                    holder.lnrLeftArticle.setVisibility(View.GONE);
                    androidQuery.id(holder.imgRightArticleIcon).progress(holder.pbrRightImageLoad).image(row.get(AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtRightArticleTitle.setText(row.get(TITLE));
                    holder.txtRightArticleDate.setText(String.format(getString(R.string.intafy_circle_member),row.get(MEMBERCOUNT)));
                    if(newSelectedGroupMap !=null && newSelectedGroupMap.containsKey(row.get(ID))){
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                        holder.txtRightArticleTitle.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightArticleDate.setTextColor(getResources().getColor(R.color.txt_color));
                    }else{
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                        holder.txtRightArticleTitle.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightArticleDate.setTextColor(getResources().getColor(R.color.white));
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
