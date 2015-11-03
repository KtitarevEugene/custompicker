package com.takemeoutto.custompicker;

import java.util.Date;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.AsyncResult;
import org.appcelerator.kroll.common.TiMessenger;
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
	private static final int MSG_SET_INNER_PADDING = TiViewProxy.MSG_LAST_ID + 4006;
	
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
		switch (message.what) {
			case MSG_SET_DIVIDERS_COLOR: {
				AsyncResult result = (AsyncResult)message.obj;
				handleMessageSetDividersColor(result.getArg());
				result.setResult(null);
				return true;
			}
			case MSG_SET_FOREGROUND_COLOR: {
				AsyncResult result = (AsyncResult)message.obj;
				handleMessageSetForegroundColor(result.getArg());
				result.setResult(null);
				return true;
			}
			case MSG_SET_MODE_24: {
				AsyncResult result = (AsyncResult)message.obj;
				handleMessageSetMode24(result.getArg());
				result.setResult(null);
				return true;
			}
			case MSG_SET_TIME: {
				AsyncResult result = (AsyncResult)message.obj;
				handleMessageSetTime(result.getArg());
				result.setResult(null);
				return true;
			}
			case MSG_SET_SEPARATOR_SIZE: {
				AsyncResult result = (AsyncResult)message.obj;
				handleMessageSetSeparatorSize(result.getArg());
				result.setResult(null);
				return true;
			}
			case MSG_SET_INNER_PADDING: {
				AsyncResult result = (AsyncResult)message.obj;
				handleMessageSetInnerPadding(result.getArg());
				result.setResult(null);
				return true;
			}
			default: {
				return super.handleMessage(message);
			}
		}
	}
	
	@Kroll.setProperty @Kroll.method
	public void setDividersColor (String colorString) {
		int color;
		try {
			color = TiConvert.toColor(colorString);
			TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_DIVIDERS_COLOR), color);
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
			TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_FOREGROUND_COLOR), color);
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
		TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_MODE_24), format24);
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
		TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_SEPARATOR_SIZE), size);
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
		TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_TIME), timeObject);
	}
	
	@Kroll.getProperty @Kroll.method
	public Object getValue () {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getTime();
		}
		return null;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setInnerPadding (Object padding) {
		TiMessenger.sendBlockingMainMessage(getMainHandler().obtainMessage(MSG_SET_INNER_PADDING), padding);
	}
	
	@Kroll.getProperty @Kroll.method
	public Object getInnerPadding () {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			return view.getInnerPadding();
		}
		return null;
	}
	
	private void handleMessageSetDividersColor(Object arg) {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			Integer color = (Integer)arg;
			view.setDividersColor(color);		
		}
	}
	
	private void handleMessageSetForegroundColor(Object arg) {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			Integer color = (Integer)arg;
			view.setForegroundColor(color);
		}
	}
	
	private void handleMessageSetMode24(Object arg) {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			Boolean format24 = (Boolean)arg;
			view.setFormat24(format24);
		}
	}
	
	private void handleMessageSetTime(Object arg) {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			Date time = TiConvert.toDate(arg);
			view.setTime(time);
		}
	}
	
	private void handleMessageSetSeparatorSize(Object arg) {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			Float size = TiConvert.toFloat(arg);
			view.setSeparatorSize(size);
		}
	}
	
	private void handleMessageSetInnerPadding(Object arg) {
		CustomPickerView view = (CustomPickerView)getOrCreateView();
		if (view != null) {
			Integer padding = TiConvert.toInt(arg);
			view.setInnerPadding(padding);
		}
	}
}
