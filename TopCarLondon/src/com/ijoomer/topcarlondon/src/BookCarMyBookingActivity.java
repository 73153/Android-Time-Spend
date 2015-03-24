package com.ijoomer.topcarlondon.src;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;
import com.smart.framework.CustomAlertNeutral;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarMyBookingActivity extends IjoomerBookSuperMaster {

	private LinearLayout lnrPickUpAddressPostCode;
	private LinearLayout lnrDropOffAddressPostCode;
	private LinearLayout lnrFlightNo;
	private LinearLayout lnrbtn;
	private IjoomerTextView txtJourneyDateTime;
	private IjoomerTextView txtJourneyDateTimeValue;
	private IjoomerTextView txtFlightNo;
	private IjoomerTextView txtFlightNoValue;
	private IjoomerTextView txtFirstName;
	private IjoomerTextView txtFirstNameValue;
	private IjoomerTextView txtLastName;
	private IjoomerTextView txtLastNameValue;
	private IjoomerTextView txtAddress;
	private IjoomerTextView txtAddressValue;
	private IjoomerTextView txtContactNo;
	private IjoomerTextView txtContactNoValue;
	private IjoomerTextView txtEmail;
	private IjoomerTextView txtEmailValue;

	private IjoomerTextView txtPickUpAddress;
	private IjoomerTextView txtPickUpAddressValue;
	private IjoomerTextView txtPickUpAddressPostCode;
	private IjoomerTextView txtPickUpAddressPostCodeValue;
	private IjoomerTextView txtDropOffAddress;
	private IjoomerTextView txtDropOffAddressValue;
	private IjoomerTextView txtDropOffAddressPostCode;
	private IjoomerTextView txtDropOffAddressPostCodeValue;
	private IjoomerTextView txtVehicle;
	private IjoomerTextView txtVehicleValue;
	private IjoomerTextView txtBabySeat;
	private IjoomerTextView txtBabySeatValue;
	private IjoomerTextView txtPassenger;
	private IjoomerTextView txtPassengerValue;
	private IjoomerTextView txtDistance;
	private IjoomerTextView txtDistanceValue;
	private IjoomerTextView txtCost;
	private IjoomerTextView txtCostValue;

	private IjoomerButton btnClose;

	private HashMap<String, String> IN_MYBOOKING_DATA;

	private CheckoutButton btnPayPal;

	private static final int server = PayPal.ENV_LIVE;
	private static final String appID = "APP-80W284485P519543T";
	private static final int request = 1;

	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;

	private ProgressBar pbrPayPalButton;
	public static String resultTitle;
	public static String resultInfo;
	public static String resultExtra;
	private PayPal payPal;
	
	private BookCarDataProvider provider;


	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_mybooking;
	}

	@Override
	public void initComponents() {

		lnrPickUpAddressPostCode = (LinearLayout) findViewById(R.id.lnrPickUpAddressPostCode);
		lnrDropOffAddressPostCode = (LinearLayout) findViewById(R.id.lnrDropOffAddressPostCode);
		lnrFlightNo = (LinearLayout) findViewById(R.id.lnrFlightNo);
		lnrbtn = (LinearLayout) findViewById(R.id.lnrbtn);
		
		txtJourneyDateTime = (IjoomerTextView) findViewById(R.id.txtJourneyDateTime);
		txtFlightNo = (IjoomerTextView) findViewById(R.id.txtFlightNo);
		txtFirstName = (IjoomerTextView) findViewById(R.id.txtFirstName);
		txtLastName = (IjoomerTextView) findViewById(R.id.txtLastName);
		txtContactNo = (IjoomerTextView) findViewById(R.id.txtContactNo);
		txtAddress = (IjoomerTextView) findViewById(R.id.txtAddress);
		txtEmail = (IjoomerTextView) findViewById(R.id.txtEmail);

		txtPickUpAddress = (IjoomerTextView) findViewById(R.id.txtPickUpAddress);
		txtPickUpAddressPostCode = (IjoomerTextView) findViewById(R.id.txtPickUpAddressPostCode);
		txtDropOffAddress = (IjoomerTextView) findViewById(R.id.txtDropOffAddress);
		txtDropOffAddressPostCode = (IjoomerTextView) findViewById(R.id.txtDropOffAddressPostCode);
		txtVehicle = (IjoomerTextView) findViewById(R.id.txtVehicle);
		txtBabySeat = (IjoomerTextView) findViewById(R.id.txtBabySeat);
		txtPassenger = (IjoomerTextView) findViewById(R.id.txtPassenger);
		txtDistance = (IjoomerTextView) findViewById(R.id.txtDistance);
		txtCost = (IjoomerTextView) findViewById(R.id.txtCost);
		
		txtJourneyDateTimeValue = (IjoomerTextView) findViewById(R.id.txtJourneyDateTimeValue);
		txtFlightNoValue = (IjoomerTextView) findViewById(R.id.txtFlightNoValue);
		txtFirstNameValue = (IjoomerTextView) findViewById(R.id.txtFirstNameValue);
		txtLastNameValue = (IjoomerTextView) findViewById(R.id.txtLastNameValue);
		txtContactNoValue = (IjoomerTextView) findViewById(R.id.txtContactNoValue);
		txtAddressValue = (IjoomerTextView) findViewById(R.id.txtAddressValue);
		txtEmailValue = (IjoomerTextView) findViewById(R.id.txtEmailValue);

		txtPickUpAddressValue = (IjoomerTextView) findViewById(R.id.txtPickUpAddressValue);
		txtPickUpAddressPostCodeValue = (IjoomerTextView) findViewById(R.id.txtPickUpAddressPostCodeValue);
		txtDropOffAddressValue = (IjoomerTextView) findViewById(R.id.txtDropOffAddressValue);
		txtDropOffAddressPostCodeValue = (IjoomerTextView) findViewById(R.id.txtDropOffAddressPostCodeValue);
		txtVehicleValue = (IjoomerTextView) findViewById(R.id.txtVehicleValue);
		txtBabySeatValue = (IjoomerTextView) findViewById(R.id.txtBabySeatValue);
		txtPassengerValue = (IjoomerTextView) findViewById(R.id.txtPassengerValue);
		txtDistanceValue = (IjoomerTextView) findViewById(R.id.txtDistanceValue);
		txtCostValue = (IjoomerTextView) findViewById(R.id.txtCostValue);
		btnClose = (IjoomerButton) findViewById(R.id.btnClose);
		pbrPayPalButton = (ProgressBar) findViewById(R.id.pbrPayPalButton);
		
		provider = new BookCarDataProvider(this);

	}

	@Override
	public void prepareViews() {
		
		txtJourneyDateTime.setTypeface(Typeface.DEFAULT_BOLD);
		txtFlightNo.setTypeface(Typeface.DEFAULT_BOLD);
		txtFirstName.setTypeface(Typeface.DEFAULT_BOLD);
		txtLastName.setTypeface(Typeface.DEFAULT_BOLD);
		txtContactNo.setTypeface(Typeface.DEFAULT_BOLD);
		txtAddress.setTypeface(Typeface.DEFAULT_BOLD);
		txtEmail.setTypeface(Typeface.DEFAULT_BOLD);
		txtPickUpAddress.setTypeface(Typeface.DEFAULT_BOLD);
		txtPickUpAddressPostCode.setTypeface(Typeface.DEFAULT_BOLD);
		txtDropOffAddress.setTypeface(Typeface.DEFAULT_BOLD);
		txtDropOffAddressPostCode.setTypeface(Typeface.DEFAULT_BOLD);
		txtVehicle.setTypeface(Typeface.DEFAULT_BOLD);
		txtBabySeat.setTypeface(Typeface.DEFAULT_BOLD);
		txtPassenger.setTypeface(Typeface.DEFAULT_BOLD);
		txtDistance.setTypeface(Typeface.DEFAULT_BOLD);
		txtCost.setTypeface(Typeface.DEFAULT_BOLD);
		
		txtDistance.setText(txtDistance.getText()+" : ");
		txtCost.setText(txtDistance.getText()+" : ");
		
		getIntentData();
		setBookNowData();

	}

	Handler hRefresh = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case INITIALIZE_SUCCESS:
				setupButtons();
				break;
			case INITIALIZE_FAILURE:
				showFailure();
				break;
			}
		}
	};

	public void setupButtons() {
		PayPal pp = PayPal.getInstance();
		btnPayPal = pp.getCheckoutButton(this, PayPal.BUTTON_152x33, CheckoutButton.TEXT_PAY);
		lnrbtn.addView(btnPayPal);
		btnPayPal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				PayPalPayment payment = exampleSimplePayment();
				Intent checkoutIntent = payPal.checkout(payment, BookCarMyBookingActivity.this, new BookCarPayPalResultDelegate());
				startActivityForResult(checkoutIntent, request);
			}
		});
		pbrPayPalButton.setVisibility(View.GONE);
	}

	public void showFailure() {
		ting("Could not initialize the PayPal library.");
		pbrPayPalButton.setVisibility(View.GONE);
	}

	private void initLibrary() {
		payPal = PayPal.getInstance();

		if (payPal == null) {

			payPal = PayPal.initWithAppID(this, appID, server);
			payPal.setLanguage("en_US"); // Sets the language for the library.
			payPal.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			// Set to true if the transaction will require shipping.
			payPal.setShippingEnabled(true);
			// Dynamic Amount Calculation allows you to set tax and shipping
			// amounts based on the user's shipping address. Shipping must be
			// enabled for Dynamic Amount Calculation. This also requires you to
			// create a class that implements PaymentAdjuster and Serializable.
			payPal.setDynamicAmountCalculationEnabled(false);

			// --
		}
	}

	@Override
	public void setActionListeners() {

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void getIntentData() {
		IN_MYBOOKING_DATA = (HashMap<String, String>) (getIntent().getSerializableExtra("IN_MYBOOKING_DATA") != null ? getIntent().getSerializableExtra("IN_MYBOOKING_DATA") : new HashMap<String, String>());
	}

	private void setBookNowData() {

		try {
			try {
				Calendar cal = IjoomerUtilities.getDateFromString(IN_MYBOOKING_DATA.get("journeyDate"));
				SimpleDateFormat format = new SimpleDateFormat("dd MMM,yyyy  HH:mm");
				txtJourneyDateTimeValue.setText(format.format(new Date(cal.getTimeInMillis())) + " Hrs");
			} catch (Exception e) {
				txtJourneyDateTimeValue.setText("");
				e.printStackTrace();
			}
			txtPickUpAddressValue.setText(IN_MYBOOKING_DATA.get("pickPoint"));
			if (IN_MYBOOKING_DATA.get("pickupCode").trim().length() > 0) {
				txtPickUpAddressPostCodeValue.setText(IN_MYBOOKING_DATA.get("pickupCode"));
			} else {
				lnrPickUpAddressPostCode.setVisibility(View.GONE);
			}
			txtDropOffAddressValue.setText(IN_MYBOOKING_DATA.get("dropPoint"));
			if (IN_MYBOOKING_DATA.get("dropoffCode").trim().length() > 0) {
				txtDropOffAddressPostCodeValue.setText(IN_MYBOOKING_DATA.get("dropoffCode"));
			} else {
				lnrDropOffAddressPostCode.setVisibility(View.GONE);
			}
			if (IN_MYBOOKING_DATA.get("flightNo").trim().length() > 0) {
				txtFlightNoValue.setText(IN_MYBOOKING_DATA.get("flightNo"));
			} else {
				lnrFlightNo.setVisibility(View.GONE);
			}
			txtVehicleValue.setText(IN_MYBOOKING_DATA.get("preferredVehicle"));
			txtBabySeatValue.setText(IN_MYBOOKING_DATA.get("extraOption1"));
			txtPassengerValue.setText(IN_MYBOOKING_DATA.get("passenger"));
			txtDistanceValue.setText(IN_MYBOOKING_DATA.get("distance"));
			txtCostValue.setText(IN_MYBOOKING_DATA.get("totcost"));
			txtFirstNameValue.setText(IN_MYBOOKING_DATA.get("firstName"));
			txtLastNameValue.setText(IN_MYBOOKING_DATA.get("lastName"));
			txtAddressValue.setText(IN_MYBOOKING_DATA.get("address"));
			txtContactNoValue.setText(IN_MYBOOKING_DATA.get("contactNo"));
			txtEmailValue.setText(IN_MYBOOKING_DATA.get("emailId"));

			if (IN_MYBOOKING_DATA.get("payPalId").trim().length() <= 0) {
				pbrPayPalButton.setVisibility(View.VISIBLE);
				Thread libraryInitializationThread = new Thread() {
					@Override
					public void run() {
						initLibrary();

						if (PayPal.getInstance().isLibraryInitialized()) {
							hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
						} else {
							hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
						}
					}
				};
				libraryInitializationThread.start();
			} else {
				btnClose.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PayPalPayment exampleSimplePayment() {
		PayPalPayment payment = new PayPalPayment();
		payment.setCurrencyType("GBP");
		payment.setRecipient(getString(R.string.pay_pal_id));
		payment.setSubtotal(new BigDecimal(IN_MYBOOKING_DATA.get("totcost").toString().replace("\u00a3", "0")));
		payment.setPaymentType(PayPal.PAYMENT_TYPE_SERVICE);
		PayPalInvoiceData invoice = new PayPalInvoiceData();
		invoice.setTax(new BigDecimal("0"));
		invoice.setShipping(new BigDecimal("0"));
		PayPalInvoiceItem item1 = new PayPalInvoiceItem();
		item1.setName("CarBooking");
		item1.setTotalPrice(new BigDecimal(IN_MYBOOKING_DATA.get("totcost").toString().replace("\u00a3", "0")));
		invoice.getInvoiceItems().add(item1);
		payment.setInvoiceData(invoice);
		payment.setMerchantName("TopCarLondon");
		payment.setIpnUrl(getString(R.string.domain_name)+"home/PaypalIPN?bcid="+IN_MYBOOKING_DATA.get("bcID").toString());

		return payment;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != request)
			return;

		if (resultTitle == "SUCCESS") {
			Intent in = new Intent();
			in.putExtra("payment", "paid");
			setResult(22, in);

			pbrPayPalButton.setVisibility(View.VISIBLE);
			IjoomerUtilities.getCustomOkDialog(getString(R.string.confirm_booking), getString(R.string.payment_paid), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
					if(provider.updatePaypalId(IN_MYBOOKING_DATA.get("bcID"), resultExtra)){
						finish();
					};
				}
			});
		} else if (resultTitle == "FAILURE") {
			Intent in = new Intent();
			in.putExtra("payment", "unpaid");
			setResult(22, in);
			IjoomerUtilities.getCustomOkDialog(getString(R.string.confirm_booking), getString(R.string.payment_unpiad), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
					finish();
				}
			});
		} else if (resultTitle == "CANCELED") {
			Intent in = new Intent();
			in.putExtra("payment", "unpaid");
			setResult(22, in);
			IjoomerUtilities.getCustomOkDialog(getString(R.string.confirm_booking), getString(R.string.payment_unpiad), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
					finish();
				}
			});
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent in = new Intent();
			in.putExtra("payment", "unpaid");
			setResult(1, in);
			IjoomerUtilities.getCustomOkDialog(getString(R.string.confirm_booking), getString(R.string.payment_unpiad), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
					finish();
				}
			});

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}