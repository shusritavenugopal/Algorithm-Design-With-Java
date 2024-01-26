public class Threesubsets {
    public static void printSubsets(int [] set) {
		int lengthOfArray = set.length;
		int subsetSize = 3;
		// int subset[] = new int[subsetSize];
		
		if (lengthOfArray < subsetSize) {
			System.out.println("The length of the set is too small to form a subset of 3");
		} else {
			for (int i = 0; i < lengthOfArray - 2; i++) {
				for (int j = i+1; j < lengthOfArray - 1; j++) {
					for (int k = j+1; k < lengthOfArray; k++) {
						System.out.println("{ " + set[i] + ", " + set[j] + ", " + set[k] + " }");
					}
				}
			}
		}
	}
	public static void main(String args[]) {
		int case1[] = {1,2,3,4};
		int case2[] = {7,3};
		int case3[] = {4,1,7,4,3,9,1,5};
		
		System.out.println("Algorithm to print all subsets of 3 elements of a given set");
		
		System.out.println("All 3 element subset of Test Case 1 = {1,2,3,4} :");
		printSubsets(case1);
		
		System.out.println("All 3 element subset of Test Case 2 = {7,3} :");
		printSubsets(case2);
		
		System.out.println("All 3 element subset of Test Case 3 = {4,1,7,4,3,9,1,5} :");
		printSubsets(case3);
	}
}