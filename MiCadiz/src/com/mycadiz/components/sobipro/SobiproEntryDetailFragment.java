package com.mycadiz.components.sobipro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidquery.AQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.common.configuration.IjoomerGlobalConfiguration;
import com.mycadiz.custom.interfaces.IjoomerSharedPreferences;
import com.mycadiz.customviews.IjoomerButton;
import com.mycadiz.customviews.IjoomerRadioButton;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.library.sobipro.SobiproCategoriesDataProvider;
import com.mycadiz.src.R;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartApplication;
import com.smart.framework.SmartFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.SupportMapFragment;

/**
 * This Fragment Contains All Method Related To SobiproEntryDetailFragment.
 *
 * @author tasol
 *
 */
public class SobiproEntryDetailFragment extends SmartFragment implements SobiproTagHolder,IjoomerSharedPreferences {
    private ArrayList<HashMap<String, String>> entryArrayList;
    private String entryID;
    private String IN_TABLE;
    private String IN_PRIVIOUS_ACTIVITY;
    private JSONArray imageList=new JSONArray();
    private AQuery androidAQuery;


    private LinearLayout lnrDirectoryDetailHeader;
    private ImageView imgEntry;
    private IjoomerTextView txtAddress;
    private IjoomerTextView txtTitle;
    private RadioGroup rdgTop;
    private IjoomerRadioButton rdbCall;
    private IjoomerRadioButton rdbEmail;
    private IjoomerRadioButton rdbWed;


    private LinearLayout lnrThingsToDoDetailHeader;
    private ImageView imgThingsToDoDetailHeader;
    private IjoomerTextView txtThingsToDoTitle;

    private LinearLayout lnrTownDetailHeader;
    private ImageView imgTownDetailHeader;
    private IjoomerTextView txtTownTitle;

    private LinearLayout lnrBeachDetailHeader;
    private ImageView imgBeachDetailHeader;
    private IjoomerTextView txtBeachTitle;


    private RadioGroup rdgBottomDirectory;
    private RadioButton rdbDirectoryDescription;
    private RadioButton rdbDirectoryMap;
    private RadioButton rdbDirectoryPhotos;

    private RadioGroup rdgBottomThingsToDo;
    private RadioButton rdbThingsToDoDescription;
    private RadioButton rdbThingsToDoPhotos;



    private RadioGroup rdgBottomTown;
    private RadioButton rdbTownDescription;
    private RadioButton rdbTownMap;
    private RadioButton rdbTownPhotos;



    private RadioGroup rdgBottomBeach;
    private RadioButton rdbBeachDescription;
    private RadioButton rdbBeachPhotos;
    private RadioButton rdbBeachAmenities;

    private LinearLayout lnrDescription;
    private IjoomerTextView txtDescription;

    private LinearLayout lnrInfo;
    private ImageView imgInfo;

    private LinearLayout lnrThingsToDoDescription;
    private IjoomerTextView txtThingsToDoDescription;

    private LinearLayout lnrTownDescription;
    private IjoomerTextView txtTownJerezAirport;
    private IjoomerTextView txtTownJerezAirportValue;
    private IjoomerTextView txtTownSevilleAirport;
    private IjoomerTextView txtTownSevilleAirportValue;
    private IjoomerTextView txtTownGibralterAirport;
    private IjoomerTextView txtTownGibralterAirportValue;
    private IjoomerTextView txtTownMalagaAirport;
    private IjoomerTextView txtTownMalagaAirportValue;
    private IjoomerTextView txtTownCadizCityCentre;
    private IjoomerTextView txtTownCadizCityCentreValue;
    private IjoomerTextView txtTownPublicTransport;
    private IjoomerTextView txtTownPublicTransportValue;
    private IjoomerTextView txtSituated;
    private IjoomerTextView txtSituatedValue;

    private IjoomerTextView txtTownDescription;

    private LinearLayout lnrBeachDescription;
    private IjoomerTextView txtBeachLengthOfBeach;
    private IjoomerTextView txtBeachLengthOfBeachValue;
    private IjoomerTextView txtBeachAverageWidth;
    private IjoomerTextView txtBeachAverageWidthValue;
    private IjoomerTextView txtBeachCompositions;
    private IjoomerTextView txtBeachCompositionsValue;
    private IjoomerTextView txtBeachSummersOccupancy;
    private IjoomerTextView txtBeachSummersOccupancyValue;
    private IjoomerTextView txtBeachLifeGuard;
    private IjoomerTextView txtBeachLifeGuardValue;
    private IjoomerTextView txtBeachPublicParking;
    private IjoomerTextView txtBeachPublicParkingValue;
    private IjoomerTextView txtBeachClosetHospital;
    private IjoomerTextView txtBeachClosetHospitalValue;
    private IjoomerTextView txtBeachPublicTransport;
    private IjoomerTextView txtBeachPublicTransportValue;
    private IjoomerTextView txtBeachDescription;

    private LinearLayout lnrBeachAmenities;
    private ImageView imgBeachAminitiesRedCross;
    private ImageView imgBeachAminitiesShowers;
    private ImageView imgBeachAminitiesSunbedUmbrellaHire;
    private ImageView imgBeachAminitiesTouriestInformationOffice;
    private ImageView imgBeachAminitiesLifeGuard;
    private ImageView imgBeachAminitiesFoodsAvilable;
    private ImageView imgBeachAminitiesDisableAccess;
    private ImageView imgBeachAminitiesPublicparking;
    private ImageView imgBeachAminitiesToilets;
    private ImageView imgBeachAminitiesWaterSportsEquipmentHire;
    private ImageView imgBeachAminitiesDrinkAvailable;
    private ImageView imgBeachAminitiesPromenade;

