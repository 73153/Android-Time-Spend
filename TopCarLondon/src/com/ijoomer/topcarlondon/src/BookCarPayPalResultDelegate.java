package com.ijoomer.topcarlondon.src;

import java.io.Serializable;

import com.paypal.android.MEP.PayPalResultDelegate;

public class BookCarPayPalResultDelegate implements PayPalResultDelegate, Serializable {
	private static final long serialVersionUID = 10001L;

	public void onPaymentSucceeded(String payKey, String paymentStatus) {

		BookCarMyBookingActivity.resultTitle = "SUCCESS";
		BookCarMyBookingActivity.resultInfo = "You have successfully completed your transaction.";
		BookCarMyBookingActivity.resultExtra = "Key: " + payKey;
	}

	public void onPaymentFailed(String paymentStatus, String correlationID, String payKey, String errorID, String errorMessage) {
		BookCarMyBookingActivity.resultTitle = "FAILURE";
		BookCarMyBookingActivity.resultInfo = errorMessage;
		BookCarMyBookingActivity.resultExtra = "Error ID: " + errorID + "\nCorrelation ID: " + correlationID + "\nPay Key: " + payKey;
	}

	public void onPaymentCanceled(String paymentStatus) {
		BookCarMyBookingActivity.resultTitle = "CANCELED";
		BookCarMyBookingActivity.resultInfo = "The transaction has been cancelled.";
		BookCarMyBookingActivity.resultExtra = "";
	}
}