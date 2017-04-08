package com.pa189.ims;

import java.util.ArrayList;

public class item {
private String ques;
private String tag;
private String date;
private int no_sub;
private String ans;
private String quesno;
private String imgques;
private String imgans;
private ArrayList<Child> children;
public item()
{
	this.no_sub=0;
}
public String getQues()
{
	return ques;
}
public void setQues(String ques)
{this.ques=ques;
}

public String getAns()
{
	return ans;
}
public void setAns(String ques)
{this.ans=ques;
}
public String getQuesno()
{
	return quesno;
}
public void setQuesno(String ques)
{this.quesno=ques;
}
public String getImgQues()
{
	return imgques;
}
public void setImgQues(String ques)
{this.imgques=ques;
}
public String getImgAns()
{
	return imgans;
}
public void setImgAns(String ques)
{this.imgans=ques;
}
public String getTag()
{
	return tag;
}
public void setTag(String tag)
{this.tag=tag;
}
public String getdate()
{
	return date;
}
public void setDate(String date)
{this.date=date;
}
public int getNoSub()
{
	return no_sub;
}
public void setNoSub(int no_sub)
{this.no_sub=no_sub;
}
public ArrayList<Child> getChildren()
{
	return children;
}

public void setChildren(ArrayList<Child> children)
{
	this.children = children;
}
}