import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("washes.txt"));
            
            if (scanner.hasNextInt()) {
                int totalRecords = scanner.nextInt();
                WashService[] washes = new WashService[totalRecords];

                for (int i = 0; i < totalRecords; i++) {
                    String type = scanner.next();
                    String id = scanner.next();
                    int days = scanner.nextInt();
                    int units = scanner.nextInt();

                    if (type.equals("MOTORCYCLE")) {
                        washes[i] = new MotorcycleWash(id, days, units);
                    } else if (type.equals("CAR")) {
                        washes[i] = new CarWash(id, days, units);
                    }
                }

                for (WashService wash : washes) {
                    System.out.println(wash.summary());
                }
            }
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File washes.txt not found.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}