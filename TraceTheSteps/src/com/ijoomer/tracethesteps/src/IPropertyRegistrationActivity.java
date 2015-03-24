package com.ijoomer.tracethesteps.src;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.ijoomer.common.classes.IjoomerMapAddress;
import com.ijoomer.common.classes.IjoomerRegistrationMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.custom.interfaces.CustomClickListner;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.oauth.IjoomerRegistration;
import com.ijoomer.weservice.IjoomerWebService;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep2Activity.
 * 
 * @author tasol
 * 
 */
public class IPropertyRegistrationActivity extends IjoomerRegistrationMaster {

	private LinearLayout lnr_form;
	private IjoomerEditText editMap;
	private IjoomerButton btnBack;
	private IjoomerButton btnSubmit;

	ArrayList<HashMap<String, String>> fields;
	ArrayList<HashMap<String, String>> groups;

	private final String TYPE = "type";
	private final String TEXT = "text";
	private final String PASSWORD = "password";
	private final String TEXTAREA = "textarea";
	private final String DATE = "date";
	private final String TIME = "time";
	private final String SELECT = "select";
	private final String MULTIPLESELECT = "multipleselect";
	private final String MAP = "map";
	private final String LABEL = "label";
	private final String VALUE = "value";
	private final String OPTIONS = "options";
	private final String NAME = "name";
	private final String REQUIRED = "required";
	final private int GET_ADDRESS_FROM_MAP = 1;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.ijoomer_registration_step2;
	}

	@Override
	public void initComponents() {
		lnr_form = (LinearLayout) findViewById(R.id.lnr_form);
		btnBack = (IjoomerButton) findViewById(R.id.btnBack);
		btnSubmit = (IjoomerButton) findViewById(R.id.btnSubmit);
	}

	private void getRegistrationField() {

		final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_register_newuser));
		new IjoomerRegistration(this).getNewUser(new WebCallListener() {

			@Override
			public void onProgressUpdate(int progressCount) {
				proSeekBar.setProgress(progressCount);
			}

			@Override
			public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				if (responseCode == 200) {
					createForm(data1);
					setEditable(true);
				} else {
					IjoomerUtilities.getCustomOkDialog(getString(R.string.dialog_loading_profile),
							getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
							new CustomAlertNeutral() {

								@Override
								public void NeutralMethod() {

								}
							});
				}
			}
		});

	}

	@Override
	public void prepareViews() {
		((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.header_registration));
		getRegistrationField();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == GET_ADDRESS_FROM_MAP) {
				editMap.setText(((HashMap<String, String>) data.getSerializableExtra("MAP_ADDRESSS_DATA")).get("address"));
			} else {
				super.onActivityResult(requestCode, resultCode, data);
			}
		}

	}

	@Override
	public void setActionListeners() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				hideSoftKeyboard();
				submitNewUser();

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * Class methods
	 */

	/**
	 * This method used to set editable view registration form.
	 * 
	 * @param isEditable
	 */
	@SuppressWarnings("unchecked")
	private void setEditable(boolean isEditable) {
		int size = lnr_form.getChildCount();
		if (isEditable) {
			for (int i = 0; i < size; i++) {

				View v = lnr_form.getChildAt(i);
				HashMap<String, String> row = (HashMap<String, String>) v.getTag();
				if (((LinearLayout) v.findViewById(R.id.lnrGgroup)).getVisibility() == View.VISIBLE) {

				} else {
					if (row.get(TYPE).equals(TEXT)) {
						((LinearLayout) v.findViewById(R.id.lnrEdit)).setVisibility(View.VISIBLE);
					} else if (row.get(TYPE).equals(TEXTAREA)) {
						((LinearLayout) v.findViewById(R.id.lnrEditArea)).setVisibility(View.VISIBLE);
					} else if (row.get(TYPE).equals(DATE)) {
						((LinearLayout) v.findViewById(R.id.lnrEditClickable)).setVisibility(View.VISIBLE);
					} else if (row.get(TYPE).equals(TIME)) {
						((LinearLayout) v.findViewById(R.id.lnrEditClickable)).setVisibility(View.VISIBLE);
					} else if (row.get(TYPE).equals(SELECT)) {
						((LinearLayout) v.findViewById(R.id.lnrSpin)).setVisibility(View.VISIBLE);
					} else if (row.get(TYPE).equals(MULTIPLESELECT)) {
						((LinearLayout) v.findViewById(R.id.lnrEditClickable)).setVisibility(View.VISIBLE);
					} else if (row.get(TYPE).equals(LABEL)) {
						((LinearLayout) v.findViewById(R.id.lnrLabel)).setVisibility(View.VISIBLE);
					}

					((LinearLayout) v.findViewById(R.id.lnrReadOnly)).setVisibility(View.GONE);
				}

			}
		} else {
			for (int i = 0; i < size; i++) {

				View v = lnr_form.getChildAt(i);
				HashMap<String, String> row = (HashMap<String, String>) v.getTag();
				if (((LinearLayout) v.findViewById(R.id.lnrGgroup)).getVisibility() == View.VISIBLE) {

				} else {
					if (row.get(TYPE).equals(TEXT)) {
						((LinearLayout) v.findViewById(R.id.lnrEdit)).setVisibility(View.GONE);
					} else if (row.get(TYPE).equals(TEXTAREA)) {
						((LinearLayout) v.findViewById(R.id.lnrEditArea)).setVisibility(View.GONE);
					} else if (row.get(TYPE).equals(DATE)) {
						((LinearLayout) v.findViewById(R.id.lnrEditClickable)).setVisibility(View.GONE);
					} else if (row.get(TYPE).equals(TIME)) {
						((LinearLayout) v.findViewById(R.id.lnrEditClickable)).setVisibility(View.GONE);
					} else if (row.get(TYPE).equals(SELECT)) {
						((LinearLayout) v.findViewById(R.id.lnrSpin)).setVisibility(View.GONE);
					} else if (row.get(TYPE).equals(MULTIPLESELECT)) {
						((LinearLayout) v.findViewById(R.id.lnrEditClickable)).setVisibility(View.GONE);
					} else if (row.get(TYPE).equals(LABEL)) {
						((LinearLayout) v.findViewById(R.id.lnrLabel)).setVisibility(View.GONE);
					}

					((LinearLayout) v.findViewById(R.id.lnrReadOnly)).setVisibility(View.VISIBLE);
				}

			}
		}

	}

	/**
	 * This method used to sumit registration form data.
	 */
	private void submitNewUser() {
		boolean validationFlag = true;
		ArrayList<HashMap<String, String>> signUpFields = new ArrayList<HashMap<String, String>>();
		int size = lnr_form.getChildCount();
		for (int i = 0; i < size; i++) {
			LinearLayout v = (LinearLayout) lnr_form.getChildAt(i);
			@SuppressWarnings("unchecked")
			HashMap<String, String> field = (HashMap<String, String>) v.getTag();

			IjoomerEditText edtValue = null;
			Spinner spnrValue = null;

			if (field != null) {
				if (field.get(TYPE).equals(TEXT)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEdit)).findViewById(R.id.txtValue);
				} else if (field.get(TYPE).equals(TEXTAREA)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditArea)).findViewById(R.id.txtValue);
				} else if (field.get(TYPE).equals(PASSWORD)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrPassword)).findViewById(R.id.txtValue);
				} else if (field.get(TYPE).equals(MAP)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditMap)).findViewById(R.id.txtValue);
				} else if (field.get("type").equals(LABEL)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrLabel)).findViewById(R.id.txtValue);
				} else if (field.get(TYPE).equals(DATE)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditClickable)).findViewById(R.id.txtValue);

					if (edtValue.getText().toString().trim().length() > 0) {
						if (!IjoomerUtilities.birthdateValidator(edtValue.getText().toString().trim())) {
							edtValue.setFocusable(true);
							edtValue.setError(getString(R.string.validation_invalid_birth_date));
							validationFlag = false;
						}
					}
				} else if (field.get(TYPE).equals(MULTIPLESELECT)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditClickable)).findViewById(R.id.txtValue);
				}
				if (field.get(TYPE).equals(TIME)) {
					edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditClickable)).findViewById(R.id.txtValue);
				}

				if (field.get(TYPE).equals(SELECT)) {
					spnrValue = (Spinner) ((LinearLayout) v.findViewById(R.id.lnrSpin)).findViewById(R.id.txtValue);
					field.put(VALUE, spnrValue.getSelectedItem().toString());
					signUpFields.add(field);
				} else if (field.get(TYPE).equals(PASSWORD) && field.get(NAME).equals("Retype Password")) {
					int len = lnr_form.getChildCount();
					for (int j = 0; j < len; j++) {
						LinearLayout view = (LinearLayout) lnr_form.getChildAt(i);
						@SuppressWarnings("unchecked")
						HashMap<String, String> row = (HashMap<String, String>) view.getTag();
						if (row.get(TYPE).equals(PASSWORD) && field.get(NAME).equals("Password")) {
							String password = ((IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrPassword)).findViewById(R.id.txtValue)).getText().toString();
							if (password.equals(edtValue.getText().toString())) {
								break;
							} else {
								edtValue.setError(getString(R.string.validation_password_not_match));
								validationFlag = false;
								break;
							}
						}
					}
				} else if (edtValue != null && edtValue.getText().toString().trim().length() <= 0 && (field.get(REQUIRED).equals("1"))) {
					edtValue.setError(getString(R.string.validation_value_required));
					validationFlag = false;
				} else {
					field.put(VALUE, edtValue.getText().toString().trim());
					signUpFields.add(field);
				}
			}
		}

		if (validationFlag) {
			final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_register_newuser));
			new IjoomerRegistration(this).submitNewUser(signUpFields, new WebCallListener() {

				@Override
				public void onProgressUpdate(int progressCount) {
					proSeekBar.setProgress(progressCount);
				}

				@Override
				public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
					if (responseCode == 200) {
						IjoomerUtilities.getCustomOkDialog(getString(R.string.dialog_loading_profile), getString(R.string.registration_successfully), getString(R.string.ok),
								R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

									@Override
									public void NeutralMethod() {

										Intent intent = new Intent("clearStackActivity");
										intent.setType("text/plain");
										sendBroadcast(intent);
										IjoomerWebService.cookies = null;

										loadNew(IjoomerLoginActivity.class, IPropertyRegistrationActivity.this, true);
										finish();
									}
								});

					} else {
						IjoomerUtilities.getCustomOkDialog(getString(R.string.dialog_loading_profile),
								getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
								new CustomAlertNeutral() {

									@Override
									public void NeutralMethod() {

									}
								});
					}
				}
			});
		}
	}

	/**
	 * This method used to create dynamic registration form.
	 */
	private void createForm(ArrayList<HashMap<String, String>> data) {
		LayoutInflater inflater = LayoutInflater.from(IPropertyRegistrationActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		params.topMargin = 10;

		fields = data;
		LinearLayout layout = null;
		int len = fields.size();
		for (int j = 0; j < len; j++) {
			final HashMap<String, String> field = fields.get(j);
			View fieldView = inflater.inflate(R.layout.iproperty_registration_dynamic_view_item, null);

			if (field.get(TYPE).equals(LABEL)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrLabel));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
			} else if (field.get(TYPE).equals(PASSWORD)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEdit));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				if (field.get(NAME).contains(getString(R.string.phone)) || field.get(NAME).contains(getString(R.string.year))) {
					edit.setInputType(InputType.TYPE_CLASS_NUMBER);
				} else if (field.get(NAME).contains(getString(R.string.website)) || field.get(NAME).contains(getString(R.string.email))) {
					edit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				}
			} else if (field.get(TYPE).equals(TEXT)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEdit));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				if (field.get(NAME).contains(getString(R.string.phone)) || field.get(NAME).contains(getString(R.string.year))) {
					edit.setInputType(InputType.TYPE_CLASS_NUMBER);
				} else if (field.get(NAME).contains(getString(R.string.website)) || field.get(NAME).contains(getString(R.string.email))) {
					edit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				}
			} else if (field.get(TYPE).equals(TEXTAREA)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditArea));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));

				if (field.get(VALUE).toString().trim().length() > 0) {
					edit.setText(field.get(VALUE));
				} else {
				}

			} else if (field.get(TYPE).equals(MAP)) {
				final IjoomerEditText edit;
				final ImageView imgMap;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditMap));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				imgMap = ((ImageView) layout.findViewById(R.id.imgMap));
				if (field.get(NAME).equalsIgnoreCase(getString(R.string.state))) {
					try {
						Address address = IjoomerUtilities.getAddressFromLatLong(0, 0);
						edit.setText(address.getAdminArea().replace(address.getCountryName() == null ? "" : address.getCountryName(), "")
								.replace(address.getPostalCode() == null ? "" : address.getPostalCode(), ""));
					} catch (Throwable e) {
						edit.setText("");
					}
				} else if (field.get(NAME).equalsIgnoreCase(getString(R.string.city_town))) {
					try {
						Address address = IjoomerUtilities.getAddressFromLatLong(0, 0);
						edit.setText(address.getSubAdminArea());
					} catch (Throwable e) {
						edit.setText("");
					}
				}

				imgMap.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						editMap = edit;
						Intent intent = new Intent(IPropertyRegistrationActivity.this, IjoomerMapAddress.class);
						startActivityForResult(intent, GET_ADDRESS_FROM_MAP);
					}
				});

			} else if (field.get(TYPE).equals(SELECT)) {
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrSpin));
				layout.setVisibility(View.VISIBLE);
				final Spinner spn;
				spn = ((Spinner) layout.findViewById(R.id.txtValue));
				spn.setAdapter(IjoomerUtilities.getSpinnerAdapter(field));
				if (field.get(NAME).equalsIgnoreCase(getString(R.string.country))) {

					try {
						Address address = IjoomerUtilities.getAddressFromLatLong(0, 0);
						String country = address.getCountryName();
						int selectedIndex = 0;
						JSONArray jsonArray = null;

						jsonArray = new JSONArray(field.get(OPTIONS));
						int optionSize = jsonArray.length();
						for (int k = 0; k < optionSize; k++) {
							JSONObject options = (JSONObject) jsonArray.get(k);

							if (options.getString(VALUE).equalsIgnoreCase(country)) {
								selectedIndex = k;
								break;
							}
						}
						spn.setSelection(selectedIndex);
					} catch (Throwable e) {
						e.printStackTrace();
						spn.setSelection(0);
					}

				}

			} else if (field.get(TYPE).equals(DATE)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						IjoomerUtilities.getDateDialog(((IjoomerEditText) v).getText().toString(), true, new CustomClickListner() {

							@Override
							public void onClick(String value) {
								((IjoomerEditText) v).setText(value);
								((IjoomerEditText) v).setError(null);
							}
						});

					}
				});

			} else if (field.get(TYPE).equals(TIME)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						IjoomerUtilities.getTimeDialog(((IjoomerEditText) v).getText().toString(), new CustomClickListner() {

							@Override
							public void onClick(String value) {
								((IjoomerEditText) v).setText(value);
								((IjoomerEditText) v).setError(null);
							}
						});

					}
				});

			} else if (field.get(TYPE).equals(MULTIPLESELECT)) {
				final IjoomerEditText edit;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				edit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						IjoomerUtilities.getMultiSelectionDialog(field.get(NAME), field.get(OPTIONS), ((IjoomerEditText) v).getText().toString(), new CustomClickListner() {

							@Override
							public void onClick(String value) {
								((IjoomerEditText) v).setText(value);
							}
						});

					}
				});
			}

			if (field.get(REQUIRED).equalsIgnoreCase("1")) {
				((IjoomerTextView) layout.findViewById(R.id.txtLable)).setText(field.get(NAME) + " *");
			} else {
				((IjoomerTextView) layout.findViewById(R.id.txtLable)).setText(field.get(NAME));
			}

			((LinearLayout) fieldView.findViewById(R.id.lnrEdit)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrEditArea)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrSpin)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrLabel)).setVisibility(View.GONE);

			layout = ((LinearLayout) fieldView.findViewById(R.id.lnrReadOnly));
			layout.setVisibility(View.VISIBLE);

			((IjoomerTextView) layout.findViewById(R.id.txtLable)).setText(field.get(NAME));
			((IjoomerEditText) layout.findViewById(R.id.txtValue)).setText(field.get(VALUE));
			fieldView.setTag(field);
			lnr_form.addView(fieldView, params);
		}
	}

}