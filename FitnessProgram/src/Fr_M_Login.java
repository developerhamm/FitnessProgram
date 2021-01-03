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

	// �α��� ȭ�� ������
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
	
	// ���̾ƿ� ����
	public void layoutInit() {
		setLayout(new BorderLayout(10, 10));
		lID = new JLabel("���̵�", JLabel.CENTER);
		lPwd = new JLabel("��й�ȣ", JLabel.CENTER);
		lPwd.setSize(50, 70);
		tfId = new JTextField(10);
		tfId.setToolTipText("ID");
		pfPwd = new JPasswordField(10);
		pfPwd.setToolTipText("Password");
		BtnSignUp = new JButton("ȸ�� ����");
		BtnLogin = new JButton("�α���");
		
		iiLogin = new ImageIcon("./img/exercise.png");
		lImg = new JLabel(new ImageIcon(iiLogin.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
		lTitle = new JLabel("THE SIX FITNESS", JLabel.CENTER);
		lTitle.setFont(new Font("���� ���", Font.BOLD, 30));
		
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
		
		setTitle("The Six ��Ʈ�Ͻ� �α���");
		setSize(400, 600);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// �̺�Ʈ ������ ���
	public void eventInit() {
		BtnSignUp.addActionListener(this);
		BtnLogin.addActionListener(this);
	}

	// �̺�Ʈ ó��
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == BtnSignUp) {
			System.out.println("[ȸ�� ���� ��ư Ŭ��]");
			signUp = new Fr_D_SignForm();
		} else if (ob == BtnLogin) {
			System.out.println("[�α��� ��ư Ŭ��]");
			loginCheck();
		}
	}
	
	// �α��� �˻�/ó��
	public void loginCheck() {
		String id = tfId.getText().trim();
		String pwd = new String(pfPwd.getPassword()).trim();
		ArrayList<DB_MemberDTO> loginUser = new ArrayList<DB_MemberDTO>();
		
		// ID �Է� Ȯ��
		if (id.length() == 0) {
			// ID ���Է½� ���
			JOptionPane.showMessageDialog(this, "ID�� �Է��ϼ���.", "�α��� �˸�", JOptionPane.INFORMATION_MESSAGE);
			pfPwd.setText(null);
			tfId.requestFocus(); // Focus �����ֱ�.
			return;
		} else if (pwd.length() == 0) {
			// ID �Է½� ��й�ȣ �˻�. ��й�ȣ ���Է½� ���
			JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է��ϼ���.", "�α��� �˸�", JOptionPane.INFORMATION_MESSAGE);
			pfPwd.requestFocus();
			return;
		}
		System.out.println("�Էµ� ����� ���� => {ID=" + id + ", Pwd=" + pwd + "}");
		
		// ID�� ��й�ȣ�� ��� �Է��ߴٸ�, member ���̺� ������ ����/��ġ�ϴ��� Ȯ���Ѵ�.
		
		try {	// ID�� member ���̺� �����ϴ��� Ȯ��
			loginUser = memberDao.selectIdCheck(id);
			
			if(!loginUser.isEmpty()) {	// ����Ʈ�� ������� ���� ���, ID�� �����Ѵ�.
				loginUser = memberDao.selectPwdCheck(id, pwd);
				
				if(!loginUser.isEmpty()) {	// ����Ʈ�� ������� ���� ���, ID�� �����Ѵ�.
					System.out.print("[�α��� ���� ");
					
					if(id.equals("admin")) {	// �α��� ������ �����ڶ�� ������ ȭ�� ǥ��
						System.out.println("�α��� ���� : ������]");
						System.out.println("=================================================================================");
						dispose();
						new Fr_M_AdminMain(id);
					} else {	// �α��� ������ �Ϲ� ȸ���̸� ȸ�� ȭ�� ǥ��
						System.out.println("�α��� ���� : �Ϲ� ȸ��]");
						System.out.println("=================================================================================");
						dispose();
						new Fr_M_MemberMain(id);
					}
					
					dispose();	// �α��� â�� �ݴ´�
				} else {
					System.err.println("[Login ��� : ��ġ���� �ʴ� ��й�ȣ]");
					JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "�α��� ���", JOptionPane.WARNING_MESSAGE);
					pfPwd.requestFocus();
				}
			} else {
				System.err.println("[Login ��� : �������� �ʴ� ���̵�]");
				JOptionPane.showMessageDialog(this, "�������� �ʴ� ���̵��Դϴ�.", "�α��� ���", JOptionPane.WARNING_MESSAGE);
				tfId.requestFocus();
			}
		} catch (Exception e) {
			System.err.println("[Login ���� : ID, pass �˻� ����]");
			e.printStackTrace();
		}
	}
	
	// ���� �޼ҵ�(�α��� ȭ��)
	public static void main(String[] args) {
		new Fr_M_Login();
	}
}
