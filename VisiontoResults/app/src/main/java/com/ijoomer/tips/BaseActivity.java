package com.ijoomer.tips;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.menubuilder.MenuDrawer;



public class BaseActivity extends FragmentActivity {

    public LinearLayout content;
    private MenuDrawer mMenuDrawer;
    public ImageView imgSlideMenu;
    public ImageView imgSlideBack;
    public LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setContentView(findViewById(R.id.main));

        content = (LinearLayout) findViewById(R.id.content);
        imgSlideMenu = (ImageView) findViewById(R.id.imgSlideMenu);
        imgSlideBack = (ImageView) findViewById(R.id.imgSlideBack);
        main = (LinearLayout) findViewById(R.id.main);

        imgSlideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuDrawer.toggleMenu(true);
            }
        });
        mMenuDrawer.setDropShadowEnabled(false);
        prepareSlideMenu();
        setupUI(main);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BaseActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }


    public void prepareSlideMenu() {

        final View sideMenuView = LayoutInflater.from(this).inflate(R.layout.slide_menu, null);

        final ListView lstSlideMenu = (ListView) sideMenuView.findViewById(R.id.lstSlideMenu);
        final SlideMenuAdapter listAdapter = new SlideMenuAdapter(this,getResources().getStringArray(R.array.slidemenu));
        lstSlideMenu.setAdapter(listAdapter);

        lstSlideMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Intent intent=null;
                switch (pos){
                    case 0:
                        intent = new Intent(BaseActivity.this,InspireMeActivity.class);
                        break;
                    case 1:
                        intent = new Intent(BaseActivity.this,TipsCollectionActivity.class);
                        break;
                    case 2:
                        intent = new Intent(BaseActivity.this,MyFavouriteActivity.class);
                        break;
                    case 3:
                        intent = new Intent(BaseActivity.this,MySettingsActivity.class);
                        break;
                    case 4:
                        intent = new Intent(BaseActivity.this,AboutVTRActivity.class);
                        break;
                    case 5:
                        intent = new Intent(BaseActivity.this,AboutActivity.class);
                        break;
                    case 6:
                        intent = new Intent(BaseActivity.this,ContactUsActivity.class);
                        break;
                    case 7:
                        intent = new Intent(BaseActivity.this,ShareAppActivity.class);
                        break;
                }
                startActivity(intent);
                finish();
            }
        });
        mMenuDrawer.setMenuView(sideMenuView);
    }

    class SlideMenuAdapter extends BaseAdapter {

        private Context context;
        private String[] names;
        public SlideMenuAdapter(Context context,String[] names){
            this.context=context;
            this.names =names;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.slide_menu_item,null);
                holder.txtSlideMenuName = (CustomTextView) convertView.findViewById(R.id.txtSlideMenuName);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtSlideMenuName.setText(Html.fromHtml(names[position]));

            return convertView;
        }

        class ViewHolder{
            CustomTextView txtSlideMenuName;
        }
    }
}

