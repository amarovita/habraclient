package ru.client.habr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;

/**
 * @author WNeZRoS
 * ����� ������������ ��� �������� � web �������� �������� GET � POST
 */
public final class URLClient {
	
	/**
	 * USER AGENT ��� �������� � ��� ��������
	 */
	public final static String USER_AGENT = "Mozilla/5.0 (Linux; Android) WebKit (KHTML, like Gecko) HabraClient 1.0";
	private static URLClient mUrlClient = null;
	
	
	private DefaultHttpClient mHttpClient = null;
	private boolean mLocked = false;
	
	/**
	 * ����� ������������ ��� �������� � web �������� �������� GET � POST
	 */
	public URLClient() {
		Log.d("URLClient.URLClient", "construct");
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		HttpConnectionParams.setSoTimeout(httpParams, 90000);
		
		mHttpClient = new DefaultHttpClient(httpParams);
		mHttpClient.getParams().setParameter("http.useragent", USER_AGENT);
	}
	
	/**
	 * �������� ��������� URLClient
	 * @return ��������� URLClient
	 */
	public static URLClient getUrlClient() {
		Log.d("URLClient.getUrlClient", "called");
		
		if(mUrlClient == null) {
			Log.i("URLClient.getUrlClient", "creating new URLClient");
			mUrlClient = new URLClient();
		}
		
		return mUrlClient;
	}
	
	/**
	 * ��������� ���� � ������
	 * @param cookies ������ ������� ��� null
	 */
	public void insertCookies(Cookie[] cookies) {
		Log.d("URLClient.insertCookies", "called with (" 
				+ (cookies == null ? "null" : "Cookie["+cookies.length+"]") + ")");
		
		if(cookies == null) return;

		for(int i = 0; i < cookies.length; i++) {
			this.insertCookie(cookies[i]);
		}
	}
	
	/**
	 * ��������� ���� � ������
	 * @param cookie ����
	 */
	public void insertCookie(Cookie cookie) {
		Log.d("URLClient.insertCookie", "called witch cookie name " + cookie.getName());
		Log.d("URLClient.insertCookie", " + " + cookie.getValue() + "  | " 
				+ cookie.getDomain() + " | " + cookie.getPath());
		
		mHttpClient.getCookieStore().addCookie(cookie);
	}

	/**
	 * ������ url ������� GET
	 * @param url URL ��� �������
	 * @return HTML ��� ��������
	 */
	public String getURL(String url) { 
		HttpGet httpGet = new HttpGet(url);
		
		try {
			while(mLocked) Thread.sleep(50);
		} catch (InterruptedException e1) {
			Log.e("URLClient.getURL", "InterruptedException: " + e1.getMessage());
		}
		
		try {
			mLocked = true;
			HttpResponse httpResponse = mHttpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			mLocked = false;
			
			if(httpEntity != null) {
				return EntityUtils.toString(httpEntity);
			}
		} catch (ClientProtocolException e) {
			Log.e("URLClient.getURL", "ClientProtocolException: " + e.getMessage());
		} catch (IOException e) {
			Log.e("URLClient.getURL", "IOException: " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * ������ url ������� GET
	 * @param url URL ��� �������
	 * @return HTML ��� ��������
	 */
	public byte[] getURLAsBytes(String url) { 
		HttpGet httpGet = new HttpGet(url);
		
		try {
			while(mLocked) Thread.sleep(50);
		} catch (InterruptedException e1) {
			Log.e("URLClient.getURL", "InterruptedException: " + e1.getMessage());
		}
		
		try {
			mLocked = true;
			HttpResponse httpResponse = mHttpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			mLocked = false;
			
			if(httpEntity != null) {
				return EntityUtils.toByteArray(httpEntity);
			}
		} catch (ClientProtocolException e) {
			Log.e("URLClient.getURLAsBytes", "ClientProtocolException: " + e.getMessage());
		} catch (IOException e) {
			Log.e("URLClient.getURLAsBytes", "IOException: " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * ������ url ������� POST
	 * @param url URL ��� �������
	 * @param post POST ��������� ��� null
	 * @param referer URL ��������� ������� ��� null
	 * @return HTML ��� ��������
	 */
	public String postURL(String url, String[][] post, String referer) { 
		HttpPost httpPost = new HttpPost(url);
		if(referer != null)	httpPost.addHeader("Referer", referer);
		
		if(post != null) {
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			for(int i = 0; i < post.length; i++) {
				nvps.add(new BasicNameValuePair(post[i][0], post[i][1]));
			}
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				Log.e("URLClient.postURL", "UnsupportedEncodingException: " + e1.getMessage());
			}
		}
		
		try {
			while(mLocked) Thread.sleep(50);
		} catch (InterruptedException e1) {
			Log.e("URLClient.getURL", "InterruptedException: " + e1.getMessage());
		}
		
		try {
			mLocked = true;
			HttpResponse httpResponse = mHttpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			mLocked = false;
			
			if(httpEntity != null) {
				String httpResult = EntityUtils.toString(httpEntity);
				return httpResult == null ? "null" : httpResult;
			}
		} catch (ClientProtocolException e) {
			Log.e("URLClient.postURL", "ClientProtocolException: " + e.getMessage());
		} catch (IOException e) {
			Log.e("URLClient.postURL", "IOException: " + e.getMessage());
		}

		return null;
	}

	/**
	 * ����� ��� ��������� Cookie �������� HTTP �������
	 * @return ������ ��������
	 */
	public Cookie[] getCookies() {
		return mHttpClient.getCookieStore().getCookies().toArray(new Cookie[0]);
	}

	/**
	 * ����������� ������ ��� �������� � WebView
	 * @param code �����
	 * @return ������
	 */
	public static String encode(String code) {
		return code.replace("%", "%25").replace("#", "%23").replace("\\", 
				"%27").replace("?", "%3F");
	}
}
