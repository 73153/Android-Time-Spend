package com.eosos.common.classes;

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
			put("IcmsFavouriteArticles", "com.eosos.components.icms.IcmsFavouriteArticlesActivity");
			put("IcmsArchivedArticles", "com.eosos.components.icms.IcmsArchivedArticlesActivity");
			put("IcmsCategoryBlog", "com.eosos.components.icms.IcmsCategoryBlogActivity");
			put("IcmsFeaturedArticles", "com.eosos.components.icms.IcmsFeaturedArticlesActivity");
			put("IcmsAllCategory", "com.eosos.components.icms.IcmsAllCategoryActivity");
			put("IcmsSingleArticle", "com.eosos.components.icms.IcmsArticleDetailActivity");
			put("IcmsSingleCategory", "com.eosos.components.icms.IcmsCategoryActivity");
			put("Login", "com.eosos.src.IjoomerLoginActivity");
			put("Registration", "com.eosos.src.IjoomerRegistrationStep1Activity");
			put("Home", "com.eosos.src.IjoomerHomeActivity");
			put("Web", "com.eosos.common.classes.IjoomerWebviewClient");
			put("JomAlbums", "com.eosos.components.jomsocial.JomAlbumsActivity");
			put("JomVideo", "com.eosos.components.jomsocial.JomVideoActivity");
			put("JomPrivacySetting", "com.eosos.components.jomsocial.JomPrivacySettingActivity");
			put("JomEvent", "com.eosos.components.jomsocial.JomEventActivity");
			put("JomFriendList", "com.eosos.components.jomsocial.JomFriendListActivity");
			put("JomGroup", "com.eosos.components.jomsocial.JomGroupActivity");
			put("JomMessage", "com.eosos.components.jomsocial.JomMessageActivity");
			put("JomProfile", "com.eosos.components.jomsocial.JomProfileActivity");
			put("JomActivities", "com.eosos.components.jomsocial.JomActivitiesActivity");
			put("JomAdvanceSearch", "com.eosos.components.jomsocial.JomAdvanceSearchActivity");
			put("PluginsContactUs", "com.eosos.plugins.PluginsContactUsActivity");
			put("PluginsYoutubePlaylist", "com.eosos.plugins.PluginsYoutubePlaylistActivity");
			put("PluginsWeather", "com.eosos.plugins.PluginsWeatherLocationActivity");
			put("SobiproCategories", "com.eosos.components.sobipro.SobiproSectionCategoryActivity");
			put("SobiproFavourite", "com.eosos.components.sobipro.SobiproFavouriteActivity");
			put("SobiproAddEntry", "com.eosos.components.sobipro.SobiproAddEntryActivity");
			put("PluginsFacebookNearByVenues", "com.eosos.plugins.PluginsFacebookCheckinActivity");
			put("PluginsVimeoPlaylist", "com.eosos.plugins.PluginsVimeoPlaylistActivity");
			put("K2Categories", "com.eosos.components.k2.K2MainActivity");
			put("K2Tag", "com.eosos.components.k2.K2MainActivity");
			put("K2UserPage", "com.eosos.components.k2.K2MainActivity");
			put("K2LatestItems", "com.eosos.components.k2.K2MainActivity");
			put("K2Items", "com.eosos.components.k2.K2MainActivity");
			put("CometChat", "com.eosos.common.classes.IjoomerCometChat");
			put("Jbolochat", "com.eosos.components.jbolochat.JboloOnlineUserListActivity");
			put("DisplayAsMenu", "com.eosos.src.IjoomerMenuActivity");
            put("easyBlogCategory", "com.eosos.components.easyblog.EasyBlogAllCategoryActivity");
            put("easyBlogAllItems", "com.eosos.components.easyblog.EasyBlogEntriesActivity");


		}
	};
	@SuppressWarnings("serial")
	public static HashMap<String, String> aliasScreens = new HashMap<String, String>() {
		{
			put("IjoomerMenuActivity", "DisplayAsMenu");
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
			put("PluginsWeatherLocationActivity", "PluginsWeather");
			put("SobiproSectionCategoryActivity", "SobiproCategories");
			put("SobiproFavouriteActivity", "SobiproFavourite");
			put("SobiproAddEntryActivity", "SobiproAddEntry");
			put("PluginsFacebookCheckinActivity", "PluginsFacebookNearByVenues");
			put("PluginsVimeoPlaylistActivity", "PluginsVimeoPlaylistActivity");
			put("K2MainActivity", "K2Categories");
			put("K2MainActivity", "K2Tag");
			put("K2MainActivity", "K2UserPage");
			put("K2MainActivity", "K2LatestItems");
			put("K2MainActivity", "K2Items");
			put("IjoomerCometChat", "CometChat");
			put("JboloOnlineUserListActivity", "Jbolochat");
            put("EasyBlogAllCategoryActivity", "easyBlogCategory");
            put("EasyBlogEntriesActivity", "easyBlogAllItems");
		}
	};
}
