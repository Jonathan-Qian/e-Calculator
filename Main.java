import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

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
				factorial, lcm;
		
		for (int i = 2; i <= iterations; i++) {
			System.out.println(i);
			factorial = factorial(i);
			lcm = lcm(denominator, factorial);
			numerator = lcm.divide(denominator).multiply(numerator).add(lcm.divide(factorial));
			denominator = lcm;
		}
		
		return numerator.divide(denominator, iterations - 1, RoundingMode.FLOOR);
	}
	
	public static BigDecimal factorial(int n) {
		BigDecimal product = new BigDecimal(n);
		
		for (int i = n - 1; i > 1; i--) {
			product = product.multiply(new BigDecimal(i));
		}
		
		return product;
	}
	
	public static BigDecimal lcm(BigDecimal n1, BigDecimal n2) {
		BigDecimal gcd = new BigDecimal(1);
		final BigDecimal ZERO = new BigDecimal(0);
		final BigDecimal ONE = new BigDecimal(1);

	    for(BigDecimal i = new BigDecimal(1); i.compareTo(n1) < 1 && i.compareTo(n2) < 1; i = i.add(ONE)) {
	      if(n1.remainder(i).equals(ZERO) && n2.remainder(i).equals(ZERO))
	        gcd = i;
	    }
	    
	    System.out.println(gcd);
	    
	    BigDecimal lcm = n1.multiply(n2).divide(gcd);
	    System.out.println(lcm);
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