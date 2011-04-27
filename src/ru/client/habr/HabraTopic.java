package ru.client.habr;

/**
 * @author WNeZRoS
 * ����� �����������
 */
public final class HabraTopic {
	
	/**
	 * @author WNeZRoS
	 * ��� �����
	 */
	public static enum HabraTopicType {
		/**
		 * ������� ����
		 */
		Post,
		
		/**
		 * �����-������
		 */
		Link,
		
		/**
		 * �������
		 */
		Translate,
		
		/**
		 * �������
		 */
		Podcast,
	}
	
	/**
	 * ��� �����
	 */
	public HabraTopicType type = HabraTopicType.Post;
	
	/**
	 * ���������
	 */
	public String title = null;
	
	/**
	 * �������
	 */
	public String content = null;

	/**
	 * ����
	 */
	public String tags = null;
	
	/**
	 * �����
	 */
	public String author = null;
	
	/**
	 * ���� ����������
	 */
	public String date = null;
	
	/**
	 * �������
	 */
	public String rating = null;
	
	/**
	 * ���-�� ���������� � ���������
	 */
	public int favorites = 0;

	/**
	 * ���-�� ������������
	 */
	public int commentsCount = 0;

	/**
	 * ���-�� ����������� ����������� � ���������� ���������
	 */
	public int commentsDiff = 0;

	/**
	 * �������������� �������� (������, ��������, ����)
	 */
	public String additional = null;

	/**
	 * ID �����
	 */
	public int id = 0;

	/**
	 * ID �����
	 */
	public String blogID = null;

	/**
	 * �������� �����
	 */
	public String blogName = null;

	/**
	 * ���� ��� ����������� �������������� ����� (/company/{NAME}/blog/{ID})
	 */
	public boolean isCorporativeBlog = false;

	/**
	 * � ��������� � �������� ������������
	 */
	public boolean inFavs = false;

	
	/**
	 * �������� ����� �����
	 * @return ����� �����
	 */
	public String getTopicURL() {
		return getBlogURL() + Integer.toString(id) + "/";
	}
	
	/**
	 * �������� ���� �����, � ������� ����� �����
	 * @return ����� �����
	 */
	public String getBlogURL() {
		return (isCorporativeBlog ? "http://habrahabr.ru/company/" + blogID 
				+ "/blog/" : "http://habrahabr.ru/blogs/" + blogID + "/");
	}
	
	/**
	 * ���������� HTML ��� ��� �����
	 * @return HTML ��� �����
	 */
	public String getDataAsHTML() {
		return "<div class=\"hentry\" id=\"post_" + String.valueOf(id) 
		+ "\"><h2 class=\"entry-title\"><a href=\"" + getBlogURL() 
		+ "\" class=\"blog\">" + blogName + "</a> &rarr; <a href=\"" 
		+ getTopicURL() + "\" class=\"topic\">" + title 
		+ "</a></h2><div class=\"content\">" + content + "</div>" 
		+ (tags.length() > 1 ? "<ul class=\"tags\">" + tags + "</ul>" : "") 
		+ "<div class=\"entry-info\"><div class=\"corner tl\"></div>" 
		+ "<div class=\"corner tr\"></div><div class=\"entry-info-wrap\">" 
		+ "<div class=\"mark\">" + rating + "</div>" 
		+ "<div class=\"published\"><span>" + date + "</span></div>" 
		+ "<div class=\"favs_count\"><span>" + String.valueOf(favorites) + "</span></div>" 
		+ "<div class=\"vcard author full\"><a href=\"http://" + author 
		+ ".habrahabr.ru/\" class=\"fn nickname url\"><span>" + author 
		+ "</span></a></div><div class=\"comments\"><a href=\"" + getTopicURL() 
		+ "#comments\"><span class=\"all\">" + String.valueOf(commentsCount) 
		+ "</span>" + (commentsDiff > 0 ? " <span class=\"new\">+" 
		+ String.valueOf(commentsDiff) + "</span>" : "") + "</a></div></div>" 
		+ "<div class=\"corner bl\"></div><div class=\"corner br\"></div></div></div>";
	}
	
