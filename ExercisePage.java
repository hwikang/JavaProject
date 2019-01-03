

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ExercisePage extends JFrame implements ActionListener{

	Calendar cal = Calendar.getInstance();
	
	JPanel btnPanel = new JPanel();//��ư�г�
		JButton backBtn = new JButton("�ڷΰ���");
		JButton leftBtn = new JButton("��");
		JButton rightBtn = new JButton("��");
		
		
			Integer years[] = {2017,2018,2019};
			DefaultComboBoxModel<Integer> yearComboModel = new DefaultComboBoxModel<Integer>(years);
		JComboBox<Integer> yearCombo = new JComboBox<Integer>(yearComboModel);
			Integer months[] = {1,2,3,4,5,6,7,8,9,10,11,12};
			DefaultComboBoxModel<Integer> monthComboModel = new DefaultComboBoxModel<Integer>(months);
		JComboBox<Integer> monthCombo = new JComboBox<Integer>(monthComboModel);

		
		JLabel yearLbl = new JLabel("��");
		JLabel monthLbl = new JLabel("��");
		
	
		
	JPanel dayTitlePanel = new JPanel(new BorderLayout());//�޷��г�
		JPanel titlePanel = new JPanel(new GridLayout(1, 7));	//�����г�
		JPanel dayPanel = new JPanel(new GridLayout(0, 7));	//��¥�г�
		
	int year;
	int month;
	int day;
	int dayLength ;
	MenuPage menu;
	
	JDialog exInputDialog;
	public ExercisePage(MenuPage menu) {
		super("����");
		this.menu = menu;
		
		
		
		//System.out.println(cal.get(Calendar.MONTH));
		btnPanel.add(backBtn);
		
		btnPanel.add(leftBtn);
		btnPanel.add(yearCombo);btnPanel.add(yearLbl);
		btnPanel.add(monthCombo);btnPanel.add(monthLbl);
		btnPanel.add(rightBtn);
		btnPanel.setBackground(Color.CYAN);
		add(btnPanel,"North");
		add(dayTitlePanel,"Center");
		
		yearCombo.setSelectedItem(cal.get(Calendar.YEAR));
		monthCombo.setSelectedItem(cal.get(Calendar.MONTH)+1); //���� ������ ����
		
		dayBtn(); //��¥�� �����ǰ� ��ư�̻���
		
		backBtn.addActionListener(this);
		leftBtn.addActionListener(this);
		rightBtn.addActionListener(this);
		yearCombo.addActionListener(this);
		monthCombo.addActionListener(this);		
		

		
		setSize(700,700);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
		
	}
	
	public void setDate() {                                  //��¥�� �޺��ڽ� ���� ����
		year = (int) yearCombo.getSelectedItem();
		month = (int) monthCombo.getSelectedItem();
		cal.set(year,month-1 , 1);   //�޺��� ������ ��¥�� ����  0�����ͽ����� ex> �ΰ�1���� ��ǻ��0��
		//System.out.println(cal.get(Calendar.MONTH));
		
	}
		
	
	public void dayBtn() {  //���� +��¥��ư ����
		String title[] = {"��","��","ȭ","��","��","��","��"};
		for(int i =0; i<title.length;i++) {
			JLabel titleLbl = new JLabel(title[i],JLabel.CENTER);
			titleLbl.setBackground(Color.ORANGE);
			titleLbl.setOpaque(true);
			if(i==0) {
				titleLbl.setForeground(Color.red);
			}else if(i==6) {
				titleLbl.setForeground(Color.blue);
			}
			titlePanel.add(titleLbl);
		}
		dayTitlePanel.add(titlePanel,"North");
		
		setDate();  //1�Ϸ� ����
		
		int firstDay = cal.get(Calendar.DAY_OF_WEEK); //1���� ���۵Ǵ� ����, index 1���ͽ�����
		for(int i =1 ; i<firstDay; i++) {				//�����̽�
			JLabel lbl = new JLabel("");
			dayPanel.add(lbl);
		}
		
		dayLength =  cal.getActualMaximum(Calendar.DAY_OF_MONTH); //��������
		for(int i =1; i<=dayLength; i++) {
			
			JButton day = new JButton(String.valueOf(i));//��¥��ư����
			
			
			day.setBackground(changeBtnColor(i));	//������ ���� �������ֱ�
			day.addActionListener(this);			//��¥ ��ư Ŭ��
			dayPanel.add(day);
			
		}
		dayTitlePanel.add(dayPanel,"Center");
	}
	
	public Color changeBtnColor(int btnNum) {
		Color color = Color.GRAY;
		
		ExerciseDAO dao = new ExerciseDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//��,��
		String date = dFormat.format(cal.getTime());		//���糯¥�� �� ��		
		String exerDate = date+"%";	
		//System.out.println("exerdate="+exerDate);
		
		List<ExerciseVO> list = dao.getExerciseData(exerDate,getLoginId()); // 18/12% ���·� �����͵�ҷ��� 
		for(int i=0; i<list.size();i++) {
			ExerciseVO vo =list.get(i);								//�ش� ���� �ش��ϴ� ���ڵ� 1�پ�
			String day = vo.getDate().substring(8, 10); //��¥���������� "08"
			if(Integer.parseInt(day)==btnNum) {			//�����Ѵ�.
				if(vo.getDoDont().equals("����")) {
					color = Color.GREEN;
				}else if(vo.getDoDont().equals("�̼���")) {
					color = Color.MAGENTA;
				}else if(vo.getDoDont().equals("��Ÿ")) {
					color = Color.BLACK;
				}
			}
			
			
			
			
		}
		
		return color;
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {				//ACTIONLISTENER
		Object obj = e.getActionCommand();
		if(obj.equals("��")) {
			backMonth();
		}else if(obj.equals("��")) {
			goMonth();
		}else if(obj.equals("�ڷΰ���")) {
			setVisible(false);
			
		}
		for(int i =0; i<=dayLength; i++) {  			//��¥��ư Ŭ����
			if(obj.equals(String.valueOf(i))) {				
				day = i;							//��¥ ����
													//���̾�α׷� ���ǲâ ����
				exInputDialog = new JDialog(this,"����", true);
				ExerciseInput ei = new ExerciseInput(this);
				exInputDialog.add(ei);				
				exInputDialog.setSize(400,500);
				exInputDialog.setVisible(true);
				exInputDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
				clear();
				dayBtn();  //��ư �ٽû���
			}
			
		} 
			
		Object obj2= e.getSource();
		if(obj2 instanceof JComboBox) {
			clear();
			dayBtn();			
		}
	}
	
	public void backMonth() {
		if(month==1) {
			if(year==2017) {
				JOptionPane.showMessageDialog(this, "���̻� ���ŷ� ���ư�������");
			}else {
			yearCombo.setSelectedItem(year-1);			
			monthCombo.setSelectedItem(12);
			}
		}else {
			monthCombo.setSelectedItem(month-1);
		}
		clear();
		dayBtn();
		
	}
	public void goMonth() {
		if(month==12) {
			if(year==2019) {
				JOptionPane.showMessageDialog(this, "�ʹ� �ռ���������");
			}else {
			yearCombo.setSelectedItem(year+1);			
			monthCombo.setSelectedItem(1);
			}
		}else {
			monthCombo.setSelectedItem(month+1);
		}
		
		clear();
		dayBtn();
	}
	public void clear() {
		dayTitlePanel.setVisible(false);
		dayPanel.removeAll();
		titlePanel.removeAll();
		dayTitlePanel.setVisible(true);
	}
	public String getLoginId() {
		String id= menu.mp.idField.getText();
		//System.out.println("expage���� �θ� id��="+id);
		return id;
	}
	
	

}
