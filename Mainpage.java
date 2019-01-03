

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.peer.KeyboardFocusManagerPeer;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Mainpage extends JFrame implements ActionListener, KeyListener{
	
		Image img = Toolkit.getDefaultToolkit().getImage("img/mainImage.jpg");
	JLabel imgLbl = new JLabel(new ImageIcon(img));	
	
	
	JPanel idPanel = new JPanel(new FlowLayout());
		JPanel idLabelPanel = new JPanel(new GridLayout(2,1));
			JLabel idLabel = new JLabel("���̵� : ");
			JLabel pwLabel = new JLabel("��й�ȣ : ");
		JPanel idFieldPanel = new JPanel(new GridLayout(2,1));
			JTextField idField = new JTextField(15);
			JPasswordField pwField = new JPasswordField(15);
		JPanel idBtnPanel = new JPanel(new GridLayout(1,2));
			JButton loginBtn = new JButton("LogIn");
			JButton regBtn = new JButton("Register");
		
	
	JDialog regDialog;
	JDialog menuDialog;
	Register reg ;
	
	String loginId="";
	public Mainpage() {
		super("�α���");
		
	/*			idPanel.setBackground(Color.BLACK);
				idLabelPanel.setBackground(Color.BLACK);
				idFieldPanel.setBackground(Color.BLACK);*/
				idLabel.setForeground(Color.white);
				pwLabel.setForeground(Color.white);
				
				idLabelPanel.add(idLabel);
				idLabelPanel.add(pwLabel);
				
				idFieldPanel.add(idField);
				idFieldPanel.add(pwField);
				
				idBtnPanel.add(loginBtn);
				idBtnPanel.add(regBtn);
				
			idPanel.add(idLabelPanel,"East");
			idPanel.add(idFieldPanel,"Center");
			idPanel.add(idBtnPanel,"South");
		//JPanel titlePanel = new JPanel(new GridLayout(2, 1));
		JLabel title = new JLabel("Come on");
		JLabel title2 = new JLabel("Let's");
		JLabel title3 = new JLabel("DO THAT SHIT");
		Font fnt = new Font("orange juice",Font.BOLD,70);	
		title.setFont(fnt); title2.setFont(fnt); title3.setFont(fnt);
		//titlePanel.add(title); titlePanel.add(title2);
		
		
		title.setBounds(50,50,500,100);
		title2.setBounds(50,150,400,100);
		title3.setBounds(50,250,600,100);
		
		title.setForeground(Color.WHITE);
		title2.setForeground(Color.WHITE);
		title3.setForeground(Color.WHITE);
		idLabelPanel.setOpaque(false);
		idPanel.setOpaque(false);
		idPanel.setBounds(290,350,300,100);//900	506
					
		
		add(BorderLayout.CENTER,imgLbl);
		
		imgLbl.add(title); imgLbl.add(title2); imgLbl.add(title3);
		imgLbl.add(idPanel);
		
		loginBtn.addActionListener(this);
		regBtn.addActionListener(this);
		
		pwField.addKeyListener(this);
		
		//setLayout(null);
		setSize(900,506);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}
	public static void main(String[] args) {
		new Mainpage(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {   //login & register
		Object obj = e.getActionCommand();
		
		if(obj.equals("LogIn")) {
			login();
		}else if(obj.equals("Register")) {
			register();
		}
		
	}
	
	public void register() {		//(�θ������̳�,���â�̸�,�θ�â���Ұ��ɿ���)
		reg = new Register(this);
		regDialog = new JDialog(this, "ȸ������", true);		
		regDialog.add(reg);
		regDialog.setSize(500, 220);
		regDialog.setVisible(true);
		
	
	}
	public void login() {

		if(idField.getText().equals("") ||pwField.getText().equals("")) {
			JOptionPane.showMessageDialog(this,"���̵�� ��й�ȣ�� �Է��ϼ���.");
		}else {
			gymMemberDAO dao = new gymMemberDAO();
			//List lst = dao.getIdList();
			int chk = dao.getLoginCheck(idField.getText(), pwField.getText());
			if(chk==0) {
				JOptionPane.showMessageDialog(this,"���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
			}else {
				JOptionPane.showMessageDialog(this,"�α��μ���.");
				new MenuPage(this); //�޴������� ��ȯ					
				setVisible(false);	//�޴������� �θ��� ��������	
			}
		}
		/*//System.out.println("Log");
		for(int i = 0; i<lst.size();i++) {
			if(lst.get(i).equals(idField.getText())){  //���̵� �����ϴ°�
				//���̵�� ��й�ȣ�� ��ġ�ϴ°�				
				String realPwd = dao.getPassword(idField.getText());  //�����ͻ��� ���� �н�����
				if(realPwd.equals(pwField.getText())) {
					loginId=pwField.getText();
					new MenuPage(this); //�޴������� ��ȯ					
					setVisible(false);	//�޴������� �θ��� ��������	
					
					break;
				}else {
					JOptionPane.showMessageDialog(this,"��й�ȣ�� Ʋ�Ƚ��ϴ�.");
				}
				
			}
		}*/
		
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_ENTER) {
			System.out.println("ENTER");
			login();
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
