package serializationwithinheritance;
import serialization.SerializedUtility;

public class InheritanceInSerializationTest {

	public static void main(String[] args) {
		String fileName = "subclass.ser";
		
		SubClass subClass = new SubClass();
		subClass.setId(10);
		subClass.setValue("Data");
		subClass.setName("Pankaj");
		
		try {
			SerializedUtility.serialize(subClass, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		try {
			SubClass subNew = (SubClass) SerializedUtility.deserialize(fileName);
			System.out.println("SubClass read = "+subNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
