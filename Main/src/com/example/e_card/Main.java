package com.example.e_card;

import java.util.ArrayList;

import com.example.e_card.R.layout;
import com.example.e_card.GrobalApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Main extends Activity {
	/*-----------------------------Jiahao------------------------*/
	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;

	private RelativeLayout composerButtonsShowHideButton;
	/*-----------------------------Jiahao------------------------*/
	private ArrayList<EImageView> list = new ArrayList<EImageView>();
	private EImageView name = null;
	private EImageView company = null;
	RelativeLayout layout = null;
	private int viewId = 0;
	private OnLongClickListener longclicklistener = new OnLongClickListener() {

			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("TAG", "longclick");
				Intent intent = new Intent(Main.this, EditActivity.class );
				startActivityForResult( intent, 0 );
				
				return false;
			}
	};
	
	//����6 ����������ͼƬ����
	private int mode =0;
	private float startdis =0;
	private float enddis =0;
	private Matrix matrix = new Matrix();
	private Bitmap curBitmap = null;
	private Bitmap newBitmap = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        name = new EImageView( this, null );
        name.setId( viewId++ );
        name.setImageResource( R.drawable.a );
        name.setOnLongClickListener( longclicklistener );
        name.setScaleType( ScaleType.MATRIX );
        list.add( name );
        
        company = new EImageView( this, null );
        company.setId( viewId++ );
        company.setImageResource( R.drawable.a );
        company.setScaleType( ScaleType.MATRIX );
        company.setOnLongClickListener( longclicklistener );
        list.add( company );
        
        //layout = new RelativeLayout( this );
        layout = (RelativeLayout) findViewById(R.id.main_layout);
        layout.setBackgroundResource( R.drawable.background );
        layout.addView( name , new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        layout.addView( company , new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        //setContentView( layout );
        
        /*-----------------------------Jiahao------------------------*/
        MyAnimations.initOffset(Main.this);

		findViews();
		setListener();

		// �ӺŵĶ���
		composerButtonsShowHideButton.startAnimation(MyAnimations.getRotateAnimation(0, 360, 200));
		/*-----------------------------Jiahao------------------------*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if( requestCode == 0 ) {
    		if( resultCode == 0 ) {
    			//add by wuyix
    			Bundle bundle = data.getExtras();
    			GrobalApplication ga = (GrobalApplication) getApplication();
				Bitmap b = ga.getBitmap();
				name.setImageBitmap(b);
    			if( bundle.containsKey( "info" )==true ) {
    				String str = data.getExtras().getString("info");
        			//name.setImageResource( R.drawable.a );
    				
    			}
    		}
    	}
    }
    
    @Override
    public boolean onTouchEvent( MotionEvent event ) {
    	
    	float scale = 1.0f;
    	
    	switch( event.getAction() & MotionEvent.ACTION_MASK ) {
    	case MotionEvent.ACTION_POINTER_DOWN:
    		mode = 1;
    		startdis = distance( event );
    		break;
    		
    	case MotionEvent.ACTION_MOVE:
    		if( mode ==1 ) {
    			enddis = distance( event );
    			float t = enddis/startdis;
    			if(  (t> 1.1 || t < 0.9 ) && t<3 ) {
    				scale = t;
    			}
    		}
    		break;
    	case MotionEvent.ACTION_POINTER_UP:
    	case MotionEvent.ACTION_UP:
    		mode = 0;
    		break;
    	}
    	
    	if( mode ==1 )
    		changeView(scale);
    	
    	return true;
    }
    
    protected void changeView( float scale ) {
    	EImageView curView = (EImageView)layout.findFocus();
    	if( curView == null) {
    		Log.v("abc", "null");
    		return ;
    	}
    	
    	Drawable drawable = curView.getDrawable();
    	curBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
    	matrix.postScale( scale, scale );
    	newBitmap = Bitmap.createBitmap(curBitmap, 0, 0, curBitmap.getWidth(), curBitmap.getHeight(), matrix, true );
    	list.remove(curView);
    	layout.removeView( curView );
    	EImageView newView = new EImageView( this, null );
    	newView.setId( curView.getId() );
    	newView.setImageBitmap( newBitmap );
    	layout.addView( newView );
    	list.add( newView );
    	setContentView( layout );
    }
    
    protected float distance( MotionEvent event ) {
    	float x = event.getX(0) - event.getX(1);
    	float y = event.getY(0) - event.getY(1);
    	return FloatMath.sqrt( x*x + y*y );
    }
    
    /*-----------------------------Jiahao------------------------*/
	private void findViews() {
		composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
		composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);
	}

	private void setListener() {
		// ���ť���õ���¼�
		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!areButtonsShowing) {
					// ͼ��Ķ���
					MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
					// �ӺŵĶ���
					composerButtonsShowHideButtonIcon.startAnimation(MyAnimations.getRotateAnimation(0, -225, 300));
				} else {
					// ͼ��Ķ���
					MyAnimations.startAnimationsOut(composerButtonsWrapper, 300);
					// �ӺŵĶ���
					composerButtonsShowHideButtonIcon.startAnimation(MyAnimations.getRotateAnimation(-225, 0, 300));
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});

		// ��Сͼ�����õ���¼�
		for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
			final ImageView smallIcon = (ImageView) composerButtonsWrapper.getChildAt(i);
			final int position = i;
			smallIcon.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// ����д����item�ĵ���¼�
					// 1.�ӺŰ�ť��С����ʧ ��С��animation
					// 2.����ť��С����ʧ ��С��animation
					// 3.�������ť�Ŵ����ʧ ͸���Ƚ��� �Ŵ󽥱��animation
					//composerButtonsShowHideButton.startAnimation(MyAnimations.getMiniAnimation(300));
					composerButtonsShowHideButtonIcon.startAnimation(MyAnimations.getRotateAnimation(-225, 0, 300));
					areButtonsShowing = !areButtonsShowing;
					//smallIcon.startAnimation(MyAnimations.getMaxAnimation(400));
					for (int j = 0; j < composerButtonsWrapper.getChildCount(); j++) {
						if (j != position) {
							final ImageView smallIcon = (ImageView) composerButtonsWrapper.getChildAt(j);
							smallIcon.startAnimation(MyAnimations.getMiniAnimation(300));
							smallIcon.setClickable(false);
						}
					}
				}
			});
		}
	}
	/*-----------------------------Jiahao------------------------*/
	//add by wuyix
	
}
