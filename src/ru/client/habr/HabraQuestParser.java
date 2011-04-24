package ru.client.habr;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class HabraQuestParser 
{
	String mData = null;		// ������ ��������
	int mStartPosition = 0;		// �������� ������� ���������� ������
	
	/**
	 * ������ ������� � ������� ��������
	 * @param data ��� ��������
	 */
	public HabraQuestParser(String data)
	{
		mData = data;
		mStartPosition = 0;
	}
	
	/**
	 * �������� ��������� ������ �� ������
	 * @return ������ �������
	 */
	public HabraQuest parseQuestFromList()
	{
		if(mData == null || mStartPosition == -1) return null;
		
		Log.d("HabraQuestParser.parseQuestFromList", "Find quest");
		
		// ������� ������ �������
    	int startPosition = mData.indexOf("<div class=\"hentry ", mStartPosition);
    	if(startPosition == -1) return null;
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Find end");
    	
    	// ���� �����
    	int endPosition = mData.indexOf("<div class=\"corner bl\"></div><div class=\"corner br\"></div>", startPosition);
    	if(endPosition == -1) return null;
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "SubString");
    	// �������� ������ �����
    	String questData = new String(mData.substring(startPosition, endPosition)) + "</div></div>";
    	HabraQuest quest = new HabraQuest();
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse data");
    	// ������ ������
    	int lastIndex = 0;
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse Title");
    	quest.title = new String(questData.substring(
    			lastIndex = (questData.indexOf("class=\"topic\">", lastIndex) + 14), 
    			lastIndex = questData.indexOf('<', lastIndex)));
  
    	Log.i("QuestData", quest.title);
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse Content");
		quest.text = new String(questData.substring(
				lastIndex = questData.indexOf("<div class=\"content\">", lastIndex), 
				lastIndex = questData.indexOf("<ul class=\"tags\">", lastIndex)));
		Log.d("HabraQuestParser.parseQuestFromList", "Parse tags");
    	lastIndex += 17;
    	quest.tags = new String(questData.substring(lastIndex, lastIndex = questData.indexOf("</ul>", lastIndex)));   	

    	quest.accepted = questData.indexOf("answer-accepted", lastIndex) != -1;
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse ID");
    	quest.id = Integer.valueOf(questData.substring(
    			lastIndex = (questData.indexOf("id=\"infopanel", lastIndex) + 13), 
    			lastIndex = questData.indexOf('"', lastIndex)));
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse rating");
    	String rs = questData.substring(
    			lastIndex = (questData.indexOf('>', (questData.indexOf("mark\">", lastIndex) + 6)) + 1),
    			lastIndex = questData.indexOf('<', lastIndex));
    	if(rs.charAt(0) == '-') quest.rating = -1; else quest.rating = 1;
    	rs = "0" + rs.substring(1);
    	quest.rating *= Integer.valueOf(rs);
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse answers count");
    	int aci = (questData.indexOf("#comments\">", lastIndex) + 11);
    	if(aci != 10)
    	{
	    	String acs = questData.substring(aci, lastIndex = questData.indexOf(' ', aci));
	    	if(acs.codePointAt(0) == 1073) quest.answerCount = 0;
	    	else quest.answerCount = Integer.valueOf(acs);
    	}
    	
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse date");
    	quest.date = new String(questData.substring(
    			lastIndex = (questData.indexOf("<span>", questData.indexOf("<div class=\"published\">", lastIndex)) + 6),
    			lastIndex = questData.indexOf('<', lastIndex)));
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse favorites");
    	
    	quest.inFavs = questData.indexOf("js-to_favs_remove", lastIndex) != -1;
    	
    	String favs = questData.substring(
    			lastIndex = (questData.indexOf('>', questData.indexOf("<div class=\"favs", lastIndex)) + 1), 
    			lastIndex = questData.indexOf('<', lastIndex));
    	if(favs.length() > 0) quest.favsCount = Integer.valueOf(favs);
    	else quest.favsCount = 0;
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Parse author");
    	quest.author = new String(questData.substring(
    			lastIndex = (questData.indexOf("url\"><span>", lastIndex) + 11), 
    			lastIndex = questData.indexOf('<', lastIndex)));
    	//js-comments-count">
    	
    	Log.d("HabraQuestParser.parseQuestFromList", "Save position");
    	// ��������� �������� �������
    	mStartPosition = endPosition;
		return quest;
	}
	
	/**
	 * �������� ������ ������
	 * @return ������ �������
	 */
	public HabraQuest parseFullQuest()
	{
		if(mData == null) return null;
		
		Log.d("HabraQuestParser.parseFullQuest", "Find quest");
		
		// ������� ������ �������
    	int lastIndex = mData.indexOf("<div class=\"hentry ");
    	if(lastIndex == -1) return null;
    	
    	Log.d("HabraQuestParser.parseFullQuest", "HabraQuest quest = new");
    	HabraQuest quest = new HabraQuest();
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse data");
    	Log.d("HabraQuestParser.parseFullQuest", "Parse Title");
    	quest.title = new String(mData.substring(
    			lastIndex = (mData.indexOf("class=\"topic\">", lastIndex) + 14), 
    			lastIndex = mData.indexOf('<', lastIndex)));
  
    	Log.i("QuestData", quest.title);
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse Content");
		quest.text = new String(mData.substring(
				lastIndex = mData.indexOf("<div class=\"content\">", lastIndex), 
				lastIndex = mData.indexOf("<ul class=\"tags\">", lastIndex)));
		Log.d("HabraQuestParser.parseFullQuest", "Parse tags");
    	lastIndex += 17;
    	quest.tags = new String(mData.substring(lastIndex, lastIndex = mData.indexOf("</ul>", lastIndex)));   	

    	quest.accepted = mData.indexOf("answer-accepted", lastIndex) != -1;
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse ID");
    	quest.id = Integer.valueOf(mData.substring(
    			lastIndex = (mData.indexOf("id=\"infopanel", lastIndex) + 13), 
    			lastIndex = mData.indexOf('"', lastIndex)));
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse rating");
    	String rs = mData.substring(lastIndex = (mData.indexOf('>', (mData.indexOf("mark\">", lastIndex) + 6)) + 1), 
    			lastIndex = mData.indexOf('<', lastIndex));
    	if(rs.charAt(0) == '-') quest.rating = -1; else quest.rating = 1;
    	rs = "0" + rs.substring(1);
    	quest.rating *= Integer.parseInt(rs);
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse date");
    	quest.date = new String(mData.substring(
    			lastIndex = (mData.indexOf("<span>", mData.indexOf("<div class=\"published\">", lastIndex)) + 6),
    			lastIndex = mData.indexOf('<', lastIndex)));
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse favorites");
    	
    	quest.inFavs = mData.indexOf("js-to_favs_remove", lastIndex) != -1;
    	
    	String favs = mData.substring(
    			lastIndex = (mData.indexOf('>', mData.indexOf("<div class=\"favs", lastIndex)) + 1), 
    			lastIndex = mData.indexOf('<', lastIndex));
    	if(favs.length() > 0) quest.favsCount = Integer.valueOf(favs);
    	else quest.favsCount = 0;
    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse author");
    	quest.author = new String(mData.substring(
    			lastIndex = (mData.indexOf("url\"><span>", lastIndex) + 11), 
    			lastIndex = mData.indexOf('<', lastIndex)));
    	    	
    	Log.d("HabraQuestParser.parseFullQuest", "Parse comments");
    	lastIndex = mData.indexOf("<ul class=\"hentry hsubentry", lastIndex);
    	if(lastIndex > 0)
    	{
    		String commentsData = new String(mData.substring(lastIndex, mData.indexOf("<div class=\"hsublevel", lastIndex)));
    		HabraQAComment comment = null;
    		List<HabraQAComment> commentsList = new ArrayList<HabraQAComment>();
    		
    		Log.i("commentsList", String.valueOf(commentsList != null) + commentsList.toString());
    		
    		int subIndex = 0;
    		while((subIndex = commentsData.indexOf("<li id=\"comment_", subIndex)) != -1)
    		{
    			Log.d("HabraQuestParser.parseFullQuest", "new Comment");
    			comment = new HabraQAComment();
    			subIndex += 16;
    			Log.d("HabraQuestParser.parseFullQuest", "Parse comment.id");
    			comment.id = Integer.valueOf(commentsData.substring(subIndex, subIndex = commentsData.indexOf('"', subIndex)));
    			Log.d("HabraQuestParser.parseFullQuest", "Parse comment.text");
    			comment.text = new String(commentsData.substring(
    					subIndex = (commentsData.indexOf("content-only\">", subIndex) + 14), 
    					subIndex = commentsData.indexOf("&nbsp;<span class=\"fn", subIndex)));
    			Log.d("HabraQuestParser.parseFullQuest", "Parse comment.author");
    			comment.author = new String(commentsData.substring(
    					subIndex = (commentsData.indexOf("http://", subIndex) + 7), 
    					subIndex = commentsData.indexOf('.', subIndex)));
    			Log.d("HabraQuestParser.parseFullQuest", "Parse comment.date");
    			comment.date = new String(commentsData.substring(
    					subIndex = (commentsData.indexOf('>', commentsData.indexOf("<abbr", subIndex)) + 1), 
    					subIndex = commentsData.indexOf("</abbr", subIndex)));
    			
    			Log.d("HabraQuestParser.parseFullQuest", "add(comment)");
    			commentsList.add(comment);
    			Log.d("HabraQuestParser.parseFullQuest", "do while");
    		}
    		
    		Log.d("HabraQuestParser.parseFullQuest", "toArray");
    		quest.comments = commentsList.toArray(new HabraQAComment[0]);
    	}

    	
    	lastIndex = mData.indexOf("js-comments-count\">", lastIndex) + 19;
    	if(lastIndex != 18)
    	{
    		quest.answerCount = Integer.valueOf(mData.substring(lastIndex, mData.indexOf('<', lastIndex)));
    	}
    	else quest.answerCount = 0;
    	    	
    	return quest;
	}
}
