/**
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axeiya.gwtckeditor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.ui.Widget;

/**
 * Defines a configuration environment for a CKEditor
 * 
 * @author Damien Picard <damien.picard@axeiya.com>
 */
public class CKConfig {
	
	private String toolbarName;
	private Toolbar toolbar;
	private String uiColor;
	private String height;
	private String width;
	private String breakLineChars;
	private String enterMode;
	private String selfClosingEnd;
	private boolean resizeEnabled;
	private int resizeMinWidth;
	private int resizeMinHeight;
	private int resizeMaxWidth;
	private int resizeMaxHeight;
	private int baseFloatZIndex;
	private int tabIndex;
	private boolean useFormPanel = true	;
	private String language;
	private boolean entities;
	private boolean entities_greek;
	private boolean entities_latin;
	private boolean focusOnStartup;
	private String fileBrowserBrowseUrl;
	private String fileBrowserImageBrowseUrl;
	private String fileBrowserImageBrowseLinkUrl;
	private boolean tableResize;
	private boolean shouldEnterSave = false;
	private String skin;
	
	JavaScriptObject config = JavaScriptObject.createObject();
	
	/**
	 * Defines existing toolbar configuration in CKEDITOR environment
	 */
	public enum PRESET_TOOLBAR {BASIC,FULL}
	
	/**
	 * Defines existing toolbar options ; use _ as "-" separator
	 */
	public enum TOOLBAR_OPTIONS{Source,Save,NewPage,Preview,Templates,Cut,Copy,Paste,PasteText,PasteFromWord,Print,SpellChecker,Scayt,Undo,Redo,Find,Replace,SelectAll,RemoveFormat,
						Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Bold,Italic,Underline,Strike,Subscript,Superscript,NumberedList,
						BulletedList,Outdent,Indent,Blockquote,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,Link,Unlink,Anchor,Image,Flash,Table,HorizontalRule,
						Smiley,SpecialChar,PageBreak,Styles,Format,Font,FontSize,TextColor,BGColor,Maximize,ShowBlocks,About,_}
	
	enum LINE_TYPE {NORMAL,SEPARATOR}
	
	/**
	 * Default full configuration
	 */
	public static CKConfig full = new CKConfig(PRESET_TOOLBAR.FULL);
	
	/**
	 * Default basic configuration
	 */
	public static CKConfig basic = new CKConfig(PRESET_TOOLBAR.BASIC);
	
	
	/**
	 * Creates a default config with the FULL toolbar
	 */
	public CKConfig(){
		this(PRESET_TOOLBAR.FULL);
	}
	
	/**
	 * Creates a default config with the given PRESET_TOOLBAR 
	 * @param toolbar the PRESET_TOOLBAR to use 
	 */
	public CKConfig(PRESET_TOOLBAR toolbar){
		initConfig();
		setToolbar(toolbar);
		LocaleInfo l = LocaleInfo.getCurrentLocale();
		//GWT.log("Locale : "+l.getLocaleName(),null);//always returns default
		if(l.getLocaleName().equals("default")){
			GWT.log("LocaleProperty : "+getLocaleProperty(), null);
			this.setLanguage(getLocaleProperty());
		}else
			this.setLanguage(LocaleInfo.getCurrentLocale().getLocaleName().split("_")[0]);
	}
	
	private native void initConfig() /*-{}-*/;
	
	private native String getLocaleProperty() /*-{
//		var metaArray = $doc.getElementsByTagName("meta");
//		for (var i=0;i<metaArray.length;i++){
//			if (metaArray[i].getAttribute("name") == "gwt:property"){
//				var content = metaArray[i].getAttribute("content");
//				var contentArray = content.split("=");
//				if(contentArray[0] == "locale"){
//					var localeArray = contentArray[1].split("_");
//					return localeArray[0];
//				}
//			}
//		}
		if($wnd.__gwt_Locale)
			return $wnd.__gwt_Locale;
		return "en";
	}-*/;
	
	
	
	/**
	 * Set the browser's Url to Open when selecting images, link and flash
	 * @param fileBrowserBrowseUrl 
	 * 		the Url to use
	 */
	public void setFileBrowserBrowseUrl(String fileBrowserBrowseUrl) {
		this.fileBrowserBrowseUrl = fileBrowserBrowseUrl;
		setNativeFileBrowserBrowseUrl(fileBrowserBrowseUrl);
	}
	
