package helloClasses2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * @author JonTheZero!!!
 */
public class CSVEntry{
	private int length=5;
	private String firstname;
	private String lastname;
	private String department;
	private String reason;
	private LocalDate date;
	private String[] datas;
	public CSVEntry() {}
	public CSVEntry(String firstname,String lastname,String department,String reason,LocalDate date) {
		datas=new String[length];
		setFirstname(firstname);
		datas[0]=firstname;
		setLastname(lastname);
		datas[1]=lastname;
		setDepartment(department);
		datas[2]=department;
		setReason(reason);
		datas[3]=reason;
		setDate(date);
		datas[4]=date.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String[] toArray() {
		return datas;
	}
	public String[] getDatas() {
		return this.datas;
	}
	public String get(int index) {
		return datas[index];
	}
	@Override public String toString() {
		String str="";
		for(int i=0;i<datas.length;i++) {
			if(i!=(datas.length-1)) {
				str=str.concat(datas[i]+" ,");
			}else {
				str=str.concat(datas[i]);
			}		
		}
		return str;
	}
	public int getLength() {
		return this.length;
	}
	
}
