

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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

public class EatStats extends JPanel implements ActionListener{
	Calendar cal = Calendar.getInstance();
	int year;
	int month;
	
	int doTrue=0; // ����
	int doFalse = 0; //�̼���
	String doPercent="0";
	int carbTotal = 0;
	int proteinTotal = 0;
	int proteinDay=1;
	int carbDay =1;
	
	int doTrueL=0; // ����
	int doFalseL = 0; //�̼���
	String doPercentL="0";
	int carbTotalL = 0;
	int proteinTotalL = 0;
	int proteinDayL=1;
	int carbDayL =1;
	
	
	
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
			JLabel yearLbl = new JLabel("");
			/*JLabel yl = new JLabel("��");
			JLabel monthLbl = new JLabel(String.valueOf(month));
			JLabel ml = new JLabel("�� � ���");*/
			
		JPanel statsPanel = new JPanel(new GridLayout(5, 1));
			JPanel doPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel doTitle = new JLabel("����");
			JLabel doRep = new JLabel("");
			JLabel doCheck = new JLabel(""); //��ȭ�� ǥ��
			
			JPanel dontPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel dontTitle = new JLabel("�̼���");
			JLabel dontRep = new JLabel("");
			JLabel dontCheck = new JLabel(""); //��ȭ�� ǥ��
			
			JPanel percentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel percentTitle = new JLabel("�����");
			JLabel percentRep = new JLabel("");
			JLabel percentCheck = new JLabel(""); //��ȭ�� ǥ��
			
			JPanel proteinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel proteinTitle = new JLabel("�ܹ��� ���");
			JLabel proteinRep = new JLabel("");
			JLabel proteinCheck = new JLabel(""); //��ȭ�� ǥ��
			
			JPanel carbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel carbTitle = new JLabel("�ܹ��� ���");
			JLabel carbRep = new JLabel("");
			JLabel carbCheck = new JLabel(""); //��ȭ�� ǥ��
		
		
		MenuPage menu;
	public EatStats(MenuPage menu) {
		this.menu = menu;
		setLayout(new BorderLayout());
		
		yearCombo.setSelectedItem(cal.get(Calendar.YEAR));
		monthCombo.setSelectedItem(cal.get(Calendar.MONTH)+1); //���� ������ ����
		writeAll();
		
/*		setSize(400,600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);*/
		
		
		leftBtn.addActionListener(this);
		rightBtn.addActionListener(this);
		yearCombo.addActionListener(this);
		monthCombo.addActionListener(this);		
	}
	public void writeAll() {

		setDate();			//�޺��ڽ� ���� ��¥���� ������
		getEatData();		//�ش� ��¥�� � ������ ������
		setEatText();  //�����Ϳ� ���� ��¥, ������� �ؽ�Ʈ ��ȭ
		///////////////////////////////////////// top
		btnPanel.setBackground(Color.CYAN);
		btnPanel.add(leftBtn);
		btnPanel.add(yearCombo);btnPanel.add(yLbl);
		btnPanel.add(monthCombo);btnPanel.add(mLbl);
		btnPanel.add(rightBtn);
		
		titlePanel.add(yearLbl); /*titlePanel.add(yl);
		titlePanel.add(monthLbl); titlePanel.add(ml);*/
		Font titleFont = new Font("HY�ü� ����",Font.BOLD,20);
		yearLbl.setFont(titleFont);
		
		
		topPanel.add(btnPanel); topPanel.add(titlePanel,"Center");
		add(topPanel,"North");
		////////////////////////////////////////// center
		statsPanel.add(doPanel);
			doPanel.add(doTitle); doPanel.add(doRep); doPanel.add(doCheck);
		statsPanel.add(dontPanel);
			dontPanel.add(dontTitle); dontPanel.add(dontRep); dontPanel.add(dontCheck);
		statsPanel.add(percentPanel);
			percentPanel.add(percentTitle); percentPanel.add(percentRep); percentPanel.add(percentCheck);
		statsPanel.add(proteinPanel);
			proteinPanel.add(proteinTitle); proteinPanel.add(proteinRep); proteinPanel.add(proteinCheck);
		statsPanel.add(carbPanel);
			carbPanel.add(carbTitle); carbPanel.add(carbRep); carbPanel.add(carbCheck);
		
		add(statsPanel,"Center");
	}
	public void setDate() {                                  //��¥�� �޺��ڽ� ���� ����
		year = (int) yearCombo.getSelectedItem();
		month = (int) monthCombo.getSelectedItem();
		cal.set(year,month-1 , 1);   //�޺��� ������ ��¥�� ����  0�����ͽ����� ex> �ΰ�1���� ��ǻ��0��
		//System.out.println(cal.get(Calendar.MONTH));
		
	}

