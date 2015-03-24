package com.ijoomer.common.classes;

import android.content.Context;
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
import android.widget.RadioGroup;

import com.ijoomer.caching.IjoomerCachingConstants;
import com.ijoomer.components.intafy.IjoomerIntafyCircleListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyDirectoryListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyEventListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyMessageListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyNetworkAddActivity;
import com.ijoomer.components.intafy.IjoomerIntafyProfileActivity;
import com.ijoomer.components.intafy.IntafyCachingConstants;
import com.ijoomer.intafy.src.IjoomerHomeActivity;
import com.ijoomer.intafy.src.R;
import com.smart.framework.CustomAlertNeutral;

import java.util.Timer;
import java.util.TimerTask;


/**
 * This Class Contains All Method Related To IjoomerHomeMaster.
 *
 * @author tasol
 *
 */
public abstract class IjoomerIntafyMaster extends IjoomerSuperMaster {


    public IjoomerIntafyMaster() {
        super();
        IjoomerCachingConstants.unNormalizeFields = IntafyCachingConstants.getUnnormlizeFields();
        IjoomerCachingConstants.unRepetedFields = IntafyCachingConstants.getUnRepetedField();
    }

    /**
     * Override method
     */
    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public View setTopAdvertisement() {
        return null;
    }

    @Override
    public View setBottomAdvertisement() {
        return null;
    }

    @Override
    public int setTabBarDividerResId() {
        return 0;
    }

    @Override
    public int setTabItemLayoutId() {
        return 0;
    }

    @Override
    public String[] setTabItemNames() {
        return null;
    }

    @Override
    public int[] setTabItemOffDrawables() {
        return null;
    }

    @Override
    public int[] setTabItemOnDrawables() {
        return null;
    }

    @Override
    public int[] setTabItemPressDrawables() {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void setActionListeners() {

    }

    @Override
    public void loadHeaderComponents() {
        super.loadHeaderComponents();
        try {
            ((ImageView) getFooterView().findViewById(R.id.imgBottomCenterIcon)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    ((ImageView) getFooterView().findViewById(R.id.imgBottomCenterIcon)).setImageResource(getIcon(FOOTERICON,true));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            ((ImageView) getFooterView().findViewById(R.id.imgBottomCenterIcon)).setImageResource(getIcon(FOOTERICON,false));
                            showBottomMenuPopup(view);
                        }
                    }, 1000);
                }
            });
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IjoomerCachingConstants.unNormalizeFields = IntafyCachingConstants.getUnnormlizeFields();
        IjoomerCachingConstants.unRepetedFields = IntafyCachingConstants.getUnRepetedField();

    }

    private void showBottomMenuPopup(View view){
        Rect r = new Rect();
        view.getDrawingRect(r);

        final PopupWindow popup = new PopupWindow(this);
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.ijoomer_intafy_bottom_popup, viewGroup);

        final ImageView imgMessage = (ImageView) layout.findViewById(R.id.imgMessage);
        final ImageView imgProfile = (ImageView) layout.findViewById(R.id.imgProfile);
        final ImageView imgCircles = (ImageView) layout.findViewById(R.id.imgCircles);
        final ImageView imgDirectories = (ImageView) layout.findViewById(R.id.imgDirectories);
        final ImageView imgHome = (ImageView) layout.findViewById(R.id.imgHome);
        final ImageView imgEvents = (ImageView) layout.findViewById(R.id.imgEvents);
        final ImageView imgClose = (ImageView) layout.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClose.setImageResource(getIcon(CLOSEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgClose.setImageResource(getIcon(CLOSEICON,false));
                        popup.dismiss();
                    }
                }, 1000);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgHome.setImageResource(getIcon(HOMEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgHome.setImageResource(getIcon(HOMEICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            popup.dismiss();
                            loadNew(IjoomerHomeActivity.class, IjoomerIntafyMaster.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgProfile.setImageResource(getIcon(HOMEPROFILEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgProfile.setImageResource(getIcon(HOMEPROFILEICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            popup.dismiss();
                            loadNew(IjoomerIntafyProfileActivity.class, IjoomerIntafyMaster.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgDirectories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDirectories.setImageResource(getIcon(DIRECTORIESICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgDirectories.setImageResource(getIcon(DIRECTORIESICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            popup.dismiss();
                            loadNew(IjoomerIntafyDirectoryListActivity.class, IjoomerIntafyMaster.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEvents.setImageResource(getIcon(HOMEEVENTICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEvents.setImageResource(getIcon(HOMEEVENTICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            popup.dismiss();
                            loadNew(IjoomerIntafyEventListActivity.class, IjoomerIntafyMaster.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);

            }
        });
        imgCircles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCircles.setImageResource(getIcon(HOMECIRCLEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgCircles.setImageResource(getIcon(HOMECIRCLEICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            popup.dismiss();
                            loadNew(IjoomerIntafyCircleListActivity.class, IjoomerIntafyMaster.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMessage.setImageResource(getIcon(HOMEMESSAGEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgMessage.setImageResource(getIcon(HOMEMESSAGEICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            popup.dismiss();
                            loadNew(IjoomerIntafyMessageListActivity.class, IjoomerIntafyMaster.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        popup.setContentView(layout);
        popup.setWidth(getDeviceWidth());
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popup.showAtLocation(layout,Gravity.BOTTOM, 0,r.bottom-convertSizeToDeviceDependent(50));
    }

    public String getAudio(String strData) {

        if (strData.contains("{voice}")) {
            strData = strData.substring(strData.indexOf("}"), strData.length());
            strData = strData.substring(1, strData.indexOf("{"));
            strData = strData.split("&")[0];
            return strData;
        }
        return null;
    }
    public String getImage(String strData) {

        if (strData.contains("{image}")) {
            strData = strData.substring(strData.indexOf("}"), strData.length());
            strData = strData.substring(1, strData.indexOf("{"));
            strData = strData.split("&")[0];
            return strData;
        }
        return null;
    }

    public String getAudioLength(String strData) {

        try {
            if (strData.contains("{voice}")) {
                strData = strData.substring(strData.indexOf("}"), strData.length());
                strData = strData.substring(1, strData.indexOf("{"));
                strData = strData.split("&")[1];
                return strData;
            }
        } catch (Exception e) {
        }
        return "0";
    }
    public String getPlainText(String strData) {

        if (strData.contains("{voice}")) {
            strData = strData.substring(0, strData.indexOf("{voice}"));
        }
        return strData;
    }



    private void showAddNetworkDialog(){
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_networks), getString(R.string.intafy_add_network_validation),
                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                    @Override
                    public void NeutralMethod() {
                        loadNew(IjoomerIntafyNetworkAddActivity.class,IjoomerIntafyMaster.this,false);
                    }
                });
    }

}
