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
	
	//=====================================화면 공통 부분 변수들========================================
	MenuBar mb;
	Menu menu;
	MenuItem miLogout;
	
	// 탭 기능
	JTabbedPane t;	// 탭 패널 생성
    JPanel ptSubmitPanel, memberInfoPanel;	// 각 탭에서 나타낼 화면 2개 생성
    JPanel myInfoPanel, myRePanel;	// 내 정보 탭 안에 들어가야 하는 내부 화면 2개 생성
    
    // 로그인 유저 기록 기능
    String userID;	// 로그인한 유저명을 표시하기 위한 String 준비
    
    //=====================================상품 관련 기능에 필요한 변수들========================================
  	// PtMember 멤버변수 선언
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
    
    //=====================================회원 관련 기능에 필요한 변수들========================================
    JPanel memberPanel;
    JPanel memberBtns;
	JButton update;;
	JButton del;
	
	JLabel m_id, m_name, birth, gender, phone, addr, joindate, m_teacher, m_price;
	
	JPanel b1, b3, b4, b5, b6, b7, b8, b9, b10;
	JLabel a1, a3, a4, a5, a6 , a7, a8, a9, a10;
    
	DB_MemberDAO dao;	// DB에 접근하기 위한 DAO 준비
	Fr_D_SignForm signUp;	// 회원 정보 수정에 사용할 다이얼로그 준비
	
	JPanel imagePanel;;
    ImageIcon memberImg;
    JLabel imgbox;
    
	//======================================예약 관련 기능에 필요한 변수들========================================
	//예약 테이블
	String[] rid2;
	DefaultTableModel redt2;
	JTable rejt2;
	JScrollPane rejsp2;
	DB_ReJTable2DAO redao2;
	//예약 관리
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
	
	// 회원 화면 생성자
	public Fr_M_MemberMain(String userID) {
		this.userID = userID;
		dao = new DB_MemberDAO();
		
		layoutInit();
		redao2.showListmemre(redt2, userID);
		getUserInfo();
		eventInit();
		
		setTitle("The Six 피트니스");
		setVisible(true);
		
		JOptionPane.showMessageDialog(this, this.userID + "님 환영합니다.", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("[종료]");
				System.out.println("=================================================================================");
				DB_DBConn.close();
				System.exit(0);
			}
		});
	}
	
	// 레이아웃 정의
	public void layoutInit() {
		setLayout(new BorderLayout());
		setSize(1200, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		
		ptSubmitLayoutInit();
		memberInfoLayoutInit();
		
		t = new JTabbedPane();
		t.add("운동 신청", ptSubmitPanel);
		t.add("내 정보", memberInfoPanel);
		add(t);
		
		mb = new MenuBar();
		menu = new Menu("계정");
		miLogout = new MenuItem("로그아웃");
		
		menu.add(miLogout);
		mb.add(menu);
		setMenuBar(mb);
	}
	
	// 운동 신청 레이아웃
	public void ptSubmitLayoutInit() {
		ptDao = new DB_PtDAO();
		
		ptimagePanel = new JPanel();
		ptImg = new ImageIcon();
	    ptimgbox = new JLabel();
		
		ptImg = new ImageIcon("./img/userBanner.jpg");
		ptimgbox = new JLabel(new ImageIcon(ptImg.getImage().getScaledInstance(1280, 125, Image.SCALE_SMOOTH)));
		ptimagePanel.add(ptimgbox);
		
		ptName = new String[] {"운동 ID", "운동 이름", "시간대", "담당 강사", "가격"};
		ptDt = new DefaultTableModel(ptName, 0) {
			private static final long serialVersionUID = -4890906273460233490L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		mTable = new JTable(ptDt);
		mtScroll = new JScrollPane(mTable);
		
		serchCombo = new String[] {"운동 이름", "시간대", "담당 강사"};
		combo = new JComboBox<String>(serchCombo);
		
		insertPtmem = new JButton("운동 신청");
		serchTf = new JTextField(20);
		serch = new JButton("검색");
		ptall = new JButton("전체 보기");
		
		pttotal = new JLabel("전체 상품 수 : " + ptDao.ptTotalshow());
		pttotalpan = new JPanel();
		pttotalpan.setLayout(new FlowLayout(FlowLayout.CENTER));
		pttotalpan.setBackground(Color.white);
		pttotalpan.setBorder(new LineBorder(Color.black,1));
		pttotalpan.add(pttotal);
		
		mPanel = new JPanel();
		mPanel.add(pttotalpan);
		mPanel.add(insertPtmem);
		insertPtmem.setFont(new Font("고딕체", Font.BOLD, 13));
		mPanel.add(combo);
		combo.setFont(new Font("고딕체", Font.BOLD, 13));
		mPanel.add(serchTf);
		mPanel.add(serch);
		serch.setFont(new Font("고딕체", Font.BOLD, 13));
		mPanel.setBackground(Color.gray);
		mPanel.add(ptall);
		ptall.setFont(new Font("고딕체", Font.BOLD, 13));
		
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
		// 디폴트 모델을 쓰려면 ArrayList의 값을 받아와서
		// 다시 배열에 뿌려줘야함
		}
		
		if (ptDt.getRowCount() > 0)
			mTable.setRowSelectionInterval(0, 0);
	}
	
	// 내정보 레이아웃
	public void memberInfoLayoutInit() {
		myInfoPanel = new JPanel();
		myInfoPanel.setLayout(new BorderLayout());
		
		memberPanel = new JPanel();
	    memberBtns = new JPanel();
		update = new JButton("정보 수정");
		del = new JButton("회원 탈퇴");
		
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
		
		// 내정보 옆 이미지 넣기
		imagePanel = new JPanel();
	    memberImg = new ImageIcon();
	    imgbox = new JLabel();
		
		memberImg = new ImageIcon("./img/fitness2.jpg");
		imgbox = new JLabel(new ImageIcon(memberImg.getImage().getScaledInstance(850, 400, Image.SCALE_SMOOTH)));
		imagePanel.add(imgbox);
	
		//내 정보 관리는 myInfoPanel에 넣을 예정
		//======================================회원 관련 기능에 필요한 변수들========================================
		memberBtns.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
		memberBtns.add(update);
		memberBtns.add(del);
		
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new GridLayout(10, 1));
		
		m_id = new JLabel("이름");
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
//		myInfoPanel.add("Center", new JButton("내정보"));
	    
	  
		myRePanel= new JPanel();
		myRePanel.setLayout(new BorderLayout());
		
		//예약 관리는 myRePanel에 넣을 예정
		//======================================예약 관련 기능에 필요한 변수들========================================
		//예약 테이블
		rid2 = new String[] { "예약 번호", "회원 ID", "회원명", "운동명", "시간", "강사", "금액" };
		redt2 = new DefaultTableModel(rid2, 0);
		rejt2 = new JTable(redt2);
		rejsp2 = new JScrollPane(rejt2);
		redao2 = new DB_ReJTable2DAO();
		//예약 관리
	    rep2 = new JPanel();
		recomboN2 = new String[] { "ALL", "예약 번호", "운동명", "시간", "강사" };
		
		lTotalRe = new JLabel("전체 예약수  :  " + redao2.selectMemReCnt(userID));
		lTotalRePanel = new JPanel();
		lTotalRePanel.setLayout(new FlowLayout());
		lTotalRePanel.setBackground(Color.white);
		lTotalRePanel.setBorder(new LineBorder(Color.black,1));
		lTotalRePanel.add(lTotalRe);
		
		rejt2insert = new JButton("예약 추가");
		rejt2update = new JButton("예약 수정");
		rejt2delete = new JButton("예약 취소");
		recombo2 = new JComboBox<String>(recomboN2);
		rejtf2 = new JTextField(20);
		reserach2 = new JButton("검색");
		
		rep2.setBackground(Color.gray);
		rep2.add(lTotalRePanel);
//		rep2.add(rejt2insert);
//		rejt2insert.setFont(new Font("고딕체", Font.BOLD, 13));
//		rep2.add(rejt2update);
//		rejt2update.setFont(new Font("고딕체", Font.BOLD, 13));
		rep2.add(rejt2delete);
		rejt2delete.setFont(new Font("고딕체", Font.BOLD, 13));
//		rejt2insert.setEnabled(false);
//		rejt2update.setEnabled(false);
		rep2.add(recombo2);
		recombo2.setFont(new Font("고딕체", Font.BOLD, 13));
		rep2.add(rejtf2);
		rejtf2.setFont(new Font("고딕체", Font.BOLD, 13));
		rep2.add(reserach2);
		reserach2.setFont(new Font("고딕체", Font.BOLD, 13));
		myRePanel.add(rejsp2, "Center");
		myRePanel.add(rep2, "South");
		
		//==============================================================================
		// 내 정보들(회원 정보, 회원 예약 정보)을 하나의 패널에 모아 담기
		userInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,2), "회원 정보"));
		myRePanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,2), "예약 정보"));
		
		memberInfoPanel = new JPanel();
		memberInfoPanel.setLayout(new BorderLayout());
		memberInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		memberInfoPanel.add("North", myInfoPanel);
		memberInfoPanel.add("Center", myRePanel);
	}
	
	// 이벤트 리스너 등록
	public void eventInit() {
		// 회원 관리 이벤트 리스너 등록
		miLogout.addActionListener(this);
		update.addActionListener(this);
		del.addActionListener(this);
		
		// 상품 관리 이벤트 리스너 등록
		insertPtmem.addActionListener(this);
		serch.addActionListener(this);
		ptall.addActionListener(this);
		combo.addActionListener(this);
		
		// 예약 관리 이벤트 리스너 등록
		rejt2insert.addActionListener(this);
		rejt2update.addActionListener(this);
		rejt2delete.addActionListener(this);
		reserach2.addActionListener(this);
	}
	
	// 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == miLogout) {
			 int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.OK_CANCEL_OPTION);
			 if(result == JOptionPane.OK_OPTION) {
				 System.out.println("[로그아웃 성공]");
				 System.out.println("=================================================================================");
				 dispose();
				 new Fr_M_Login();
				 JOptionPane.showMessageDialog(this, "로그아웃 되었습니다.");
			 }
		}
		//=====================================회원 관련 이벤트 처리========================================
		//회원 정보 이벤트
		if (ob == update) {
			 System.out.println("작동합니다");
			 DB_MemberDTO user1 = new DB_MemberDTO();
			 user1.setM_id(userID);
			 new Fr_D_SignForm(user1, this);
		}
		if (ob == del) {
			
			 int result = JOptionPane.showConfirmDialog(null, "정말로 탈퇴 하시겠습니까?\n탈퇴시 예약 정보도 함께 삭제됩니다.", "회원 탈퇴", JOptionPane.OK_CANCEL_OPTION);
			 if(result == JOptionPane.OK_OPTION) {
				 System.out.println("작동합니다");
				 DB_MemberDAO dao = new DB_MemberDAO();
				 DB_MemberDTO user1 = new DB_MemberDTO();
				 user1.setM_id(userID);
				
				 dao.rememberdelete(user1);	// 예약 테이블에서 회원이 예약한 내용 삭제
				 dao.memberdelete(user1); // 회원 테이블에서 회원 삭제
				 
				 dispose();
				 new Fr_M_Login();
				 JOptionPane.showMessageDialog(this, "탈퇴 되었습니다.");
			 }
		}
		//=====================================예약 관련 이벤트 처리========================================
		// 예약 관리
		if (e.getSource() == rejt2insert) {// 추가 메뉴아이템 클릭
//			new ReJTable2DialogGUI(this, "추가");
		} else if (e.getSource() == rejt2update) {// 수정 메뉴아이템 클릭
//			new ReJTable2DialogGUI(this, "수정");
		} else if (e.getSource() == rejt2delete) {// 취소 메뉴아이템 클릭
			if (rejt2.getSelectedRow() > -1) {
				int row = rejt2.getSelectedRow();
				System.out.println("선택행 : " + row);
				Object obj = rejt2.getValueAt(row, 0);// 행 열에 해당하는 value
				System.out.println("값 : " + obj);
				int result = JOptionPane.showConfirmDialog(null, "정말로 취소 하시겠습니까?", "취소하기", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (redao2.deletere(obj.toString()) > 0) {
						JOptionPane.showMessageDialog(this, "취소 되었습니다.");

						redao2.showListmemre(redt2, userID);
						lTotalRe.setText("전체 예약수 : " + redao2.selectMemReCnt(userID));
						getUserInfo();
						if (redt2.getRowCount() > 0)
							rejt2.setRowSelectionInterval(0, 0);
					} else {
						JOptionPane.showMessageDialog(this, "취소 실패되었습니다.");
					}
				}
			}

		} else if (e.getSource() == reserach2) {// 검색 버튼 클릭
			// JComboBox에 선택된 value 가져오기
			String fieldName = recombo2.getSelectedItem().toString();
			System.out.println("필드명 " + fieldName);
			switch (recombo2.getSelectedItem().toString()) {
			case "All":
				fieldName = "All";
			case "예약 번호":
				fieldName = "r_id";
				break;
			case "운동명":
				fieldName = "e_name";
				break;
			case "시간":
				fieldName = "time";
				break;
			case "강사":
				fieldName = "e_teacher";
				break;
			}

			if (fieldName.trim().equals("ALL")) {// 전체검색
				redao2.showListmemre(redt2, userID);
				rejtf2.setText("");
				rejtf2.requestFocus();
				if (redt2.getRowCount() > 0)
					rejt2.setRowSelectionInterval(0, 0);
					
			} else {
				if (rejtf2.getText().trim().equals("")) {
					Fr_D_ReJTable2DialogGUI.messageBox(this, "검색단어를 입력해주세요!");
					rejtf2.requestFocus();
				} else {// 검색어를 입력했을경우
					redao2.searchmemre(redt2, fieldName, rejtf2.getText(), userID);
					if (redt2.getRowCount() > 0)
						rejt2.setRowSelectionInterval(0, 0);
				}
				rejtf2.setText("");
				rejtf2.requestFocus();
			}
		}	
	
		//=====================================운동 관련 이벤트 처리========================================
		if (e.getSource() == insertPtmem) {
				new Fr_D_PtDailog(this, "운동 신청");
		} else if (e.getSource() == serch) {
			// 콤보 박스의 아이템은 한글이기 때문에 컬럼명과의 매치가 안된다
			// 다시 스위치문을 이용해서 컬럼명으로 변경해줌
			String comboName = null;
			switch (combo.getSelectedItem().toString()) {
			case "운동 이름":
				comboName = "e_name";
				break;
			case "시간대":
				comboName = "time";
				break;
			case "담당 강사":
				comboName = "e_teacher";
				break;
			}
			String comboTxt = serchTf.getText();
			System.out.println("검색명 출력 : " + comboName);
			
			if (serchTf.getText().trim().equals("")) {
				Fr_D_PtDailog.messageBox(this, "검색어를 입력해 주세요~!");
				serchTf.requestFocus();
			} else {
				// 초기화 시키는 for문
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
					Fr_D_PtDailog.messageBox(this, "검색된 결과가 없습니다.  다시 검색해주세요.");
				}		
			}
		// 전체보기 액션
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
		
		// 콤보 셀렉트
				if (e.getSource() == combo) {
					String comboSelete = null;
					switch (combo.getSelectedItem().toString()) {
					case "운동 이름":
						comboSelete = "e_name";
						break;
					case "시간대":
						comboSelete = "time";
						break;
					case "담당 강사":
						comboSelete = "e_teacher";
						break;
					}
					System.out.println(comboSelete + " : 콤보셀렉");
					
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
	
	// 로그인한 회원 정보 가져오기
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
		
		ArrayList<DB_PtDTO> userTotalPrice = dao.selectTotalPrice(userID);	// 현재 회원의 예약 결제 총액을 구하기 위한 메소드
		
		m_id.setText("ID                :   "); a1.setText(userID);
		m_name.setText("이름           :   "); a3.setText(userName);
		birth.setText("생년월일  :   "); a4.setText(userbirth);
		gender.setText("성별           :   "); a5.setText(usergender);
		phone.setText("휴대전화  :   "); a6.setText(userphone);
		addr.setText("주소           :   "); a7.setText(useraddr);
		joindate.setText("가입일자  :   "); a8.setText(userjoindate);
		m_teacher.setText("담당강사  :   "); a9.setText(userteacher);
		m_price.setText("총액           :   ");
		a10.setText(Integer.toString(userTotalPrice.get(0).getPrice()));
	}
}
