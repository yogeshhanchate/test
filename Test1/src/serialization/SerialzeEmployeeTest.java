package serialization;

public class SerialzeEmployeeTest {
	public static void main(String[] args) {
		String filename="test.ser";
		Employee emp = new Employee("Yogesh",1000,"Solapur");		
		try{
		SerializedUtility.serialize(emp, filename);
		}catch(Exception e){e.printStackTrace();}
		Employee e=null;
		try{			
			e=(Employee)SerializedUtility.deserialize(filename);			
		}catch(Exception et){et.printStackTrace();}
		System.out.println("Emp "+emp);
		System.out.println("e "+e);
	}

}