    private LinearLayout lnrThingsToDoInfo;
    private IjoomerTextView txtThingsToDoActivityLevel;
    private IjoomerTextView txtThingsToDoActivityLevelValue;
    private IjoomerTextView txtThingsToDoApproximateCost;
    private IjoomerTextView txtThingsToDoApproximateCostValue;
    private IjoomerTextView txtThingsToDoAgeRange;
    private IjoomerTextView txtThingsToDoAgeRangeValue;
    private IjoomerTextView txtThingsToDoDurationofActivity;
    private IjoomerTextView txtThingsToDoDurationofActivityValue;
    private IjoomerTextView txtThingsToDoSeason;
    private IjoomerTextView txtThingsToDoSeasonValue;
    private Button btnThingsToDoBusinesses;


    private LinearLayout lnrPhotos;
    private ImageView imgPhotosFirst;
    private ImageView imgPhotosTwo;
    private ImageView imgPhotosThree;
    private ImageView imgPhotosFour;
    private ImageView imgPhotosFive;
    private ImageView imgPhotosSix;

    private LinearLayout lnrMap;
    private FrameLayout frameMap;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private IjoomerButton btnDirection;


    private SobiproCategoriesDataProvider dataProvider;
    private String phoneNumber;
    private String emailAddress;
    private String webSiteUrl;
    private Timer t;
    String entriesLatitude="";
    String entriesLongitude="";
    String entriesTitle="";
    int heightOfDevice;

    /**
     * Constructor.
     *
     * @param entryID
     *            represented selected entry id.
     * @param IN_TABLE
     *            represented table name.
     */

    public SobiproEntryDetailFragment(String entryID, String IN_TABLE,String IN_PAGELAYOUT,String IN_PRIVIOUS_ACTIVITY) {
        this.entryID = entryID;
        this.IN_TABLE = IN_TABLE;
        this.IN_PRIVIOUS_ACTIVITY = IN_PRIVIOUS_ACTIVITY;
    }

    /**
     * Overrides methods.
     */

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_entry_detail_header;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public void initComponents(View currentView) {
        dataProvider = new SobiproCategoriesDataProvider(getActivity());
        androidAQuery = new AQuery(getActivity());
        lnrDirectoryDetailHeader = (LinearLayout) currentView.findViewById(R.id.lnrDirectoryDetailHeader);
        imgEntry = (ImageView) currentView.findViewById(R.id.imgEntry);
        txtAddress = (IjoomerTextView) currentView.findViewById(R.id.txtAddress);
        txtTitle = (IjoomerTextView) currentView.findViewById(R.id.txtTitle);
        rdgTop = (RadioGroup) currentView.findViewById(R.id.rdgTop);
        rdbCall = (IjoomerRadioButton) currentView.findViewById(R.id.rdbCall);
        rdbEmail = (IjoomerRadioButton) currentView.findViewById(R.id.rdbEmail);
        rdbWed = (IjoomerRadioButton) currentView.findViewById(R.id.rdbWed);
        heightOfDevice =((SmartActivity) getActivity()).getDeviceHeight();
        lnrThingsToDoDetailHeader = (LinearLayout) currentView.findViewById(R.id.lnrThingsToDoDetailHeader);
        txtThingsToDoTitle = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoTitle);
        imgThingsToDoDetailHeader = (ImageView) currentView.findViewById(R.id.imgThingsToDoDetailHeader);

        lnrTownDetailHeader = (LinearLayout) currentView.findViewById(R.id.lnrTownDetailHeader);
        txtTownTitle = (IjoomerTextView) currentView.findViewById(R.id.txtTownTitle);
        imgTownDetailHeader = (ImageView) currentView.findViewById(R.id.imgTownDetailHeader);

        lnrBeachDetailHeader = (LinearLayout) currentView.findViewById(R.id.lnrBeachDetailHeader);
        txtBeachTitle = (IjoomerTextView) currentView.findViewById(R.id.txtBeachTitle);
        imgBeachDetailHeader = (ImageView) currentView.findViewById(R.id.imgBeachDetailHeader);

        rdgBottomDirectory = (RadioGroup) currentView.findViewById(R.id.rdgBottomDirectory);
        rdbDirectoryDescription = (RadioButton) currentView.findViewById(R.id.rdbDirectoryDescription);
        rdbDirectoryMap = (RadioButton) currentView.findViewById(R.id.rdbDirectoryMap);
        rdbDirectoryPhotos = (RadioButton) currentView.findViewById(R.id.rdbDirectoryPhotos);
        rdgBottomThingsToDo = (RadioGroup) currentView.findViewById(R.id.rdgBottomThingsToDo);
        rdbThingsToDoDescription = (RadioButton) currentView.findViewById(R.id.rdbThingsToDoDescription);
        rdbThingsToDoPhotos = (RadioButton) currentView.findViewById(R.id.rdbThingsToDoPhotos);

        rdgBottomTown = (RadioGroup) currentView.findViewById(R.id.rdgBottomTown);
        rdbTownMap = (RadioButton) currentView.findViewById(R.id.rdbTownMap);
        rdbTownDescription = (RadioButton) currentView.findViewById(R.id.rdbTownDescription);
        rdbTownPhotos = (RadioButton) currentView.findViewById(R.id.rdbTownPhotos);
        rdgBottomBeach = (RadioGroup) currentView.findViewById(R.id.rdgBottomBeach);
        rdbBeachPhotos = (RadioButton) currentView.findViewById(R.id.rdbBeachPhotos);
        rdbBeachAmenities = (RadioButton) currentView.findViewById(R.id.rdbBeachAmenities);
        rdbBeachDescription = (RadioButton) currentView.findViewById(R.id.rdbBeachDescription);

