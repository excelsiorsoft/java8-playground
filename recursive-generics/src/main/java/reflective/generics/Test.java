package reflective.generics;

class X<T> {
	public T method(T arg) {
		return arg;
    }
}

class SubClass extends X<SubClass> {}

class SubClassB extends X<SubClassB> {}

public class Test {
	
	public static void main() {
	SubClass a = new SubClass();
	SubClass b = new SubClass();
	
	 SubClassB c = new SubClassB();
	 
	 a.method(b);
	 //a.method(c); //cannot do - not accepted as per the SubClass's contract (needs to be of the same type)
	}
	 
	 
}
	
