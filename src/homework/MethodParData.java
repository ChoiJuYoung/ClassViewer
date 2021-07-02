package homework;

class MethodParData 
{
	private String[] parameter;
	
	
	public void setSize(int n)
	{
		parameter = new String[n];
		for(int i = 0; i < n; i++)
			parameter[i] = "";
	}
	
	public void mpdadd(int n, String s)
	{
		parameter[n] = s;
	}
	
	public String getpar(int n)
	{
		return parameter[n];
	}
}
