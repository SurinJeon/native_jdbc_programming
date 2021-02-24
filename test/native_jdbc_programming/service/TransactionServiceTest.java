package native_jdbc_programming.service;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_programming.dto.Department;
import native_jdbc_programming.dto.Title;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TransactionServiceTest {

	private static TransactionService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TransactionServiceTest를 실행하기 전에 수행
		service = new TransactionService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// TransactionServiceTest를 실행한 후에 수행
		service = null;
	}	

	@After
	public void tearDown() throws Exception {
		// Testmethod 끝날 때마다 호출
		System.out.println();
	}

	// 각각 4개의 세부 테스트 필요 (All or Nothing) >> o, x / x, o / x, x / o, o
	
	@Test
	public void test01TransAddTitleAndDepartment_FailTitle() {
		System.out.printf("%s()%n", "TransAddTitleAndDepartment_FailTitle");
		
		Title title = new Title(1, "인턴"); // 실행불가능한 문장(duplicated)
		Department dept = new Department(5, "비상계획부", 10); // 실행가능한 문장
		
		String res = service.transAddTitleAndDepartment(title, dept);
		Assert.assertEquals("rollback", res);
		
	}

	@Test
	public void test02TransAddTitleAndDepartment_FailDept() {
		System.out.printf("%s()%n", "TransAddTitleAndDepartment_FailDept");

		Title title = new Title(6, "인턴"); // 실행불가능한 문장(duplicated)
		Department dept = new Department(1, "비상계획부", 10); // 실행가능한 문장

		String res = service.transAddTitleAndDepartment(title, dept);
		Assert.assertEquals("rollback", res);
	}
	
	@Test
	public void test03TransAddTitleAndDepartment_FailBoth() {
		System.out.printf("%s()%n", "TransAddTitleAndDepartment_FailBoth");

		Title title = new Title(1, "인턴"); // 실행불가능한 문장(duplicated)
		Department dept = new Department(1, "비상계획부", 10); // 실행가능한 문장

		String res = service.transAddTitleAndDepartment(title, dept);
		Assert.assertEquals("rollback", res);
	}
	
	@Test
	public void test04TransAddTitleAndDepartment_Success() {
		System.out.printf("%s()%n", "TransAddTitleAndDepartment_Success");

		Title title = new Title(6, "인턴"); // 실행불가능한 문장(duplicated)
		Department dept = new Department(5, "비상계획부", 10); // 실행가능한 문장

		String res = service.transAddTitleAndDepartment(title, dept);
		Assert.assertEquals("commit", res);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void test05TransRemoveTitleAndDepartment_FailTitle() {
		System.out.printf("%s()%n", "TransRemoveTitleAndDepartment_FailTitle");
		
		Title title = new Title(0); 
		Department dept = new Department(5); 

		int res = service.transRemoveTitleAndDepartment(title, dept);
		Assert.assertEquals(1, res); // << 중간에 exception으로 걸리는게 아니라 res 누적이라서 1이 출력될 것
		System.out.println(res);
	}

	@Test
	public void test06TransRemoveTitleAndDepartment_FailDept() {
		System.out.printf("%s()%n", "TransRemoveTitleAndDepartment_FailDept");
		
		Title title = new Title(6); 
		Department dept = new Department(0); 

		int res = service.transRemoveTitleAndDepartment(title, dept);
		Assert.assertEquals(1, res); 
		System.out.println(res);
	
	}
	
	@Test
	public void test07TransRemoveTitleAndDepartment_FailBoth() {
		System.out.printf("%s()%n", "TransRemoveTitleAndDepartment_FailBoth");
		
		Title title = new Title(0); 
		Department dept = new Department(0); 

		int res = service.transRemoveTitleAndDepartment(title, dept);
		Assert.assertEquals(0, res); 
		System.out.println(res);
	}
	
	@Test
	public void test08TransRemoveTitleAndDepartment_Success() {
		System.out.printf("%s()%n", "TransRemoveTitleAndDepartment_Success");
		
		Title title = new Title(6); 
		Department dept = new Department(5);

		int res = service.transRemoveTitleAndDepartment(title, dept);
		Assert.assertEquals(2, res);
		System.out.println(res);
	}

}