	public void setShouldEnterSave(boolean enterShouldSave){
		this.shouldEnterSave = enterShouldSave;
		setNativeShouldEnterSave(enterShouldSave);
	}
	
	private native void setNativeShouldEnterSave(boolean enterShouldSave)/*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.keystrokes += [ 13 , 'save' ];
	}-*/;
	
	/**
	 * Set the Image browser's Url to Open when selecting images
	 * @param fileBrowserBrowseUrl 
	 * 		the Url to use
	 */
	public void setFileBrowserImageBrowseUrl(String fileBrowserImageBrowseUrl) {
		this.fileBrowserImageBrowseUrl = fileBrowserImageBrowseUrl;
		setNativeFileBrowserImageBrowseUrl(fileBrowserImageBrowseUrl);
	}
	
	/**
	 * Set the Image browser's link tab Url to Open when selecting images
	 * @param fileBrowserBrowseUrl 
	 * 		the Url to use
	 */
	public void setFileBrowserImageBrowseLinkUrl(String fileBrowserImageBrowseLinkUrl) {
		this.fileBrowserImageBrowseLinkUrl = fileBrowserImageBrowseLinkUrl;
		setNativeFileBrowserImageBrowseLinkUrl(fileBrowserImageBrowseLinkUrl);
	}

	/**
	 * Sets the toolbar to a pre-defined one ; this will unset any Toolbar set before
	 * @param toolbar The preset toolbar to use
	 */
	public void setToolbar(PRESET_TOOLBAR toolbar){
		if(toolbar == PRESET_TOOLBAR.BASIC){
			toolbarName = "Basic";
		}else if(toolbar == PRESET_TOOLBAR.FULL){
			toolbarName = "Full";
		}
	}
	
	
	
	public void setTableResize(boolean tableResize) {
		this.tableResize = tableResize;
		setNativeTableResize(tableResize);
	}

	

	/**
	 * Load the toolbar from the CKEDITOR.config.toolbar_{name} toolbar configuration (see CKEditor doc for further details) ; this will unset any Toolbar set before
	 * @param name The toolbar name
	 */
	public void setToolbarName(String name){
		this.toolbarName = name;
	}

	
	/**
	 * Sets the toolbar from an options list ; this will unset any PRESET_TOOLBAR set before
	 * @param toolbars Options list
	 */
	public void setToolbar(Toolbar t){
		toolbarName = null;
		toolbar = t;
	}
	
	/**
	 * Define the editor's background color (uiColor must be defined in CSS Style)
	 * @param uiColor The background color
	 */
	public void setUiColor(String uiColor){
		this.uiColor = uiColor;
		setNativeUiColor(uiColor);
	}
	
	
	/**
	 * Define if the ckEditor instance should have the focus on startup or not
	 * @param name The toolbar name
	 */
	public void setFocusOnStartup(boolean focus){
		setNativeFocusOnStartup(focus);
		this.focusOnStartup = focus;
		
	}
	
	/**
	 * Set the height of the editing area (relative or absolute)
	 * @param height The editing area height
	 */
	public void setHeight(String height) {
		this.height = height;
		setNativeHeight(height);
	}

	/**
	 * Set the width of the editor (relative or absolute)
	 * @param width The editor width
	 */
	public void setWidth(String width) {
		this.width = width;
		setNativeWidth(width);
	}
	
	/**
	 * Defines the base Z-index for floating dialogs and popups.
	 * @param zIndex The base Z-index for floating dialogs and popups.
	 */
	public void setBaseFloatZIndex(int zIndex){
		baseFloatZIndex = zIndex;
		setNativeBaseFloatZIndex(zIndex);
	}
	
	/**
	 * The user interface language localization to use. If empty, the editor automatically localize the editor to the user language, if supported, otherwise the CKEDITOR.config.defaultLanguage language is used. 
	 * @param language
	 */
	public void setLanguage(String language){
		this.language = language;
		setNativeLanguage(language);
	}
	
