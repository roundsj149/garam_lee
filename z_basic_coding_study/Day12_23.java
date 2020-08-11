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

import java.util.Scanner;

public class Day12_23 {

	public static void main(String[] args) {

		// 다이아몬드
		System.out.print("숫자를 입력하세요> ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();

		// 위쪽
		for (int i = 1; i <= num; i++) {
			// 왼쪽
			for (int j = num; j >= 1; j--) {
				if (j <= i) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			// 오른쪽
			for (int k = 0; k < num; k++) {
				if (k < i - 1) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}

		// 아래쪽
		for (int i = num - 1; i >= 1; i--) {
			// 왼쪽
			for (int j = num - 1; j >= 0; j--) {
				if (j < i) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			// 오른쪽
			for (int k = 0; k < num - 1; k++) {
				if (k < i - 1) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
		
		// /ㅣ 이 모양
		for (int i = 1; i <= 5; i++) {
			for (int j = 5; j >= 1; j--) {
				if (j <= i) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();

		// \ㅣ 이 모양
		for (int i = 5; i >= 1; i--) {
			for (int j = 5; j >= 1; j--) {
				if (j > i) {
					System.out.print(" ");
				} else {
					System.out.print("*");
				}
			}
			System.out.println();
		}
		System.out.println();

		// ㅣ/ 이 모양
		for (int i = 5; i >= 1; i--) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}

		// /\ 이 모양
		for (int i = 1; i <= 5; i++) {
			for (int j = 5; j >= 1; j--) {
				if (j <= i) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			for (int k = 0; k < 5; k++) {
				if (k < i - 1) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}