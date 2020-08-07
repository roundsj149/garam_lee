// 7. 863은 소수 인가?
package garamCodingTestPractice;

public class Day04_07 {

	public static void main(String[] args) {

		int num = 863;
		int cnt = 0;
		
		for(int i=2; i<num; i++) {
			if(num % i == 0) {
				cnt++;
			} 
		}
		
		if(cnt == 0) {
			System.out.println("소수입니다");
		} else {
			System.out.println("소수가 아닙니다.");
		}
		
	}
}