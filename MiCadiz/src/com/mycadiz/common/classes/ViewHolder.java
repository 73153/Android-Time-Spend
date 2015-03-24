package com.mycadiz.common.classes;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mycadiz.customviews.IjoomerButton;
import com.mycadiz.customviews.IjoomerTextView;

/**
 * This Class Contains All Method Related To ViewHolder.
 * 
 * @author tasol
 * 
 */
public class ViewHolder {

	public Integer efficientFlag = 0;

	/*
	 * Ijoomer Map Address
	 */
	public IjoomerTextView txtMapAddressData;


	/*
	 * Ijoomer Side Menu
	 */
    public LinearLayout lnrHomeItem;
	public ImageView imgMenuItemicon;
	public IjoomerTextView txtMenuItemCaption;


	/*
	 * Sobipro Section Categories
	 */
	public IjoomerTextView sobiproTxtCategoriesCaption;

	/*
	 * Sobipro Entries
	 */
    public LinearLayout lnrThingsToDoEntry;
	public ImageView imgEntryIcon;
	public LinearLayout lnrEntry;
	public IjoomerButton btnEntryRemove;
	public IjoomerTextView txtTitle;
    public IjoomerTextView txtThingsToDoTitle;
	public IjoomerTextView txtReview;
	public IjoomerTextView txtAddress;
    public IjoomerTextView txtPhone;
	public IjoomerTextView txtDistance;
	public IjoomerTextView txtPrice;
	public IjoomerTextView txtCaption;
	public IjoomerTextView txtValue;
	public LinearLayout lnrAbout;
	public LinearLayout lnrRatingCriteria;
	public IjoomerTextView txtPositiveReview;
	public IjoomerTextView txtNegativeReview;
	public IjoomerTextView txtGoodFor;
	public ImageView imgSeparator;
	public LinearLayout lnrPros;
	public LinearLayout lnrCons;
	public IjoomerTextView txtProsTitle;
	public IjoomerTextView txtConsTitle;
	public LinearLayout lnrRatingLeft;
	public LinearLayout lnrRatingRight;
	public IjoomerTextView txtPlus;
	public LinearLayout lnrExpandedView;
	public LinearLayout lnrGridBorder;
	public ImageView imgCall;
	public ImageView imgEmail;
	public IjoomerButton btnFavourite;
	public ImageView imgShare;
	public IjoomerTextView txtLess;
	public IjoomerTextView txtReviewOn;
	public IjoomerTextView txtDescription;
	public LinearLayout lnrReview;
	public IjoomerTextView txtAddReview;

	// facebook checkins

	public IjoomerTextView txtLocationValue;

	/*
	 * k2 Gallery
	 */
	public ImageView imgItem;

    /**
     * Ijoomer Emojis
     */
    public ImageView imgEmojis;

    public LinearLayout lnrMain;



    
}
