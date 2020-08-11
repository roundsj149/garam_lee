// 13. 1+(1+2)+(1+2+3)+(1+2+3+4)+...+(1+2+3+...+10)의 결과를 계산하시오.
package garamCodingTestPractice;

public class Day07_13 {

	public static void main(String[] args) {
		int sum = 0;
		int temp = 0;
		
		for(int i=1; i<=10; i++) {
			temp += i;
			sum += temp;
		}
		System.out.println(sum);
	}

}
