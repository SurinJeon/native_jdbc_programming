package native_jdbc_programming.ch01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import native_jdbc_programming.dto.Department;

/**
 * @author lenovo
 * JDBC 
 * 2021.02.15
 */
public class JdbcConEx2 {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Department> list = null;
		
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");
			
			// 2. 데이터베이스 커넥션 생성 (import는 java.sql로!)
			
			// 접근할 db의 url
			String url = "jdbc:mysql://localhost:3306/mysql_study?useSSL=false"; // useSSL을 써야 메세지가 뜨지 않음
			// port 번호는 사실 안적어도 되는데 fm대로 하면 적어야함
			
			String user = "user_mysql_study"; // 다르게 쓰면 Access denied for user 씀
			String password = "rootroot";
			
			con = DriverManager.getConnection(url, user, password);
			System.out.println("con : " + con); // << 찍어서 직접 확인해보기
			
			// 3. statement  생성
			String sql = "insert into department values (?, ?, ?)"; // 아직 틀만 작성하는 단계라 물음표 들어가있음
			pstmt = con.prepareStatement(sql); // 사실 보안(SQK Ingection)에 취약하기 때문에 createStatement 쓰지 않는 것이 좋음
			System.out.println("pstmt > " + pstmt);
			
			Department newDept = new Department(7, "회계", 11);
			
			pstmt.setInt(1, newDept.getDeptno());
			pstmt.setString(2, newDept.getDeptname());
			pstmt.setInt(3, newDept.getFloor());
			
			// 아직까지는 실제로 insert되진 않았음
			
			// 4. pstmt 실행
			int res = pstmt.executeUpdate();
			
			if(res == 1) {
				System.out.println("추가 성공");
			} else {
				System.out.println("추가 실패");
			}
			// 두번 실행하면 Duplicate entry '7' for key 'PRIMARY' 에러뜸
			System.out.println("pstmt > " + pstmt);
			
			
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			System.err.println("JDBC Driver Not Found");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 세 개 다 예외처리 해줘야함 다만 예외가 거의 발생하지 않기 때문에 한 줄로 처리함
//			try { rs.close(); } catch (SQLException e) {} >> select때만 res가 있음

			try { pstmt.close(); } catch (SQLException e) {}
			
			try { con.close(); } catch (SQLException e) {}
			
		}
		
	} // end of main

} // end of class
