package cloneexample;

import java.util.HashMap;
import java.util.Map;

public class CloningTest {

	public static void main(String[] args) throws CloneNotSupportedException {

		Employee emp = new Employee();

		emp.setId(1);
		emp.setName("Pankaj");
		Map<String, String> props = new HashMap<String, String>();
		props.put("salary", "10000");
		props.put("city", "Bangalore");
		emp.setProp(props);

		Employee clonedEmp = (Employee) emp.clone();

		// Check whether the emp and clonedEmp attributes are same or different
		System.out.println("emp and clonedEmp == test: " + (emp == clonedEmp));
		
		System.out.println("emp and clonedEmp HashMap == test: " + (emp.getProp() == clonedEmp.getProp()));
		System.out.println("id == "+(emp.getId()==clonedEmp.getId()));
		System.out.println("name == "+(emp.getName()==clonedEmp.getName()));
		
		// Lets see the effect of using default cloning
		
		// change emp props
		emp.getProp().put("title", "CEO");
		emp.getProp().put("city", "New York");
		System.out.println("clonedEmp props:" + clonedEmp.getProp());

		// change emp name
		emp.setName("new");
		System.out.println("clonedEmp name:" + clonedEmp.getName());

	}

}
