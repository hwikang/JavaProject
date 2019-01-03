

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class EatInput extends JPanel implements ActionListener{
	
	double protein = 0;
	double carb = 0;
	String proteinF ="";
	String carbF ="";
	
	JPanel performPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton defaultBtn = new JButton("��Ÿ");
		JButton trueBtn = new JButton("����");
		JButton falseBtn = new JButton("�̼���");
	JPanel ioPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(4,1));
			JPanel chickPanel = new JPanel(new FlowLayout());
				JLabel chickLbl = new JLabel("�߰�����");
				JTextField chickTf = new JTextField(15);
				JLabel gram = new JLabel("g");
			JPanel sweetPanel = new JPanel(new FlowLayout());
				JLabel sweetLbl = new JLabel("����");
				JTextField sweetTf = new JTextField(15);
				JLabel gram2 = new JLabel("g");
			JPanel ricePanel = new JPanel(new FlowLayout());
				JLabel riceLbl = new JLabel("�Ϲݽ�");
				JTextField riceTf = new JTextField(15);
				JLabel ggi = new JLabel("��");
			JPanel powderPanel = new JPanel(new FlowLayout());
				JLabel powderLbl = new JLabel("�ܹ���������");
				JTextField powderTf = new JTextField(15);
				JLabel scoop = new JLabel("����");
			
		JButton calculator = new JButton("���");
		JPanel outputPanel = new JPanel(new GridLayout(2,1));
			JPanel proteinPanel = new JPanel(new FlowLayout());
				JLabel proteinLbl= new JLabel("��  �ܹ��� = ");
				JTextField proteinTf = new JTextField(10);
			JPanel carbPanel = new JPanel(new FlowLayout());
				JLabel carbLbl= new JLabel("��ź��ȭ�� = ");
				JTextField carbTf = new JTextField(10);
				
			
	JButton saveButton = new JButton("����");			
		
		EatPage ep;
		boolean isCalculator = false;
		
	public EatInput(EatPage ep) {
		setLayout(new BorderLayout(0,20));
		this.ep = ep;
		////////////////////////////////////////////////////��ư����
		defaultBtn.setBackground(Color.gray);
		trueBtn.setBackground(Color.green);
		falseBtn.setBackground(Color.magenta);
		
		

		//////////////////////////////////////////////////////////
		
		
		performPanel.add(defaultBtn); performPanel.add(trueBtn); performPanel.add(falseBtn);
		
		ioPanel.add(inputPanel,"North"); 
			inputPanel.add(chickPanel); inputPanel.add(sweetPanel); inputPanel.add(ricePanel);
			inputPanel.add(powderPanel);
				chickPanel.add(chickLbl); chickPanel.add(chickTf); chickPanel.add(gram);
				sweetPanel.add(sweetLbl); sweetPanel.add(sweetTf); sweetPanel.add(gram2);
				ricePanel.add(riceLbl); ricePanel.add(riceTf); ricePanel.add(ggi);
				powderPanel.add(powderLbl); powderPanel.add(powderTf); powderPanel.add(scoop);
		ioPanel.add(calculator,"Center"); 
		ioPanel.add(outputPanel,"South");
			outputPanel.add(proteinPanel); outputPanel.add(carbPanel);
				proteinPanel.add(proteinLbl);proteinPanel.add(proteinTf);
				carbPanel.add(carbLbl);	carbPanel.add(carbTf);	
		
		
		add(performPanel,"North");
		add(ioPanel,"Center");
		add(saveButton,"South");
			
		defaultBtn.addActionListener(this);
		trueBtn.addActionListener(this);
		falseBtn.addActionListener(this);
		calculator.addActionListener(this);
		saveButton.addActionListener(this);
		
		fillInputField();
		
	}
	public void fillInputField() {		//�׳��� ������
		EatDAO dao = new EatDAO();
		
		List<EatVO> list = dao.getEatData(getDate(), ep.menu.mp.idField.getText());
		for(int i =0; i<list.size();i++) {
			EatVO vo = new EatVO();
			vo = list.get(i);
			if(vo.getDoDont().equals("��Ÿ")) {  
				clickedDefaultBtn();
			}else if(vo.getDoDont().equals("����")) { 			
				clickedTrueBtn();
			}else if(vo.getDoDont().equals("�̼���")) {  
				clickedFalseBtn();
			}	
			proteinTf.setText(String.valueOf(vo.getProtein()));
			carbTf.setText(String.valueOf(vo.getCarb()));
			
		}
		
	}
	
	public void getTotal() {						//ź��ȭ�� �ܹ��� ����
		protein=0;
		carb =0;									//�ʱ�ȭ
		if(!chickTf.getText().equals("")) {
			protein += Integer.parseInt(chickTf.getText())*0.23; //�������� 1g�� �ܹ��� 0.23g
		}
		if(!powderTf.getText().equals("")) {
			protein += Integer.parseInt(powderTf.getText())*30;  //1���� 30g
		}
		if(!sweetTf.getText().equals("")) {
			carb += Integer.parseInt(sweetTf.getText())*0.3; //���� 1g�� ź�� 0.3g
			protein += Integer.parseInt(sweetTf.getText())*0.02; //�ܹ��� 0.02g
			
		}
		if(!riceTf.getText().equals("")) {
			carb += Integer.parseInt(riceTf.getText())*50; //1������ ź�� 40g �ܹ���20g
			protein += Integer.parseInt(riceTf.getText())*20;
		}
		DecimalFormat df = new DecimalFormat("###.##");
		proteinF = df.format(protein);  			//�ܹ��� ź�� ����
		carbF = df.format(carb);
	}
	public void actionPerformed(ActionEvent e) {					//ACTIONLISTENER
		Object obj =e.getActionCommand();
		if(obj.equals("���")) {
			getTotal();			
			isCalculator=true;
			proteinTf.setText(proteinF);
			carbTf.setText(carbF);
		}
		if(obj.equals("��Ÿ")) {
			clickedDefaultBtn();
			
		}else if(obj.equals("����")){
			clickedTrueBtn();
			
		}else if(obj.equals("�̼���")){
			clickedFalseBtn();
			
		}
		if(obj.equals("����")) {
			if(defaultBtn.isEnabled()&&trueBtn.isEnabled()&&falseBtn.isEnabled()) {
				JOptionPane.showMessageDialog(this, "���� ��ư�� Ŭ���ϼ���");
				
			}else if(falseBtn.isEnabled()&&!isCalculator) {  //�̼����� �ƴѵ� �����Ҷ�
				JOptionPane.showMessageDialog(this, "��� �� ���� �ϼ���");
			}
				
			else {
				getTotal();					//ź��ȭ�� �ܹ��� ���
				EatVO vo = setVO();			//vo ����
				EatDAO dao = new EatDAO();
				dao.deleteEatData(vo);
				dao.setEatData(vo);
				ep.eatInputDialog.setVisible(false);
			}
		}
	}
	
	public EatVO setVO() {
		EatVO vo = new EatVO();
		vo.setId(ep.menu.mp.idField.getText()); 
		vo.setDate(getDate());
		vo.setDoDont(btnChoice());
		if(btnChoice().equals("�̼���")) {
			vo.setMeal(0);
			vo.setCarb(0);
			vo.setProtein(0);
		}else {
			if(riceTf.getText().equals("")) {
				vo.setMeal(0);
			}else {
				vo.setCarb(Double.parseDouble(carbF));		
				vo.setProtein(Double.parseDouble(proteinF));
				vo.setMeal(Integer.parseInt(riceTf.getText()));
			}
		}
		return vo;
	}
	//////////////////////////////////////////////////////////�Է°�ã�ƿ���
	public String getDate() {				//��¥ �Űܹޱ�
		
		Calendar cal = Calendar.getInstance();
		cal.set(ep.year, ep.month-1, ep.day);		
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM/dd");		
		String date = dFormat.format(cal.getTime());		
		
		return date;
	}
	public String btnChoice() {					//�� ��ư
		String btn ="";
		if(!defaultBtn.isEnabled()) {
			btn = defaultBtn.getText();
			
		}else if(!trueBtn.isEnabled()) {
			btn = trueBtn.getText();
			
		}else if(!falseBtn.isEnabled()) {
			btn = falseBtn.getText();
			
		}
		
		return btn;
		
	}
	/////////////////////////////////////////////////////////////////
	public void clickedDefaultBtn() {	//��ư������ �ٸ���ư Ȱ��ȭ
		
		defaultBtn.setEnabled(false);
		trueBtn.setEnabled(true);
		falseBtn.setEnabled(true);
		defaultBtn.setBorderPainted(true);
		trueBtn.setBorderPainted(false);
		falseBtn.setBorderPainted(false);	
		enableAll();
		
	}
	public void clickedTrueBtn() {		
		defaultBtn.setEnabled(true);
		trueBtn.setEnabled(false);
		falseBtn.setEnabled(true);
		defaultBtn.setBorderPainted(false);
		trueBtn.setBorderPainted(true);
		falseBtn.setBorderPainted(false);	
		enableAll();
	}
	public void clickedFalseBtn() {
		defaultBtn.setEnabled(true);
		trueBtn.setEnabled(true);
		falseBtn.setEnabled(false);
		defaultBtn.setBorderPainted(false);
		trueBtn.setBorderPainted(false);
		falseBtn.setBorderPainted(true);	
		disEnableAll();
	}
	//�̼��� ��ư��Ŭ���ɶ� ��� �Է�â ��Ȱ��ȭ 
		public void disEnableAll() {				
			chickTf.setEnabled(false);
			sweetTf.setEnabled(false);
			powderTf.setEnabled(false);
			riceTf.setEnabled(false);
			calculator.setEnabled(false);
			
		}
		public void enableAll() {
			chickTf.setEnabled(true);
			sweetTf.setEnabled(true);
			powderTf.setEnabled(true);
			riceTf.setEnabled(true);
			calculator.setEnabled(true);
			
		}

	/*public static void main(String[] args) {
		
		new EatInput();
	}*/

}
