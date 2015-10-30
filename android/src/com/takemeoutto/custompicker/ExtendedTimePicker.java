package com.takemeoutto.custompicker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ExtendedTimePicker extends TimePicker {
	
	private static final String LCAT = "ExtendedTimePicker";

	private static final String SELECTION_DIVIDER_FIELD_NAME = "mSelectionDivider";
	private static final String SELECTOR_WHEEL_PAINT = "mSelectorWheelPaint";
	
	private int separatorId;
	private Map<String,Integer> pickersIds;

	private int dividersColor;
	private int foregroundColor;
	private int innerPadding;

	public ExtendedTimePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initIds();
		Log.d(LCAT, "constructor called");
	}

	public ExtendedTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		initIds();
		Log.d(LCAT, "constructor called");
	}

	public ExtendedTimePicker(Context context) {
		super(context);
		initIds();
		Log.d(LCAT, "constructor called");
		setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + 20, getPaddingBottom());
	}

	private void initIds() {
		Resources system = Resources.getSystem();
		pickersIds = new HashMap<String, Integer> ();
		pickersIds.put("hour", system.getIdentifier("hour", "id", "android"));
		pickersIds.put("minute", system.getIdentifier("minute", "id", "android"));
		pickersIds.put("amPm", system.getIdentifier("amPm", "id", "android"));
		
		separatorId = system.getIdentifier("divider", "id", "android");
	}

	public void setDividersColor(int color) {
		dividersColor = color;
		Set<String> keys = pickersIds.keySet();
		for (String key : keys) {
			changeNumberPickerDividers(pickersIds.get(key), color);
		}
	}

	public void setForegroundColor(int color) {
		foregroundColor = color;
		Set<String> keys = pickersIds.keySet();
		for (String key : keys) {
			changeNumberPickerColor(pickersIds.get(key), color);
		}
		changeSeparatorColor(separatorId, color);
	}
	
	public void setSeparatorSize (float size) {
		TextView separator = (TextView)findViewById(separatorId);
		if (separator != null) {
			separator.setTextSize(size);
		}
	}
	
	public void setInnerPadding (int padding) {
		innerPadding = padding;
		NumberPicker picker = (NumberPicker)findViewById(pickersIds.get("hour"));
		if (picker != null) {
			picker.setX(picker.getX() + (float)padding / 2);		
		}
		picker = (NumberPicker)findViewById(pickersIds.get(is24HourView() ? "minute" : "amPm"));
		if (picker != null) {
			picker.setX(picker.getX() - (float)padding / 2);		
		}
	}
	
	public int getDividersColor() {
		return dividersColor;
	}
	
	public int getForegroundColor() {
		return foregroundColor;
	}
	
	public float getSeparatorSize() {
		TextView separator = (TextView)findViewById(separatorId);
		if (separator != null) {
			return separator.getTextSize();
		}
		return 0f;
	}
	
	public int getInnerPadding() {
		return innerPadding;
	}

	private void changeNumberPickerColor(int id, int color) {
		NumberPicker picker = (NumberPicker)findViewById(id);		
		if (picker != null) {
			int childrenCount = picker.getChildCount();
			Class<?> numberPickerWidget = null;
			Field wheelPaint = null;
			try {
				numberPickerWidget = Class.forName("android.widget.NumberPicker");
				wheelPaint = numberPickerWidget.getDeclaredField(SELECTOR_WHEEL_PAINT);
				wheelPaint.setAccessible(true);
				((Paint)wheelPaint.get(picker)).setColor(color);
				for (int i = 0; i < childrenCount; ++i) {
					View child = picker.getChildAt(i);
					if (child instanceof EditText) {
						((EditText)child).setTextColor(color);
					}
				}
				picker.invalidate();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void changeNumberPickerDividers(int id, int color) {
		NumberPicker picker = (NumberPicker)findViewById(id);
		if (picker != null) {
			Class<?> numberPickerWidget = null;
			Field divider = null;
			try {
				numberPickerWidget = Class.forName("android.widget.NumberPicker");
				divider = numberPickerWidget.getDeclaredField(SELECTION_DIVIDER_FIELD_NAME);
				divider.setAccessible(true);
				divider.set(picker, new ColorDrawable(color));
				picker.invalidate();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void changeSeparatorColor(int id, int color) {
		TextView separator = (TextView)findViewById(id);
		if (separator != null) {
			separator.setTextColor(color);
		}
	}
}
