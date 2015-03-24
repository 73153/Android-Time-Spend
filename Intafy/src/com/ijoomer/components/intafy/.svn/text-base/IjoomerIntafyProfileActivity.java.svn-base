package com.ijoomer.components.intafy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.cropimage.CropImage;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.InternalStorageContentProvider;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyUserDataProvider;
import com.ijoomer.page.indicator.CirclePageIndicator;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyProfileActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private PageAdapter adapter;

    private IntafyUserDataProvider intafyUserDataProvider;
    private ProgressBar pbrImageLoad;
    private ProgressBar pbrFieldLoad;

    private RoundedImageView imgUserImage;
    private IjoomerTextView txtUserFristLastName;
    private IjoomerTextView txtUserStatus;

    private IjoomerTextView txtUserEmail;
    private AQuery androidQuery;
    final private int PICK_IMAGE_USER_AVATAR = 1;
    final private int CAPTURE_IMAGE_USER_AVATAR = 2;
    final private int CROPED_IMAGE_USER_AVATAR = 3;
    private String selectedImagePathUserAvatar;
    private String selectedOrignalImagePathUserAvatar;
    private JSONArray profileField;
    private HashMap<String,String> profileData;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private IjoomerTextView headerText;
    private File mFileTemp;
    private File mOrignalFileTemp;
    private  Uri mImageCaptureUri;
    public final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public final String TEMP_ORIGNAL_PHOTO_FILE_NAME = "temp_orignal_photo.jpg";
    private boolean isAvatarChanged=false;

    /**
     * Overrides methods
     */



    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_profile_details;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return R.layout.ijoomer_intafy_header;
    }

    @Override
    public int setFooterLayoutId() {
        return R.layout.ijoomer_intafy_footer;
    }

    @Override
    public void initComponents() {
        pbrImageLoad = (ProgressBar)findViewById(R.id.pbrImageLoad);
        pbrFieldLoad = (ProgressBar)findViewById(R.id.pbrFieldLoad);

        imgUserImage = (RoundedImageView) findViewById(R.id.imgUserImage);
        txtUserFristLastName = (IjoomerTextView) findViewById(R.id.txtUserFristLastName);
        txtUserStatus = (IjoomerTextView) findViewById(R.id.txtUserStatus);

        txtUserEmail = (IjoomerTextView) findViewById(R.id.txtUserEmail);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator =(CirclePageIndicator) findViewById(R.id.indicator);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
        intafyUserDataProvider = new IntafyUserDataProvider(this);
        androidQuery = new AQuery(this);
    }

    @Override
    public void prepareViews() {
        headerText.setText(getString(R.string.intafy_my_profile));
        headerLeftText.setText(getString(R.string.intafy_back));
        footerLeftText.setText(getString(R.string.intafy_edit_profile));
        footerRightText.setText(getString(R.string.intafy_change_photo));
        getProfile();

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
            mOrignalFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_ORIGNAL_PHOTO_FILE_NAME);
        }
        else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
            mOrignalFileTemp = new File(getFilesDir(), TEMP_ORIGNAL_PHOTO_FILE_NAME);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            footerLeftText.setTextColor(getResources().getColor(R.color.white));
            getProfile();
            IjoomerApplicationConfiguration.setReloadRequired(false);
        }
    }

    @Override
    public void setActionListeners() {
        imgUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadNew(IjoomerIntafyImageViewActivity.class,IjoomerIntafyProfileActivity.this,false,"IN_IMAGE",profileData.get(USER_ORIGNLEAVATAR));
                }catch (Throwable e){
                    e.printStackTrace();
                }
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
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        footerRightText.setTextColor(getResources().getColor(R.color.white));
                        showChangePhotoPopup(view);
                    }
                }, 1000);
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            footerLeftText.setTextColor(getResources().getColor(R.color.white));
                            loadNew(IjoomerIntafyEditProfileActivity.class, IjoomerIntafyProfileActivity.this, false, "IN_FIELD", profileField.toString(), "IN_FIRSTNAME", profileData.get(USER_NAME), "IN_LASTNAME", profileData.get(LASTNAME), "IN_STATUS", profileData.get(USER_STATUS), "IN_PINCODE", profileData.get(PINCODE), "IN_EMAIL", profileData.get(EMAIL));
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });

    }


    private void getProfileField(){
        String connectedUserId = getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
        intafyUserDataProvider.getUserDetails("0",connectedUserId,networkId,new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                try{

                    if(responseCode==200){
                        try{
                            profileField= new JSONArray(data1.get(0).get("fields"));
                            JSONArray jsonArray = new JSONArray();
                            for (int i=0;i<profileField.length();i++){
                                if(profileField.getJSONObject(i).getString(VALUE).trim().length()>0){
                                    if(profileField.getJSONObject(i).getString(TYPE).equalsIgnoreCase("phone")){
                                        JSONObject jsonObject = profileField.getJSONObject(i);
                                        String[] phone = jsonObject.getString(VALUE).split("-");
                                        String number = "+";
                                        for (int j=0;j<phone.length;j++){
                                            number +=phone[j]+"-";
                                        }
                                        if(number.length()>2) {
                                            jsonObject.put(VALUE, number.substring(0, number.length() - 1));
                                            jsonArray.put(jsonObject);
                                        }
                                    }else{
                                        jsonArray.put(profileField.getJSONObject(i));
                                    }
                                }
                            }
                            adapter = new PageAdapter(getSupportFragmentManager(),jsonArray);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(0);
                        indicator.setPageColor(Color.TRANSPARENT);
                        indicator.setStrokeColor(Color.parseColor(getString(R.color.white)));
                        indicator.setStrokeWidth(convertSizeToDeviceDependent(1));
                        indicator.setRadius(convertSizeToDeviceDependent(3));
                        indicator.setFillColor(Color.parseColor(getString(R.color.white)));
                        indicator.setViewPager(viewPager, 0);
                        indicator.setSnap(true);
                    }else{
                        responseErrorMessageHandler(responseCode);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
                pbrFieldLoad.setVisibility(View.GONE);
            }

            @Override
            public void onProgressUpdate(int progressCount) {
            }
        });
    }

    private void getProfile(){
        pbrFieldLoad.setVisibility(View.VISIBLE);
        final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        String connectedUserId = getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
        intafyUserDataProvider.getUserProfile("0",connectedUserId,networkId,new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                try{
                    if(responseCode==200){
                        profileData = data1.get(0);
                        if(!isAvatarChanged) {
                            androidQuery.id(imgUserImage).progress(pbrImageLoad).image(profileData.get(USER_AVATAR), true, true, 150, R.drawable.ic_launcher);
                        }else{
                            isAvatarChanged=false;
                        }
                        if(profileData.get(USER_NAME).trim().length()>0){
                            txtUserFristLastName.setText(profileData.get(USER_NAME) +" "+profileData.get(LASTNAME));
                            txtUserFristLastName.setVisibility(View.VISIBLE);
                        }
                        if(profileData.get(USER_STATUS).trim().length()>0){
                            txtUserStatus.setText(profileData.get(USER_STATUS));
                            txtUserStatus.setVisibility(View.VISIBLE);
                        }
                        if(profileData.get(EMAIL).trim().length()>0){
                            txtUserEmail.setText(profileData.get(EMAIL));
                            txtUserEmail.setVisibility(View.VISIBLE);
                        }
                        if(data2.toString().equals("1")) {
                            getProfileField();
                        }
                    }else{
                        responseErrorMessageHandler(responseCode);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgressUpdate(int progressCount) {
                seekBar.setProgress(progressCount);
            }
        });


    }
    private void showChangePhotoPopup(View view){
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
                        txtTakePhoto.setTextColor(getResources().getColor(R.color.white));
                        try{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            String state = Environment.getExternalStorageState();
                            if (Environment.MEDIA_MOUNTED.equals(state)) {
                                mImageCaptureUri = Uri.fromFile(mFileTemp);
                            }
                            else {
                                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
                            }
                            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, CAPTURE_IMAGE_USER_AVATAR);
                            popup.dismiss();
                        } catch (Throwable e) {
                            popup.dismiss();
                            e.printStackTrace();
                        }
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
                        txtUploadPhoto.setTextColor(getResources().getColor(R.color.white));
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

    private class PageAdapter extends FragmentStatePagerAdapter {

        private JSONArray fieldJsonArray;
        public PageAdapter(FragmentManager fm,JSONArray jsonArray) {
            super(fm);
            fieldJsonArray =jsonArray;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int pos) {
            JSONArray data = new JSONArray();
            int startIndex=0;
            if(pos>0){
                startIndex = pos*3;
            }
            int endIndex = startIndex+3;
            if(fieldJsonArray.length()<endIndex){
                endIndex=fieldJsonArray.length();
            }
            for (int i=startIndex;i<endIndex;i++){
                try{
                    data.put(fieldJsonArray.get(i));
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
            return new IjoomerIntafyProfileFragment(data);
        }

        @Override
        public int getCount() {
            return fieldJsonArray.length()>3 ? fieldJsonArray.length()%3==0?(fieldJsonArray.length()/3):(fieldJsonArray.length()/3)+1:1;
        }

    }

    private void updateAvatar(){
        isAvatarChanged=true;
        final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        intafyUserDataProvider.updateUserProfile(selectedImagePathUserAvatar,selectedOrignalImagePathUserAvatar,new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                if(responseCode==200){
                    IjoomerApplicationConfiguration.setReloadRequired(true);
                    onResume();
                }else{
                    isAvatarChanged=false;
                    androidQuery.id(imgUserImage).progress(pbrImageLoad).image(profileData.get(USER_AVATAR),true,true,150,0);
                    responseErrorMessageHandler(responseCode);
                }
            }

            @Override
            public void onProgressUpdate(int progressCount) {
                seekBar.setProgress(progressCount);
            }
        });
    }




        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_USER_AVATAR) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                startCropImage(mFileTemp);
            } else if (requestCode == CAPTURE_IMAGE_USER_AVATAR) {
                startCropImage(mFileTemp);
            }  else if(requestCode == CROPED_IMAGE_USER_AVATAR){
                selectedImagePathUserAvatar = data.getStringExtra(CropImage.IMAGE_PATH);
                if (selectedImagePathUserAvatar == null) {
                    return;
                }
                imgUserImage.setImageBitmap(BitmapFactory.decodeFile(selectedImagePathUserAvatar));
                updateAvatar();
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }else{
            selectedImagePathUserAvatar="";
            selectedOrignalImagePathUserAvatar="";
        }

    }

    private void startCropImage(final File file) {
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
                            b= Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                        default:
                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                            break;
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }

                FileOutputStream out1=null;
                FileOutputStream out2=null;
                try {
                    if(mFileTemp.exists())
                        mFileTemp.delete();
                    if(mOrignalFileTemp.exists())
                        mOrignalFileTemp.delete();
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
                        mOrignalFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_ORIGNAL_PHOTO_FILE_NAME);
                    }
                    else {
                        mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
                        mOrignalFileTemp = new File(getFilesDir(), TEMP_ORIGNAL_PHOTO_FILE_NAME);
                    }
                    out1 = new FileOutputStream(mFileTemp);
                    b.compress(Bitmap.CompressFormat.JPEG, 90, out1);
                    out2 = new FileOutputStream(mOrignalFileTemp);
                    b.compress(Bitmap.CompressFormat.JPEG, 90, out2);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try{
                        out1.close();
                        out2.close();
                    } catch(Throwable ignore) {

                    }
                }

                selectedOrignalImagePathUserAvatar = mOrignalFileTemp.getAbsolutePath();

                Intent intent = new Intent(IjoomerIntafyProfileActivity.this, CropImage.class);
                intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
                intent.putExtra(CropImage.SCALE, true);

                intent.putExtra(CropImage.ASPECT_X, 1);
                intent.putExtra(CropImage.ASPECT_Y, 1);
                intent.putExtra(CropImage.OUTPUT_X, 150);
                intent.putExtra(CropImage.OUTPUT_Y, 150);

                startActivityForResult(intent, CROPED_IMAGE_USER_AVATAR);
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
            int inSampleSize = 1024;
            if (o.outHeight > inSampleSize || o.outWidth > inSampleSize) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(inSampleSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
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
    public void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /**
     * This method used to shown response message.
     * @param responseCode represented response code
     */
    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_my_profile), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

}
