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
	
	// DB�� ��ü ����Ʈ �����´�
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
			System.out.println(e + " : �˻� ����");
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
	
	// �˻��Ҷ� �޺��ڽ��� ����Ʈ�� Ŭ���ϸ� ������������ ����
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
			System.out.println(e + " : �޺��ڽ� ����Ʈ ��� ����");
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
	
	// ȸ�� ���� ���� ��ġ �����Ҷ� �ߺ��� ��ġ�� �� ����
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
			System.out.println(e + " : ���� ��� ����");
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
			System.out.println("���� ����");
			
			String sql = "insert into pt (e_id, e_name, time, e_teacher, price)" + 
					"values (seq_ptid.nextval,?,?,?,?)";
			
			pst = con.prepareStatement(sql);
			
			pst.setString(1, ptInsert.getE_name());
			
			pst.setString(2, ptInsert.getTime());
			
			pst.setString(3, ptInsert.getE_teacher());
			
			pst.setInt(4, ptInsert.getPrice());
			
			System.out.println(ptInsert.toString());
			
			result = pst.executeUpdate();
			System.out.println(result + " : �� �Է�");
			
			DB_PtDTO dto = new DB_PtDTO();
			System.out.println("insert to : " + dto.toString());
		} catch (Exception e) {
			System.out.println(e + " : insert ����");
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
			// to_cahr ���� �״�� �������⶧���� ���꽺Ʈ������ id ��ȣ �߶���ߵ�
			pst.setString(5, ptupdate.getE_id());
			
			System.out.println(ptupdate.toString());
			System.out.println(pst);

			result = pst.executeUpdate();
			System.out.println(result + " : �� �Է�");
		} catch (Exception e) {
			System.out.println(e + " : update ����");
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
			
			System.out.println(sql + " : ���� sql��");
			
			result = pst.executeUpdate();
			System.out.println(result + " : �� ���� �Ϸ�");
		} catch (Exception e) {
			System.out.println(e + " : Delete ����");
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
	
	//total �� �޾ƿ���
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
			System.out.println(e + " : �˻� ����");
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
	
	// �˻�
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
			System.out.println(e + " : �˻� ����");
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
	
	// � ����Ҷ� ���� ������ ���� ��� �Ǿ� �ִ��� üũ
	public ArrayList<DB_PtDTO> InsertCheck(String ename, String time, String teacher, int price) {
		
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		try {
			System.out.println("���� ����");
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
			System.out.println(e + " : Insert Check �ȵ�");
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
	
	// � �����Ҷ� ���� ������ ���� ��� �Ǿ� �ִ��� üũ
	public ArrayList<DB_PtDTO> UpdateCheck(String ename, String time, String teacher, int price) {
		
		ArrayList<DB_PtDTO> list = new ArrayList<DB_PtDTO>();
		try {
			System.out.println("���� ����");
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
			System.out.println(e + " : Insert Check �ȵ�");
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
	
	// � ID ��ȸ
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
				System.out.println(e + " : e_id ��� ����");
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
