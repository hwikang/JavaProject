

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Stats extends JFrame {
	JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
	
	
	
	MenuPage menu;
	public Stats(MenuPage menu) {
		this.menu = menu;
		
		EatStats eat = new EatStats(menu);
		ExerStats exer = new ExerStats(menu);
				
				
				
		tp.add("� ��� ���", exer );
		tp.add("�Ĵ� ��� ���", eat);
		
		
		
		add(tp);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600,500);
		setVisible(true);
	}

	/*public static void main(String[] args) {
		new Stats();

	}
*/
}
