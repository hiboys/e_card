package com.example.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
 
public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
    //add by wuyix
    public static HttpResponse doConnectByGet(String url, Map<String, Object> params) {

		HttpResponse response = null;

		if (params != null) {
			StringBuffer sb = new StringBuffer();

			for (Map.Entry<String, Object> entry : params.entrySet()) {
				try {
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue()
									.toString(), "UTF-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
			sb.deleteCharAt(sb.length() - 1);
			url += url + "?" + sb.toString();
		}
		HttpGet get = new HttpGet(url);

		try {
			response = new DefaultHttpClient().execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
    
}