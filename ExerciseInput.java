

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ExerciseInput extends JPanel implements ActionListener{

	
	
	JPanel exercisePanel = new JPanel(new BorderLayout());
		JLabel dateLabel =new JLabel("dddddddddddddd");
		JPanel labelPanel = new JPanel(new GridLayout(3, 1));
			JLabel trueFalseLbl = new JLabel("    �����");
			JLabel weightLbl = new JLabel("    ����Ʈ");
			JLabel cardioLbl = new JLabel("    �����");
		JPanel inputPanel = new JPanel(new GridLayout(3, 1));
			JPanel btnPanel = new JPanel(new GridLayout(1,3));
				JButton defaultBtn = new JButton("��Ÿ");
				JButton trueBtn = new JButton("����");
				JButton falseBtn = new JButton("�̼���");
			JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
				String weights[] = {"���","��","����","�ٸ�","��","����"};
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(weights);
				JComboBox<String> weightCombo = new JComboBox<String>();
			JPanel cardioPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));				
				JTextField cardioTf = new JTextField(10);
				JLabel bun = new JLabel("��");
	
		JPanel descPanel = new JPanel(new BorderLayout(30,0));
			JLabel descLbl = new JLabel("    ����");
			JTextArea ta = new JTextArea("");	
			JScrollPane desc = new JScrollPane(ta);
			JLabel spaceLbl = new JLabel("    ");
			
	
		
		JPanel savePanel = new JPanel(new BorderLayout());
			JButton saveBtn = new JButton("����");
	
			
		
			ExercisePage ep;
			
	public ExerciseInput(ExercisePage ep) {
		/*super("� ��� �Է�");*/
		this.ep = ep;
		setLayout(new BorderLayout(0,30));  //����
		 
		
		
		
		
		////////////////////////////////////////////////////��ư����
			defaultBtn.setBackground(Color.gray);
			trueBtn.setBackground(Color.green);
			falseBtn.setBackground(Color.magenta);
			

			defaultBtn.setBorderPainted(false);
			trueBtn.setBorderPainted(false);
			falseBtn.setBorderPainted(false);			
			
			defaultBtn.setBorder(new LineBorder(Color.black,3));
			trueBtn.setBorder(new LineBorder(Color.black,3));
			falseBtn.setBorder(new LineBorder(Color.black,3));
		//////////////////////////////////////////////////////////input�г�	
			btnPanel.add(defaultBtn);
			btnPanel.add(trueBtn);
			btnPanel.add(falseBtn);
			
			weightCombo.setModel(model);
			weightCombo.setSize(200, 100);
			weightPanel.add(weightCombo);
			
			cardioTf.setHorizontalAlignment(JTextField.RIGHT);
			cardioPanel.add(cardioTf);
			cardioPanel.add(bun);
			
			inputPanel.add(btnPanel);
			inputPanel.add(weightPanel);
			inputPanel.add(cardioPanel);			
	////////////////////////////////////////////////////////////EXERCISE�г�		
			labelPanel.add(trueFalseLbl);
			labelPanel.add(weightLbl);
			labelPanel.add(cardioLbl);
		
			
			dateLabel.setText(getDate());
			dateLabel.setHorizontalAlignment(JTextField.CENTER);
			
		exercisePanel.add(dateLabel	,"North");
		exercisePanel.add(labelPanel,"West");
		exercisePanel.add(inputPanel,"Center");	
		
		add(exercisePanel,"North");		
	/////////////////////////////////////////////////////////	
		descPanel.add(descLbl,"West");
		descPanel.add(desc,"Center");
		descPanel.add(spaceLbl,"East");
				
		add(descPanel,"Center");		
	
		savePanel.add(saveBtn);
		add(savePanel,"South");
		
			
		
		
		defaultBtn.addActionListener(this);
		trueBtn.addActionListener(this);
		falseBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		
		fillInputField();  //������ ä������ ä���
		
	}
	
	
	public String getDate() {				//��¥ �Űܹޱ�
		
		Calendar cal = Calendar.getInstance();
		cal.set(ep.year, ep.month-1, ep.day);		
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM/dd");		
		String date = dFormat.format(cal.getTime());		
		
		return date;
	}
	
	public void fillInputField() {			//�̹� ä�����ִ°� �ҷ�����
		ExerciseDAO dao = new ExerciseDAO();
		
		List<ExerciseVO> al =  dao.getExerciseData(getDate(),getLoginId());  //��¥�� ���ڵ�ҷ���
		ExerciseVO vo = new ExerciseVO();
		
		for(int i = 0 ; i<al.size();i++) {   //al ��������, ���µ����Ͱ� ��������			
			vo = al.get(i); //1���� ���ڵ尡 vo�� ����			
			//��ư���ºҷ��ͼ� ��ư���� ���� 
			if(vo.getDoDont().equals("��Ÿ")) {  
				clickedDefaultBtn();
			}else if(vo.getDoDont().equals("����")) { 			
				clickedTrueBtn();
			}else if(vo.getDoDont().equals("�̼���")) {  
				clickedFalseBtn();
			}			
			//����� �ҷ�����
			weightCombo.setSelectedItem(vo.getExerPart());
			//����� �ҷ�����
			cardioTf.setText(String.valueOf(vo.getCardio()));
			//���� �ҷ�����
			ta.setText(vo.getExerdesc());
			
				
		}
		
		//setVisible(true);
	}
	
	public void clickedDefaultBtn() {
		
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

	@Override
	public void actionPerformed(ActionEvent e) {			//ACTION LISTENER
		Object obj = e.getActionCommand();
		if(obj.equals("��Ÿ")) {
			clickedDefaultBtn();
			
		}else if(obj.equals("����")){
			clickedTrueBtn();
			
		}else if(obj.equals("�̼���")){
			clickedFalseBtn();
			
		}		
		
		if(obj.equals("����")) {       //������ �����ư
			if(defaultBtn.isEnabled()&&trueBtn.isEnabled()&&falseBtn.isEnabled()) {
				JOptionPane.showMessageDialog(this, "���� ��ư�� Ŭ���ϼ���");
				
			}else {
				int result = 0;
				ExerciseVO vo =setExerVO();  //�Է°� vo�� �ֱ�
				ExerciseDAO dao = new ExerciseDAO();
				dao.deleteExerciseData(vo);	//�����Ұ�� ���� ������ �����  
				result =dao.setExerciseData(vo); //���̺� ����
				if(result>0) {
					ep.exInputDialog.setVisible(false);				
				}
			}
		}
		
		
		
	}
	
	
	public ExerciseVO setExerVO() {  //�Է°��� vo�� �����
		ExerciseVO vo = new ExerciseVO();
		
		//�α��� ���̵�ҷ�����
		
		vo.setId(getLoginId());
		
		vo.setDate(getDate());	//Ŭ���� ��¥ �ҷ�����
		vo.setDoDont(btnChoice());  //���� �̼��� ��Ÿ
		
		if(btnChoice().equals("�̼���")) {  //�̼����̸�
			vo.setExerPart("����");
			vo.setCardio(0);
			
		}else {  //�����̳� ��Ÿ
			String exerPart =(String)weightCombo.getSelectedItem();  	//�����
			vo.setExerPart(exerPart); 
			
			
			if(cardioTf.getText().equals("0") || cardioTf.getText().equals("")) {   //����Ҿ�  , 
				
				
				vo.setCardio(0);
			}else {
				
				Integer cardio = Integer.parseInt(cardioTf.getText());
				vo.setCardio(cardio);
			}
		}		
		
		String desc = ta.getText();  //�����
		if(desc.equals(null)) {
			vo.setExerdesc("");
		}else {
			vo.setExerdesc(ta.getText());
		}
		
		return vo;
		
		
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
	
	public String getLoginId() {						//���̵� �ҷ�����
		String loginId =ep.menu.mp.idField.getText();  //������������ �����ִ� ���̵� �ʵ� ����
		return loginId;
	}
	
	
	//�̼��� ��ư��Ŭ���ɶ� ��� �Է�â ��Ȱ��ȭ 
	public void disEnableAll() {				
		weightCombo.setEnabled(false);
		cardioTf.setEnabled(false);
		ta.setEnabled(false);
		
	}
	public void enableAll() {
		weightCombo.setEnabled(true);
		cardioTf.setEnabled(true);
		ta.setEnabled(true);
		
	}
	

}
