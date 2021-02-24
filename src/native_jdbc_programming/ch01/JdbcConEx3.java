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
 * JDBC try-catch-resource
 * 2021.02.15
 */
public class JdbcConEx3 {

	public static void main(String[] args) {

		ArrayList<Department> list = null;

		String url = "jdbc:mysql://localhost:3306/mysql_study?useSSL=false"; 
		String user = "user_mysql_study"; // 다르게 쓰면 Access denied for user 씀
		String password = "rootroot";

		String sql = "select deptno, deptname, floor from department";
		
		
		// con을 괄호안에 넣으면 해당 블럭이 끝나고 나서 con.close가 자동호출됨
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs= stmt.executeQuery(sql);
				)
			{
			Class.forName("com.mysql.jdbc.Driver");
			list = new ArrayList<Department>(); 
			
			System.out.println(list);
			while(rs.next()) {
				list.add(getDepartment(rs));
			}
			
			System.out.println(list);
		
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver Not Found");
		} catch (SQLException e) {
			e.printStackTrace();
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
