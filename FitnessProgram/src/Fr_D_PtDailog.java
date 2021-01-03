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
	
	// 편집 다이얼로그 생성
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
	// Memeber의 운동 신청을 위한 생성자
	public Fr_D_PtDailog(Fr_M_MemberMain memberMain, String index) {
		super(memberMain, "운동 신청");
		
		// 편집 다이얼로그 생성
		ptwe = new JPanel(new GridLayout(5, 1));
		ptcen = new JPanel(new GridLayout(5, 1));
		ptsout = new JPanel();
		
		timeCombo = new String[] {"월요일 오전", "월요일 오후", "화요일 오전", "화요일 오후", "수요일 오전", "수요일 오후",
				 "목요일 오전", "목요일 오후", "금요일 오전", "금요일 오후", "토요일 오전", "토요일 오후", "일요일 오전", "일요일 오후"};
		combo = new JComboBox<String>(timeCombo);
		
		pt_id = new JLabel("PT ID :  ");
		ptEname = new JLabel("운동명 :  ");
		ptTime = new JLabel("시간대 :  ");
		ptEteacher = new JLabel("담당 강사 : ");
		ptEprice = new JLabel("가 격 :  ");
		
		ptTxtId = new JTextField(10);
		ptTxtName = new JTextField(10);
		ptTxtTeacher = new JTextField(10);
		ptTxtPrice = new JTextField(10);
		
		ptEixt = new JButton("취 소");
		
		medao = new DB_MemberDAO();
		dao = new DB_PtDAO();
		redao = new DB_ReJTable2DAO();
		
		this.userID = memberMain.userID;
		this.ptMem = memberMain;
		
		ptmemin = new JButton();
		
		// 텍스트필드의 값을 가져와서 다이얼로그로 뿌려주기
		
		if (index.equals("운동 신청")) {
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
		pt_id.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptEname);
		ptEname.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptTime);
		ptTime.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptEteacher);
		ptEteacher.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptEprice);
		ptEprice.setFont(new Font("고딕체", Font.BOLD, 13));
		
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
		
		System.out.println("멤버 프레임 생성자 끝남");
	}
	
	// Admin의 모든 버튼 작동을 위한 생성자
	public Fr_D_PtDailog(Fr_M_AdminMain adminMain, String index) {
		super(adminMain, "PT");
		
		// 편집 다이얼로그 생성
		ptwe = new JPanel(new GridLayout(5, 1));
		ptcen = new JPanel(new GridLayout(5, 1));
		ptsout = new JPanel();
		
		timeCombo = new String[] {"월요일 오전", "월요일 오후", "화요일 오전", "화요일 오후", "수요일 오전", "수요일 오후",
				 "목요일 오전", "목요일 오후", "금요일 오전", "금요일 오후", "토요일 오전", "토요일 오후", "일요일 오전", "일요일 오후"};
		combo = new JComboBox<String>(timeCombo);
		
		pt_id = new JLabel("PT ID :  ");
		ptEname = new JLabel("운동명 :  ");
		ptTime = new JLabel("시간대 :  ");
		ptEteacher = new JLabel("담당 강사 : ");
		ptEprice = new JLabel("가 격 :  ");
		
		ptTxtId = new JTextField(10);
		ptTxtName = new JTextField(10);
		ptTxtTeacher = new JTextField(10);
		ptTxtPrice = new JTextField(10);
		
		ptEixt = new JButton("취 소");
		
		medao = new DB_MemberDAO();
		dao = new DB_PtDAO();
		redao = new DB_ReJTable2DAO();
		
		this.ptAd = adminMain;
		
		ptmemin = new JButton();
		ptInUpDe = new JButton();
		
		// 값에 따라 바뀌는 다이얼로그 버튼
		// 텍스트 필드의 값을 가져온다
		if (index.equals("운동 등록")) {
			ptInUpDe = new JButton(index);
			ptTxtId.setEditable(false);
			
		} else if(index.equals("운동 수정")) {
			ptInUpDe = new JButton(index);
			int row = adminMain.mTable.getSelectedRow();
			ptTxtId.setText(adminMain.mTable.getValueAt(row, 0).toString());
			ptTxtName.setText(adminMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(adminMain.mTable.getValueAt(row, 2).toString());
			ptTxtTeacher.setText(adminMain.mTable.getValueAt(row, 3).toString());
			ptTxtPrice.setText(adminMain.mTable.getValueAt(row, 4).toString());
		
			ptTxtId.setEditable(false);
		
		} else if(index.equals("운동 삭제")){
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
		pt_id.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptEname);
		ptEname.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptTime);
		ptTime.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptEteacher);
		ptEteacher.setFont(new Font("고딕체", Font.BOLD, 13));
		ptwe.add(ptEprice);
		ptEprice.setFont(new Font("고딕체", Font.BOLD, 13));
		
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
		
		// 텍스트필드로 등록 및 수정 삭제하는 액션 퍼폼
		if (btnPt.equals("운동 등록")) {
			DB_PtDTO dto = new DB_PtDTO();
			dto.setE_name(ptTxtName.getText());
			dto.setTime(combo.getSelectedItem().toString());
			dto.setE_teacher(ptTxtTeacher.getText());
			try {
				if(ptTxtPrice.getText().length() > 7) {
					messageBox(this, "금액은 최대 백만 단위까지만 가능합니다.");
					return;
				}
				dto.setPrice(Integer.parseInt(ptTxtPrice.getText()));
			} catch(Exception e1) {
				messageBox(this, "금액은 숫자로 입력해주세요.");
				
				return;
			}
			
			//중복된 운동 데이터 확인후 넣기
			ArrayList<DB_PtDTO> list = dao.InsertCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText(), Integer.parseInt(ptTxtPrice.getText()));
			if (list.isEmpty()) {
				dao.ptInsert(dto);
				messageBox(this, "운동이 등록 되었습니다.");
				ptAd.pttotal.setText("전체 상품 수 : " + dao.ptTotalshow());
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
				messageBox(this, "중복된 사항이 있습니다. \n 확인 하시길 바랍니다.");
			}
			// 다이얼로그로 수정
		} else if (btnPt.equals("운동 수정")) {
			DB_PtDTO dto = new DB_PtDTO();
			dto.setE_id(ptTxtId.getText());
			dto.setE_name(ptTxtName.getText());
			dto.setTime(combo.getSelectedItem().toString());
			dto.setE_teacher(ptTxtTeacher.getText());
			try {
				if(ptTxtPrice.getText().length() > 7) {
					messageBox(this, "금액은 최대 백만 단위까지만 가능합니다.");
					return;
				}
				dto.setPrice(Integer.parseInt(ptTxtPrice.getText()));
			} catch(Exception e1) {
				messageBox(this, "금액은 숫자로 입력해주세요.");
				
				return;
			}
			
//			ArrayList<DB_PtDTO> list = dao.InsertCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText());
//			ArrayList<DB_PtDTO> list = dao.InsertCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText(), Integer.parseInt(ptTxtPrice.getText()));
			ArrayList<DB_PtDTO> list = dao.UpdateCheck(ptTxtName.getText(), combo.getSelectedItem().toString(), ptTxtTeacher.getText(), Integer.parseInt(ptTxtPrice.getText()));
			if (list.isEmpty()) {
				if (dao.ptUpdate(dto) > 0) {
					messageBox(this, "수정이 완료되었습니다.");
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
					messageBox(this, "수정이 올바르게 되지 않았습니다.");
				}
			} else {
				messageBox(this, "중복된 사항이 있습니다. \n 확인 하시길 바랍니다.");
			}
			
		// 다이얼로그로 삭제
		} else if (btnPt.equals("운동 삭제")){
			int t = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?\n삭제시 해당 운동과 관련된 예약 정보도 함께 삭제됩니다.", "운동 삭제 경고", JOptionPane.OK_CANCEL_OPTION);
			if (t == JOptionPane.OK_OPTION) {
				DB_PtDTO dto = new DB_PtDTO();
				dto.setE_id(ptTxtId.getText());	
				// 운동삭제 할 때 삭제할 운동을 예약한 회원 예약상품도 같이 삭제하게 만듬
				redao.ptreDelete(ptTxtId.getText());
				redao.showListadmre(ptAd.redt2);
				if (dao.ptDelete(dto) > 0) {
					messageBox(this, "삭제 되었습니다.");
					ptAd.pttotal.setText("전체 상품 수 : " + dao.ptTotalshow());
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
					System.out.println("운동 삭제 완료");
				} else {
					messageBox(this, "삭제 되지 않았습니다.");
				}
			}
			// 운동 신청 할때 예약이 중복된 운동id값 체크 후 insert
		} else if (btnPt.equals("운동 신청")) {			
			ArrayList<DB_ReDTO> list = redao.ptInsertCheck(userID, ptTxtId.getText());
			System.out.println(userID);
			System.out.println(ptTxtId.getText());
			System.out.println(list.size() + " : 중복 체크 값");
			if (list.isEmpty()) {
				messageBox(this, "운동 신청이 되었습니다.");
				dispose();
				redao.ptReinsert(userID, ptTxtId.getText());
				redao.showListmemre(ptMem.redt2, userID);	// 운동 신청 후에 회원의 예약 목록 갱신
				ptMem.lTotalRe.setText("전체 예약수 : " + redao.selectMemReCnt(userID));
				ptMem.getUserInfo();
			} else {
				messageBox(this, "해당 시간대에 예약된 운동이 있습니다.");
			}
			System.out.println("운동 신청 작동");
		} else if (btnPt.equals("취 소")) {
			dispose();
		}
	}
	
	
	public static void messageBox(Object obj, String msg) {
		JOptionPane.showMessageDialog((Component) obj, msg);
	}

}
