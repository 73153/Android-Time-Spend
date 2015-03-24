package com.ijoomer.components.intafy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.cropimage.CropImage;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.IjoomerVoiceAndTextMessager;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyCircleDataProvider;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyCircleMessageListActivity extends IjoomerIntafyMaster {

    private IjoomerIntafyCircleMessageListFragment circleMessageListFragment;
    private String IN_CIRCLE_ID;
    private String IN_CIRCLE_NAME;
    private String IN_CIRCLE_ISEDIT;
    private String IN_PUSHID;
    private String IN_PUSH_TYPE;
    private IjoomerEditText edtMessage;
    private ImageView imgSend;
    private IntafyCircleDataProvider circleDataProvider;
    final private int PICK_IMAGE_USER_AVATAR = 1;
    final private int CAPTURE_IMAGE_USER_AVATAR = 2;
    private String selectedImagePathUserAvatar;
    private IjoomerVoiceAndTextMessager voiceMessager;
    private LinearLayout lnrSendText;
    private LinearLayout lnrVoice;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private IjoomerTextView headerLeftText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private String IN_TABLENAME;
    private File mFileTemp;
    public final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_message_details_list;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return R.layout.ijoomer_intafy_circle_message_detail_header;
    }

    @Override
    public int setFooterLayoutId() {
        return R.layout.ijoomer_intafy_footer;
    }

    @Override
    public void initComponents() {
        edtMessage = (IjoomerEditText) findViewById(R.id.edtMessage);
        lnrVoice = (LinearLayout) findViewById(R.id.lnrVoice);
        lnrSendText = (LinearLayout) findViewById(R.id.lnrSendText);
        voiceMessager = (IjoomerVoiceAndTextMessager) findViewById(R.id.voiceMessager);
        imgSend = (ImageView) findViewById(R.id.imgSend);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        }
        else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    @Override
    public void prepareViews() {
        IN_CIRCLE_ID = getIntent().getStringExtra("IN_CIRCLE_ID")!=null ? getIntent().getStringExtra("IN_CIRCLE_ID"):"0";
        IN_CIRCLE_NAME = getIntent().getStringExtra("IN_CIRCLE_NAME")!=null ? getIntent().getStringExtra("IN_CIRCLE_NAME"):"";
        IN_CIRCLE_ISEDIT = getIntent().getStringExtra("IN_CIRCLE_ISEDIT")!=null ? getIntent().getStringExtra("IN_CIRCLE_ISEDIT"):"0";
        IN_TABLENAME = getIntent().getStringExtra("IN_TABLENAME")!=null ? getIntent().getStringExtra("IN_TABLENAME"):"";
        IN_PUSHID = getIntent().getStringExtra("IN_PUSHID")!=null ? getIntent().getStringExtra("IN_PUSHID"):"";
        IN_PUSH_TYPE = getIntent().getStringExtra("IN_PUSH_TYPE")!=null ? getIntent().getStringExtra("IN_PUSH_TYPE"):"";
        if(IN_PUSHID.trim().length()>0 && IN_PUSH_TYPE.trim().length()>0){
            String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
            String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
            new IntafyNetworkDataProvider(this).deletePushNotificationData(networkId,connectedUserId,IN_PUSH_TYPE);
        }
        circleMessageListFragment = new IjoomerIntafyCircleMessageListFragment(IN_CIRCLE_ID,IN_CIRCLE_NAME);
        addFragment(R.id.lnrFragment, circleMessageListFragment);
        headerText.setText(String.format(getString(R.string.intafy_group_thread),IN_CIRCLE_NAME));
        footerRightText.setText(getString(R.string.intafy_audio));
        footerLeftText.setText(getString(R.string.intafy_image));
        headerLeftText.setText(getString(R.string.intafy_back));
        circleDataProvider = new IntafyCircleDataProvider(this);
        ((ImageView) getHeaderView().findViewById(R.id.imgMapUserAvatar)).setImageResource(R.drawable.top_bar_circles_icon);
    }

    @Override
    public void setActionListeners() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSend.setImageResource(getIcon(SENDSMALLICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSend.setImageResource(getIcon(SENDSMALLICON,false));
                        if(edtMessage.getText().toString().trim().length()>0){
                            final SeekBar seekBar  =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                            circleDataProvider.addMessage(IN_CIRCLE_ID, edtMessage.getText().toString(), null,"message",new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    if (responseCode == 200) {
                                        edtMessage.setText("");
                                        circleMessageListFragment.update();
                                    } else {
                                        responseErrorMessageHandler(responseCode);
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {
                                    seekBar.setProgress(progressCount);
                                }
                            });
                        }else{
                            edtMessage.setError(getString(R.string.intafy_validation_value_required));
                        }
                    }
                }, 1000);
            }
        });
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        footerRightText.setTextColor(getResources().getColor(R.color.white));
                        if (lnrVoice.getVisibility() == View.GONE) {
                            lnrSendText.setVisibility(View.GONE);
                            lnrVoice.setVisibility(View.VISIBLE);
                            footerRightText.setText(getResources().getString(R.string.intafy_text));
                        } else {
                            lnrSendText.setVisibility(View.VISIBLE);
                            lnrVoice.setVisibility(View.GONE);
                            footerRightText.setText(getResources().getString(R.string.intafy_audio));
                        }
                    }
                }, 1000);
            }
        });
        lnrHeaderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) getHeaderView().findViewById(R.id.imgMapUserAvatar)).setImageResource(R.drawable.top_bar_circles_icon_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ((ImageView) getHeaderView().findViewById(R.id.imgMapUserAvatar)).setImageResource(R.drawable.top_bar_circles_icon);
                        try {
                            loadNew(IjoomerIntafyCircleMemberListActivity.class, IjoomerIntafyCircleMessageListActivity.this, false, "IN_CIRCLE_ID", IN_CIRCLE_ID,"IN_CIRCLE_ISEDIT",IN_CIRCLE_ISEDIT,"IN_TABLENAME",IN_TABLENAME);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);

            }
        });

        lnrHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
                        finish();
                    }
                }, 1000);
            }
        });

        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        footerLeftText.setTextColor(getResources().getColor(R.color.white));
                        showUploadImagePopup(v);
                    }
                }, 1000);
            }
        });

        voiceMessager.setMessageHandler(new IjoomerVoiceAndTextMessager.MessageHandler() {
            @Override
            public void onVoiceMessageRecordingComplete(String message, String voiceMessagePath) {
                uploadAudio(voiceMessagePath);
            }

            @Override
            public void onButtonSend(String message) {

            }

            @Override
            public void onToggle(int messager) {

            }
        });
    }
    private void uploadAudio(String voiceFilePath){
        final SeekBar seekBar  =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        circleDataProvider.addMessage(IN_CIRCLE_ID, "", voiceFilePath,"audio", new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                if (responseCode == 200) {
                    edtMessage.setText("");
                    if(circleMessageListFragment !=null){
                        circleMessageListFragment.update();
                    }
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

    private void showUploadImagePopup(View view){
        Rect r = new Rect();
        view.getDrawingRect(r);

        final PopupWindow popup = new PopupWindow(this);
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.ijoomer_intafy_profile_change_photo_popup, viewGroup);

        final IjoomerTextView txtTakePhoto = (IjoomerTextView) layout.findViewById(R.id.txtTakePhoto);
        final IjoomerTextView txtUploadPhoto = (IjoomerTextView) layout.findViewById(R.id.txtUploadPhoto);
        final IjoomerTextView txtCancel = (IjoomerTextView) layout.findViewById(R.id.txtCancel);

        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTakePhoto.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                        startActivityForResult(intent, CAPTURE_IMAGE_USER_AVATAR);
                        txtTakePhoto.setTextColor(getResources().getColor(R.color.white));
                        popup.dismiss();
                    }
                }, 1000);

            }
        });

        txtUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUploadPhoto.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE_USER_AVATAR);
                        txtUploadPhoto.setTextColor(getResources().getColor(R.color.white));
                        popup.dismiss();
                    }
                }, 1000);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCancel.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        txtUploadPhoto.setTextColor(getResources().getColor(R.color.white));
                        popup.dismiss();
                    }
                }, 1000);
            }
        });

        popup.setContentView(layout);
        popup.setWidth(getDeviceWidth() - convertSizeToDeviceDependent(20));
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popup.showAtLocation(layout,Gravity.BOTTOM, 0,r.bottom-convertSizeToDeviceDependent(25));
    }
    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(String.format(getString(R.string.intafy_group_thread),IN_CIRCLE_NAME), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    @Override
    protected void onResume() {
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            if(circleMessageListFragment !=null){
                circleMessageListFragment.update();
            }
            IjoomerApplicationConfiguration.setReloadRequired(false);
        }
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_USER_AVATAR) {
//                selectedImagePathUserAvatar = getImagePath();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(getImageUri(getImagePath()));
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                uploadImage(mFileTemp);
            } else if (requestCode == PICK_IMAGE_USER_AVATAR) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                }catch (Throwable e){
                    e.printStackTrace();
                }
