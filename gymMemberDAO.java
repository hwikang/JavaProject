

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class gymMemberDAO extends DBConnect {

	public gymMemberDAO() {
		getPassword("khdrogba");
	}
	
	public List<String> getIdList(){			//아이디 중복 확인
		List<String> lst =new ArrayList<String>();
		//connect();
		String sql = "select id from login"; 
		try {
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				lst.add(rs.getString(1));  //1번컬럼 -> 아이디 호출함
				//System.out.println(rs.getString(1));
			}
			
			
		} catch (Exception e) {
			System.out.println("get ID List error");
			e.printStackTrace();
		}
			
		return lst;
	}
	public String getPassword(String id) {
		
		String password ="" ;
		try {
			connect();
			String sql = "select pwd from login where id=? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery(); //run해서 나온거 들어감
			rs.next();
			password = rs.getString(1);
			
		} catch (Exception e) {
			System.out.println("get password error");
			e.printStackTrace();
		}
		
		return password;
	}
	
	
	public int insertMembership(gymMemberVO vo){  
		int result=0;
		try {
			connect();
			String sql = "insert into login values(?,?,?,sysdate)";
			ps = con.prepareStatement(sql);
			
			System.out.println("아이디는 ="+vo.getId());
		
			ps.setString(1,vo.getId());
			ps.setString(2,vo.getPwd());
			ps.setString(3,vo.getName());
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {disConnect();}
		
		
		return result;
		
	}
	
	public int getLoginCheck(String id, String pwd) {
		int result=0;
		try {
			connect();
			String sql = "select count(id) from login where id=? and pwd=?";
			ps = con.prepareStatement(sql);
			ps.setString(1,  id);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {disConnect();}
		
		
		return result;
		
	}
/*
	public static void main(String[] args) {
		new gymMemberDAO();

	}*/

}
