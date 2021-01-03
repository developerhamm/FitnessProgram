import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Fr_M_MemberMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8487681640764045489L;
	
	//=====================================ȭ�� ���� �κ� ������========================================
	MenuBar mb;
	Menu menu;
	MenuItem miLogout;
	
	// �� ���
	JTabbedPane t;	// �� �г� ����
    JPanel ptSubmitPanel, memberInfoPanel;	// �� �ǿ��� ��Ÿ�� ȭ�� 2�� ����
    JPanel myInfoPanel, myRePanel;	// �� ���� �� �ȿ� ���� �ϴ� ���� ȭ�� 2�� ����
    
    // �α��� ���� ��� ���
    String userID;	// �α����� �������� ǥ���ϱ� ���� String �غ�
    
    //=====================================��ǰ ���� ��ɿ� �ʿ��� ������========================================
  	// PtMember ������� ����
    String ptName[];
	DefaultTableModel ptDt;
	JTable mTable;
	JScrollPane mtScroll;
	
	JPanel mPanel;
	JPanel pttotalpan;
	JLabel pttotal;
	
	String serchCombo[];
	JComboBox<String> combo;
	JButton insertPtmem;
	JTextField serchTf;
	JButton serch;
	JButton ptall;
	
	JPanel ptimagePanel;
    ImageIcon ptImg;
    JLabel ptimgbox;
	
	DB_PtDAO ptDao;
    
    //=====================================ȸ�� ���� ��ɿ� �ʿ��� ������========================================
    JPanel memberPanel;
    JPanel memberBtns;
	JButton update;;
	JButton del;
	
	JLabel m_id, m_name, birth, gender, phone, addr, joindate, m_teacher, m_price;
	
	JPanel b1, b3, b4, b5, b6, b7, b8, b9, b10;
	JLabel a1, a3, a4, a5, a6 , a7, a8, a9, a10;
    
	DB_MemberDAO dao;	// DB�� �����ϱ� ���� DAO �غ�
	Fr_D_SignForm signUp;	// ȸ�� ���� ������ ����� ���̾�α� �غ�
	
	JPanel imagePanel;;
    ImageIcon memberImg;
    JLabel imgbox;
    
	//======================================���� ���� ��ɿ� �ʿ��� ������========================================
	//���� ���̺�
	String[] rid2;
	DefaultTableModel redt2;
	JTable rejt2;
	JScrollPane rejsp2;
	DB_ReJTable2DAO redao2;
	//���� ����
    JPanel rep2;
    JPanel lTotalRePanel;
	String[] recomboN2;
	
	JButton rejt2insert;
	JButton rejt2update;
	JButton rejt2delete;
	JLabel empty1, lTotalRe;
	JComboBox<String> recombo2;
	JTextField rejtf2;
	JButton reserach2;
	
	// ȸ�� ȭ�� ������
	public Fr_M_MemberMain(String userID) {
		this.userID = userID;
		dao = new DB_MemberDAO();
		
		layoutInit();
		redao2.showListmemre(redt2, userID);
		getUserInfo();
		eventInit();
		
		setTitle("The Six ��Ʈ�Ͻ�");
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
		
		ptSubmitLayoutInit();
		memberInfoLayoutInit();
		
		t = new JTabbedPane();
		t.add("� ��û", ptSubmitPanel);
		t.add("�� ����", memberInfoPanel);
		add(t);
		
		mb = new MenuBar();
		menu = new Menu("����");
		miLogout = new MenuItem("�α׾ƿ�");
		
		menu.add(miLogout);
		mb.add(menu);
		setMenuBar(mb);
	}
	
	// � ��û ���̾ƿ�
	public void ptSubmitLayoutInit() {
		ptDao = new DB_PtDAO();
		
		ptimagePanel = new JPanel();
		ptImg = new ImageIcon();
	    ptimgbox = new JLabel();
		
		ptImg = new ImageIcon("./img/userBanner.jpg");
		ptimgbox = new JLabel(new ImageIcon(ptImg.getImage().getScaledInstance(1280, 125, Image.SCALE_SMOOTH)));
		ptimagePanel.add(ptimgbox);
		
		ptName = new String[] {"� ID", "� �̸�", "�ð���", "��� ����", "����"};
		ptDt = new DefaultTableModel(ptName, 0) {
			private static final long serialVersionUID = -4890906273460233490L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		mTable = new JTable(ptDt);
		mtScroll = new JScrollPane(mTable);
		
		serchCombo = new String[] {"� �̸�", "�ð���", "��� ����"};
		combo = new JComboBox<String>(serchCombo);
		
		insertPtmem = new JButton("� ��û");
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
		mPanel.add(insertPtmem);
		insertPtmem.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.add(combo);
		combo.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.add(serchTf);
		mPanel.add(serch);
		serch.setFont(new Font("���ü", Font.BOLD, 13));
		mPanel.setBackground(Color.gray);
		mPanel.add(ptall);
		ptall.setFont(new Font("���ü", Font.BOLD, 13));
		
		ptSubmitPanel = new JPanel();
		ptSubmitPanel.setLayout(new BorderLayout());
		ptSubmitPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ptSubmitPanel.add(ptimgbox, "North");
		ptSubmitPanel.add(mtScroll, "Center");
		ptSubmitPanel.add(mPanel, "South");
		
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
		// ����Ʈ ���� ������ ArrayList�� ���� �޾ƿͼ�
		// �ٽ� �迭�� �ѷ������
		}
		
		if (ptDt.getRowCount() > 0)
			mTable.setRowSelectionInterval(0, 0);
	}
	
	// ������ ���̾ƿ�
	public void memberInfoLayoutInit() {
		myInfoPanel = new JPanel();
		myInfoPanel.setLayout(new BorderLayout());
		
		memberPanel = new JPanel();
	    memberBtns = new JPanel();
		update = new JButton("���� ����");
		del = new JButton("ȸ�� Ż��");
		
		b1 = new JPanel();
		b3 = new JPanel();
		b4 = new JPanel();
		b5 = new JPanel();
		b6 = new JPanel();
		b7 = new JPanel();
		b8 = new JPanel();
		b9 = new JPanel();
		b10 = new JPanel();
		
		b1.setLayout(new FlowLayout(FlowLayout.LEFT));
		b3.setLayout(new FlowLayout(FlowLayout.LEFT));
		b4.setLayout(new FlowLayout(FlowLayout.LEFT));
		b5.setLayout(new FlowLayout(FlowLayout.LEFT));
		b6.setLayout(new FlowLayout(FlowLayout.LEFT));
		b7.setLayout(new FlowLayout(FlowLayout.LEFT));
		b8.setLayout(new FlowLayout(FlowLayout.LEFT));
		b9.setLayout(new FlowLayout(FlowLayout.LEFT));
		b10.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		a1 = new JLabel();
		a3 = new JLabel();
		a4 = new JLabel();
		a5 = new JLabel();
		a6 = new JLabel();
		a7 = new JLabel();
		a8 = new JLabel();
		a9 = new JLabel();
		a10 = new JLabel();
		
		// ������ �� �̹��� �ֱ�
		imagePanel = new JPanel();
	    memberImg = new ImageIcon();
	    imgbox = new JLabel();
		
		memberImg = new ImageIcon("./img/fitness2.jpg");
		imgbox = new JLabel(new ImageIcon(memberImg.getImage().getScaledInstance(850, 400, Image.SCALE_SMOOTH)));
		imagePanel.add(imgbox);
	
		//�� ���� ������ myInfoPanel�� ���� ����
		//======================================ȸ�� ���� ��ɿ� �ʿ��� ������========================================
		memberBtns.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
		memberBtns.add(update);
		memberBtns.add(del);
		
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new GridLayout(10, 1));
		
		m_id = new JLabel("�̸�");
		m_name = new JLabel();
		birth = new JLabel();
		gender = new JLabel();
		phone = new JLabel();
		addr = new JLabel();
		joindate = new JLabel();
		m_teacher = new JLabel();
		m_price = new JLabel();
		
		b1.add(m_id);
		b1.add(a1);
		b3.add(m_name);
		b3.add(a3);
		b4.add(birth);
		b4.add(a4);
		b5.add(gender);
		b5.add(a5);
		b6.add(phone);
		b6.add(a6);
		b7.add(addr);
		b7.add(a7);
		b8.add(joindate);
		b8.add(a8);
		b9.add(m_teacher);
		b9.add(a9);
		b10.add(m_price);
		b10.add(a10);
		
		labelsPanel.add(b1); labelsPanel.add(b3); labelsPanel.add(b4);
		labelsPanel.add(b5); labelsPanel.add(b6); labelsPanel.add(b7); 
		labelsPanel.add(b8); labelsPanel.add(b9); labelsPanel.add(b10);
	    
	    JPanel userInfoPanel = new JPanel();
	    userInfoPanel.setLayout(new BorderLayout());
	    userInfoPanel.add("Center", labelsPanel);
	    userInfoPanel.add("South", memberBtns);
	    
	    myInfoPanel.setLayout(new BorderLayout());
