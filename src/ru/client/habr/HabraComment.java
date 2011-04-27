package ru.client.habr;

/**
 * @author WNeZRoS
 * ����� ����������� � �����
 */
public final class HabraComment 
{
	/**
	 * ������ ������
	 */
	public String avatar = null;
	
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
	public int rating = 0;
	
	/**
	 * �� �����������
	 */
	public int id = 0;
	
	/**
	 * �����
	 */
	public String text = null;
	
	/**
	 * ����� �� �����������
	 */
	public HabraComment replyTo = null;
	
	/**
	 * ������ �����������
	 */
	public int padding = 0;
	
	/**
	 * � ���� � ���������
	 */
	public boolean inFavs = false;
	
	/**
	 * @return ����� ����������� ��� HTML ���
	 */
	public String getCommentAsHTML() {
		return "<div id=\"comment_" + id + "\" class=\"comment_holder padding" 
		+ (padding >= 20 ? "Big" : String.valueOf(padding)) 
		+ "\"><div class=\"msg-meta\"><ul class=\"menu info author hcard\">" 
		+ "<li class=\"avatar\">" + "<a href=\"http://" + author 
		+ ".habrahabr.ru/\"><img src=\"" + avatar + "\"/></a>" 
		+ "</li><li class=\"fn nickname username\"><a href=\"http://" 
		+ author + ".habrahabr.ru/\" class=\"url\">" + author 
		+ "</a>,</li><li class=\"date\"><abbr class=\"published\">" + date 
		+ "</abbr></li><li class=\"mark\"><span>" 
		+ ( rating < 0 ? "-" : (rating == 0 ? "" : "+") ) + rating 
		+ "</span></li></ul></div>" + text + "</div>";
	}
	
	/**
	 * @param noAvatar ������ ������
	 * @return ����� ����������� ��� HTML ���
	 */
	public String getCommentAsHTML(boolean noAvatar) {
		return "<div id=\"comment_" + id + "\" class=\"comment_holder padding" 
		+ (padding >= 20 ? "Big" : String.valueOf(padding)) 
		+ "\"><div class=\"msg-meta\"><ul class=\"menu info author hcard\">" 
		+ (noAvatar ? "" : "<li class=\"avatar\">" + "<a href=\"http://" + author 
		+ ".habrahabr.ru/\"><img src=\"" + avatar + "\"/></a>") 
		+ "</li><li class=\"fn nickname username\"><a href=\"http://" 
		+ author + ".habrahabr.ru/\" class=\"url\">" + author 
		+ "</a>,</li><li class=\"date\"><abbr class=\"published\">" + date 
		+ "</abbr></li><li class=\"mark\"><span>" 
		+ ( rating < 0 ? "-" : (rating == 0 ? "" : "+") ) + rating 
		+ "</span></li></ul></div>" + text + "</div>";
	}
	
	/**
	 * ��������� � +
	 * @param postID �� ����� 
	 * @return ����������
	 */
	public boolean voteUp(int postID) {
		String[][] post = {{"action","vote"}, {"target_name","post_comment"}, 
				{"target_id",String.valueOf(id)}, {"mark", "1"}, {"signed_id",String.valueOf(postID)}};
		return URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	/**
	 * ���������� � -
	 * @param postID �� �����
	 * @return ����������
	 */
	public boolean voteDown(int postID) {
		String[][] post = {{"action","vote"}, {"target_name","post_comment"}, 
				{"target_id",String.valueOf(id)}, {"mark", "-1"}, {"signed_id",String.valueOf(postID)}};
		return URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	/**
	 * �������� � ���������
	 * @return ����������
	 */
	public boolean addToFavorites() {
		String[][] post = {{"action","add"}, {"target_type","comments"}, {"target_id",String.valueOf(id)}};
		inFavs = URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/").contains("<message>ok</message>");
		return inFavs;
	}
	
	/**
	 * ������� �� ����������
	 * @return ����������
	 */
	public boolean removeFromFavorites() {
		String[][] post = {{"action","remove"}, {"target_type","comments"}, {"target_id",String.valueOf(id)}};
		inFavs = !URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		
		return !inFavs;
	}
}
