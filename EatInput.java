

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
		JButton defaultBtn = new JButton("기타");
		JButton trueBtn = new JButton("수행");
		JButton falseBtn = new JButton("미수행");
	JPanel ioPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(4,1));
			JPanel chickPanel = new JPanel(new FlowLayout());
				JLabel chickLbl = new JLabel("닭가슴살");
				JTextField chickTf = new JTextField(15);
				JLabel gram = new JLabel("g");
			JPanel sweetPanel = new JPanel(new FlowLayout());
				JLabel sweetLbl = new JLabel("고구마");
				JTextField sweetTf = new JTextField(15);
				JLabel gram2 = new JLabel("g");
			JPanel ricePanel = new JPanel(new FlowLayout());
				JLabel riceLbl = new JLabel("일반식");
				JTextField riceTf = new JTextField(15);
				JLabel ggi = new JLabel("끼");
			JPanel powderPanel = new JPanel(new FlowLayout());
				JLabel powderLbl = new JLabel("단백질보충제");
				JTextField powderTf = new JTextField(15);
				JLabel scoop = new JLabel("스쿱");
			
		JButton calculator = new JButton("계산");
		JPanel outputPanel = new JPanel(new GridLayout(2,1));
			JPanel proteinPanel = new JPanel(new FlowLayout());
				JLabel proteinLbl= new JLabel("총  단백질 = ");
				JTextField proteinTf = new JTextField(10);
			JPanel carbPanel = new JPanel(new FlowLayout());
				JLabel carbLbl= new JLabel("총탄수화물 = ");
				JTextField carbTf = new JTextField(10);
				
			
	JButton saveButton = new JButton("저장");			
		
		EatPage ep;
		boolean isCalculator = false;
		
	public EatInput(EatPage ep) {
		setLayout(new BorderLayout(0,20));
		this.ep = ep;
		////////////////////////////////////////////////////버튼설정
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
	public void fillInputField() {		//그날의 데이터
		EatDAO dao = new EatDAO();
		
		List<EatVO> list = dao.getEatData(getDate(), ep.menu.mp.idField.getText());
		for(int i =0; i<list.size();i++) {
			EatVO vo = new EatVO();
			vo = list.get(i);
			if(vo.getDoDont().equals("기타")) {  
				clickedDefaultBtn();
			}else if(vo.getDoDont().equals("수행")) { 			
				clickedTrueBtn();
			}else if(vo.getDoDont().equals("미수행")) {  
				clickedFalseBtn();
			}	
			proteinTf.setText(String.valueOf(vo.getProtein()));
			carbTf.setText(String.valueOf(vo.getCarb()));
			
		}
		
	}
	
	public void getTotal() {						//탄수화물 단백질 계산기
		protein=0;
		carb =0;									//초기화
		if(!chickTf.getText().equals("")) {
			protein += Integer.parseInt(chickTf.getText())*0.23; //생가슴살 1g당 단백질 0.23g
		}
		if(!powderTf.getText().equals("")) {
			protein += Integer.parseInt(powderTf.getText())*30;  //1스쿱에 30g
		}
		if(!sweetTf.getText().equals("")) {
			carb += Integer.parseInt(sweetTf.getText())*0.3; //고구마 1g당 탄수 0.3g
			protein += Integer.parseInt(sweetTf.getText())*0.02; //단백질 0.02g
			
		}
		if(!riceTf.getText().equals("")) {
			carb += Integer.parseInt(riceTf.getText())*50; //1끼기준 탄수 40g 단백질20g
			protein += Integer.parseInt(riceTf.getText())*20;
		}
		DecimalFormat df = new DecimalFormat("###.##");
		proteinF = df.format(protein);  			//단백질 탄수 포맷
		carbF = df.format(carb);
	}
	public void actionPerformed(ActionEvent e) {					//ACTIONLISTENER
		Object obj =e.getActionCommand();
		if(obj.equals("계산")) {
			getTotal();			
			isCalculator=true;
			proteinTf.setText(proteinF);
			carbTf.setText(carbF);
		}
		if(obj.equals("기타")) {
			clickedDefaultBtn();
			
		}else if(obj.equals("수행")){
			clickedTrueBtn();
			
		}else if(obj.equals("미수행")){
			clickedFalseBtn();
			
		}
		if(obj.equals("저장")) {
			if(defaultBtn.isEnabled()&&trueBtn.isEnabled()&&falseBtn.isEnabled()) {
				JOptionPane.showMessageDialog(this, "수행 버튼을 클릭하세요");
				
			}else if(falseBtn.isEnabled()&&!isCalculator) {  //미수행이 아닌데 계산안할때
				JOptionPane.showMessageDialog(this, "계산 후 저장 하세요");
			}
				
			else {
				getTotal();					//탄수화물 단백질 계산
				EatVO vo = setVO();			//vo 대입
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
		if(btnChoice().equals("미수행")) {
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
	//////////////////////////////////////////////////////////입력값찾아오기
	public String getDate() {				//날짜 옮겨받기
		
		Calendar cal = Calendar.getInstance();
		cal.set(ep.year, ep.month-1, ep.day);		
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM/dd");		
		String date = dFormat.format(cal.getTime());		
		
		return date;
	}
	public String btnChoice() {					//고른 버튼
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
	public void clickedDefaultBtn() {	//버튼누를시 다른버튼 활성화
		
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
	//미수행 버튼이클릭될때 모든 입력창 비활성화 
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
