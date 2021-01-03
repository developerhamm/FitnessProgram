public class DB_MemberDTO {
	String m_id, pwd, m_name, birth, gender, phone, addr, joindate, m_teacher;

	boolean isFull() {
		if (m_id.length() == 0 || pwd.length() == 0 || m_name.length() == 0)
			return false;
		return true;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}

	public String getM_teacher() {
		return m_teacher;
	}

	public void setM_teacher(String m_teacher) {
		this.m_teacher = m_teacher;
	}

	@Override
	public String toString() {
		return "MemberDTO [m_id=" + m_id + ", pwd=" + pwd + ", m_name=" + m_name + ", birth=" + birth
				+ ", gender=" + gender + ", phone=" + phone + ", addr=" + addr + ", joindate=" + joindate
				+ ", m_teacher=" + m_teacher + "]";
	}
}