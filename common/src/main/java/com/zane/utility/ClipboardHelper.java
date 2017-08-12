package com.zane.utility;

import android.content.Context;
import android.text.ClipboardManager;

@SuppressWarnings("deprecation")
public class ClipboardHelper {
	public static void copyText(Context c, CharSequence text) {
		if (null == text) {
			return;
		}
		
		ClipboardManager cm = (ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
		cm.setText(text);
	}
	
	public static CharSequence getText(Context c) {
		ClipboardManager cm = (ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
		return cm.getText();
	}
}
