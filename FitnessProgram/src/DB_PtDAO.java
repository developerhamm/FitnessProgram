import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_PtDAO {
	
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	
	public DB_PtDAO() {
		con = DB_DBConn.getConnection();
	}
	
	// DB의 전체 리스트 가져온다
	public ArrayList<DB_PtDTO> ptShowList() {
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		
		try {
			
			String sql = "select e_id, e_name, time, e_teacher," + 
					" price from pt order by to_number(e_id)";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			
			while(rs.next()) {
				DB_PtDTO dto = new DB_PtDTO();
				
				dto.e_id = rs.getString("e_id");
				dto.e_name = rs.getString("e_name");
				dto.time = rs.getString("time");
				dto.e_teacher = rs.getString("e_teacher");
				dto.price = rs.getInt("price");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 검색 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// 검색할때 콤보박스의 리스트를 클릭하면 오름차순으로 정렬
	public ArrayList<DB_PtDTO> comboSeletedShow(String combo) {
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		
		try {
			
			
			String sql = "select e_id, e_name, time, e_teacher, "
					+ "price from pt order by " + combo + "";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				DB_PtDTO dto = new DB_PtDTO();
				
				dto.e_id = rs.getString("e_id");
				dto.e_name = rs.getString("e_name");
				dto.time = rs.getString("time");
				dto.e_teacher = rs.getString("e_teacher");
				dto.price = rs.getInt("price");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 콤보박스 리스트 출력 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return list;	
	}
	
	// 회원 가입 폼의 코치 선택할때 중복된 코치의 값 제거
	public ArrayList<DB_PtDTO> ptSelectTeacher() {
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		
		try {
			String sql = "select distinct e_teacher from pt";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			
			while(rs.next()) {
				DB_PtDTO dto = new DB_PtDTO();
				
				dto.e_teacher = rs.getString("e_teacher");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 선생 출력 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// Insert
	public int ptInsert(DB_PtDTO ptInsert) {
		
		int result = 0;
		try {
			System.out.println("연결 성공");
			
			String sql = "insert into pt (e_id, e_name, time, e_teacher, price)" + 
					"values (seq_ptid.nextval,?,?,?,?)";
			
			pst = con.prepareStatement(sql);
			
			pst.setString(1, ptInsert.getE_name());
			
			pst.setString(2, ptInsert.getTime());
			
			pst.setString(3, ptInsert.getE_teacher());
			
			pst.setInt(4, ptInsert.getPrice());
			
			System.out.println(ptInsert.toString());
			
			result = pst.executeUpdate();
			System.out.println(result + " : 행 입력");
			
			DB_PtDTO dto = new DB_PtDTO();
			System.out.println("insert to : " + dto.toString());
		} catch (Exception e) {
			System.out.println(e + " : insert 실패");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return result;	
	}
	
	// Update
	public int ptUpdate(DB_PtDTO ptupdate) {
		int result = 0;
		
		try {
			String sql = "update pt set e_name=?, time=?, e_teacher=?, price=?"
					+ " where e_id=?";
			pst = con.prepareStatement(sql);
			
			pst.setString(1, ptupdate.getE_name());
			pst.setString(2, ptupdate.getTime());
			pst.setString(3, ptupdate.getE_teacher());
			pst.setInt(4, ptupdate.getPrice());
			// to_cahr 문자 그대로 가져오기때문에 서브스트링으로 id 번호 잘라줘야됨
			pst.setString(5, ptupdate.getE_id());
			
			System.out.println(ptupdate.toString());
			System.out.println(pst);

			result = pst.executeUpdate();
			System.out.println(result + " : 행 입력");
		} catch (Exception e) {
			System.out.println(e + " : update 실패");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	// Delete
	public int ptDelete(DB_PtDTO ptDelete) {
		int result = 0;
		try {
			String sql = "delete from pt where e_id=?";
			
			pst = con.prepareStatement(sql);
			
			pst.setString(1, ptDelete.getE_id());
			
			System.out.println(sql + " : 삭제 sql문");
			
			result = pst.executeUpdate();
			System.out.println(result + " : 행 실행 완료");
		} catch (Exception e) {
			System.out.println(e + " : Delete 실패");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	//total 값 받아오기
	public int ptTotalshow() {
		int pttotal = 0;
		
		try {
			
			String sql = "select count(e_id) from pt";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			
			while(rs.next()) {
				pttotal = rs.getInt("count(e_id)");
			}
		} catch (Exception e) {
			System.out.println(e + " : 검색 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return pttotal;
	}
	
	// 검색
	public ArrayList<DB_PtDTO> ptSearch(String comboName, String word) {
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		
		try {
			
			String sql = "select * from pt where " + comboName.trim()
			+ " like '%" + word.trim() + "%'";
			
			System.out.println(sql);
			
			st = con.createStatement();
			rs = st.executeQuery(sql);

			
			while(rs.next()) {
				DB_PtDTO dto = new DB_PtDTO();
				
				dto.e_id = rs.getString("e_id");
				dto.e_name = rs.getString("e_name");
				dto.time = rs.getString("time");
				dto.e_teacher = rs.getString("e_teacher");
				dto.price = rs.getInt("price");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 검색 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); st.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// 운동 등록할때 같은 값으로 선행 등록 되어 있는지 체크
	public ArrayList<DB_PtDTO> InsertCheck(String ename, String time, String teacher, int price) {
		
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		try {
			System.out.println("연결 성공");
			String sql = "select e_id from pt " 
			+ " where e_name='" + ename + "'" + " and time='" + time + "' and e_teacher= '" + teacher + "' and price = " + price +""
			+ " or time='" + time + "' and e_teacher= '" + teacher + "'";
			pst = con.prepareStatement(sql);
			System.out.println(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				DB_PtDTO dto = new DB_PtDTO();
				dto.e_id = rs.getString(1);
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e + " : Insert Check 안됨");
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return list;
	}
	
	// 운동 수정할때 같은 값으로 선행 등록 되어 있는지 체크
	public ArrayList<DB_PtDTO> UpdateCheck(String ename, String time, String teacher, int price) {
		
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		try {
			System.out.println("연결 성공");
			String sql = "select e_id from pt " 
			+ " where e_name='" + ename + "'" + " and time='" + time + "' and e_teacher= '" + teacher + "' and price = " + price + "";
			pst = con.prepareStatement(sql);
			System.out.println(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				DB_PtDTO dto = new DB_PtDTO();
				dto.e_id = rs.getString(1);
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e + " : Insert Check 안됨");
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return list;
	}
	
	// 운동 ID 조회
		public ArrayList<DB_PtDTO> ptSelectE_id() {
			ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
			
			try {
				
				String sql = "select distinct e_id from pt order by to_number(e_id)";
				
				System.out.println(sql);
				
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();

				
				while(rs.next()) {
					DB_PtDTO dto = new DB_PtDTO();
					
					dto.e_teacher = rs.getString("e_id");
					
					System.out.println("select : " + dto.toString());
						
					list.add(dto);
				}
			} catch (Exception e) {
				System.out.println(e + " : e_id 출력 실패");
				e.printStackTrace();
			} finally {
				try {
					rs.close(); pst.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return list;
		}
	}
