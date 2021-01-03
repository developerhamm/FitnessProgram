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
	
	// ȸ�� ���� ȭ�� ������
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
		
		setTitle("ȸ�� ����");
		setVisible(true);
	}
	
	// ȸ�� ���� ���� ȭ��(������) ������
	public Fr_D_SignForm(DB_MemberDTO user, Fr_M_AdminMain am) {
		// ������ ȭ���� ���̺��� �����ϱ� ���ؼ�, �������� AdminMain Ŭ������ �޾ƿ´�.
		memberDao = new DB_MemberDAO();
		ptDao = new DB_PtDAO();
		this.am = am;	// ȸ������ ȭ���� �� ���θ��� ȭ���� ����(���)�Ѵ�.
		ArrayList<DB_MemberDTO> dtoSelectedMember = new ArrayList<DB_MemberDTO>();
		
		layoutInit();
		btnsPanel.add(btnUpdate);
		mainPanel.add("Center", formPanel);
		mainPanel.add("South", btnsPanel);
		
		add(mainPanel);
		
		eventInit();
		
		dtoSelectedMember = memberDao.selectGetMember(user.getM_id());
		DB_MemberDTO SelectedMemberInfo = dtoSelectedMember.get(0);
		
		System.out.println("[" + SelectedMemberInfo.getM_id() + " ȸ�� ���� ȭ�� ǥ��]");
		
		// ȸ�� ���� �����ϱ� ����, ���� ȸ�� ������ ȭ�鿡 ǥ���ϱ�
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
		
		// �����ϸ� �ȵǴ� ������ ������ ����
		tfId.setEnabled(false);
		BtnIdCheck.setEnabled(false);
	
		setTitle("ȸ�� ���� ����");
		setVisible(true);
	}
	
	// ȸ�� ���� ���� ȭ��(ȸ��) ������
	public Fr_D_SignForm(DB_MemberDTO user, Fr_M_MemberMain mm) {
		// ȸ�� ȭ���� ���̺��� �����ϱ� ���ؼ�, �������� MemberMain Ŭ������ �޾ƿ´�.
		memberDao = new DB_MemberDAO();
		ptDao = new DB_PtDAO();
		this.mm = mm;	// ȸ������ ȭ���� �� ������� ȭ���� ����(���)�Ѵ�.
		ArrayList<DB_MemberDTO> dtoSelectedMember = new ArrayList<DB_MemberDTO>();
		
		layoutInit();
		btnsPanel.add(btnUpdate);
		mainPanel.add("Center", formPanel);
		mainPanel.add("South", btnsPanel);
		
		add(mainPanel);
		
		eventInit();
		
		dtoSelectedMember = memberDao.selectGetMember(user.getM_id());
		DB_MemberDTO SelectedMemberInfo = dtoSelectedMember.get(0);
		
		System.out.println("[" + SelectedMemberInfo.getM_id() + " ȸ�� ���� ȭ�� ǥ��]");
		
		// ȸ�� ���� �����ϱ� ����, ���� ȸ�� ������ ȭ�鿡 ǥ���ϱ�
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
		
		// �����ϸ� �ȵǴ� ������ ������ ����
		tfId.setEnabled(false);
		BtnIdCheck.setEnabled(false);
	
		setTitle("ȸ�� ���� ����");
		setVisible(true);
	}
	
	// ���̾ƿ� ����
	public void layoutInit() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		lId = new JLabel("���̵�*", JLabel.CENTER);
		lPwd1 = new JLabel("��й�ȣ*", JLabel.CENTER);
		lPwd2 = new JLabel("���Ȯ��*", JLabel.CENTER);
		lName = new JLabel("�̸�*", JLabel.CENTER);
		lBirth = new JLabel("�������", JLabel.CENTER);
		lBirthYear = new JLabel("��", JLabel.CENTER);
		lBirthMonth = new JLabel("��", JLabel.CENTER);
		lBirthDay = new JLabel("��", JLabel.CENTER);
		lGender = new JLabel("����", JLabel.CENTER);
		lRrnBar = new JLabel("��", JLabel.CENTER);
		lPhone = new JLabel("��ȭ��ȣ", JLabel.CENTER);
		lAddr = new JLabel("�ּ�", JLabel.CENTER);
		lTeacher = new JLabel("��簭��", JLabel.CENTER);
		lPhoneBar1 = new JLabel("��", JLabel.CENTER);
		lPhoneBar2 = new JLabel("��", JLabel.CENTER);
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
		cbGender.addItem("��");
		cbGender.addItem("��");
		tfPhone1 = new JTextField(3);
		tfPhone2 = new JTextField(4);
		tfPhone3 = new JTextField(4);
		tfAddr = new JTextField(25);
		BtnIdCheck = new JButton("�ߺ� Ȯ��");
		btnSignIn = new JButton("���");
		btnUpdate = new JButton("����");
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
		
		formPanel.add("North", new JLabel("*�� �ʼ� �Է»����Դϴ�."));
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
	
	// �̺�Ʈ ������ ���
	public void eventInit() {
		BtnIdCheck.addActionListener(this);
		btnSignIn.addActionListener(this);
		btnUpdate.addActionListener(this);
		cbBirthYear.addActionListener(this);
		cbBirthMonth.addActionListener(this);
	}
	
	// �̺�Ʈ ó��
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == BtnIdCheck) {
			System.out.println("[�ߺ� ID �˻�]");
			isIdChecked = true;
			
			checkID();
		} else if (ob == btnSignIn) {
			System.out.println("[��� ��ư Ŭ��]");
			signUp();
		} else if (ob == btnUpdate) {
			System.out.println("[���� ��ư Ŭ��]");
			Update();
		} else if (ob == cbBirthYear || ob == cbBirthMonth) {
			System.out.println("[�� �Ǵ� �� �޺��ڽ� Ŭ��]");
			setBirthDays();
		}
	}
	
	// ���̵� �ߺ� �˻�
	public void checkID() {
		String inputId = tfId.getText().trim();
		
		if(!inputId.equals("")) {
			ArrayList<DB_MemberDTO> selectedMember = memberDao.selectIdCheck(inputId);
			
			if(selectedMember.isEmpty()) {
				isIdUseable = true;
				isIdChecked = true;
				checkedName = tfId.getText().trim();
				
				System.out.println("[���� ����� ID:" + presentName + ", �ߺ�üũ �Ϸ�� ���̵�:" + checkedName + "]");
				JOptionPane.showMessageDialog(this, "��� ������ ID�Դϴ�.", "�ߺ� ID �˻� ����", JOptionPane.INFORMATION_MESSAGE);
				
				return;
			} else {
				isIdUseable = false;
				isIdChecked = true;
				
				JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ID�Դϴ�.",  "�ߺ� ID �˻� ���", JOptionPane.WARNING_MESSAGE);
				
				return;
			}
		} else {
			isIdUseable = false;
			isIdChecked = false;
			
			JOptionPane.showMessageDialog(this, "ID�� �Է����ּ���.", "�ߺ� ID �˻� ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
	}
	
	// ȸ�� ����
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
		
		System.out.println("[�Էµ� ����� ����]\n��> " + newUser.toString());
		
		if(!newUser.isFull()) {
			JOptionPane.showMessageDialog(this, "��������� Ȯ�����ּ���.", "������� ���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(!isIdChecked || !(checkedName.equals(presentName))) {
			System.out.println("[���� ����� ID:" + presentName + "!= �ߺ�üũ �Ϸ�� ���̵�:" + checkedName + "]");
			JOptionPane.showMessageDialog(this, "ID �ߺ��� Ȯ�����ּ���.", "�ߺ� ID �˻� ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if(!isIdUseable) {
			isIdChecked=false;
			JOptionPane.showMessageDialog(this, "�ߺ��� ID�Դϴ�. ID�� �ٽ� �Է����ּ���.", "�ߺ� ID �˻� ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if (!newUser.getPwd().equals(new String(pfPwd2.getPassword()).trim())) {
			JOptionPane.showMessageDialog(this, "PassWord�� ��Ȯ�� �Է����ּ���.", "��й�ȣ ����ġ ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}

		if ((cbBirthYear.getSelectedItem() == null || cbBirthMonth.getSelectedItem() == null || cbBirthDay.getSelectedItem() == null)
				&& !(cbBirthYear.getSelectedItem() == null && cbBirthMonth.getSelectedItem() == null && cbBirthDay.getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(this, "��������� ��Ȯ�� �Է����ּ���.", "������� ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if(!newUser.getPhone().isEmpty()) {
			if(tfPhone1.getText().length() != 3) {
				JOptionPane.showMessageDialog(this, "�ڵ��� �ĺ���ȣ�� 3�ڸ��� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
				
				return;
			} else if(tfPhone2.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "�ڵ��� ���ڸ��� 4�ڸ��� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
				
				return;
			} else if(tfPhone3.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "�ڵ��� ���ڸ��� 4�ڸ��� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
				
				return;
			}
		}
		
		if(!newUser.getPhone().isEmpty() && newUser.getPhone().length() != 11) {
			JOptionPane.showMessageDialog(this, "��ȭ��ȣ�� ��Ȯ�� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		try {
			int result = memberDao.insertMember(newUser);
			if(result == 1) {
				JOptionPane.showMessageDialog(this, "���� ���ԵǾ����ϴ�.", "ȸ�� ���� ����", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			}
		} catch (Exception e) {
			System.err.println("[SignForm ���� : �ű� ȸ�� ���� ����]");
			e.printStackTrace();
		}
	}
	
	// ȸ�� ���� ����
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
			JOptionPane.showMessageDialog(this, "��������� Ȯ�����ּ���.", "������� ���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (!updatedUser.getPwd().equals(new String(pfPwd2.getPassword()).trim())) {
			JOptionPane.showMessageDialog(this, "PassWord�� ��Ȯ�� �Է����ּ���.", "��й�ȣ ����ġ ���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if ((cbBirthYear.getSelectedItem() == null || cbBirthMonth.getSelectedItem() == null || cbBirthDay.getSelectedItem() == null)
				&& !(cbBirthYear.getSelectedItem() == null && cbBirthMonth.getSelectedItem() == null && cbBirthDay.getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(this, "��������� ��Ȯ�� �Է����ּ���.", "������� ���", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		if(!updatedUser.getPhone().isEmpty()) {
			if(tfPhone1.getText().length() != 3) {
				JOptionPane.showMessageDialog(this, "�ڵ��� �ĺ���ȣ�� 3�ڸ��� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
				return;
			} else if(tfPhone2.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "�ڵ��� ���ڸ��� 4�ڸ��� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
				return;
			} else if(tfPhone3.getText().length() != 4) {
				JOptionPane.showMessageDialog(this, "�ڵ��� ���ڸ��� 4�ڸ��� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		if(!updatedUser.getPhone().isEmpty() && updatedUser.getPhone().length() != 11) {
			JOptionPane.showMessageDialog(this, "��ȭ��ȣ�� ��Ȯ�� �Է����ּ���.", "��ȭ��ȣ ���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			int result = memberDao.updateMember(updatedUser);
			
			if(result == 1) {
				if(am != null) {
					am.showTables();	// ������ ȸ�� ���̺��� ���ΰ�ħ
				} else if(mm != null) {	
					mm.getUserInfo();	// �Ϲ� ȸ�� ȸ�� ���̺��� ���ΰ�ħ
				}
				
				JOptionPane.showMessageDialog(this, "ȸ�� ������ �����Ǿ����ϴ�.");
				setVisible(false);
				dispose();
			}
		} catch (Exception e) {
			System.err.println("[SignForm ���� : ȸ�� ���� ���� ����]");
			e.printStackTrace();
		}
	}

	// ������ �ش��ϴ� �� ǥ��
	public void setBirthDays() {
		if(cbBirthYear.getSelectedItem() != null && cbBirthMonth.getSelectedItem() != null) {
			String selectedYear = cbBirthYear.getSelectedItem().toString();
			
			int monthArray[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
			if (Integer.parseInt(selectedYear) % 4 == 0 && Integer.parseInt(selectedYear) % 100 != 0 || Integer.parseInt(selectedYear) % 400 == 0) {
				monthArray[1] = 29; // ������ ��� 2������ 29�Ϸ� ����
			} else {
				monthArray[1] = 28; // ������ �ƴѰ�� 2������ ��¥�� 28�Ϸ� ������
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