//	    myInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    myInfoPanel.add("East", imagePanel);	
	    myInfoPanel.add("Center", userInfoPanel);
//	    myInfoPanel.add("West", labelsPanel);
//	    myInfoPanel.add("South", memberBtns);
//		myInfoPanel.add("Center", new JButton("������"));
	    
	  
		myRePanel= new JPanel();
		myRePanel.setLayout(new BorderLayout());
		
		//���� ������ myRePanel�� ���� ����
		//======================================���� ���� ��ɿ� �ʿ��� ������========================================
		//���� ���̺�
		rid2 = new String[] { "���� ��ȣ", "ȸ�� ID", "ȸ����", "���", "�ð�", "����", "�ݾ�" };
		redt2 = new DefaultTableModel(rid2, 0);
		rejt2 = new JTable(redt2);
		rejsp2 = new JScrollPane(rejt2);
		redao2 = new DB_ReJTable2DAO();
		//���� ����
	    rep2 = new JPanel();
		recomboN2 = new String[] { "ALL", "���� ��ȣ", "���", "�ð�", "����" };
		
		lTotalRe = new JLabel("��ü �����  :  " + redao2.selectMemReCnt(userID));
		lTotalRePanel = new JPanel();
		lTotalRePanel.setLayout(new FlowLayout());
		lTotalRePanel.setBackground(Color.white);
		lTotalRePanel.setBorder(new LineBorder(Color.black,1));
		lTotalRePanel.add(lTotalRe);
		
		rejt2insert = new JButton("���� �߰�");
		rejt2update = new JButton("���� ����");
		rejt2delete = new JButton("���� ���");
		recombo2 = new JComboBox<String>(recomboN2);
		rejtf2 = new JTextField(20);
		reserach2 = new JButton("�˻�");
		
		rep2.setBackground(Color.gray);
		rep2.add(lTotalRePanel);
