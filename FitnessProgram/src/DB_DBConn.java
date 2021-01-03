import java.sql.Connection;
import java.sql.DriverManager;

public class DB_DBConn {// �̱���(�̱۷���) ����
	private static Connection dbConn = null;

	public static Connection getConnection() {
		// �� �� ����� ��ü�� ��� ���. ������� ���� ��츸 ���� �õ�.
		if (dbConn == null) {
			try {
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				String user = "six";
				String pwd = "1234";
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				System.out.println("[DB ���� ON - ���� DB : " + user + "]");
			} catch (Exception e) {
				System.out.println("���� : " + e.toString());
			}
		}

		return dbConn;
	}

	// getConnection() �޼ҵ� �����ε�
	public static Connection getConnection(String url, String user, String pwd) {
		if (dbConn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				System.out.println("[DB ���� ON - ���� DB : " + user + "]");
			} catch (Exception e) {
				System.out.println("���� : " + e.toString());
			}
		}

		return dbConn;
	}

	public static void close() {
		if (dbConn != null) {
			try {
				// isClose()�� ���� ���� Ȯ���ϴ� �޼ҵ�
				if (!dbConn.isClosed())
					dbConn.close();
				System.out.println("[DB ���� OFF]");
			} catch (Exception e) {
				System.out.println("���� : " + e.toString());
			}
		}

		dbConn = null;
	}

}
