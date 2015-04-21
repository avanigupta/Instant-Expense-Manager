package com.paisa.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class TypeFaceUtil {
	
	private Context mContext;
	
	private Typeface openSansBold;
	private Typeface openSansItalic;
	private Typeface openSansRegular;
	private Typeface myAppIcomTheme;

	private static TypeFaceUtil instance;
	
	//enum
	public static enum EnumCustomTypeFace{
		OPEN_SANS_BOLD,
		OPEN_SANS_ITALIC,
		OPEN_SANS_REGULAR,
		APP_ICON_THEME
		}
	
	/**
	 * import all of the typefaces
	 * @param context
	 */
	public TypeFaceUtil(Context context){
		mContext = context;
		openSansBold= Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Bold.ttf");
		openSansItalic= Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Italic.ttf");
		openSansRegular= Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
		myAppIcomTheme = Typeface.createFromAsset(mContext.getAssets(), "Tradus.ttf");
	}
	
	/**
	 * static method to get instance
	 * @param context
	 * @return
	 */
	public static TypeFaceUtil getInstance(Context context){
		if(instance == null)
			instance = new TypeFaceUtil(context);
		
		return instance;
	}
	
	/**
	 * sets the typeface for a button
	 * @param customTypeFace: Enum for identifying typeface
	 * @param button: typeface to be applied
	 */
	public void setCustomTypeFace(EnumCustomTypeFace customTypeFace, Button button){
		button.setTypeface(getCustomTypeFace(customTypeFace));
	}	
	
	
	/**
	 * sets typeface on a textview			
	 * @param customTypeFace
	 * @param textView
	 */
	public void setsetCustomTypeFace(EnumCustomTypeFace customTypeFace, TextView textView){
		textView.setTypeface(getCustomTypeFace(customTypeFace));
	}
	
	
	/**
	 * 
	 * @param customTypeFace
	 * @return
	 */
	public Typeface getCustomTypeFace(EnumCustomTypeFace customTypeFace){
		switch(customTypeFace){
		case OPEN_SANS_BOLD: return openSansBold;  
		case OPEN_SANS_REGULAR: return openSansRegular;  
		case OPEN_SANS_ITALIC: return openSansItalic;  
		case APP_ICON_THEME: return myAppIcomTheme; 
		default: return myAppIcomTheme;
		}
		
		
	
	}
	
	
}


