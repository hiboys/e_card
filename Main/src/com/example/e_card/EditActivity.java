package com.example.e_card;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.tools.XMLParser;
import com.example.tools.Utils;
import com.example.widgets.*;
import com.example.widgets.AmbilWarnaDialog.OnAmbilWarnaListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class EditActivity extends Activity {

	private EditText edit = null;
	private Button button = null;
	private Button btn_colorpicker = null; // add by wuyix
	private Spinner spinner = null; // add by wuyix
	private LazyAdapter adapter; // add by wuyix
	private Button btn_cancel = null; //add by wuyix
	private String font_name = null; //add by wuyix

	private OnClickListener clicklistener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.button1) {
				//add by wuyix
				Bitmap mBitmap = getFontBitmap();
				if (mBitmap!=null) {
					GrobalApplication ga = (GrobalApplication)getApplication();
					ga.setBitmap(mBitmap);
				}
				String info = edit.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("info", info);
				setResult(0, intent);
				finish();
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		edit = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		btn_colorpicker = (Button) findViewById(R.id.btn_pickcolor); // add by
																		// wuyix
		btn_colorpicker.setOnClickListener(colorPickListener);
		button.setOnClickListener(clicklistener);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(1, intent);
				finish();
				
			}
		});
		spinner = (Spinner) findViewById(R.id.list); // add by wuyix
		initSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_edit, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Bundle bundle = new Bundle();
			Intent intent = new Intent();
			intent.putExtras(bundle);
			setResult(0, intent);
			finish();
		}
		return false;

	}

	/* add by wuyix */
	private OnClickListener colorPickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			createColorPickDialog();
		}
	};

	/* add by wuyix */
	public void createColorPickDialog() {

		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, edit.getTextColors().getDefaultColor(),
				new OnAmbilWarnaListener() {
					public void onOk(AmbilWarnaDialog dialog, int color) {
						// color is the color selected by the user.
						edit.setTextColor(color);
					}

					public void onCancel(AmbilWarnaDialog dialog) {
						// cancel was selected by the user
					}
				});

		dialog.show();
	}

	/* add by wuyix */
	public void initSpinner() {
		ArrayList<HashMap<String, String>> nameList = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		try {
			String xml = parser.getXmlFromUrl(StaticVar.URL); // getting XML
																// from URL
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(StaticVar.KEY_FONTS);
			// looping through all song nodes <song>
			System.out.print(nl.getLength());
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(StaticVar.KEY_ID, parser.getValue(e, StaticVar.KEY_ID));
				map.put(StaticVar.KEY_NAME,
						parser.getValue(e, StaticVar.KEY_NAME));
				map.put(StaticVar.KEY_THUMB_URL,
						parser.getValue(e, StaticVar.KEY_THUMB_URL));
				// adding HashList to ArrayList
				nameList.add(map);
			}
			spinner = (Spinner) findViewById(R.id.list);
			// Getting adapter by passing xml data ArrayList
			adapter = new LazyAdapter(this, nameList);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(spinnerItemSelectedListener);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/*add by wuyix*/
	public Bitmap getFontBitmap()
	{
		Bitmap b = null;
		HttpResponse response = null;
		Map<String, Object> httpGetParams = null;
		httpGetParams = new HashMap<String, Object>();
		httpGetParams.put("text", edit.getText());
		httpGetParams.put("color", Integer.toHexString(edit
				.getTextColors().getDefaultColor()));
		httpGetParams.put("font", font_name);
		// 获得一个输入流
		try {
			response = Utils.doConnectByGet(StaticVar.GET_FONT_URL, httpGetParams);
			if (response != null) {
				HttpEntity httpEntity = response.getEntity();
				InputStream is = httpEntity.getContent();
				System.out.println("Get, Yes!");
				b = BitmapFactory.decodeStream(is);
				is.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return b;
	}
	
	//add by wuyix
	private OnItemSelectedListener spinnerItemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			font_name = ((Map<String, String>)adapter.getItem(arg2)).get(StaticVar.KEY_NAME);
			System.out.print(font_name+"\n");
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
}
