package native_jdbc_programming.util;

import java.sql.Connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JdbcUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.printf("%s()%n", "setUpBeforeClass");
	} // TestClass 수행 전 (1)

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.printf("%s()%n", "tearDownAfterClass");
	} // TestClass 수행 후 (5)

	@Before
	public void setUp() throws Exception {
		System.out.printf("%s()%n", "setUp");
	} // testMethod 수행 전 (2)

	@After
	public void tearDown() throws Exception {
		System.out.printf("%s()%n", "tesarDown");
	} // testMethod 수행 후 (4)

	@Test
	public void testGetConnection() {
		System.out.printf("%s()%n", "testGetConnection");
		
		Connection con = JdbcUtil.getConnection();
		System.out.println("con > " + con);
		
		// 성공을 하면 con은 null이 아님
		// 그러나 실패를 하면 null
		Assert.assertNotNull(con); // null이 아니라면 성공했다는 의미 >> 테스트 결과가 파란색창으로 뜸
		
//		fail("Not yet implemented");
	} // testMethod (3)

}
