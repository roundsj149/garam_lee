/*
반복문을 이용하여 369게임에서 박수를 쳐야 하는 경우의 수를 순서대로 화면에 출력해보자. 1부터 시작하며 99까지만 한다. 
실행 결과 예) 
3 박수한번
6 박수 한번
9 박수 한번 
.
.
.
33 박수 두번
.
98 박수 한번
99 박수 두번
*/
package garamCodingTestPractice;

public class Day10_20 {

	public static void main(String[] args) {
		/*
		 * int cnt = 0; 
		 * int num = 0; 
		 * boolean run = true;
		 * 
		 * for(int i=1; i<100; i++) { 
		 * 		num = i;
		 * 		while(run) { 
		 * 			if((num%10 !=0) && ((num%10) % 3 == 0)) { 
		 * 				cnt++; 
		 * 			} 
		 * 			num = num / 10; 
		 * 			if(num == 0) { 
		 * 				run = false; 
		 * 			}
		 *		 }
		 *		 if(cnt == 1) { 
		 * 			System.out.println(i+" 박수 한 번");
		 * 		}
		 * 		if(cnt == 2) {
		 * 			System.out.println(i+" 박수 두 번");
		 * 		}
		 *		cnt = 0;
		 * 		run = true;
		 * }
		 */

		int cnt = 0;
		int num = 0;

		for (int i = 1; i <= 99; i++) {

			num = i;
			while (num > 0) {
				if (num % 10 != 0 && (num % 10) % 3 == 0) {
					cnt++;
				}
				num /= 10;
			}

			if (cnt == 1) {
				System.out.println(i + ": 박수 한 번");
			} else if (cnt == 2) {
				System.out.println(i + ": 박수 두 번");
			}
			cnt = 0;
		}
	}
}