	/**
	 * Define if the editor can be resized or not
	 * @param resizeEnabled
	 */
	public void setResizeEnabled(boolean resizeEnabled)
	{
		this.resizeEnabled = resizeEnabled;
		setNativeResizeEnabled(resizeEnabled);
	}
	
	
	/**
	 * The minimum editor width, in pixels, when resizing it with the resize handle. 
	 * @param resizeMinWidth
	 */
	public void setResizeMinWidth(int resizeMinWidth) {
		this.resizeMinWidth = resizeMinWidth;
		setNativeMinWidth(resizeMinWidth);
	}

	/**
	 * The minimum editor height, in pixels, when resizing it with the resize handle.
	 * @param resizeMinHeight
	 */
	public void setResizeMinHeight(int resizeMinHeight) {
		this.resizeMinHeight = resizeMinHeight;
		setNativeMinHeight(resizeMinHeight);
	}
	
	/**
	 * The maximum editor width, in pixels, when resizing it with the resize handle. 
	 * @param resizeMaxWidth
	 */
	public void setResizeMaxWidth(int resizeMaxWidth) {
		this.resizeMaxWidth = resizeMaxWidth;
		setNativeMaxWidth(resizeMaxWidth);
	}
	
	/**
	 * The maximum editor height, in pixels, when resizing it with the resize handle. 
	 * @param resizeMaxHeight
	 */
	public void setResizeMaxHeight(int resizeMaxHeight) {
		this.resizeMaxHeight = resizeMaxHeight;
		setNativeMaxHeight(resizeMaxHeight);
	}

	/**
	 * Whether to use HTML entities in the output.
	 * @param entities
	 */
	public void setEntities(boolean entities) {
		this.entities = entities;
		setNativeEntities(entities);
	}

	/**
	 * Whether to convert some symbols, mathematical symbols, and Greek letters to HTML entities. This may be more relevant for users typing text written in Greek. The list of entities can be found at the W3C HTML 4.01 Specification, section 24.3.1. 
	 * @param entitiesGreek
	 */
	public void setEntities_greek(boolean entitiesGreek) {
		entities_greek = entitiesGreek;
		setNativeEntitiesGreek(entitiesGreek);
	}

	/**
	 * Whether to convert some Latin characters (Latin alphabet No. 1, ISO 8859-1) to HTML entities. The list of entities can be found at the W3C HTML 4.01 Specification, section 24.2.1. 
	 * @param entitiesLatin
	 */
	public void setEntities_latin(boolean entitiesLatin) {
		entities_latin = entitiesLatin;
		setNativeEntitiesLatin(entitiesLatin);
	}
	
	/**
	 * The characters to use when occurs a breakLine (e.g: "\n")
	 * @param breakLineChars
	 */
	public void setBreakLineChars(String breakLineChars)
	{
		this.breakLineChars = breakLineChars;
		
	}
	
