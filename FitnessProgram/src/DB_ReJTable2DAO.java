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

	// 繕噺(淫軒切)
	public void showListadmre(DefaultTableModel t_model) {
		try {
			ps = con.prepareStatement("select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_teacher, p.price"
					+ " from re r join member m on(r.rm_id = m.m_id) join pt p on(re_id = p.e_id)"
					+ " ORDER BY TO_NUMBER(r.r_id)");
			rs = ps.executeQuery();
			
			// DefaultTableModel拭 赤澗 奄糎 汽戚斗 走酔奄
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };
				t_model.addRow(data); // DefaultTableModel拭 傾坪球 蓄亜
			}
		} catch (SQLException e) {
			System.out.println(e + "=> showListadmre fail");
		} finally {
			dbClose();
		}
	}

	// 繕噺(呉獄)
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

	// 伊事(淫軒切)
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

	// 伊事(呉獄)
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

	// 蓄亜
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

	// 呪舛
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

	// 肢薦
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

	// 噺据 肢薦獣 背雁 噺据税 森鉦 舛左亀 敗臆 肢薦馬奄 是廃 五社球
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
	
	// 恥 森鉦鯵呪 朝錘闘
		public int selectTotalReCnt() {
			int totalReCount = 0;
			ps = null;
			rs = null;
			
			System.out.println("	[ReDAO selectTotalReCnt() 呪楳]");
			
			try {
				String sql = "SELECT COUNT(r_id) FROM re";
				
				System.out.println("	[叔楳 辛芝 : TotalReCnt ==> 叔楳吉 SQL庚 : " + sql + "]");
				
				ps = con.prepareStatement(sql);
				
				rs = ps.executeQuery();
				
				while (rs.next()) {
					totalReCount = rs.getInt("COUNT(r_id)");
				}
				System.out.println("	[" + rs.getFetchSize() +"鯵 汽戚斗 識澱喫]");
				System.out.println();
			} catch (Exception e) {
				System.err.println("[ReDAO 森須 : selectTotalReCnt() 叔鳶]");
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
					System.err.println("[ReDAO 森須 : 梓端 丸奄 叔鳶]");
					e.printStackTrace();
				}
			}
			return totalReCount;
		}
		
		//  切奄 森鉦鯵呪 朝錘闘
			public int selectMemReCnt(String userID) {
				int totalReCount = 0;
				ps = null;
				rs = null;
				
				System.out.println("	[ReDAO selectTotalReCnt() 呪楳]");
				
				try {
					String sql = ("SELECT COUNT(re.r_id) FROM re join member on(re.rm_id = member.m_id) where re.rm_id = '" + userID + "'");
					
					System.out.println("	[叔楳 辛芝 : MemReCnt ==> 叔楳吉 SQL庚 : " + sql + "]");
					
					ps = con.prepareStatement(sql);
					
					rs = ps.executeQuery();
					
					while (rs.next()) {
						totalReCount = rs.getInt("COUNT(re.r_id)");
					}
					System.out.println("	[" + rs.getFetchSize() +"鯵 汽戚斗 識澱喫]");
					System.out.println();
				} catch (Exception e) {
					System.err.println("[ReDAO 森須 : selectMemReCnt() 叔鳶]");
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
						System.err.println("[ReDAO 森須 : 梓端 丸奄 叔鳶]");
						e.printStackTrace();
					}
				}
				return totalReCount;
			}


	//////////////// 雌念 砺戚鷺拭辞 森鉦 砺戚鷺拭 蓄亜馬澗 SQL 五社球級////////////////////////

	// 績獣 森鉦 砺戚鷺 sql 五辞球
	// 錘疑 肢薦鞠檎 森鉦砺戚鷺亀 肢薦 五辞球
	public int ptreDelete(String ptreText) {

		int result = 0;
		try {
			String sql = "delete from re where re_id=?";

			ps = con.prepareStatement(sql);

			System.out.println(ptreText);

			ps.setString(1, ptreText.trim());

			System.out.println(sql + " : 肢薦 sql庚");

			result = ps.executeUpdate();
			System.out.println(result + " : 楳 叔楳 刃戟 けいしけいし");

		} catch (Exception e) {
			System.out.println(e + " : Delete 叔鳶");
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
			System.out.println("尻衣 失因");

			System.out.println(loginUser);
			System.out.println(ptText);

			String sql = "insert into re (r_id, rm_id, re_id)" + "values(seq_reid.nextval,'" + loginUser + "'," + "'"
					+ ptText + "')";
			ps = con.prepareStatement(sql);

			result = ps.executeUpdate();
			System.out.println(sql + " : sql庚");
			System.out.println(result + " : 鯵税 楳 叔楳刃戟");

		} catch (Exception e) {
			System.out.println(e + "ReInset 叔鳶");
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

	// 績獣 DB税 掻差吉 pt焼戚巨 端滴背辞 掻差吉 葵 端滴馬澗 五辞球
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
			System.out.println(e + " : Insert Check 照喫");
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
