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

import com.axeiya.gwtckeditor.client.events.HasSaveHandlers;
import com.axeiya.gwtckeditor.client.events.SaveEvent;
import com.axeiya.gwtckeditor.client.events.SaveHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * This class provides a CKEdtior as a Widget
 * 
 * @author Damien Picard <damien.picard@axeiya.com>
 */
public class CKEditor extends Composite implements HasSaveHandlers<CKEditor>, ClickHandler, HasAlignment {

	private String name;
	private JavaScriptObject editor;
	private TextArea textArea;
	private Element baseTextArea;
	private JavaScriptObject dataProcessor;
	private CKConfig config;
	private boolean enabledInHostedMode = true;
	private boolean replaced = false;
	private boolean textWaitingForAttachment = false;
	private String waitingText;
	
	private HorizontalAlignmentConstant hAlign =null;
	private VerticalAlignmentConstant vAlign = null;
	

	/**
	 * Creates an editor with the CKConfig.basic configuration. By default, the
	 * CKEditor is enabled in hosted mode ; if not, the CKEditor is replaced by
	 * a simple TextArea
	 */
	public CKEditor() {
		this(CKConfig.basic);
	}

	/**
	 * Creates an editor with the given configuration. By default, the CKEditor
	 * is enabled in hosted mode ; if not, the CKEditor is replaced by a simple
	 * TextArea
	 * 
	 * @param config
	 *            The configuration
	 */
	public CKEditor(CKConfig config) {
		super();
		this.config = config;
		initCKEditor();
	}

	/**
	 * Creates an editor with the CKConfig.basic configuration.
	 * 
	 * @param enabledInHostedMode
	 *            Indicates if the editor must be used in Hosted Mode
	 */
	public CKEditor(boolean enabledInHostedMode) {
		this(enabledInHostedMode, CKConfig.basic);
	}

	/**
	 * Creates an editor with the given CKConfig
	 * 
	 * @param enabledInHostedMode
	 *            Indicates if the editor must be used in Hosted Mode
	 * @param config
	 *            The configuration
	 */
	public CKEditor(boolean enabledInHostedMode, CKConfig config) {
		super();
		this.enabledInHostedMode = enabledInHostedMode;
		this.config = config;
		initCKEditor();
	}

	/**
	 * Initialize the editor
	 */
	private void initCKEditor() {
		Element div = DOM.createDiv();
		
		

		if (GWT.isScript() || enabledInHostedMode) {
			baseTextArea = DOM.createTextArea();
			name = HTMLPanel.createUniqueId();
			div.appendChild(baseTextArea);
			DOM.setElementAttribute(baseTextArea, "name", name);
			this.sinkEvents(Event.ONCLICK| Event.KEYEVENTS);
			
	
		} else {
			textArea = new TextArea();
			if (config.getHeight() != null)
				textArea.setHeight(config.getHeight());
			if (config.getWidth() != null)
				textArea.setWidth(config.getWidth());
			div.appendChild(textArea.getElement());
		}
		if(config.isUsingFormPanel())
		{
			FormPanel form = new FormPanel();
			Button submit = new Button();
			submit.addClickHandler(this);
			submit.getElement().setAttribute("name", "submit");
			submit.getElement().setAttribute("style", "visibility:hidden; display:none;");
			
			form.getElement().appendChild(div);
			form.add(submit);
			initWidget(form);
		}
		else
		{
			SimplePanel simplePanel = new SimplePanel();
			simplePanel.getElement().appendChild(div);
			initWidget(simplePanel);
		}
	}

	@Override
	protected void onLoad() {
		if ((GWT.isScript() || enabledInHostedMode) && !replaced) {
			replaced = true;
			replaceTextArea(baseTextArea, this.config.getConfigObject());
			
			if(textWaitingForAttachment)
			{				
				textWaitingForAttachment = false;
				setHTML(waitingText);
			}
			
			if(hAlign != null)
			{
				setHorizontalAlignment(hAlign);
			}
			
			if(vAlign != null)
			{
				setVerticalAlignment(vAlign);
			}

			/*if (config.getBreakLineChars() != null) {
				setNativeBreakLineChars(config.getBreakLineChars());
			}

			if (config.getSelfClosingEnd() != null) {
				setNativeSelfClosingEnd(config.getSelfClosingEnd());
			}*/
		}
	}

	private native void replaceTextArea(Object o, JavaScriptObject config) /*-{
		this.@com.axeiya.gwtckeditor.client.CKEditor::editor = $wnd.CKEDITOR.replace(o,config);

//		if($wnd.CKEDITOR.htmlDataProcessor()){
//			this.@com.axeiya.gwtckeditor.client.CKEditor::dataProcessor = new $wnd.CKEDITOR.htmlDataProcessor(this.@com.axeiya.gwtckeditor.client.CKEditor::editor);
//			this.@com.axeiya.gwtckeditor.client.CKEditor::editor.dataProcessor = this.@com.axeiya.gwtckeditor.client.CKEditor::dataProcessor;
//		}else if(this.@com.axeiya.gwtckeditor.client.CKEditor::editor.dataProcessor){
//			this.@com.axeiya.gwtckeditor.client.CKEditor::dataProcessor = this.@com.axeiya.gwtckeditor.client.CKEditor::editor.dataProcessor;
//		}
	}-*/;

