

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
			JButton idCheckBtn = new JButton("ID �ߺ�üũ");
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
	boolean b=true;  // ID�ߺ� üũ�� -> true�� ȸ�����Ծȵ�
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

	public gymMemberVO setVO() {  				 //�Է°��� vo ������ ����
		gymMemberVO vo = new gymMemberVO();
			
			
			vo.setId(idField.getText().toLowerCase());
			vo.setPwd(pwField.getText());
			vo.setName(nameField.getText());

		return vo;
	}
	
	public void newMember() {  				 //�Է°����� ���ο� ����� ����
		if(b) {
			JOptionPane.showMessageDialog(this,"�ߺ� Ȯ�κ��� �ϼ���.");
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
	
	public void idCheck() {		//id�� �ߺ����� üũ 
		String id =idField.getText().toLowerCase();
		gymMemberDAO dao = new gymMemberDAO();
		List<String> lst = dao.getIdList();
		
		
		for(int i = 0 ; i<lst.size();i++) {	//lst �� 0 Ȥ�� 1���� ���ϵ�
			System.out.println(lst.get(i));
			if(lst.get(i).equals(id)) {
				b = true;
				break;
			}else {
				b =false;		//ã���� true ������ false�γ� 
			}
		}
		if(id.equals("")||id.length()<4) {
			b= true;
			JOptionPane.showMessageDialog(this,"4�� �̻� ���̵� �Է��ϼ���.");
		}
		else if(b) {
			JOptionPane.showMessageDialog(this,"�̹� �����ϴ� ���̵��Դϴ�.");
		}else {
			JOptionPane.showMessageDialog(this,"��밡���� ���̵� �Դϴ�.");
		}
			

	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getActionCommand();
		System.out.println(obj);
		if(obj.equals("Register")) {
			if(!pwField.getText().equals(pwReField.getText())) {  //when password is wrong
				JOptionPane.showMessageDialog(this,"�н����尡 ��ġ���� �ʽ��ϴ�.");
								
			}else if(idField.getText().equals("")||pwField.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"���̵�� ��й�ȣ�� �Է��ϼ���.");
			}else if(nameField.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"�̸��� �Է��ϼ���.");
			}
			else {										//ȸ�����ԿϷ�
				newMember();
			}
		}else if(obj.equals("ID �ߺ�üũ")) {
			System.out.println("�ߺ�üũ��");
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
