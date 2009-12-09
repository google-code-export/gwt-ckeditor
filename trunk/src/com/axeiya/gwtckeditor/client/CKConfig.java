package com.axeiya.gwtckeditor.client;

import com.google.gwt.core.client.JavaScriptObject;

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
	}
	
	private static native void initConfig() /*-{
		
	}-*/;
	
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
	}
	
	/**
	 * Set the height of the editing area (relative or absolute)
	 * @param height The editing area height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * Set the width of the editor (relative or absolute)
	 * @param width The editor width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * Returns a CKEDITOR.config object with the defined configuration
	 * @return a CKEDITOR.config object
	 */
	public JavaScriptObject getConfigObject(){
		JavaScriptObject config = JavaScriptObject.createObject();
		if(toolbarName != null){
			config = setToolbarNameObject(config, toolbarName);
		}else{
			config = setToolbarObject(config,toolbar.getRepresentation());
		}
		if(uiColor != null){
			config = setUiColor(config,uiColor);
		}
		if(height != null){
			config = setHeight(config, height);
		}
		if(width != null){
			config = setWidth(config, width);
		}
		return config;
	}
	
	private static native JavaScriptObject setToolbarNameObject(JavaScriptObject config, String name) /*-{
		config.toolbar = name;
		return config;
	}-*/;
	
	private static native JavaScriptObject setUiColor(JavaScriptObject config, String uiColor) /*-{
		config.uiColor = uiColor;
		return config;
	}-*/;
	
	private static native JavaScriptObject setHeight(JavaScriptObject config, String height) /*-{
		config.height = height;
		return config;
	}-*/;
	
	private static native JavaScriptObject setWidth(JavaScriptObject config, String width) /*-{
		config.width = width;
		return config;
	}-*/;
	
	private static native JavaScriptObject setToolbarObject(JavaScriptObject config, JavaScriptObject toolbarArray) /*-{
		$wnd.CKEDITOR.config.toolbar_temp = toolbarArray;
		config.toolbar = 'temp';
		return config;
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
}
