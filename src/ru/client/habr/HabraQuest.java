package ru.client.habr;

/**
 * @author WNeZRoS
 * ����� �������
 */
public final class HabraQuest 
{	
	/**
	 * ID �������
	 */
	public int id = 0;
	
	/**
	 * ���������
	 */
	public String title = null;
	
	/**
	 * ����� �������
	 */
	public String text = null;
	
	/**
	 * ����
	 */
	public String tags = null;
	
	/**
	 * �������
	 */
	public int rating = 0;
	
	/**
	 * ���� ����������
	 */
	public String date = null;
	
	/**
	 * �����
	 */
	public String author = null;
	
	/**
	 * ���-�� ���������� � ���������
	 */
	public int favsCount = 0;
	
	/**
	 * ���-�� �������
	 */
	public int answerCount = 0;
	
	/**
	 * � ��������� � ����
	 */
	public boolean inFavs = false;
	
	/**
	 * ������?
	 */
	public boolean accepted = false;
	
	/**
	 * ����������� � �������
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
	 * �������� � ���������
	 * @return ������� �� ��������
	 */
	public boolean addToFavorites() {
		String[][] post = {{"action","add"}, {"target_type","questions"}, {"target_id",String.valueOf(id)}};
		inFavs = URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		
		if(inFavs) favsCount++;
		return inFavs;
	}
	
	/**
	 * ������� �� ����������
	 * @return ������� �� �������
	 */
	public boolean removeFromFavorites() {
		String[][] post = {{"action","remove"}, {"target_type","questions"}, {"target_id",String.valueOf(id)}};
		inFavs = !URLClient.getUrlClient().postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		
		if(!inFavs) favsCount--;
		return !inFavs;
	}
	
	/**
	 * ���������� URL �������
	 * @return URL �������
	 */
	public String getQuestURL() {
		return "http://habrahabr.ru/qa/" + String.valueOf(id) + "/";
	}
	
	/**
	 * ���������� HTML ��� �������
	 * @return HTML ���
	 */
	public String getDataAsHTML() {
		return "<div class=\"hentry question_hentry\" id=\"" + String.valueOf(id) 
		+ "\"><h2 class=\"entry-title\"><a href=\"" + getQuestURL() 
		+ "\" class=\"topic\">" + title + "</a></h2><div class=\"content\">" 
		+ text + "</div><ul class=\"tags\">" + tags 
		+ "</ul><div class=\"entry-info vote_holder answer-positive\">" 
		+ "<div class=\"corner tl\"></div><div class=\"corner tr\"></div>" 
		+ "<div class=\"entry-info-wrap\"><div class=\"mark\"><span>" 
		+ ( rating > 0 ? "+" : "" ) + String.valueOf(rating) 
		+ "</span></div><div class=\"informative\"><span>" 
		+ String.valueOf(answerCount) + " " + getAnswer() + "</span></div>" 
		+ "<div class=\"published\"><span>" + date + "</span></div>" 
		+ "<div class=\"favs_count\"><span>" + favsCount + "</span></div>" 
		+ "<div class=\"vcard author full\"><a href=\"http://" + author 
		+ ".habrahabr.ru/\" class=\"fn nickname url\"><span>" + author 
		+ "</span></a></div></div><div class=\"corner bl\"></div>" 
		+ "<div class=\"corner br\"></div></div></div>";
	}
	
	/**
	 * ���������� HTML ��� ������
	 * @param noContent ������ �������
	 * @param noTags ������ ����
	 * @param noMark ������ ������
	 * @param noAnswersCount ������ ���-�� �������
	 * @param noDate ������ ����
	 * @param noFavs ������ ���-�� ���������� � ���������
	 * @param noAuthor ������ ������
	 * @return HTML ���
	 */
	public String getDataAsHTML(boolean noContent, boolean noTags, boolean noMark, 
			boolean noAnswersCount, boolean noDate, boolean noFavs, boolean noAuthor) {
		return "<div class=\"hentry question_hentry\" id=\"" + String.valueOf(id) 
		+ "\"><h2 class=\"entry-title\"><a href=\"" + getQuestURL() 
		+ "\" class=\"topic\">" + title + "</a></h2>" 
		+ (noContent ? "" : "<div class=\"content\">" + text + "</div>")
		+ (noTags ? "" : "<ul class=\"tags\">" + tags + "</ul>")
		+ (noMark && noAnswersCount && noDate && noAuthor ? "" 
				: "<div class=\"entry-info vote_holder answer-positive\">" 
		+ "<div class=\"corner tl\"></div><div class=\"corner tr\"></div>" 
		+ "<div class=\"entry-info-wrap\">" 
		+ (noMark ? "" : "<div class=\"mark\"><span>" 
			+ ( rating > 0 ? "+" : "" ) + String.valueOf(rating) + "</span></div>") 
		+ (noAnswersCount ? "" : "<div class=\"informative\"><span>" 
			+ String.valueOf(answerCount) + " " + getAnswer() + "</span></div>") 
		+ (noDate ? "" : "<div class=\"published\"><span>" + date + "</span></div>")
		+ (noFavs ? "" : "<div class=\"favs_count\"><span>" + favsCount + "</span></div>")
		+ (noAuthor ? "" : "<div class=\"vcard author full\"><a href=\"http://" 
			+ author + ".habrahabr.ru/\" class=\"fn nickname url\"><span>" 
			+ author + "</span></a></div>") 
		+ "</div><div class=\"corner bl\"></div>" 
		+ "<div class=\"corner br\"></div></div>") + "</div>";
	}
	
	/**
	 * ����� ������������ � �������
	 * @return HTML ��� ������������
	 */
	public String getCommentsAsHTML() {
		String data = "";
		if(comments == null) return data;

		for(int i = 0; i < comments.length; i++) {
			data += comments[i].getDataAsHTML();
		}
			
		return data;
	}
	
	private String getAnswer() {
		int mod = answerCount / 10;
		
		if((answerCount >= 6 && answerCount <= 20) || mod == 0 || mod >= 5)
			return "�������";
		else if(mod == 1) 
			return "�����";
		else if(mod >= 2 && mod <= 4)
			return "������";
		
		return "�������";
	}
}
