package ru.client.habr;

/**
 * @author WNeZRoS
 * ����� ������ �� ������
 */
public final class HabraAnswer {
	
	/**
	 * ID ������
	 */
	public int id = 0;
	
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
	 * ����� ������
	 */
	public String text = null;
	
	/**
	 * �������
	 */
	public int rating = 0;
	
	/**
	 * ���� ����� �������?
	 */
	public boolean isSolution = false;
	
	/**
	 * ����������� � ������
	 */
	public HabraQAComment[] comments = null;
	
	/**
	 * ���������� � +
	 * @return ������� �� �������������
	 */
	public boolean voteUp() {
		String[][] post = {{"action","vote"}, {"target_name","qa_question"}, 
				{"target_id",String.valueOf(id)}, {"mark", "1"}};
		return URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	/**
	 * ���������� � -
	 * @return ������� �� �������������
	 */
	public boolean voteDown() {
		String[][] post = {{"action","vote"}, {"target_name","qa_question"}, 
				{"target_id",String.valueOf(id)}, {"mark", "-1"}};
		return URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	/**
	 * @param questionID ID �������
	 * @return ������ �� �����
	 */
	public String getAnswerURL(int questionID) {
		return "http://habrahabr.ru/qa/" + questionID + "/#answer_" + id;
	}
	
	/**
	 * @return ����� ��� HTML
	 */
	public String getDataAsHTML() {
		return "<div class=\"comment_holder vote_holder answer\">" 
		+ "<div class=\"msg-meta\"><ul class=\"menu info author hcard\">" 
		+ "<li class=\"avatar\"><a href=\"http://" + author 
		+ ".habrahabr.ru/\"><img src=\"" + avatar + "\"/></a></li>" 
		+ "<li class=\"fn nickname username\"><a href=\"http://" + author 
		+ ".habrahabr.ru/\" class=\"url\">" + author + "</a>,</li>" 
		+ "<li class=\"date\"><abbr class=\"published\">" + date 
		+ "</abbr></li><li class=\"mark\"><span>" + String.valueOf(rating) 
		+ "</span></li></ul></div>" + text + "</div>";
	}
	
	/**
	 * @param noAvatar ������ ������
	 * @return ����� ��� HTML
	 */
	public String getDataAsHTML(boolean noAvatar) {
		return "<div class=\"comment_holder vote_holder answer\">" 
		+ "<div class=\"msg-meta\"><ul class=\"menu info author hcard\">" 
		+ "<li class=\"avatar\">" + (noAvatar ? "" : "<a href=\"http://" + author 
		+ ".habrahabr.ru/\"><img src=\"" + avatar + "\"/></a>") + "</li>" 
		+ "<li class=\"fn nickname username\"><a href=\"http://" + author 
		+ ".habrahabr.ru/\" class=\"url\">" + author + "</a>,</li>" 
		+ "<li class=\"date\"><abbr class=\"published\">" + date 
		+ "</abbr></li><li class=\"mark\"><span>" + String.valueOf(rating) 
		+ "</span></li></ul></div>" + text + "</div>";
	}
	
	/**
	 * @return ����������� � ������ ��� HTML ���
	 */
	public String getCommentsAsHTML() {
		String data = "";
		if(comments == null) return data;

		for(int i = 0; i < comments.length; i++) {
			data += comments[i].getDataAsHTML();
		}
			
		return data;
	}
}
