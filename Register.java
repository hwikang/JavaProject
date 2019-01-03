

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener, KeyListener{

	JPanel allPanel = new JPanel(new GridLayout(4, 1,5,5));
		JPanel idPanel = new JPanel(new GridLayout(1, 3,5,5));
			JLabel idlabel = new JLabel("ID");
			JTextField idField = new JTextField(13);
			JButton idCheckBtn = new JButton("ID 중복체크");
		JPanel pwPanel = new JPanel(new GridLayout(1, 3,5,5));
			JLabel pwlabel = new JLabel("Password");
			JPasswordField pwField = new JPasswordField(13);
			JLabel pwCheckLbl = new JLabel("");			
		JPanel pwRePanel = new JPanel(new GridLayout(1, 3,5,5));
			JLabel pwRelabel = new JLabel("Re-enter Passworkd");
			JPasswordField pwReField = new JPasswordField(13);
			JLabel pwReCheckLbl = new JLabel("");			
		JPanel namePanel = new JPanel(new GridLayout(1, 3,5,5));
			JLabel namelabel = new JLabel("Name");
			JTextField nameField = new JTextField(13);
			JLabel nameCheckLbl = new JLabel("");
		
	JPanel submitPanel = new JPanel(new BorderLayout(5,5));	
		JButton regBtn = new JButton("Register");
	
	Mainpage mp;
	boolean b=true;  // ID중복 체크용 -> true면 회원가입안됨
	public Register(Mainpage mp) {
		this.mp= mp;		
	
		idPanel.add(idlabel); idPanel.add(idField);	
		idPanel.add(idCheckBtn);
		
		pwPanel.add(pwlabel); pwPanel.add(pwField);
		pwPanel.add(pwCheckLbl);
		
		pwRePanel.add(pwRelabel); pwRePanel.add(pwReField);	
		pwRePanel.add(pwReCheckLbl);	
			
		namePanel.add(namelabel);namePanel.add(nameField);
		namePanel.add(nameCheckLbl);
		
		allPanel.add(idPanel); allPanel.add(pwPanel);
		allPanel.add(pwRePanel); allPanel.add(namePanel);
		
		submitPanel.add(regBtn);
		

		add(allPanel,"Center");
		add(submitPanel,"South");
		
		regBtn.addActionListener(this);
		idCheckBtn.addActionListener(this);
		nameField.addKeyListener(this);
		idField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode== KeyEvent.VK_ENTER) {
					idCheck();
				}
			}
		});
		
		/*setSize(500,200);
		//setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);*/
	}

	public gymMemberVO setVO() {  				 //입력값을 vo 값으로 만듬
		gymMemberVO vo = new gymMemberVO();
			
			
			vo.setId(idField.getText().toLowerCase());
			vo.setPwd(pwField.getText());
			vo.setName(nameField.getText());

		return vo;
	}
	
	public void newMember() {  				 //입력값으로 새로운 멤버를 받음
		if(b) {
			JOptionPane.showMessageDialog(this,"중복 확인부터 하세요.");
		}else {
			gymMemberVO vo = setVO();
			gymMemberDAO dao = new gymMemberDAO();
			int result = dao.insertMembership(vo);
			//System.out.println(result);
			if(result>0) {
				//System.out.println(result);
				mp.regDialog.setVisible(false);
			}
		}
		
	}
	
	public void idCheck() {		//id가 중복인지 체크 
		String id =idField.getText().toLowerCase();
		gymMemberDAO dao = new gymMemberDAO();
		List<String> lst = dao.getIdList();
		
		
		for(int i = 0 ; i<lst.size();i++) {	//lst 는 0 혹은 1개가 리턴됨
			System.out.println(lst.get(i));
			if(lst.get(i).equals(id)) {
				b = true;
				break;
			}else {
				b =false;		//찾으면 true 없으면 false로끝 
			}
		}
		if(id.equals("")||id.length()<4) {
			b= true;
			JOptionPane.showMessageDialog(this,"4자 이상 아이디를 입력하세요.");
		}
		else if(b) {
			JOptionPane.showMessageDialog(this,"이미 존재하는 아이디입니다.");
		}else {
			JOptionPane.showMessageDialog(this,"사용가능한 아이디 입니다.");
		}
			

	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getActionCommand();
		System.out.println(obj);
		if(obj.equals("Register")) {
			if(!pwField.getText().equals(pwReField.getText())) {  //when password is wrong
				JOptionPane.showMessageDialog(this,"패스워드가 일치하지 않습니다.");
								
			}else if(idField.getText().equals("")||pwField.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"아이디와 비밀번호를 입력하세요.");
			}else if(nameField.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"이름을 입력하세요.");
			}
			else {										//회원가입완료
				newMember();
			}
		}else if(obj.equals("ID 중복체크")) {
			System.out.println("중복체크중");
			idCheck();
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode== KeyEvent.VK_ENTER) {
			newMember();
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
