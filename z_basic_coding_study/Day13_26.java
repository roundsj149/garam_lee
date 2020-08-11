/*
"hello world" 를 꺼구로 출력하세요
결과:
dlrow olleh
 */
package garamCodingTestPractice;

public class Day13_26 {

	public static void main(String[] args) {

		String str = "hello world";
		char[] arr = new char[str.length()];
		
		for(int i=0; i<str.length(); i++) {
			arr[i] = str.charAt(i);
		}
		for(int i=arr.length-1; i>=0; i--) {
			System.out.print(arr[i]);
		}
	}
}
