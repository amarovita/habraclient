package ru.habrahabr;

public class RemoveImage 
{
	/**
	 * ����� ��� ��� ����������� �������� � ������
	 * @param data �����
	 * @return ����� ��� ��������
	 */
	public static String remove(String data)
	{
		if(data == null) return null;
		return data.replaceAll("<img[^>]+src=\"([^\"]+)\"[^>]+>", "<h4 class=\"ufo\">��������� ��� � ���������� <a href=\"$1\">��������</a></h4>");
	}
}
