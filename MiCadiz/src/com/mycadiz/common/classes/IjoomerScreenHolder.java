package com.mycadiz.common.classes;

import java.util.HashMap;

/**
 * This Class Contains All Method Related To IjoomerScreenHolder.
 * 
 * @author tasol
 * 
 */
public class IjoomerScreenHolder {

	@SuppressWarnings("serial")
	public static HashMap<String, String> originalScreens = new HashMap<String, String>() {
		{

			put("Login", "com.mycadiz.src.IjoomerLoginActivity");
			put("Registration", "com.mycadiz.src.IjoomerRegistrationStep1Activity");
			put("Home", "com.mycadiz.src.IjoomerHomeActivity");
			put("Web", "com.mycadiz.common.classes.IjoomerWebviewClient");
			put("SobiproCategories", "com.mycadiz.components.sobipro.SobiproSectionCategoryActivity");
            put("SobiproSearch", "com.mycadiz.components.sobipro.SobiproSearchActivity");
            put("SobiproShare", "com.mycadiz.components.sobipro.SobiproShareActivity");
            put("SobiproInformation", "com.mycadiz.components.sobipro.SobiproInfrormationActivity");
            put("SobiproSearchResults", "com.mycadiz.components.sobipro.SobiproPreSearchResultEntriesActivity");
            put("CometChat", "com.mycadiz.common.classes.IjoomerCometChat");

		}
	};
	@SuppressWarnings("serial")
	public static HashMap<String, String> aliasScreens = new HashMap<String, String>() {
		{

			put("IjoomerWebviewClient", "Web");
			put("IjoomerLoginActivity", "Login");
			put("IjoomerRegistrationStep1Activity", "Registration");
			put("IjoomerHomeActivity", "Home");
			put("SobiproSectionCategoryActivity", "SobiproCategories");
            put("SobiproSearchActivity", "SobiproSearch");
            put("SobiproPreSearchResultEntriesActivity", "SobiproSearchResults");
            put("SobiproShareActivity", "SobiproShare");
            put("SobiproInfrormationActivity", "SobiproInformation");
            put("IjoomerCometChat", "CometChat");
        }
	};
}
