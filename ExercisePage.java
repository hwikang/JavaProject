

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
	
	JPanel btnPanel = new JPanel();//버튼패널
		JButton backBtn = new JButton("뒤로가기");
		JButton leftBtn = new JButton("◀");
		JButton rightBtn = new JButton("▶");
		
		
			Integer years[] = {2017,2018,2019};
			DefaultComboBoxModel<Integer> yearComboModel = new DefaultComboBoxModel<Integer>(years);
		JComboBox<Integer> yearCombo = new JComboBox<Integer>(yearComboModel);
			Integer months[] = {1,2,3,4,5,6,7,8,9,10,11,12};
			DefaultComboBoxModel<Integer> monthComboModel = new DefaultComboBoxModel<Integer>(months);
		JComboBox<Integer> monthCombo = new JComboBox<Integer>(monthComboModel);

		
		JLabel yearLbl = new JLabel("년");
		JLabel monthLbl = new JLabel("월");
		
	
		
	JPanel dayTitlePanel = new JPanel(new BorderLayout());//달력패널
		JPanel titlePanel = new JPanel(new GridLayout(1, 7));	//요일패널
		JPanel dayPanel = new JPanel(new GridLayout(0, 7));	//날짜패널
		
	int year;
	int month;
	int day;
	int dayLength ;
	MenuPage menu;
	
	JDialog exInputDialog;
	public ExercisePage(MenuPage menu) {
		super("운동기록");
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
		monthCombo.setSelectedItem(cal.get(Calendar.MONTH)+1); //현재 연월로 맞춤
		
		dayBtn(); //날짜가 지정되고 버튼이생김
		
		backBtn.addActionListener(this);
		leftBtn.addActionListener(this);
		rightBtn.addActionListener(this);
		yearCombo.addActionListener(this);
		monthCombo.addActionListener(this);		
		

		
		setSize(700,700);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
		
	}
	
	public void setDate() {                                  //날짜를 콤보박스 기준 지정
		year = (int) yearCombo.getSelectedItem();
		month = (int) monthCombo.getSelectedItem();
		cal.set(year,month-1 , 1);   //콤보에 셀렉된 날짜로 세팅  0월부터시작함 ex> 인간1월은 컴퓨터0월
		//System.out.println(cal.get(Calendar.MONTH));
		
	}
		
	
	public void dayBtn() {  //요일 +날짜버튼 생성
		String title[] = {"일","월","화","수","목","금","토"};
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
		
		setDate();  //1일로 세팅
		
		int firstDay = cal.get(Calendar.DAY_OF_WEEK); //1일이 시작되는 요일, index 1부터시작함
		for(int i =1 ; i<firstDay; i++) {				//스페이스
			JLabel lbl = new JLabel("");
			dayPanel.add(lbl);
		}
		
		dayLength =  cal.getActualMaximum(Calendar.DAY_OF_MONTH); //마지막날
		for(int i =1; i<=dayLength; i++) {
			
			JButton day = new JButton(String.valueOf(i));//날짜버튼생성
			
			
			day.setBackground(changeBtnColor(i));	//날마다 색깔 지정해주기
			day.addActionListener(this);			//날짜 버튼 클릭
			dayPanel.add(day);
			
		}
		dayTitlePanel.add(dayPanel,"Center");
	}
	
	public Color changeBtnColor(int btnNum) {
		Color color = Color.GRAY;
		
		ExerciseDAO dao = new ExerciseDAO();
		SimpleDateFormat dFormat = new SimpleDateFormat("yy/MM");	//연,월
		String date = dFormat.format(cal.getTime());		//현재날짜의 연 월		
		String exerDate = date+"%";	
		//System.out.println("exerdate="+exerDate);
		
		List<ExerciseVO> list = dao.getExerciseData(exerDate,getLoginId()); // 18/12% 형태로 데이터들불러옴 
		for(int i=0; i<list.size();i++) {
			ExerciseVO vo =list.get(i);								//해당 월에 해당하는 레코드 1줄씩
			String day = vo.getDate().substring(8, 10); //날짜만가져오기 "08"
			if(Integer.parseInt(day)==btnNum) {			//존재한다.
				if(vo.getDoDont().equals("수행")) {
					color = Color.GREEN;
				}else if(vo.getDoDont().equals("미수행")) {
					color = Color.MAGENTA;
				}else if(vo.getDoDont().equals("기타")) {
					color = Color.BLACK;
				}
			}
			
			
			
			
		}
		
		return color;
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {				//ACTIONLISTENER
		Object obj = e.getActionCommand();
		if(obj.equals("◀")) {
			backMonth();
		}else if(obj.equals("▶")) {
			goMonth();
		}else if(obj.equals("뒤로가기")) {
			setVisible(false);
			
		}
		for(int i =0; i<=dayLength; i++) {  			//날짜버튼 클릭시
			if(obj.equals(String.valueOf(i))) {				
				day = i;							//날짜 저장
													//다이얼로그로 운동인풋창 생성
				exInputDialog = new JDialog(this,"운동기록", true);
				ExerciseInput ei = new ExerciseInput(this);
				exInputDialog.add(ei);				
				exInputDialog.setSize(400,500);
				exInputDialog.setVisible(true);
				exInputDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
				clear();
				dayBtn();  //버튼 다시생성
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
				JOptionPane.showMessageDialog(this, "더이상 과거로 돌아갈순없소");
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
				JOptionPane.showMessageDialog(this, "너무 앞서가지말게");
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
		//System.out.println("expage에서 부른 id값="+id);
		return id;
	}
	
	

}