	public void getEatData() {
		
		EatDAO dao = new EatDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//��,��
		String date = dFormat.format(cal.getTime());		//���糯¥�� �� ��		
		String eatDate = date+"%";	
		
		List<EatVO> list = dao.getEatData(eatDate,getLoginId());
		
		for(int i=0; i<list.size();i++) {
			EatVO vo =list.get(i);	
			
			if(vo.getDoDont().equals("��Ÿ")) {
				
			}
			else if(vo.getDoDont().equals("����")) {
				doTrue+=1;
			}else if(vo.getDoDont().equals("�̼���")) {
				doFalse+=1;
			}
			
			if(vo.getCarb()>0) {
				carbTotal += vo.getCarb();
				carbDay +=1;
			}
			if(vo.getProtein()>0) {
				proteinTotal += vo.getProtein();
				proteinDay +=1;
			}
			
		}
		
		double dayLength = cal.getActualMaximum(Calendar.DAY_OF_MONTH);		
		DecimalFormat df = new DecimalFormat("##.##");	
		double per = doTrue/dayLength ;	//
		doPercent = df.format(per);		//����� =����/��������
	
		
	}
	
	public void compareLastData() {
		cal.set(year,month-2 , 1);  //������
		EatDAO dao = new EatDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//��,��
		String date = dFormat.format(cal.getTime());		//���糯¥�� �� ��		
		String eatDate = date+"%";	
		
		List<EatVO> list = dao.getEatData(eatDate,getLoginId());
		
		for(int i=0; i<list.size();i++) {
			EatVO vo =list.get(i);	
			
			if(vo.getDoDont().equals("��Ÿ")) {
				
			}
			else if(vo.getDoDont().equals("����")) {
				doTrueL+=1;
			}else if(vo.getDoDont().equals("�̼���")) {
				doFalseL+=1;
			}
			
			if(vo.getCarb()>0) {
				carbTotalL += vo.getCarb();
				carbDayL +=1;
			}
			if(vo.getProtein()>0) {
				proteinTotalL += vo.getProtein();
				proteinDayL +=1;
			}
			
		}
		
		double dayLength = cal.getActualMaximum(Calendar.DAY_OF_MONTH);		
		DecimalFormat df = new DecimalFormat("##.##");	
		double per = doTrueL/dayLength ;	//
		doPercentL = df.format(per);		//����� =����/��������
	}
	
	public void setEatText() {
		compareLastData(); //������ ������ �ҷ�����
		yearLbl.setText(String.valueOf(year) +" �� "+String.valueOf(month) +" �� �Ĵ� ���");
		//monthLbl.setText(String.valueOf(month));
		
		doRep.setText(String.valueOf(doTrue) +" ȸ ");
		dontRep.setText(String.valueOf(doFalse) + " ȸ ");
		percentRep.setText(doPercent +" % ");
		
		proteinRep.setText(String.valueOf(proteinTotal/proteinDay) +" g ����");
		carbRep.setText(String.valueOf(carbTotal/carbDay) +" g ����");
		
		String upDown =""; //���� or ����
		int doTrueNum = doTrue-doTrueL; //���� �񱳼�ġ
		if(doTrueNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			doCheck.setForeground(Color.BLUE);
		}else {
			doCheck.setForeground(Color.RED);
		}
		doCheck.setText("���� ��� " +doTrueNum+" ȸ "+upDown);
		
		int doFalseNum = doFalse-doFalseL; //���� �񱳼�ġ
		if(doFalseNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			dontCheck.setForeground(Color.RED);
		}else {
			dontCheck.setForeground(Color.BLUE);
		}
		dontCheck.setText("���� ��� " +doFalseNum+ " ȸ "+upDown);
		
		Double percentNum = Double.parseDouble(doPercent)-Double.parseDouble(doPercentL); //���� �񱳼�ġ
		if(percentNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			percentCheck.setForeground(Color.blue);
		}else {
			percentCheck.setForeground(Color.red);
		}
		percentCheck.setText("���� ��� " +percentNum+" % "+upDown);
		
		int proteinNum = proteinTotal/proteinDay - proteinTotalL/proteinDayL ;
		if(proteinNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			proteinCheck.setForeground(Color.blue);
		}else {
			proteinCheck.setForeground(Color.red);
		}
		proteinCheck.setText("���� ��� " +proteinNum+" g "+upDown);
		
		int carbNum = carbTotal/carbDay - carbTotalL/carbDayL ;
		if(carbNum>=0) {
			upDown = "����";
		}else {
			upDown ="����";
		}
		if(upDown.equals("����")) {
			carbCheck.setForeground(Color.blue);
		}else {
			carbCheck.setForeground(Color.red);
		}
		carbCheck.setText("���� ��� " +carbNum+" g "+upDown);

	}	
	public void clear() {
		titlePanel.setVisible(false);
		statsPanel.setVisible(false);
		titlePanel.removeAll();
		titlePanel.removeAll();
	
		doTrue=0; // ����
		doFalse = 0; //�̼���
		doPercent="0";
		carbTotal = 0;
		proteinTotal = 0;
		proteinDay=1;
		carbDay =1;
		doTrueL=0; // ����
		doFalseL = 0; //�̼���
		doPercentL="0";
		carbTotalL = 0;
		proteinTotalL = 0;
		proteinDayL=1;
		carbDayL =1;
		
		
		
		
		statsPanel.setVisible(true);
		titlePanel.setVisible(true);
		
		
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
		
	

}
