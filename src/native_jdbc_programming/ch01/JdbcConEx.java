package native_jdbc_programming.ch01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import native_jdbc_programming.dto.Department;

/**
 * @author lenovo
 * JDBC 프로그램의 전형적인 실행 순서
 * 2021.02.15
 */
public class JdbcConEx {

	public static void main(String[] args) {

		Connection con = null;
		Statement stmt = null;
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
			stmt = con.createStatement(); // 사실 보안(SQK Ingection)에 취약하기 때문에 createStatement 쓰지 않는 것이 좋음
			System.out.println("stmt : " + stmt);
			
			// 4. 쿼리 실행 (만약 attribute 이름 잘못 적으면 SyntaxErrorException가 뜸)
			String sql = "select deptno, deptname, floor from department";
			rs= stmt.executeQuery(sql);	
			
			// 5. 쿼리 실행결과 출력
			// rs 받기위해 반복문 돌림
			// rs.next는 그 다음 요소 의미, 그다음이 있으면 true, 없으면 false를 리턴
			list = new ArrayList<Department>(); // >> ArrayList를 쓰는 이유는 Department에 몇 개가 들어올 지 모르기 때문
			
			System.out.println(list);
			while(rs.next()) {
				list.add(getDepartment(rs));
			}
			
			System.out.println(list);
					
			/*while(rs.next()){
				Department dept = getDepartment(rs); // 아래 과정 method로 빼서 간결하게 하기 
				System.out.println(dept); 
				
//				int deptno = rs.getInt("deptno"); // lable로 받아옴
//				String deptname = rs.getString("deptname");
//				int floor = rs.getInt("floor");
//				
//				System.out.printf("%d, %s, %d%n", deptno, deptname, floor);
				
			}
			*/
			
			// 6. 커넥션 종료 (역순으로 종료해아함!)
			// 하지만 예외발생했을땐 반영되지 않을 수 있음
			// rs.close();
			// stmt.close();
			// con.close();
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			System.err.println("JDBC Driver Not Found");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 세 개 다 예외처리 해줘야함 다만 예외가 거의 발생하지 않기 때문에 한 줄로 처리함
			try { rs.close(); } catch (SQLException e) {}

			try { stmt.close(); } catch (SQLException e) {}
			
			try { con.close(); } catch (SQLException e) {}
			
		}
		
		System.out.println("Department Query 결과는");
		for(Department d : list) {
			System.out.println(d);
		}
		
	} // end of main

	private static Department getDepartment(ResultSet rs) throws SQLException{
		
		int deptno = rs.getInt("deptno");
		String deptname = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptno, deptname, floor);
		
	} // end of getDepartment

}
