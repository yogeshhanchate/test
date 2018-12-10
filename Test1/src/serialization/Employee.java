package serialization;

import java.io.Serializable;

public class Employee implements Serializable {
	
	private static final long serialVersionUID =1L;
	private String name;
	private transient int salary;
	private String address;	
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Employee{name"+name+" address "+address+" Salary "+salary+"}";
	}
	public Employee(String name, int salary, String address) {
		super();
		this.name = name;
		this.salary = salary;
		this.address = address;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
