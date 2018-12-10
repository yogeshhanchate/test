package cloneexample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Employee implements Cloneable{
	private int id;
	private String name;
	private Map<String,String> prop;
	private Object props;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getProp() {
		return prop;
	}
	public void setProp(Map<String, String> prop) {
		this.prop = prop;
	}
	/*public Object clone() throws CloneNotSupportedException{//it is shallow cloning
		return super.clone();
	}*/
	static{
		synchronized(new Employee()){
			
		}
	}
	public Object clone() throws CloneNotSupportedException {
	
		Object obj = super.clone(); //utilize clone Object method
	
		Employee emp = (Employee) obj;
			
		// deep cloning for immutable fields
		emp.setProp(null);
		Map<String, String> hm = new HashMap<String, String>();
		String key;
		Iterator<String> it = this.prop.keySet().iterator();
		// Deep  of field by field
		while (it.hasNext()) {
			key = it.next();
			hm.put(key, this.prop.get(key));
		}
		emp.setProp(hm);
		
		return emp;
	}

}
