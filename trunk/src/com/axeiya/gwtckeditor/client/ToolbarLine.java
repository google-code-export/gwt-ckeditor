package com.axeiya.gwtckeditor.client;

import java.util.ArrayList;
import java.util.Collection;

import com.axeiya.gwtckeditor.client.CKConfig.LINE_TYPE;
import com.axeiya.gwtckeditor.client.CKConfig.TOOLBAR_OPTIONS;
import com.google.gwt.core.client.JavaScriptObject;

public class ToolbarLine {
	private ArrayList<TOOLBAR_OPTIONS> blocks;
	private LINE_TYPE type = LINE_TYPE.NORMAL;
	
	public ToolbarLine(){
		blocks = new ArrayList<TOOLBAR_OPTIONS>();
	}
	
	public ToolbarLine(LINE_TYPE t){
		this();
		type = t;
	}
	
	public void add(TOOLBAR_OPTIONS t){
		blocks.add(t);
	}
	
	public void addAll(Collection<TOOLBAR_OPTIONS> options){
		blocks.addAll(options);
	}
	
	public void addAll(TOOLBAR_OPTIONS[] options){
		for(int i=0;i<options.length;i++){
			blocks.add(options[i]);
		}
	}
	
	public void addBlockSeparator(){
		blocks.add(TOOLBAR_OPTIONS._);
	}
	
	public Object getRepresentation(){
		if(type == LINE_TYPE.SEPARATOR){
			return getSeparator();
		}else{
			JavaScriptObject array = JavaScriptObject.createArray();
			for(TOOLBAR_OPTIONS opt:blocks){
				if(opt == TOOLBAR_OPTIONS._)
					array = addToArray(array,"-");
				else
					array = addToArray(array,opt.toString());
			}
			return array;
		}
	}
	
	private static native String getSeparator() /*-{
		var temp = new String("/");
		return temp;
	}-*/;
	
	private static native JavaScriptObject addToArray(JavaScriptObject base, String option) /*-{
		base[base.length] = option;
		return base;
	}-*/;
	
}