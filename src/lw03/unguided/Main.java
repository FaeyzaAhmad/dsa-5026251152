package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;



public class Main {
    public static void main(String[] args) {
        Set<String> registeredStudents = new HashSet<>();
        Set<String> checkedInStudents = new HashSet<>();
        int rejectedAttempts = 0;

        try {
            File registFile = new File("src/lw03/unguided/registrations.txt");
            Scanner regSc = new Scanner(registFile);
            while (regSc.hasNextLine()) {
                String id = regSc.nextLine().trim();
                if (!id.isEmpty()) {
                    registeredStudents.add(id);
                }
            }
            regSc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File registrations.txt not found.");
            return;
        }

        System.out.println("===== Event Check-In Results =====");
        try {
            File checkinFile = new File("src/lw03/unguided/checkins.txt");
            Scanner checkinSc = new Scanner(checkinFile);
            while (checkinSc.hasNextLine()) {
                String id = checkinSc.nextLine().trim();
                if (id.isEmpty()) continue;

                if (!registeredStudents.contains(id)) {
                    System.out.println(id + ": Rejected (not registered)");
                    rejectedAttempts++;
                } else if (checkedInStudents.contains(id)) {
                    System.out.println(id + ": Rejected (already checked in)");
                    rejectedAttempts++;
                } else {
                    System.out.println(id + ": Checked in");
                    checkedInStudents.add(id);
                }
            }
            checkinSc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File checkins.txt not found.");
            return;
        }

        int totalRegistered = registeredStudents.size();
        int successfulCheckins = checkedInStudents.size();
        int absentStudents = totalRegistered - successfulCheckins;

        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + totalRegistered);
        System.out.println("Successful check-ins: " + successfulCheckins);
        System.out.println("Absent students: " + absentStudents);
        System.out.println("Rejected attempts: " + rejectedAttempts);
    }
}