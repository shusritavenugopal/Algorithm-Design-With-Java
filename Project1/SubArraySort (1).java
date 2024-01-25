import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubArraySort {
    public static void main(String[] args) {
        List<Integer> sub1 = new ArrayList<>();
        List<Integer> sub2 = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("How many elements in first array");
        int m = sc.nextInt();
        System.out.println("Enter sorted first subarray");
        for (int i = 0; i < m; i++) sub1.add(sc.nextInt());
        
        System.out.println("How many elements in second array");
        int n = sc.nextInt();
        System.out.println("Enter sorted second subarray");
        for (int i = 0; i < n; i++) sub2.add(sc.nextInt());
        
        m = sub1.size();
        n = sub2.size();

        List<Integer> A = new ArrayList<>();
        int  min = m > n ? n : m;
        int[] aux = new int[min];
        int ap = 0, p1 = 0, p2 = 0;

        if (m == 0 && n == 0 ) {
            System.out.println("Nothing to Display");
        } else if (m == 0 && n > 1) {
            for (int i = 0; i < n; i++) A.add(sub2.get(i));
        } else if (n == 0 && m > 1) {
            for (int i = 0; i < m; i++) A.add(sub1.get(i));
        } else {
            while (p1 <= m && p2 <= n) {
                if (ap == min || (p1 == m || p2 == n)) {
                    for(int i = 0; i < ap; i++) {
                        A.add(aux[i]);
                    }
                    ap = 0;
                }
                if (p1 < m && p2 < n) {
                    if (sub1.get(p1) <= sub2.get(p2)) {
                        aux[ap] = sub1.get(p1);
                        p1++;
                    } else if (sub2.get(p2) < sub1.get(p1)) {
                        aux[ap] = sub2.get(p2);
                        p2++;
                    }
                } else {
                    break;
                }
                ap++;
            }
            if (p1 != m) {
                for (int i = p1; i < m; i++) {
                    A.add(sub1.get(i));
                }
            }
            if(p2 != n) {
                for (int i = p2; i < n; i++) {
                    A.add(sub2.get(i));
                }
            }
        }
        System.out.println("Sorted Array A of size m+n is " + A);
    }
}
