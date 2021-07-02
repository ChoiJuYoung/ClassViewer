package homework;

class ClassData 
{
	String[] Name;
	int i;
	public ClassData()
	{
		i = 0;
	}
	
	public void CDnum(int n)
	{
		Name = new String[n];
	}
	
	public void CDadd(String s)
	{
		Name[i] = s;
		i++;
	}
	
	public String getCD(int n)
	{
		return Name[n];
	}
}
