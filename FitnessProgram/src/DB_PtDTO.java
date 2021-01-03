public class DB_PtDTO {
//	create table pt (
//			e_id    varchar2(15)    primary key,
//			e_name  varchar2(30)    not null,
//			time    varchar2(20)    not null,
//			e_teacher       varchar2(10)    not null,
//			price   number(7)       not null
//			);
	
	String e_id;
	String e_name;
	String time;
	String e_teacher;
	int price;

	public String getE_id() {
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
	}


	public String getE_name() {
		return e_name;
	}


	public void setE_name(String e_name) {
		this.e_name = e_name;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getE_teacher() {
		return e_teacher;
	}


	public void setE_teacher(String e_teacher) {
		this.e_teacher = e_teacher;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "PtDTO [e_id=" + e_id + ", e_name=" + e_name + ", time=" + time + ", e_teacher=" + e_teacher + ", price="
				+ price + "]";
	}
}
