package com.mycadiz.common.classes;

import java.util.HashMap;

/**
 * This Class Contains UnNormalizeFields CoreCachingConstants.
 * 
 * @author tasol
 * 
 */
@SuppressWarnings("serial")
public class CoreCachingConstants {

	public static HashMap<String, String> getUnnormlizeFields() {
		HashMap<String, String> unNormalizeFields = new HashMap<String, String>() {
			{
				put("menu", "");// core
				put("menuitem", "");// core
				put("options", "");// core
				put("privacy", "");// core
				put("screens", "");
                put("entries", "");
                put("categories", "");
                put("value", "");
                put("reviewrating", "");
                put("img_galleries", "");
                put("criterionaverage", "");
                put("ratings", "");
                put("from","");
                put("to","");
                put("field","");
                put("location", "");
                put("category_list", "");
			}
		};
		return unNormalizeFields;
	}
}
