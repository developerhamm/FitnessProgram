import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fr_D_PtDailog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -2937385415231613170L;
	
	// ���� ���̾�α� ����
	JPanel ptwe;
	JPanel ptcen;
	JPanel ptsout;
	
	String timeCombo[];
	JComboBox<String> combo;
	
	JLabel pt_id;
	JLabel ptEname;
	JLabel ptTime;
	JLabel ptEteacher;
	JLabel ptEprice;
	
	JTextField ptTxtId;
	JTextField ptTxtName;
	JTextField ptTxtTeacher;
	JTextField ptTxtPrice;
	
	JButton ptInUpDe;
	JButton ptmemin;
	JButton ptEixt;
	
	DB_MemberDAO medao;
	DB_PtDAO dao;
	DB_ReJTable2DAO redao;
	Fr_M_AdminMain ptAd;
	Fr_M_MemberMain ptMem;
	String userID;
	// Memeber�� � ��û�� ���� ������
	public Fr_D_PtDailog(Fr_M_MemberMain memberMain, String index) {
		super(memberMain, "� ��û");
		
		// ���� ���̾�α� ����
		ptwe = new JPanel(new GridLayout(5, 1));
		ptcen = new JPanel(new GridLayout(5, 1));
		ptsout = new JPanel();
		
		timeCombo = new String[] {"������ ����", "������ ����", "ȭ���� ����", "ȭ���� ����", "������ ����", "������ ����",
				 "����� ����", "����� ����", "�ݿ��� ����", "�ݿ��� ����", "����� ����", "����� ����", "�Ͽ��� ����", "�Ͽ��� ����"};
		combo = new JComboBox<String>(timeCombo);
		
		pt_id = new JLabel("PT ID :  ");
		ptEname = new JLabel("��� :  ");
		ptTime = new JLabel("�ð��� :  ");
		ptEteacher = new JLabel("��� ���� : ");
		ptEprice = new JLabel("�� �� :  ");
		
		ptTxtId = new JTextField(10);
		ptTxtName = new JTextField(10);
		ptTxtTeacher = new JTextField(10);
		ptTxtPrice = new JTextField(10);
		
		ptEixt = new JButton("�� ��");
		
		medao = new DB_MemberDAO();
		dao = new DB_PtDAO();
		redao = new DB_ReJTable2DAO();
		
		this.userID = memberMain.userID;
		this.ptMem = memberMain;
		
		ptmemin = new JButton();
		
		// �ؽ�Ʈ�ʵ��� ���� �����ͼ� ���̾�α׷� �ѷ��ֱ�
		
		if (index.equals("� ��û")) {
			ptmemin = new JButton(index);
			int row = memberMain.mTable.getSelectedRow();
			ptTxtId.setText(memberMain.mTable.getValueAt(row, 0).toString());
			ptTxtName.setText(memberMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(memberMain.mTable.getValueAt(row, 2).toString());
			ptTxtTeacher.setText(memberMain.mTable.getValueAt(row, 3).toString());
			ptTxtPrice.setText(memberMain.mTable.getValueAt(row, 4).toString());
			
			ptTxtId.setEditable(false);
			ptTxtName.setEditable(false);
			combo.setEditable(false);
			combo.setEnabled(false);
			ptTxtTeacher.setEditable(false);
			ptTxtPrice.setEditable(false);
		}
		
		ptwe.add(pt_id);
		pt_id.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptEname);
		ptEname.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptTime);
		ptTime.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptEteacher);
		ptEteacher.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptEprice);
		ptEprice.setFont(new Font("���ü", Font.BOLD, 13));
		
		ptcen.add(ptTxtId);
		ptcen.add(ptTxtName);
		ptcen.add(combo);
		ptcen.add(ptTxtTeacher);
		ptcen.add(ptTxtPrice);
		
		ptsout.add(ptmemin);
		ptsout.add(ptEixt);
		
		add(ptwe, "West");
		add(ptcen, "Center");
		add(ptsout, "South");
		
		setSize(300, 250);
		setVisible(true);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		ptmemin.addActionListener(this);
		ptEixt.addActionListener(this);	
		
		System.out.println("��� ������ ������ ����");
	}
	
	// Admin�� ��� ��ư �۵��� ���� ������
	public Fr_D_PtDailog(Fr_M_AdminMain adminMain, String index) {
		super(adminMain, "PT");
		
		// ���� ���̾�α� ����
		ptwe = new JPanel(new GridLayout(5, 1));
		ptcen = new JPanel(new GridLayout(5, 1));
		ptsout = new JPanel();
		
		timeCombo = new String[] {"������ ����", "������ ����", "ȭ���� ����", "ȭ���� ����", "������ ����", "������ ����",
				 "����� ����", "����� ����", "�ݿ��� ����", "�ݿ��� ����", "����� ����", "����� ����", "�Ͽ��� ����", "�Ͽ��� ����"};
		combo = new JComboBox<String>(timeCombo);
		
		pt_id = new JLabel("PT ID :  ");
		ptEname = new JLabel("��� :  ");
		ptTime = new JLabel("�ð��� :  ");
		ptEteacher = new JLabel("��� ���� : ");
		ptEprice = new JLabel("�� �� :  ");
		
		ptTxtId = new JTextField(10);
		ptTxtName = new JTextField(10);
		ptTxtTeacher = new JTextField(10);
		ptTxtPrice = new JTextField(10);
		
		ptEixt = new JButton("�� ��");
		
		medao = new DB_MemberDAO();
		dao = new DB_PtDAO();
		redao = new DB_ReJTable2DAO();
		
		this.ptAd = adminMain;
		
		ptmemin = new JButton();
		ptInUpDe = new JButton();
		
		// ���� ���� �ٲ�� ���̾�α� ��ư
		// �ؽ�Ʈ �ʵ��� ���� �����´�
		if (index.equals("� ���")) {
			ptInUpDe = new JButton(index);
			ptTxtId.setEditable(false);
			
		} else if(index.equals("� ����")) {
			ptInUpDe = new JButton(index);
			int row = adminMain.mTable.getSelectedRow();
			ptTxtId.setText(adminMain.mTable.getValueAt(row, 0).toString());
			ptTxtName.setText(adminMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(adminMain.mTable.getValueAt(row, 2).toString());
			ptTxtTeacher.setText(adminMain.mTable.getValueAt(row, 3).toString());
			ptTxtPrice.setText(adminMain.mTable.getValueAt(row, 4).toString());
		
			ptTxtId.setEditable(false);
		
		} else if(index.equals("� ����")){
			ptInUpDe = new JButton(index);
			int row = adminMain.mTable.getSelectedRow();
			ptTxtId.setText(adminMain.mTable.getValueAt(row, 0).toString());
			ptTxtName.setText(adminMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(adminMain.mTable.getValueAt(row, 2).toString());
			ptTxtTeacher.setText(adminMain.mTable.getValueAt(row, 3).toString());
			ptTxtPrice.setText(adminMain.mTable.getValueAt(row, 4).toString());
			
			ptTxtId.setEditable(false);
			ptTxtName.setEditable(false);
			combo.setEditable(false);
			combo.setEnabled(false);;
			ptTxtTeacher.setEditable(false);
			ptTxtPrice.setEditable(false);
		}
			
		ptwe.add(pt_id);
		pt_id.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptEname);
		ptEname.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptTime);
		ptTime.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptEteacher);
		ptEteacher.setFont(new Font("���ü", Font.BOLD, 13));
		ptwe.add(ptEprice);
		ptEprice.setFont(new Font("���ü", Font.BOLD, 13));
		
		ptcen.add(ptTxtId);
		ptcen.add(ptTxtName);
		ptcen.add(combo);
		ptcen.add(ptTxtTeacher);
		ptcen.add(ptTxtPrice);
		
		ptsout.add(ptInUpDe);
		ptsout.add(ptEixt);
		
		add(ptwe, "West");
		add(ptcen, "Center");
		add(ptsout, "South");
		
		setSize(300, 250);
		setVisible(true);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		ptInUpDe.addActionListener(this);
		ptmemin.addActionListener(this);
		ptEixt.addActionListener(this);	
}

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnPt = e.getActionCommand();
		
		// �ؽ�Ʈ�ʵ�� ��� �� ���� �����ϴ� �׼� ����
		if (btnPt.equals("� ���")) {
			DB_PtDTO dto = new DB_PtDTO();
			dto.setE_name(ptTxtName.getText());
			dto.setTime(combo.getSelectedItem().toString());
			dto.setE_teacher(ptTxtTeacher.getText());
			try {
				if(ptTxtPrice.getText().length() > 7) {
					messageBox(this, "�ݾ��� �ִ� �鸸 ���������� �����մϴ�.");
					return;
				}
				dto.setPrice(Integer.parseInt(ptTxtPrice.getText()));
			} catch(Exception e1) {
				messageBox(this, "�ݾ��� ���ڷ� �Է����ּ���.");
				
				return;
			}
			
			//�ߺ��� � ������ Ȯ���� �ֱ�
			ArrayList<DB_PtDTO> list = dao.InsertCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText(), Integer.parseInt(ptTxtPrice.getText()));
			if (list.isEmpty()) {
				dao.ptInsert(dto);
				messageBox(this, "��� ��� �Ǿ����ϴ�.");
				ptAd.pttotal.setText("��ü ��ǰ �� : " + dao.ptTotalshow());
				dispose();
				for (int i = 0; i < ptAd.ptDt.getRowCount();) {
					ptAd.ptDt.removeRow(0);
				}
				ArrayList<DB_PtDTO> ptdto = dao.ptShowList();
				for(int i=0; i < ptdto.size(); i++) {
					String[] temp = new String[5];
					temp[0] = ptdto.get(i).getE_id();
					temp[1] = ptdto.get(i).getE_name();
					temp[2] = ptdto.get(i).getTime();
					temp[3] = ptdto.get(i).getE_teacher();
					temp[4] = Integer.toString(ptdto.get(i).getPrice());	
					ptAd.ptDt.addRow(temp);
				}
			} else {
				messageBox(this, "�ߺ��� ������ �ֽ��ϴ�. \n Ȯ�� �Ͻñ� �ٶ��ϴ�.");
			}
			// ���̾�α׷� ����
		} else if (btnPt.equals("� ����")) {
			DB_PtDTO dto = new DB_PtDTO();
			dto.setE_id(ptTxtId.getText());
			dto.setE_name(ptTxtName.getText());
			dto.setTime(combo.getSelectedItem().toString());
			dto.setE_teacher(ptTxtTeacher.getText());
			try {
				if(ptTxtPrice.getText().length() > 7) {
					messageBox(this, "�ݾ��� �ִ� �鸸 ���������� �����մϴ�.");
					return;
				}
				dto.setPrice(Integer.parseInt(ptTxtPrice.getText()));
			} catch(Exception e1) {
				messageBox(this, "�ݾ��� ���ڷ� �Է����ּ���.");
				
				return;
			}
			
//			ArrayList<DB_PtDTO> list = dao.InsertCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText());
//			ArrayList<DB_PtDTO> list = dao.InsertCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText(), Integer.parseInt(ptTxtPrice.getText()));
			ArrayList<DB_PtDTO> list = dao.UpdateCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText(), Integer.parseInt(ptTxtPrice.getText()));
			if (list.isEmpty()) {
				if (dao.ptUpdate(dto) > 0) {
					messageBox(this, "������ �Ϸ�Ǿ����ϴ�.");
					redao.showListadmre(ptAd.redt2);
					dispose();
					for (int i = 0; i < ptAd.ptDt.getRowCount();) {
						ptAd.ptDt.removeRow(0);
					}
					ArrayList<DB_PtDTO> ptdto = dao.ptShowList();
					for(int i=0; i < ptdto.size(); i++) {
						String[] temp = new String[5];
						temp[0] = ptdto.get(i).getE_id();
						temp[1] = ptdto.get(i).getE_name();
						temp[2] = ptdto.get(i).getTime();
						temp[3] = ptdto.get(i).getE_teacher();
						temp[4] = Integer.toString(ptdto.get(i).getPrice());	
						ptAd.ptDt.addRow(temp);
					}
				} else {
					messageBox(this, "������ �ùٸ��� ���� �ʾҽ��ϴ�.");
				}
			} else {
				messageBox(this, "�ߺ��� ������ �ֽ��ϴ�. \n Ȯ�� �Ͻñ� �ٶ��ϴ�.");
			}
			
		// ���̾�α׷� ����
		} else if (btnPt.equals("� ����")){
			int t = JOptionPane.showConfirmDialog(this, "������ �����Ͻðڽ��ϱ�?\n������ �ش� ��� ���õ� ���� ������ �Բ� �����˴ϴ�.", "� ���� ���", JOptionPane.OK_CANCEL_OPTION);
			if (t == JOptionPane.OK_OPTION) {
				DB_PtDTO dto = new DB_PtDTO();
				dto.setE_id(ptTxtId.getText());	
				// ����� �� �� ������ ��� ������ ȸ�� �����ǰ�� ���� �����ϰ� ����
				redao.ptreDelete(ptTxtId.getText());
				redao.showListadmre(ptAd.redt2);
				if (dao.ptDelete(dto) > 0) {
					messageBox(this, "���� �Ǿ����ϴ�.");
					ptAd.pttotal.setText("��ü ��ǰ �� : " + dao.ptTotalshow());
					medao.updateMemberbyDelTchr(ptTxtTeacher.getText());
					ptAd.showTables();
					dispose();
					for (int i = 0; i < ptAd.ptDt.getRowCount();) {
						ptAd.ptDt.removeRow(0);
					}
					ArrayList<DB_PtDTO> ptdto = dao.ptShowList();
					for(int i=0; i < ptdto.size(); i++) {
						String[] temp = new String[5];
						temp[0] = ptdto.get(i).getE_id();
						temp[1] = ptdto.get(i).getE_name();
						temp[2] = ptdto.get(i).getTime();
						temp[3] = ptdto.get(i).getE_teacher();
						temp[4] = Integer.toString(ptdto.get(i).getPrice());	
						ptAd.ptDt.addRow(temp);
					}
					System.out.println("� ���� �Ϸ�");
				} else {
					messageBox(this, "���� ���� �ʾҽ��ϴ�.");
				}
			}
			// � ��û �Ҷ� ������ �ߺ��� �id�� üũ �� insert
		} else if (btnPt.equals("� ��û")) {			
			ArrayList<DB_ReDTO> list = redao.ptInsertCheck(userID, ptTxtId.getText());
			System.out.println(userID);
			System.out.println(ptTxtId.getText());
			System.out.println(list.size() + " : �ߺ� üũ ��");
			if (list.isEmpty()) {
				messageBox(this, "� ��û�� �Ǿ����ϴ�.");
				dispose();
				redao.ptReinsert(userID, ptTxtId.getText());
				redao.showListmemre(ptMem.redt2, userID);	// � ��û �Ŀ� ȸ���� ���� ��� ����
				ptMem.lTotalRe.setText("��ü ����� : " + redao.selectMemReCnt(userID));
				ptMem.getUserInfo();
			} else {
				messageBox(this, "�ش� �ð��뿡 ����� ��� �ֽ��ϴ�.");
			}
			System.out.println("� ��û �۵�");
		} else if (btnPt.equals("�� ��")) {
			dispose();
		}
	}
	
	
	public static void messageBox(Object obj, String msg) {
		JOptionPane.showMessageDialog((Component) obj, msg);
	}

}
