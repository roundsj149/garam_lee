// 21. 1~10000사이에 8이 몇번 나오는가?
package garamCodingTestPractice;

public class Day11_21 {

	public static void main(String[] args) {
		
		int cnt = 0;
		int num = 0;
		
		for(int i = 0; i <= 10000; i++) {
			num = i;
			while(num > 0) {
				if(num%10 != 0 && (num%10)%8 == 0) {
					cnt++;
				}
				num /= 10;
			}
		}
		System.out.println(cnt);
		
		/*
		int cnt = 0;
		int num = 0;
		
		for(int i=1; i<=10000; i++) {
			
			num = i;
			if(num < 10) {
				if(num % 8 == 0) {
					cnt++;
				}
			} else {
				while(num != 0) {
					if(num%10 == 8) {
						cnt++;
					}
					num = num/10;
				}
			}
		}
		System.out.println(cnt);
		*/
	}
}