import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
	public static void main(String[] args) {
		System.out.println("Start");
		int iterations = 100000;
		long end, start = System.currentTimeMillis();
		BigDecimal calculation = calculate(iterations);
		end = System.currentTimeMillis();
		System.out.println();
		System.out.println("Execution time: " + ((end - start) / 1000.0) + " seconds");
		check(calculation);
		System.out.println(iterations * (-0.417 + 0.433 * Math.log(iterations * 1.0)));
		System.out.println();

		//int correctDigits;

		/*for (int i = 0; i <= 200000; i += 100) {
			System.out.println(i);
			calculation = calculate(i);
			correctDigits = check(calculation);
			System.out.println();

			try {
				PrintWriter writer = new PrintWriter(new FileWriter("correctDigits.csv", true));

				writer.println(i + "," + correctDigits);

				writer.close();
			}
			catch (Exception e) {
				System.err.println(e);
			}
		}*/
	}
	
	public static BigDecimal calculate(int iterations) {
		final BigDecimal ONE = new BigDecimal(1);
		BigDecimal numerator = new BigDecimal(2), denominator = ONE, bdi;
		
		
		for (int i = 2; i <= iterations; i++) {
			bdi = new BigDecimal(i);
			numerator = bdi.multiply(numerator).add(ONE);
			denominator = denominator.multiply(bdi);
		}
		
		double scale = iterations * (-0.417 + 0.433 * Math.log(iterations * 1.0));
		scale -= 0.005 * (iterations - 500); // comment this line out if you don't want to have more digits than are correct instead of less digits than are correct
		
		return numerator.divide(denominator, (int) (scale), RoundingMode.FLOOR);
	}
	
	public static int check(BigDecimal calculation) {
		try {
			//System.out.println(calculation);
			
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

			if (calculationDigits == correctDigits) {
				System.out.println("Scale is probably too small.");
			}

			//calculation = calculation.setScale(correctDigits - 1, RoundingMode.FLOOR);
			//System.out.println(calculation);

			return correctDigits;
		}
		catch (IOException e) {
			System.err.println(e);
		}

		return 0;
	}
}