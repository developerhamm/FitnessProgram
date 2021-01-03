public class DB_ReDTO {
	String r_id;
	String rm_id;
	String re_id;

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public String getRm_id() {
		return rm_id;
	}

	public void setRm_id(String rm_id) {
		this.rm_id = rm_id;
	}

	public String getRe_id() {
		return re_id;
	}

	public void setRe_id(String re_id) {
		this.re_id = re_id;
	}

	@Override
	public String toString() {
		return "ReDTO [r_id=" + r_id + ", rm_id=" + rm_id + ", re_id=" + re_id + "]";
	}
}
