package com.example.e_card;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class EImageView extends ImageView {

	private static final String TAG = "ETextView";
	public static final int STATE_STOP = 0;  
	public static final int STATE_MOVE = 1; 
	public int mState;
	public int mPreviousx = 0;
	public int mPreviousy = 0;
	
	// Initial the view.
	public EImageView(Context context, AttributeSet attribute) {
		this(context, attribute, 0);
		
	}

	// Initial the view.
	public EImageView(Context context, AttributeSet attribute, int style) {
		super(context, attribute, style);
		
		this.setImageResource( R.drawable.ic_launcher );
		
		this.setFocusable( true );
		this.setFocusableInTouchMode( true );
		
		mState = STATE_STOP;
		
		
		
		
		this.setOnTouchListener( new OnTouchListener()
		{

			/*public boolean onTouch(View arg0, MotionEvent event ) {
				// TODO Auto-generated method stub
				
				EImageView etextview = (EImageView)arg0;
				final int iAction = event.getAction();
				final int iCurrentx = (int)event.getX();
				final int iCurrenty = (int)event.getY();
				switch(iAction)
				{
				case MotionEvent.ACTION_DOWN:
					Log.e(TAG,"ACTION_DOWN");
					//etextview.mState = STATE_;
					etextview.mPreviousx = iCurrentx;
					etextview.mPreviousy = iCurrenty;
					
					break;
				case MotionEvent.ACTION_MOVE:
					Log.e(TAG, "ACTION_MOVE");
					etextview.mState = STATE_MOVE;
					int iDeltx = iCurrentx - etextview.mPreviousx;
					int iDelty = iCurrenty - etextview.mPreviousy;
					final int iLeft = etextview.getLeft();
					final int iTop = etextview.getTop();
					if(iDeltx != 0 || iDelty != 0)
						etextview.layout(iLeft + iDeltx, 
								iTop + iDelty, 
								iLeft + iDeltx + etextview.getWidth(), 
								iTop + iDelty + etextview.getHeight());
					etextview.mPreviousx = iCurrentx - iDeltx;
					etextview.mPreviousy = iCurrenty - iDelty;
					break;
				case MotionEvent.ACTION_UP:
					Log.e(TAG,"ACTION_UP");
					//mState = STATE_MOVE;
					break;
				case MotionEvent.ACTION_CANCEL:
					Log.e(TAG, "ACTION_CANCEL");
					etextview.mState = STATE_STOP;
					break;
				}
				return false;
			}
			*/
			public boolean onTouch(View v, MotionEvent event) {
				LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
				switch (event.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					int x_cord = (int) event.getRawX();
					int y_cord = (int) event.getRawY();

					
					layoutParams.leftMargin = (int) (x_cord-25) ;
					layoutParams.topMargin = (int) (y_cord-50) ;
					v.setLayoutParams(layoutParams);
					break;
				default:
					break;
				}
				return false;
			}
			
		});
		
	}
	
	public void setLocation( )
	{
		
	}
}