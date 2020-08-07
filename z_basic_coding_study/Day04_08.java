// 8. 2~100사이의 소수를 구해보자
package garamCodingTestPractice;

public class Day04_08 {

	public static void main(String[] args) {
		
		int cnt = 0;
		
		for(int i=2; i<=100; i++) {
			for(int j=2; j<i; j++) {
				if(i%j == 0) {
					cnt++;
				}
			}
			if(cnt == 0) {
				System.out.println(i);
			}
			cnt = 0;
		}
	}
}
