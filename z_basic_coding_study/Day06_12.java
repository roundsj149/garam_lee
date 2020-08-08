// 12. 1+(-2)+3+(-4)+... 과 같은 식으로 계속 더해나갔을 때, 몇까지 더해야 총합이 100이상이 되는지 구하시오.
package garamCodingTestPractice;

public class Day06_12 {

	public static void main(String[] args) {

		int num = 0;
		int sum = 0;
		boolean run = true;
		
		while(run) {
			if(sum >=100) {
				System.out.println(num);
				run = false;
			} else {
				num++;
				if(num%2 == 0) {
					sum -= num;
				} else {
					sum += num;
				}
			}
		}
	}
}