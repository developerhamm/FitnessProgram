import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Fr_M_Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1445214176327987639L;
	
	JPanel loginInfoPanel, logoPanel, inputPanel, btnsPanel;
	JLabel lID, lPwd, lImg, lTitle;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton BtnSignUp, BtnLogin;
	ImageIcon iiLogin;
	
	DB_MemberDAO memberDao;
	Fr_D_SignForm signUp;

	// 로그인 화면 생성자
	public Fr_M_Login() {
		memberDao = new DB_MemberDAO();
		
		layoutInit();
		eventInit();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DB_DBConn.close();
				System.exit(0);
			}
		});
	}
	
	// 레이아웃 정의
	public void layoutInit() {
		setLayout(new BorderLayout(10, 10));
		lID = new JLabel("아이디", JLabel.CENTER);
		lPwd = new JLabel("비밀번호", JLabel.CENTER);
		lPwd.setSize(50, 70);
		tfId = new JTextField(10);
		tfId.setToolTipText("ID");
		pfPwd = new JPasswordField(10);
		pfPwd.setToolTipText("Password");
		BtnSignUp = new JButton("회원 가입");
		BtnLogin = new JButton("로그인");
		
		iiLogin = new ImageIcon("./img/exercise.png");
		lImg = new JLabel(new ImageIcon(iiLogin.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
		lTitle = new JLabel("THE SIX FITNESS", JLabel.CENTER);
		lTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		logoPanel = new JPanel();
		logoPanel.setLayout(new BorderLayout());
		logoPanel.add("Center", lImg);
		logoPanel.add("South", lTitle);
		
		loginInfoPanel = new JPanel();
		loginInfoPanel.setLayout(new BorderLayout(10, 10));
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,2,10,10));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 70));

		inputPanel.add(lID);
		inputPanel.add(tfId);
		inputPanel.add(lPwd);
		inputPanel.add(pfPwd);
		
		loginInfoPanel.add("Center", logoPanel);
		loginInfoPanel.add("South", inputPanel);
		
		btnsPanel = new JPanel();
		btnsPanel.setLayout(new FlowLayout());
		btnsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btnsPanel.add(BtnLogin);
		btnsPanel.add(BtnSignUp);
		
		add("Center", loginInfoPanel);
		add("South", btnsPanel);
		
		setTitle("The Six 피트니스 로그인");
		setSize(400, 600);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// 이벤트 리스너 등록
	public void eventInit() {
		BtnSignUp.addActionListener(this);
		BtnLogin.addActionListener(this);
	}

	// 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == BtnSignUp) {
			System.out.println("[회원 가입 버튼 클릭]");
			signUp = new Fr_D_SignForm();
		} else if (ob == BtnLogin) {
			System.out.println("[로그인 버튼 클릭]");
			loginCheck();
		}
	}
	
	// 로그인 검사/처리
	public void loginCheck() {
		String id = tfId.getText().trim();
		String pwd = new String(pfPwd.getPassword()).trim();
		ArrayList<DB_MemberDTO> loginUser = new ArrayList<DB_MemberDTO>();
		
		// ID 입력 확인
		if (id.length() == 0) {
			// ID 미입력시 경고
			JOptionPane.showMessageDialog(this, "ID를 입력하세요.", "로그인 알림", JOptionPane.INFORMATION_MESSAGE);
			pfPwd.setText(null);
			tfId.requestFocus(); // Focus 맞춰주기.
			return;
		} else if (pwd.length() == 0) {
			// ID 입력시 비밀번호 검사. 비밀번호 미입력시 경고
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.", "로그인 알림", JOptionPane.INFORMATION_MESSAGE);
			pfPwd.requestFocus();
			return;
		}
		System.out.println("입력된 사용자 정보 => {ID=" + id + ", Pwd=" + pwd + "}");
		
		// ID와 비밀번호를 모두 입력했다면, member 테이블에 정보가 존재/일치하는지 확인한다.
		
		try {	// ID가 member 테이블에 존재하는지 확인
			loginUser = memberDao.selectIdCheck(id);
			
			if(!loginUser.isEmpty()) {	// 리스트가 비어있지 않은 경우, ID가 존재한다.
				loginUser = memberDao.selectPwdCheck(id, pwd);
				
				if(!loginUser.isEmpty()) {	// 리스트가 비어있지 않은 경우, ID가 존재한다.
					System.out.print("[로그인 성공 ");
					
					if(id.equals("admin")) {	// 로그인 계정이 관리자라면 관리자 화면 표시
						System.out.println("로그인 계정 : 관리자]");
						System.out.println("=================================================================================");
						dispose();
						new Fr_M_AdminMain(id);
					} else {	// 로그인 계정이 일반 회원이면 회원 화면 표시
						System.out.println("로그인 계정 : 일반 회원]");
						System.out.println("=================================================================================");
						dispose();
						new Fr_M_MemberMain(id);
					}
					
					dispose();	// 로그인 창은 닫는다
				} else {
					System.err.println("[Login 경고 : 일치하지 않는 비밀번호]");
					JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "로그인 경고", JOptionPane.WARNING_MESSAGE);
					pfPwd.requestFocus();
				}
			} else {
				System.err.println("[Login 경고 : 존재하지 않는 아이디]");
				JOptionPane.showMessageDialog(this, "존재하지 않는 아이디입니다.", "로그인 경고", JOptionPane.WARNING_MESSAGE);
				tfId.requestFocus();
			}
		} catch (Exception e) {
			System.err.println("[Login 예외 : ID, pass 검색 실패]");
			e.printStackTrace();
		}
	}
	
	// 실행 메소드(로그인 화면)
	public static void main(String[] args) {
		new Fr_M_Login();
	}
}
