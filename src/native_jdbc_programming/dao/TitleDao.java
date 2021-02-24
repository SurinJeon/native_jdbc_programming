package native_jdbc_programming.dao;

import java.util.List;

import native_jdbc_programming.dto.Title;

/**
 * @author lenovo
 * 1. Create(insert)
 * 2. Read (select, select where) // 전체 검색과 조건에 따른 검색해서 두개!
 * 3. Update(update)
 * 4. Delete(delete)
 * ArrayList를 쓸 것
 */
public interface TitleDao {

	// 모두 추상 method 
	List<Title> selectTitleByAll(); // ArrayList 대신 부모인 List로 받아옴
	Title selectTitleByNo(Title title); // select where 절은 검색 결과가 하나만 나올것이기 때문에 리스트가 아니라 객체하나로 받는다 (그래서 Title)
	// parameter를 Title로 넘겨주는게 객체지향
	int insertTitle(Title title); 
	int updateTitle(Title title); 
	int deleteTitle(int titleNo); // 이건 int로 작성해보기
	
}



