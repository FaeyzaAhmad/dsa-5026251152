package lw03.unguided;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Maimport java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(Main.class.getResourceAsStream("enrollment.txt"));
        Map<String, Integer> enrollmentMap = new LinkedHashMap<>();
 
        List<String> checkResults = new ArrayList<>();
        int rejectedOperations = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            String type = line.substring(0, line.indexOf(" "));
            String details = line.substring(line.indexOf(" ") + 1);

            if (type.equals("REGISTER")) {
                String courseCode = details.substring(0, details.indexOf(" "));
                int qty = Integer.parseInt(details.substring(details.indexOf(" ") + 1));
                
                if (qty <= 0) {
                    rejectedOperations++;
                } else {
                    if (enrollmentMap.containsKey(courseCode)) {
                        int currentEnrollment = enrollmentMap.get(courseCode);
                        enrollmentMap.put(courseCode, currentEnrollment + qty);
                    } else {
                        enrollmentMap.put(courseCode, qty);
                    }
                }
            } 
            else if (type.equals("WITHDRAW")) {
                String courseCode = details.substring(0, details.indexOf(" "));
                int qty = Integer.parseInt(details.substring(details.indexOf(" ") + 1));

                if (qty <= 0) {
                    rejectedOperations++;
                } else {
                    if (enrollmentMap.containsKey(courseCode) && enrollmentMap.get(courseCode) >= qty) {
                        int currentEnrollment = enrollmentMap.get(courseCode);
                        enrollmentMap.put(courseCode, currentEnrollment - qty);
                    } else {
                        rejectedOperations++;
                    }
                }
            } 
            else if (type.equals("CHECK")) {
                String courseCode = details;
                if (enrollmentMap.containsKey(courseCode)) {
                    checkResults.add(courseCode + ": " + enrollmentMap.get(courseCode) + " students");
                } else {
                    checkResults.add(courseCode + ": Not found");
                }
            }
        }
        
        System.out.println("===== Enrollment Checks");
        for (String result : checkResults) {
            System.out.println(result);
        }

        System.out.println("===== Final Enrollment =====");
        for (String p : enrollmentMap.keySet()) {
            System.out.println(p + ": " + enrollmentMap.get(p) + " students");
        }
        
        System.out.println("Rejected operations: " + rejectedOperations);
    }
}   