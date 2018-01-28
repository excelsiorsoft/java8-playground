package interfaces;

public class MyGoodDataImpl implements MyProperData {

	
	public boolean isNull(String str) {
		System.out.println("Impl Null Check");

		return str == null ? true : false;
	}
	
	public static void main(String args[]){
		MyGoodDataImpl obj = new MyGoodDataImpl();
		obj.print("");
		obj.isNull("abc");
	}
}
