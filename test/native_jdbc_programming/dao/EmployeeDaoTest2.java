package native_jdbc_programming.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_programming.dao.impl.EmployeeDaoImpl;
import native_jdbc_programming.dto.Department;
import native_jdbc_programming.dto.Employee;
import native_jdbc_programming.dto.Title;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest2 {

	private static EmployeeDao dao = EmployeeDaoImpl.getInstance();

	@After
	public void tearDown() throws Exception {
		System.out.println();
	}

	@Test
	public void test04SelectEmployeeByAll() {
		System.out.printf("%s() %n", "testSelectEmployeeByAll");

		List<Employee> list = dao.selectEmployeeByAll();
		Assert.assertNotNull(list);

		for (Employee e : list) {
			System.out.println(e);
		}
	}

	@Test
	public void test05SelectEmployeeByNo() {
		System.out.printf("%s() %n", "testSelectEmployeeByNo");
		Employee emp = new Employee(1003);
		Employee searchEmp = dao.selectEmployeeByNo(emp);
		Assert.assertNotNull(emp);

		System.out.println(searchEmp);

	}

	@Test
	public void test01InsertEmployee() {
		System.out.printf("%s() %n", "testInsertEmployee");

		Employee newEmp = new Employee(1004, "전수린", new Title(3), new Employee(4377), 3000000, new Department(2));

		int res = dao.insertEmployee(newEmp);

		Assert.assertEquals(1, res);
		System.out.println(res);

		System.out.println(dao.selectEmployeeByNo(newEmp));

	}

	@Test
	public void test02UpdateEmployee() {
		System.out.printf("%s() %n", "testUpdateEmployee");

		Employee upEmp = new Employee(1004, "짱수린", new Title(3), new Employee(4377), 3000000, new Department(2));

		int res = dao.updateEmployee(upEmp);
		Assert.assertEquals(1, res);
		System.out.println(res);

		System.out.println(dao.selectEmployeeByNo(upEmp));

	}

	@Test
	public void test03DeleteEmployee() {
		System.out.printf("%s() %n", "testDeleteEmployee");
		Employee newEmp = new Employee(1004, "전수린", new Title(3), new Employee(4377), 3000000, new Department(2));

		int res = dao.deleteEmployee(newEmp);
		Assert.assertEquals(1, res);
		System.out.println(res);

		dao.selectEmployeeByAll().stream().forEach(System.out::println);
	}

//	@Test
//	public void testSelectEmployeeByView() {
//
//		System.out.printf("%s() %n", "testSelectEmployeeByView");
//
//		List<Employee> list = dao.selectEmployeeByView();
//		Assert.assertNotNull(list);
//
//		for (Employee e : list) {
//			System.out.println(e.toString2());
//		}
//
//	}

}
