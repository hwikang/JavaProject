

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
	
	int doTrue=0; // 수행
	int doFalse = 0; //미수행
	String doPercent="0";
	int carbTotal = 0;
	int proteinTotal = 0;
	int proteinDay=1;
	int carbDay =1;
	
	int doTrueL=0; // 수행
	int doFalseL = 0; //미수행
	String doPercentL="0";
	int carbTotalL = 0;
	int proteinTotalL = 0;
	int proteinDayL=1;
	int carbDayL =1;
	
	
	
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
			JLabel yearLbl = new JLabel("");
			/*JLabel yl = new JLabel("년");
			JLabel monthLbl = new JLabel(String.valueOf(month));
			JLabel ml = new JLabel("월 운동 통계");*/
			
		JPanel statsPanel = new JPanel(new GridLayout(5, 1));
			JPanel doPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel doTitle = new JLabel("수행");
			JLabel doRep = new JLabel("");
			JLabel doCheck = new JLabel(""); //변화량 표시
			
			JPanel dontPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel dontTitle = new JLabel("미수행");
			JLabel dontRep = new JLabel("");
			JLabel dontCheck = new JLabel(""); //변화량 표시
			
			JPanel percentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel percentTitle = new JLabel("수행률");
			JLabel percentRep = new JLabel("");
			JLabel percentCheck = new JLabel(""); //변화량 표시
			
			JPanel proteinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel proteinTitle = new JLabel("단백질 평균");
			JLabel proteinRep = new JLabel("");
			JLabel proteinCheck = new JLabel(""); //변화량 표시
			
			JPanel carbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel carbTitle = new JLabel("단백질 평균");
			JLabel carbRep = new JLabel("");
			JLabel carbCheck = new JLabel(""); //변화량 표시
		
		
		MenuPage menu;
	public EatStats(MenuPage menu) {
		this.menu = menu;
		setLayout(new BorderLayout());
		
		yearCombo.setSelectedItem(cal.get(Calendar.YEAR));
		monthCombo.setSelectedItem(cal.get(Calendar.MONTH)+1); //현재 연월로 맞춤
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

		setDate();			//콤보박스 기준 날짜정보 가져옴
		getEatData();		//해당 날짜의 운동 데이터 가져옴
		setEatText();  //데이터에 따라 날짜, 운동에대한 텍스트 변화
		///////////////////////////////////////// top
		btnPanel.setBackground(Color.CYAN);
		btnPanel.add(leftBtn);
		btnPanel.add(yearCombo);btnPanel.add(yLbl);
		btnPanel.add(monthCombo);btnPanel.add(mLbl);
		btnPanel.add(rightBtn);
		
		titlePanel.add(yearLbl); /*titlePanel.add(yl);
		titlePanel.add(monthLbl); titlePanel.add(ml);*/
		Font titleFont = new Font("HY궁서 보통",Font.BOLD,20);
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
	public void setDate() {                                  //날짜를 콤보박스 기준 지정
		year = (int) yearCombo.getSelectedItem();
		month = (int) monthCombo.getSelectedItem();
		cal.set(year,month-1 , 1);   //콤보에 셀렉된 날짜로 세팅  0월부터시작함 ex> 인간1월은 컴퓨터0월
		//System.out.println(cal.get(Calendar.MONTH));
		
	}

	public void getEatData() {
		
		EatDAO dao = new EatDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//연,월
		String date = dFormat.format(cal.getTime());		//현재날짜의 연 월		
		String eatDate = date+"%";	
		
		List<EatVO> list = dao.getEatData(eatDate,getLoginId());
		
		for(int i=0; i<list.size();i++) {
			EatVO vo =list.get(i);	
			
			if(vo.getDoDont().equals("기타")) {
				
			}
			else if(vo.getDoDont().equals("수행")) {
				doTrue+=1;
			}else if(vo.getDoDont().equals("미수행")) {
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
		doPercent = df.format(per);		//수행률 =수행/마지막날
	
		
	}
	
	public void compareLastData() {
		cal.set(year,month-2 , 1);  //이전월
		EatDAO dao = new EatDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//연,월
		String date = dFormat.format(cal.getTime());		//현재날짜의 연 월		
		String eatDate = date+"%";	
		
		List<EatVO> list = dao.getEatData(eatDate,getLoginId());
		
		for(int i=0; i<list.size();i++) {
			EatVO vo =list.get(i);	
			
			if(vo.getDoDont().equals("기타")) {
				
			}
			else if(vo.getDoDont().equals("수행")) {
				doTrueL+=1;
			}else if(vo.getDoDont().equals("미수행")) {
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
		doPercentL = df.format(per);		//수행률 =수행/마지막날
	}
	
	public void setEatText() {
		compareLastData(); //지난달 데이터 불러오기
		yearLbl.setText(String.valueOf(year) +" 년 "+String.valueOf(month) +" 월 식단 통계");
		//monthLbl.setText(String.valueOf(month));
		
		doRep.setText(String.valueOf(doTrue) +" 회 ");
		dontRep.setText(String.valueOf(doFalse) + " 회 ");
		percentRep.setText(doPercent +" % ");
		
		proteinRep.setText(String.valueOf(proteinTotal/proteinDay) +" g 섭취");
		carbRep.setText(String.valueOf(carbTotal/carbDay) +" g 섭취");
		
		String upDown =""; //증가 or 감소
		int doTrueNum = doTrue-doTrueL; //전월 비교수치
		if(doTrueNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			doCheck.setForeground(Color.BLUE);
		}else {
			doCheck.setForeground(Color.RED);
		}
		doCheck.setText("전월 대비 " +doTrueNum+" 회 "+upDown);
		
		int doFalseNum = doFalse-doFalseL; //전월 비교수치
		if(doFalseNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			dontCheck.setForeground(Color.RED);
		}else {
			dontCheck.setForeground(Color.BLUE);
		}
		dontCheck.setText("전월 대비 " +doFalseNum+ " 회 "+upDown);
		
		Double percentNum = Double.parseDouble(doPercent)-Double.parseDouble(doPercentL); //전월 비교수치
		if(percentNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			percentCheck.setForeground(Color.blue);
		}else {
			percentCheck.setForeground(Color.red);
		}
		percentCheck.setText("전월 대비 " +percentNum+" % "+upDown);
		
		int proteinNum = proteinTotal/proteinDay - proteinTotalL/proteinDayL ;
		if(proteinNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			proteinCheck.setForeground(Color.blue);
		}else {
			proteinCheck.setForeground(Color.red);
		}
		proteinCheck.setText("전월 대비 " +proteinNum+" g "+upDown);
		
		int carbNum = carbTotal/carbDay - carbTotalL/carbDayL ;
		if(carbNum>=0) {
			upDown = "증가";
		}else {
			upDown ="감소";
		}
		if(upDown.equals("증가")) {
			carbCheck.setForeground(Color.blue);
		}else {
			carbCheck.setForeground(Color.red);
		}
		carbCheck.setText("전월 대비 " +carbNum+" g "+upDown);

	}	
	public void clear() {
		titlePanel.setVisible(false);
		statsPanel.setVisible(false);
		titlePanel.removeAll();
		titlePanel.removeAll();
	
		doTrue=0; // 수행
		doFalse = 0; //미수행
		doPercent="0";
		carbTotal = 0;
		proteinTotal = 0;
		proteinDay=1;
		carbDay =1;
		doTrueL=0; // 수행
		doFalseL = 0; //미수행
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
		
	

}