//                selectedImagePathUserAvatar = getAbsolutePath(data.getData());
                uploadImage(mFileTemp);
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }else{
            selectedImagePathUserAvatar="";
        }

    }

    public void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private void uploadImage(final File file) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap b = decodeFileFromPath(file.getPath());
                try {
                    ExifInterface ei = new ExifInterface(file.getPath());
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    Matrix matrix = new Matrix();
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                        default:
                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                FileOutputStream out1 = null;
                try {
                    if (mFileTemp.exists())
                        mFileTemp.delete();
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
                    } else {
                        mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
                    }
                    out1 = new FileOutputStream(mFileTemp);
                    b.compress(Bitmap.CompressFormat.JPEG, 90, out1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out1.close();
                    } catch (Throwable ignore) {

                    }
                }
                selectedImagePathUserAvatar = mFileTemp.getAbsolutePath();
                final SeekBar seekBar  =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                circleDataProvider.addMessage(IN_CIRCLE_ID, "", selectedImagePathUserAvatar,"image",new WebCallListener() {
                    @Override
                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                        if (responseCode == 200) {
                            edtMessage.setText("");
                            circleMessageListFragment.update();
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
        });

    }

    private Bitmap decodeFileFromPath(String path){
        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = getContentResolver().openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            if (o.outHeight > 1024 || o.outWidth > 1024) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(1024 / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

}
