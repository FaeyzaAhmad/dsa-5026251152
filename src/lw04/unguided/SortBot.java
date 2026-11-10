package lw04.unguided;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SortBot {
    public static void main(String[] args) {
        try {
            File file = new File("src/lw04/unguided/parcels.txt");
            Scanner scanner = new Scanner(file);

            int N = scanner.nextInt();
            int[] A = new int[N];
            for (int i = 0; i < N; i++) {
                A[i] = scanner.nextInt();
            }

            int M = scanner.nextInt();
            int[] targets = new int[M];
            for (int i = 0; i < M; i++) {
                targets[i] = scanner.nextInt();
            }
            scanner.close();

            int[] after2Passes = new int[N];

            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < N - 1 - i; j++) {
                    if (A[j] < A[j + 1]) {
                        int temp = A[j];
                        A[j] = A[j + 1];
                        A[j + 1] = temp;
                    }
                }
                
                if (i == 1) {
                    for (int k = 0; k < N; k++) {
                        after2Passes[k] = A[k];
                    }
                }
            }

            System.out.println("==== After 2 Passes ====");
            for (int i = 0; i < N; i++) {
                System.out.print(after2Passes[i]);
                if (i < N - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            System.out.println("==== Fully Sorted ====");
            for (int i = 0; i < N; i++) {
                System.out.print(A[i]);
                if (i < N - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            System.out.println("==== Search Results ====");
            for (int i = 0; i < M; i++) {
                int target = targets[i];
                int low = 0;
                int high = N - 1;
                int steps = 0;
                boolean found = false;
                int foundIndex = -1;

                while (low <= high) {
                    steps++;
                    int mid = (low + high) / 2;
                    
                    if (A[mid] == target) {
                        found = true;
                        foundIndex = mid;
                        break;
                    } else if (target > A[mid]) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }

                if (found) {
                    System.out.println(target + ": FOUND at index " + foundIndex + " (steps: " + steps + ")");
                } else {
                    System.out.println(target + ": NOT FOUND (steps: " + steps + ")");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File parcels.txt tidak ditemukan.");
        }
    }
}