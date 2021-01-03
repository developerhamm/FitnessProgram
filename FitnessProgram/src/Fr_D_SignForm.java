import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Fr_D_SignForm extends JDialog implements ActionListener {
	private static final long serialVersionUID = 818207317840612254L;
	
	DB_MemberDAO memberDao;
	DB_PtDAO ptDao;
	Fr_M_AdminMain am;
	Fr_M_MemberMain mm;
	
	JPanel mainPanel, formPanel, btnsPanel;
	JLabel lId, lPwd1, lPwd2, lName, lBirth, lGender, lPhone, lAddr, lTeacher, lJob, lRrnBar, lBirthYear, lBirthMonth, lBirthDay ,lPhoneBar1, lPhoneBar2;
	JTextField tfId, tfName, tfBirth, tfPhone1, tfPhone2, tfPhone3, tfAddr;
	JPasswordField pfPwd1, pfPwd2;
	JButton BtnIdCheck, btnSignIn, btnUpdate;
	JComboBox<String> cbBirthYear, cbBirthMonth, cbBirthDay, cbGender, cbTeacher;
	
	String checkedName, presentName;
	Boolean isIdUseable, isIdChecked;				
	
	// 회원 가입 화면 생성자
	public Fr_D_SignForm() {
		memberDao = new DB_MemberDAO();
		ptDao = new DB_PtDAO();
		
		layoutInit();
		
		btnsPanel.add(btnSignIn);
		mainPanel.add("Center", formPanel);
		mainPanel.add("South", btnsPanel);
		add(mainPanel);
		
		isIdUseable = false;
		isIdChecked = false;
		checkedName = "";
		presentName = "";
		
		eventInit();
		
		setTitle("회원 가입");
		setVisible(true);
	}
	
	// 회원 정보 수정 화면(관리자) 생성자
	public Fr_D_SignForm(DB_MemberDTO user, Fr_M_AdminMain am) {
		// 관리자 화면의 테이블을 갱신하기 위해서, 실행중인 AdminMain 클래스를 받아온다.
		memberDao = new DB_MemberDAO();
		ptDao = new DB_PtDAO();
		this.am = am;	// 회원수정 화면을 연 어드민메인 화면을 저장(기억)한다.
		ArrayList<DB_MemberDTO> dtoSelectedMember = new ArrayList<DB_MemberDTO>();
		
		layoutInit();
		btnsPanel.add(btnUpdate);
		mainPanel.add("Center", formPanel);
		mainPanel.add("South", btnsPanel);
		
		add(mainPanel);
		
		eventInit();
		
		dtoSelectedMember = memberDao.selectGetMember(user.getM_id());
		DB_MemberDTO SelectedMemberInfo = dtoSelectedMember.get(0);
		
		System.out.println("[" + SelectedMemberInfo.getM_id() + " 회원 수정 화면 표시]");
		
		// 회원 정보 수정하기 위해, 기존 회원 정보를 화면에 표시하기
		tfId.setText(SelectedMemberInfo.getM_id());
		pfPwd1.setText(SelectedMemberInfo.getPwd());
		pfPwd2.setText(SelectedMemberInfo.getPwd());
		tfName.setText(SelectedMemberInfo.getM_name());
		if(SelectedMemberInfo.getBirth() != null) {
			cbBirthYear.setSelectedItem(SelectedMemberInfo.getBirth().substring(0, 4));
			cbBirthMonth.setSelectedItem(SelectedMemberInfo.getBirth().substring(4, 6));
			cbBirthDay.setSelectedItem(SelectedMemberInfo.getBirth().substring(6, 8));
		}
		cbGender.setSelectedItem(SelectedMemberInfo.getGender());
		String phoneNum = SelectedMemberInfo.getPhone();
		if(phoneNum != null) {
			if(phoneNum.length() >= 3) tfPhone1.setText(phoneNum.substring(0, 3));
			if(phoneNum.length() >= 7) tfPhone2.setText(phoneNum.substring(3, 7));
			if(phoneNum.length() >= 11) tfPhone3.setText(phoneNum.substring(7, 11));
		}
		tfAddr.setText(SelectedMemberInfo.getAddr());
		cbTeacher.setSelectedItem(SelectedMemberInfo.getM_teacher());
		
		// 수정하면 안되는 내용은 접근을 막기
		tfId.setEnabled(false);
		BtnIdCheck.setEnabled(false);
	
		setTitle("회원 정보 수정");
		setVisible(true);
	}
	
	// 회원 정보 수정 화면(회원) 생성자
	public Fr_D_SignForm(DB_MemberDTO user, Fr_M_MemberMain mm) {
		// 회원 화면의 테이블을 갱신하기 위해서, 실행중인 MemberMain 클래스를 받아온다.
		memberDao = new DB_MemberDAO();
		ptDao = new DB_PtDAO();
		this.mm = mm;	// 회원수정 화면을 연 멤버메인 화면을 저장(기억)한다.
		ArrayList<DB_MemberDTO> dtoSelectedMember = new ArrayList<DB_MemberDTO>();
		
		layoutInit();
		btnsPanel.add(btnUpdate);
		mainPanel.add("Center", formPanel);
		mainPanel.add("South", btnsPanel);
		
		add(mainPanel);
		
		eventInit();
		
		dtoSelectedMember = memberDao.selectGetMember(user.getM_id());
		DB_MemberDTO SelectedMemberInfo = dtoSelectedMember.get(0);
		
		System.out.println("[" + SelectedMemberInfo.getM_id() + " 회원 수정 화면 표시]");
		
		// 회원 정보 수정하기 위해, 기존 회원 정보를 화면에 표시하기
		tfId.setText(SelectedMemberInfo.getM_id());
		pfPwd1.setText(SelectedMemberInfo.getPwd());
		pfPwd2.setText(SelectedMemberInfo.getPwd());
		tfName.setText(SelectedMemberInfo.getM_name());
		if(SelectedMemberInfo.getBirth() != null) {
			cbBirthYear.setSelectedItem(SelectedMemberInfo.getBirth().substring(0, 4));
			cbBirthMonth.setSelectedItem(SelectedMemberInfo.getBirth().substring(4, 6));
			cbBirthDay.setSelectedItem(SelectedMemberInfo.getBirth().substring(6, 8));
		}
		cbGender.setSelectedItem(SelectedMemberInfo.getGender());
		String phoneNum = SelectedMemberInfo.getPhone();
		if(phoneNum != null) {
			if(phoneNum.length() >= 3) tfPhone1.setText(phoneNum.substring(0, 3));
			if(phoneNum.length() >= 7) tfPhone2.setText(phoneNum.substring(3, 7));
			if(phoneNum.length() >= 11) tfPhone3.setText(phoneNum.substring(7, 11));
		}
		tfAddr.setText(SelectedMemberInfo.getAddr());
		cbTeacher.setSelectedItem(SelectedMemberInfo.getM_teacher());
		
		// 수정하면 안되는 내용은 접근을 막기
		tfId.setEnabled(false);
		BtnIdCheck.setEnabled(false);
	
		setTitle("회원 정보 수정");
		setVisible(true);
	}
	
	// 레이아웃 정의
	public void layoutInit() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		lId = new JLabel("아이디*", JLabel.CENTER);
		lPwd1 = new JLabel("비밀번호*", JLabel.CENTER);
		lPwd2 = new JLabel("비번확인*", JLabel.CENTER);
		lName = new JLabel("이름*", JLabel.CENTER);
		lBirth = new JLabel("생년월일", JLabel.CENTER);
		lBirthYear = new JLabel("년", JLabel.CENTER);
		lBirthMonth = new JLabel("월", JLabel.CENTER);
		lBirthDay = new JLabel("일", JLabel.CENTER);
		lGender = new JLabel("성별", JLabel.CENTER);
		lRrnBar = new JLabel("―", JLabel.CENTER);
		lPhone = new JLabel("전화번호", JLabel.CENTER);
		lAddr = new JLabel("주소", JLabel.CENTER);
		lTeacher = new JLabel("담당강사", JLabel.CENTER);
		lPhoneBar1 = new JLabel("―", JLabel.CENTER);
		lPhoneBar2 = new JLabel("―", JLabel.CENTER);
		tfId = new JTextField(10);
		pfPwd1 = new JPasswordField(10);
		pfPwd2 = new JPasswordField(10);
		tfName = new JTextField(10);
		tfBirth = new JTextField(6);
		cbBirthYear = new JComboBox<String>();
		cbBirthMonth = new JComboBox<String>();
		cbBirthDay = new JComboBox<String>();
		cbGender = new JComboBox<String>();
		cbGender.addItem(null);
		cbGender.addItem("남");
		cbGender.addItem("여");
		tfPhone1 = new JTextField(3);
		tfPhone2 = new JTextField(4);
		tfPhone3 = new JTextField(4);
		tfAddr = new JTextField(25);
		BtnIdCheck = new JButton("중복 확인");
		btnSignIn = new JButton("등록");
		btnUpdate = new JButton("수정");
		cbTeacher = new JComboBox<String>();
		ArrayList<DB_PtDTO> ptDto = ptDao.ptSelectTeacher();
		cbTeacher.addItem(null);
		for(int i = 0; i < ptDto.size(); i ++) {
			cbTeacher.addItem(ptDto.get(i).getE_teacher());
		}
		cbBirthYear.addItem(null);
		for (int i = 0; i <= 100; i++) {
			cbBirthYear.addItem(Integer.toString(new GregorianCalendar().get(Calendar.YEAR) - i));
		}
		cbBirthMonth.addItem(null);
		for (int i = 1; i <= 12; i++) {
			cbBirthMonth.addItem(String.format("%02d", i));
		}
		cbBirthDay.addItem(null);
		setBirthDays();
		formPanel = new JPanel();
		formPanel.setLayout(new BorderLayout(10, 10));
		
		JPanel idBox = new JPanel();
		idBox.setLayout(new GridLayout(1, 2, 10, 0));
		idBox.add(tfId);
		idBox.add(BtnIdCheck);
		
		JPanel birthBox = new JPanel();
		birthBox.setLayout(new GridLayout(1, 5, 5, 0));
		birthBox.add(cbBirthYear);
		birthBox.add(lBirthYear);
		birthBox.add(cbBirthMonth);
		birthBox.add(lBirthMonth);
		birthBox.add(cbBirthDay);
		birthBox.add(lBirthDay);
		
		JPanel phoneBox = new JPanel();
		phoneBox.setLayout(new GridLayout(1, 5, 5, 0));
		phoneBox.add(tfPhone1);
		phoneBox.add(lPhoneBar1);
		phoneBox.add(tfPhone2);
		phoneBox.add(lPhoneBar2);
		phoneBox.add(tfPhone3);
		
		JPanel inputs, labels;
		labels = new JPanel();
		labels.setLayout(new GridLayout(9,1,10,10));
		labels.add(lId);
		labels.add(lPwd1);
		labels.add(lPwd2);
		labels.add(lName);
		labels.add(lBirth);
		labels.add(lGender);
		labels.add(lPhone);
		labels.add(lAddr);
		labels.add(lTeacher);
		inputs = new JPanel();
		inputs.setLayout(new GridLayout(9,1,10,10));
		inputs.add(idBox);
		inputs.add(pfPwd1);
		inputs.add(pfPwd2);
		inputs.add(tfName);
		inputs.add(birthBox);
		inputs.add(cbGender);
		inputs.add(phoneBox);
		inputs.add(tfAddr);
		inputs.add(cbTeacher);
		
		formPanel.add("North", new JLabel("*는 필수 입력사항입니다."));
		formPanel.add("Center", inputs);
		formPanel.add("West", labels);
		
		btnsPanel = new JPanel();
		
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout());
		setSize(450, 550);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
	
	// 이벤트 리스너 등록
	public void eventInit() {
		BtnIdCheck.addActionListener(this);
		btnSignIn.addActionListener(this);
		btnUpdate.addActionListener(this);
		cbBirthYear.addActionListener(this);
		cbBirthMonth.addActionListener(this);
	}
	
	// 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == BtnIdCheck) {
			System.out.println("[중복 ID 검사]");
			isIdChecked = true;
			
			checkID();
		} else if (ob == btnSignIn) {
			System.out.println("[등록 버튼 클릭]");
			signUp();
		} else if (ob == btnUpdate) {
			System.out.println("[수정 버튼 클릭]");
			Update();
		} else if (ob == cbBirthYear || ob == cbBirthMonth) {
			System.out.println("[연 또는 월 콤보박스 클릭]");
			setBirthDays();
		}
	}
	
	// 아이디 중복 검사
	public void checkID() {
		String inputId = tfId.getText().trim();
		
		if(!inputId.equals("")) {
			ArrayList<DB_MemberDTO> selectedMember = memberDao.selectIdCheck(inputId);
			
			if(selectedMember.isEmpty()) {
				isIdUseable = true;
				isIdChecked = true;
				checkedName = tfId.getText().trim();
				
				System.out.println("[현재 제출된 ID:" + presentName + ", 중복체크 완료된 아이디:" + checkedName + "]");
				JOptionPane.showMessageDialog(this, "사용 가능한 ID입니다.", "중복 ID 검사 성공", JOptionPane.INFORMATION_MESSAGE);
				
				return;
			} else {
				isIdUseable = false;
				isIdChecked = true;
				
				JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다.",  "중복 ID 검사 경고", JOptionPane.WARNING_MESSAGE);
				
				return;
			}
		} else {
			isIdUseable = false;
			isIdChecked = false;
			
			JOptionPane.showMessageDialog(this, "ID를 입력해주세요.", "중복 ID 검사 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
	}
	
	// 회원 가입
	public void signUp() {
		DB_MemberDTO newUser = new DB_MemberDTO();
		
		newUser.m_id = tfId.getText().trim();
		newUser.pwd = new String(pfPwd1.getPassword()).trim();
		newUser.m_name = tfName.getText().trim();
		if(cbBirthYear.getSelectedItem() != null && cbBirthMonth.getSelectedItem() != null && cbBirthDay.getSelectedItem() != null) {
			newUser.birth = cbBirthYear.getSelectedItem().toString() + cbBirthMonth.getSelectedItem().toString() + cbBirthDay.getSelectedItem().toString();
		}
		newUser.gender = (String)cbGender.getSelectedItem();
		newUser.phone = tfPhone1.getText().trim() + tfPhone2.getText().trim() + tfPhone3.getText().trim();
		newUser.addr = tfAddr.getText().trim();
		newUser.m_teacher = (String)cbTeacher.getSelectedItem();
		
		presentName = tfId.getText().trim();
		
		System.out.println("[입력된 사용자 정보]\n└> " + newUser.toString());
		
		if(!newUser.isFull()) {
			JOptionPane.showMessageDialog(this, "기재사항을 확인해주세요.", "기재사항 경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(!isIdChecked || !(checkedName.equals(presentName))) {
			System.out.println("[현재 제출된 ID:" + presentName + "!= 중복체크 완료된 아이디:" + checkedName + "]");
			JOptionPane.showMessageDialog(this, "ID 중복을 확인해주세요.", "중복 ID 검사 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if(!isIdUseable) {
			isIdChecked=false;
			JOptionPane.showMessageDialog(this, "중복된 ID입니다. ID를 다시 입력해주세요.", "중복 ID 검사 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if (!newUser.getPwd().equals(new String(pfPwd2.getPassword()).trim())) {
			JOptionPane.showMessageDialog(this, "PassWord를 정확히 입력해주세요.", "비밀번호 불일치 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}

		if ((cbBirthYear.getSelectedItem() == null || cbBirthMonth.getSelectedItem() == null || cbBirthDay.getSelectedItem() == null)
				&& !(cbBirthYear.getSelectedItem() == null && cbBirthMonth.getSelectedItem() == null && cbBirthDay.getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(this, "생년월일을 정확히 입력해주세요.", "생년월일 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if(!newUser.getPhone().isEmpty()) {
			if(tfPhone1.getText().length() != 3) {
				JOptionPane.showMessageDialog(this, "핸드폰 식별번호는 3자리만 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
				
				return;
			} else if(tfPhone2.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "핸드폰 앞자리는 4자리만 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
				
				return;
			} else if(tfPhone3.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "핸드폰 뒷자리는 4자리만 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
				
				return;
			}
		}
		
		if(!newUser.getPhone().isEmpty() && newUser.getPhone().length() != 11) {
			JOptionPane.showMessageDialog(this, "전화번호를 정확히 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		try {
			int result = memberDao.insertMember(newUser);
			if(result == 1) {
				JOptionPane.showMessageDialog(this, "새로 가입되었습니다.", "회원 가입 성공", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			}
		} catch (Exception e) {
			System.err.println("[SignForm 예외 : 신규 회원 가입 실패]");
			e.printStackTrace();
		}
	}
	
	// 회원 정보 수정
	public void Update() {
		DB_MemberDTO updatedUser = new DB_MemberDTO();
		
		updatedUser.m_id = tfId.getText().trim();
		updatedUser.pwd = new String(pfPwd1.getPassword()).trim();
		updatedUser.m_name = tfName.getText().trim();
		if(cbBirthYear.getSelectedItem() != null && cbBirthMonth.getSelectedItem() != null && cbBirthDay.getSelectedItem() != null) {
			updatedUser.birth = cbBirthYear.getSelectedItem().toString() + cbBirthMonth.getSelectedItem().toString() + cbBirthDay.getSelectedItem().toString();
		}
		updatedUser.gender = (String)cbGender.getSelectedItem();
		updatedUser.phone = tfPhone1.getText().trim() + tfPhone2.getText().trim() + tfPhone3.getText().trim();
		updatedUser.addr = tfAddr.getText().trim();
		updatedUser.m_teacher = (String)cbTeacher.getSelectedItem();
		
		if(!updatedUser.isFull()) {
			JOptionPane.showMessageDialog(this, "기재사항을 확인해주세요.", "기재사항 경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (!updatedUser.getPwd().equals(new String(pfPwd2.getPassword()).trim())) {
			JOptionPane.showMessageDialog(this, "PassWord를 정확히 입력해주세요.", "비밀번호 불일치 경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if ((cbBirthYear.getSelectedItem() == null || cbBirthMonth.getSelectedItem() == null || cbBirthDay.getSelectedItem() == null)
				&& !(cbBirthYear.getSelectedItem() == null && cbBirthMonth.getSelectedItem() == null && cbBirthDay.getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(this, "생년월일을 정확히 입력해주세요.", "생년월일 경고", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if(!updatedUser.getPhone().isEmpty()) {
			if(tfPhone1.getText().length() != 3) {
				JOptionPane.showMessageDialog(this, "핸드폰 식별번호는 3자리만 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
				return;
			} else if(tfPhone2.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "핸드폰 앞자리는 4자리만 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
				return;
			} else if(tfPhone3.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "핸드폰 뒷자리는 4자리만 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		if(!updatedUser.getPhone().isEmpty() && updatedUser.getPhone().length() != 11) {
			JOptionPane.showMessageDialog(this, "전화번호를 정확히 입력해주세요.", "전화번호 경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			int result = memberDao.updateMember(updatedUser);
			
			if(result == 1) {
				if(am != null) {
					am.showTables();	// 관리자 회원 테이블을 새로고침
				} else if(mm != null) {	
					mm.getUserInfo();	// 일반 회원 회원 테이블을 새로고침
				}
				
				JOptionPane.showMessageDialog(this, "회원 정보가 수정되었습니다.");
				setVisible(false);
				dispose();
			}
		} catch (Exception e) {
			System.err.println("[SignForm 예외 : 회원 정보 수정 실패]");
			e.printStackTrace();
		}
	}

	// 연월에 해당하는 일 표시
	public void setBirthDays() {
		if(cbBirthYear.getSelectedItem() != null && cbBirthMonth.getSelectedItem() != null) {
			String selectedYear = cbBirthYear.getSelectedItem().toString();
			
			int monthArray[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
			if (Integer.parseInt(selectedYear) % 4 == 0 && Integer.parseInt(selectedYear) % 100 != 0 || Integer.parseInt(selectedYear) % 400 == 0) {
				monthArray[1] = 29; // 윤년인 경우 2월달을 29일로 세팅
			} else {
				monthArray[1] = 28; // 윤년이 아닌경우 2월달의 날짜를 28일로 세팅함
			} 
			
			cbBirthDay.removeAllItems();
			cbBirthDay.addItem(null);
			switch(cbBirthMonth.getSelectedItem().toString()) {
			case "01":
				for(int i = 1; i <= monthArray[0]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "02":
				for(int i = 1; i <= monthArray[1]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "03":
				for(int i = 1; i <= monthArray[2]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "04":
				for(int i = 1; i <= monthArray[3]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "05":
				for(int i = 1; i <= monthArray[4]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "06":
				for(int i = 1; i <= monthArray[5]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "07":
				for(int i = 1; i <= monthArray[6]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "08":
				for(int i = 1; i <= monthArray[7]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "09":
				for(int i = 1; i <= monthArray[8]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "10":
				for(int i = 1; i <= monthArray[9]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "11":
				for(int i = 1; i <= monthArray[10]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			case "12":
				for(int i = 0; i <= monthArray[11]; i++) {
					cbBirthDay.addItem(String.format("%02d", i));
				}
				break;
			}
		}
	}
}