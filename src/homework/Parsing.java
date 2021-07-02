package homework;

import java.util.StringTokenizer;

class Parsing 
{
	private StringTokenizer st;
	
	public void Parse(String src, String delim)
	{
		st = new StringTokenizer(src,delim);
	}
	
	public String getTokenSt()
	{
		return st.nextToken();
	}
	
	public String getTokenSt(String delim)
	{
		return st.nextToken(delim);
	}
	
	public boolean gethasMore()
	{
		return st.hasMoreTokens();
	}
	
	public int countSt()
	{
		return st.countTokens();
	}
}
