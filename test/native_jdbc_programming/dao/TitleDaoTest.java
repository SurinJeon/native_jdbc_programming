package native_jdbc_programming.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_programming.dao.impl.TitleDaoImpl;
import native_jdbc_programming.dto.Title;


//test method 순서 정해주기

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 알파벳 순으로 수행하겠다
public class TitleDaoTest {

	private static TitleDao dao = TitleDaoImpl.getInstance(); // TitleDao는 부모기 때문에 부모를 참조해서 자손들로 접근해야 더 코드를 쓰기 용이함 
	
	@After
	public void tearDown() throws Exception {
		System.out.println();
	} // 각 테스트 끝나고 구분 위해 공백 찍는 용도로 긁어옴
	
	
	@Test
	public void test04SelectTitleByAll() {
		System.out.printf("%s() %n", "testSelectTitleByAll");
		List<Title> titleList = dao.selectTitleByAll();
		Assert.assertNotNull(titleList); // null 이 아니면 데이터가 있다는 뜻이므로 성공임
		
		titleList.stream().forEach(System.out::println); // << 아래 for문과 같은 출력문을 찍는 문장임 
/*		for(Title t : titleList) {
			System.out.println(t);
		}
*/	}

	@Test
	public void test05SelectTitleByNo() {
		System.out.printf("%s() %n", "ttestSelectTitleByNo");
		Title title = new Title(3);
		Title searchTitle = dao.selectTitleByNo(title);
		Assert.assertNotNull(searchTitle);
		System.out.println(searchTitle);
		
	}

	@Test
	public void test01InsertTitle() {
		System.out.printf("%s() %n", "testInsertTitle");
		Title newTitle = new Title(6, "인턴");
		int res = dao.insertTitle(newTitle); // 반영된 행이 하나면 그건 잘 된거임
		Assert.assertEquals(1, res);
		System.out.println(res);
		System.out.println(dao.selectTitleByNo(newTitle));
	}

	@Test
	public void test02UpdateTitle() {
		System.out.printf("%s() %n", "testUpdateTitle");
		Title newTitle = new Title(6, "계약직");
		int res = dao.updateTitle(newTitle); // 반영된 행이 하나면 그건 잘 된거임
		Assert.assertEquals(1, res);
		System.out.println(res);
		System.out.println(dao.selectTitleByNo(newTitle));
	
	}

	@Test
	public void test03DeleteTitle() {
		System.out.printf("%s() %n", "testDeleteTitle");
		int res = dao.deleteTitle(6);
		Assert.assertEquals(1, res);
		System.out.println(res);
		dao.selectTitleByAll().stream().forEach(System.out::println);
	}

}
