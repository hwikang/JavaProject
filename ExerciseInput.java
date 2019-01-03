

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
			JLabel trueFalseLbl = new JLabel("    운동여부");
			JLabel weightLbl = new JLabel("    웨이트");
			JLabel cardioLbl = new JLabel("    유산소");
		JPanel inputPanel = new JPanel(new GridLayout(3, 1));
			JPanel btnPanel = new JPanel(new GridLayout(1,3));
				JButton defaultBtn = new JButton("기타");
				JButton trueBtn = new JButton("수행");
				JButton falseBtn = new JButton("미수행");
			JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
				String weights[] = {"어깨","등","가슴","다리","팔","안함"};
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(weights);
				JComboBox<String> weightCombo = new JComboBox<String>();
			JPanel cardioPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));				
				JTextField cardioTf = new JTextField(10);
				JLabel bun = new JLabel("분");
	
		JPanel descPanel = new JPanel(new BorderLayout(30,0));
			JLabel descLbl = new JLabel("    내용");
			JTextArea ta = new JTextArea("");	
			JScrollPane desc = new JScrollPane(ta);
			JLabel spaceLbl = new JLabel("    ");
			
	
		
		JPanel savePanel = new JPanel(new BorderLayout());
			JButton saveBtn = new JButton("저장");
	
			
		
			ExercisePage ep;
			
	public ExerciseInput(ExercisePage ep) {
		/*super("운동 기록 입력");*/
		this.ep = ep;
		setLayout(new BorderLayout(0,30));  //간격
		 
		
		
		
		
		////////////////////////////////////////////////////버튼설정
			defaultBtn.setBackground(Color.gray);
			trueBtn.setBackground(Color.green);
			falseBtn.setBackground(Color.magenta);
			

			defaultBtn.setBorderPainted(false);
			trueBtn.setBorderPainted(false);
			falseBtn.setBorderPainted(false);			
			
			defaultBtn.setBorder(new LineBorder(Color.black,3));
			trueBtn.setBorder(new LineBorder(Color.black,3));
			falseBtn.setBorder(new LineBorder(Color.black,3));
		//////////////////////////////////////////////////////////input패널	
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
	////////////////////////////////////////////////////////////EXERCISE패널		
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
		
		fillInputField();  //이전에 채웠던값 채우기
		
	}
	
	
	public String getDate() {				//날짜 옮겨받기
		
		Calendar cal = Calendar.getInstance();
		cal.set(ep.year, ep.month-1, ep.day);		
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM/dd");		
		String date = dFormat.format(cal.getTime());		
		
		return date;
	}
	
	public void fillInputField() {			//이미 채워져있는값 불러오기
		ExerciseDAO dao = new ExerciseDAO();
		
		List<ExerciseVO> al =  dao.getExerciseData(getDate(),getLoginId());  //날짜로 레코드불러옴
		ExerciseVO vo = new ExerciseVO();
		
		for(int i = 0 ; i<al.size();i++) {   //al 있을때만, 나온데이터가 있을떄만			
			vo = al.get(i); //1줄의 레코드가 vo로 읽힘			
			//버튼상태불러와서 버튼상태 수정 
			if(vo.getDoDont().equals("기타")) {  
				clickedDefaultBtn();
			}else if(vo.getDoDont().equals("수행")) { 			
				clickedTrueBtn();
			}else if(vo.getDoDont().equals("미수행")) {  
				clickedFalseBtn();
			}			
			//운동부위 불러오기
			weightCombo.setSelectedItem(vo.getExerPart());
			//유산소 불러오기
			cardioTf.setText(String.valueOf(vo.getCardio()));
			//내용 불러오기
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
		if(obj.equals("기타")) {
			clickedDefaultBtn();
			
		}else if(obj.equals("수행")){
			clickedTrueBtn();
			
		}else if(obj.equals("미수행")){
			clickedFalseBtn();
			
		}		
		
		if(obj.equals("저장")) {       //데이터 저장버튼
			if(defaultBtn.isEnabled()&&trueBtn.isEnabled()&&falseBtn.isEnabled()) {
				JOptionPane.showMessageDialog(this, "수행 버튼을 클릭하세요");
				
			}else {
				int result = 0;
				ExerciseVO vo =setExerVO();  //입력값 vo로 넣기
				ExerciseDAO dao = new ExerciseDAO();
				dao.deleteExerciseData(vo);	//수정할경우 기존 데이터 지우고  
				result =dao.setExerciseData(vo); //테이블에 대입
				if(result>0) {
					ep.exInputDialog.setVisible(false);				
				}
			}
		}
		
		
		
	}
	
	
	public ExerciseVO setExerVO() {  //입력값을 vo로 만들기
		ExerciseVO vo = new ExerciseVO();
		
		//로그인 아이디불러오기
		
		vo.setId(getLoginId());
		
		vo.setDate(getDate());	//클릭한 날짜 불러오기
		vo.setDoDont(btnChoice());  //수행 미수행 기타
		
		if(btnChoice().equals("미수행")) {  //미수행이면
			vo.setExerPart("안함");
			vo.setCardio(0);
			
		}else {  //수행이나 기타
			String exerPart =(String)weightCombo.getSelectedItem();  	//운동부위
			vo.setExerPart(exerPart); 
			
			
			if(cardioTf.getText().equals("0") || cardioTf.getText().equals("")) {   //유산소양  , 
				
				
				vo.setCardio(0);
			}else {
				
				Integer cardio = Integer.parseInt(cardioTf.getText());
				vo.setCardio(cardio);
			}
		}		
		
		String desc = ta.getText();  //내용란
		if(desc.equals(null)) {
			vo.setExerdesc("");
		}else {
			vo.setExerdesc(ta.getText());
		}
		
		return vo;
		
		
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
	
	public String getLoginId() {						//아이디 불러오기
		String loginId =ep.menu.mp.idField.getText();  //메인페이지에 적혀있는 아이디 필드 정보
		return loginId;
	}
	
	
	//미수행 버튼이클릭될때 모든 입력창 비활성화 
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
