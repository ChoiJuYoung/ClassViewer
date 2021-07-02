package homework;

import java.io.*;

class FileOpen 
{
	private String Source;
	
	public void setSource(String str)
	{
		if(Source == null)
			Source = str;
		else
			Source += str;
	}
	
	public String getSource()
	{
		String temp = Source;
		Source = "";
		return temp;
	}
	
	public FileOpen(String name)
	{
		int b = 0;
		StringBuffer buf = new StringBuffer();
		FileInputStream file = null;
		try{
			file = new FileInputStream(name);
			b = file.read();
			while(b != -1)
			{
				buf.append((char)b);
				b = file.read();
			}
			setSource(buf.toString());			
		}
		catch(FileNotFoundException e)
		{
			System.out.println("파일을 찾을 수 없습니다.");
		}
		catch(IOException e)
		{
			System.out.println("Input err");
		}
		
	}
}
