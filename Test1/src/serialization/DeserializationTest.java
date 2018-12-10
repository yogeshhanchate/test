package serialization;

public class DeserializationTest {
	public static void main(String[] args) {
		String filename="test.ser";
		Employee e =null;
		try{
			e=(Employee)SerializedUtility.deserialize(filename);
		}catch(Exception ex){ex.printStackTrace();}
		System.out.println(e);
	}
}
