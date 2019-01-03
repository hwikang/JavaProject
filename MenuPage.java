

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
	
		
		JButton ExerciseBtn = new JButton("운동기록");
		JButton recordBtn = new JButton("통계");
		JButton eatBtn = new JButton("식단기록");
		JButton chatBtn = new JButton("채팅");
		Font font = new Font("휴먼옛체", Font.BOLD, 50);
		Mainpage mp;
		JDialog exerpage;
	public MenuPage(Mainpage mp) {
		super("메뉴");
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
		if(obj.equals("운동기록")) {			
			new ExercisePage(this);   //운동
			
		}else if(obj.equals("식단기록")) {
			new EatPage(this);
		}else if(obj.equals("통계")) {
			new Stats(this);
		}else if(obj.equals("채팅")) {
			new healthTalkClient(this);
		}
		
	}

}
