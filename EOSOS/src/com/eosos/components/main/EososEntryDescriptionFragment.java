package com.eosos.components.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.eosos.common.classes.IjoomerSuperMaster;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;

/**
 * This Fragment Contains All Method Related To IcmsArchivedArticlesFragment.
 * 
 * @author tasol
 * 
 */
public class EososEntryDescriptionFragment extends SmartFragment implements EososTagHolder {

	private EososDataProvider dataProvider;
	private ArrayList<HashMap<String, String>> data;
	IjoomerTextView txtDescription;

	public EososEntryDescriptionFragment(ArrayList<HashMap<String, String>> data) {
		this.data = data;
	}

	/**
	 * Overrides methods
	 */
	@Override
	public int setLayoutId() {
		return R.layout.eosos_entry_description_fragment;
	}

	@Override
	public void initComponents(View currentView) {
		dataProvider = new EososDataProvider(getActivity());
		txtDescription = (IjoomerTextView) currentView.findViewById(R.id.txtDescription);

	}

	@Override
	public void prepareViews(View currentView) {
		txtDescription.setText(data.get(0).get("description"));
		txtDescription.setMovementMethod(new ScrollingMovementMethod());

	}

	@Override
	public void setActionListeners(View currentView) {

	}

	@Override
	public View setLayoutView() {
		return null;
	}

}