package interfaces;

/**
 * See {@link https://www.journaldev.com/2752/java-8-interface-changes-static-method-default-method}
 * for description
 * 
 * @author Simeon
 *
 */
public interface MyProperData {

	default void print(String str) {
		if (!isNull(str)) {
			System.out.println("MyData Print::" + str);
		}
	}

	static /*default*/boolean isNull(String str) {
		System.out.println("Interface Null Check");

		return str == null ? true : "".equals(str) ? true : false;
	}
}