	/**
	 * ���������� HTML ��� ��� �����
	 * @param noContent �� ���������� �������
	 * @param noTags �� ���������� ����
	 * @param noMark �� ���������� ������
	 * @param noDate �� ���������� ���� ����������
	 * @param noFavs �� ���������� ���-�� ���������� � ���������
	 * @param noAuthor �� ���������� ������
	 * @param noComments �� ���������� ���-�� ������������
	 * @return HTML ��� �����
	 */
	public String getDataAsHTML(boolean noContent, boolean noTags, boolean noMark, 
			boolean noDate, boolean noFavs, boolean noAuthor, boolean noComments) {
		return "<div class=\"hentry\" id=\"post_" + String.valueOf(id) 
		+ "\"><h2 class=\"entry-title\"><a href=\"" + getBlogURL() 
		+ "\" class=\"blog\">" + blogName + "</a> &rarr; <a href=\"" 
		+ getTopicURL() + "\" class=\"topic\">" + title + "</a></h2>" 
		+ (noContent ? "" : "<div class=\"content\">" + content + "</div>") 
		+ (tags.length() > 1 && !noTags ? "<ul class=\"tags\">" + tags + "</ul>" : "") 
		+ (noMark && noDate && noFavs && noAuthor && noComments ? "" 
				: "<div class=\"entry-info\"><div class=\"corner tl\"></div>" 
					+ "<div class=\"corner tr\"></div><div class=\"entry-info-wrap\">" 
					+ (noMark ? "" : "<div class=\"mark\">" + rating + "</div>") 
					+ (noDate ? "" : "<div class=\"published\"><span>" + date + "</span></div>") 
					+ (noFavs ? "" : "<div class=\"favs_count\"><span>" 
							+ String.valueOf(favorites) + "</span></div>") 
					+ (noAuthor ? "" : "<div class=\"vcard author full\"><a href=\"http://" 
							+ author + ".habrahabr.ru/\" class=\"fn nickname url\"><span>" 
							+ author + "</span></a></div>") 
					+ (noComments ? "" : "<div class=\"comments\"><a href=\"" 
							+ getTopicURL() + "#comments\"><span class=\"all\">" 
							+ Integer.toString(commentsCount) + "</span>" 
							+ (commentsDiff > 0 ? " <span class=\"new\">+" 
									+ Integer.toString(commentsDiff) + "</span>" : "") 
		+ "</a></div>") 
		+ "</div><div class=\"corner bl\"></div><div class=\"corner br\"></div>") 
		+ "</div></div>";
	}
	
	/**
	 * ���������� � +
	 * @return ������� �� �������������
	 */
	public boolean voteUp() {
		String[][] post = {{"action","vote"}, {"target_name","post"}, 
				{"target_id",String.valueOf(id)}, {"mark", "1"}};
		return URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	/**
	 * ���������� � -
	 * @return ������� �� �������������
	 */
	public boolean voteDown() {
		String[][] post = {{"action","vote"}, {"target_name","post"}, 
				{"target_id",String.valueOf(id)}, {"mark", "-1"}};
		return URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	/**
	 * �������� � ���������
	 * @return ������� �� ��������
	 */
	public boolean addToFavorites() {
		String[][] post = {{"action","add"}, {"target_type","post"}, {"target_id",String.valueOf(id)}};
		inFavs = URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/").contains("<message>ok</message>");
		
		return inFavs;
	}
	
	/**
	 * ������� �� ����������
	 * @return ������� �� �������
	 */
	public boolean removeFromFavorites() {
		String[][] post = {{"action","remove"}, {"target_type","post"}, {"target_id",String.valueOf(id)}};
		inFavs = !URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		
		return !inFavs;
	}
}