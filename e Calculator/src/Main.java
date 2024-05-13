import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		System.out.println("start");
		long end, start = System.currentTimeMillis();
		BigDecimal calculation = calculate(100000);
		end = System.currentTimeMillis();
		System.out.println();
		System.out.println("Execution time: " + ((end - start) / 1000.0) + " seconds");
		check(calculation);
		System.out.println();
	}
	
	public static BigDecimal calculate(int iterations) {
		final BigDecimal ONE = new BigDecimal(1);
		BigDecimal numerator = new BigDecimal(2),
				denominator = ONE,
				bdi;
		
		
		for (int i = 2; i <= iterations; i++) {
			bdi = new BigDecimal(i);
			numerator = bdi.multiply(numerator).add(ONE);
			denominator = denominator.multiply(bdi);
		}
		
		/*int scale;
		
		if (iterations < 15) {
			scale = iterations - 3;
		}
		else {
			scale = (int) (-0.00000452 * Math.pow(iterations, 3.0) + 0.00376 * Math.pow(iterations, 2.0) + 1.36 * iterations - 8.5) - 1;
		}*/
		
		return numerator.divide(denominator, 2000000, RoundingMode.FLOOR);
	}
	
	public static void check(BigDecimal calculation) {
		try {
			System.out.println(calculation);
			
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