package com.eosos.caching;

import java.util.HashMap;

/**
 * This Class Contains UnNormalizeFields IjoomerCachingConstants.
 * 
 * @author tasol
 * 
 */
@SuppressWarnings("serial")
public class IjoomerCachingConstants {

	public static HashMap<String, String> unNormalizeFields = new HashMap<String, String>() {
		{
			put("categories", "");
			put("value", "");
			put("reviewrating", "");
			put("img_galleries", "");
			put("criterionaverage", "");
			put("ratings", "");
			put("options", "");
			put("from", "");
			put("to", "");
			put("location", "");
			put("category_list", "");
			put("field", "");
		}
	};
}
