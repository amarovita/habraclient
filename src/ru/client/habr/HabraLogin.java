package ru.client.habr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class HabraLogin 
{
	private String mCacheDir = null;
	private String mUserName = null;
	//private int mUserID = 0;
	
	private static HabraLogin habraLogin = null;
	
	/**
	 * �������� ����� HabraLogin �����. ��������! ����� ���������� ��� ���� ���-�� �������
	 * @return ��������� HabraLogin
	 */
	public static HabraLogin getHabraLogin()
	{
		if(habraLogin == null)
		{
			Log.w("HabraLogin.getHabraLogin", "create new HabraLogin");
		}
		return habraLogin;
	}
	
	/**
	 * ����� ����������� �� ����
	 * @param cacheDir ����� �������� ������
	 */
	public HabraLogin(String cacheDir)
	{
		mCacheDir = cacheDir;
		habraLogin = this;
	}
	
	/**
	 * ��������� ��������� ������������ ��� ���
	 * @return ���� ��� �� ����
	 */
	public boolean isLogged()
	{
		return mUserName != null;
	}
	
	/**
	 * ���� � ������� �� �����
	 * @param login �����
	 * @param password ������
	 * @param captcha ������
	 * @return null ��� �������� ������
	 */
	public String login(String login, String password, String captcha)
	{
		String[][] post = new String[][]{{"act","login"}, {"redirect_url","http://habrahabr.ru/"},
        		{"login",login},{"password",password},{"captcha",captcha},{"","true"}};
        
        String data = URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/auth/", post, "http://habrahabr.ru/login/");
        
        Log.d("login", data);
        
        int errorIndex = data.indexOf("<error");
        if(errorIndex == -1) 
        {
        	return null;
        }
        
        String result = data.substring(data.indexOf('>', errorIndex) + 1);
        return result.substring(0, result.indexOf('<'));
	}
	
	/**
	 * ����� �� �������� (������ ����������)
	 * @return ���������� ������
	 */
	public boolean logout()
	{
		return true;
	}
	
	/**
	 * �������� ������
	 * @return ���� �� ����� ������
	 */
	public String getCaptcha()
	{
		byte[] data =  URLClient.getUrlClient().getURLAsBytes("http://habrahabr.ru/core/captcha/");
		String fileCaptcha = mCacheDir + "/captcha.png";
		
		File captcha = new File(fileCaptcha);
		try {
			if(!captcha.exists()) captcha.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(captcha);
			outputStream.write(data);
			outputStream.close();
		} catch (IOException e) {
			Log.e("HabraLogin.getCaptcha", "IOException: " + e.getMessage());
		}
		
		return fileCaptcha;
	}
}
