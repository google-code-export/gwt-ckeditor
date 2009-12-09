package com.axeiya.gwtckeditor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class provides a CKEdtior as a Widget
 * 
 * @author Damien Picard <damien.picard@axeiya.com>
 */
public class CKEditor extends Widget {
    
	private String name;
    private JavaScriptObject editor;
    private TextArea textArea;
    private Element baseTextArea;
    private CKConfig config;
    private boolean enabledInHostedMode = false;
    
    /**
     * Creates an editor with the CKConfig.basic configuration. By default, the CKEditor is disabled in hosted mode ; then, the CKEditor is replaced by a simple TextArea
     */
    public CKEditor(){
    	this(CKConfig.basic);
    }
    
    /**
     * Creates an editor with the given configuration. By default, the CKEditor is disabled in hosted mode ; then, the CKEditor is replaced by a simple TextArea
     * @param config The configuration
     */
    public CKEditor(CKConfig config){
        super();
        this.config = config;
        initCKEditor();
    }
    
    /**
     * Creates an editor with the CKConfig.basic configuration.
     * @param enabledInHostedMode Indicates if the editor must be used in Hosted Mode
     */
    public CKEditor(boolean enabledInHostedMode){
    	this(enabledInHostedMode,CKConfig.basic);
    }
    
    /**
     * Creates an editor with the given CKConfig
     * @param enabledInHostedMode Indicates if the editor must be used in Hosted Mode
     * @param config The configuration
     */
    public CKEditor(boolean enabledInHostedMode, CKConfig config){
    	super();
    	this.enabledInHostedMode = enabledInHostedMode;
    	this.config = config;
    	initCKEditor();
    }
   
    /**
     * Initialize the editor
     */
    private void initCKEditor(){
        DivElement div = Document.get().createDivElement();
        
        if(GWT.isScript() || enabledInHostedMode){
        	baseTextArea = DOM.createTextArea();
        	name = HTMLPanel.createUniqueId();
        	div.appendChild(baseTextArea);
        	DOM.setElementAttribute(baseTextArea, "name", name);
        	DOM.setElementAttribute(baseTextArea, "class", "ckeditor");
        	setElement(div);
        }else{
        	textArea = new TextArea();
        	if(config.getHeight() != null)
        		textArea.setHeight(config.getHeight());
        	if(config.getWidth() != null)
        		textArea.setWidth(config.getWidth());
        	setElement(div);
        	div.appendChild(textArea.getElement());
        }
    }
    
    @Override
    protected void onLoad(){
    	if(GWT.isScript() || enabledInHostedMode){
    		replaceTextArea(baseTextArea, this.config.getConfigObject());
    	}
    }
    
    private static native void replaceTextArea(Object o, JavaScriptObject config) /*-{
        this.@com.axeiya.gwtckeditor.client.CKEditor::editor = $wnd.CKEDITOR.replace(o,config);
    }-*/;
    
    private static native String getNativeText() /*-{
    	var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
    	return e.getData();
	}-*/;
    
    private static native void setNativeText(String text) /*-{
		var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
		e.setData(text,new Function());
	}-*/;

    /**
     * Returns the editor text
     * @return the editor text
     */
    public String getText(){
    	if(GWT.isScript() || enabledInHostedMode){
    		return getNativeText();
    	}else{
    		return textArea.getText();
    	}
    }
    
    /**
     * {@link #getText()}
     * @return
     */
    public String getData(){
    	return getText();
    }
    
    /**
     * Set the editor text
     * @param text The text to set
     */
    public void setText(String text){
    	if(GWT.isScript() || enabledInHostedMode){
    		setNativeText(text);
    	}else{
    		textArea.setText(text);
    	}
    }
    
    /**
     * {@link #setText(String)}
     * @param data
     */
    public void setData(String data){
    	setText(data);
    }
}
