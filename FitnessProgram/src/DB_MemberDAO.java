import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_MemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// 회원 테이블 DAO 생성자
	public DB_MemberDAO() {
		conn = DB_DBConn.getConnection();	// DBConn에서 생성한 con 객체를 사용
		pstmt = null;
		rs = null;
	}
	
	// ~~~~~~~~~ 관리자용 SQL 메소드 ~~~~~~~~~ //
	// 회원 ID의 존재 여부 조회
	public ArrayList<DB_MemberDTO> selectIdCheck(String userId) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectIdCehck() 수행]");
		
		try {
			String sql = "SELECT m_id FROM member WHERE m_id = ?";
			
			System.out.println("	[실행 옵션 : IdCehck ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				
				System.out.println("	" + dto.toString());
				list.add(dto);
			}
			System.out.println("	[" + list.size()+"개 데이터 선택됨]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : selectIdCehck() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!rs.equals(null)) {
					rs.close();
				}
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 회원 ID와 PWD의 매칭 여부 조회
	public ArrayList<DB_MemberDTO> selectPwdCheck(String userId, String userPwd) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectPwdCheck() 수행]");
		
		try {
			String sql = "SELECT m_id FROM member WHERE m_id = ? AND pwd = ?";
			
			System.out.println("	[실행 옵션 : PwdCheck ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				
				System.out.println("	" + dto.toString());
				list.add(dto);
			}
			System.out.println("	[" + list.size()+"개 데이터 선택됨]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : selectPwdCheck() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!rs.equals(null)) {
					rs.close();
				}
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 특정 회원의 전체 정보 조회
	public ArrayList<DB_MemberDTO> selectGetMember(String userId) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectGetMember() 수행]");
		
		try {
			String sql = "SELECT * FROM member WHERE m_id = ?";
			
			System.out.println("	[실행 옵션 : GetMember ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setM_name(rs.getString("m_name"));
				dto.setBirth(rs.getString("birth"));
				dto.setGender(rs.getString("gender"));
				dto.setPhone(rs.getString("phone"));
				dto.setAddr(rs.getString("addr"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setM_teacher(rs.getString("m_teacher"));
				
				System.out.println("	" + dto.toString());
				list.add(dto);
			}
			System.out.println("	[" + list.size()+"개 데이터 선택됨]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : selectGetMember() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!rs.equals(null)) {
					rs.close();
				}
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 전체 회원 조회(관리자)
	public ArrayList<DB_MemberDTO> selectAdminShowMember(String orderBy) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectAdminShowMember() 수행]");
		
		try {
			String sql = "SELECT m_id, pwd, m_name, NVL(birth, '(해당 없음)') as birth, NVL(gender, '(해당 없음)') as gender, "
					+ "NVL2(phone, REGEXP_REPLACE(REGEXP_REPLACE (phone, '[^[:digit:]]'), '(^02|050[[:digit:]]{1}|[[:digit:]]{3})([[:digit:]]{3,4})([[:digit:]]{4})','\\1-\\2-\\3'), '(해당 없음)') as phone, "
					+ "NVL(addr, '(해당 없음)') as addr, TO_CHAR(joindate, '\"\"YYYY\"년 \"MM\"월 \"DD\"일 \"hh24\"시 \"mi\"분\"') as joindate, NVL(m_teacher, '(해당 없음)') as m_teacher "
					+ "FROM member ORDER BY " + orderBy;
			
			System.out.println("	[실행 옵션 : AdminShowMember ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setM_name(rs.getString("m_name"));
				dto.setBirth(rs.getString("birth"));
				dto.setGender(rs.getString("gender"));
				dto.setPhone(rs.getString("phone"));
				dto.setAddr(rs.getString("addr"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setM_teacher(rs.getString("m_teacher"));
				
				System.out.println("	" + dto.toString());
				list.add(dto);
			}
			System.out.println("	[" + list.size()+"개 데이터 선택됨]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : selectAdminShowMember() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!rs.equals(null)) {
					rs.close();
				}
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 전체 회원 검색(관리자)
	public ArrayList<DB_MemberDTO> selectAdminShowMemberSearch(String key, String word, String orderBy) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectAdminShowMemberSearch() 수행]");
		
		try {
			String sql = "SELECT m_id, pwd, m_name, NVL(birth, '(해당 없음)') as birth, NVL(gender, '(해당 없음)') as gender, "
					+ "NVL2(phone, REGEXP_REPLACE(REGEXP_REPLACE (phone, '[^[:digit:]]'), '(^02|050[[:digit:]]{1}|[[:digit:]]{3})([[:digit:]]{3,4})([[:digit:]]{4})','\\1-\\2-\\3'), '(해당 없음)') as phone, "
					+ "NVL(addr, '(해당 없음)') as addr, TO_CHAR(joindate, '\"\"YYYY\"년 \"MM\"월 \"DD\"일 \"hh24\"시 \"mi\"분\"') as joindate, NVL(m_teacher, '(해당 없음)') as m_teacher "
					+ "FROM member WHERE LOWER(" + key + ") like LOWER(?) ORDER BY " + orderBy;
			
			System.out.println("	[실행 옵션 : AdminShowMemberSearch ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+word+"%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setM_name(rs.getString("m_name"));
				dto.setBirth(rs.getString("birth"));
				dto.setGender(rs.getString("gender"));
				dto.setPhone(rs.getString("phone"));
				dto.setAddr(rs.getString("addr"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setM_teacher(rs.getString("m_teacher"));
				
				System.out.println("	" + dto.toString());
				list.add(dto);
			}
			System.out.println("	[" + list.size()+"개 데이터 선택됨]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : selectAdminShowMemberSearch() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!rs.equals(null)) {
					rs.close();
				}
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return list;
	}

	// 회원 삭제(관리자)
	public int deleteMember(String userId) {
		int result = 0;
		pstmt = null;
		
		System.out.println("	[MemberDAO deleteMember() 수행]");
		
		try {
			String sql = "DELETE FROM member WHERE m_id = ?";
			System.out.println("	[실행 옵션 : deleteMember ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : deleteMember() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 회원 정보 수정(관리자)
	public int updateMember(DB_MemberDTO user) {
		int result = 0;
		pstmt = null;
		
		System.out.println("	[MemberDAO updateMember() 수행]");
		
		try {
			String sql = "UPDATE member SET pwd = ?, m_name = ?, birth = ?, gender = ?, phone = ?, addr = ?, m_teacher = ? WHERE m_id = ?";
			System.out.println("	[실행 옵션 : updateMember ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			
			DB_MemberDTO temp = user;
			pstmt.setString(1, temp.getPwd());
			pstmt.setString(2, temp.getM_name());
			pstmt.setString(3, temp.getBirth());
			pstmt.setString(4, temp.getGender());
			pstmt.setString(5, temp.getPhone());
			pstmt.setString(6, temp.getAddr());
			pstmt.setString(7, temp.getM_teacher());
			pstmt.setString(8, temp.getM_id());
			
			result = pstmt.executeUpdate();
			
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : updateMember() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 회원 정보 수정(강사 삭제 대응)
		public int updateMemberbyDelTchr(String teacher) {
			int result = 0;
			pstmt = null;
			
			System.out.println("	[MemberDAO updateMember() 수행]");
			
			try {
				String sql = "UPDATE member SET m_teacher = null "
						+ "WHERE m_teacher = ?"
						+ "AND ((SELECT COUNT(*) FROM pt WHERE e_teacher = ?) = 0)";
				System.out.println("	[실행 옵션 : updateMember ==> 실행된 SQL문 : " + sql + "]");
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, teacher);
				pstmt.setString(2, teacher);
				
				result = pstmt.executeUpdate();
				
				System.out.println();
			} catch (Exception e) {
				System.err.println("[MemberDAO 예외 : updateMember() 실패]");
				e.printStackTrace();
			} finally {
				try {
					if(!pstmt.equals(null)) {
						pstmt.close();
					}
				} catch (SQLException e) {
					System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
					e.printStackTrace();
				}
			}
			return result;
		}
	
	// 회원 가입
	public int insertMember(DB_MemberDTO user) {
		int result = 0;
		pstmt = null;
		
		System.out.println("	[MemberDAO insertMember() 수행]");
		
		try {
			String sql = "INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
			System.out.println("	[실행 옵션 : insertMember ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			
			DB_MemberDTO temp = user;
			pstmt.setString(1, temp.getM_id());
			pstmt.setString(2, temp.getPwd());
			pstmt.setString(3, temp.getM_name());
			pstmt.setString(4, temp.getBirth());
			pstmt.setString(5, temp.getGender());
			pstmt.setString(6, temp.getPhone());
			pstmt.setString(7, temp.getAddr());
			pstmt.setString(8, temp.getM_teacher());
			
			result = pstmt.executeUpdate();
			
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : insertMember() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 전체 회원수 조회
	public int selectTotalMemberCnt() {
		int totalMemberCount = 0;
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectTotalMemberCnt() 수행]");
		
		try {
			String sql = "SELECT COUNT(m_id) FROM member";
			
			System.out.println("	[실행 옵션 : TotalMemberCnt ==> 실행된 SQL문 : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				totalMemberCount = rs.getInt("COUNT(m_id)");
			}
			System.out.println("	[" + rs.getFetchSize() +"개 데이터 선택됨]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO 예외 : selectTotalMemberCnt() 실패]");
			e.printStackTrace();
		} finally {
			try {
				if(!rs.equals(null)) {
					rs.close();
				}
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO 예외 : 객체 닫기 실패]");
				e.printStackTrace();
			}
		}
		return totalMemberCount;
	}
		
	// ~~~~~~~~~ 일반 회원용 SQL 메소드 ~~~~~~~~~ //
	public ArrayList<DB_MemberDTO> selectUserInfo(String user) {
	      ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         String sql = "SELECT * FROM member where m_id = '" + user+"'";
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setM_name(rs.getString("m_name"));
				dto.setBirth(rs.getString("birth"));
				dto.setGender(rs.getString("gender"));
				dto.setPhone(rs.getString("phone"));
				dto.setAddr(rs.getString("addr"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setM_teacher(rs.getString("m_teacher"));
	            list.add(dto);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            pstmt.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return list;
	   }
	
	public ArrayList<DB_PtDTO> selectTotalPrice(String user) {
	      ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         String sql = "SELECT sum(price) FROM re join pt on(re_id=e_id) where rm_id = '" + user+"'";
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while (rs.next()) {
	        	 DB_PtDTO dto = new DB_PtDTO();
				dto.setPrice(rs.getInt("sum(price)"));
	            list.add(dto);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            pstmt.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return list;
	   }

	public int memberdelete(DB_MemberDTO userId) {
		int result = 0;
		PreparedStatement pstmt = null;
				
		try {
			String sql = "DELETE FROM member "
					+ "WHERE m_id = '" + userId.getM_id() + "'";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("회원 탈퇴 실패 " + e);
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
		
	public int rememberdelete(DB_MemberDTO userId) {
		int result = 0;
		PreparedStatement pstmt = null;
				
		try {
			String sql = "DELETE FROM re "
					+ "WHERE rm_id = '" + userId.getM_id() + "'";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("회원 탈퇴 실패 " + e);
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	
	// ~~~~~~~~~ 예약 테이블 삽입용 SQL 메소드 ~~~~~~~~~ //
	// 회원 ID 조회(중복 예약 방지)
	public ArrayList<DB_MemberDTO> memberSelectM_id() {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		
		try {
			conn = DB_DBConn.getConnection();
			
			String sql = "SELECT distinct m_id FROM member ORDER BY m_id";
			
			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			
			while(rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				
				dto.m_id = rs.getString("m_id");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : e_id 출력 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
}