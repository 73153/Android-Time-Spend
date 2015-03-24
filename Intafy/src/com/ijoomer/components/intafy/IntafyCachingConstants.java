package com.ijoomer.components.intafy;

import java.util.HashMap;

/**
 * This class contains unNormalizeFields JomCachingConstants.
 * 
 * @author tasol
 * 
 */
@SuppressWarnings("serial")
public class IntafyCachingConstants {

	public static HashMap<String, String> getUnnormlizeFields() {
		HashMap<String, String> unNormalizeFields = new HashMap<String, String>() {
			{
				put("fields", "");
                put("menu", "");
			}
		};
		return unNormalizeFields;
	}
	public static HashMap<String, String> getUnRepetedField() {
        HashMap<String, String> unNormalizeFields = new HashMap<String, String>() {
            {
            }
        };
        return unNormalizeFields;
    }
}
