package com.takemeoutto.custompicker;

import java.util.Date;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.AsyncResult;
import org.appcelerator.kroll.common.TiMessenger;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

@Kroll.proxy(creatableInModule = CustompickerModule.class)
public class CustomPickerProxy extends TiViewProxy {

	private static final String LCAT = "CustomPickerProxy";

	private static final int MSG_SET_DIVIDERS_COLOR = TiViewProxy.MSG_LAST_ID + 4001;
	private static final int MSG_SET_FOREGROUND_COLOR = TiViewProxy.MSG_LAST_ID + 4002;
	private static final int MSG_SET_MODE_24 = TiViewProxy.MSG_LAST_ID + 4003;
	private static final int MSG_SET_TIME = TiViewProxy.MSG_LAST_ID + 4004;
	private static final int MSG_SET_SEPARATOR_SIZE = TiViewProxy.MSG_LAST_ID + 4005;
	
	public CustomPickerProxy() {
		super();
		Log.d(LCAT, "Class constructor called");
	}

	@Override
	public TiUIView createView(Activity activity) {
		return new CustomPickerView(this, activity);
	}

	@Override
	public void handleCreationDict(KrollDict options) {
		super.handleCreationDict(options);
	}
	
	@Override
	public boolean handleMessage(Message message) {
		AsyncResult result = null;
		if (message != null) {
			result = (AsyncResult) message.obj;
		}
		postOnMain(result.getArg(), message.what);
		
		if (result != null) {
			result.setResult(null);
		}
		return true;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setDividersColor (String colorString) {
		int color;
		try {
			color = TiConvert.toColor(colorString);
			if (TiApplication.isUIThread()) {
				postOnMain(color, MSG_SET_DIVIDERS_COLOR);
			} else {
				TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_DIVIDERS_COLOR), color);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Kroll.getProperty @Kroll.method
	public Object getDividersColor () {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getDividersColor();
		}
		return null;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setForegroundColor (String colorString) {
		int color;
		try {
			color = TiConvert.toColor(colorString);
			if (TiApplication.isUIThread()) {
				postOnMain(color, MSG_SET_FOREGROUND_COLOR);
			} else {
				TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_FOREGROUND_COLOR), color);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();			
		}
	}
	
	@Kroll.getProperty @Kroll.method
	public Object getForegroundColor () {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getForegroundColor();
		}
		return null;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setFormat24 (boolean format24) {
		if (TiApplication.isUIThread()) {
			postOnMain(format24, MSG_SET_MODE_24);
		} else {
			TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_MODE_24), format24);
		}
	}
	
	@Kroll.getProperty @Kroll.method
	public Object getFormat24 () {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getFormat24();
		}
		return null;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setSeparatorSize(Object size) {
		if (TiApplication.isUIThread()) {
			postOnMain(size, MSG_SET_SEPARATOR_SIZE);
		} else {
			TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_SEPARATOR_SIZE), size);
		}
	}
	
	@Kroll.getProperty @Kroll.method
	public Object getSeparatorSize() {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getSeparatorSize();
		}
		return null;
	}

	
	@Kroll.setProperty @Kroll.method
	public void setValue (Object timeObject) {
		if (TiApplication.isUIThread()) {
			postOnMain(timeObject, MSG_SET_TIME);
		} else {
			TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_TIME), timeObject);
		}
	}
	@Kroll.getProperty @Kroll.method
	public Object getValue () {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getTime();
		}
		return null;
	}
	
	public void postOnMain (final Object arg, final int message) {
		TiMessenger.postOnMain(new Thread() {
			@Override
			public void run () {
				CustomPickerView view = (CustomPickerView)getOrCreateView();
				switch (message) {
					case MSG_SET_DIVIDERS_COLOR: {
						Integer color = (Integer)arg;
						view.setDividersColor(color);
						break;
					}
					case MSG_SET_FOREGROUND_COLOR: {
						Integer color = (Integer)arg;
						view.setForegroundColor(color);
						break;
					}
					case MSG_SET_MODE_24: {
						Boolean format24 = (Boolean)arg;
						view.setFormat24(format24);
						break;
					}
					case MSG_SET_TIME: {
						Date time = TiConvert.toDate(arg);
						view.setTime(time);
						break;
					}
					case MSG_SET_SEPARATOR_SIZE: {
						Float size = TiConvert.toFloat(arg);
						view.setSeparatorSize(size);
						break;
					}
					default: {
						return;
					}
				}
			}
		});
	}
}
