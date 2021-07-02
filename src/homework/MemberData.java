package homework;

class MemberData 
{
	private String[] Name,Type,Access,Method;
	int i;
	
	public void setSize(int n)
	{
		Name = new String[n];
		Type = new String[n];
		Access = new String[n];
		Method = new String[n];
		
		for(int i = 0; i < Name.length; i++)
			Method[i] = "";
		
		i = 0;
	}
	
	public int num()
	{
		return Name.length;
	}
	
	public void MDadd(String name, String type, String access, String method)
	{
		Name[i] = name.trim();
		Type[i] = type.trim();
		Access[i] = access.trim();
		Method[i] = method.trim();
		i++;
	}
	
	public void addmethod(int n, String md)
	{
		Method[n] += (md + " ");
	}
	
	public String getName(int n)
	{
		return Name[n];
	}
	
	public String getType(int n)
	{
		return Type[n];
	}
	
	public String getAccess(int n)
	{
		return Access[n];
	}
	
	public String getMethod(int n)
	{
		return Method[n];
	}
}
