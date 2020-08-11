/*
24.
다음과 같은 배열이 있다.
정렬해서 출력하세요.(버블 소팅 , 셀렉트 소팅)
[1,7,6,3,2,8,9,4,5]
 */
package garamCodingTestPractice;

public class Day12_24 {

	public static void main(String[] args) {
		// 1) 버블정렬
		
		int[] num = {1,7,6,3,2,8,9,4,5};
		int temp;
		
		for(int i=num.length-1; i>0; i--) {
			for(int j=0; j<i; j++) {
				if(num[j] > num[j+1]) {
					temp = num[j];
					num[j] = num[j+1];
					num[j+1] = temp;
				}
			}
		}
		for(int result : num) {
			System.out.print(result+" ");
		}
		System.out.println();
		
		// 2) 선택정렬
		int[] num2 = {1,7,6,3,2,8,9,4,5}; // 길이: 9
		int temp2;
		
		for(int i=0; i<num2.length-1; i++) {
			for(int j=i+1; j<num2.length; j++) {
				if(num2[i] > num2[j]) {
					temp2 = num2[i];
					num2[i] = num2[j];
					num2[j] = temp2;
				}
			}
		}
		for(int result : num) {
			System.out.print(result+" ");
		}
	}
}






















