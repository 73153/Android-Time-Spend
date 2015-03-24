package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyMessageDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
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
public class IjoomerIntafyMessageListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private ListView lstMessage;
    private LinearLayout listFooter;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private AQuery androidQuery;
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private IntafyMessageDataProvider messageDataProvider;
    private SeekBar seekBar;
    public boolean isDeleteActive;
    private String sortedBy ="date";
    private int getDataCount=0;
    public String selectedMessageId="";
    public HashMap<String,String> selectMessageMap;

    public IjoomerIntafyMessageListFragment() {
    }

    /**
     * Overrides method
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_article_list_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        listFooter = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.ijoomer_list_footer, null);
        lstMessage = (ListView) currentView.findViewById(R.id.lstArticle);
        lstMessage.addFooterView(listFooter, null, false);
        lstMessage.setAdapter(null);
        selectMessageMap = new HashMap<String, String>();
        androidQuery = new AQuery(getActivity());
        messageDataProvider = new IntafyMessageDataProvider(getActivity());

    }

    @Override
    public void prepareViews(View currentView) {

        getMessageList(true);
    }

    @Override
    public void setActionListeners(View currentView) {
        lstMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                final HashMap<String, String> row = (HashMap<String, String>) listData.get(position).getValues().get(0);
                final ViewHolder holder = (ViewHolder) view.getTag();
                if (position % 2 == 0) {
                    if(isDeleteActive){
                        if (selectMessageMap.containsKey(row.get(ID))) {
                            selectMessageMap.remove(row.get(ID));
                            holder.txtLeftMessageUserName.setTextColor(getResources().getColor(R.color.blue));
                            holder.txtLeftMessageDate.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                        } else {
                            holder.txtLeftMessageUserName.setTextColor(getResources().getColor(R.color.white));
                            holder.txtLeftMessageDate.setTextColor(getResources().getColor(R.color.txt_color));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                            selectMessageMap.put(row.get(ID), row.get(BODY));
                        }
                    }else{
                        holder.txtLeftMessageUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftMessageDate.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.txtLeftMessageUserName.setTextColor(getResources().getColor(R.color.blue));
                                holder.txtLeftMessageDate.setTextColor(getResources().getColor(R.color.white));
                                holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                                try {
                                    ((SmartActivity) getActivity()).loadNew(IjoomerIntafyMessageDetailListActivity.class, getActivity(), false, "IN_MESSAGE_ID", row.get(ID), "IN_USER_ID", row.get(USER_ID),"IN_USER_AVATAR",row.get(USER_AVATAR), "IN_USERNAME", row.get(USER_NAME));
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        },1000);
                    }
                } else {
                    if (isDeleteActive) {
                        if (selectMessageMap.containsKey(row.get(ID))) {
                            selectMessageMap.remove(row.get(ID));
                            holder.txtRightMessageUserName.setTextColor(getResources().getColor(R.color.blue));
                            holder.txtRightMessageDate.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                        } else {
                            holder.txtRightMessageUserName.setTextColor(getResources().getColor(R.color.white));
                            holder.txtRightMessageDate.setTextColor(getResources().getColor(R.color.txt_color));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                            selectMessageMap.put(row.get(ID), row.get(BODY));
                        }
                    } else {
                        holder.txtRightMessageUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightMessageDate.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.txtRightMessageUserName.setTextColor(getResources().getColor(R.color.blue));
                                holder.txtRightMessageDate.setTextColor(getResources().getColor(R.color.white));
                                holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                                try {
                                    ((SmartActivity) getActivity()).loadNew(IjoomerIntafyMessageDetailListActivity.class, getActivity(), false, "IN_MESSAGE_ID", row.get(ID), "IN_USER_ID", row.get(USER_ID),"IN_USER_AVATAR",row.get(USER_AVATAR), "IN_USERNAME", row.get(USER_NAME));
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        },1000);
                    }
                }
            }
        });
        lstMessage.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(getDataCount>1) {
                    if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
                        if (!messageDataProvider.isCalling() && messageDataProvider.hasNextPage()) {
                            listFooterVisible();
                            String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                            String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                            getDataCount = 0;
                            messageDataProvider.getMessageList(sortedBy.equals(DATE)?"MessageDateWise":"MessageUserWise", networkId, connectedUserId, new WebCallListenerWithCacheInfo() {

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

    /**
     * Class methods
     */

    /**
     * This method used to update fragment.
     */
    public void update(String type,boolean isProgressShown) {
        this.sortedBy = type;
        selectMessageMap = new HashMap<String, String>();
        getMessageList(isProgressShown);
    }

    public String getSelectedMessageId(){
        selectedMessageId = "";
        Iterator<String> itr = selectMessageMap.keySet().iterator();
        while (itr.hasNext()) {
            selectedMessageId += itr.next()+",";
        }
        return selectedMessageId.length()>1 ? selectedMessageId.substring(0, selectedMessageId.length()-1) : "";
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


    /**
     * This method used to get all photos.
     * @param isShowProgress represented progress shown
     */
    private void getMessageList(boolean isShowProgress){
        getDataCount=0;
        messageDataProvider.restorePagingSettings();
        if(isShowProgress){
            seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        }
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        messageDataProvider.getMessageList(sortedBy.equals(DATE)?"MessageDateWise":"MessageUserWise", networkId, connectedUserId, new WebCallListenerWithCacheInfo() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit, boolean fromCache) {
                try{
                if (responseCode == 200) {
                    if (data1 != null) {
                        prepareList(data1, false, fromCache, pageNo, pageLimit);
                        listAdapterWithHolder = getListAdapter();
                        lstMessage.setAdapter(listAdapterWithHolder);
                        if(fromCache){
                            getDataCount++;
                        }else{
                            getDataCount+=2;
                        }
                    }
                } else {
                    lstMessage.setAdapter(null);
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


    public void prepareList(ArrayList<HashMap<String, String>> data, boolean append, boolean fromCache, int pageno, int pagelimit) {
        if (data != null) {
            if (!append) {
                listData.clear();
            } else {
                if (!fromCache) {
                    int startIndex = ((pageno - 1) * pagelimit);
                    int endIndex = listAdapterWithHolder.getCount();
                    if (startIndex > 0) {
                        for (int i = endIndex; i >= startIndex; i--) {
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
                item.setItemLayout(R.layout.ijoomer_intafy_message_list_item);
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
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_messages), getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    /**
     * List adapter for all album.
     */
    private SmartListAdapterWithHolder getListAdapter() {

        SmartListAdapterWithHolder adapterNetwork = new SmartListAdapterWithHolder(getActivity(), R.layout.ijoomer_intafy_message_list_item, listData, new ItemView() {

            @SuppressWarnings("unchecked")
            @Override
            public View setItemView(int position, View v, SmartListItem item, ViewHolder holder) {

                holder.lnrLeftUser = (LinearLayout) v.findViewById(R.id.lnrLeftUser);
                holder.lnrLeftClickable = (LinearLayout) v.findViewById(R.id.lnrLeftClickable);
                holder.pbrLeftImageLoad = (ProgressBar) v.findViewById(R.id.pbrLeftImageLoad);
                holder.imgLeftUserIcon = (RoundedImageView) v.findViewById(R.id.imgLeftUserIcon);
                holder.txtLeftMessageUserName = (IjoomerTextView) v.findViewById(R.id.txtLeftMessageUserName);
                holder.txtLeftMessageSubject = (IjoomerTextView) v.findViewById(R.id.txtLeftMessageSubject);
                holder.txtLeftMessageDate = (IjoomerTextView) v.findViewById(R.id.txtLeftMessageDate);

                holder.lnrRightUser = (LinearLayout) v.findViewById(R.id.lnrRightUser);
                holder.lnrRightClickable = (LinearLayout) v.findViewById(R.id.lnrRightClickable);
                holder.pbrRightImageLoad = (ProgressBar) v.findViewById(R.id.pbrRightImageLoad);
                holder.imgRightUserIcon = (RoundedImageView) v.findViewById(R.id.imgRightUserIcon);
                holder.txtRightMessageUserName = (IjoomerTextView) v.findViewById(R.id.txtRightMessageUserName);
                holder.txtRightMessageSubject = (IjoomerTextView) v.findViewById(R.id.txtRightMessageSubject);
                holder.txtRightMessageDate = (IjoomerTextView) v.findViewById(R.id.txtRightMessageDate);

                final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

                if(position%2==0){
                    holder.lnrLeftUser.setVisibility(View.VISIBLE);
                    holder.lnrRightUser.setVisibility(View.GONE);
                    androidQuery.id(holder.imgLeftUserIcon).progress(holder.pbrLeftImageLoad).image(row.get(USER_AVATAR),true,true,80,R.drawable.ic_launcher);
                    holder.txtLeftMessageUserName.setText(row.get(USER_NAME)+" "+row.get(USER_LASTNAME));
                    holder.txtLeftMessageDate.setText(IjoomerUtilities.convertDateStringFormat(row.get(DATE), "yyyy-MM-dd kk:mm", IjoomerApplicationConfiguration.dateTimeFormat));
                    if(selectMessageMap!=null && selectMessageMap.containsKey(row.get(ID))){
                        holder.txtLeftMessageUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtLeftMessageDate.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                    }else{
                        holder.txtLeftMessageUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtLeftMessageDate.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                    }
                }else{
                    holder.lnrRightUser.setVisibility(View.VISIBLE);
                    holder.lnrLeftUser.setVisibility(View.GONE);
                    androidQuery.id(holder.imgRightUserIcon).progress(holder.pbrRightImageLoad).image(row.get(USER_AVATAR),true,true,80,R.drawable.ic_launcher);
                    holder.txtRightMessageUserName.setText(row.get(USER_NAME)+" "+row.get(USER_LASTNAME));
                    holder.txtRightMessageDate.setText(IjoomerUtilities.convertDateStringFormat(row.get(DATE), "yyyy-MM-dd kk:mm", IjoomerApplicationConfiguration.dateTimeFormat));
                    if(selectMessageMap!=null && selectMessageMap.containsKey(row.get(ID))){
                        holder.txtRightMessageUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightMessageDate.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                    }else{
                        holder.txtRightMessageUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightMessageDate.setTextColor(getResources().getColor(R.color.white));
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