        lnrDescription = (LinearLayout) currentView.findViewById(R.id.lnrDescription);
        txtDescription = (IjoomerTextView) currentView.findViewById(R.id.txtDescription);

        lnrThingsToDoDescription = (LinearLayout) currentView.findViewById(R.id.lnrThingsToDoDescription);
        txtThingsToDoDescription = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoDescription);

        lnrTownDescription = (LinearLayout) currentView.findViewById(R.id.lnrTownDescription);
        txtTownJerezAirport = (IjoomerTextView) currentView.findViewById(R.id.txtTownJerezAirport);
        txtTownJerezAirportValue = (IjoomerTextView) currentView.findViewById(R.id.txtTownJerezAirportValue);
        txtTownSevilleAirport = (IjoomerTextView) currentView.findViewById(R.id.txtTownSevilleAirport);
        txtTownSevilleAirportValue = (IjoomerTextView) currentView.findViewById(R.id.txtTownSevilleAirportValue);
        txtTownGibralterAirport = (IjoomerTextView) currentView.findViewById(R.id.txtTownGibralterAirport);
        txtTownGibralterAirportValue = (IjoomerTextView) currentView.findViewById(R.id.txtTownGibralterAirportValue);
        txtTownMalagaAirport = (IjoomerTextView) currentView.findViewById(R.id.txtTownMalagaAirport);
        txtTownMalagaAirportValue = (IjoomerTextView) currentView.findViewById(R.id.txtTownMalagaAirportValue);
        txtTownCadizCityCentre = (IjoomerTextView) currentView.findViewById(R.id.txtTownCadizCityCentre);
        txtTownCadizCityCentreValue = (IjoomerTextView) currentView.findViewById(R.id.txtTownCadizCityCentreValue);
        txtTownPublicTransport = (IjoomerTextView) currentView.findViewById(R.id.txtTownPublicTransport);
        txtTownPublicTransportValue = (IjoomerTextView) currentView.findViewById(R.id.txtTownPublicTransportValue);
        txtSituated = (IjoomerTextView) currentView.findViewById(R.id.txtSituated);
        txtSituatedValue = (IjoomerTextView) currentView.findViewById(R.id.txtSituatedValue);
        txtTownDescription = (IjoomerTextView) currentView.findViewById(R.id.txtTownDescription);

        lnrBeachDescription = (LinearLayout) currentView.findViewById(R.id.lnrBeachDescription);
        txtBeachLengthOfBeach = (IjoomerTextView) currentView.findViewById(R.id.txtBeachLengthOfBeach);
        txtBeachLengthOfBeachValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachLengthOfBeachValue);
        txtBeachAverageWidth = (IjoomerTextView) currentView.findViewById(R.id.txtBeachAverageWidth);
        txtBeachAverageWidthValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachAverageWidthValue);
        txtBeachCompositions = (IjoomerTextView) currentView.findViewById(R.id.txtBeachCompositions);
        txtBeachCompositionsValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachCompositionsValue);
        txtBeachSummersOccupancy = (IjoomerTextView) currentView.findViewById(R.id.txtBeachSummersOccupancy);
        txtBeachSummersOccupancyValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachSummersOccupancyValue);
        txtBeachLifeGuard = (IjoomerTextView) currentView.findViewById(R.id.txtBeachLifeGuard);
        txtBeachLifeGuardValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachLifeGuardValue);
        txtBeachPublicParking = (IjoomerTextView) currentView.findViewById(R.id.txtBeachPublicParking);
        txtBeachPublicParkingValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachPublicParkingValue);
        txtBeachPublicTransport = (IjoomerTextView) currentView.findViewById(R.id.txtBeachPublicTransport);
        txtBeachPublicTransportValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachPublicTransportValue);
        txtBeachClosetHospital = (IjoomerTextView) currentView.findViewById(R.id.txtBeachClosetHospital);
        txtBeachClosetHospitalValue = (IjoomerTextView) currentView.findViewById(R.id.txtBeachClosetHospitalValue);
        txtBeachDescription = (IjoomerTextView) currentView.findViewById(R.id.txtBeachDescription);

        lnrBeachAmenities = (LinearLayout) currentView.findViewById(R.id.lnrBeachAmenities);
        imgBeachAminitiesRedCross = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesRedCross);
        imgBeachAminitiesShowers = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesShowers);
        imgBeachAminitiesSunbedUmbrellaHire = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesSunbedUmbrellaHire);
        imgBeachAminitiesTouriestInformationOffice = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesTouriestInformationOffice);
        imgBeachAminitiesLifeGuard = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesLifeGuard);
        imgBeachAminitiesFoodsAvilable = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesFoodsAvilable);
        imgBeachAminitiesDisableAccess = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesDisableAccess);
        imgBeachAminitiesPublicparking = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesPublicparking);
        imgBeachAminitiesToilets = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesToilets);
        imgBeachAminitiesPromenade = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesPromenade);
        imgBeachAminitiesDrinkAvailable = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesDrinkAvailable);
        imgBeachAminitiesWaterSportsEquipmentHire = (ImageView) currentView.findViewById(R.id.imgBeachAminitiesWaterSportsEquipmentHire);

        lnrThingsToDoInfo = (LinearLayout) currentView.findViewById(R.id.lnrThingsToDoInfo);
        txtThingsToDoActivityLevel = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoActivityLevel);
        txtThingsToDoActivityLevelValue = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoActivityLevelValue);
        txtThingsToDoAgeRange = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoAgeRange);
        txtThingsToDoAgeRangeValue = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoAgeRangeValue);
        txtThingsToDoApproximateCost = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoApproximateCost);
        txtThingsToDoApproximateCostValue = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoApproximateCostValue);
        txtThingsToDoDurationofActivity = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoDurationofActivity);
        txtThingsToDoDurationofActivityValue = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoDurationofActivityValue);
        txtThingsToDoSeason = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoSeason);
        txtThingsToDoSeasonValue = (IjoomerTextView) currentView.findViewById(R.id.txtThingsToDoSeasonValue);
        btnThingsToDoBusinesses  = (Button) currentView.findViewById(R.id.btnThingsToDoBusinesses);

        lnrInfo = (LinearLayout) currentView.findViewById(R.id.lnrInfo);
        imgInfo = (ImageView) currentView.findViewById(R.id.imgInfo);


        lnrPhotos = (LinearLayout) currentView.findViewById(R.id.lnrPhotos);
        imgPhotosFirst=(ImageView) currentView.findViewById(R.id.imgPhotosFirst);
        imgPhotosTwo=(ImageView) currentView.findViewById(R.id.imgPhotosTwo);
        imgPhotosThree=(ImageView) currentView.findViewById(R.id.imgPhotosThree);
        imgPhotosFour=(ImageView) currentView.findViewById(R.id.imgPhotosFour);
        imgPhotosFive=(ImageView) currentView.findViewById(R.id.imgPhotosFive);
        imgPhotosSix=(ImageView) currentView.findViewById(R.id.imgPhotosSix);

        lnrMap = (LinearLayout) currentView.findViewById(R.id.lnrMap);
        frameMap = (FrameLayout) currentView.findViewById(R.id.frameMap);
        mapFragment = new SupportMapFragment();
        addFragment(lnrMap.getId(), mapFragment);
        btnDirection=(IjoomerButton) currentView.findViewById(R.id.btnDirection);
    }

    @Override
    public void prepareViews(View currentView) {
        entryArrayList = dataProvider.getEntriesFromCache(IN_TABLE,entryID);
        prepareHeader();
    }

    @Override
    public void onResume() {
        rdbCall.setChecked(false);
        rdbWed.setChecked(false);
        rdbEmail.setChecked(false);
        super.onResume();
    }

    @Override
    public void setActionListeners(View currentView) {

        btnThingsToDoBusinesses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String,String>> hiddenMenu = IjoomerGlobalConfiguration.getHiddenMenu(getActivity(), SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE, ""));
                try{
                    JSONArray menuArray = new JSONArray(hiddenMenu.get(0).get("menuitem"));
                    for(int i=0;i<menuArray.length();i++){
                        JSONObject obj = menuArray.getJSONObject(i);
                        if(obj.getString("itemid").equals(v.getTag().toString())) {
                            ((SmartActivity)getActivity()).loadNew(SobiproSectionCategoryActivity.class,getActivity(),false,"IN_OBJ",obj.toString(),"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                            break;
                        }

                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

        btnDirection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (((SmartActivity) getActivity()).getLatitude() != null && ((SmartActivity) getActivity()).getLatitude().length() > 0) {

                        String uri = "http://maps.google.com/maps?f=d&hl=es&saddr="+((SmartActivity)getActivity()).getLatitude()+","+((SmartActivity)getActivity()).getLongitude()+"&daddr="+entryArrayList.get(0).get(LATITUDE)+","+entryArrayList.get(0).get(LONGITUDE);
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        rdgBottomDirectory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lnrDescription.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);
                switch (i){
                    case R.id.rdbDirectoryDescription:
                        lnrDescription.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbDirectoryMap:
                        frameMap.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbDirectoryPhotos:
                        lnrPhotos.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbDirectoryInfo:
                        lnrInfo.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        rdgBottomThingsToDo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lnrDescription.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);
                switch (i){
                    case R.id.rdbThingsToDoDescription:
                        lnrThingsToDoDescription.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbThingsToDoPhotos:
                        lnrPhotos.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbThingsToDoInfo:
                        lnrThingsToDoInfo.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });


        rdgBottomTown.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lnrDescription.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);
                switch (i){
                    case R.id.rdbTownDescription:
                        lnrTownDescription.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbTownMap:
                        frameMap.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbTownPhotos:
                        lnrPhotos.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });


        rdgBottomBeach.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lnrDescription.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);
                switch (i){
                    case R.id.rdbBeachDescription:
                        lnrBeachDescription.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbBeachAmenities:
                        lnrBeachAmenities.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbBeachMap:
                        frameMap.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbBeachPhotos:
                        lnrPhotos.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        rdgTop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdbCall:
                        IjoomerUtilities.getCustomConfirmDialog(getString(R.string.sobipro_event_entries_detail), getString(R.string.sobipro_call_this_number), getString(R.string.yes), getString(R.string.no), new CustomAlertMagnatic() {
                            @Override
                            public void PositiveMethod() {
                                if (phoneNumber != null && phoneNumber.length() > 0) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                                    startActivity(intent);
                                } else {
                                    ((SmartActivity) getActivity()).ting(getString(R.string.sobipro_not_available_phone));
                                }
                            }

                            @Override
                            public void NegativeMethod() {

                            }
                        });
                        break;
                    case R.id.rdbEmail:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress.replace("mailto:","")});
                        intent.putExtra(Intent.EXTRA_SUBJECT,"");
                        intent.putExtra(Intent.EXTRA_TEXT,"");
                        try {
                            startActivity(Intent.createChooser(intent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            ((SmartActivity)getActivity()).ting(getString(R.string.share_email_no_client));
                        }
                        break;
                    case R.id.rdbWed:
                        Uri uri = Uri.parse(webSiteUrl);
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(webIntent);
                        break;

                }
            }
        });

    }


    /**
     * Class methods.
     */

    /**
     * This method is used to handled and displayed some details of entry which
     * is displayed and managed in listview header.
     */

    public void prepareHeader() {
        try {
            if(entryArrayList.get(0).get(SECTION).equals("Directory")){
                lnrDirectoryDetailHeader.setVisibility(View.VISIBLE);
                lnrTownDetailHeader.setVisibility(View.GONE);
                lnrThingsToDoDetailHeader.setVisibility(View.GONE);
                lnrBeachDetailHeader.setVisibility(View.GONE);
                entriesLatitude = entryArrayList.get(0).get(LATITUDE);
                entriesLongitude = entryArrayList.get(0).get(LONGITUDE);
                entriesTitle = entryArrayList.get(0).get(TITLE);

                rdgBottomDirectory.setVisibility(View.VISIBLE);
                rdgBottomThingsToDo.setVisibility(View.GONE);
                rdgBottomTown.setVisibility(View.GONE);
                rdgBottomBeach.setVisibility(View.GONE);
                lnrDescription.setVisibility(View.VISIBLE);
                lnrPhotos.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);

                txtTitle.setText(entriesTitle);

                JSONArray fields = new JSONArray(entryArrayList.get(0).get(FIELD));
                for (int i=0;i<fields.length();i++){
                    JSONObject field = fields.getJSONObject(i);

                    if (field.getString(LABELID).equalsIgnoreCase(FIELDADDRESS)) {
                        txtAddress.setText(field.getString(VALUE));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPOSTCODE)){
                        txtAddress.setText(txtAddress.getText()+" "+field.getString(VALUE));
                    }else if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO)) {
                        if(field.getString(VALUE).trim().length()<=0){
                            androidAQuery.id(imgEntry).image(getResources().getDrawable(R.drawable.sobipro_default_image));
                        }else if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                            imgEntry.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                        }else{
                            androidAQuery.id(imgEntry).image(field.getString(VALUE),true,true,0,R.drawable.sobipro_default_image);
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDTELEPHONENUMBER)){
                        if(field.getString(VALUE).trim().length()>0){
                            phoneNumber = field.getString(VALUE);
                            rdbCall.setVisibility(View.VISIBLE);
                        }else{
                            rdbCall.setVisibility(View.GONE);
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDEMAILADDRESS)){
                        if(field.getString(VALUE).trim().length()>0){
                            emailAddress = field.getString(VALUE);
                            rdbEmail.setVisibility(View.VISIBLE);
                        }else{
                            rdbEmail.setVisibility(View.GONE);
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDWEBSITE)){
                        if(field.getString(VALUE).trim().length()>0){
                            webSiteUrl = field.getString(VALUE);
                            rdbWed.setVisibility(View.VISIBLE);
                        }else{
                            rdbWed.setVisibility(View.GONE);
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGE1)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFirst.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFirst).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFirst.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",0,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGE2)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosTwo.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosTwo).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosTwo.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",1,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGE3)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosThree.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosThree).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosThree.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",2,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGE4)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFour.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFour).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFour.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",3,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGE5)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFive.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFive).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFive.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",4,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGE6)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosSix.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosSix).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosSix.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",5,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDENGLISHDESCRIPTION) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSPANISHDESCRIPTION) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDFILE)){
                        if(field.getString(VALUE).trim().length()>0) {
                            imgInfo.setTag(field.getString(VALUE));
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgInfo.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgInfo).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgInfo.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        JSONArray imgList = new JSONArray();
                                        JSONObject image = new JSONObject();
                                        image.put("image", view.getTag().toString());
                                        imgList.put(image);
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imgList.toString(), "IN_INDEX", 0, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
                if(txtDescription.getText().toString().trim().length()<=0){
                    rdbDirectoryDescription.setVisibility(View.GONE);
                    lnrDescription.setVisibility(View.GONE);
                    frameMap.setVisibility(View.VISIBLE);
                    rdbDirectoryMap.setChecked(true);
                }
                if(imageList.length()>0){
                    rdbDirectoryPhotos.setVisibility(View.VISIBLE);
                }else{
                    rdbDirectoryPhotos.setVisibility(View.GONE);
                }
            }else if(entryArrayList.get(0).get(SECTION).equals("Things To Do")){
                lnrDirectoryDetailHeader.setVisibility(View.GONE);
                lnrTownDetailHeader.setVisibility(View.GONE);
                lnrThingsToDoDetailHeader.setVisibility(View.VISIBLE);
                lnrBeachDetailHeader.setVisibility(View.GONE);
                entriesLatitude = entryArrayList.get(0).get(LATITUDE);
                entriesLongitude = entryArrayList.get(0).get(LONGITUDE);
                if(getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                    entriesTitle = entryArrayList.get(0).get(TITLESPANISH);
                }else{
                    entriesTitle = entryArrayList.get(0).get(TITLE);
                }

                rdgBottomDirectory.setVisibility(View.GONE);
                rdgBottomThingsToDo.setVisibility(View.VISIBLE);
                rdgBottomTown.setVisibility(View.GONE);
                rdgBottomBeach.setVisibility(View.GONE);
                lnrDescription.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.VISIBLE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);

                txtThingsToDoTitle.setText(entriesTitle);
                JSONArray fields = new JSONArray(entryArrayList.get(0).get(FIELD));
                for (int i=0;i<fields.length();i++){
                    JSONObject field = fields.getJSONObject(i);

                    if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO) && field.getString(VALUE).trim().length()>0) {
                        if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                            imgThingsToDoDetailHeader.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            txtThingsToDoTitle.setBackgroundColor(getResources().getColor(R.color.sobipro_blue_opac));
                        }else{
                            androidAQuery.id(imgThingsToDoDetailHeader).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),R.drawable.sobipro_default_image);
                            txtThingsToDoTitle.setBackgroundColor(getResources().getColor(R.color.sobipro_blue_opac));
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEONE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFirst.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFirst).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFirst.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 0, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGETWO)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosTwo.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosTwo).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosTwo.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 1, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGETHREE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosThree.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosThree).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosThree.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 2, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEFOUR)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosThree.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFour).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFour.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",3,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEFIVE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFive.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFive).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFive.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",4,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGESIX)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosSix.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosSix).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosSix.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",5,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDENGLISHDESCRIPTION) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtThingsToDoDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSPANISHDESCRIPTION) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtThingsToDoDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDNAMESPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtThingsToDoTitle.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDACTIVITYLEVEL) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtThingsToDoActivityLevel.setText(field.getString(CAPTION));
                        txtThingsToDoActivityLevelValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDACTIVITYLEVELSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es") ){
                        txtThingsToDoActivityLevel.setText(field.getString(CAPTION));
                        txtThingsToDoActivityLevelValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSEASON) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en") ){
                        txtThingsToDoSeason.setText(field.getString(CAPTION));
                        txtThingsToDoSeasonValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSEASONSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es") ){
                        txtThingsToDoSeason.setText(field.getString(CAPTION));
                        txtThingsToDoSeasonValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDURATIONOFACTIVITY) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtThingsToDoDurationofActivity.setText(field.getString(CAPTION));
                        txtThingsToDoDurationofActivityValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDURATIONOFACTIVITYSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtThingsToDoDurationofActivity.setText(field.getString(CAPTION));
                        txtThingsToDoDurationofActivityValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAGERANGE) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtThingsToDoAgeRange.setText(field.getString(CAPTION));
                        txtThingsToDoAgeRangeValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAGERANGESPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtThingsToDoAgeRange.setText(field.getString(CAPTION));
                        txtThingsToDoAgeRangeValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAPPROXIMATECOST) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtThingsToDoApproximateCost.setText(field.getString(CAPTION));
                        txtThingsToDoApproximateCostValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAPPROXIMATECOSTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtThingsToDoApproximateCost.setText(field.getString(CAPTION));
                        txtThingsToDoApproximateCostValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELD_HIDDENMENUID)){
                        if(field.getString(VALUE).trim().length()>0){
                            btnThingsToDoBusinesses.setTag(field.getString(VALUE));
                            btnThingsToDoBusinesses.setVisibility(View.VISIBLE);
                        }else{
                            btnThingsToDoBusinesses.setVisibility(View.GONE);
                        }
                    }
                }

                if(txtThingsToDoDescription.getText().toString().trim().length()<=0){
                    rdbThingsToDoDescription.setVisibility(View.GONE);
                    lnrThingsToDoDescription.setVisibility(View.GONE);
                    rdbThingsToDoPhotos.setChecked(true);
                    lnrPhotos.setVisibility(View.VISIBLE);
                }
                if(imageList.length()>0){
                    rdbThingsToDoPhotos.setVisibility(View.VISIBLE);
                }else{
                    rdbThingsToDoPhotos.setVisibility(View.GONE);
                    lnrPhotos.setVisibility(View.VISIBLE);
                }
            }else if(entryArrayList.get(0).get(SECTION).equals("Towns & Villages")){
                lnrDirectoryDetailHeader.setVisibility(View.GONE);
                lnrTownDetailHeader.setVisibility(View.VISIBLE);
                lnrThingsToDoDetailHeader.setVisibility(View.GONE);
                lnrBeachDetailHeader.setVisibility(View.GONE);
                entriesLatitude = entryArrayList.get(0).get(LATITUDE);
                entriesLongitude = entryArrayList.get(0).get(LONGITUDE);
                entriesTitle = entryArrayList.get(0).get(TITLE);

                rdgBottomDirectory.setVisibility(View.GONE);
                rdgBottomThingsToDo.setVisibility(View.GONE);
                rdgBottomTown.setVisibility(View.VISIBLE);
                rdgBottomBeach.setVisibility(View.GONE);
                lnrDescription.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.VISIBLE);
                lnrBeachDescription.setVisibility(View.GONE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);
                txtTownTitle.setText(entriesTitle);
                JSONArray fields = new JSONArray(entryArrayList.get(0).get(FIELD));
                for (int i=0;i<fields.length();i++){
                    JSONObject field = fields.getJSONObject(i);

                    if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO) && field.getString(VALUE).trim().length()>0) {
                        if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                            imgTownDetailHeader.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            txtTownTitle.setBackgroundColor(getResources().getColor(R.color.sobipro_blue_opac));
                        }else{
                            androidAQuery.id(imgTownDetailHeader).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),R.drawable.sobipro_default_image);
                            txtTownTitle.setBackgroundColor(getResources().getColor(R.color.sobipro_blue_opac));
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEONE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFirst.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFirst).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFirst.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 0, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGETWO)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosTwo.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosTwo).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosTwo.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 1, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGETHREE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosThree.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosThree).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosThree.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 2, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEFOUR)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFour.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFour).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFour.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",3,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEFIVE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFive.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFive).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFive.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",4,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGESIX)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosSix.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosSix).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosSix.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",5,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDESCRIPTIONENGLISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en") ){
                        txtTownDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDESCRIPTIONSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es") ){
                        txtTownDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMJEREZAIRPORT) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en") ){
                        txtTownJerezAirport.setText(field.getString(CAPTION));
                        txtTownJerezAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMSEVILLEAIRPORT) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtTownSevilleAirport.setText(field.getString(CAPTION));
                        txtTownSevilleAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMGIBRALTERAIRPORT) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtTownGibralterAirport.setText(field.getString(CAPTION));
                        txtTownGibralterAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMMALAGAAIRPORT) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtTownMalagaAirport.setText(field.getString(CAPTION));
                        txtTownMalagaAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMCADIZCITYCENTRE) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtTownCadizCityCentre.setText(field.getString(CAPTION));
                        txtTownCadizCityCentreValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMJEREZAIRPORTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtTownJerezAirport.setText(field.getString(CAPTION));
                        txtTownJerezAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMSEVILLEAIRPORTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtTownSevilleAirport.setText(field.getString(CAPTION));
                        txtTownSevilleAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMGIBRALTERAIRPORTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtTownGibralterAirport.setText(field.getString(CAPTION));
                        txtTownGibralterAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMMALAGAAIRPORTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtTownMalagaAirport.setText(field.getString(CAPTION));
                        txtTownMalagaAirportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDISTANCEFROMCADIZCITYCENTRESPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtTownCadizCityCentre.setText(field.getString(CAPTION));
                        txtTownCadizCityCentreValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPUBLICTRANSPORT) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtTownPublicTransport.setText(field.getString(CAPTION));
                        txtTownPublicTransportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPUBLICTRANSPORTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtTownPublicTransport.setText(field.getString(CAPTION));
                        txtTownPublicTransportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSITUATED) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtSituated.setText(field.getString(CAPTION));
                        txtSituatedValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSITUATEDSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtSituated.setText(field.getString(CAPTION));
                        txtSituatedValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }

                }

                if(txtTownDescription.getText().toString().trim().length()<=0){
                    rdbTownDescription.setVisibility(View.GONE);
                    lnrTownDescription.setVisibility(View.GONE);
                    rdbTownMap.setChecked(true);
                    frameMap.setVisibility(View.VISIBLE);
                }
                if(imageList.length()>0){
                    rdbTownPhotos.setVisibility(View.VISIBLE);
                }else{
                    rdbTownPhotos.setVisibility(View.GONE);
                }
            }else if(entryArrayList.get(0).get(SECTION).equals("Beaches")){
                lnrDirectoryDetailHeader.setVisibility(View.GONE);
                lnrTownDetailHeader.setVisibility(View.GONE);
                lnrThingsToDoDetailHeader.setVisibility(View.GONE);
                lnrBeachDetailHeader.setVisibility(View.VISIBLE);
                entriesLatitude = entryArrayList.get(0).get(LATITUDE);
                entriesLongitude = entryArrayList.get(0).get(LONGITUDE);
                entriesTitle = entryArrayList.get(0).get(TITLE);

                rdgBottomDirectory.setVisibility(View.GONE);
                rdgBottomThingsToDo.setVisibility(View.GONE);
                rdgBottomTown.setVisibility(View.GONE);
                rdgBottomBeach.setVisibility(View.VISIBLE);
                lnrDescription.setVisibility(View.GONE);
                lnrPhotos.setVisibility(View.GONE);
                frameMap.setVisibility(View.GONE);
                lnrInfo.setVisibility(View.GONE);
                lnrThingsToDoDescription.setVisibility(View.GONE);
                lnrTownDescription.setVisibility(View.GONE);
                lnrBeachDescription.setVisibility(View.VISIBLE);
                lnrBeachAmenities.setVisibility(View.GONE);
                lnrThingsToDoInfo.setVisibility(View.GONE);

                txtBeachTitle.setText(entriesTitle);
                JSONArray fields = new JSONArray(entryArrayList.get(0).get(FIELD));
                for (int i=0;i<fields.length();i++){
                    JSONObject field = fields.getJSONObject(i);

                    if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO) && field.getString(VALUE).trim().length()>0) {
                        if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                            imgBeachDetailHeader.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            txtBeachTitle.setBackgroundColor(getResources().getColor(R.color.sobipro_blue_opac));
                        }else{
                            androidAQuery.id(imgBeachDetailHeader).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),R.drawable.sobipro_default_image);
                            txtBeachTitle.setBackgroundColor(getResources().getColor(R.color.sobipro_blue_opac));
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEONE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFirst.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFirst).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFirst.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 0, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGETWO)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosTwo.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosTwo).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosTwo.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 1, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGETHREE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosThree.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosThree).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosThree.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity) getActivity()).loadNew(SobiproGalleryActivity.class, getActivity(), false, "IN_PHOTOS_PATHS", imageList.toString(), "IN_INDEX", 2, "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEFOUR)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFour.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFour).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFour.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",3,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGEFIVE)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosFive.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosFive).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosFive.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",4,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDIMAGESIX)){
                        if(field.getString(VALUE).trim().length()>0){
                            JSONObject image = new JSONObject();
                            image.put("image",field.getString(VALUE));
                            imageList.put(image);
                            if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                imgPhotosSix.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                            }else{
                                androidAQuery.id(imgPhotosSix).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                            }
                            imgPhotosSix.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ((SmartActivity)getActivity()).loadNew(SobiproGalleryActivity.class,getActivity(),false,"IN_PHOTOS_PATHS",imageList.toString(),"IN_INDEX",5,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDESCRIPTIONENGLISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDDESCRIPTIONSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachDescription.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDFILE)){
                        androidAQuery.id(imgInfo).image(field.getString(VALUE), true, true,((SmartActivity)getActivity()).getDeviceWidth(),0);
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDLENGTHOFBEACH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en") ){
                        txtBeachLengthOfBeach.setText(field.getString(CAPTION));
                        txtBeachLengthOfBeachValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAVERAGEWIDTH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachAverageWidth.setText(field.getString(CAPTION));
                        txtBeachAverageWidthValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDLENGTHOFBEACHSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachLengthOfBeach.setText(field.getString(CAPTION));
                        txtBeachLengthOfBeachValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAVERAGEWIDTHSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachAverageWidth.setText(field.getString(CAPTION));
                        txtBeachAverageWidthValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDCOMPOSITION) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachCompositions.setText(field.getString(CAPTION));
                        txtBeachCompositionsValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDCOMPOSITIONSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachCompositions.setText(field.getString(CAPTION));
                        txtBeachCompositionsValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSUMMEROCCUPANCY) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachSummersOccupancy.setText(field.getString(CAPTION));
                        txtBeachSummersOccupancyValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDSUMMEROCCUPANCYSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachSummersOccupancy.setText(field.getString(CAPTION));
                        txtBeachSummersOccupancyValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDLIFEGUARD) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachLifeGuard.setText(field.getString(CAPTION));
                        txtBeachLifeGuardValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDLIFEGUARDSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachLifeGuard.setText(field.getString(CAPTION));
                        txtBeachLifeGuardValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPUBLICPARKING) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachPublicParking.setText(field.getString(CAPTION));
                        txtBeachPublicParkingValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPUBLICPARKINGSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachPublicParking.setText(field.getString(CAPTION));
                        txtBeachPublicParkingValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPUBLICTRANSPORT) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachPublicTransport.setText(field.getString(CAPTION));
                        txtBeachPublicTransportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDPUBLICTRANSPORTSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachPublicTransport.setText(field.getString(CAPTION));
                        txtBeachPublicTransportValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDCLOSESTHOSPITAL) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")){
                        txtBeachClosetHospital.setText(field.getString(CAPTION));
                        txtBeachClosetHospitalValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDCLOSESTHOSPITALSPANISH) && getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("es")){
                        txtBeachClosetHospital.setText(field.getString(CAPTION));
                        txtBeachClosetHospitalValue.setText(field.getString(VALUE).replace("\r","").replace("\t",""));
                    }else if(field.getString(LABELID).equalsIgnoreCase(FIELDAMENITIES)){
                        String [] aminities = field.getString(VALUE).split(",");
                        for (int j=0;j<aminities.length;j++){
                            if(aminities[j].equalsIgnoreCase(SHOWERS)){
                                imgBeachAminitiesShowers.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(SUNBEDUMBRELLAHIRE)){
                                imgBeachAminitiesSunbedUmbrellaHire.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(DISABLEDACCESS)){
                                imgBeachAminitiesDisableAccess.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(PUBLICPARKING)){
                                imgBeachAminitiesPublicparking.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(FOODAVAILABLE)){
                                imgBeachAminitiesFoodsAvilable.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(LIFEGUARD)){
                                imgBeachAminitiesLifeGuard.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(REDCROSS)){
                                imgBeachAminitiesRedCross.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(TOILETS)){
                                imgBeachAminitiesToilets.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(TOURISTINFORMATIONOFFICE)){
                                imgBeachAminitiesTouriestInformationOffice.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(PROMENADE)){
                                imgBeachAminitiesPromenade.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(DRINKAVAILABLE)){
                                imgBeachAminitiesDrinkAvailable.setImageResource(R.drawable.sobipro_detail_write);
                            }else if(aminities[j].equalsIgnoreCase(WATERSPORTSEQUIPMENTHIRE)){
                                imgBeachAminitiesWaterSportsEquipmentHire.setImageResource(R.drawable.sobipro_detail_write);
                            }
                        }

                    }
                }

                if(txtBeachDescription.getText().toString().trim().length()<=0){
                    rdbBeachDescription.setVisibility(View.GONE);
                    lnrBeachDescription.setVisibility(View.GONE);
                    rdbBeachAmenities.setChecked(true);
                    lnrBeachAmenities.setVisibility(View.VISIBLE);
                }
                if(imageList.length()>0){
                    rdbBeachPhotos.setVisibility(View.VISIBLE);
                }else{
                    rdbBeachPhotos.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(entryArrayList.get(0).get(SECTION).equals("Beaches") ||entryArrayList.get(0).get(SECTION).equals("Towns & Villages") ||entryArrayList.get(0).get(SECTION).equals("Directory")){
            try{
                t = new Timer();
                t.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    googleMap = mapFragment.getExtendedMap();
                                    mapFragment.getView().setClickable(true);
                                    if (googleMap != null) {
                                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                                        googleMap.getUiSettings().setCompassEnabled(true);
                                        googleMap.getUiSettings().setZoomGesturesEnabled(true);
                                        googleMap.getUiSettings().setScrollGesturesEnabled(true);
                                        googleMap.getUiSettings().setRotateGesturesEnabled(true);
                                        googleMap.getUiSettings().setTiltGesturesEnabled(true);
                                        placeMarker(entriesTitle,entriesLatitude,entriesLongitude);
                                        t.cancel();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }
                }, 0, 500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void placeMarker(String title,String latitude,String longitude) {
        try {
            googleMap.addMarker(new MarkerOptions().title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.sobipro_map_pin))
                    .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)),8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
