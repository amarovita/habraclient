package ru.client.habr;

/**
 * @author WNeZRoS
 * ������� �������� HTML ��������
 */
public final class RemoveNode {
	
	/**
	 * ����� ��� ��� ����������� �������� � ������
	 * @param data ����� HTML ��������
	 * @return ����� ��� ��������
	 */
	public static String removeImage(String data)
	{
		if(data == null) return null;
		return data.replaceAll("<img[^>]+src=\"([^\"]+)\"[^>]+>", "<h4 class=\"ufo\">��������� ��� � ���������� <a href=\"$1\">��������</a></h4>");
	}
	
	/**
	 * ������ ����� ������ �� ������
	 * @param data ����� HTML ��������
	 * @return ����� ��� �����
	 */
	public static String removeVideo(String data)
	{
		if(data == null) return null;
		return data.replaceAll("<object.+<embed src=\"([^\"]+)\".+</object>", "<a href=\"$1\">����� ��� �����</a>");
	}
}
