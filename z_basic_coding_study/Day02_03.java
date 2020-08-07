// 3. for 문을 이용해서 1부터 76까지의 합을 구하는 코드를 작성하자.
package garamCodingTestPractice;

public class Day02_03 {

	public static void main(String[] args) {

		int sum=0;
		int num=76;
		
		for(int i = 1; i<=num; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}