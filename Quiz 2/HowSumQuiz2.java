import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class HowSumQuiz2 {
	static List<Integer> howSum(int targetSum, int[] numbers, Map<Integer, List<Integer>> memo) {
	   if (memo.containsKey(targetSum)) return memo.get(targetSum);
	   if (targetSum == 0) return new ArrayList<>();
	   if (targetSum < 0)  return null;
	   int sum = 0;
	   for (int i : numbers) {
		   sum += i;
	   }
	   if (sum < targetSum) return null;
	   for (int number : numbers) {
		  int remainder = targetSum - number;
		  List<Integer> result = howSum(remainder, numbers, memo);
		  if (result != null) {
			 result.add(number);
			 memo.put(targetSum, result);
			 return memo.get(targetSum);
		  }
	   }
	   memo.put(targetSum, null);
	   return null;
	}
	 public static void main(String[] args) {
		 int targetSum1 = 7;
		 int[] numbers1 = {2,3};
		 HashMap<Integer, List<Integer>> memo1 = new HashMap<Integer, List<Integer>>();
		 System.out.println("Test Case 1 : " + targetSum1 + " , " + Arrays.toString(numbers1));
		 System.out.println(howSum(targetSum1, numbers1, memo1));
		 
		 int targetSum2 = 7;
		 int[] numbers2 = {5,3,4,7};
		 HashMap<Integer, List<Integer>> memo2 = new HashMap<Integer, List<Integer>>();
		 System.out.println("Test Case 2 : " + targetSum2 + " , " + Arrays.toString(numbers2));
		 System.out.println(howSum(targetSum2, numbers2, memo2));
		 
		 int targetSum3 = 300;
		 int[] numbers3 = {7,14};
		 HashMap<Integer, List<Integer>> memo3 = new HashMap<Integer, List<Integer>>();
		 System.out.println("Test Case 3 : " + targetSum3 + " , " + Arrays.toString(numbers3));
		 System.out.println(howSum(targetSum3, numbers3, memo3));
	 }
}
