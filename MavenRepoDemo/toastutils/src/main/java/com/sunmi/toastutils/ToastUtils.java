package com.sunmi.toastutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

	protected static final String TAG = "ToastUtils";
	public static Toast toast;
	/**
	 * 信息提示
	 * @param context
	 * @param content
	 */
	public static void makeToast(Context context, String content) {
		if(context==null)return;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * 短时间
	 * @param context
	 * @param resId
	 */
	public static void showShortText(Context context, int resId) {
		try {
			if(context==null)return;
			if(toast != null)
				toast.cancel();
			toast = Toast.makeText(context, context.getString(resId),Toast.LENGTH_SHORT);
			toast.show();
		} catch (Exception e) {
		}
	}
	public static void showShortText(Context context, CharSequence text) {
		if(context==null)return;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static void showLongText(Context context, int resId) {
		try {
			if(context==null)return;
			if(toast != null)
				toast.cancel();
			toast = Toast.makeText(context, context.getString(resId),Toast.LENGTH_LONG);
			toast.show();
			
		} catch (Exception e) {
		}
	}

	public static void showLongText(Context context, CharSequence text) {
		if(context==null)return;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, text,Toast.LENGTH_LONG);
		toast.show();
	}
	
	
	   /**
	    * Tocast在Top
	    * @param content
	    * @param ct
	    */
		public static void ShowTopTotast(String content, Context ct) {
			Toast toast = Toast.makeText(ct, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
		}

		/**
		 * Toast 在center
		 * @param content
		 * @param ct
		 */
		public static void ShowCenterTotast(String content, Context ct) {
			Toast toast = Toast.makeText(ct, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

		/**
		 * Tocast 在Bottom
		 * @param content
		 * @param ct
		 */
		public static void ShowBottomTotast(String content, Context ct) {
			Toast toast = Toast.makeText(ct, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM, 0, 0);
			toast.show();
		}
}
