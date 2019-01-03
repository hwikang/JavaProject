

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ExerStats extends JPanel implements ActionListener{
	
	int shoulder =0;
	int chest  =0;
	int back = 0;
	int leg = 0;
	int arm = 0;
	int cardio =0;
	int perform = 0;  //����Ƚ��
	int cardioTotal = 0;
	
	int shoulderL =0;		//������ ��ġ
	int chestL =0;
	int backL = 0;
	int legL = 0;
	int armL = 0;
	int cardioL =0;
	int performL = 0;  
	int cardioTotalL = 0;
	
	int year;
	int month;
	
	Calendar cal = Calendar.getInstance();
	

	
	JPanel topPanel = new JPanel(new GridLayout(2, 1));
	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//��ư�г�
	
		JButton leftBtn = new JButton("��");
		JButton rightBtn = new JButton("��");
		
		
			Integer years[] = {2017,2018,2019};
			DefaultComboBoxModel<Integer> yearComboModel = new DefaultComboBoxModel<Integer>(years);
		JComboBox<Integer> yearCombo = new JComboBox<Integer>(yearComboModel);
			Integer months[] = {1,2,3,4,5,6,7,8,9,10,11,12};
			DefaultComboBoxModel<Integer> monthComboModel = new DefaultComboBoxModel<Integer>(months);
		JComboBox<Integer> monthCombo = new JComboBox<Integer>(monthComboModel);
	
		
		JLabel yLbl = new JLabel("��");
		JLabel mLbl = new JLabel("��");
	
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel yearLbl = new JLabel();
		
		
	
	JPanel statsPanel = new JPanel(new GridLayout(7, 1));
		JPanel shoulderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel shoulderTitle = new JLabel("���");
			JLabel shoulderRep = new JLabel(String.valueOf(shoulder));
			JLabel shoulderTime = new JLabel("ȸ");
			JLabel shoulderCheck = new JLabel(""); //��ȭ�� ǥ��
			
		JPanel chestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel chestTitle = new JLabel("����");
			JLabel chestRep = new JLabel(String.valueOf(chest));
			JLabel chestTime = new JLabel("ȸ");
			JLabel chestCheck = new JLabel(""); //��ȭ�� ǥ��
		JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel backTitle = new JLabel("��  ");
			JLabel backRep = new JLabel(String.valueOf(back));
			JLabel backTime = new JLabel("ȸ");
			JLabel backCheck = new JLabel(""); //��ȭ�� ǥ��
		JPanel legPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel legTitle = new JLabel("�ٸ�");
			JLabel legRep = new JLabel(String.valueOf(leg));
			JLabel legTime = new JLabel("ȸ");
			JLabel legCheck = new JLabel(""); //��ȭ�� ǥ��
		JPanel armPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel armTitle = new JLabel("��");
			JLabel armRep = new JLabel(String.valueOf(arm));
			JLabel armTime = new JLabel("ȸ");
			JLabel armCheck = new JLabel(""); //��ȭ�� ǥ��
		JPanel cardioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel cardioTitle = new JLabel("�����");
			JLabel cardioRep = new JLabel(String.valueOf(cardio));
			JLabel cardioTime = new JLabel("ȸ");
			JLabel cardioCheck = new JLabel(""); //��ȭ�� ǥ��
			JLabel cardioMinute = new JLabel(String.valueOf(cardioTotal));
			JLabel cardioM = new JLabel("��"); //����� �ѷ� ǥ��
			
		JPanel doPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel doTitle = new JLabel("�� � Ƚ��");
			JLabel doRep = new JLabel(String.valueOf(perform));
			JLabel doTime = new JLabel("ȸ");
			JLabel doCheck = new JLabel(""); //��ȭ�� ǥ��
			MenuPage menu;
	public ExerStats(MenuPage menu) {
		setLayout(new BorderLayout());
		this.menu = menu;
		yearCombo.setSelectedItem(cal.get(Calendar.YEAR));
		monthCombo.setSelectedItem(cal.get(Calendar.MONTH)+1); //���� ������ ����
		writeAll();
		
		
		////////////////////////////////////////////
		/*setSize(400,600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		*/
		
		leftBtn.addActionListener(this);
		rightBtn.addActionListener(this);
		yearCombo.addActionListener(this);
		monthCombo.addActionListener(this);		
		
	}
	
	public void writeAll() {

		setDate();			//�޺��ڽ� ���� ��¥���� ������
		getExerData();		//�ش� ��¥�� � ������ ������
		setExerText();  //�����Ϳ� ���� ��¥, ������� �ؽ�Ʈ ��ȭ
		///////////////////////////////////////////top
		btnPanel.setBackground(Color.CYAN);
		btnPanel.add(leftBtn);
		btnPanel.add(yearCombo);btnPanel.add(yLbl);
		btnPanel.add(monthCombo);btnPanel.add(mLbl);
		btnPanel.add(rightBtn);
		
		titlePanel.add(yearLbl);
		Font titleFont = new Font("HY�ü� ����",Font.BOLD,20);
		yearLbl.setFont(titleFont);
		topPanel.add(btnPanel); topPanel.add(titlePanel,"Center");
		add(topPanel,"North");
		////////////////////////////////////////center
		
		
		statsPanel.add(shoulderPanel);
		shoulderPanel.add(shoulderTitle); shoulderPanel.add(shoulderRep);
		shoulderPanel.add(shoulderTime); shoulderPanel.add(shoulderCheck);
		statsPanel.add(chestPanel);
		chestPanel.add(chestTitle); chestPanel.add(chestRep);
		chestPanel.add(chestTime); chestPanel.add(chestCheck);
		statsPanel.add(backPanel);
		
		
		backPanel.add(backTitle); backPanel.add(backRep);
		backPanel.add(backTime); backPanel.add(backCheck);
		statsPanel.add(legPanel);
		legPanel.add(legTitle); legPanel.add(legRep);
		legPanel.add(legTime); legPanel.add(legCheck);
		statsPanel.add(armPanel);
		armPanel.add(armTitle); armPanel.add(armRep);
		armPanel.add(armTime); armPanel.add(armCheck);
		statsPanel.add(cardioPanel);
		cardioPanel.add(cardioTitle); cardioPanel.add(cardioRep);
		cardioPanel.add(cardioTime); cardioPanel.add(cardioCheck);	
		cardioPanel.add(cardioMinute); cardioPanel.add(cardioM);
		statsPanel.add(doPanel);
		doPanel.add(doTitle); doPanel.add(doRep);
		doPanel.add(doTime); doPanel.add(doCheck);
		
		add(statsPanel,"Center");
	}
	public void setExerText() {
		compareLastData();  //������ ������
		
		yearLbl.setText(String.valueOf(year)+" �� "+String.valueOf(month)+" �� � ���");
		
		
		shoulderRep.setText(String.valueOf(shoulder));
		chestRep.setText(String.valueOf(chest));
		backRep.setText(String.valueOf(back));
		legRep.setText(String.valueOf(leg));
		armRep.setText(String.valueOf(arm));
		cardioRep.setText(String.valueOf(cardio));
		cardioMinute.setText("�� " + String.valueOf(cardioTotal));
		doRep.setText(String.valueOf(perform));

		String upDown =""; //���� or ����
		int shoulderNum = shoulder-shoulderL; //���� �񱳼�ġ
		if(shoulderNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			shoulderCheck.setForeground(Color.BLUE);
		}else {
			shoulderCheck.setForeground(Color.RED);
		}
		shoulderCheck.setText("���� ��� " +shoulderNum+"ȸ"+upDown);
		
		int chestNum = chest-chestL; //���� �񱳼�ġ
		if(chestNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			chestCheck.setForeground(Color.BLUE);
		}else {
			chestCheck.setForeground(Color.RED);
		}
		chestCheck.setText("���� ��� " +chestNum+"ȸ"+upDown);
		
		int backNum = back-backL; //���� �񱳼�ġ
		if(backNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			backCheck.setForeground(Color.BLUE);
		}else {
			backCheck.setForeground(Color.RED);
		}
		backCheck.setText("���� ��� " +backNum+"ȸ"+upDown);
		
		int legNum = leg-legL; //���� �񱳼�ġ
		if(legNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			legCheck.setForeground(Color.BLUE);
		}else {
			legCheck.setForeground(Color.RED);
		}
		legCheck.setText("���� ��� " +legNum+"ȸ"+upDown);
		
		int armNum = arm-armL; //���� �񱳼�ġ
		if(armNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			armCheck.setForeground(Color.BLUE);
		}else {
			armCheck.setForeground(Color.RED);
		}
		armCheck.setText("���� ��� " +armNum+"ȸ"+upDown);
		
		int cardioNum = cardio-cardioL; //���� �񱳼�ġ
		if(cardioNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			cardioCheck.setForeground(Color.BLUE);
		}else {
			cardioCheck.setForeground(Color.RED);
		}
		cardioCheck.setText("���� ��� " +cardioNum+"ȸ"+upDown);
		
		int performNum = perform-performL; //���� �񱳼�ġ
		if(performNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			doCheck.setForeground(Color.BLUE);
		}else {
			doCheck.setForeground(Color.RED);
		}
		doCheck.setText("���� ��� " +performNum+"ȸ"+upDown);
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
		writeAll() ;
		
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
		writeAll() ;
	}
	
	public void setDate() {                                  //��¥�� �޺��ڽ� ���� ����
		year = (int) yearCombo.getSelectedItem();
		month = (int) monthCombo.getSelectedItem();
		cal.set(year,month-1 , 1);   //�޺��� ������ ��¥�� ����  0�����ͽ����� ex> �ΰ�1���� ��ǻ��0��
		//System.out.println(cal.get(Calendar.MONTH));
		
	}
	

/*	public static void main(String[] args) {
		new ExerStats();

	}*/
	
	public void clear() {
		titlePanel.setVisible(false);
		statsPanel.setVisible(false);
		titlePanel.removeAll();
		titlePanel.removeAll();
		shoulder =0;
		chest  =0;
		back = 0;
		leg = 0;
		arm = 0;
		cardio =0;
		perform = 0;  //����Ƚ��
		cardioTotal = 0;
		shoulderL =0;		//������ ��ġ
		chestL =0;
		backL = 0;
		legL = 0;
		armL = 0;
		cardioL =0;
		performL = 0;  
		cardioTotalL = 0;
		statsPanel.setVisible(true);
		titlePanel.setVisible(true);
		
		
	}


	public void getExerData() {
		
		ExerciseDAO dao = new ExerciseDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//��,��
		String date = dFormat.format(cal.getTime());		//���糯¥�� �� ��		
		String exerDate = date+"%";	
		
		List<ExerciseVO> list = dao.getExerciseData(exerDate,getLoginId());
		
		for(int i=0; i<list.size();i++) {
			ExerciseVO vo =list.get(i);	
			
			if(vo.getExerPart().equals("����")||vo.getExerPart().equals(null)) {
				
			}
			else if(vo.getExerPart().equals("���")) {
				shoulder+=1;
			}else if(vo.getExerPart().equals("����")) {
				chest+=1;
			}else if(vo.getExerPart().equals("��")) {
				back+=1;
			}else if(vo.getExerPart().equals("�ٸ�")) {
				leg+=1;
			}else if(vo.getExerPart().equals("��")) {
				arm+=1;
			}
			if(vo.getCardio()>0) {
				cardioTotal += vo.getCardio();
				cardio +=1;
			}
			if(vo.getDoDont().equals("����")) {
				perform +=1;
			}
			
			
		}
		
		//System.out.println(back);
		
	}
	public void compareLastData() {
		cal.set(year,month-2 , 1);  //������  0 1 2 3 4 5 6 7 8 9 10 11
		ExerciseDAO dao = new ExerciseDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//��,��
		String date = dFormat.format(cal.getTime());		//���糯¥�� �� ��		
		String exerDate = date+"%";	
		//System.out.println(exerDate);
		List<ExerciseVO> list = dao.getExerciseData(exerDate,getLoginId());		
		for(int i=0; i<list.size();i++) {
			ExerciseVO vo =list.get(i);			
			if(vo.getExerPart().equals("����")||vo.getExerPart().equals(null)) {
			}
			else if(vo.getExerPart().equals("���")) {
				shoulderL+=1;
			}else if(vo.getExerPart().equals("����")) {
				chestL+=1;
			}else if(vo.getExerPart().equals("��")) {
				backL+=1;
			}else if(vo.getExerPart().equals("�ٸ�")) {
				legL+=1;
			}else if(vo.getExerPart().equals("��")) {
				armL+=1;
			}
			if(vo.getCardio()>0) {
				cardioTotalL += vo.getCardio();
				cardioL +=1;
			}
			if(vo.getDoDont().equals("����")) {
				performL +=1;
			}
			
			
		}
	}
	
	
	
	
	
	public String getLoginId() {
		String id= menu.mp.idField.getText();
		
		return id;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getActionCommand();
		if(obj.equals("��")) {
			backMonth();
		}else if(obj.equals("��")) {
			goMonth();
		}else if(obj.equals("�ڷΰ���")) {
			setVisible(false);
			
		}
		Object obj2= e.getSource();
		if(obj2 instanceof JComboBox) {
			clear();
			writeAll();	
		}
		
	}

}
