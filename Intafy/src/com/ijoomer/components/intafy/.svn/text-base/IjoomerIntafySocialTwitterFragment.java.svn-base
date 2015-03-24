package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.URLImageParser;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerIndexableListView;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyArticleDataProvider;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.library.intafy.IntafySocialDataProvider;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterIndexScrolling;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Fragment Contains All Method Related To JomAlbumAllFragment.
 *
 * @author tasol
 *
 */
@SuppressLint("ValidFragment")
public class IjoomerIntafySocialTwitterFragment extends SmartFragment implements IntafyTagHolder,IjoomerSharedPreferences {


    private ListView lstSocial;
    private LinearLayout listFooter;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private AQuery androidQuery;
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private ProgressBar pbrLoading;
    private IntafySocialDataProvider socialDataProvider;
    private HashMap<String ,String> network;
    public IjoomerIntafySocialTwitterFragment() {
    }

    /**
     * Overrides method
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_social_list_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        listFooter = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.ijoomer_list_footer, null);
        lstSocial = (ListView) currentView.findViewById(R.id.lstSocial);
        pbrLoading = (ProgressBar) currentView.findViewById(R.id.pbrLoading);
        lstSocial.addFooterView(listFooter, null, false);
        lstSocial.setAdapter(null);
        androidQuery = new AQuery(getActivity());
        socialDataProvider = new IntafySocialDataProvider(getActivity());
        lstSocial.setDivider(null);
        lstSocial.setDividerHeight(0);
        final String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        final String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        network = new IntafyNetworkDataProvider(getActivity()).getNetwork(networkId,connectedUserId);
    }

    @Override
    public void prepareViews(View currentView) {
        getFacebookFeed();
    }

    @Override
    public void setActionListeners(View currentView) {


    }


    private void getFacebookFeed(){
        pbrLoading.setVisibility(View.VISIBLE);
        final String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        final String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        ArrayList<HashMap<String,String>> data = socialDataProvider.getTwitterFeedFromDB(networkId, connectedUserId);
        if(data!=null && data.size()>0){
            prepareList(data,false);
            listAdapterWithHolder = getListAdapter();
            lstSocial.setAdapter(listAdapterWithHolder);
            pbrLoading.setVisibility(View.GONE);
        }else {
            responseErrorMessageHandler(204);
        }
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_twitter_social_stream), getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

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
        getFacebookFeed();
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

    public void prepareList(ArrayList<HashMap<String, String>> data, boolean append) {
        if (data != null) {
            if (!append) {
                listData.clear();
            }
            for (int i=0;i<data.size();i++){
                HashMap<String, String> hashMap = data.get(i);

                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.ijoomer_intafy_social_list_item);
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
     * List adapter for all album.
     */
    private SmartListAdapterWithHolder getListAdapter() {

        SmartListAdapterWithHolder adapterNetwork = new SmartListAdapterWithHolder(getActivity(), R.layout.ijoomer_intafy_social_list_item, listData, new ItemView() {

            @SuppressWarnings("unchecked")
            @Override
            public View setItemView(final int position, View v, SmartListItem item, ViewHolder holder) {
                holder.pbrImageLoad = (ProgressBar) v.findViewById(R.id.pbrImageLoad);
                holder.imgSocialUser = (RoundedImageView) v.findViewById(R.id.imgSocialUser);
                holder.imgFacebookOrTwitter = (ImageView) v.findViewById(R.id.imgFacebookOrTwitter);
                holder.txtLeftSocialTitleAndContent = (IjoomerTextView) v.findViewById(R.id.txtLeftSocialTitleAndContent);
                holder.txtLeftSocialUserName = (IjoomerTextView) v.findViewById(R.id.txtLeftSocialUserName);
                holder.txtLeftSocialDate = (IjoomerTextView) v.findViewById(R.id.txtLeftSocialDate);
                final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

                holder.txtLeftSocialUserName.setText(row.get("userName"));
                holder.txtLeftSocialDate.setText(IjoomerUtilities.getDateStringCurrentTimeZone(Long.parseLong(row.get("date"))));
                SpannableStringBuilder builder;
                try {
                    URLImageParser p = new URLImageParser(holder.txtLeftSocialTitleAndContent, getActivity());
                    builder = (SpannableStringBuilder) Html.fromHtml(row.get("title").trim()+" "+row.get("content").trim(), p, null);
                } catch (Throwable e) {
                    builder = (SpannableStringBuilder) Html.fromHtml(row.get("title").trim()+" "+row.get("content").trim());
                }

                holder.txtLeftSocialTitleAndContent.setMovementMethod(LinkMovementMethod.getInstance());
                holder.txtLeftSocialTitleAndContent.setText(builder);
                androidQuery.id(holder.imgSocialUser).progress(holder.pbrImageLoad).image(row.get("userAvatar"),true,true,70,R.drawable.thumbh_img);
                androidQuery.id( holder.imgFacebookOrTwitter).image(R.drawable.twitter_small_icon);

                holder.imgSocialUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = null;
                        try {
                            // get the Twitter app if possible
                            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id="+network.get("twitterPageId")));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } catch (Exception e) {
                            // no Twitter app, revert to browser
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+network.get("twitterPageId")));
                        }
                        getActivity().startActivity(intent);
                    }
                });
                holder.txtLeftSocialUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = null;
                        try {
                            // get the Twitter app if possible
                            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id="+network.get("twitterPageId")));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } catch (Exception e) {
                            // no Twitter app, revert to browser
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+network.get("twitterPageId")));
                        }
                        getActivity().startActivity(intent);
                    }
                });
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
