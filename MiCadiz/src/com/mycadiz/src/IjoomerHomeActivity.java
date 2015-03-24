package com.mycadiz.src;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mycadiz.common.classes.IjoomerHomeMaster;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.common.classes.ViewHolder;
import com.mycadiz.common.configuration.IjoomerGlobalConfiguration;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerHomeActivity extends IjoomerHomeMaster {

    private LinearLayout lnrHomeMain;
    private ArrayList<HashMap<String,String>> menuData;
    private JSONArray menuItems;
    private ArrayList<SmartListItem> dataHomeMenu;
    private SmartListAdapterWithHolder gridAdapter;

    private final String ICON = "icon";
    private final String ITEMVIEW = "itemview";
    private final String ITEMDATA = "itemdata";
    private final String ITEMCAPTION = "itemcaption";

    IjoomerGlobalConfiguration globalConfiguration;

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_home_menu;
    }

    @Override
    public void initComponents() {
        lnrHomeMain = (LinearLayout) findViewById(R.id.lnrHomeMain);

        dataHomeMenu = new ArrayList<SmartListItem>();
        menuData = IjoomerGlobalConfiguration.getHomeMenu(this,SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE,""));

        try {
            menuItems = new JSONArray(menuData.get(0).get("menuitem"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        prepareHomeMenu();
//        prepareGrid(menuItems);
//        getHomeMenuAdapter();
//        grdHome.setAdapter(gridAdapter);
        globalConfiguration = new IjoomerGlobalConfiguration(this);
    }

    private void prepareHomeMenu(){
        for(int i=0;i<menuItems.length();i+=3){
            LinearLayout layoutOuter = new LinearLayout(this);
            layoutOuter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutOuter.setGravity(Gravity.CENTER);
            layoutOuter.setOrientation(LinearLayout.HORIZONTAL);
            for (int j=i;j<i+3;j++){
                View layoutInner = LayoutInflater.from(this).inflate(R.layout.ijoomer_home_grid_menuitem,null,false);
                ImageView imgMenuItemicon =(ImageView) layoutInner.findViewById(R.id.imgMenuItemicon);
                IjoomerTextView txtMenuItemCaption  =(IjoomerTextView) layoutInner.findViewById(R.id.txtMenuItemCaption);

                imgMenuItemicon.setTag(j);
                switch (j){
                    case 0 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.business_listing_icon));
                        break;
                    case 1 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.towns_and_villages_icon));
                        break;
                    case 2 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.beaches_icon));
                        break;
                    case 3 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.things_to_do_icon));
                        break;
                    case 4 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.tourist_info_icon));
                        break;
                    case 5 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.gastronomy_icon));
                        break;
                    case 6 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.hotels_icon));
                        break;
                    case 7 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.taxis_icon));
                        break;
                    case 8 :
                        imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.search_icon));
                        break;
                    default:
                        break;
                }
                try {
                    JSONObject objItem = menuItems.getJSONObject(j);
                    if (objItem.getString(ITEMVIEW).equals("Login") && (SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LOGIN_REQ_OBJECT, null)) != null) {
                        objItem.put("logout", "logout");
                    }
                    if (!objItem.has(ICON)) {
                        ArrayList<HashMap<String, String>> iconData = IjoomerGlobalConfiguration.getSideMenuIcon(this, objItem.getString(ITEMVIEW),SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE,""));
                        if (iconData != null && iconData.size() > 0) {
                            objItem.put(ICON, iconData.get(0).get(ICON));
                        }
                    }

                    txtMenuItemCaption.setText(objItem.getString(ITEMCAPTION));
                    layoutInner.setTag(objItem);

                    layoutInner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            final IjoomerTextView txtMenuItemCaption = (IjoomerTextView) v.findViewById(R.id.txtMenuItemCaption);
                            final ImageView imgMenuItemicon = (ImageView) v.findViewById(R.id.imgMenuItemicon);
                            txtMenuItemCaption.setTextColor(getResources().getColor(R.color.home_selected_item));
                            int position = Integer.parseInt(imgMenuItemicon.getTag().toString());
                            switch (position){
                                case 0 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.business_listing_icon_h));
                                    break;
                                case 1 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.towns_and_villages_icon_h));
                                    break;
                                case 2 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.beaches_icon_h));
                                    break;
                                case 3 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.things_to_do_icon_h));
                                    break;
                                case 4 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.tourist_info_icon_h));
                                    break;
                                case 5 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.gastronomy_icon_h));
                                    break;
                                case 6 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.hotels_icon_h));
                                    break;
                                case 7 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.taxis_icon_h));
                                    break;
                                case 8 :
                                    imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.search_icon_h));
                                    break;
                                default:
                                    break;
                            }
                            getResources().getConfiguration().locale.getLanguage();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    int position = Integer.parseInt(imgMenuItemicon.getTag().toString());
                                    switch (position){
                                        case 0 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.business_listing_icon));
                                            break;
                                        case 1 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.towns_and_villages_icon));
                                            break;
                                        case 2 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.beaches_icon));
                                            break;
                                        case 3 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.things_to_do_icon));
                                            break;
                                        case 4 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.tourist_info_icon));
                                            break;
                                        case 5 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.gastronomy_icon));
                                            break;
                                        case 6 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.hotels_icon));
                                            break;
                                        case 7 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.taxis_icon));
                                            break;
                                        case 8 :
                                            imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.search_icon));
                                            break;
                                        default:
                                            break;
                                    }
                                    txtMenuItemCaption.setTextColor(getResources().getColor(R.color.txt_color));
                                    try {
                                        JSONObject object = (JSONObject) v.getTag();
                                        object.put("isHomeIcon", "true");
                                        launchActivity((JSONObject)v.getTag());
                                    }catch (Throwable e){
                                        e.printStackTrace();
                                    }
                                }
                            },1000);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                layoutOuter.addView(layoutInner,new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
            }
            lnrHomeMain.addView(layoutOuter,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f));
        }
    }

    @Override
    public void prepareViews() {
        ((ImageView) getHeaderView().findViewById(R.id.imgHeader)).setVisibility(View.VISIBLE);
        ((ImageView) getHeaderView().findViewById(R.id.imgSettings)).setVisibility(View.VISIBLE);
    }

    @Override
    public void setActionListeners() {
        ((ImageView) getHeaderView().findViewById(R.id.imgSettings)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ((ImageView) getHeaderView().findViewById(R.id.imgSettings)).setImageResource(R.drawable.sobipro_settings_hover);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) getHeaderView().findViewById(R.id.imgSettings)).setImageResource(R.drawable.sobipro_settings);
                        showSettingPopup(view);
                    }
                },1000);

            }
        });
    }

    public Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null)
            return null;
        try {

            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();

        return location;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(gridAdapter!=null){
            gridAdapter.notifyDataSetChanged();
        }
    }

    public void showSettingPopup(View v) {

        Rect rect = locateView(v);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.sobipro_settings, null);
        final IjoomerTextView txtSynchronization = (IjoomerTextView) layout.findViewById(R.id.txtSynchronization);
        final IjoomerTextView txtChangeLanguage = (IjoomerTextView) layout.findViewById(R.id.txtChangeLanguage);

        if(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LAST_REQUEST_TIME,"").length()<=0){
            txtSynchronization.setVisibility(View.GONE);
        }
        final PopupWindow popup = new PopupWindow(this);
        popup.setAnimationStyle(R.style.animation);
        popup.setContentView(layout);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popup.showAtLocation(layout, Gravity.RIGHT | Gravity.TOP,(v.getWidth()/2)-convertSizeToDeviceDependent(13),rect.bottom);
        if (popup.isShowing()) {

            txtSynchronization.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        popup.dismiss();
                        IjoomerUtilities.getCustomConfirmDialog(getString(R.string.synchronization), getString(R.string.are_you_sure_synchronization), getString(R.string.yes), getString(R.string.no),
                                new CustomAlertMagnatic() {

                                    @Override
                                    public void PositiveMethod() {
                                        IjoomerUtilities.addToNotificationBar(getString(R.string.synchronization),getString(R.string.synchronization),getString(R.string.synchronization_with_server_started));
                                        globalConfiguration.loadAllData(false,"1","",new WebCallListener() {
                                            @Override
                                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                                if (responseCode == 200) {
                                                    setLastRequestTime();
                                                    getSmartApplication().writeSharedPreferences(SP_LAST_REQUEST_TIME,getLastRequestTime());
                                                    IjoomerUtilities.addToNotificationBar(getString(R.string.synchronization), getString(R.string.synchronization), getString(R.string.synchronization_with_server_complete));
                                                } else {
                                                    responseMessageHandler(responseCode, true);
                                                }
                                            }

                                            @Override
                                            public void onProgressUpdate(int progressCount) {
                                            }
                                        });
                                    }

                                    @Override
                                    public void NegativeMethod() {

                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            txtChangeLanguage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        popup.dismiss();
                        Intent intent = new Intent(
                                "clearStackActivity");
                        intent.setType("text/plain");
                        sendBroadcast(intent);
                        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SP_LNAGUAGE,"");
                        loadNew(IjoomerSelectLanguageActivity.class,IjoomerHomeActivity.this,true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private void setLastRequestTime(){
        getSmartApplication().writeSharedPreferences(SP_LAST_REQUEST_TIME,String.valueOf(((System.currentTimeMillis()/1000)-900)));
    }

    private String getLastRequestTime(){
        return getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_TIME, "");
    }

    private void responseMessageHandler(final int responseCode,
                                        final boolean finishActivityOnConnectionProblem) {
        IjoomerUtilities.getCustomOkDialog(
                getString(R.string.synchronization),
                getString(getResources().getIdentifier("code" + responseCode,
                        "string", getPackageName())), getString(R.string.ok),
                R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                    @Override
                    public void NeutralMethod() {
                        if (responseCode == 599
                                && finishActivityOnConnectionProblem) {
                            finish();
                        }
                    }
                });
    }

    /**
     * This method used to prepare list home icon grid.
     */
    private void prepareGrid(JSONArray data) {
        dataHomeMenu.clear();

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject objItem = data.getJSONObject(i);
                if (!objItem.has(ICON)) {
                    ArrayList<HashMap<String, String>> iconData = IjoomerGlobalConfiguration.getSideMenuIcon(this, objItem.getString(ITEMVIEW),SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE,""));
                    if (iconData != null && iconData.size() > 0) {
                        objItem.put(ICON, iconData.get(0).get(ICON));
                    }
                }
                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.ijoomer_home_grid_menuitem);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(objItem);
                item.setValues(obj);
                dataHomeMenu.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

