package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

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
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.custom.interfaces.CustomClickListner;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.library.iproperty.IPropertySearchDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

public class IPropertySearchActivity extends IPropertyMasterActivity {
	private final String DATE = "date";
	private final int GET_ADDRESS_FROM_MAP = 1;
	private String IN_MENUID;
	private String ITEMID = "itemid";
	private final String LABEL = "label";
	private final String MAP = "map";
	private final String MULTIPLESELECT = "multipleselect";
	private final String NAME = "name";
	private final String OPTIONS = "options";
	private final String SELECT = "select";
	private final String TEXT = "text";
	private final String TEXTAREA = "textarea";
	private final String TIME = "time";
	private final String TYPE = "type";
	private final String VALUE = "value";
	private IjoomerButton btnSearch;
	private IjoomerButton btnMap;
	private IjoomerEditText editMap;
	private LinearLayout lnr_form;
	private IPropertySearchDataProvider provider;

	public void createSearchForm(ArrayList<HashMap<String, String>> searchField) {
		lnr_form.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		params.topMargin = 10;
		LinearLayout layout = null;
		for (final HashMap<String, String> field : searchField) {

			View fieldView = LayoutInflater.from(this).inflate(R.layout.iproperty_search_dynamic_view_item, null);

			if (field.get(TYPE).equals(LABEL)) {
				final IjoomerEditText edit;
				final IjoomerTextView txtLable;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrLabel));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
			} else if (field.get(TYPE).equals(TEXT)) {
				final IjoomerEditText edit;
				final IjoomerTextView txtLable;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEdit));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
				if (field.get(NAME).contains(getString(R.string.phone)) || field.get(NAME).contains(getString(R.string.year))) {
					edit.setInputType(InputType.TYPE_CLASS_NUMBER);
				} else if (field.get(NAME).contains(getString(R.string.website)) || field.get(NAME).contains(getString(R.string.email))) {
					edit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				}
			} else if (field.get(TYPE).equals(TEXTAREA)) {
				final IjoomerEditText edit;
				final IjoomerTextView txtLable;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditArea));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
			} else if (field.get(TYPE).equals(MAP)) {
				final IjoomerEditText edit;
				final IjoomerTextView txtLable;
				final ImageView imgMap;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditMap));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
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
						Intent intent = new Intent(IPropertySearchActivity.this, IjoomerMapAddress.class);
						startActivityForResult(intent, GET_ADDRESS_FROM_MAP);
					}
				});

			} else if (field.get(TYPE).equals(SELECT)) {
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrSpin));
				layout.setVisibility(View.VISIBLE);
				final Spinner spn;
				final IjoomerTextView txtLable;
				spn = ((Spinner) layout.findViewById(R.id.txtValue));
				spn.setAdapter(IjoomerUtilities.getSpinnerAdapter(field));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
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
				final IjoomerTextView txtLable;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
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
				final IjoomerTextView txtLable;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
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
				final IjoomerTextView txtLable;
				layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable));
				layout.setVisibility(View.VISIBLE);
				edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
				txtLable = ((IjoomerTextView) layout.findViewById(R.id.txtLable));
				txtLable.setText(field.get(NAME));
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

			((LinearLayout) fieldView.findViewById(R.id.lnrEdit)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrEditArea)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrSpin)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrEditClickable)).setVisibility(View.GONE);
			((LinearLayout) fieldView.findViewById(R.id.lnrLabel)).setVisibility(View.GONE);

			fieldView.setTag(field);
			lnr_form.addView(fieldView, params);

		}

	}

	private ArrayList<HashMap<String, String>> submitSearchField() {
		ArrayList<HashMap<String, String>> searchField = new ArrayList<HashMap<String, String>>();
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
					if (spnrValue.getSelectedItemPosition() != 0) {
						field.put(VALUE, spnrValue.getSelectedItem().toString());
					} else {
						field.put(VALUE, "");
					}
					searchField.add(field);
				} else {
					field.put(VALUE, edtValue.getText().toString().trim());
					searchField.add(field);
				}
			}
		}
		return searchField;
	}

	private void responseMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_steps), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
				getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && isFinish) {
							finish();
						}
					}
				});
	}

	private void setEditable(boolean isEditable) {
		int size = lnr_form.getChildCount();
		if (isEditable) {
			for (int i = 0; i < size; i++) {

				View v = lnr_form.getChildAt(i);
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) v.getTag();

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

			}
		} else {
			for (int i = 0; i < size; i++) {

				View v = lnr_form.getChildAt(i);
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) v.getTag();
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

			}

		}

	}

	public void initComponents() {
		lnr_form = ((LinearLayout) findViewById(R.id.lnr_form));
		btnSearch = ((IjoomerButton) findViewById(R.id.btnSearch));
		btnMap = ((IjoomerButton) findViewById(R.id.btnMap));
		provider = new IPropertySearchDataProvider(this);
		setEditable(true);
	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == RESULT_OK) {
			if (requestCode == GET_ADDRESS_FROM_MAP)
				editMap.setText(((HashMap<String, String>) intent.getSerializableExtra("MAP_ADDRESSS_DATA")).get("address"));
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	public void prepareViews() {
		try {
			IN_MENUID = new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMID) == null ? "0" : new JSONObject(getIntent().getStringExtra("IN_OBJ"))
					.getString(ITEMID);
		} catch (Throwable e) {
			e.printStackTrace();
			IN_MENUID = "0";
		}
		final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_search_property));
		provider.getSearchForm(IN_MENUID, new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				if (responseCode == 200) {
					if (data1 != null && data1.size() > 0) {
						createSearchForm(data1);
						setEditable(true);
					}
				} else {
					responseMessageHandler(responseCode, true);
				}
			}

			public void onProgressUpdate(int progressCount) {
				proSeekBar.setProgress(progressCount);
			}
		});
	}

	public void setActionListeners() {
		btnSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hideSoftKeyboard();
				try {
					loadNew(IPropertySearchResultListActivity.class, IPropertySearchActivity.this, false, "IN_OBJ",getIntent().getStringExtra("IN_OBJ"),"IN_MENUID", IN_MENUID, "IN_SEARCH_FIELD", submitSearchField());
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnMap.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hideSoftKeyboard();
				Address address = IjoomerUtilities.getAddressFromLatLong(0, 0);
				if(address!=null){
				try {
					loadNew(IPropertySearchResultListActivity.class, IPropertySearchActivity.this, false, "IN_OBJ",getIntent().getStringExtra("IN_OBJ"),"IN_MENUID", IN_MENUID, "IN_LATITUDE", String.valueOf(address.getLatitude()), "IN_LONGITUDE", String.valueOf(address.getLongitude()));
				} catch (Throwable e) {
					e.printStackTrace();
				}
				}else{
					ting(getString(R.string.lat_long_not_found));
				}
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_search;
	}
}