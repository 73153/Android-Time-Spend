package com.mycadiz.components.sobipro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SeekBar;

import com.mycadiz.common.classes.IjoomerSuperMaster;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.common.classes.ViewHolder;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.library.sobipro.SobiproCategoriesDataProvider;
import com.mycadiz.src.R;
import com.mycadiz.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Fragment Contains All Method Related To
 * SobiproSectionCategoryGridFragment.
 *
 * @author tasol
 *
 */

public class SobiproSectionCategoryGridFragment extends SmartFragment implements SobiproTagHolder {

    private ListView lstCategory;
    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private String IN_FEATUREDFIRST;
    private String IN_PRIVIOUS_ACTIVITY;
    private ArrayList<HashMap<String, String>> sectionCategory;
    private String IN_SECTION_ID, IN_CAT_ID, IN_PAGELAYOUT;
    private SobiproCategoriesDataProvider categoriesDataProvider;
    private ArrayList<HashMap<String,String>> IN_SUB_CATEGORIES;

    public SobiproSectionCategoryGridFragment(String IN_SECTION_ID, String IN_CAT_ID, String IN_PAGELAYOUT, String IN_FEATUREDFIRST,ArrayList<HashMap<String,String>> IN_SUB_CATEGORIES,String IN_PRIVIOUS_ACTIVITY) {
        this.IN_SECTION_ID = IN_SECTION_ID;
        this.IN_CAT_ID = IN_CAT_ID;
        this.IN_PAGELAYOUT = IN_PAGELAYOUT;
        this.IN_FEATUREDFIRST = IN_FEATUREDFIRST;
        this.IN_SUB_CATEGORIES=IN_SUB_CATEGORIES;
        this.IN_PRIVIOUS_ACTIVITY=IN_PRIVIOUS_ACTIVITY;

    }

    /**
     * Overrides methods.
     */
    @Override
    public int setLayoutId() {
        return R.layout.sobipro_section_category_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        categoriesDataProvider = new SobiproCategoriesDataProvider(getActivity());
        lstCategory = (ListView) currentView.findViewById(R.id.lstCategory);
    }

    @Override
    public void prepareViews(View currentView) {
        if(IN_SUB_CATEGORIES.size()>0){
            sectionCategory = IN_SUB_CATEGORIES;
            prepareList(IN_SUB_CATEGORIES, false);
            listAdapterWithHolder = getListAdapter(listData);
            lstCategory.setAdapter(listAdapterWithHolder);
        }else{
            getSectionCategories();
        }
    }

