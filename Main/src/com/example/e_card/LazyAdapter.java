package com.example.e_card;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tools.*;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String> > data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String> > d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        //TextView name = (TextView) vi.findViewById(R.id.name);
 
        HashMap<String, String> font = new HashMap<String, String>();
        font = data.get(position);
        //name.setText(font.get(StaticVar.KEY_NAME));
        // Setting all values in listview
  
        imageLoader.DisplayImage(StaticVar.THUMB_URL_PREFIX+font.get(StaticVar.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}