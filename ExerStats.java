

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
	int perform = 0;  //수행횟수
	int cardioTotal = 0;
	
	int shoulderL =0;		//지난달 수치
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
	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//버튼패널
	
		JButton leftBtn = new JButton("◀");
		JButton rightBtn = new JButton("▶");
		
		
			Integer years[] = {2017,2018,2019};
			DefaultComboBoxModel<Integer> yearComboModel = new DefaultComboBoxModel<Integer>(years);
		JComboBox<Integer> yearCombo = new JComboBox<Integer>(yearComboModel);
			Integer months[] = {1,2,3,4,5,6,7,8,9,10,11,12};
			DefaultComboBoxModel<Integer> monthComboModel = new DefaultComboBoxModel<Integer>(months);
		JComboBox<Integer> monthCombo = new JComboBox<Integer>(monthComboModel);
	
		
		JLabel yLbl = new JLabel("년");
		JLabel mLbl = new JLabel("월");
	
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel yearLbl = new JLabel();
		
		
	
	JPanel statsPanel = new JPanel(new GridLayout(7, 1));
		JPanel shoulderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel shoulderTitle = new JLabel("어깨");
			JLabel shoulderRep = new JLabel(String.valueOf(shoulder));
			JLabel shoulderTime = new JLabel("회");
			JLabel shoulderCheck = new JLabel(""); //변화량 표시
			
		JPanel chestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel chestTitle = new JLabel("가슴");
			JLabel chestRep = new JLabel(String.valueOf(chest));
			JLabel chestTime = new JLabel("회");
			JLabel chestCheck = new JLabel(""); //변화량 표시
		JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel backTitle = new JLabel("등  ");
			JLabel backRep = new JLabel(String.valueOf(back));
			JLabel backTime = new JLabel("회");
			JLabel backCheck = new JLabel(""); //변화량 표시
		JPanel legPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel legTitle = new JLabel("다리");
			JLabel legRep = new JLabel(String.valueOf(leg));
			JLabel legTime = new JLabel("회");
			JLabel legCheck = new JLabel(""); //변화량 표시
		JPanel armPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel armTitle = new JLabel("팔");
			JLabel armRep = new JLabel(String.valueOf(arm));
			JLabel armTime = new JLabel("회");
			JLabel armCheck = new JLabel(""); //변화량 표시
		JPanel cardioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel cardioTitle = new JLabel("유산소");
			JLabel cardioRep = new JLabel(String.valueOf(cardio));
			JLabel cardioTime = new JLabel("회");
			JLabel cardioCheck = new JLabel(""); //변화량 표시
			JLabel cardioMinute = new JLabel(String.valueOf(cardioTotal));
			JLabel cardioM = new JLabel("분"); //유산소 총량 표시
			
		JPanel doPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel doTitle = new JLabel("총 운동 횟수");
			JLabel doRep = new JLabel(String.valueOf(perform));
			JLabel doTime = new JLabel("회");
			JLabel doCheck = new JLabel(""); //변화량 표시
			MenuPage menu;
	public ExerStats(MenuPage menu) {
		setLayout(new BorderLayout());
		this.menu = menu;
		yearCombo.setSelectedItem(cal.get(Calendar.YEAR));
		monthCombo.setSelectedItem(cal.get(Calendar.MONTH)+1); //현재 연월로 맞춤
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

		setDate();			//콤보박스 기준 날짜정보 가져옴
		getExerData();		//해당 날짜의 운동 데이터 가져옴
		setExerText();  //데이터에 따라 날짜, 운동에대한 텍스트 변화
		///////////////////////////////////////////top
		btnPanel.setBackground(Color.CYAN);
		btnPanel.add(leftBtn);
		btnPanel.add(yearCombo);btnPanel.add(yLbl);
		btnPanel.add(monthCombo);btnPanel.add(mLbl);
		btnPanel.add(rightBtn);
		
		titlePanel.add(yearLbl);
		Font titleFont = new Font("HY궁서 보통",Font.BOLD,20);
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
		compareLastData();  //지난달 데이터
		
		yearLbl.setText(String.valueOf(year)+" 년 "+String.valueOf(month)+" 월 운동 통계");
		
		
		shoulderRep.setText(String.valueOf(shoulder));
		chestRep.setText(String.valueOf(chest));
		backRep.setText(String.valueOf(back));
		legRep.setText(String.valueOf(leg));
		armRep.setText(String.valueOf(arm));
		cardioRep.setText(String.valueOf(cardio));
		cardioMinute.setText("총 " + String.valueOf(cardioTotal));
		doRep.setText(String.valueOf(perform));

		String upDown =""; //증가 or 감소
		int shoulderNum = shoulder-shoulderL; //전월 비교수치
		if(shoulderNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			shoulderCheck.setForeground(Color.BLUE);
		}else {
			shoulderCheck.setForeground(Color.RED);
		}
		shoulderCheck.setText("전월 대비 " +shoulderNum+"회"+upDown);
		
		int chestNum = chest-chestL; //전월 비교수치
		if(chestNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			chestCheck.setForeground(Color.BLUE);
		}else {
			chestCheck.setForeground(Color.RED);
		}
		chestCheck.setText("전월 대비 " +chestNum+"회"+upDown);
		
		int backNum = back-backL; //전월 비교수치
		if(backNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			backCheck.setForeground(Color.BLUE);
		}else {
			backCheck.setForeground(Color.RED);
		}
		backCheck.setText("전월 대비 " +backNum+"회"+upDown);
		
		int legNum = leg-legL; //전월 비교수치
		if(legNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			legCheck.setForeground(Color.BLUE);
		}else {
			legCheck.setForeground(Color.RED);
		}
		legCheck.setText("전월 대비 " +legNum+"회"+upDown);
		
		int armNum = arm-armL; //전월 비교수치
		if(armNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			armCheck.setForeground(Color.BLUE);
		}else {
			armCheck.setForeground(Color.RED);
		}
		armCheck.setText("전월 대비 " +armNum+"회"+upDown);
		
		int cardioNum = cardio-cardioL; //전월 비교수치
		if(cardioNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			cardioCheck.setForeground(Color.BLUE);
		}else {
			cardioCheck.setForeground(Color.RED);
		}
		cardioCheck.setText("전월 대비 " +cardioNum+"회"+upDown);
		
		int performNum = perform-performL; //전월 비교수치
		if(performNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			doCheck.setForeground(Color.BLUE);
		}else {
			doCheck.setForeground(Color.RED);
		}
		doCheck.setText("전월 대비 " +performNum+"회"+upDown);
	}
	
	public void backMonth() {
		if(month==1) {
			if(year==2017) {
				JOptionPane.showMessageDialog(this, "더이상 과거로 돌아갈순없소");
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
				JOptionPane.showMessageDialog(this, "너무 앞서가지말게");
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
	
	public void setDate() {                                  //날짜를 콤보박스 기준 지정
		year = (int) yearCombo.getSelectedItem();
		month = (int) monthCombo.getSelectedItem();
		cal.set(year,month-1 , 1);   //콤보에 셀렉된 날짜로 세팅  0월부터시작함 ex> 인간1월은 컴퓨터0월
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
		perform = 0;  //수행횟수
		cardioTotal = 0;
		shoulderL =0;		//지난달 수치
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
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//연,월
		String date = dFormat.format(cal.getTime());		//현재날짜의 연 월		
		String exerDate = date+"%";	
		
		List<ExerciseVO> list = dao.getExerciseData(exerDate,getLoginId());
		
		for(int i=0; i<list.size();i++) {
			ExerciseVO vo =list.get(i);	
			
			if(vo.getExerPart().equals("안함")||vo.getExerPart().equals(null)) {
				
			}
			else if(vo.getExerPart().equals("어깨")) {
				shoulder+=1;
			}else if(vo.getExerPart().equals("가슴")) {
				chest+=1;
			}else if(vo.getExerPart().equals("등")) {
				back+=1;
			}else if(vo.getExerPart().equals("다리")) {
				leg+=1;
			}else if(vo.getExerPart().equals("팔")) {
				arm+=1;
			}
			if(vo.getCardio()>0) {
				cardioTotal += vo.getCardio();
				cardio +=1;
			}
			if(vo.getDoDont().equals("수행")) {
				perform +=1;
			}
			
			
		}
		
		//System.out.println(back);
		
	}
	public void compareLastData() {
		cal.set(year,month-2 , 1);  //이전월  0 1 2 3 4 5 6 7 8 9 10 11
		ExerciseDAO dao = new ExerciseDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//연,월
		String date = dFormat.format(cal.getTime());		//현재날짜의 연 월		
		String exerDate = date+"%";	
		//System.out.println(exerDate);
		List<ExerciseVO> list = dao.getExerciseData(exerDate,getLoginId());		
		for(int i=0; i<list.size();i++) {
			ExerciseVO vo =list.get(i);			
			if(vo.getExerPart().equals("안함")||vo.getExerPart().equals(null)) {
			}
			else if(vo.getExerPart().equals("어깨")) {
				shoulderL+=1;
			}else if(vo.getExerPart().equals("가슴")) {
				chestL+=1;
			}else if(vo.getExerPart().equals("등")) {
				backL+=1;
			}else if(vo.getExerPart().equals("다리")) {
				legL+=1;
			}else if(vo.getExerPart().equals("팔")) {
				armL+=1;
			}
			if(vo.getCardio()>0) {
				cardioTotalL += vo.getCardio();
				cardioL +=1;
			}
			if(vo.getDoDont().equals("수행")) {
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
		if(obj.equals("◀")) {
			backMonth();
		}else if(obj.equals("▶")) {
			goMonth();
		}else if(obj.equals("뒤로가기")) {
			setVisible(false);
			
		}
		Object obj2= e.getSource();
		if(obj2 instanceof JComboBox) {
			clear();
			writeAll();	
		}
		
	}

}
