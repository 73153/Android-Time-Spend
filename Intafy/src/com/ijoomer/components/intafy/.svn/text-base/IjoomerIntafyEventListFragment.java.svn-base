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
import com.ijoomer.library.intafy.IntafyEventDataProvider;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
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
public class IjoomerIntafyEventListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private ListView lstArticle;
    private LinearLayout listFooter;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private AQuery androidQuery;
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private IntafyEventDataProvider eventDataProvider;
    private SeekBar seekBar;
    private String type ="my";
    private int getDataCount=0;
    private int listPointer;
    public IjoomerIntafyEventListFragment() {
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
        lstArticle = (ListView) currentView.findViewById(R.id.lstArticle);
        lstArticle.addFooterView(listFooter, null, false);
        lstArticle.setAdapter(null);
        androidQuery = new AQuery(getActivity());
        eventDataProvider = new IntafyEventDataProvider(getActivity());

    }

    @Override
    public void prepareViews(View currentView) {
        getArticlesList(true);
    }

    @Override
    public void setActionListeners(View currentView) {
        lstArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> row = (HashMap<String, String>) listData.get(position).getValues().get(0);
                final ViewHolder holder = (ViewHolder) view.getTag();
                if(position%2==0){
                    holder.txtLeftArticleTitle.setTextColor(getResources().getColor(R.color.white));
                    holder.txtLeftArticleDate.setTextColor(getResources().getColor(R.color.txt_color));
                    holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.txtLeftArticleTitle.setTextColor(getResources().getColor(R.color.blue));
                            holder.txtLeftArticleDate.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                            try{
                                ((SmartActivity)getActivity()).loadNew(IjoomerIntafyEventDetailsActivity.class,getActivity(),false,"IN_EVENT_ID",row.get(ID));
                            }catch (Throwable e){
                                e.printStackTrace();
                            }
                        }
                    },1000);
                }else{
                    holder.txtRightArticleTitle.setTextColor(getResources().getColor(R.color.white));
                    holder.txtRightArticleDate.setTextColor(getResources().getColor(R.color.txt_color));
                    holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.txtRightArticleTitle.setTextColor(getResources().getColor(R.color.blue));
                            holder.txtRightArticleDate.setTextColor(getResources().getColor(R.color.white));
                            holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                            try{
                                ((SmartActivity)getActivity()).loadNew(IjoomerIntafyEventDetailsActivity.class,getActivity(),false,"IN_EVENT_ID",row.get(ID));
                            }catch (Throwable e){
                                e.printStackTrace();
                            }
                        }
                    },1000);
                }
            }
        });
        lstArticle.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(getDataCount>1){
                    if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
                        if (!eventDataProvider.isCalling() && eventDataProvider.hasNextPage()) {
                            listFooterVisible();
                            String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                            String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                            getDataCount = 0;
                            eventDataProvider.getEvent(type, networkId, connectedUserId, new WebCallListenerWithCacheInfo() {

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
    public void update(String type,boolean showProgress) {
        this.type = type;
        getArticlesList(showProgress);
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
    private void getArticlesList(boolean isShowProgress){
        getDataCount=0;
        eventDataProvider.restorePagingSettings();
        if(isShowProgress){
            seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        }
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        eventDataProvider.getEvent(type, networkId, connectedUserId, new WebCallListenerWithCacheInfo() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit, boolean fromCache) {
                try{
                    if (responseCode == 200) {
                        if (data1 != null) {
                            prepareList(data1, false, fromCache, pageNo, pageLimit);
                            listAdapterWithHolder = getListAdapter();
                            lstArticle.setAdapter(listAdapterWithHolder);
                            lstArticle.post(new Runnable() {
                                @Override
                                public void run() {
                                    lstArticle.setSelection(listPointer);
                                }
                            });

                            if(fromCache){
                                getDataCount++;
                            }else{
                                getDataCount+=2;
                            }
                        }
                    } else {
                        lstArticle.setAdapter(null);
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
                listPointer = 0;
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
                item.setItemLayout(R.layout.ijoomer_intafy_articel_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);
                item.setValues(obj);
                if (append) {
                    listAdapterWithHolder.add(item);
                } else {
                    if(!fromCache && hashMap.get("past").equals("1")){
                        listPointer++;
                    }
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
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_event), getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

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
            public View setItemView(int position, View v, SmartListItem item, ViewHolder holder) {

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
                    holder.txtLeftArticleDate.setText(IjoomerUtilities.convertDateStringFormat(row.get(DATE), "yyyy-MM-dd", IjoomerApplicationConfiguration.dateFormat)+" "+row.get(STARTTIME)+" "+getString(R.string.intafy_to)+" "+row.get(ENDTIME));
                }else{
                    holder.lnrRightArticle.setVisibility(View.VISIBLE);
                    holder.lnrLeftArticle.setVisibility(View.GONE);
                    androidQuery.id(holder.imgRightArticleIcon).progress(holder.pbrRightImageLoad).image(row.get(AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtRightArticleTitle.setText(row.get(TITLE));
                    holder.txtRightArticleDate.setText(IjoomerUtilities.convertDateStringFormat(row.get(DATE), "yyyy-MM-dd", IjoomerApplicationConfiguration.dateFormat)+" "+row.get(STARTTIME)+" "+getString(R.string.intafy_to)+" "+row.get(ENDTIME));
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
