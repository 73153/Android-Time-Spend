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
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.cropimage.CropImage;
import com.ijoomer.custom.interfaces.SelectImageDialogListner;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.InternalStorageContentProvider;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyCircleDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.ijoomer.weservice.WebCallListenerWithCacheInfo;
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
import java.util.Iterator;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyCircleAddActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerEditText edtCircleTitle;
    private IjoomerTextView txtSelectUser;
    private RoundedImageView imgCircleAvatar;
    private ProgressBar pbrImageLoad;
    private ImageView imgCreate;
    private ImageView imgAddImage;
    private String selectedImagePathUserAvatar="";
    final private int PICK_IMAGE_USER_AVATAR = 1;
    final private int CAPTURE_IMAGE_USER_AVATAR = 2;
    private final int CROPED_IMAGE_USER_AVATAR = 4;
    public final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private File mFileTemp;
    private IntafyCircleDataProvider circleDataProvider;
    private String selectedUserIds;
    private String selectedUserName;
    private HashMap<String,String> selectedUserMap;
    private HashMap<String,String> newSelectedUserMap;
    private String selecteUserId="";
    private final int CIRCLE=3;
    private String IN_CIRCLE_ID;
    private String IN_TABLENAME;
    private AQuery aQuery;
    private IjoomerTextView headerText;
    private IjoomerTextView headerLeftText;
    private LinearLayout lnrHeaderLeft;
    Uri mImageCaptureUri;
    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_circle_add;
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
        return 0;
    }

    @Override
    public void initComponents() {

        edtCircleTitle = (IjoomerEditText)findViewById(R.id.edtCircleTitle);
        txtSelectUser = (IjoomerTextView)findViewById(R.id.txtSelectUser);
        imgCircleAvatar = (RoundedImageView)findViewById(R.id.imgCircleAvatar);
        pbrImageLoad = (ProgressBar)findViewById(R.id.pbrImageLoad);
        imgCreate = (ImageView)findViewById(R.id.imgCreate);
        imgAddImage = (ImageView)findViewById(R.id.imgAddImage);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        circleDataProvider = new IntafyCircleDataProvider(this);
        aQuery = new AQuery(this);
    }

    @Override
    public void prepareViews() {
        IN_CIRCLE_ID = getIntent().getStringExtra("IN_CIRCLE_ID")!=null ?getIntent().getStringExtra("IN_CIRCLE_ID"):"0";
        IN_TABLENAME = getIntent().getStringExtra("IN_TABLENAME")!=null ?getIntent().getStringExtra("IN_TABLENAME"):"";
        if(!IN_CIRCLE_ID.equals("0")){
            headerText.setText(getString(R.string.intafy_edit_circle));
            final String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
            final String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
            if(IN_TABLENAME.trim().length()>0){
                ArrayList<HashMap<String,String>> group = circleDataProvider.getCircleDetails(IN_TABLENAME,IN_CIRCLE_ID,networkId,connectedUserId);
                edtCircleTitle.setText(group.get(0).get(TITLE));
                aQuery.id(imgCircleAvatar).progress(pbrImageLoad).image(group.get(0).get(AVATAR),true,true,150,R.drawable.ic_launcher);
                if (selectedUserMap == null) {
                    selectedUserMap = new HashMap<String, String>();
                }
                String[] groupMemberIdNameArray = group.get(0).get("memberIds").split(",");
                for (String memberIdName : groupMemberIdNameArray){
                    String [] idArray = memberIdName.split(":");
                    if(idArray.length>1) {
                        String id = idArray[0];
                        String name = idArray[1];
                        selectedUserMap.put(id, name);
                    }
                }
                setIntialSelectedUser(selectedUserMap);
                imgCreate.setImageResource(getIcon(SAVEICON,false));
            }else{
                final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                circleDataProvider.getGroups("","CircleDateWise",false,false,"",networkId,connectedUserId,new WebCallListenerWithCacheInfo() {
                    @Override
                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit, boolean fromCache) {
                        if(!fromCache){
                            if(responseCode==200){
                                if(data1!=null && data1.size()>0){
                                    ArrayList<HashMap<String,String>> group = circleDataProvider.getCircleDetails(IN_TABLENAME,IN_CIRCLE_ID,networkId,connectedUserId);
                                    edtCircleTitle.setText(group.get(0).get(TITLE));
                                    aQuery.id(imgCircleAvatar).progress(pbrImageLoad).image(group.get(0).get(AVATAR),true,true,150,R.drawable.ic_launcher);
                                    if (selectedUserMap == null) {
                                        selectedUserMap = new HashMap<String, String>();
                                    }
                                    String[] groupMemberIdNameArray = group.get(0).get("memberIds").split(",");
                                    for (String memberIdName : groupMemberIdNameArray){
                                        String [] idArray = memberIdName.split(":");
                                        if(idArray.length>1) {
                                            String id = idArray[0];
                                            String name = idArray[1];
                                            selectedUserMap.put(id, name);
                                        }
                                    }
                                    setIntialSelectedUser(selectedUserMap);
                                    imgCreate.setImageResource(getIcon(SAVEICON,false));
                                }
                            }
                        }
                    }

                    @Override
                    public void onProgressUpdate(int progressCount) {
                        seekBar.setProgress(progressCount);
                    }
                });
            }

        }else{
            pbrImageLoad.setVisibility(View.GONE);
            headerText.setText(getString(R.string.intafy_create_circle));
            imgCircleAvatar.setImageResource(R.drawable.ijoomer_background);
            imgCreate.setImageResource(getIcon(ADDICON,false));
        }

        ((IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst)).setText(getString(R.string.intafy_back));

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        }
        else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    @Override
    public void setActionListeners() {
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
        txtSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUserMap == null) {
                    selectedUserMap = new HashMap<String, String>();
                }
                if(newSelectedUserMap==null){
                    newSelectedUserMap=new HashMap<String, String>();
                }
                try {
                    loadNewResult(IjoomerIntafyInviteUserForCircleActivity.class, IjoomerIntafyCircleAddActivity.this, CIRCLE, "IN_SELECTED_USER", selectedUserMap,"IN_NEW_SELECTED_USER",newSelectedUserMap,"IN_CIRCLE_ID",IN_CIRCLE_ID);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!IN_CIRCLE_ID.equals("0")){
                    imgCreate.setImageResource(getIcon(SAVEICON,true));
                }else{
                    imgCreate.setImageResource(getIcon(ADDICON,true));
                }
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(!IN_CIRCLE_ID.equals("0")){
                            imgCreate.setImageResource(getIcon(SAVEICON,false));
                        }else{
                            imgCreate.setImageResource(getIcon(ADDICON,false));
                        }
                        boolean isValidate =true;

                        if(edtCircleTitle.getText().toString().trim().length()<=0){
                            edtCircleTitle.setError(getString(R.string.intafy_validation_value_required));
                            isValidate=false;
                        }

                        if(isValidate) {
                            final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                            circleDataProvider.addOrEditCircle(IN_CIRCLE_ID, edtCircleTitle.getText().toString(), selectedImagePathUserAvatar,selectedUserIds, new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    if (responseCode == 200) {
                                        IjoomerApplicationConfiguration.setReloadRequired(true);
                                        finish();
                                    } else {
                                        if(responseCode==400){
                                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_circle),errorMessage, getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                @Override
                                                public void NeutralMethod() {

                                                }
                                            });
                                        }else{
                                            responseErrorMessageHandler(responseCode);
                                        }
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {
                                    seekBar.setProgress(progressCount);
                                }
                            });
                        }
                    }
                }, 1000);
            }
        });
        imgAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAddImage.setImageResource(R.drawable.plus_icon_en_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgAddImage.setImageResource(R.drawable.plus_icon_en);
                        selectPhotoPopup();
                    }
                }, 1000);
            }
        });
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_circle), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    private void selectPhotoPopup(){
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
        popup.showAtLocation(layout, Gravity.BOTTOM, 0,0);
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
                imgCircleAvatar.setImageBitmap(BitmapFactory.decodeFile(selectedImagePathUserAvatar));
            } else if(requestCode == CIRCLE) {
                setSelectedUser((HashMap<String, String>) data.getSerializableExtra("IN_SELECTED_USER") != null ? (HashMap<String, String>) data.getSerializableExtra("IN_SELECTED_USER") : new HashMap<String, String>());
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }else{
            if(IN_CIRCLE_ID.equals("0") && selectedImagePathUserAvatar==null || selectedImagePathUserAvatar.length()<=0) {
                imgCircleAvatar.setImageResource(R.drawable.ijoomer_background);
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
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


                Intent intent = new Intent(IjoomerIntafyCircleAddActivity.this, CropImage.class);
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


    public void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private void setSelectedUser(HashMap<String,String> selectedUsers){
        newSelectedUserMap = selectedUsers;
        selectedUserName="";
        selectedUserIds="";
        Iterator<String> itr = selectedUsers.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            boolean isNewUser = true;
            if(selecteUserId.trim().length()>0){
                String[] eventUsers = selecteUserId.split(",");
                for (String user :eventUsers){
                    if(user.split(":")[0].equals(key)){
                        isNewUser = false;
                        break;
                    }
                }
            }
            if(isNewUser) {
                selectedUserIds += key + ",";
            }
            selectedUserName+=newSelectedUserMap.get(key)+",";
        }
        if(selectedUserIds.length()>1) {
            selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
        }
        if(selectedUserName.length()>1) {
            selectedUserName = selectedUserName.substring(0, selectedUserName.length() - 1);
        }

        txtSelectUser.setText(selectedUserName);
        txtSelectUser.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setIntialSelectedUser(HashMap<String,String> selectedUsers){
        selectedUserMap = selectedUsers;
        selectedUserName="";
        selectedUserIds="";
        Iterator<String> itr = selectedUsers.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();

            selectedUserIds += key+",";
            selectedUserName+=selectedUsers.get(key)+",";
        }
        if(selectedUserIds.length()>1) {
            selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
            selecteUserId = selectedUserIds.substring(0, selectedUserIds.length() - 1);
        }
        if(selectedUserName.length()>1) {
            selectedUserName = selectedUserName.substring(0, selectedUserName.length() - 1);
        }

        txtSelectUser.setText(selectedUserName);
        txtSelectUser.setMovementMethod(new ScrollingMovementMethod());
    }

}
