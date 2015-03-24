package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerIndexableListView;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyDirectoriesDataProvider;
import com.ijoomer.weservice.WebCallListener;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Fragment Contains All Method Related To JomAlbumAllFragment.
 *
 * @author tasol
 *
 */
@SuppressLint("ValidFragment")
public class IjoomerIntafyDirectorySearchListFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {

    private IjoomerIndexableListView lstDirectory;
    private LinearLayout listFooter;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    public ArrayList<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();
    private AQuery androidQuery;
    private SmartListAdapterIndexScrolling listAdapterWithHolder;
    private String[] sections;
    private HashMap<String, Integer> alphaIndexer;
    private ArrayList<String> sectionArray;

    private IntafyDirectoriesDataProvider intafyDirectoriesDataProvider;
    private boolean isBlockActive;
    public String blockUserId="";
    public HashMap<String,String> blockUserMap;
    private String searchKeyWord;
    private int getDataCount=0;
    public IjoomerIntafyDirectorySearchListFragment(String searchKeyWord) {
        this.searchKeyWord=searchKeyWord;
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
        lstDirectory.setFastScrollEnabled(true);
        lstDirectory.setDivider(null);
        lstDirectory.setDividerHeight(0);
        blockUserMap = new HashMap<String, String>();
    }

    @Override
    public void prepareViews(View currentView) {
        getSearchMemberList();
    }

