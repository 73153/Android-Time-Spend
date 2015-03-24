package com.mycadiz.components.sobipro;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mycadiz.common.classes.ViewHolder;
import com.mycadiz.customviews.IjoomerListView;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.library.sobipro.SobiproAdvanceSearchFieldsDataProvider;
import com.mycadiz.src.R;
import com.smart.framework.ItemView;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This Fragment Contains All Method Related To SobiproEntriesListFragment.
 *
 * @author tasol
 *
 */
public class SobiproSearchCategoryAndTownListFragment extends SmartFragment implements SobiproTagHolder {
    private String IN_SECTION_ID, IN_TYPE;
    private IjoomerListView lstEntries;
    private IjoomerTextView txtTitle;
    private ImageView imgClose;
    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private SobiproAdvanceSearchFieldsDataProvider dataProvider;
    private JSONArray data;


    public SobiproSearchCategoryAndTownListFragment(String IN_SECTION_ID, String IN_TYPE) {
        this.IN_SECTION_ID = IN_SECTION_ID;
        this.IN_TYPE = IN_TYPE;
    }

    /**
     * Overrides methods.
     */
    @Override
    public int setLayoutId() {
        return R.layout.sobipro_search_category_and_town_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        dataProvider = new SobiproAdvanceSearchFieldsDataProvider(getActivity());
        lstEntries = (IjoomerListView) currentView.findViewById(R.id.lstEntries);
        txtTitle = (IjoomerTextView) currentView.findViewById(R.id.txtTitle);
        imgClose = (ImageView) currentView.findViewById(R.id.imgClose);
    }


    @Override
    public void prepareViews(View currentView) {
        try{
            if(IN_TYPE.equals("category")){
                data=dataProvider.getTreeViseCategory(IN_SECTION_ID,0);
                txtTitle.setText(getString(R.string.sobipro_select_category));
            }else{
                data=dataProvider.getTown();
                txtTitle.setText(getString(R.string.sobipro_select_town));
            }
            prepareList(data);
            listAdapterWithHolder = getListAdapter(listData);
            lstEntries.setAdapter(listAdapterWithHolder);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }



    @Override
    public void setActionListeners(View currentView) {
        imgClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });
    }

    /**
     * Class Methods.
     */



    public void prepareList(JSONArray data) {

        if (data != null) {
            listData.clear();
            for(int i=0;i<data.length();i++){
                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.sobipro_search_category_town_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                try{
                    obj.add(data.getJSONObject(i));
                    item.setValues(obj);
                    listData.add(item);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * List adapter for entry list.
     *
     * @param listData
     *            represented entry data
     * @return represented {@link com.smart.framework.SmartListAdapterWithHolder}
     */

    public SmartListAdapterWithHolder getListAdapter(ArrayList<SmartListItem> listData) {

        SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.sobipro_search_category_town_list_item, listData, new ItemView() {
            @Override
            public View setItemView(final int position, View v, SmartListItem item, ViewHolder holder) {

                holder.lnrMain = (LinearLayout) v.findViewById(R.id.lnrMain);
                holder.txtTitle = (IjoomerTextView) v.findViewById(R.id.txtTitle);
                @SuppressWarnings("unchecked")
                final JSONObject value = (JSONObject) item.getValues().get(0);
                try{
                    if (getActivity().getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es") && value.has("name_spanish") && value.get("name_spanish").toString().trim().length()>0) {
                        holder.txtTitle.setText(value.getString("name_spanish"));
                    }else{
                        holder.txtTitle.setText(value.getString("name"));
                    }
                    if(IN_TYPE.equals("category")) {
                        if (holder.txtTitle.getText().toString().contains(">")) {
                            holder.lnrMain.setBackgroundResource(R.drawable.sobipro_search_title_bg);
                        } else {
                            holder.lnrMain.setBackgroundResource(R.drawable.sobipro_search_title_bg_selected);
                        }
                    }else{
                        holder.lnrMain.setBackgroundResource(R.drawable.sobipro_search_title_bg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                holder.lnrMain.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (!value.has("hasChild") || value.getString("hasChild").equals("0")) {
                                Intent intent = new Intent();
                                try {
                                    if (getActivity().getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es") && value.has("name_spanish") && value.get("name_spanish").toString().trim().length() > 0) {
                                        intent.putExtra("title", value.getString("name_spanish").trim());
                                        intent.putExtra("value", value.getString("value").trim());
                                    } else {
                                        intent.putExtra("title", value.getString("name").trim());
                                        intent.putExtra("value", value.getString("value").trim());
                                    }
                                } catch (Throwable e) {
                                    intent.putExtra("title", "");
                                    intent.putExtra("value", "");
                                    e.printStackTrace();
                                }
                                getActivity().setResult(Activity.RESULT_OK, intent);
                                getActivity().finish();
                            }
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                });
                return v;
            }

            @Override
            public View setItemView(int position, View v, SmartListItem item) {
                return null;
            }
        });
        return adapterWithHolder;
    }

}
