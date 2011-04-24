package ru.client.habr;

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
	String title = null;						// ���������
	String content = null;						// ����� ������
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
	boolean isCorporativeBlog = false;			// ��� ���� ��������?
	boolean inFavs = false;						// � ���������
	
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
	public String getDataAsHTML()
	{
		return "<div class=\"hentry\"><h2 class=\"entry-title\"><a href=\"" + 
			getBlogURL() + "\" class=\"blog\">" + blogName + "</a> &rarr; <a href=\"" + 
			getTopicURL() + "\" class=\"topic\">" + title + "</a></h2><div class=\"content\">" + content + "</div>" + 
			(tags.length() > 1 ? "<ul class=\"tags\">" + tags + "</ul>" : "") + 
			"<div class=\"entry-info\"><div class=\"corner tl\"></div><div class=\"corner tr\"></div><div class=\"entry-info-wrap\"><div class=\"mark\">" + 
			rating + "</div><div class=\"published\"><span>" + date + "</span></div><div class=\"favs_count\"><span>" + 
			Integer.toString(favorites) + "</span></div><div class=\"vcard author full\"><a href=\"http://" + author + ".habrahabr.ru/\" class=\"fn nickname url\"><span>" + 
			author + "</span></a></div><div class=\"comments\"><a href=\"" + getTopicURL() + "#comments\"><span class=\"all\">" + 
			Integer.toString(commentsCount) + "</span>" + (commentsDiff > 0 ? " <span class=\"new\">+" + Integer.toString(commentsDiff) + "</span>" : "") + 
			"</a></div></div><div class=\"corner bl\"></div><div class=\"corner br\"></div></div></div>";
	}
	
	public String getDataAsHTML(boolean noContent, boolean noTags, boolean noMark, boolean noDate, boolean noFavs, boolean noAuthor, boolean noComments)
	{
		return "<div class=\"hentry\"><h2 class=\"entry-title\"><a href=\"" + 
			getBlogURL() + "\" class=\"blog\">" + blogName + "</a> &rarr; <a href=\"" + 
			getTopicURL() + "\" class=\"topic\">" + title + "</a></h2>" + 
			(noContent ? "" : "<div class=\"content\">" + content + "</div>") + 
			(tags.length() > 1 && !noTags ? "<ul class=\"tags\">" + tags + "</ul>" : "") + 
			(noMark && noDate && noFavs && noAuthor && noComments ? "" :
			"<div class=\"entry-info\"><div class=\"corner tl\"></div><div class=\"corner tr\"></div><div class=\"entry-info-wrap\">" +
			(noMark ? "" : "<div class=\"mark\">" + rating + "</div>") +
			(noDate ? "" : "<div class=\"published\"><span>" + date + "</span></div>") + 
			(noFavs ? "" : "<div class=\"favs_count\"><span>" + String.valueOf(favorites) + "</span></div>") + 
			(noAuthor ? "" : "<div class=\"vcard author full\"><a href=\"http://" + author + ".habrahabr.ru/\" class=\"fn nickname url\"><span>" + author + "</span></a></div>") + 
			(noComments ? "" : "<div class=\"comments\"><a href=\"" + getTopicURL() + "#comments\"><span class=\"all\">" + 
			Integer.toString(commentsCount) + "</span>" + (commentsDiff > 0 ? " <span class=\"new\">+" + Integer.toString(commentsDiff) + "</span>" : "") + "</a></div>") + 
			"</div><div class=\"corner bl\"></div><div class=\"corner br\"></div>") + "</div></div>";
	}
	
	public boolean voteUp(URLClient url)
	{
		String[][] post = {{"action","vote"}, {"target_name","post"}, 
				{"target_id",String.valueOf(id)}, {"mark", "1"}};
		return url.postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	public boolean voteDown(URLClient url)
	{
		String[][] post = {{"action","vote"}, {"target_name","post"}, 
				{"target_id",String.valueOf(id)}, {"mark", "-1"}};
		return url.postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	public boolean addToFavorites(URLClient url)
	{
		String[][] post = {{"action","add"}, {"target_type","post"}, {"target_id",String.valueOf(id)}};
		inFavs = url.postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/").contains("<message>ok</message>");
		return inFavs;
	}
	
	public boolean removeFromFavorites(URLClient url)
	{
		String[][] post = {{"action","remove"}, {"target_type","post"}, {"target_id",String.valueOf(id)}};
		inFavs = !url.postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		return !inFavs;
	}
}