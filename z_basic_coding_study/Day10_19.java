/*
19. 별찍기 문제
다음과 같이 출력해보자
*
**
***
****
*****
*/
package garamCodingTestPractice;

public class Day10_19 {
	
	public static void main(String[] args) {
		for(int i=1; i<=5; i++) {
			for(int j=0; j<i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
}