    @Override
    public void setActionListeners(View currentView) {
        lstCategory.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                try {
                    int IN_POS = 0;
                    if (pos < SobiproMasterActivity.IMAGE_MAX_SIZE)
                        IN_POS = pos;
                    else
                        IN_POS = pos % ((SobiproMasterActivity) getActivity()).IMAGE_MAX_SIZE;

                    getSubCategories(IN_SECTION_ID,sectionCategory.get(pos).get("id"),IN_PAGELAYOUT,IN_FEATUREDFIRST,sectionCategory.get(pos).get("name").trim(),IN_POS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Class methods.
     */

    /**
     * This method is used to get section categories.
     */

    private void getSubCategories(final String sectionId,final String categoryId,final String pageLayout,final String featuredFirst,final String categoryName,final int pos){
        if(Boolean.parseBoolean(getString(R.string.local_database))){
            ArrayList<HashMap<String, String>> categories = categoriesDataProvider.getSectionCategoryFromDB(categoryId);
            try{
                if (categories != null && categories.size()>0) {
                    ((SmartActivity) getActivity()).loadNew(SobiproSectionCategoryActivity.class, getActivity(), false,"IN_OBJ",getActivity().getIntent().getStringExtra("IN_OBJ"),"IN_SECTION_ID",sectionId,"IN_CAT_ID",categoryId, "IN_PAGELAYOUT", pageLayout, "IN_FEATUREDFIRST", featuredFirst,"IN_SUB_CATEGORIES",categories,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                } else {
                    ((IjoomerSuperMaster) getActivity()).setScreenCaption(categoryName);
                    ((SmartActivity) getActivity()).loadNew(SobiproEntriesActivity.class, getActivity(), false,"IN_SECTION_ID",sectionId,"IN_PARENT_ID",categoryId, "IN_POS",
                            pos, "IN_PAGELAYOUT", pageLayout, "IN_FEATUREDFIRST", featuredFirst,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                }
            }catch (Throwable e){
                e.printStackTrace();
            }
        }else{
            categoriesDataProvider.restorePagingSettings();
            final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_sending_request));
            categoriesDataProvider.getSectionCategories(sectionId,categoryId, new WebCallListener() {

                @Override
                public void onProgressUpdate(int progressCount) {
                    proSeekBar.setProgress(progressCount);
                }

                @Override
                public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                    try {
                        if (responseCode == 200) {
                            if (data1 != null && data1.size()>0) {
                                ((SmartActivity) getActivity()).loadNew(SobiproSectionCategoryActivity.class, getActivity(), false,"IN_OBJ",getActivity().getIntent().getStringExtra("IN_OBJ"),"IN_SECTION_ID",sectionId,"IN_CAT_ID",categoryId, "IN_PAGELAYOUT", pageLayout, "IN_FEATUREDFIRST", featuredFirst,"IN_SUB_CATEGORIES",data1,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                            } else {
                                ((IjoomerSuperMaster) getActivity()).setScreenCaption(categoryName);
                                ((SmartActivity) getActivity()).loadNew(SobiproEntriesActivity.class, getActivity(), false,"IN_SECTION_ID",sectionId,"IN_PARENT_ID",categoryId, "IN_POS",
                                        pos, "IN_PAGELAYOUT", pageLayout, "IN_FEATUREDFIRST", featuredFirst,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                            }
                        } else {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_article),
                                    getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.ok),
                                    R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                                @Override
                                public void NeutralMethod() {

                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }
    private void getSectionCategories() {
        if(Boolean.parseBoolean(getString(R.string.local_database))){
            ArrayList<HashMap<String, String>> categories = categoriesDataProvider.getSectionCategoryFromDB(IN_CAT_ID.equals("0") ? IN_SECTION_ID:IN_CAT_ID);
            try{
                if (categories != null && categories.size()>0) {
                    sectionCategory = categories;
                    prepareList(categories, false);
                    listAdapterWithHolder = getListAdapter(listData);
                    lstCategory.setAdapter(listAdapterWithHolder);
                } else {
                    ((IjoomerSuperMaster) getActivity()).setScreenCaption("");
                    ((SmartActivity) getActivity()).loadNew(SobiproEntriesActivity.class, getActivity(), true,"IN_SECTION_ID",IN_SECTION_ID,"IN_PARENT_ID",IN_CAT_ID, "IN_POS",
                            0, "IN_PAGELAYOUT", IN_PAGELAYOUT, "IN_FEATUREDFIRST", IN_FEATUREDFIRST,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                }
            }catch (Throwable e){
                e.printStackTrace();
            }
        }else{
            categoriesDataProvider.restorePagingSettings();
            final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_sending_request));
            categoriesDataProvider.getSectionCategories(IN_SECTION_ID,IN_CAT_ID, new WebCallListener() {

                @Override
                public void onProgressUpdate(int progressCount) {
                    proSeekBar.setProgress(progressCount);
                }

                @Override
                public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                    try {
                        if (responseCode == 200) {
                            if (data1 != null && data1.size()>0) {
                                sectionCategory = data1;
                                prepareList(data1, false);
                                listAdapterWithHolder = getListAdapter(listData);
                                lstCategory.setAdapter(listAdapterWithHolder);
                            } else {
                                ((IjoomerSuperMaster) getActivity()).setScreenCaption("");
                                ((SmartActivity) getActivity()).loadNew(SobiproEntriesActivity.class, getActivity(), false,"IN_SECTION_ID",IN_SECTION_ID,"IN_PARENT_ID",IN_CAT_ID, "IN_POS",
                                        0, "IN_PAGELAYOUT", IN_PAGELAYOUT, "IN_FEATUREDFIRST", IN_FEATUREDFIRST,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                            }
                        } else {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_article),
                                    getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.ok),
                                    R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                                @Override
                                public void NeutralMethod() {

                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    /**
     * This method is used to prepare initial list from response data.
     *
     * @param data
     *            represented data from response.
     */

    public void prepareList(ArrayList<HashMap<String, String>> data, boolean append) {

        if (data != null) {

            if (!append) {
                listData.clear();
            }
            for (HashMap<String, String> hashMap : data) {

                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.sobipro_section_category_item);
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
     * List adapter for section category list.
     *
     * @param listData
     *            represented section category data
     * @return represented {@link SmartListAdapterWithHolder}
     */

    public SmartListAdapterWithHolder getListAdapter(ArrayList<SmartListItem> listData) {

        SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.sobipro_section_category_item, listData, new ItemView() {
            @Override
            public View setItemView(int position, View v, SmartListItem item, ViewHolder holder) {

                holder.sobiproTxtCategoriesCaption = (IjoomerTextView) v.findViewById(R.id.sobiproTxtCategoriesCaption);

                @SuppressWarnings("unchecked")
                HashMap<String, String> value = (HashMap<String, String>) item.getValues().get(0);
                if(getResources().getConfiguration().locale!=null && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en") && value.get(NAME).trim().length()>0){
                    holder.sobiproTxtCategoriesCaption.setText(value.get(NAME).trim());
                }else{
                    if(value.get(NAMESPANISH).trim().length()>0){
                        holder.sobiproTxtCategoriesCaption.setText(value.get(NAMESPANISH).trim());
                    }else{
                        holder.sobiproTxtCategoriesCaption.setText(value.get(NAME).trim());
                    }
                }
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
