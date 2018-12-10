package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializedUtility {
	public static void serialize(Object obj,String filename){
		try{
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos =new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.close();
		}catch(Exception e){e.printStackTrace();}		
	}
	public static Object deserialize(String filename){
		Object obj=null;
		try{
			FileInputStream fis =new FileInputStream(filename);
			ObjectInputStream ois =new ObjectInputStream(fis);
			obj=ois.readObject();
			ois.close();
		}catch(Exception e){e.printStackTrace();}
		return obj;
	}
}