//		rep2.add(rejt2insert);
//		rejt2insert.setFont(new Font("���ü", Font.BOLD, 13));
//		rep2.add(rejt2update);
//		rejt2update.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(rejt2delete);
		rejt2delete.setFont(new Font("���ü", Font.BOLD, 13));
//		rejt2insert.setEnabled(false);
//		rejt2update.setEnabled(false);
		rep2.add(recombo2);
		recombo2.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(rejtf2);
		rejtf2.setFont(new Font("���ü", Font.BOLD, 13));
		rep2.add(reserach2);
		reserach2.setFont(new Font("���ü", Font.BOLD, 13));
		myRePanel.add(rejsp2, "Center");
		myRePanel.add(rep2, "South");
		
		//==============================================================================
		// �� ������(ȸ�� ����, ȸ�� ���� ����)�� �ϳ��� �гο� ��� ���
		userInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,2), "ȸ�� ����"));
		myRePanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,2), "���� ����"));
		
		memberInfoPanel = new JPanel();
		memberInfoPanel.setLayout(new BorderLayout());
		memberInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		memberInfoPanel.add("North", myInfoPanel);
		memberInfoPanel.add("Center", myRePanel);
	}
	
	// �̺�Ʈ ������ ���
	public void eventInit() {
		// ȸ�� ���� �̺�Ʈ ������ ���
		miLogout.addActionListener(this);
		update.addActionListener(this);
		del.addActionListener(this);
		
		// ��ǰ ���� �̺�Ʈ ������ ���
		insertPtmem.addActionListener(this);
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
		if (ob == miLogout) {
			 int result = JOptionPane.showConfirmDialog(null, "�α׾ƿ� �Ͻðڽ��ϱ�?", "�α׾ƿ�", JOptionPane.OK_CANCEL_OPTION);
			 if(result == JOptionPane.OK_OPTION) {
				 System.out.println("[�α׾ƿ� ����]");
				 System.out.println("=================================================================================");
				 dispose();
				 new Fr_M_Login();
				 JOptionPane.showMessageDialog(this, "�α׾ƿ� �Ǿ����ϴ�.");
			 }
		}
		//=====================================ȸ�� ���� �̺�Ʈ ó��========================================
		//ȸ�� ���� �̺�Ʈ
		if (ob == update) {
			 System.out.println("�۵��մϴ�");
			 DB_MemberDTO user1 = new DB_MemberDTO();
			 user1.setM_id(userID);
			 new Fr_D_SignForm(user1, this);
		}
		if (ob == del) {
			
			 int result = JOptionPane.showConfirmDialog(null, "������ Ż�� �Ͻðڽ��ϱ�?\nŻ��� ���� ������ �Բ� �����˴ϴ�.", "ȸ�� Ż��", JOptionPane.OK_CANCEL_OPTION);
			 if(result == JOptionPane.OK_OPTION) {
				 System.out.println("�۵��մϴ�");
				 DB_MemberDAO dao = new DB_MemberDAO();
				 DB_MemberDTO user1 = new DB_MemberDTO();
				 user1.setM_id(userID);
				
				 dao.rememberdelete(user1);	// ���� ���̺��� ȸ���� ������ ���� ����
				 dao.memberdelete(user1); // ȸ�� ���̺��� ȸ�� ����
				 
				 dispose();
				 new Fr_M_Login();
				 JOptionPane.showMessageDialog(this, "Ż�� �Ǿ����ϴ�.");
			 }
		}
		//=====================================���� ���� �̺�Ʈ ó��========================================
		// ���� ����
		if (e.getSource() == rejt2insert) {// �߰� �޴������� Ŭ��
//			new ReJTable2DialogGUI(this, "�߰�");
		} else if (e.getSource() == rejt2update) {// ���� �޴������� Ŭ��
//			new ReJTable2DialogGUI(this, "����");
		} else if (e.getSource() == rejt2delete) {// ��� �޴������� Ŭ��
			if (rejt2.getSelectedRow() > -1) {
				int row = rejt2.getSelectedRow();
				System.out.println("������ : " + row);
				Object obj = rejt2.getValueAt(row, 0);// �� ���� �ش��ϴ� value
				System.out.println("�� : " + obj);
				int result = JOptionPane.showConfirmDialog(null, "������ ��� �Ͻðڽ��ϱ�?", "����ϱ�", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (redao2.deletere(obj.toString()) > 0) {
						JOptionPane.showMessageDialog(this, "��� �Ǿ����ϴ�.");

						redao2.showListmemre(redt2, userID);
						lTotalRe.setText("��ü ����� : " + redao2.selectMemReCnt(userID));
						getUserInfo();
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
				redao2.showListmemre(redt2, userID);
				rejtf2.setText("");
				rejtf2.requestFocus();
				if (redt2.getRowCount() > 0)
					rejt2.setRowSelectionInterval(0, 0);
					
			} else {
				if (rejtf2.getText().trim().equals("")) {
					Fr_D_ReJTable2DialogGUI.messageBox(this, "�˻��ܾ �Է����ּ���!");
					rejtf2.requestFocus();
				} else {// �˻�� �Է��������
					redao2.searchmemre(redt2, fieldName, rejtf2.getText(), userID);
					if (redt2.getRowCount() > 0)
						rejt2.setRowSelectionInterval(0, 0);
				}
				rejtf2.setText("");
				rejtf2.requestFocus();
			}
		}	
	
		//=====================================� ���� �̺�Ʈ ó��========================================
		if (e.getSource() == insertPtmem) {
				new Fr_D_PtDailog(this, "� ��û");
		} else if (e.getSource() == serch) {
			// �޺� �ڽ��� �������� �ѱ��̱� ������ �÷������ ��ġ�� �ȵȴ�
			// �ٽ� ����ġ���� �̿��ؼ� �÷������� ��������
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
				// �ʱ�ȭ ��Ű�� for��
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
		// ��ü���� �׼�
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
		}
		
		// �޺� ����Ʈ
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
					System.out.println(comboSelete + " : �޺�����");
					
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
	}
	
	// �α����� ȸ�� ���� ��������
	public void getUserInfo() {
		ArrayList<DB_MemberDTO> userInfo = dao.selectUserInfo(userID);
		String userID = userInfo.get(0).getM_id();
		String userName = userInfo.get(0).getM_name();
		String userbirth = userInfo.get(0).getBirth();
		String usergender = userInfo.get(0).getGender();
		String userphone = userInfo.get(0).getPhone();
		String useraddr = userInfo.get(0).getAddr();
		String userjoindate = userInfo.get(0).getJoindate();
		String userteacher = userInfo.get(0).getM_teacher();
		System.out.println(userInfo.get(0).toString());
		
		ArrayList<DB_PtDTO> userTotalPrice = dao.selectTotalPrice(userID);	// ���� ȸ���� ���� ���� �Ѿ��� ���ϱ� ���� �޼ҵ�
		
		m_id.setText("ID                :   "); a1.setText(userID);
		m_name.setText("�̸�           :   "); a3.setText(userName);
		birth.setText("�������  :   "); a4.setText(userbirth);
		gender.setText("����           :   "); a5.setText(usergender);
		phone.setText("�޴���ȭ  :   "); a6.setText(userphone);
		addr.setText("�ּ�           :   "); a7.setText(useraddr);
		joindate.setText("��������  :   "); a8.setText(userjoindate);
		m_teacher.setText("��簭��  :   "); a9.setText(userteacher);
		m_price.setText("�Ѿ�           :   ");
		a10.setText(Integer.toString(userTotalPrice.get(0).getPrice()));
	}
}
