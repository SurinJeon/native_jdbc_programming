package native_jdbc_programming.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
	
//	// test용 main
//	public static void main(String[] args) {
//		Connection con = JdbcUtil.getConnection();
//		System.out.println("con > " + con); // 찍어서 출력 되는지 확인해야함
//	}

	// 여기서는 커넥션만 가져올것임
	// properties 던져주면 여기서 알아서 커넥션 처리해서 커넥션 된 걸 던져주는 것
	// try-catch-Resource문 활용
	public static Connection getConnection() {
		String propertiesPath = "db.properties"; // resources 밑에 있는 properties 이름이랑 똑같아야지 받아올수있음!!!!!!!!!
		Connection con = null;
		try (InputStream in = ClassLoader.getSystemResourceAsStream(propertiesPath)){ // 읽어들어오는 것이기 때문에 Input임 
			Properties prop = new Properties();
			prop.load(in); // db.properties안에 있는 내용일 일단 불러옴
			con = DriverManager.getConnection(prop.getProperty("url"), prop); // properties 이용해서 Connection하겠다
			
			
			/*
			 확인용도로 출력 찍어본거 주석처리
			System.out.println("prop > " + prop); // 제대로 불러왔는지 확인 차 출력문 찍어보기 (이건 우리가 직접 찍은것)
			
			for(Entry<Object, Object> e : prop.entrySet()) { // for문 돌려서 Entryset의 key value를 찍어보기
				System.out.printf("%s -> %s %n", e.getKey(), e.getValue());
			}
			
			// EntrySet의 key를 통해서 value 가져오는 법
			for(Object key : prop.keySet()) {
				System.out.print(key + " >> ");
				System.out.println(prop.get(key)); // get(key) 하니까 value가 나옴
			}
			
			*/
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}

}