	@Deprecated
	/**
	 * Use getNativeHTML() instead
	 */
	private native String getNativeText() /*-{
		var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
		return e.getData();
	}-*/;
	
	private native String getNativeHTML() /*-{
	var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
	return e.getData();
}-*/;

	@Deprecated
	/**
	 * Use setNativeHTML(String html) instead
	 */
	private native void setNativeText(String text) /*-{
		var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
		e.setData(text,new Function());
	}-*/;

	private native void setNativeHTML(String html) /*-{
	var e = this.@com.axeiya.gwtckeditor.client.CKEditor::editor;
	e.setData(html,new Function());
}-*/;
	
//	private native void setNativeBreakLineChars(String breakLineChars) /*-{
//		var dataProcessor = this.@com.axeiya.gwtckeditor.client.CKEditor::dataProcessor;
//		if(dataProcessor){
//			dataProcessor.writer.lineBreakChars = breakLineChars;
//		}
//	}-*/;

//	private native void setNativeSelfClosingEnd(String selfClosingEnd) /*-{
//		var dataProcessor = this.@com.axeiya.gwtckeditor.client.CKEditor::dataProcessor;
//		if(dataProcessor){
//			dataProcessor.writer.selfClosingEnd = selfClosingEnd;
//		}
//	}-*/;

	
	/**
	 * Use getHTML() instead.
	 * Returns the editor text
	 * 
	 * @return the editor text
	 */
	@Deprecated
	public String getText() {
		if (GWT.isScript() || enabledInHostedMode) {
			return getNativeText();
		} else {
			return textArea.getText();
		}
	}
	
	/**
	 * Returns the editor text
	 * 
	 * @return the editor text
	 */
	public String getHTML() {
		if (GWT.isScript() || enabledInHostedMode) {
			return getNativeHTML();
		} else {
			return textArea.getText();
		}
	}


	/**
	 * {@link #getHTML()}
	 * 
	 * @return
	 */
	public String getData() {
		return getHTML();
	}

	
	/**
	 * Use setHtml(String html) instead.
	 * Set the editor text
	 * 
	 * @param text
	 *            The text to set
	 */
	@Deprecated
	public void setText(String text) {
		if (GWT.isScript() || enabledInHostedMode) {
			if(replaced)
				setNativeText(text);
			else{
				waitingText = text;
				textWaitingForAttachment = true;
			}
		} else {
			textArea.setText(text);
		}
	}
	
	/**
	 * Set the editor's html
	 * 
	 * @param html
	 *            The html string to set
	 */
	public void setHTML(String html) {
		if (GWT.isScript() || enabledInHostedMode) {
			if(replaced)
				setNativeHTML(html);
			else{
				waitingText = html;
				textWaitingForAttachment = true;
			}
		} else {
			textArea.setText(html);
		}
	}

	/**
	 * {@link #setHTML(String)}
	 * 
	 * @param data
	 */
	public void setData(String data) {
		setHTML(data);
	}

	/**
	 * Used for catching Save event
	 * 
	 * @param o
	 * @return
	 */
	private static native String getParentClassname(JavaScriptObject o) /*-{
		var classname = o.parentNode.getAttribute("class");
		if(classname == null)
			return o.parentNode.className;
		return classname;
	}-*/;

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
//		System.out.println("Declenche");
//		String classString = getParentClassname(event.getEventTarget());
//		String[] classes = classString.split(" ");
//		for (String c : classes) {
//			if (c.trim().equals("cke_button_save")) {
//				event.stopPropagation();
//				SaveEvent.fire(this, this, this.getText());
//				return;
//			}
//		}
//	
		
	}
	
	

	@Override
	public void onClick(ClickEvent event) {
		if(event.getRelativeElement().getAttribute("name").equals("submit"))
		{
			event.stopPropagation();
			SaveEvent.fire(this, this, this.getHTML());
		}
		
	}

	@Override
	public HandlerRegistration addSaveHandler(SaveHandler<CKEditor> handler) {
		return addHandler(handler, SaveEvent.getType());
	}

	@Override
	public HorizontalAlignmentConstant getHorizontalAlignment() {
		return hAlign;
	}

	@Override
	public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
		this.hAlign = align;
		if(replaced)
			this.getElement().getParentElement().setAttribute("align", align.getTextAlignString());
	}

	@Override
	public VerticalAlignmentConstant getVerticalAlignment() {
		return vAlign;
	}

	@Override
	public void setVerticalAlignment(VerticalAlignmentConstant align) {
		this.vAlign = align;
		if(replaced)
			this.getElement().getParentElement().setAttribute("style", "vertical-align:" + align.getVerticalAlignString());
		
	}
}