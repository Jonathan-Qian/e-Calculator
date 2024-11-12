import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
	public static void main(String[] args) {
		System.out.println("start");
		long end, start = System.currentTimeMillis();
		BigDecimal calculation = calculate(8);
		end = System.currentTimeMillis();
		System.out.println();
		System.out.println("Execution time: " + ((end - start) / 1000.0) + " seconds");
		System.out.println(calculation);
		check(calculation);
	}
	
	public static BigDecimal calculate(int iterations) {
		BigDecimal numerator = new BigDecimal(2),
				denominator = new BigDecimal(1),
				factorial = new BigDecimal(1),
				lcm = new BigDecimal(1);
		
		for (int i = 2; i <= iterations; i++) {
			System.out.println("i: " + i);
			factorial = factorial.multiply(new BigDecimal(i));
			lcm = cascadingLCM(denominator, factorial, lcm);
			numerator = lcm.divide(denominator).multiply(numerator).add(lcm.divide(factorial));
			denominator = lcm;
		}
		
		return numerator.divide(denominator, iterations - 1, RoundingMode.FLOOR);
	}
	
	public static BigDecimal cascadingLCM(BigDecimal n1, BigDecimal n2, BigDecimal oldLCM) {
	    System.out.println("gcd: " + oldLCM);
	    
	    BigDecimal lcm = n1.multiply(n2).divide(oldLCM);
	    System.out.println("lcm: " + lcm);
	    System.out.println();
	    return lcm;
	}
	
	public static void check(BigDecimal calculation) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("digits.txt"));
			
			String fromFile = reader.readLine();
			String calculated = calculation.toString();
			
			reader.close();
			
			int calculationDigits;
			
			if (calculated.indexOf('.') == -1)
				calculationDigits = calculated.length();
			else
				calculationDigits = calculated.length() - 1;
			
			System.out.println("Calculation digits: " + calculationDigits);
			
			int correctDigits = 0;
			char c;
			
			for (int i = 0; i < calculated.length(); i++) {
				c = fromFile.charAt(i);
				if (c == calculated.charAt(i)) {
					if (c != '.') {
						correctDigits++;
					}
				}
				else
					break;
			}
			
			System.out.println("Consecutive correct digits: " + correctDigits);
			calculation = calculation.setScale(correctDigits - 1, RoundingMode.FLOOR);
			System.out.println(calculation);
		}
		catch (IOException e) {
			System.err.println(e);
		}
	}
}