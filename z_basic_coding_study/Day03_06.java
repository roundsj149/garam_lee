// 6. 구구단을 출력을 하되 7단과 6단을 제외하고 출력하자.

package garamCodingTestPractice;

public class Day03_06 {

	public static void main(String[] args) {

		for(int i=2; i<=9; i++) {
			if(i == 6 || i == 7) {
				continue;
			} else {
				for(int j=1; j<=9; j++) {
					System.out.println(i+" * "+j+" = "+i*j);
				}
			}
		}
	}
}