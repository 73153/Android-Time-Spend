package com.mycadiz.components.sobipro;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.mycadiz.caching.IjoomerCachingConstants;
import com.mycadiz.common.classes.IjoomerMenus;
import com.mycadiz.common.classes.IjoomerScreenHolder;
import com.mycadiz.common.classes.IjoomerSuperMaster;
import com.mycadiz.custom.interfaces.IjoomerSharedPreferences;
import com.mycadiz.src.IjoomerHomeActivity;
import com.mycadiz.src.R;

import org.json.JSONObject;

public abstract class SobiproMasterActivity extends IjoomerSuperMaster implements SobiproTagHolder,IjoomerSharedPreferences {

    public static SobiproTheme[] themes;
    private final String MENUITEM = "menuitem";
    private final String ITEMVIEW = "itemview";
    private final String ITEMDATA = "itemdata";
    private final String ITEMCAPTION = "itemcaption";
    public static int IMAGE_MAX_SIZE = 12;

    public SobiproMasterActivity() {
        super();
        IjoomerCachingConstants.unNormalizeFields = SobiproCachingConstants.getUnnormlizeFields();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }

    @Override
    public void loadHeaderComponents() {
        super.loadHeaderComponents();

        try {
            ((ImageView) getHeaderView().findViewById(R.id.imgback)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ImageView) getHeaderView().findViewById(R.id.imgback)).setImageResource(R.drawable.sobipro_header_left_hover);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((ImageView) getHeaderView().findViewById(R.id.imgback)).setImageResource(R.drawable.sobipro_header_left);
                            finish();
                        }
                    },1000);

                }
            });

            ((ImageView) getHeaderView().findViewById(R.id.imgListing)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ImageView) getHeaderView().findViewById(R.id.imgListing)).setImageResource(R.drawable.sobipro_header_right_hover);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((ImageView) getHeaderView().findViewById(R.id.imgListing)).setImageResource(R.drawable.sobipro_header_right);
                            Intent intent = new Intent("clearStackActivity");
                            intent.setType("text/plain");
                            sendBroadcast(intent);
                            loadNew(IjoomerHomeActivity.class, SobiproMasterActivity.this, true);
                        }
                    },1000);

                }
            });
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public void launchActivity(JSONObject obj) {
        try {

            setScreenCaption(obj.getString(ITEMCAPTION));
            final String className = IjoomerScreenHolder.originalScreens
                    .get(obj.getString(ITEMVIEW));

            IjoomerMenus.getInstance().setTabBarData(null);
            IjoomerMenus.getInstance().setSideMenuData(null);

            final Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClassName(this, className);
            intent.putExtra("IN_USERID", "0");
            if(obj.getString(ITEMVIEW).equals("SobiproShare")){
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link));
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,getString(R.string.share)));
                return;
            }else if (obj.getString(ITEMVIEW).equals("Login")
                    && (getSmartApplication().readSharedPreferences()
                    .getString(SP_LOGIN_REQ_OBJECT, "")).length() > 0) {
                logout();
                return;
            } else if (obj.getString(ITEMVIEW).equals("Registration")) {
                logout();
                return;
            }

            else if (obj.getString(ITEMVIEW).equals("Web")) {
                try {
                    intent.setClassName(this, className);
                    intent.putExtra(
                            "url",
                            new JSONObject(obj.getString(ITEMDATA))
                                    .getString("url") + "");
                    startActivity(intent);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            intent.putExtra("IN_OBJ", obj.toString());
            intent.putExtra("IN_PRIVIOUS_ACTIVITY",obj.getString(ITEMCAPTION));

            startActivity(intent);

        } catch (Exception e) {
            tong(getString(R.string.sdk_error));
            e.printStackTrace();
        }
    }

    @Override
    public void initTheme() {
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return R.layout.sobipro_header;
    }

    @Override
    public int setFooterLayoutId() {
        return R.layout.sobipro_footer;
    }

    @Override
    public String[] setTabItemNames() {
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
    public int[] setTabItemOnDrawables() {
        return null;
    }

    @Override
    public int[] setTabItemOffDrawables() {
        return null;
    }

    @Override
    public int[] setTabItemPressDrawables() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IjoomerCachingConstants.unNormalizeFields = SobiproCachingConstants.getUnnormlizeFields();
    }
}
