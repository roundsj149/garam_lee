/*
25.
다음과 같은 배열이 있다.
가장 큰 값 과 작은 값을 출력하세요
[1,7,6,3,2,8,9,4,5]
 */
package garamCodingTestPractice;

public class Day13_25 {
	
	public static void main(String[] args) {
		
		int min = 99999;
		int max = -1;
		int[] arr = {1,7,6,3,2,8,9,4,5};
		
		for(int num : arr) {
			if(num < min) {
				min = num;
			}
			if(num > max) {
				max = num;
			}
		}
		
		System.out.println("최댓값: "+max+", 최솟값: "+min);
	}
}