package ru.client.habr;

public class HabraQuest 
{	
	int id = 0;					// ID
	String title = null;		// ���������
	String text = null;			// ����� �������
	String tags = null;			// ����
	int rating = 0;				// �������
	String date = null;			// ���� ����������
	String author = null;		// �����
	int favsCount = 0;			// ���-�� ���������� � ���������
	int answerCount = 0;		// ���-�� �������
	boolean inFavs = false; 	// � ���������
	boolean accepted = false;	// ������ �����
	HabraQAComment[] comments = null;
	
	public boolean voteUp(URLClient url)
	{
		String[][] post = {{"action","vote"}, {"target_name","qa_question"}, 
				{"target_id",String.valueOf(id)}, {"mark", "1"}};
		return url.postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	public boolean voteDown(URLClient url)
	{
		String[][] post = {{"action","vote"}, {"target_name","qa_question"}, 
				{"target_id",String.valueOf(id)}, {"mark", "-1"}};
		return url.postURL("http://habrahabr.ru/ajax/voting/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
	}
	
	public boolean addToFavorites(URLClient url)
	{
		String[][] post = {{"action","add"}, {"target_type","questions"}, {"target_id",String.valueOf(id)}};
		inFavs = url.postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		if(inFavs) favsCount++;
		return inFavs;
	}
	
	public boolean removeFromFavorites(URLClient url)
	{
		String[][] post = {{"action","remove"}, {"target_type","questions"}, {"target_id",String.valueOf(id)}};
		inFavs = !url.postURL("http://habrahabr.ru/ajax/favorites/", post, 
				"http://habrahabr.ru/qa/").contains("<message>ok</message>");
		if(!inFavs) favsCount--;
		return !inFavs;
	}
	
	public String getQuestURL()
	{
		return "http://habrahabr.ru/qa/" + String.valueOf(id) + "/";
	}
	
	public String getDataAsHTML()
	{
		return "<div class=\"hentry question_hentry\"><h2 class=\"entry-title\"><a href=\"" + 
		getQuestURL() + "\" class=\"topic\">" + title + "</a></h2><div class=\"content\">" + text +
		"</div><ul class=\"tags\">" + tags + "</ul><div class=\"entry-info vote_holder answer-positive\">" + 
		"<div class=\"corner tl\"></div><div class=\"corner tr\"></div><div class=\"entry-info-wrap\"><div class=\"mark\"><span>" + 
		( rating > 0 ? "+" : "" ) + String.valueOf(rating) + "</span></div><div class=\"informative\"><span>" + 
		String.valueOf(answerCount) + " ������</span></div><div class=\"published\"><span>" + date + 
		"</span></div><div class=\"favs_count\"><span>" + favsCount + "</span></div><div class=\"vcard author full\"><a href=\"http://" + 
		author + ".habrahabr.ru/\" class=\"fn nickname url\"><span>" + author + "</span></a></div></div><div class=\"corner bl\"></div><div class=\"corner br\"></div></div></div>";
	}	
	
	public String getCommentsAsHTML()
	{
		String data = "";
		if(comments == null) return data;

        for(int i = 0; i < comments.length; i++)
        {
          data += comments[i].getDataAsHTML();
        }
			
		return data;
	}
}