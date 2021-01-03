import java.sql.Connection;
import java.sql.DriverManager;

public class DB_DBConn {// 싱글톤(싱글레톤) 패턴
	private static Connection dbConn = null;

	public static Connection getConnection() {
		// 한 번 연결된 객체를 계속 사용. 연결되지 않은 경우만 연결 시도.
		if (dbConn == null) {
			try {
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				String user = "six";
				String pwd = "1234";
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				System.out.println("[DB 연결 ON - 접속 DB : " + user + "]");
			} catch (Exception e) {
				System.out.println("예외 : " + e.toString());
			}
		}

		return dbConn;
	}

	// getConnection() 메소드 오버로딩
	public static Connection getConnection(String url, String user, String pwd) {
		if (dbConn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				System.out.println("[DB 연결 ON - 접속 DB : " + user + "]");
			} catch (Exception e) {
				System.out.println("예외 : " + e.toString());
			}
		}

		return dbConn;
	}

	public static void close() {
		if (dbConn != null) {
			try {
				// isClose()는 연결 상태 확인하는 메소드
				if (!dbConn.isClosed())
					dbConn.close();
				System.out.println("[DB 연결 OFF]");
			} catch (Exception e) {
				System.out.println("예외 : " + e.toString());
			}
		}

		dbConn = null;
	}

}
