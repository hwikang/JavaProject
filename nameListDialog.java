

import javax.swing.JList;
import javax.swing.JPanel;

public class nameListDialog extends JPanel{
	JList<String> nl = new JList<String>();
	
	
	
	healthTalkClient hc;
	public nameListDialog(healthTalkClient hc) {
		this.hc= hc;
		if(!hc.model.isEmpty()) {
			nl.setModel(hc.model);
		}
		add(nl);
	}

	/*public static void main(String[] args) {
		

	}*/

}
