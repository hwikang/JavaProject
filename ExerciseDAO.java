

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO extends DBConnect {

	public ExerciseDAO() {
		
	}
	public int deleteExerciseData(ExerciseVO vo) {
		int result=0;
		try {
			
			connect();
			String sql ="delete from exercise where exerdate like ? and id = ? ";  //%날짜
					
			ps= con.prepareStatement(sql);			
			ps.setString(1, vo.getDate());
			ps.setString(2, vo.getId());
			
			result = ps.executeUpdate();		
			
		} catch (Exception e) {
			System.out.println("setExerciseData ERROR");
			e.printStackTrace();
		}finally {disConnect();}
		return result;
	}
	public int setExerciseData(ExerciseVO vo) {
		int result=0;
		try {
			
			connect();
			String sql = "insert into exercise(id,exerdate,dodont,exerpart,cardio,exerdesc)"
					+ "	values(?,?,?,?,?,?)";
					
			ps= con.prepareStatement(sql);
			
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getDate());
			ps.setString(3, vo.getDoDont());
			ps.setString(4, vo.getExerPart());
			ps.setInt(5, vo.getCardio());
			ps.setString(6, vo.getExerdesc());
			
			
			result = ps.executeUpdate();
			
			
			
		} catch (Exception e) {
			System.out.println("setExerciseData ERROR");
			e.printStackTrace();
		}finally {disConnect();}
		return result;
	}
	
	public List<ExerciseVO> getExerciseData(String exerDate,String loginId) {
		List<ExerciseVO> al= new ArrayList<ExerciseVO>();// 리스트계열은 순서유지
		
		try {
			//System.out.println(exerDate);
			connect();
			String sql ="select * from exercise where exerdate like ? and id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, exerDate);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ExerciseVO vo = new ExerciseVO();
				vo.setId(rs.getString(1));
				vo.setDate(rs.getString(2));
				vo.setDoDont(rs.getString(3));
				vo.setExerPart(rs.getString(4));
				vo.setCardio(rs.getInt(5));
				vo.setExerdesc(rs.getString(6));
				
				al.add(vo);
				

			}
			
			
			
		} catch (Exception e) {
			System.out.println("getExercisedata error");
			//e.printStackTrace();
		}
		
		return al;
	}
	
	
	
	
	
	
	
/*
	public static void main(String[] args) {
		new ExerciseDAO();

	}*/

}
