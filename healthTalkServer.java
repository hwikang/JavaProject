

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class healthTalkServer extends Thread {
	ServerSocket ss;
	
	
	
	
	ArrayList<ChatService> conList = new ArrayList<ChatService>();
	
	public healthTalkServer() {
		this.start();
	}
	
	public void run() {
		try {
			ss = new ServerSocket(17170);
			while(true) {
			System.out.println("server waiting connection");	
			Socket s =ss.accept();
			
			ChatService cs = new ChatService(s);
			conList.add(cs);
			
			String nameList = getUserIdAll();
			
			cs.setMessageAll("/@NL" + nameList);
			cs.setMessageAll("/@UC" +cs.userIp+"¥‘¿Ã ¡¢º”«œºÃΩ¿¥œ¥Ÿ.");
			
			Thread t = new Thread(cs);
			t.start();
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	public String getUserIdAll() {
		String idList = "";
		for(int i = 0; i<conList.size(); i++) {
			idList += conList.get(i).userIp +"/";				
		}
		return idList;
	}
	
	//inner class for get info
	class ChatService extends Thread{
		Socket s ;
		BufferedReader br;
		PrintWriter pw;
		String userIp ;
		public ChatService(Socket s) {
			this.s=s;
			
			try {
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));

				pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
				
				InetAddress ia = s.getInetAddress();
				userIp = ia.getHostAddress();
			} catch (Exception e) {
				System.out.println("chatservice instance error");
				e.printStackTrace();
			}
			
					
		
		}//instance method
		
		public void setMessageAll(String msg) {
			for(int i = 0; i<conList.size();i++) {
				try {
					ChatService cs = conList.get(i);
					cs.pw.println(msg);
					cs.pw.flush();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("¡¢º”¿⁄ø¨∞·≤˜±Ë");
					conList.remove(i);
					i--;
					
					
					
				}
		
			}
		}
		
		public void run() {
			while(true) {
				try {
					String msg = br.readLine();
					if(!msg.equals("")) {
						setMessageAll("/@MS"+"["+userIp+"]"+msg);							
						
					}
				}catch (Exception e) {
					System.out.println("msg error");
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		new healthTalkServer();
	}
}
