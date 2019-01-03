

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPage extends JFrame implements ActionListener{

	JPanel pane = new JPanel(new GridLayout(2,2));
	
		
		JButton ExerciseBtn = new JButton("����");
		JButton recordBtn = new JButton("���");
		JButton eatBtn = new JButton("�Ĵܱ��");
		JButton chatBtn = new JButton("ä��");
		Font font = new Font("�޸տ�ü", Font.BOLD, 50);
		Mainpage mp;
		JDialog exerpage;
	public MenuPage(Mainpage mp) {
		super("�޴�");
		this.mp = mp;
		setLayout(new BorderLayout());
		
		pane.add(ExerciseBtn);pane.add(recordBtn);
		pane.add(eatBtn);pane.add(chatBtn);
		add(pane);
		
		ExerciseBtn.setFont(font);
		recordBtn.setFont(font);
		eatBtn.setFont(font);
		chatBtn.setFont(font);
		
		ExerciseBtn.addActionListener(this);
		recordBtn.addActionListener(this);
		eatBtn.addActionListener(this);
		chatBtn.addActionListener(this);
		
		setSize(530,530);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}

/*	public static void main(String[] args) {
		new MenuPage();

	}
*/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getActionCommand();
		if(obj.equals("����")) {			
			new ExercisePage(this);   //�
			
		}else if(obj.equals("�Ĵܱ��")) {
			new EatPage(this);
		}else if(obj.equals("���")) {
			new Stats(this);
		}else if(obj.equals("ä��")) {
			new healthTalkClient(this);
		}
		
	}

}
