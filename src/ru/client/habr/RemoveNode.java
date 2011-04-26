package ru.client.habr;

public class RemoveNode 
{
	/**
	 * ����� ��� ��� ����������� �������� � ������
	 * @param data �����
	 * @return ����� ��� ��������
	 */
	public static String removeImage(String data)
	{
		if(data == null) return null;
		return data.replaceAll("<img[^>]+src=\"([^\"]+)\"[^>]+>", "<h4 class=\"ufo\">��������� ��� � ���������� <a href=\"$1\">��������</a></h4>");
	}
	
	public static String removeVideo(String data)
	{
		if(data == null) return null;
		return data.replaceAll("<object.+<embed src=\"([^\"]+)\".+</object>", "<a href=\"$1\">����� ��� �����</a>");
	}
}