    @Override
    public void setActionListeners(View currentView) {

        lstDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                final ViewHolder holder = (ViewHolder) view.getTag();
                if (position % 2 == 0) {
                    if(isBlockActive){
                        if(searchData.get(position).get(USER_BLOCK).equals("1")) {
                            getBlcokUserDialog(searchData.get(position).get(USER_NAME), searchData.get(position).get(USER_LASTNAME), searchData.get(position).get(USER_ID));
                        }else{
                            if (blockUserMap.containsKey(searchData.get(position).get(USER_ID))) {
                                blockUserMap.remove(searchData.get(position).get(USER_ID));
                                holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.blue));
                                holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.white));
                                holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left);
                            } else {
                                holder.txtLeftUserName.setTextColor(getResources().getColor(R.color.white));
                                holder.txtLeftUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                                holder.lnrLeftClickable.setBackgroundResource(R.drawable.list_item_box_left_selected);
                                blockUserMap.put(searchData.get(position).get(USER_ID), searchData.get(position).get(USER_NAME));
                            }
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
                                if (searchData.get(position).get(USER_ID).equals(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0"))) {
                                    ((SmartActivity) getActivity()).loadNew(IjoomerIntafyProfileActivity.class, getActivity(), false);
                                } else {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(IjoomerIntafyProfileOtherActivity.class, getActivity(), false, "IN_USERID", searchData.get(position).get(USER_ID));
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },1000);

                    }
                } else {
                    if (isBlockActive) {
                        if (searchData.get(position).get(USER_BLOCK).equals("1")) {
                            getBlcokUserDialog(searchData.get(position).get(USER_NAME), searchData.get(position).get(USER_LASTNAME), searchData.get(position).get(USER_ID));
                        } else {
                            if (blockUserMap.containsKey(searchData.get(position).get(USER_ID))) {
                                blockUserMap.remove(searchData.get(position).get(USER_ID));
                                holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                                holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                                holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                            } else {
                                holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                                holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                                holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                                blockUserMap.put(searchData.get(position).get(USER_ID), searchData.get(position).get(USER_NAME));
                            }
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
                                if (searchData.get(position).get(USER_ID).equals(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0"))) {
                                    ((SmartActivity) getActivity()).loadNew(IjoomerIntafyProfileActivity.class, getActivity(), false);
                                } else {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(IjoomerIntafyProfileOtherActivity.class, getActivity(), false, "IN_USERID", searchData.get(position).get(USER_ID));
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },1000);
                    }
                }
            }
        });
    }

    public void enableBlock(){
        isBlockActive =true;
        blockUserMap.clear();
        if(listData!=null && listData.size()>0 && listAdapterWithHolder!=null ){
            listAdapterWithHolder.notifyDataSetChanged();
        }
    }

    public void disableBlock(){
        isBlockActive =false;
        if(listData!=null && listData.size()>0 && listAdapterWithHolder!=null ){
            listAdapterWithHolder.notifyDataSetChanged();
        }
    }

    public String getBlockUserId(){
        blockUserId = "";
        Iterator<String> itr = blockUserMap.keySet().iterator();
        while (itr.hasNext()) {
            blockUserId += itr.next()+",";
        }
        return blockUserId.length()>1 ? blockUserId.substring(0, blockUserId.length()-1) : "";
    }

    private void getSearchMemberList(){
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        searchData = intafyDirectoriesDataProvider.searchMember(searchKeyWord,networkId,connectedUserId);
        if(searchData!=null && searchData.size()>0){
            prepareList(searchData);
            listAdapterWithHolder = getListAdapter();
            lstDirectory.setAdapter(listAdapterWithHolder);
        }else{
            responseErrorMessageHandler(204);
        }
    }

    /**
     * Class methods
     */

    /**
     * This method used to update fragment.
     */
    public void update() {
        blockUserMap = new HashMap<String, String>();
        getSearchMemberList();
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

    public void prepareList(ArrayList<HashMap<String, String>> data) {
        sectionArray.clear();
        if (data != null) {
            alphaIndexer = new HashMap<String, Integer>();
            sectionArray = new ArrayList<String>();
            listData.clear();

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
                listData.add(item);
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
                    if(isBlockActive && blockUserMap!=null && blockUserMap.containsKey(row.get(USER_ID))){
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
                    androidQuery.id(holder.imgRightUserIcon).progress(holder.pbrRightImageLoad).image(row.get(USER_AVATAR),true,true,70,R.drawable.ic_launcher);
                    holder.txtRightUserName.setText(row.get(USER_NAME));
                    holder.txtRightUserStatus.setText(row.get(USER_STATUS));
                    if(isBlockActive && blockUserMap!=null && blockUserMap.containsKey(row.get(USER_ID))){
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.white));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.txt_color));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right_selected);
                    }else{
                        holder.txtRightUserName.setTextColor(getResources().getColor(R.color.blue));
                        holder.txtRightUserStatus.setTextColor(getResources().getColor(R.color.white));
                        holder.lnrRightClickable.setBackgroundResource(R.drawable.list_item_box_right);
                    }
                }
                if(isBlockActive){
                    if(position%2==0){
                        if (row.get(USER_BLOCK).equals("1")) {
                            holder.lnrLeftBlockUser.setVisibility(View.VISIBLE);
                        }else{
                            holder.lnrLeftBlockUser.setVisibility(View.GONE);
                        }
                    }else{
                        if (row.get(USER_BLOCK).equals("1")) {
                            holder.lnrRightBlockUser.setVisibility(View.VISIBLE);
                        }else{
                            holder.lnrRightBlockUser.setVisibility(View.GONE);
                        }
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

    private void getBlcokUserDialog(String firstName,String lastName,final String userId) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.ijoomer_intafy_block_user_dialog);

        IjoomerTextView txtFirstname = (IjoomerTextView) dialog.findViewById(R.id.txtFirstname);
        IjoomerTextView txtSurname = (IjoomerTextView) dialog.findViewById(R.id.txtSurname);
        txtFirstname.setText(firstName);
        txtSurname.setText(lastName);
        final ImageView imgUnblock = (ImageView) dialog.findViewById(R.id.imgUnblock);
        final ImageView imgDelete = (ImageView) dialog.findViewById(R.id.imgDelete);

        imgUnblock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgUnblock.setImageResource(((IjoomerSuperMaster) getActivity()).getIcon(((IjoomerSuperMaster) getActivity()).UNBLOCKICON, true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgUnblock.setImageResource(((IjoomerSuperMaster) getActivity()).getIcon(((IjoomerSuperMaster) getActivity()).UNBLOCKICON, false));
                        dialog.dismiss();
                        final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                        intafyDirectoriesDataProvider.unBlockUser(userId, new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                if (responseCode == 200) {
                                    String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                                    String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                                    intafyDirectoriesDataProvider.updateUnblockUser(" " + USER_ID + "='" + userId + "'", networkId, connectedUserId);
                                    getSearchMemberList();
                                } else {
                                    responseErrorMessageHandler(responseCode);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {
                                seekBar.setProgress(progressCount);
                            }
                        });
                    }
                }, 1000);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgDelete.setImageResource(((IjoomerSuperMaster) getActivity()).getIcon(((IjoomerSuperMaster) getActivity()).DELETEICON, true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgDelete.setImageResource(((IjoomerSuperMaster)getActivity()).getIcon(((IjoomerSuperMaster)getActivity()).DELETEICON,false));
                        dialog.dismiss();
                        final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                        intafyDirectoriesDataProvider.deleteUser(userId, new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                if (responseCode == 200) {
                                    String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                                    String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                                    intafyDirectoriesDataProvider.updateUnblockUser(" " + USER_ID + "='" + userId + "'", networkId, connectedUserId);
                                    getSearchMemberList();
                                } else {
                                    responseErrorMessageHandler(responseCode);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {
                                seekBar.setProgress(progressCount);
                            }
                        });
                    }
                }, 1000);
            }
        });
        dialog.show();
    }

}