//    public void launchActivity(JSONObject obj) {
//        try {
//            setScreenCaption(obj.getString(ITEMCAPTION));
//            final String className = IjoomerScreenHolder.originalScreens
//                    .get(obj.getString(ITEMVIEW));
//
//            final Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setClassName(this, className);
//            intent.putExtra("IN_USERID", "0");
//            if (obj.getString(ITEMVIEW).equals("Login")
//                    && (getSmartApplication().readSharedPreferences()
//                    .getString(SP_LOGIN_REQ_OBJECT, "")).length() > 0) {
//                logout();
//                return;
//            } else if (obj.getString(ITEMVIEW).equals("Registration")) {
//                logout();
//                return;
//            }
//
//            else if (obj.getString(ITEMVIEW).equals("Web")) {
//                try {
//                    intent.setClassName(this, className);
//                    intent.putExtra(
//                            "url",
//                            new JSONObject(obj.getString(ITEMDATA))
//                                    .getString("url") + "");
//                    startActivity(intent);
//                    return;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            intent.putExtra("IN_OBJ", obj.toString());
//            intent.putExtra("IN_PRIVIOUS_ACTIVITY",getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY"));
//
//            startActivity(intent);
//        } catch (Exception e) {
//            tong(getString(R.string.sdk_error));
//            e.printStackTrace();
//        }
//    }

    /**
     * List adapter for home icon grid.
     */
    private void getHomeMenuAdapter() {
        gridAdapter = new SmartListAdapterWithHolder(this, R.layout.ijoomer_home_grid_menuitem, dataHomeMenu, new ItemView() {

            @Override
            public View setItemView(final int position, View v, SmartListItem item,final ViewHolder holder) {
                holder.imgMenuItemicon = (ImageView) v.findViewById(R.id.imgMenuItemicon);
                holder.txtMenuItemCaption = (IjoomerTextView) v.findViewById(R.id.txtMenuItemCaption);
                holder.lnrHomeItem =(LinearLayout) v.findViewById(R.id.lnrHomeItem);

                holder.txtMenuItemCaption.setTextColor(getResources().getColor(R.color.txt_color));
                final JSONObject obj = (JSONObject) item.getValues().get(0);
                switch (position){
                    case 0 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.business_listing_icon));
                        break;
                    case 1 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.towns_and_villages_icon));
                        break;
                    case 2 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.beaches_icon));
                        break;
                    case 3 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.things_to_do_icon));
                        break;
                    case 4 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.tourist_info_icon));
                        break;
                    case 5 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.gastronomy_icon));
                        break;
                    case 6 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.hotels_icon));
                        break;
                    case 7 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.taxis_icon));
                        break;
                    case 8 :
                        holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.search_icon));
                        break;
                    default:
                        break;
                }
                try {
                    holder.txtMenuItemCaption.setText(obj.getString(ITEMCAPTION));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                holder.lnrHomeItem.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        try{
                            holder.txtMenuItemCaption.setTextColor(getResources().getColor(R.color.home_selected_item));
                            switch (position){
                                case 0 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.business_listing_icon_h));
                                    break;
                                case 1 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.towns_and_villages_icon_h));
                                    break;
                                case 2 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.beaches_icon_h));
                                    break;
                                case 3 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.things_to_do_icon_h));
                                    break;
                                case 4 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.tourist_info_icon_h));
                                    break;
                                case 5 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.gastronomy_icon_h));
                                    break;
                                case 6 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.hotels_icon_h));
                                    break;
                                case 7 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.taxis_icon_h));
                                    break;
                                case 8 :
                                    holder.imgMenuItemicon.setImageDrawable(getResources().getDrawable(R.drawable.search_icon_h));
                                    break;
                                default:
                                    break;
                            }
                            getResources().getConfiguration().locale.getLanguage();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    launchActivity(obj);
                                }
                            },1000);
                        }catch (Exception e){
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
    }


}
