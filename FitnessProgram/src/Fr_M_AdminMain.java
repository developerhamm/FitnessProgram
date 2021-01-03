import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Fr_M_AdminMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4652601346626156680L;
	
	//=====================================ȭ�� ���� �κ� ������========================================
	// ��� �޴�
	MenuBar mb;
	Menu menu;
	MenuItem miLogout;
	
	// �� ���
	JTabbedPane t;
    JPanel ptPanel, memberPanel, rePanel;
    
    // �α��� ���� ��� ���
    String userID;
    
    //=====================================��ǰ ���� ��ɿ� �ʿ��� ������========================================
    // ptAdmin ��������� 
    String ptName[];
	DefaultTableModel ptDt;
	JTable mTable;
	JScrollPane mtScroll;
	
	JPanel mPanel;
	JPanel pttotalpan;
	JLabel pttotal;
	
	String serchCombo[];
	JComboBox<String> combo;
	JButton insertPt;
	JButton updatePt;
	JButton deletePt;
	JTextField serchTf;
	JButton serch;
	JButton ptall;
	
	DB_PtDAO ptDao;
 	//=====================================ȸ�� ���� ��ɿ� �ʿ��� ������========================================
 	String memberData[][];
	String memberTitle[];
	DefaultTableModel memberModel;
	JTable memberTable;
	JScrollPane memberScrlPane;
 	
    JPanel memberControlPanel;
    JPanel memberMetaInfoPanel, memberBtnsPanel;
	JButton BtnMemberDelete, BtnMemberUpdate, BtnMemberSelect;
	JComboBox<String> cbSearchFilter, cbOrderFilter;
	JTextField tfSearchText;
	JLabel lTotalMember;
	
	String memberOrderby;	// ȸ�� ���̺� ������ ���� ����
	
	DB_MemberDAO memberDao;
	Fr_D_SignForm signUpdate;
	
	//======================================���� ���� ��ɿ� �ʿ��� ������========================================
	String[] rid2 ;
	DefaultTableModel redt2;
	JTable rejt2;
	JScrollPane rejsp2;
	
	JPanel rep2;
	JPanel lTotalRePanel;
	String[] recomboN2;

	JButton rejt2insert, rejt2update, rejt2delete;
	JLabel empty1, lTotalRe;
	JComboBox<String> recombo2;
	JTextField rejtf2;
	JButton reserach2;
	
	DB_ReJTable2DAO reDao;

	// ������ ȭ�� ������
	public Fr_M_AdminMain(String userID) {
		this.userID = userID;
		memberDao = new DB_MemberDAO();
		reDao = new DB_ReJTable2DAO();
		
		memberOrderby = "joindate";
		
		layoutInit();
		showTables();
		reDao.showListadmre(redt2);
		eventInit();
		
		setTitle("The Six ��Ʈ�Ͻ� - ������");
		setVisible(true);
		
		JOptionPane.showMessageDialog(this, this.userID + "�� ȯ���մϴ�.", "�α��� ����", JOptionPane.INFORMATION_MESSAGE);
	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("[����]");
				System.out.println("=================================================================================");
				DB_DBConn.close();
				System.exit(0);
			}
		});
	}
	
	// ���̾ƿ� ����
	public void layoutInit() {
		setLayout(new BorderLayout());
		setSize(1200, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		
		ptLayoutInit(); 	// ��ǰ ���� �ǿ� ���� ���̾ƿ� ����
		memberLayoutInit();	// ȸ�� ���� �ǿ� ���� ���̾ƿ� ����
		reLayoutInit();		// ���� ���� �ǿ� ���� ���̾ƿ� ����
		
		t = new JTabbedPane();
		t.add("� ����", ptPanel);
		t.add("ȸ�� ����", memberPanel);
		t.add("���� ����", rePanel);
		add(t);
		
		mb = new MenuBar();
		menu = new Menu("����");
		miLogout = new MenuItem("�α׾ƿ�");
		
		menu.add(miLogout);
		mb.add(menu);
		setMenuBar(mb);
	}
	
	// ��ǰ ���� ���̾ƿ�
	public void ptLayoutInit() {
		// ptProduct �����
		ptDao = new DB_PtDAO();
		
		ptName = new String[] {"� ID", "� �̸�", "�ð���", "��� ����", "����"};
		ptDt = new DefaultTableModel(ptName, 0) {
			private static final long serialVersionUID = -1181334915458554664L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		mTable = new JTable(ptDt);
		mtScroll = new JScrollPane(mTable);
		
		serchCombo = new String[] {"� �̸�", "�ð���", "��� ����"};
		combo = new JComboBox<String>(serchCombo);
		insertPt = new JButton("� ���");
		updatePt = new JButton("� ����");
		deletePt = new JButton("� ����");
		serchTf = new JTextField(20);
		serch = new JButton("�˻�");
		ptall = new JButton("��ü ����");
		
		pttotal = new JLabel("��ü ��ǰ �� : " + ptDao.ptTotalshow());
		pttotalpan = new JPanel();
		pttotalpan.setLayout(new FlowLayout(FlowLayout.CENTER));
		pttotalpan.setBackground(Color.white);
		pttotalpan.setBorder(new LineBorder(Color.black,1));
		pttotalpan.add(pttotal);
		
		mPanel = new JPanel();
		mPanel.add(pttotalpan);
		mPanel.add(insertPt);
		insertPt.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.add(updatePt);
		updatePt.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.add(deletePt);
		deletePt.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.add(combo);
		combo.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.add(serchTf);
		mPanel.add(serch);
		serch.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.setBackground(Color.gray);
		mPanel.add(ptall);
		ptall.setFont(new Font("���ü", Font.BOLD, 13));
		
		
		ptPanel = new JPanel();
		ptPanel.setLayout(new BorderLayout());
		ptPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ptPanel.add(mtScroll, "Center");
		ptPanel.add(mPanel, "South");
		
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ArrayList<DB_PtDTO> ptdto = ptDao.ptShowList();
		for(int i=0; i < ptdto.size(); i++) {
			String[] temp = new String[5];
			temp[0] = ptdto.get(i).getE_id();
			temp[1] = ptdto.get(i).getE_name();
			temp[2] = ptdto.get(i).getTime();
			temp[3] = ptdto.get(i).getE_teacher();
			temp[4] = Integer.toString(ptdto.get(i).getPrice());
			
			ptDt.addRow(temp);
		}
		if (ptDt.getRowCount() > 0)
			mTable.setRowSelectionInterval(0, 0);			
	}
	
	// ȸ�� ���� ���̾ƿ�
	public void memberLayoutInit() {
		memberData = new String[0][9];
		memberTitle = new String[] { "ȸ�� ID", "��й�ȣ", "ȸ�� �̸�", "�������", "����", "��ȭ��ȣ", "�ּ�", "��������", "��� ����" };
		
		memberModel = new DefaultTableModel(memberData, memberTitle) {
			private static final long serialVersionUID = 6300785512235916424L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		memberTable = new JTable(memberModel);
		memberTable.getTableHeader().setReorderingAllowed(false); // Į�� �̵� ����
		memberScrlPane = new JScrollPane(memberTable);
		
		BtnMemberDelete = new JButton("ȸ�� ����");
		BtnMemberUpdate = new JButton("ȸ�� ����");
		cbSearchFilter = new JComboBox<String>(new String[] {"ȸ�� ID", "ȸ�� �̸�", "�ּ�", "��� ����"});
		cbOrderFilter = new JComboBox<String>(new String[] {"ȸ�� ID", "ȸ�� �̸�", "�������", "����", "�ּ�", "��������", "��� ����"});
		cbOrderFilter.setSelectedIndex(5);
		tfSearchText = new JTextField(null, 20);
		BtnMemberSelect = new JButton("�˻�");
		
		lTotalMember = new JLabel("��ü ȸ����  :  " + memberDao.selectTotalMemberCnt());
		
		memberMetaInfoPanel = new JPanel();
		memberMetaInfoPanel.setLayout(new FlowLayout());
		memberMetaInfoPanel.setBackground(Color.white);
		memberMetaInfoPanel.setBorder(new LineBorder(Color.black,1));
		memberMetaInfoPanel.add(lTotalMember);
		
		memberBtnsPanel = new JPanel();
		memberBtnsPanel.setLayout(new FlowLayout());
		memberBtnsPanel.setBackground(Color.gray);
		memberBtnsPanel.add(memberMetaInfoPanel);
		memberBtnsPanel.add(new JLabel("���� ����", JLabel.RIGHT));
		memberBtnsPanel.add(cbOrderFilter);
		cbOrderFilter.setFont(new Font("���ü", Font.BOLD, 13));
		memberBtnsPanel.add(BtnMemberDelete);
		BtnMemberDelete.setFont(new Font("���ü", Font.BOLD, 13));
		memberBtnsPanel.add(BtnMemberUpdate);
		BtnMemberUpdate.setFont(new Font("���ü", Font.BOLD, 13));
		memberBtnsPanel.add(cbSearchFilter);
		cbSearchFilter.setFont(new Font("���ü", Font.BOLD, 13));
		memberBtnsPanel.add(tfSearchText);
		tfSearchText.setFont(new Font("���ü", Font.BOLD, 13));
		memberBtnsPanel.add(BtnMemberSelect);
		BtnMemberSelect.setFont(new Font("���ü", Font.BOLD, 13));
		
		memberPanel = new JPanel();
		memberPanel.setLayout(new BorderLayout());
		memberPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		memberPanel.add("Center", memberScrlPane);
		memberPanel.add("South", memberBtnsPanel);
	}
	
	// ���� ���� ���̾ƿ�
	public void reLayoutInit() {
		//���� ����
		rid2 = new String[] { "���� ��ȣ", "ȸ�� ID", "� ID","ȸ����", "���", "�ð�", "����", "�ݾ�"};
		redt2 = new DefaultTableModel(rid2, 0);
		rejt2 = new JTable(redt2);
		rejsp2 = new JScrollPane(rejt2);
		
		rep2 = new JPanel();
		recomboN2 = new String[] { "ALL", "���� ��ȣ", "ȸ�� ID", "� ID", "ȸ����", "���", "�ð�", "����" };

		lTotalRe = new JLabel("��ü �����  :  " + reDao.selectTotalReCnt());
		lTotalRePanel = new JPanel();
		lTotalRePanel.setLayout(new FlowLayout());
		lTotalRePanel.setBackground(Color.white);
		lTotalRePanel.setBorder(new LineBorder(Color.black,1));
		lTotalRePanel.add(lTotalRe);
		
		rejt2insert = new JButton("���� �߰�");
		rejt2update = new JButton("���� ����");
		rejt2delete = new JButton("���� ���");
		empty1 = new JLabel("                         ");
		recombo2 = new JComboBox<String>(recomboN2);
		rejtf2 = new JTextField(20);
		reserach2 = new JButton("�˻�");
		
		rep2.setBackground(Color.gray);
		rep2.add(lTotalRePanel);
		rep2.add(rejt2insert);
		rejt2insert.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(rejt2update);
		rejt2update.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(rejt2delete);
		rejt2delete.setFont(new Font("���ü", Font.BOLD, 13));
		
		rep2.add(recombo2);
		recombo2.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(rejtf2);
		rejtf2.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(reserach2);
		reserach2.setFont(new Font("���ü", Font.BOLD, 13));
		
		rePanel = new JPanel();
		rePanel.setLayout(new BorderLayout());
		rePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rePanel.add(rejsp2);
		rePanel.add("South", rep2);
	}
	
	// �̺�Ʈ ������ ���
	public void eventInit() {
		// ȸ�� ���� �̺�Ʈ ������ ���
		BtnMemberDelete.addActionListener(this);
		BtnMemberUpdate.addActionListener(this);
		BtnMemberSelect.addActionListener(this);
		miLogout.addActionListener(this);
		cbOrderFilter.addActionListener(this);
		
		// ��ǰ ���� �̺�Ʈ ������ ���
		insertPt.addActionListener(this);
		updatePt.addActionListener(this);
		deletePt.addActionListener(this);
		serch.addActionListener(this);
		ptall.addActionListener(this);
		combo.addActionListener(this);
		
		// ���� ���� �̺�Ʈ ������ ���
		rejt2insert.addActionListener(this);
		rejt2update.addActionListener(this);
		rejt2delete.addActionListener(this);
		reserach2.addActionListener(this);
	}
	
	// �̺�Ʈ ó��
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		//=====================================ȸ�� ���� �̺�Ʈ ó��========================================
		if (ob == BtnMemberDelete) {
			deleteMember();	// ȸ�� ���� �޼ҵ� ����
		} else if (ob == BtnMemberUpdate){
			updateMember();	// ȸ�� ���� �޼ҵ� ����
		} else if (ob == BtnMemberSelect) {
			searchMember(); // ȸ�� �˻� �޼ҵ� ����
		} else if (ob == miLogout) {
			 int result = JOptionPane.showConfirmDialog(null, "�α׾ƿ� �Ͻðڽ��ϱ�?", "�α׾ƿ�", JOptionPane.OK_CANCEL_OPTION);
			 if(result == JOptionPane.OK_OPTION) {
				 System.out.println("[�α׾ƿ� ����]");
				 System.out.println("=================================================================================");
				 dispose();
				 new Fr_M_Login();
				 JOptionPane.showMessageDialog(this, "�α׾ƿ� �Ǿ����ϴ�.");
			 }
		} else if (ob == cbOrderFilter) {
			switch (cbOrderFilter.getSelectedItem().toString()) {
			case "ȸ�� ID":
				memberOrderby = "m_id";
				break;
			case "ȸ�� �̸�":
				memberOrderby = "m_name";
				break;
			case "�������":
				memberOrderby = "birth";
				break;
			case "����":
				memberOrderby = "gender";
				break;
			case "�ּ�":
				memberOrderby = "addr";
				break;
			case "��������":
				memberOrderby = "joindate";
				break;
			case "��� ����":
				memberOrderby = "m_teacher";
				break;
			}
			showTables();
		}
		
		//=====================================� ���� �̺�Ʈ ó��========================================
		// � ����
		if (e.getSource() == insertPt) {
			new Fr_D_PtDailog(this, "� ���");
		} else if (e.getSource() == updatePt) {
			if (mTable.getSelectedRow() > -1) {
				new Fr_D_PtDailog(this, "� ����");
			}
		} else if (e.getSource() == deletePt) {
			if (mTable.getSelectedRow() > -1) {
				new Fr_D_PtDailog(this, "� ����");
			}
		} else if (e.getSource() == ptall) {
			for (int i = 0; i < ptDt.getRowCount();) {
				ptDt.removeRow(0);
			}
			ArrayList<DB_PtDTO> ptdto = ptDao.ptShowList();
			for(int i=0; i < ptdto.size(); i++) {
				String[] temp = new String[5];
				temp[0] = ptdto.get(i).getE_id();
				temp[1] = ptdto.get(i).getE_name();
				temp[2] = ptdto.get(i).getTime();
				temp[3] = ptdto.get(i).getE_teacher();
				temp[4] = Integer.toString(ptdto.get(i).getPrice());
				
				ptDt.addRow(temp);
			}
		} else if (e.getSource() == serch) {
			String comboName = null;
			switch (combo.getSelectedItem().toString()) {
			case "� �̸�":
				comboName = "e_name";
				break;
			case "�ð���":
				comboName = "time";
				break;
			case "��� ����":
				comboName = "e_teacher";
				break;
			}
			String comboTxt = serchTf.getText();
			System.out.println("�˻��� ��� : " + comboName);
			
			if (serchTf.getText().trim().equals("")) {
				Fr_D_PtDailog.messageBox(this, "�˻�� �Է��� �ּ���~!");
				serchTf.requestFocus();
			} else {
				for (int i = 0; i < ptDt.getRowCount();) {
					ptDt.removeRow(0);
				}
				
				ArrayList<DB_PtDTO> list = ptDao.ptSearch(comboName, comboTxt);
				
				if (list.size() > 0) {
					ArrayList<DB_PtDTO> ptdto = ptDao.ptSearch(comboName, comboTxt);
					
					for(int i=0; i < ptdto.size(); i++) {
						String[] temp = new String[5];
						temp[0] = ptdto.get(i).getE_id();
						temp[1] = ptdto.get(i).getE_name();
						temp[2] = ptdto.get(i).getTime();
						temp[3] = ptdto.get(i).getE_teacher();
						temp[4] = Integer.toString(ptdto.get(i).getPrice());
						
						ptDt.addRow(temp);
					}
				} else {
					Fr_D_PtDailog.messageBox(this, "�˻��� ����� �����ϴ�.  �ٽ� �˻����ּ���.");
				}				
			} 
		}
		// �޺� ����Ʈ �׼�
		if (e.getSource() == combo) {
			String comboSelete = null;
			switch (combo.getSelectedItem().toString()) {
			case "� �̸�":
				comboSelete = "e_name";
				break;
			case "�ð���":
				comboSelete = "time";
				break;
			case "��� ����":
				comboSelete = "e_teacher";
				break;
			}
			
			for (int t = 0; t < ptDt.getRowCount();) {
				ptDt.removeRow(0);
			}
			
			ArrayList<DB_PtDTO> list = ptDao.comboSeletedShow(comboSelete);
			
			for(int i=0; i < list.size(); i++) {
				String[] temp = new String[5];
				temp[0] = list.get(i).getE_id();
				temp[1] = list.get(i).getE_name();
				temp[2] = list.get(i).getTime();
				temp[3] = list.get(i).getE_teacher();
				temp[4] = Integer.toString(list.get(i).getPrice());
				
				ptDt.addRow(temp);
			}
		}
		
		//=====================================���� ���� �̺�Ʈ ó��========================================
		// ���� ����
		if (e.getSource() == rejt2insert) {// �߰� �޴������� Ŭ��
			new Fr_D_ReJTable2DialogGUI(this, "�߰�");
		} else if (e.getSource() == rejt2update) {// ���� �޴������� Ŭ��
			if(rejt2.getSelectedRow() > -1) {
				new Fr_D_ReJTable2DialogGUI(this, "����");
			}
		} else if (e.getSource() == rejt2delete) {// ��� �޴������� Ŭ��
			if (rejt2.getSelectedRow() > -1) {
				int row = rejt2.getSelectedRow();
				System.out.println("������ : " + row);
				Object obj = rejt2.getValueAt(row, 0);// �� ���� �ش��ϴ� value
				System.out.println("�� : " + obj);
				int result = JOptionPane.showConfirmDialog(null, "������ ��� �Ͻðڽ��ϱ�?", "����ϱ�",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (reDao.deletere(obj.toString()) > 0) {
						JOptionPane.showMessageDialog(this, "��� �Ǿ����ϴ�.");
						
						reDao.showListadmre(redt2);
						lTotalRe.setText("��ü ����� : " + reDao.selectTotalReCnt());
						if (redt2.getRowCount() > 0)
							rejt2.setRowSelectionInterval(0, 0);
					} else {
						JOptionPane.showMessageDialog(this, "��� ���еǾ����ϴ�.");
					}
				}
			}
		} else if (e.getSource() == reserach2) {// �˻� ��ư Ŭ��
			// JComboBox�� ���õ� value ��������
			String fieldName = recombo2.getSelectedItem().toString();
			System.out.println("�ʵ�� " + fieldName);
			switch (recombo2.getSelectedItem().toString()) {
			case "All":
				fieldName = "All";
			case "���� ��ȣ":
				fieldName = "r_id";
				break;
			case "ȸ�� ID":
				fieldName = "rm_id";
				break;
			case "� ID":
				fieldName = "re_id";
				break;
			case "ȸ����":
				fieldName = "m_name";
				break;
			case "���":
				fieldName = "e_name";
				break;
			case "�ð�":
				fieldName = "time";
				break;
			case "����":
				fieldName = "e_teacher";
				break;
			}

			if (fieldName.trim().equals("ALL")) {// ��ü�˻�
				
				try {
					reDao.showListadmre(redt2);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				rejtf2.setText("");
				rejtf2.requestFocus();
				if (redt2.getRowCount() > 0)
					rejt2.setRowSelectionInterval(0, 0);
					
			} else {
				if (rejtf2.getText().trim().equals("")) {
					Fr_D_ReJTable2DialogGUI.messageBox(this, "�˻��ܾ �Է����ּ���!");
					rejtf2.requestFocus();
				} else {// �˻�� �Է��������
					reDao.searchadmre(redt2, fieldName, rejtf2.getText());
					if (redt2.getRowCount() > 0)
						rejt2.setRowSelectionInterval(0, 0);
				}
				rejtf2.setText("");
				rejtf2.requestFocus();
			}
		}
	}
	
	// ȸ�� ���̺� ���� �޼ҵ�
	public void showTables() {
		memberModel.setRowCount(0); // ���ΰ�ģ �Ŀ� �����ֱ�
		
		ArrayList<DB_MemberDTO> dtoMembers = new ArrayList<DB_MemberDTO>();
		dtoMembers = memberDao.selectAdminShowMember(memberOrderby);
		
		String[] temp = new String[9];
		
		for(int i = 0; i<dtoMembers.size(); i++) {
			temp[0] = dtoMembers.get(i).getM_id();
			temp[1] = dtoMembers.get(i).getPwd();
			temp[2] = dtoMembers.get(i).getM_name();
			temp[3] = dtoMembers.get(i).getBirth();
			temp[4] = dtoMembers.get(i).getGender();
			temp[5] = dtoMembers.get(i).getPhone();
			temp[6] = dtoMembers.get(i).getAddr();
			temp[7] = dtoMembers.get(i).getJoindate();
			temp[8] = dtoMembers.get(i).getM_teacher();
			
			memberModel.addRow(temp);
		}
	}

	// ȸ�� ���� �޼ҵ�
	public void deleteMember() {
		String memberDelete = JOptionPane.showInputDialog(this, "������ ȸ�� ID�� �Է��ϼ���.", "ȸ�� ����", JOptionPane.INFORMATION_MESSAGE);

		if (memberDelete == null)
			return;
		if (userID.equals(memberDelete)) { // �ڱ� �ڽ��� ������ �� ���� �Ѵ�
			JOptionPane.showMessageDialog(this, "�����ڴ� �����Ͻ� �� �����ϴ�.", "ȸ�� ���� ���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		ArrayList<DB_MemberDTO> selectUser = memberDao.selectIdCheck(memberDelete);

		if (selectUser.isEmpty()) {
			JOptionPane.showMessageDialog(this, "�Է��Ͻ� ID�� �������� �ʽ��ϴ�.", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			int t = JOptionPane.showConfirmDialog(this, "������ �����Ͻðڽ��ϱ�?\n������ �ش� ȸ���� ���� ������ �Բ� �����˴ϴ�.", "ȸ�� ���� ���", JOptionPane.OK_CANCEL_OPTION);
			if(t == JOptionPane.OK_OPTION) {
				try {
					reDao.reDeleteByRm_id(memberDelete);	// ȸ���� �����ϱ� ���ؼ� ���� ���� ������ �����Ѵ�.
					memberDao.deleteMember(memberDelete);
					lTotalMember.setText("��ü ȸ���� : " + memberDao.selectTotalMemberCnt());	// ȸ�� ���� �� ȸ������ �����Ѵ�.
					lTotalRe.setText("��ü ����� : " + reDao.selectTotalReCnt());				// ȸ�� ���� �� ������� �����Ѵ�. (12/07 12:57 ����)
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "���������� �������� �ʾҽ��ϴ�. (���� �߻�)", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				showTables();
				reDao.showListadmre(redt2);
				JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.", "ȸ�� ���� ����", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	// ȸ�� ���� �޼ҵ�
	public void updateMember() {
		String selectId = "";
		int row = memberTable.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "������ �����͸� �����ϼ���.", "ȸ�� ����", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		selectId = (String) (memberModel.getValueAt(row, 0));
		
		if (userID.equals(selectId)) { // �ڱ� �ڽ��� ������ �� ���� �Ѵ�
			JOptionPane.showMessageDialog(this, "�����ڴ� ������ ������ �� �����ϴ�.", "ȸ�� ���� ���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		ArrayList<DB_MemberDTO> selectUser = memberDao.selectIdCheck(selectId);
		
		if(selectUser.isEmpty()) {
			JOptionPane.showMessageDialog(this, "�����Ͱ� �������� �ʽ��ϴ�.", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		signUpdate = new Fr_D_SignForm(selectUser.get(0), this);
	}
	
	// ȸ�� �˻� �޼ҵ�
	public void searchMember() {
		String tag = null;
		switch(cbSearchFilter.getSelectedItem().toString()) {
		case "ȸ�� ID":
			tag = "m_id";
			break;
		case "ȸ�� �̸�":
			tag = "m_name";
			break;
		case "��� ����":
			tag = "m_teacher";
			break;
		case "�ּ�":
			tag = "addr";
			break;
		}
		String inputText = tfSearchText.getText().trim();
		
		System.out.println("�˻� : " + tag + " - " + inputText);
		
		memberModel.setRowCount(0); // ���ΰ�ģ �Ŀ� �����ֱ�
		
		ArrayList<DB_MemberDTO> dtoMembers = new ArrayList<DB_MemberDTO>();
		
		dtoMembers = memberDao.selectAdminShowMemberSearch(tag, inputText, memberOrderby);
		String[] temp = new String[9];
		
		for(int i = 0; i<dtoMembers.size(); i++) {
			temp[0] = dtoMembers.get(i).getM_id();
			temp[1] = dtoMembers.get(i).getPwd();
			temp[2] = dtoMembers.get(i).getM_name();
			temp[3] = dtoMembers.get(i).getBirth();
			temp[4] = dtoMembers.get(i).getGender();
			temp[5] = dtoMembers.get(i).getPhone();
			temp[6] = dtoMembers.get(i).getAddr();
			temp[7] = dtoMembers.get(i).getJoindate();
			temp[8] = dtoMembers.get(i).getM_teacher();
			
			memberModel.addRow(temp);
		}
	}
}