	/**
	 * The tabIndex for tinyMCE instance
	 * @param tabIndex
	 */
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
		setNativeTabIndex(tabIndex);
	}
	
	
	/**
	 * The way a tag self Close himself (e.g : "/>" or " />"
	 * @param selfClosingEnd
	 */
	public void setSelfClosingEnd(String selfClosingEnd) {
		this.selfClosingEnd = selfClosingEnd;
	}
	
	
	/**
	 * What kind of tag will be written when pressing Enter (p|div|br) 
	 * @param enterMode
	 */
	public void setEnterMode(String enterMode) {
		this.enterMode = enterMode;
		setNativeEnterMode(enterMode);
	}

	/**
	 * Returns a CKEDITOR.config object with the defined configuration
	 * @return a CKEDITOR.config object
	 */
	public JavaScriptObject getConfigObject(){
		
		if(toolbarName != null){
			setToolbarNameObject(toolbarName);
		}else{
			setToolbarObject(toolbar.getRepresentation());
		}
		return config;
	}
	
	private native void setNativeFileBrowserBrowseUrl(String browseUrl)/*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.filebrowserBrowseUrl = browseUrl;
	}-*/;
	
	private native void setNativeFileBrowserImageBrowseUrl(String browseUrl) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.filebrowserImageBrowseUrl = browseUrl;
	}-*/;
	
	private native void setNativeFileBrowserImageBrowseLinkUrl(String browseUrl) /*-{
	this.@com.axeiya.gwtckeditor.client.CKConfig::config.filebrowserImageBrowseLinkUrl = browseUrl;
}-*/;
	
	private native void setNativeEnterMode(String enterMode)/*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.enterMode = enterMode;
	}-*/;
	
	private native void setToolbarNameObject(String name) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.toolbar = name;
	}-*/;
	
	private native void setNativeUiColor(String uiColor) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.uiColor = uiColor;
	}-*/;
	
	private native void setNativeHeight(String height) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.height = height;
	}-*/;
	
	private native void setNativeWidth(String width) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.width = width;
	}-*/;
	
	private native void setNativeBaseFloatZIndex(int zIndex) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.baseFloatZIndex = zIndex;
	}-*/;
	
	private native void setNativeLanguage(String language) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.language = language;
	}-*/;

	private native void setNativeResizeEnabled(boolean resizeEnabled) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.resize_enabled = resizeEnabled;
	}-*/;

	
	private native void setNativeMaxWidth(int width) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.resize_maxWidth = width;
	}-*/;
	
	private native void setNativeMinWidth(int width) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.resize_minWidth = width;
	}-*/;
	
	private native void setNativeMaxHeight(int height) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.resize_maxHeight = height;
	}-*/;
	
	private native void setNativeMinHeight(int height) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.resize_minHeight = height;
	}-*/;
	
	private native void setNativeEntities(boolean entities) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.entities = entities;
	}-*/;
	
	private native void setNativeEntitiesGreek(boolean entitiesGreek) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.entities_greek = entitiesGreek;
	}-*/;
	
	private native void setNativeEntitiesLatin(boolean entitiesLatin) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.entities_latin = entitiesLatin;
	}-*/;
	
	private native void setToolbarObject(JavaScriptObject toolbarArray) /*-{
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.toolbar_temp = toolbarArray;
		this.@com.axeiya.gwtckeditor.client.CKConfig::config.toolbar = 'temp';
	}-*/;
	
	private native void setNativeTableResize(boolean tableResize) /*-{
		if(tableResize){
			this.@com.axeiya.gwtckeditor.client.CKConfig::config.extraPlugins = 'tableresize';
		}
		else{
			this.@com.axeiya.gwtckeditor.client.CKConfig::config.extraPlugins = '';
		}
		
	}-*/;
	
	private native void setNativeTabIndex(int tabIndex) /*-{
	this.@com.axeiya.gwtckeditor.client.CKConfig::config.tabIndex = tabIndex;
}-*/;
	
	private native void setNativeFocusOnStartup(boolean focus) /*-{
	this.@com.axeiya.gwtckeditor.client.CKConfig::config.startupFocus = focus;
	
}-*/;
	
	
	/**
	 * Returns the config height
	 * @return height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Returns the config width
	 * @return width
	 */
	public String getWidth() {
		return width;
	}

	public String getUiColor() {
		return uiColor;
	}

	public int getResizeMinWidth() {
		return resizeMinWidth;
	}

	public int getResizeMinHeight() {
		return resizeMinHeight;
	}
	
	

	public boolean isFocusOnStartup() {
		return focusOnStartup;
	}

	public int getResizeMaxWidth() {
		return resizeMaxWidth;
	}

	public int getResizeMaxHeight() {
		return resizeMaxHeight;
	}

	public int getBaseFloatZIndex() {
		return baseFloatZIndex;
	}

	public String getLanguage() {
		return language;
	}

	public boolean isEntities() {
		return entities;
	}

	
	public boolean isEntities_greek() {
		return entities_greek;
	}

	public boolean isEntities_latin() {
		return entities_latin;
	}

	public String getBreakLineChars() {
		return breakLineChars;
	}

	public String getSelfClosingEnd() {
		return selfClosingEnd;
	}

	public String getEnterMode() {
		return enterMode;
	}

	public boolean isResizeEnabled() {
		return resizeEnabled;
	}

	public void setUseFormPanel(boolean useFormPanel) {
		this.useFormPanel = useFormPanel;
	}

	public boolean isUsingFormPanel() {
		return useFormPanel;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public String getFileBrowserBrowseUrl() {
		return fileBrowserBrowseUrl;
	}

	public String getFileBrowserImageBrowseUrl() {
		return fileBrowserImageBrowseUrl;
	}
	
}
