package com.example.e_card;

import android.app.Application;
import android.graphics.Bitmap;

public class GrobalApplication extends Application {
	private Bitmap mBitmap;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.mBitmap = bitmap;
	}
}
