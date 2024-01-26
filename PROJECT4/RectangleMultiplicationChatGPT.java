import java.math.BigInteger;

public class RectangleMultiplicationChatGPT {

    public static void main(String[] args) {
        BigInteger multiplicand = new BigInteger("7000");
        BigInteger multiplier = new BigInteger("7294");
        BigInteger result = rectangleMultiplication(multiplicand, multiplier);
        System.out.println("Result: " + result);
    }

    public static BigInteger rectangleMultiplication(BigInteger x, BigInteger y) {
        boolean isNegative = (x.compareTo(BigInteger.ZERO) < 0) ^ (y.compareTo(BigInteger.ZERO) < 0);

        x = x.abs();
        y = y.abs();

        int xLength = x.toString().length();
        int yLength = y.toString().length();
        int maxLength = Math.max(xLength, yLength);

        // Base case for single digit multiplication
        if (maxLength == 1) {
            BigInteger product = x.multiply(y);
            return isNegative ? product.negate() : product;
        }

        int split = maxLength / 2;

        // Splitting the numbers into halves
        BigInteger xHigh = x.divide(BigInteger.TEN.pow(split));
        BigInteger xLow = x.remainder(BigInteger.TEN.pow(split));
        BigInteger yHigh = y.divide(BigInteger.TEN.pow(split));
        BigInteger yLow = y.remainder(BigInteger.TEN.pow(split));

        // Recursive steps
        BigInteger z0 = rectangleMultiplication(xLow, yLow);
        BigInteger z1 = rectangleMultiplication(xLow.add(xHigh), yLow.add(yHigh));
        BigInteger z2 = rectangleMultiplication(xHigh, yHigh);

        BigInteger result = z2.multiply(BigInteger.TEN.pow(2 * split))
                .add(z1.subtract(z2).subtract(z0).multiply(BigInteger.TEN.pow(split))).add(z0);

        return isNegative ? result.negate() : result;
    }
}
