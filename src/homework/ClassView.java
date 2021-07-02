// 2014920034 �̻�
// 2014920056 ���ֿ�
// �� �ۼ��Ͽ����ϴ�.
package homework;

import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.event.*;


public class ClassView extends JFrame implements KeyListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static ClassView cv;
	private JTree tree;
	private JTextArea textbody;
	private JTextArea textvariable;
	private DefaultMutableTreeNode[] methodnode;
	private DefaultMutableTreeNode[] variablenode;
	private JScrollPane scroll; 
	private JPanel panel;
	private JScrollPane tableScrollPane;
	private JScrollPane textScrollPane;
	private JTable table;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem menuopen, menusave, menuexit;
	private ClassData CD = new ClassData(); // Class ������ ������ ��
	private MethodData MtD = new MethodData(); // Method ������ ������ ��
	private MemberData MeD = new MemberData(); // Variable ������ ������ ��
	private MethodParData MPD = new MethodParData(); // Method Parameter�� ������ ��
	private JPanel rightPanel;
	private String cppsrc; // cpp�ҽ��� �޾ƿ� ���ڿ�
	
	public void fileopen()
	{
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			//************* File Open
			File file = fc.getSelectedFile();
			if(file.toString().contains(".cpp")) // file�� cpp������ ��� open
			{
				FileOpen FO = new FileOpen(file.toString()); // File Open
				cppsrc = FO.getSource(); // cppsrc�� cpp���� ���� �Է�
				menuopen.setEnabled(false); // ��open����
				//************* File Open �Ϸ�
				cv.ClassViewer();
			}
		}
		else // ������ �߸� �ҷ���
		{
			System.out.println("������ ã�� �� �����ϴ�.");
		}
	}

	public void filesave()
	{
		String save = "class ";
		String access[] = {"public", "private", "protected"};
		save += CD.getCD(0);
		save += "\n{\n";
		
		for(int i = 0; i < access.length; i++)
		{
			
			//����� ���ڿ� ����
			save += (access[i] + ":\n");
			for(int j = 0; j < MtD.num(); j++)
				if(MtD.getAccess(j).equals(access[i]))
					save += ("\t" + (MtD.getType(j) + " " + MtD.getMDname(j) + "(" + MPD.getpar(j) + ");").trim() + "\n");
			
			for(int j = 0; j < MeD.num(); j++)
			{
				if(MeD.getAccess(j).equals(access[i]))
				{
					if(!MeD.getType(j).contains("["))
						save += ("\t" + MeD.getType(j) + " " + MeD.getName(j) + ";\n");
					else
						save += ("\t" + MeD.getType(j).split("\\[")[0] + " " + MeD.getName(j) + "[" + MeD.getType(j).split("\\[")[1].split("\\]")[0] + "];\n");
				}
			}			
		}
		save += "};\n";
		
		for(int i = 0; i < MtD.num(); i++)
		{
			save += (CD.getCD(0) + "::");
			save += (MtD.getMDname(i) + "(" + MPD.getpar(i) + ")\n{" + MtD.getBody(i) + "\n}\n");
		}
		
		// file�� ���

		JFileChooser fc = new JFileChooser();
		PrintWriter out = null;
		
		int returnVal = fc.showSaveDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			if(file.toString().contains(".cpp")) // cpp���Ϸ� ������ ��츸 ����
			{
				try {
					out = new PrintWriter(file.toString());
					out.print(save);
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		else // ������ �߸� ������
		{
		}
	
	}
	
	
	public ClassView()
	{
		// â �Ӽ�
		this.setSize(800,500);
		this.setTitle("Class Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menubar = new JMenuBar();
		menu = new JMenu("File");
		menuopen = new JMenuItem("Open");
		menusave = new JMenuItem("Save");
		menuexit = new JMenuItem("Exit");
		
		menuopen.addActionListener(this);
		menusave.addActionListener(this);
		menuexit.addActionListener(this);
		
		menu.add(menuopen);
		menu.add(menusave);
		menu.add(menuexit);
		menubar.add(menu);
		setJMenuBar(menubar);
		
		this.setVisible(true);
		
	}
	
	
	public void ClassViewer()
	{		
		// ���� �ҽ�
		String temp; // Parsing�� ������ String Variable
		String[] splitt; // split�� ������ String Variable
		StringTokenizer s;
		String tmp;
		
		
		//************* Parsing
		splitt = cppsrc.split("class"); // Ŭ������ delimiter�� �Ľ�
		CD.CDnum(splitt.length - 1); // Ŭ������ ���� ��������
		
		Parsing ps = new Parsing();
		ps.Parse(cppsrc, "{}");
		while(ps.gethasMore())
		{
			temp = ps.getTokenSt();
			if(temp.contains("class")) // if temp.contains("class") start
			{
				String access,type,name;
				int i = 0;
				s = new StringTokenizer(temp);
				StringTokenizer t;
				temp = s.nextToken();
				temp = s.nextToken();
				
				// Class�̸� ��������
				CD.CDadd(temp);
				// ClassData�� Class �̸� �߰�

				// class body
				temp = ps.getTokenSt();
				s = new StringTokenizer(temp,"(");
				while(s.hasMoreTokens())
				{
					i++;
					s.nextToken();
				}
				MtD.setSize(i-1);
				MPD.setSize(i-1);
				// Method ���� ���ϱ�
				
				s = new StringTokenizer(temp,";");
				i = (-i+1);
				while(s.hasMoreTokens())
				{
					i++;
					s.nextToken();
				}
				MeD.setSize(i-1);
				// Member Data���� ���ϱ�
				
				tmp = temp;
				s = new StringTokenizer(tmp,":");
				temp = s.nextToken();
				access = temp.trim();
				temp = s.nextToken();
				t = new StringTokenizer(temp, ";");
				while(t.hasMoreTokens())
				{
					temp = t.nextToken().trim();
					if(!temp.contains(" "))
					{
						name = temp;
						type = "";
					}
					else
					{
						String OZiSeok[] = temp.split(" ");
						name = OZiSeok[1];
						type = OZiSeok[0];
					}
					
					if(name.contains("["))
					{
						type = type + "[" + name.split("\\[")[1].split("\\]")[0] + "]";
						name = name.split("\\[")[0];
					}
					
					if(name.contains("private") || name.contains("public") || name.contains("protected"))
					{
						access = name;
						tmp = s.nextToken();
						tmp = tmp.trim();
						t = new StringTokenizer(tmp,";");
					}
					else if(name.contains("()"))
					{
						MPD.mpdadd(MtD.MDadd(name.split("\\(")[0],type,access), "");
					}
					else if(name.contains("("))
					{
						MPD.mpdadd(MtD.MDadd(name.split("\\(+")[0],type,access), name.split("\\(+")[1].split("\\)")[0]);
					}
					else
					{
						MeD.MDadd(name,type,access,"");
					}

				}
				
				
			} // if temp.contains("class") END
			else // if !temp.contains("class")
			{
				int i = 0;
				
				if(temp.contains("::")) // if temp contains "::"
				{
					temp = temp.trim();

					String name;
					name = temp.split("::")[1].split("\\(")[0];
					while(!name.equals(MtD.getMDname(i)))
						i++;
					MtD.setk(i);
					temp = ps.getTokenSt();
					MtD.addbody(i, temp);
				}
				else
				{
					if((temp.trim().length() == 0))
					{
						MtD.addbody(MtD.getk(), "}");
					}
					else
					{
						MtD.addbody(MtD.getk(), "{");
						MtD.addbody(MtD.getk(), temp);
					}
					
					if(MtD.getBody(MtD.getk()).trim().equals("}"))
						MtD.setbody(MtD.getk(), "");
					
				}
			}
		}
		//************* Parsing END
		
		/* ���� �ø����б� ��ǻ�� ���к�
		 * 2014920034 �̻�
		 * 2014920056 ���ֿ� */
		
		
		//************* Show Using Methods, Variable
		for(int i = 0; i < MtD.num(); i++)
		{
			for(int j = 0; j < MeD.num(); j++)
			{
				if(MtD.getBody(i).contains(MeD.getName(j)))
				{
					MtD.addvariabe(i, MeD.getName(j));
					MeD.addmethod(j, MtD.getMDname(i));
				}
			}
		}
		//************* Show Using MEthods, Variable ENDS

		//************* GUI
		
		// node ����		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(CD.getCD(0));
		methodnode = new DefaultMutableTreeNode[MtD.num()];
		variablenode = new DefaultMutableTreeNode[MeD.num()];
		
		// �ʱ�ȭ �� ��Ʈ�� �߰�
		for(int i = 0; i < MtD.num(); i++)
		{
			methodnode[i] = new DefaultMutableTreeNode(MtD.getMDname(i) + "(" + MPD.getpar(i) + ")");
			root.add(methodnode[i]);
		}
		
		for(int i = 0; i < MeD.num(); i++)
		{
			if(MeD.getType(i).contains("["))
				variablenode[i] = new DefaultMutableTreeNode(MeD.getName(i) + ": " + MeD.getType(i).split("\\[")[0] + "[]");
			else
				variablenode[i] = new DefaultMutableTreeNode(MeD.getName(i) + ": " + MeD.getType(i));
			root.add(variablenode[i]);
		}
		
		// root�� root�� JTree ����
		tree = new JTree(root);
		
		textvariable = new JTextArea();
		textvariable.setEditable(false);
		
		table = new JTable();		
		table.setEnabled(false);
		tableScrollPane = new JScrollPane();
		this.tableScrollPane.setViewportView(table);
		
		textbody = new JTextArea();
		textbody.addKeyListener(this);
		this.textScrollPane = new JScrollPane();
		this.textScrollPane.setViewportView(this.textbody);

		this.rightPanel = new JPanel();
		this.rightPanel.setLayout(new CardLayout());
		this.rightPanel.add(textScrollPane);
		this.rightPanel.add(tableScrollPane);
		add(rightPanel,"Center");
		
		
		tree.setVisibleRowCount(14);
		tree.addTreeSelectionListener(new TreeListener());

		scroll = new JScrollPane(tree);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(scroll);
		panel.add(textvariable);
		add(panel,"West");

		setVisible(true);
		//************* GUI END
	}
	
	//************* Tree Listener
	private class TreeListener implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			if(node == null)
				return ;
			String s = (String) node.getUserObject();
			
			if(s.contains("("))
			{
				int i = 0;
				while(!(MtD.getMDname(i).equals(s.split("\\(")[0])))
					i++;
				MtD.setk(i);
				textbody.setText(MtD.getBody(i));
				textvariable.setText("Using Variable\n" + MtD.getvariable(i));
				CardLayout c = (CardLayout)rightPanel.getLayout();
				c.first(rightPanel);
			}
			else if(s.equals(CD.getCD(0)))
			{
				String columnNames[] =	{"Name", "Type", "Access"};

				String[][] rowData = new String[MtD.num() + MeD.num()][3];
				
				for(int i=0; i < MtD.num(); i++)
				{
					rowData[i][0] = MtD.getMDname(i) + "(" + MPD.getpar(i) + ")";
					rowData[i][1] = MtD.getType(i);
					rowData[i][2] = MtD.getAccess(i);
				}
				
				for(int i = MtD.num(); i < MtD.num() + MeD.num(); i++)
				{
					rowData[i][0] = MeD.getName(i - MtD.num());
					rowData[i][2] = MeD.getAccess(i - MtD.num());
					
					if(MeD.getType(i - MtD.num()).contains("["))
						rowData[i][1] = (MeD.getType(i - MtD.num())).split("\\[")[0] + "[]";
					else
						rowData[i][1] = MeD.getType(i - MtD.num());
				}
				table.setModel(new DefaultTableModel(rowData, columnNames));
				CardLayout c = (CardLayout)rightPanel.getLayout();
				c.last(rightPanel);
				textvariable.setText("");
			}
			else
			{
				int i = 0;
				while(!(s.split("\\:")[0].equals(MeD.getName(i))))
					i++;
				
				String columnNames[] = {"Name", "methods"};
				String rowData[][] = { {s.split("\\:")[0] , MeD.getMethod(i)} };

				table.setModel(new DefaultTableModel(rowData, columnNames));
				CardLayout c = (CardLayout)rightPanel.getLayout();
				c.last(rightPanel);
				textvariable.setText("");
			}
		}
	}
	//************* Tree Listener END
	
	//************* Key Listener
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) 
	{
		MtD.setbody(MtD.getk(), textbody.getText());
	}
	
	
	//************* Key Listener END
	
	//************* Action Listener
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == menuopen)
		{
			cv.fileopen();
		}
		else if(e.getSource() == menusave)
		{
			cv.filesave();
		}
		else if(e.getSource() == menuexit)
		{
			System.exit(0);
		}
		else
		{
			System.out.println("�߸��� �����Դϴ�.");
		}
	}
	
	//************* Action Listener END
	public static void main(String[] args) 
	{
		try
		{
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		cv = new ClassView();
	}
}
