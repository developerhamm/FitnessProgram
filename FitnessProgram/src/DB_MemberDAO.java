import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_MemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// ȸ�� ���̺� DAO ������
	public DB_MemberDAO() {
		conn = DB_DBConn.getConnection();	// DBConn���� ������ con ��ü�� ���
		pstmt = null;
		rs = null;
	}
	
	// ~~~~~~~~~ �����ڿ� SQL �޼ҵ� ~~~~~~~~~ //
	// ȸ�� ID�� ���� ���� ��ȸ
	public ArrayList<DB_MemberDTO> selectIdCheck(String userId) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectIdCehck() ����]");
		
		try {
			String sql = "SELECT m_id FROM member WHERE m_id = ?";
			
			System.out.println("	[���� �ɼ� : IdCehck ==> ����� SQL�� : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DB_MemberDTO dto = new DB_MemberDTO();
				dto.setM_id(rs.getString("m_id"));
				
				System.out.println("	" + dto.toString());
				list.add(dto);
			}
			System.out.println("	[" + list.size()+"�� ������ ���õ�]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : selectIdCehck() ����]");
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
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// ȸ�� ID�� PWD�� ��Ī ���� ��ȸ
	public ArrayList<DB_MemberDTO> selectPwdCheck(String userId, String userPwd) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectPwdCheck() ����]");
		
		try {
			String sql = "SELECT m_id FROM member WHERE m_id = ? AND pwd = ?";
			
			System.out.println("	[���� �ɼ� : PwdCheck ==> ����� SQL�� : " + sql + "]");
			
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
			System.out.println("	[" + list.size()+"�� ������ ���õ�]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : selectPwdCheck() ����]");
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
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// Ư�� ȸ���� ��ü ���� ��ȸ
	public ArrayList<DB_MemberDTO> selectGetMember(String userId) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectGetMember() ����]");
		
		try {
			String sql = "SELECT * FROM member WHERE m_id = ?";
			
			System.out.println("	[���� �ɼ� : GetMember ==> ����� SQL�� : " + sql + "]");
			
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
			System.out.println("	[" + list.size()+"�� ������ ���õ�]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : selectGetMember() ����]");
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
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// ��ü ȸ�� ��ȸ(������)
	public ArrayList<DB_MemberDTO> selectAdminShowMember(String orderBy) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectAdminShowMember() ����]");
		
		try {
			String sql = "SELECT m_id, pwd, m_name, NVL(birth, '(�ش� ����)') as birth, NVL(gender, '(�ش� ����)') as gender, "
					+ "NVL2(phone, REGEXP_REPLACE(REGEXP_REPLACE (phone, '[^[:digit:]]'), '(^02|050[[:digit:]]{1}|[[:digit:]]{3})([[:digit:]]{3,4})([[:digit:]]{4})','\\1-\\2-\\3'), '(�ش� ����)') as phone, "
					+ "NVL(addr, '(�ش� ����)') as addr, TO_CHAR(joindate, '\"\"YYYY\"�� \"MM\"�� \"DD\"�� \"hh24\"�� \"mi\"��\"') as joindate, NVL(m_teacher, '(�ش� ����)') as m_teacher "
					+ "FROM member ORDER BY " + orderBy;
			
			System.out.println("	[���� �ɼ� : AdminShowMember ==> ����� SQL�� : " + sql + "]");
			
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
			System.out.println("	[" + list.size()+"�� ������ ���õ�]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : selectAdminShowMember() ����]");
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
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// ��ü ȸ�� �˻�(������)
	public ArrayList<DB_MemberDTO> selectAdminShowMemberSearch(String key, String word, String orderBy) {
		ArrayList<DB_MemberDTO> list = new ArrayList<DB_MemberDTO>();
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectAdminShowMemberSearch() ����]");
		
		try {
			String sql = "SELECT m_id, pwd, m_name, NVL(birth, '(�ش� ����)') as birth, NVL(gender, '(�ش� ����)') as gender, "
					+ "NVL2(phone, REGEXP_REPLACE(REGEXP_REPLACE (phone, '[^[:digit:]]'), '(^02|050[[:digit:]]{1}|[[:digit:]]{3})([[:digit:]]{3,4})([[:digit:]]{4})','\\1-\\2-\\3'), '(�ش� ����)') as phone, "
					+ "NVL(addr, '(�ش� ����)') as addr, TO_CHAR(joindate, '\"\"YYYY\"�� \"MM\"�� \"DD\"�� \"hh24\"�� \"mi\"��\"') as joindate, NVL(m_teacher, '(�ش� ����)') as m_teacher "
					+ "FROM member WHERE LOWER(" + key + ") like LOWER(?) ORDER BY " + orderBy;
			
			System.out.println("	[���� �ɼ� : AdminShowMemberSearch ==> ����� SQL�� : " + sql + "]");
			
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
			System.out.println("	[" + list.size()+"�� ������ ���õ�]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : selectAdminShowMemberSearch() ����]");
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
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return list;
	}

	// ȸ�� ����(������)
	public int deleteMember(String userId) {
		int result = 0;
		pstmt = null;
		
		System.out.println("	[MemberDAO deleteMember() ����]");
		
		try {
			String sql = "DELETE FROM member WHERE m_id = ?";
			System.out.println("	[���� �ɼ� : deleteMember ==> ����� SQL�� : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : deleteMember() ����]");
			e.printStackTrace();
		} finally {
			try {
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// ȸ�� ���� ����(������)
	public int updateMember(DB_MemberDTO user) {
		int result = 0;
		pstmt = null;
		
		System.out.println("	[MemberDAO updateMember() ����]");
		
		try {
			String sql = "UPDATE member SET pwd = ?, m_name = ?, birth = ?, gender = ?, phone = ?, addr = ?, m_teacher = ? WHERE m_id = ?";
			System.out.println("	[���� �ɼ� : updateMember ==> ����� SQL�� : " + sql + "]");
			
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
			System.err.println("[MemberDAO ���� : updateMember() ����]");
			e.printStackTrace();
		} finally {
			try {
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// ȸ�� ���� ����(���� ���� ����)
		public int updateMemberbyDelTchr(String teacher) {
			int result = 0;
			pstmt = null;
			
			System.out.println("	[MemberDAO updateMember() ����]");
			
			try {
				String sql = "UPDATE member SET m_teacher = null "
						+ "WHERE m_teacher = ?"
						+ "AND ((SELECT COUNT(*) FROM pt WHERE e_teacher = ?) = 0)";
				System.out.println("	[���� �ɼ� : updateMember ==> ����� SQL�� : " + sql + "]");
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, teacher);
				pstmt.setString(2, teacher);
				
				result = pstmt.executeUpdate();
				
				System.out.println();
			} catch (Exception e) {
				System.err.println("[MemberDAO ���� : updateMember() ����]");
				e.printStackTrace();
			} finally {
				try {
					if(!pstmt.equals(null)) {
						pstmt.close();
					}
				} catch (SQLException e) {
					System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
					e.printStackTrace();
				}
			}
			return result;
		}
	
	// ȸ�� ����
	public int insertMember(DB_MemberDTO user) {
		int result = 0;
		pstmt = null;
		
		System.out.println("	[MemberDAO insertMember() ����]");
		
		try {
			String sql = "INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
			System.out.println("	[���� �ɼ� : insertMember ==> ����� SQL�� : " + sql + "]");
			
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
			System.err.println("[MemberDAO ���� : insertMember() ����]");
			e.printStackTrace();
		} finally {
			try {
				if(!pstmt.equals(null)) {
					pstmt.close();
				}
			} catch (SQLException e) {
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// ��ü ȸ���� ��ȸ
	public int selectTotalMemberCnt() {
		int totalMemberCount = 0;
		pstmt = null;
		rs = null;
		
		System.out.println("	[MemberDAO selectTotalMemberCnt() ����]");
		
		try {
			String sql = "SELECT COUNT(m_id) FROM member";
			
			System.out.println("	[���� �ɼ� : TotalMemberCnt ==> ����� SQL�� : " + sql + "]");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				totalMemberCount = rs.getInt("COUNT(m_id)");
			}
			System.out.println("	[" + rs.getFetchSize() +"�� ������ ���õ�]");
			System.out.println();
		} catch (Exception e) {
			System.err.println("[MemberDAO ���� : selectTotalMemberCnt() ����]");
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
				System.err.println("[MemberDAO ���� : ��ü �ݱ� ����]");
				e.printStackTrace();
			}
		}
		return totalMemberCount;
	}
		
	// ~~~~~~~~~ �Ϲ� ȸ���� SQL �޼ҵ� ~~~~~~~~~ //
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
			System.out.println("ȸ�� Ż�� ���� " + e);
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
			System.out.println("ȸ�� Ż�� ���� " + e);
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
	
	
	// ~~~~~~~~~ ���� ���̺� ���Կ� SQL �޼ҵ� ~~~~~~~~~ //
	// ȸ�� ID ��ȸ(�ߺ� ���� ����)
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
			System.out.println(e + " : e_id ��� ����");
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