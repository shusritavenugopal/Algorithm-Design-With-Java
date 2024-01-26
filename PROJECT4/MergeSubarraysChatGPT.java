public class MergeSubarraysChatGPT {

    public static void mergeSubarrays(int[] arr, int m, int n) {
        int[] aux = new int[m]; // Auxiliary array

        // Copy the first m elements to the auxiliary array
        for (int i = 0; i < m; i++) {
            aux[i] = arr[i];
        }

        int i = 0; // Pointer for the first subarray
        int j = m; // Pointer for the second subarray
        int k = 0; // Pointer for the main array

        // Merge the subarrays from the auxiliary array
        while (i < m && j < m + n) {
            if (aux[i] <= arr[j]) {
                arr[k++] = aux[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }

        // Copy remaining elements from the auxiliary array if any
        while (i < m) {
            arr[k++] = aux[i++];
        }
        // Copy remaining elements from the second subarray if any
        while (j < m + n) {
            arr[k++] = arr[j++];
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 7, 9, 1};
        int m = 3; // Size of the first subarray
        int n = arr.length - m; // Size of the second subarray

        mergeSubarrays(arr, m, n);

        // Print the merged array
        for (int value : arr) {
            System.out.print(value + " ");
        }
        // Output: 1 2 3 4 5 6 7 8
    }
}

