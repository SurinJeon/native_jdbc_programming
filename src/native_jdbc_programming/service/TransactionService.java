package native_jdbc_programming.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import native_jdbc_programming.dto.Department;
import native_jdbc_programming.dto.Title;
import native_jdbc_programming.util.JdbcUtil;

public class TransactionService {

	// Title과 Department 모두 add 하는 transaction (All or Nothing)
	public String transAddTitleAndDepartment(Title title, Department dept) {
		String titleSql = "insert into title values (?, ?)"; // Title Insert		
		String deptSql = "insert into department values (?, ?, ?)"; // Department Insert
		
		// 하나의 Connection으로 수행하기
		
		Connection con = null;
		// Connection 하나로 수행하기 때문에 두 가지 pstmt가 필요함
		PreparedStatement tPstmt = null; // title용 pstmt
		PreparedStatement dPstmt = null; // dept용 pstmt
		String res = null;
		
		try {
			// Connection 하나를 생성해서 두 Pstmt에 던지기
			con = JdbcUtil.getConnection(); // 이렇게 불러오면 보통 auto-commit상태 >> 풀어줘야함
			con.setAutoCommit(false);
			
			tPstmt = con.prepareStatement(titleSql);
			tPstmt.setInt(1, title.gettNo());
			tPstmt.setString(2, title.gettName());
			tPstmt.executeUpdate(); // 여기까지는 수행만 한 것 << commit을 아직 하지 않음...
			// add와 remove이기 때문에 executeQuery()가 아니라 executeUpdate()
			
			dPstmt = con.prepareStatement(deptSql);
			dPstmt.setInt(1, dept.getDeptno());
			dPstmt.setString(2, dept.getDeptname());
			dPstmt.setInt(3, dept.getFloor());
			dPstmt.executeUpdate();
			
			// 여기까지 문제없이 왔으면 정상승인! con을 commit() 해주면 됨
			// 위에서 실행 중 문제가 생겼다면 catch문으로 잡혀들어갈 것
			con.commit();
			res = "commit";
		} catch (SQLException e) {
			res = "rollback";
			rollbackUtil(con);
		} finally {
			System.out.println(res);
			closeUtil(con, tPstmt, dPstmt);
		}
		return res;
		
	} // end of addTitleAndDepartment()

	public void closeUtil(Connection con, PreparedStatement tPstmt, PreparedStatement dPstmt) {
		try {
			con.setAutoCommit(true);
			
			// 또한 resource문이 아니기 때문에 Pstmt와 con을 연결해제하는 과정 필요
			
			// 원래는 모두 각각 따로 try-catch 묶어야하는데 일단 한방에 묶어놓음
			// if문은 String res 때문에 넣음
			if(tPstmt != null) tPstmt.close();
			if(dPstmt != null) dPstmt.close();
			if(con != null) con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} // 작업 성공이든 실패든 모두 원상복원하는 과정 반드시 필요
	} // end of closeUtil()

	public void rollbackUtil(Connection con) {
		try {
			con.rollback(); // 문제가 생긴다면 commit() 말고 rollback()해라
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	} // end of rollBackUtil();
	
	public int transRemoveTitleAndDepartment(Title title, Department dept) {
		String titleSql = "delete from title where tno = ?"; // Title Delete
		String deptSql = "delete from department where deptno = ?"; // Department Delete

		Connection con = null;

		PreparedStatement tPstmt = null;
		PreparedStatement dPstmt = null;
		int res = 0;
		
		try {
			con = JdbcUtil.getConnection();
			con.setAutoCommit(false);

			tPstmt = con.prepareStatement(titleSql);
			tPstmt.setInt(1, title.gettNo());
			res += tPstmt.executeUpdate();

			dPstmt = con.prepareStatement(deptSql);
			dPstmt.setInt(1, dept.getDeptno());
			res += dPstmt.executeUpdate();

			if(res == 2) {
				con.commit();
				System.out.println("commit()");
			} else {
				throw new SQLException();
			} // 반영된 행 값이 2개가 아니라면 예외를 의도적으로 발생시켜서 catch 블럭에 잡히도록 함
			
		} catch (SQLException e) {
			rollbackUtil(con);
		} finally {
			closeUtil(con, tPstmt, dPstmt);
		}
		
		return res;
		
	} // end of transRemoveTitleAndDepartment()

}
