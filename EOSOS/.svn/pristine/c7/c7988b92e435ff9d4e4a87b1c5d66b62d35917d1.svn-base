package com.eosos.components.main;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.classes.IjoomerWebviewClient;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.smart.framework.CustomAlertMagnatic;

public class EososInformationActivity extends EososMasterActivity {
    private SeekBar seekRate, seekFaq, seekFeedback, seekTerms;
    private int progress;
    private String faqContent;

    @Override
    public int setLayoutId() {
        return R.layout.eosos_information;
    }

    @Override
    public void initComponents() {
        seekRate = (SeekBar) findViewById(R.id.seekRate);
        seekFaq = (SeekBar) findViewById(R.id.seekFaq);
        seekFeedback = (SeekBar) findViewById(R.id.seekFeedback);
        seekTerms = (SeekBar) findViewById(R.id.seekTerms);
        faqContent = "<h3>What does EOSOS Elite stand for?</h3>EOSOS Elite specializes in nightlife and entertainment and hence ‘eosos’ (latin) is referring to the dawn after a good night out. The last part, elite, is a reference to the high profile venues we are working with.<br><br><h3>What can I use the application for?</h3>EOSOS Elite created ‘Elite Clubs’ with the vision to provide a global directory of the leading nightclubs across the globe. In today's busy life, we need information quickly and easily. The Elite Clubs provides not only essential information about the world’s leading nightclubs in the most vital cities, but also includes tools to help plan and enjoy your night.<br><br><h3>In which countries can I use the app?</h3>We have venues listed on 5 continents, more than 60 countries and 130 cities globally.<br><br><h3>Can I access the information of this app on any other devices?</h3>Yes, the content of this application can also be downloaded to your iPad and any device that works with Android applications.<br><br><h3>What types of clubs are in the apps’ directory?</h3>We refrain from using the word ‘best’ because everyone’s definition of ‘best’ is different; however, we would venture to say that we have included the world’s most prestigious clubs. We are constantly looking for feedback to maintain an accurate list, as some clubs improve, while others slowly fade away.<br><br><h3>How can I update the application?</h3>As with any other app, styling and functionality updates will be available through the app store. However, content updates are instantaneously drawn from our servers, hence, you never need to update the content – it is always up-to-date.<br><br><h3>Does the app work in offline modus?</h3>This app will work best online; however, main information such as the directory will be ‘cached’ on your phone. Note, the content will not update when you are offline.<br><br><h3>How does the geotagging function work?</h3>You need to allow your phone/tablet to detect your current location. The application will show your point on a map.  Nightclubs from our directory within that region will be shown.<br><br><h3>How can I add a club to my favorites list?</h3>A club can simply be added to the favorite list by clicking the black diamond on the right top of the description page of the club. Once a club has been favored its diamond will appear red and it will appear in the favorite list.<br><br><h3>How does the Route feature work?</h3>The route feature enables you to plan your route to a chosen nightclub. Simply select the start of the journey and the club. You will be able to choose between walking and car.<br><br><h3>How does the Planner feature work?</h3>The planner enables you to look up clubs in cities on particular nights. So, if you are planning on going to Paris on a Wednesday, you can look up which clubs will actually be open that night.<br><br><h3>A club is not yet in the list, where can I recommend it?</h3>We are constantly looking for feedback to have the most comprehensive list possible. You can recommend a club on the settings page of the app. We will review your submission an add it if it meets our criteria.<br><br><h3>How can a club be removed or information be adjusted?</h3>Just as you can suggest adding a club, you can suggest information change or removal. As global nightlife is fast shifting, local feedback is the best way to make sure our list stays current.<br><br><h3>Can I rate a club?</h3>Yes, you can rate a club from one to five ‘globes’ and leave a comment if you like. The globe rating will help identify the clubs, which you should visit in any particular city. The ratings will be reset every now and so often in order to have an up-to-date rating of all clubs.<br><br><h3>How will my rating and feedback be processed?</h3>Feedback directly to us (via settings) will be processed within 48h. Ratings and comments will be instantly posted to the app and can be seen by you and other users straight away.";

    }

    @Override
    public void onResume() {
        super.onResume();
        seekRate.setProgress(0);
        seekFaq.setProgress(0);
        seekFeedback.setProgress(0);
        seekTerms.setProgress(0);
    }

    @Override
    public void prepareViews() {
        ((IjoomerTextView)getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.eosos_elite_clubs_header));
        ((ImageView)getHeaderView().findViewById(R.id.imgHome)).setVisibility(View.GONE);
        ((IjoomerTextView)getHeaderView().findViewById(R.id.txtBack)).setVisibility(View.VISIBLE);
    }


    @Override
    public void setActionListeners() {
        ((IjoomerTextView)getHeaderView().findViewById(R.id.txtBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("clearStackActivity");
                intent.setType("text/plain");
                sendBroadcast(intent);
                finish();
            }
        });
        seekTerms.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

                setProgreeHandler(seekBar);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 100) {
                    try {
                        try {
                            loadNew(IjoomerWebviewClient.class, EososInformationActivity.this, false, "url", "file:///android_asset/terms.html","IN_FROM",getString(R.string.eosos_terms_n_conditions_header));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        seekFeedback.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setProgreeHandler(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 100) {
                    loadNew(EososFeedbackActivity.class, EososInformationActivity.this, false);
                }
            }
        });

        seekFaq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setProgreeHandler(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 100) {
                    try {
                        loadNew(IjoomerWebviewClient.class, EososInformationActivity.this, false, "IN_CONTENT", faqContent,"IN_FROM",getString(R.string.eosos_f_a_q_header));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        seekRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setProgreeHandler(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 100) {
                    IjoomerUtilities.getCustomConfirmRatingAndFacebookShareDialog(getString(R.string.app_text), getString(R.string.app_rating_text), new CustomAlertMagnatic() {
                        @Override
                        public void PositiveMethod() {
                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }

                        @Override
                        public void NegativeMethod() {
                            seekRate.setProgress(0);
                        }
                    });

                }
            }
        });

    }

    public void setProgreeHandler(final SeekBar seekBar) {

        progress = seekBar.getProgress();
        if (progress > 50) {

            new Thread(new Runnable() {

                @Override
                public void run() {

                    while (progress < 100) {
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                progress += 5;
                                seekBar.setProgress(progress);
                            }
                        });
                    }

                }
            }).start();

        } else {
            seekBar.setProgress(0);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        seekRate.setProgress(0);
        seekFaq.setProgress(0);
        seekFeedback.setProgress(0);
        seekTerms.setProgress(0);
    }
}
