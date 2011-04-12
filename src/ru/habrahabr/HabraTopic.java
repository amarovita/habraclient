package ru.habrahabr;

public class HabraTopic
{
	public static enum HabraTopicType
	{
		Post,		// ������� ����
		Link,		// ������
		Translate,	// �������
		Podcast,	// �������
	}
	
	HabraTopicType type = HabraTopicType.Post;	// ��� ������
	String title = null;					// ���������
	String content = null;					// ����� ������
	String tags = null;							// ����
	// TODO: replace tag to String[]
	String author = null;						// �����
	String date = null;							// ���� ����������
	String rating = null;						// �������
	Integer favorites = null;					// ���-�� ���������� � ���������
	Integer commentsCount = null;				// ���-�� ������������
	Integer commentsDiff = null;				// ������� ������������ � �������� ���������
	String additional = null;					// �������������� ���������� (������ �� ��������)
	Integer id = null;							// ID ������
	String blogID = null;						// ����, � ������� �������� �����
	String blogName = null;
	boolean isCorporativeBlog = false;
	
	/**
	 * �������� ����� �����
	 * @return ����� �����
	 */
	public String getTopicURL()
	{
		return getBlogURL() + Integer.toString(id) + "/";
	}
	
	/**
	 * �������� ���� �����, � ������� ����� �����
	 * @return ����� �����
	 */
	public String getBlogURL()
	{
		if(isCorporativeBlog) return "http://habrahabr.ru/company/" + blogID + "/blog/";
		return "http://habrahabr.ru/blogs/" + blogID + "/";
	}
	
	/**
	 * ���������� HTML ��� ��� �����
	 * @return ��� �����
	 */
	public String getTopicDataAsHTML()
	{
		return "<div class=\"hentry\"><h2 class=\"entry-title\"><a href=\"" + 
			getBlogURL() + "\" class=\"blog\">" + blogName + "</a> &rarr; <a href=\"" + 
			getTopicURL() + "\" class=\"topic\">" + title + "</a></h2><div class=\"content\">" + 
			content + "</div><ul class=\"tags\">" + tags + "</ul><div class=\"entry-info\"><div class=\"corner tl\"></div><div class=\"corner tr\"></div><div class=\"entry-info-wrap\"><div class=\"mark\">" + 
			rating + "</div><div class=\"published\"><span>" + date + "</span></div><div class=\"favs_count\">" + 
			Integer.toString(favorites) + "</div><div class=\"vcard author full\"><a href=\"http://" + author + ".habrahabr.ru/\" class=\"fn nickname url\"><span>" + 
			author + "</span></a></div><div class=\"comments\"><a href=\"" + getTopicURL() + "#comments\"><span class=\"all\">" + 
			Integer.toString(commentsCount) + "</span> <span class=\"new\">+ " + Integer.toString(commentsDiff) + 
			"</span></a></div></div><div class=\"corner bl\"></div><div class=\"corner br\"></div></div></div>";
	}
}