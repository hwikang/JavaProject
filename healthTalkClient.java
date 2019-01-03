

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class healthTalkClient extends JFrame implements Runnable,ActionListener,KeyListener {
	
	JPanel topPanel = new JPanel(new BorderLayout());
		JButton conBtn = new JButton("접속하기");
		JLabel listLbl = new JLabel("접속하기");
		JButton listBtn = new JButton("접속자목록");
	JPanel centerPanel =  new JPanel(new BorderLayout());
		JTextArea chatTa = new JTextArea();
		JScrollPane sp = new JScrollPane(chatTa);
	JPanel bottomPanel =  new JPanel(new BorderLayout());
		JTextField chatTf = new JTextField();
		JButton sendBtn = new JButton("SEND");
	
	
	
	
	Socket s;
	BufferedReader br;
	PrintWriter pw;
	
	DefaultListModel<String> model= new DefaultListModel<String>();
	
	MenuPage menu;
	public healthTalkClient(MenuPage menu) {
		super("짐톡");
		this.menu = menu;
		
		topPanel.add(conBtn,"West");
		topPanel.add(listLbl,"Center");
		topPanel.add(listBtn,"East");
		centerPanel.add(sp);
		bottomPanel.add(chatTf,"Center");
		bottomPanel.add(sendBtn,"East");
		
		
		add(topPanel,"North");
		add(centerPanel,"Center");
		add(bottomPanel,"South");
		
		
		
		setVisible(true);
		setSize(350,700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		conBtn.addActionListener(this);
		listBtn.addActionListener(this);
		sendBtn.addActionListener(this);
		chatTf.addKeyListener(this);
		
		
		
		
		
		
		
	}
	public void setConnectionServer() {
		try {
			InetAddress ia = InetAddress.getByName("172.16.1.13");		//ip
			s = new Socket(ia,17170);
			listLbl.setVisible(false);
			listLbl.setText("접속이 완료 되었습니다.");
			listLbl.setVisible(true);
			br =new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			System.out.println("setCONNECTION SERVER ERROR");
			listLbl.setVisible(false);
			listLbl.setText("접속에 실패 하였습니다.");
			listLbl.setVisible(true);
			e.printStackTrace();
		}
	}
	public void sendMessage() {
		String msg = chatTf.getText();
		if(!msg.equals("")) {
			pw.println(msg);
			pw.flush();
			chatTf.setText("");
			
		}
	}
	

	/*public static void main(String[] args) {
		new healthTalkClient();

	}*/
	@Override
	public void run() {
		while(true) {
			try {
				String msg = br.readLine();
				if(msg!=null) {
					if(msg.substring(0, 4).equals("/@NL")){
						String id = msg.substring(4);
						StringTokenizer token = new StringTokenizer(id, "/");
						
						while(token.hasMoreTokens()) {
							model.addElement(token.nextToken());
						}
						
					}if(msg.substring(0, 4).equals("/@MS")){
						chatTa.append(msg.substring(4)+"\n");
					}if(msg.substring(0, 4).equals("/@UC")){
						chatTa.append(msg.substring(4)+"\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("input error");
			}
			
			
			
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getActionCommand();
		if(obj.equals("접속하기")) {
			setConnectionServer();
		}else if(obj.equals("접속자목록")) {
			JDialog nl = new JDialog(this, "접속자목록", true);
			nameListDialog nlDialog = new nameListDialog(this);
			nl.add(nlDialog);
			nl.setSize(200, 400);
			nl.setVisible(true);
			
			nl.setDefaultCloseOperation(DISPOSE_ON_CLOSE);			
			
		}else if(obj.equals("SEND")) {
			sendMessage();			
			
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if(keycode == KeyEvent.VK_ENTER) {
			sendMessage();	
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
