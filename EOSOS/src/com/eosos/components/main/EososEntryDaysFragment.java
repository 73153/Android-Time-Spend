package com.eosos.components.main;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.smart.framework.SmartFragment;

import java.util.ArrayList;

/**
 * This Fragment Contains All Method Related To IcmsArchivedArticlesFragment.
 *
 * @author tasol
 *
 */
public class EososEntryDaysFragment extends SmartFragment implements EososTagHolder {

    private ArrayList<Boolean> data;
    IjoomerTextView txtFr, txtSa, txtSu, txtM, txtTu, txtWe, txtTh;
    private ImageView imgFr;
    private ImageView imgSa;
    private ImageView imgSu;
    private ImageView imgM;
    private ImageView imgTu;
    private ImageView imgWe;
    private ImageView imgTh;

    private AQuery aQuery;

    public EososEntryDaysFragment(ArrayList<Boolean> data) {
        this.data = data;
    }

    /**
     * Overrides methods
     */
    @Override
    public int setLayoutId() {
        return R.layout.eosos_entry_days_fragment;
    }

    @Override
    public void initComponents(View currentView) {
        txtFr = (IjoomerTextView) currentView.findViewById(R.id.txtFr);
        txtSa = (IjoomerTextView) currentView.findViewById(R.id.txtSa);
        txtSu = (IjoomerTextView) currentView.findViewById(R.id.txtSu);
        txtM = (IjoomerTextView) currentView.findViewById(R.id.txtM);
        txtTu = (IjoomerTextView) currentView.findViewById(R.id.txtTu);
        txtWe = (IjoomerTextView) currentView.findViewById(R.id.txtWe);
        txtTh = (IjoomerTextView) currentView.findViewById(R.id.txtTh);

        imgFr = (ImageView) currentView.findViewById(R.id.imgFr);
        imgSa = (ImageView) currentView.findViewById(R.id.imgSa);
        imgSu = (ImageView) currentView.findViewById(R.id.imgSu);
        imgM = (ImageView) currentView.findViewById(R.id.imgM);
        imgTu = (ImageView) currentView.findViewById(R.id.imgTu);
        imgWe = (ImageView) currentView.findViewById(R.id.imgWe);
        imgTh = (ImageView) currentView.findViewById(R.id.imgTh);

        aQuery = new AQuery(getActivity());
    }

    @Override
    public void prepareViews(View currentView) {
        if (data.get(0)) {
            txtM.setTextColor(Color.WHITE);
            aQuery.id(imgM).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }

        if (data.get(1)) {
            txtTu.setTextColor(Color.WHITE);
            aQuery.id(imgTu).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }

        if (data.get(2)) {
            txtWe.setTextColor(Color.WHITE);
            aQuery.id(imgWe).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }
        if (data.get(3)) {
            txtTh.setTextColor(Color.WHITE);
            aQuery.id(imgTh).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }
        if (data.get(4)) {
            txtFr.setTextColor(Color.WHITE);
            aQuery.id(imgFr).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }
        if (data.get(5)) {
            txtSa.setTextColor(Color.WHITE);
            aQuery.id(imgSa).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }
        if (data.get(6)) {
            txtSu.setTextColor(Color.WHITE);
            aQuery.id(imgSu).image(getActivity().getResources().getDrawable(R.drawable.circle_filled));
        }
    }

    @Override
    public void setActionListeners(View currentView) {

    }

    @Override
    public View setLayoutView() {
        return null;
    }

}