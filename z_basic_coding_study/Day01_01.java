/* 1. 임의의 시험 점수를 입력(혹은 랜덤으로)받아 90 ~ 100점은 A, 80 ~ 89점은 B, 70 ~ 79점은 C, 
		      60 ~ 69점은 D, 나머지 점수는 F를 출력하는 프로그램을 작성하시오. */
package garamCodingTestPractice;

import java.util.Scanner;

public class Day01_01 {

	public static void main(String[] args) {
		
		// 1) 시험점수 입력 받기
		Scanner sc = new Scanner(System.in);
		System.out.print("시험 점수를 입력하세요> ");
		int score = sc.nextInt();
				
		if(score >= 90 && score <=100) {
			System.out.println("A");
		} else if(score >= 80 && score <=89) {
			System.out.println("B");
		} else if(score >= 70 && score <=79) {
			System.out.println("C");
		} else if(score >= 60 && score <=69) {
			System.out.println("D");
		} else {
			System.out.println("F");
		}
				
		sc.close();

		// 2) 시험점수 랜덤 돌리기
		int randomScore = (int)(Math.random()*101);
		System.out.println(randomScore);
		int gradeCheck = randomScore/10;
		
		switch(gradeCheck) {
			case 10:
			case 9:
				System.out.println("A");
				break;
			case 8:
				System.out.println("B");
				break;
			case 7:
				System.out.println("C");
				break;
			case 6:
				System.out.println("D");
				break;
			case 5:
			case 4:
			case 3:
			case 2:
			case 1:
			case 0:
				System.out.println("F");
				break;
		}
	}
}
