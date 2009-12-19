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
        Element div = DOM.createDiv();
        
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
    
    private native void replaceTextArea(Object o, JavaScriptObject config) /*-{
        this.@com.axeiya.gwtckeditor.client.CKEditor::editor = $wnd.CKEDITOR.replace(o,config);
    }-*/;
    
    private native String getNativeText() /*-{
    	var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
    	return e.getData();
	}-*/;
    
    private native void setNativeText(String text) /*-{
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
    		GWT.log(editor.toString(), null);
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
