/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2012 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.modules.titanium.ui.widget;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiUIHelper;
import org.appcelerator.titanium.view.TiUIView;

import android.content.res.ColorStateList;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TiUIProgressBar extends TiUIView
{

	private TextView label;
	private ProgressBar progress;
	private LinearLayout view;

	public TiUIProgressBar(final TiViewProxy proxy)
	{
		super(proxy);

		view = new LinearLayout(proxy.getActivity()) {
			@Override
			protected void onLayout(boolean changed, int left, int top, int right, int bottom)
			{
				super.onLayout(changed, left, top, right, bottom);
				TiUIHelper.firePostLayoutEvent(proxy);
			}
		};
		view.setOrientation(LinearLayout.VERTICAL);
		label = new TextView(proxy.getActivity());
		label.setGravity(Gravity.TOP | Gravity.LEFT);
		label.setPadding(0, 0, 0, 0);
		label.setSingleLine(false);

		progress = new ProgressBar(proxy.getActivity(), null, android.R.attr.progressBarStyleHorizontal);
		progress.setIndeterminate(false);
		progress.setMax(1000);

		view.addView(label);
		view.addView(progress);

		setNativeView(view);
	}

	@Override
	public void processProperties(KrollDict d)
	{
		super.processProperties(d);

		if (d.containsKey(TiC.PROPERTY_MESSAGE)) {
			handleSetMessage(TiConvert.toString(d, TiC.PROPERTY_MESSAGE));
		}
		if (d.containsKey(TiC.PROPERTY_COLOR)) {
			final int color = TiConvert.toColor(d, TiC.PROPERTY_COLOR);
			handleSetMessageColor(color);
		}
		if (d.containsKey(TiC.PROPERTY_TINT_COLOR)) {
			handleSetTintColor(TiConvert.toColor(d, TiC.PROPERTY_TINT_COLOR));
		}
		if (d.containsKey(TiC.PROPERTY_TRACK_TINT_COLOR)) {
			handleSetTrackTintColor(TiConvert.toColor(d, TiC.PROPERTY_TRACK_TINT_COLOR));
		}
		updateProgress();
	}

	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy)
	{
		super.propertyChanged(key, oldValue, newValue, proxy);

		if (key.equals(TiC.PROPERTY_VALUE) || key.equals("min") || key.equals("max")) {
			updateProgress();
		} else if (key.equals(TiC.PROPERTY_MESSAGE)) {
			String message = TiConvert.toString(newValue);
			if (message != null) {
				handleSetMessage(message);
			}
		} else if (key.equals(TiC.PROPERTY_COLOR)) {
			final int color = TiConvert.toColor(TiConvert.toString(newValue));
			handleSetMessageColor(color);
		} else if (key.equals(TiC.PROPERTY_TINT_COLOR)) {
			int tintColor = TiConvert.toColor(TiConvert.toString(newValue));
			handleSetTintColor(tintColor);
		} else if (key.equals(TiC.PROPERTY_TRACK_TINT_COLOR)) {
			int trackTintColor = TiConvert.toColor(TiConvert.toString(newValue));
			handleSetTrackTintColor(trackTintColor);
		}
	}

	private double getMin()
	{
		Object value = proxy.getProperty("min");
		if (value == null) {
			return 0;
		}

		return TiConvert.toDouble(value);
	}

	private double getMax()
	{
		Object value = proxy.getProperty("max");
		if (value == null) {
			return 0;
		}

		return TiConvert.toDouble(value);
	}

	private double getValue()
	{
		Object value = proxy.getProperty(TiC.PROPERTY_VALUE);
		if (value == null) {
			return 0;
		}

		return TiConvert.toDouble(value);
	}

	private int convertRange(double min, double max, double value, int base)
	{
		return (int) Math.floor((value / (max - min)) * base);
	}

	public void updateProgress()
	{
		progress.setProgress(convertRange(getMin(), getMax(), getValue(), 1000));
	}

	public void handleSetMessage(String message)
	{
		label.setText(message);
		label.requestLayout();
	}

	protected void handleSetMessageColor(int color)
	{
		label.setTextColor(color);
	}

	protected void handleSetTintColor(int color)
	{
		ColorStateList singleColorStateList = ColorStateList.valueOf(color);
		progress.setProgressTintList(singleColorStateList);
	}

	protected void handleSetTrackTintColor(int color)
	{
		ColorStateList singleColorStateList = ColorStateList.valueOf(color);
		progress.setProgressBackgroundTintList(singleColorStateList);
	}
}
