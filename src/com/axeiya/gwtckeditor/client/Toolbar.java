package com.axeiya.gwtckeditor.client;

import java.util.ArrayList;

import com.axeiya.gwtckeditor.client.CKConfig.LINE_TYPE;
import com.google.gwt.core.client.JavaScriptObject;

public class Toolbar{
	private ArrayList<ToolbarLine> lines;
	
	public Toolbar(){
		lines = new ArrayList<ToolbarLine>();
	}
	
	public void add(ToolbarLine l){
		lines.add(l);
	}
	
	public void addSeparator(){
		lines.add(new ToolbarLine(LINE_TYPE.SEPARATOR));
	}
	
	public JavaScriptObject getRepresentation(){
		JavaScriptObject array = JavaScriptObject.createArray();
		for(ToolbarLine line:lines){
			Object representation = line.getRepresentation();
			if(representation instanceof JavaScriptObject)
				array = addToArray(array,(JavaScriptObject)representation);
			else
				array = addToArray(array,(String)representation);
		}
		return array;
	}
	
	private static native JavaScriptObject addToArray(JavaScriptObject base, JavaScriptObject option) /*-{
		base[base.length] = option;
		return base;
	}-*/;
	
	private static native JavaScriptObject addToArray(JavaScriptObject base, String option) /*-{
		base[base.length] = option;
		return base;
	}-*/;
}
