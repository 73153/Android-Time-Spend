package com.ijoomer.common.classes;

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
			put("Book Now", "com.ijoomer.topcarlondon.src.BookCarHomeActivity");
			put("Contact Us", "com.ijoomer.topcarlondon.src.BookCarContactUsActivity");
			put("My Booking", "com.ijoomer.topcarlondon.src.BookCarMyBookingListActivity");
			put("Area Covered", "com.ijoomer.topcarlondon.src.BookCarAreaCoverdListActivity");

		}
	};
	@SuppressWarnings("serial")
	public static HashMap<String, String> aliasScreens = new HashMap<String, String>() {
		{
			put("IcmsFavouriteArticlesActivity", "IcmsFavouriteArticles");
			put("IcmsArchivedArticlesActivity", "IcmsArchivedArticles");
			put("IcmsCategoryBlogActivity", "IcmsCategoryBlog");
			put("IcmsFeaturedArticlesActivity", "IcmsFeaturedArticles");
			put("IcmsArticleDetailActivity", "IcmsSingleArticle");
			put("IcmsCategoryActivity", "IcmsSingleCategory");
			put("IcmsAllCategoryActivity", "IcmsAllCategory");
			put("IjoomerWebviewClient", "Web");
			put("IjoomerLoginActivity", "Login");
			put("IjoomerRegistrationStep1Activity", "Registration");
			put("IjoomerHomeActivity", "Home");
			put("JomAlbumsActivity", "JomAlbums");
			put("JomVideoActivity", "JomVideo");
			put("JomPrivacySettingActivity", "JomPrivacySetting");
			put("JomEventActivity", "JomEvent");
			put("JomFriendListActivity", "JomFriendList");
			put("JomGroupActivity", "JomGroup");
			put("JomMessageActivity", "JomMessage");
			put("JomProfileActivity", "JomProfile");
			put("JomActivitiesActivity", "JomActivities");
			put("JomAdvanceSearchActivity", "JomAdvanceSearch");
			put("PluginsContactUsActivity", "PluginsContactUs");
			put("PluginsYoutubePlaylistActivity", "PluginsYoutubePlaylist");
			put("SobiproSectionCategoryActivity", "SobiproCategories");
			put("SobiproFavouriteActivity", "SobiproFavourite");
			put("SobiproAddEntryActivity", "SobiproAddEntry");
			put("PluginsFacebookCheckinActivity", "PluginsFacebookNearByVenues");
			put("PluginsVimeoPlaylistActivity","PluginsVimeoPlaylistActivity");
			put("K2MainActivity", "K2Categories");
            put("K2MainActivity", "K2Tag");
            put("K2MainActivity", "K2UserPage");
            put("K2MainActivity", "K2LatestItems");
            put("K2MainActivity", "K2Items");
            put("IjoomerCometChat", "CometChat");


        }
	};
}
