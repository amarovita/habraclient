package ru.habrahabr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Habrahabr extends Activity {
    
	WebView mResultView = null;
	URLClient urlClient = null;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d("onCreate", "SharedPref");
        
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences.Editor preferencesEditor = preferences.edit();
        
        urlClient = new URLClient();
        
        mResultView = (WebView) findViewById(R.id.result);
        mResultView.getSettings().setAllowFileAccess(true);
        mResultView.getSettings().setJavaScriptEnabled(true);
        mResultView.loadData("Loading...", "text/html", "utf-8");
        
        String filesDir = copyInFilesDir("general.css"); 
        Log.d("general.css", filesDir);
        String out = "<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><link href=\"file://" + filesDir + "\" rel=\"stylesheet\" media=\"all\"/></head>";
        //String data = urlClient.getURL("http://habrahabr.ru/?fl=all");
        byte[] bb = urlClient.getURLAsBytes("http://habrahabr.ru/core/captcha/");
        
        String imgCa = getFilesDir().getAbsolutePath() + "/code.png";
        Log.i("imgCa", imgCa);
        File f = new File(imgCa);
        try {
			f.createNewFile();
			new FileOutputStream(f).write(bb);
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		
		String[] l = getFilesDir().list();
		for(int i = 0; i < l.length; i++)
			Log.d("List", l[i]);
        
        out = "<img src=\"file://"+imgCa+"\"/>";
        
        String[][] post = new String[][]{{"act","login"}, {"redirect_url","http://habrahabr.ru/"},
        		{"login","WNeZRoS"},{"password","123456"},{"captcha",""}};
        
        //data = urlClient.postURL("http://habrahabr.ru/ajax/auth/", post);
        
        /*HabraCutTopicParser parser = new HabraCutTopicParser(data);
        HabraTopic topic = null;
        while((topic = parser.parseTopic()) != null)
        {
        	out += URLClient.encode(RemoveImage.remove(topic.getTopicDataAsHTML()));
        }*/
            
        mResultView.loadData(out, "text/html", "utf-8");
        
        final Button go = (Button) findViewById(R.id.go);
        final EditText url = (EditText) findViewById(R.id.url);
        
        go.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mResultView.loadData(urlClient.getURL(url.getText().toString()), "text/html", "utf-8");
			}
		});
        
    }
    
    public void onBackPressed()
    {
    
    }
    
    /**
     * �������� ���� �� assets � ~/files/
     * @param file ���� ��� �����������
     * @return ���� �� ����� � ����� files
     */
    public String copyInFilesDir(String file)
    {
    	String filePath = getFilesDir().getAbsolutePath() + "/" + file;
    	Log.d("copyFile", filePath);
    	
    	File fileDesc = new File(filePath);
    	
    	if(fileDesc.exists()) return filePath;
    	
		try {
			InputStream inputStream = getAssets().open(file);
			FileOutputStream outputStream = new FileOutputStream(fileDesc);
			
	    	byte buf[] = new byte[1024];
	    	
	    	int len = 0;
    		while ((len = inputStream.read(buf)) != -1) 
			{
				outputStream.write(buf, 0, len);
			}
			
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
			return null;
	    }
    
    	return filePath;
    }
}