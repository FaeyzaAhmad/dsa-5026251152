package lw04.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("src/lw04/prelab/branches.txt"));
            
            if (!scanner.hasNextInt()) return;
            
            int n = scanner.nextInt();
            int[] branches = new int[n];
            for (int i = 0; i < n; i++) {
                branches[i] = scanner.nextInt();
            }
            
            int m = scanner.nextInt();
            int[] targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = scanner.nextInt();
            }
            scanner.close();
            
            for (int i = 1; i <= 3 && i < n; i++) {
                int key = branches[i];
                int j = i - 1;
                while (j >= 0 && branches[j] < key) {
                    branches[j + 1] = branches[j];
                    j--;
                }
                branches[j + 1] = key;
            }
            
            for (int i = 0; i < n; i++) {
                System.out.print(branches[i]);
                if (i < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            
            for (int t = 0; t < m; t++) {
                int target = targets[t];
                int foundIndex = -1;
                
                for (int i = 0; i < n; i++) {
                    if (branches[i] == target) {
                        foundIndex = i;
                        break;
                    }
                }
                
                if (foundIndex != -1) {
                    System.out.println(target + ": FOUND at index " + foundIndex);
                } else {
                    System.out.println(target + ": NOT FOUND");
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File branches.txt tidak ditemukan.");
        }
    }
}
