import java.util.*;

public class RectangleMultiplication {
    public static String rectangleMultiplication(String num1, String num2) {
        // big integers to strings
        String num1Str = num1.startsWith("-") ? num1.substring(1) : num1;
        String num2Str = num2.startsWith("-") ? num2.substring(1) : num2;

        // Determine the sign of the result
        boolean isNegative = (num1.startsWith("-") ^ num2.startsWith("-"));

        // Initialize the result
        int[] result = new int[num1Str.length() + num2Str.length()];

        // Iterate through the digits of the second integer (num2)
        for (int i = num2Str.length() - 1; i >= 0; i--) {
            int carry = 0;
            int digit2 = Character.getNumericValue(num2Str.charAt(i));
            for (int j = num1Str.length() - 1; j >= 0; j--) {
                int digit1 = Character.getNumericValue(num1Str.charAt(j));
                int product = digit1 * digit2 + result[i + j + 1] + carry;
                carry = product / 10;
                result[i + j + 1] = product % 10;
            }

            // Add any remaining carry
            result[i] += carry;
        }

        // Convert the result to a string
        StringBuilder resultStr = new StringBuilder();
        for (int digit : result) {
            if (!(resultStr.length() == 0 && digit == 0)) {
                resultStr.append(digit);
            }
        }

        // If the result is empty then product is 0
        if (resultStr.length() == 0) {
            return "0";
        }

        // Add the sign to the result if needed
        return isNegative ? "-" + resultStr.toString() : resultStr.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter first big integer: ");
        String num1 = sc.nextLine();
        System.out.println("Enter second big integer: ");
        String num2 = sc.nextLine();
        String result = rectangleMultiplication(num1, num2);
        System.out.println("The Product of Two Big Integers using Rectangle Method is " + result);
    }
}