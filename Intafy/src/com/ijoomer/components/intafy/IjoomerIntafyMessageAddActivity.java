package com.ijoomer.components.intafy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.IjoomerVoiceAndTextMessager;
import com.ijoomer.customviews.InternalStorageContentProvider;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyMessageDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyMessageAddActivity extends IjoomerIntafyMaster {

    private IjoomerEditText edtMessage;
    private IjoomerTextView txtSelectUser;
    private IjoomerVoiceAndTextMessager voiceMessager;
    private ImageView imgSend;
    private IntafyMessageDataProvider messageDataProvider;
    private String selectedUserIds;
    private String selectedUserName;
    private HashMap<String, String> selectedUserMap;
    private final int USER = 3;
    private String IN_USER_ID;
    private String IN_USER_NAME;
    final private int PICK_IMAGE_USER_AVATAR = 1;
    final private int CAPTURE_IMAGE_USER_AVATAR = 2;
    private String selectedImagePathUserAvatar;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private File mFileTemp;
    public final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_message_add;
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
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
        edtMessage = (IjoomerEditText) findViewById(R.id.edtMessage);
        voiceMessager = (IjoomerVoiceAndTextMessager) findViewById(R.id.voiceMessager);
        txtSelectUser = (IjoomerTextView) findViewById(R.id.txtSelectUser);
        imgSend = (ImageView) findViewById(R.id.imgSend);
        messageDataProvider = new IntafyMessageDataProvider(this);
        IN_USER_ID = getIntent().getStringExtra("IN_USER_ID") != null ? getIntent().getStringExtra("IN_USER_ID") : "0";
        IN_USER_NAME = getIntent().getStringExtra("IN_USER_NAME") != null ? getIntent().getStringExtra("IN_USER_NAME") : "";
        if (!IN_USER_ID.equals("0")) {
            txtSelectUser.setText(IN_USER_NAME);
            selectedUserIds = IN_USER_ID;
        }

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    @Override
    public void prepareViews() {
        headerText.setText(getString(R.string.intafy_send_compose_message));
        headerLeftText.setText(getString(R.string.intafy_back));
        footerRightText.setText(getString(R.string.intafy_audio));
        footerLeftText.setText(getString(R.string.intafy_image));
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
                if (IN_USER_ID.equals("0")) {
                    if (selectedUserMap == null) {
                        selectedUserMap = new HashMap<String, String>();
                    }
                    try {
                        loadNewResult(IjoomerIntafySelectUserForMessageActivity.class, IjoomerIntafyMessageAddActivity.this, USER, "IN_SELECTED_USER", selectedUserMap);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
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

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgSend.setImageResource(getIcon(SENDICON, true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSend.setImageResource(getIcon(SENDICON, false));
                        boolean isValidate = true;

                        if (edtMessage.getText().toString().trim().length() <= 0) {
                            edtMessage.setError(getString(R.string.intafy_validation_value_required));
                            isValidate = false;
                        }

                        if (selectedUserIds.trim().length() <= 0) {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_messages), getString(R.string.intafy_send_message_validation), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                @Override
                                public void NeutralMethod() {

                                }
                            });
                            isValidate = false;
                        }
                        if (isValidate) {
                            final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                            messageDataProvider.sendMessage(selectedUserIds, edtMessage.getText().toString(), null, new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    if (responseCode == 200) {
                                        IjoomerApplicationConfiguration.setReloadRequired(true);
                                        finish();
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
                    }
                }, 1000);
            }
        });

        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (selectedUserIds.trim().length() > 0) {
                    footerRightText.setTextColor(getResources().getColor(R.color.blue));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            footerRightText.setTextColor(getResources().getColor(R.color.white));
                            if (voiceMessager.getVisibility() == View.GONE) {
                                edtMessage.setVisibility(View.GONE);
                                voiceMessager.setVisibility(View.VISIBLE);
                                footerRightText.setText(getResources().getString(R.string.intafy_text));
                            } else {
                                edtMessage.setVisibility(View.VISIBLE);
                                voiceMessager.setVisibility(View.GONE);
                                footerRightText.setText(getResources().getString(R.string.intafy_audio));
                            }
                        }
                    }, 1000);
                }
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (selectedUserIds.trim().length() > 0) {
                    footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            footerLeftText.setTextColor(getResources().getColor(R.color.white));
                            showUploadImagePopup(v);
                        }
                    }, 1000);
                }
            }
        });
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_messages), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedUserIds == null || selectedUserIds.trim().length() <= 0) {
            footerLeftText.setTextColor(getResources().getColor(R.color.txt_color));
            footerRightText.setTextColor(getResources().getColor(R.color.txt_color));
        } else {
            footerLeftText.setTextColor(getResources().getColor(R.color.white));
            footerRightText.setTextColor(getResources().getColor(R.color.white));
        }
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
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                uploadImage(mFileTemp);
//                uploadImage();
            } else if (requestCode == PICK_IMAGE_USER_AVATAR) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
//                selectedImagePathUserAvatar = getAbsolutePath(data.getData());
                uploadImage(mFileTemp);
            } else if (requestCode == USER) {
                setSelectedUser((HashMap<String, String>) data.getSerializableExtra("IN_SELECTED_USER") != null ? (HashMap<String, String>) data.getSerializableExtra("IN_SELECTED_USER") : new HashMap<String, String>());
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            selectedImagePathUserAvatar = "";
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void uploadAudio(String voiceFilePath) {
        final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        messageDataProvider.sendMessage(selectedUserIds, "", voiceFilePath, new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                if (responseCode == 200) {
                    IjoomerApplicationConfiguration.setReloadRequired(true);
                    finish();
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

    private void setSelectedUser(HashMap<String, String> selectedUsers) {
        selectedUserMap = selectedUsers;
        selectedUserName = "";
        selectedUserIds = "";
        Iterator<String> itr = selectedUsers.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            selectedUserIds += key + ",";
            selectedUserName += selectedUsers.get(key) + ",";
        }
        if (selectedUserIds.length() > 0) {
            selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
        }
        if (selectedUserName.length() > 0) {
            selectedUserName = selectedUserName.substring(0, selectedUserName.length() - 1);
        }
        txtSelectUser.setText(selectedUserName);
    }

    private void showUploadImagePopup(View view) {
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
        popup.showAtLocation(layout, Gravity.BOTTOM, 0, r.bottom - convertSizeToDeviceDependent(25));
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

                final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                messageDataProvider.sendMessage(selectedUserIds, "", selectedImagePathUserAvatar, new WebCallListener() {
                    @Override
                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                        if (responseCode == 200) {
                            IjoomerApplicationConfiguration.setReloadRequired(true);
                            finish();
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

    private Bitmap decodeFileFromPath(String path) {
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
