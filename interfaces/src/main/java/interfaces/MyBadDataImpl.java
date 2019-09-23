package interfaces;

public class MyBadDataImpl implements MyImproperData {

	
	public boolean isNull(String str) {
		System.out.println("Impl Null Check");

		return str == null ? true : false;
	}
	
	public static void main(String args[]){
		MyBadDataImpl obj = new MyBadDataImpl();
		obj.print("");
		obj.isNull("abc");
	}
}
