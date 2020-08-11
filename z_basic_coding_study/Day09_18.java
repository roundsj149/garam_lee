/* 18. 자연수 1부터 시작해서 모든 홀수와 3의 배수인 짝수를 더해 나간다.
그 합이 언제(몇을 더했을 때) 1000을 넘어서는지 , 그리고 1000을 넘어선 값은 얼마가 되는지 계산하여 출력하는 프로그램을 작성해 보자.*/
package garamCodingTestPractice;

public class Day09_18 {

	public static void main(String[] args) {
		
		int num = 1;
		int sum = 0;
		boolean run = true;
		
		while(run) {
			if(num%2 !=0 || (num%3 == 0 && num%2 == 0)) {
				sum += num;
			}
			if(sum > 1000) {
				System.out.println(num);
				System.out.println(sum);
				run = false;
			} else {
				num++;
			}
		}
		
	}
}