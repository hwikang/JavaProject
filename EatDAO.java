


import java.util.ArrayList;
import java.util.List;

public class EatDAO extends DBConnect {

	
	
	
	
	public EatDAO() {
		
	}
	public int deleteEatData(EatVO vo) {
		int result = 0;
		try {
			connect();
			String sql = "delete from eat where eatdate like ? and id = ? ";  //%날짜
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getDate());
			ps.setString(2, vo.getId());
			
			
			result =ps.executeUpdate();
			//System.out.println(result +"데이터삭제완료");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete eat data error");
		}finally {disConnect();}
		
		return result;
	}
	
	public int setEatData(EatVO vo) {
		int result = 0;
		try {
			connect();
			String sql = "insert into eat(id,eatdate,dodont,carbohydrate,protein,meal) values(?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getDate());
			ps.setString(3, vo.getDoDont());
			ps.setDouble(4, vo.getCarb());
			ps.setDouble(5, vo.getProtein());
			ps.setInt(6, vo.getMeal());
			
			result =ps.executeUpdate();
			System.out.println(result +"식단 데이터 입력 완료");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("set eat data error");
		}finally {disConnect();}
		
		return result;
	}
	public List<EatVO> getEatData(String eatDate,String loginId){
		List<EatVO> list = new ArrayList<EatVO>();
		connect();
		try {
			String sql = "select * from eat where eatdate like ? and id=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, eatDate);
			ps.setString(2, loginId);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				EatVO vo = new EatVO();
				vo.setId(rs.getString(1));
				vo.setDate(rs.getString(2));
				vo.setDoDont(rs.getString(3));
				vo.setCarb(rs.getDouble(4));
				vo.setProtein(rs.getDouble(5));
				vo.setMeal(rs.getInt(6));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("get eat data error");
		}
		
		
		return list;
	}
	

}
