

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {

	static {
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
	} catch (Exception e) {
		System.out.println("driver loading error");
		e.printStackTrace();
	}
	
	}
	
	
	String url = "jdbc:oracle:thin:@172.16.1.13:1521:orcl";
	Connection con ;
	PreparedStatement ps;
	ResultSet rs;
	
	
	
	
	public DBConnect() {
		
	}
	
	public void connect() {
		try {
			//Thread.sleep(100);
			con =DriverManager.getConnection(url, "scott", "tiger");
	
		} catch (Exception e) {
			System.out.println("connection error");
			e.printStackTrace();
		}
		
		//System.out.println("connected!");
		
	}
	
	public void disConnect() {
		try {
			
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
			if(con!=null)con.close();
		} catch (Exception e) {
			System.out.println("disconnection error");
			e.printStackTrace();
		}
		
	}
	


}
