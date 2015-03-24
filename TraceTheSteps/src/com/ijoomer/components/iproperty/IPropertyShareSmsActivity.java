package com.ijoomer.components.iproperty;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;

import com.ijoomer.common.classes.IjoomerWebviewClient;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.tracethesteps.src.R;

public class IPropertyShareSmsActivity extends IPropertyMasterActivity {

	private String IN_PROPERTY_TITLE;
	private String IN_SHARE_LINK;
	private IjoomerButton btnSend;
	private IjoomerEditText edtMessage;
	private IjoomerEditText edtPhoneNo;
	private ImageView imgPickUpPhoneNo;
	private IjoomerTextView txtPropertyTitle;
	private IjoomerTextView txtShareLink;

	private String getPhoneNumber(Uri paramUri) {
		String id = "";
		String no="";
		Cursor cursor = getContentResolver().query(paramUri, null, null, null, null);
		
		while(cursor.moveToNext()){
			id = cursor.getString(cursor.getColumnIndex("_id"));
			if("1".equalsIgnoreCase(cursor.getString(cursor.getColumnIndex("has_phone_number")))){
				Cursor cursorNo = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id = " + id, null, null);
				while (cursorNo.moveToNext()) {
					if (cursorNo.getInt(cursorNo.getColumnIndex("data2")) == 2){
						no = no.concat(cursorNo.getString(cursorNo.getColumnIndex("data1")));
						break;
					}
				}
				cursorNo.close();
			}
		}
		cursor.close();
		return no;
	}

	private void sendSMS(String paramString1, String paramString2, String paramString3) {
		Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + paramString1));
		localIntent.putExtra("sms_body", paramString2 + "\n" + paramString3);
		startActivity(localIntent);
	}

	public void initComponents() {
		imgPickUpPhoneNo = ((ImageView) findViewById(R.id.imgPickUpPhoneNo));
		edtPhoneNo = ((IjoomerEditText) findViewById(R.id.edtPhoneNo));
		edtMessage = ((IjoomerEditText) findViewById(R.id.edtMessage));
		txtShareLink = ((IjoomerTextView) findViewById(R.id.txtShareLink));
		txtPropertyTitle = ((IjoomerTextView) findViewById(R.id.txtPropertyTitle));
		btnSend = ((IjoomerButton) findViewById(R.id.btnSend));
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent paramIntent) {
		super.onActivityResult(requestCode, resultCode, paramIntent);
		if (resultCode == RESULT_OK) {
			String str = getPhoneNumber(paramIntent.getData());
			if (str.trim().length() > 0) {
				edtPhoneNo.setText(str);
				edtPhoneNo.setSelection(edtPhoneNo.getText().length());
			}
		} else {
			ting("Phone Number Not Founded ...");
		}
	}

	public void prepareViews() {
		IN_SHARE_LINK = getIntent().getStringExtra("IN_SHARE_LINK") != null ? getIntent().getStringExtra("IN_SHARE_LINK"):"";
		IN_PROPERTY_TITLE = getIntent().getStringExtra("IN_PROPERTY_TITLE") != null ? getIntent().getStringExtra("IN_PROPERTY_TITLE"):"";
		txtShareLink.setText(IN_SHARE_LINK);
		txtShareLink.setMovementMethod(new ScrollingMovementMethod());
		if(IN_PROPERTY_TITLE.trim().length()>0){
			txtPropertyTitle.setText(IN_PROPERTY_TITLE);
		}else{
			txtPropertyTitle.setText(getString(R.string.iproperty_no_title));
		}
	}

	public void setActionListeners() {
		txtShareLink.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IjoomerWebviewClient.class, IPropertyShareSmsActivity.this, false, "url", IN_SHARE_LINK);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendSMS(edtPhoneNo.getText().toString(), edtMessage.getText().toString(), IN_SHARE_LINK);
			}
		});
		imgPickUpPhoneNo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent localIntent = new Intent("android.intent.action.PICK", ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(localIntent, 1);
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_share_sms;
	}
}