import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class DB_ReJTable2DAO {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	public DB_ReJTable2DAO() {
		con = DB_DBConn.getConnection();
	}

	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}

	////////////////////////////// ++++++++++++++++++++++++++++////////////////

	// 조회(관리자)
	public void showListadmre(DefaultTableModel t_model) {
		try {
			ps = con.prepareStatement("select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_teacher, p.price"
					+ " from re r join member m on(r.rm_id = m.m_id) join pt p on(re_id = p.e_id)"
					+ " ORDER BY TO_NUMBER(r.r_id)");
			rs = ps.executeQuery();
			
			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };
				t_model.addRow(data); // DefaultTableModel에 레코드 추가
			}
		} catch (SQLException e) {
			System.out.println(e + "=> showListadmre fail");
		} finally {
			dbClose();
		}
	}

	// 조회(멤버)
	public void showListmemre(DefaultTableModel t_model, String user) {
		try {
			ps = con.prepareStatement("select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_teacher, p.price"
					+ " from re r join member m on(r.rm_id = m.m_id) join pt p on(re_id = p.e_id) " + " where rm_id = '"
					+ user + "' ORDER BY TO_NUMBER(r.r_id)");
			rs = ps.executeQuery();

			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };
				t_model.addRow(data); 
			}
		} catch (SQLException e) {
			System.out.println(e + "=> showListmemre fail");
		} finally {
			dbClose();
		}
	}

	// 검색(관리자)
	public void searchadmre(DefaultTableModel redt2, String fieldName, String word) {
		String sql = "select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_teacher, p.price"
				+ " from re r join member m on(r.rm_id = m.m_id) join pt p on(re_id = p.e_id)" + " WHERE "
				+ fieldName.trim() + " LIKE '%" + word.trim() + "%' ORDER BY TO_NUMBER(r.r_id)";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			for (int i = 0; i < redt2.getRowCount();) {
				redt2.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };

				redt2.addRow(data);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> Searchadmre fail");
		} finally {
			dbClose();
		}
	}

	// 검색(멤버)
	public void searchmemre(DefaultTableModel redt2, String fieldName, String word, String user) {

		String sql = "select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_teacher, p.price"
				+ " from re r join member m on(r.rm_id = m.m_id) join pt p on(re_id = p.e_id)" + " WHERE "
				+ fieldName.trim() + " LIKE '%" + word.trim() + "%' AND rm_id = '" + user
				+ "' ORDER BY TO_NUMBER(r.r_id)";

		System.out.println(sql);

		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			for (int i = 0; i < redt2.getRowCount();) {
				redt2.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };
				redt2.addRow(data);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> Searchmemre fail");
		} finally {
			dbClose();
		}
	}

	// 추가
	public int insertre(Fr_D_ReJTable2DialogGUI re) {
		int result = 0;
		try {
			ps = con.prepareStatement("insert into re (r_id, rm_id, re_id)" + "values (seq_reid.nextval,?,?)");
			ps.setString(1, re.cbM_id.getSelectedItem().toString());
			ps.setString(2, re.cbE_id.getSelectedItem().toString());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> insertre fail");
		} finally {
			dbClose();
		}
		return result;
	}

	// 수정
	public int updatere(Fr_D_ReJTable2DialogGUI re) {
		int result = 0;
		String sql = "UPDATE re SET rm_id=?, re_id=? WHERE r_id=?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, re.cbM_id.getSelectedItem().toString());
			ps.setString(2, re.cbE_id.getSelectedItem().toString());
			ps.setString(3, re.r_id.getText().trim());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> updatere fail");
		} finally {
			dbClose();
		}

		return result;
	}

	// 삭제
	public int deletere(String r_id) {
		int result = 0;
		try {
			ps = con.prepareStatement("delete re where r_id = ? ");
			ps.setString(1, r_id.trim());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> deletere fail");
		} finally {
			dbClose();
		}

		return result;
	}

	////////////////////////////// ++++++++++++++++++++++++++++////////////////

	// 회원 삭제시 해당 회원의 예약 정보도 함께 삭제하기 위한 메소드
	public int reDeleteByRm_id(String rm_id) {
		int result = 0;
		try {
			ps = con.prepareStatement("delete re where rm_id = ? ");
			ps.setString(1, rm_id.trim());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> reDelete fail");
		} finally {
			dbClose();
		}

		return result;
	}
	
	// 총 예약개수 카운트
		public int selectTotalReCnt() {
			int totalReCount = 0;
			ps = null;
			rs = null;
			
			System.out.println("	[ReDAO selectTotalReCnt() 수행]");
			
			try {
				String sql = "SELECT COUNT(r_id) FROM re";
				
				System.out.println("	[실행 옵션 : TotalReCnt ==> 실행된 SQL문 : " + sql + "]");
				
				ps = con.prepareStatement(sql);
				
				rs = ps.executeQuery();
				
				while (rs.next()) {
					totalReCount = rs.getInt("COUNT(r_id)");
				}
				System.out.println("	[" + rs.getFetchSize() +"개 데이터 선택됨]");
				System.out.println();
			} catch (Exception e) {
				System.err.println("[ReDAO 예외 : selectTotalReCnt() 실패]");
				e.printStackTrace();
			} finally {
				try {
					if(!rs.equals(null)) {
						rs.close();
					}
					if(!ps.equals(null)) {
						ps.close();
					}
				} catch (SQLException e) {
					System.err.println("[ReDAO 예외 : 객체 닫기 실패]");
					e.printStackTrace();
				}
			}
			return totalReCount;
		}
		
		//  자기 예약개수 카운트
			public int selectMemReCnt(String userID) {
				int totalReCount = 0;
				ps = null;
				rs = null;
				
				System.out.println("	[ReDAO selectTotalReCnt() 수행]");
				
				try {
					String sql = ("SELECT COUNT(re.r_id) FROM re join member on(re.rm_id = member.m_id) where re.rm_id = '" + userID + "'");
					
					System.out.println("	[실행 옵션 : MemReCnt ==> 실행된 SQL문 : " + sql + "]");
					
					ps = con.prepareStatement(sql);
					
					rs = ps.executeQuery();
					
					while (rs.next()) {
						totalReCount = rs.getInt("COUNT(re.r_id)");
					}
					System.out.println("	[" + rs.getFetchSize() +"개 데이터 선택됨]");
					System.out.println();
				} catch (Exception e) {
					System.err.println("[ReDAO 예외 : selectMemReCnt() 실패]");
					e.printStackTrace();
				} finally {
					try {
						if(!rs.equals(null)) {
							rs.close();
						}
						if(!ps.equals(null)) {
							ps.close();
						}
					} catch (SQLException e) {
						System.err.println("[ReDAO 예외 : 객체 닫기 실패]");
						e.printStackTrace();
					}
				}
				return totalReCount;
			}


	//////////////// 상품 테이블에서 예약 테이블에 추가하는 SQL 메소드들////////////////////////

	// 임시 예약 테이블 sql 메서드
	// 운동 삭제되면 예약테이블도 삭제 메서드
	public int ptreDelete(String ptreText) {

		int result = 0;
		try {
			String sql = "delete from re where re_id=?";

			ps = con.prepareStatement(sql);

			System.out.println(ptreText);

			ps.setString(1, ptreText.trim());

			System.out.println(sql + " : 삭제 sql문");

			result = ps.executeUpdate();
			System.out.println(result + " : 행 실행 완료 ㅁㄴㅇㅁㄴㅇ");

		} catch (Exception e) {
			System.out.println(e + " : Delete 실패");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return 0;
	}

	public int ptReinsert(String loginUser, String ptText) {
		int result = 0;

		try {
			System.out.println("연결 성공");

			System.out.println(loginUser);
			System.out.println(ptText);

			String sql = "insert into re (r_id, rm_id, re_id)" + "values(seq_reid.nextval,'" + loginUser + "'," + "'"
					+ ptText + "')";
			ps = con.prepareStatement(sql);

			result = ps.executeUpdate();
			System.out.println(sql + " : sql문");
			System.out.println(result + " : 개의 행 실행완료");

		} catch (Exception e) {
			System.out.println(e + "ReInset 실패");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	// 임시 DB의 중복된 pt아이디 체크해서 중복된 값 체크하는 메서드
	public ArrayList<DB_ReDTO> ptInsertCheck(String loginUser, String ptText) {
		ArrayList<DB_ReDTO> list = new ArrayList<DB_ReDTO>();
		try {
			String sql = "select r.r_id, r.rm_id, p.time"
					+ " from re r join member m on(r.rm_id = m.m_id) join pt p on(re_id = p.e_id)" + " WHERE rm_id='"
					+ loginUser + "'" + " and time=" + "(select time from pt where e_id = '" + ptText + "')";
			ps = con.prepareStatement(sql);
			System.out.println(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				DB_ReDTO dto = new DB_ReDTO();
				dto.r_id = rs.getString(1);
				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e + " : Insert Check 안됨");
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
}
