package com.takemeoutto.custompicker;

import java.util.Calendar;
import java.util.Date;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

public class CustomPickerView extends TiUIView {
	
	private static final String LCAT = "CustomPickerView";
	
	private static final String PROPERTY_DIVIDERS_COLOR = "dividersColor";
	private static final String PROPERTY_FOREGROUND_COLOR = "foregroundColor";
	private static final String PROPERTY_FORMAT_24 = "format24";
	private static final String PROPERTY_SEPARATOR_SIZE = "separatorSize";
	private static final String PROPERTY_VALUE = "value";
	private static final String PROPERTY_INNER_PADDING = "innerPadding";

	private ExtendedTimePicker timePicker;
	
	public CustomPickerView(TiViewProxy proxy) {
		this(proxy, null);
	}
	
	public CustomPickerView(TiViewProxy proxy, Context context) {
		super(proxy);
		Log.d(LCAT, "Constructor called");
		if (context != null) {
			initView(context);
		} else {
			initView(proxy.getActivity());
		}
	}
	
	private void initView(Context context) {
		View nativeView;
		String packageName = context.getPackageName();
		Resources res = context.getResources();
		int layout = res.getIdentifier("layout", "layout", packageName);
		int timePickerId = res.getIdentifier("timePickerId", "id", packageName);
		
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		nativeView = layoutInflater.inflate(layout, null);
		timePicker = (ExtendedTimePicker)nativeView.findViewById(timePickerId);
		
		/*LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(layoutParams);

		timePicker = new ExtendedTimePicker(context, attribute);
		timePicker.setIs24HourView(false);
		*/
		if (!proxy.hasProperty(PROPERTY_VALUE)) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
			c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
			proxy.setProperty(PROPERTY_VALUE, c.getTime());
		}

		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			public void onTimeChanged(TimePicker picker, int h, int m) {
				KrollDict d = new KrollDict();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, h);
				c.set(Calendar.MINUTE, m);
				d.put("value", c.getTime());
				CustomPickerView.this.proxy.setProperty(PROPERTY_VALUE, c.getTime());
				CustomPickerView.this.proxy.fireEvent("change", d);
			}
		});
		// layout.addView(timePicker);
		setNativeView(nativeView);
	}

	@Override
	public void processProperties(KrollDict d) {
		super.processProperties(d);
		if (d.containsKey(PROPERTY_FORMAT_24)) {
			try {
				timePicker.setIs24HourView(TiConvert.toBoolean(d, PROPERTY_FORMAT_24, false));
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		if (d.containsKey(PROPERTY_DIVIDERS_COLOR)) {
			try {
				timePicker.setDividersColor(TiConvert.toColor(d, PROPERTY_DIVIDERS_COLOR));
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		if (d.containsKey(PROPERTY_FOREGROUND_COLOR)) {
			try {
				timePicker.setForegroundColor(TiConvert.toColor(d, PROPERTY_FOREGROUND_COLOR));
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		if (d.containsKey(PROPERTY_SEPARATOR_SIZE)) {
			try {
				timePicker.setSeparatorSize(TiConvert.toFloat(d, PROPERTY_SEPARATOR_SIZE));			
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (d.containsKey(PROPERTY_VALUE)) {
			try {
				Date time = TiConvert.toDate(d, PROPERTY_VALUE);
				Calendar c = Calendar.getInstance();
				c.setTime(time);
				timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
				timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		if (d.containsKey(PROPERTY_INNER_PADDING)) {
			try {
				timePicker.setInnerPadding(TiConvert.toInt(d, PROPERTY_INNER_PADDING));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
		if (newValue == null) {
			return;
		}
		try {
			if (key.equals(PROPERTY_DIVIDERS_COLOR)) {
				int color = TiConvert.toColor((String)newValue);
				timePicker.setDividersColor(color);
			} else if (key.equals(PROPERTY_FOREGROUND_COLOR)) {
				int color = TiConvert.toColor((String)newValue);
				timePicker.setForegroundColor(color);
			} else if (key.equals(PROPERTY_FORMAT_24)) {
				boolean format24 = TiConvert.toBoolean(newValue);
				timePicker.setIs24HourView(format24);
			} else if (key.equals(PROPERTY_SEPARATOR_SIZE)) {
				float size = TiConvert.toFloat(newValue);
				timePicker.setSeparatorSize(size);
			} else if (key.equals(PROPERTY_INNER_PADDING)) {
				int padding = TiConvert.toInt(newValue);
				timePicker.setInnerPadding(padding);
			} else {
				super.propertyChanged(key, oldValue, newValue, proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDividersColor(int color) {
		timePicker.setDividersColor(color);
	}
	
	public Object getDividersColor() {
		if (proxy.hasProperty(PROPERTY_DIVIDERS_COLOR)) {
			return proxy.getProperty(PROPERTY_DIVIDERS_COLOR);
		}
		return null;
	}

	public void setForegroundColor(int color) {
		timePicker.setForegroundColor(color);
	}
	
	public Object getForegroundColor() {
		if (proxy.hasProperty(PROPERTY_FOREGROUND_COLOR)) {
			return proxy.getProperty(PROPERTY_FOREGROUND_COLOR);
		}
		return null;
	}
	
	public void setFormat24 (boolean format24) {
		timePicker.setIs24HourView(format24);
	}
	
	public Object getFormat24() {
		if (proxy.hasProperty(PROPERTY_FORMAT_24)) {
			return proxy.getProperty(PROPERTY_FORMAT_24);
		}
		return null;
	}
	
	public void setSeparatorSize(float size) {
		timePicker.setSeparatorSize(size);
	}
	
	public Object getSeparatorSize() {
		if (proxy.hasProperty(PROPERTY_SEPARATOR_SIZE)) {
			return proxy.getProperty(PROPERTY_SEPARATOR_SIZE);
		}
		return null;
	}
	
	public void setTime(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
	}

	public Object getTime() {
		if (proxy.hasProperty(PROPERTY_VALUE)) {
			return proxy.getProperty(PROPERTY_VALUE);
		}
		return null;
	}
	
	public void setInnerPadding(int padding) {
		timePicker.setInnerPadding(padding);
	}
	
	public Object getInnerPadding() {
		if (proxy.hasProperty(PROPERTY_INNER_PADDING)) {
			return proxy.getProperty(PROPERTY_INNER_PADDING);
		}
		return null;
	}
}
