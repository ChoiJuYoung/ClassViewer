package homework;

class MethodData 
{
	private String[] Name,Type,Access,Body, Variable;
	private int i,k;
	
	
	public void setSize(int n)
	{
		Name = new String[n];
		Type = new String[n];
		Access = new String[n];
		Body = new String[n];
		Variable = new String[n];
		for(int i =0; i<Name.length; i++)
		{
			Body[i] = "";
			Variable[i] = "";
		}
		
		i = 0;
	}
	
	public int num()
	{
		return Name.length;
	}
	
	public void addvariabe(int n, String s)
	{
		Variable[n] += (s + " ");
	}
	
	public String getvariable(int n)
	{
		return Variable[n];
	}
	
	public void addbody(int n, String body)
	{
		Body[n] += body;
	}
	
	public void setbody(int n, String body)
	{
		Body[n] = body;
	}
	
	public int MDadd(String name, String type, String access)
	{
		Name[i] = name.trim();
		Type[i] = type.trim();
		Access[i] = access.trim();
		i++;
		return i-1;
	}
	
	public void setk(int n)
	{
		k = n;
	}
	
	public int getk()
	{
		return k;
	}
	
	public String getMDname(int n)
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
	
	public String getBody(int n)
	{
		return Body[n];
	}
}
