/*
23. 별찍기 문제
다음과 같이 출력해보자

		   *
		  ***
		 *****
		  ***
		   *
 */
package garamCodingTestPractice;

public class Day12_23 {

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i <= 5 / 2) {// 위쪽 영역 - 2보다 작거나 같음
					if (i + j <= 5 / 2 - 1)// 왼쪽 위 공백찍기
						System.out.print(" ");
					else if (j - i >= 5 / 2 + 1) // 오른쪽 위 공백찍기
						System.out.print(" ");
					else
						System.out.print("*");// *찍기
				} else if (i > 5 / 2) { // 아래쪽 영역 - 2보다 큼
				
					if (i - j >= 5 / 2 + 1) // 왼쪽 밑 공백
						System.out.print(" ");
					else if (i + j >= 5 / 2 * 3 + 1)// 오른쪽 밑 공백
						System.out.print(" ");
					else
						System.out.print("*"); // *찍기
				}
			}
			System.out.println();// 줄바꿈
		}
	}
}