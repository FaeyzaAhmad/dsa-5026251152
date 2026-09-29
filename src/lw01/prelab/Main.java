import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Chargeable {
    int calculateCharge();
}

abstract class PrintJob implements Chargeable {
    protected String id;
    protected int pages;

    public PrintJob(String id, int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Pages must be greater than zero");
        }
        this.id = id;
        this.pages = pages;
    }

    @Override
    public abstract int calculateCharge();

    public int calculateCharge(int copies) {
        if (copies <= 0) {
            throw new IllegalArgumentException("Copies must be greater than zero");
        }
        return copies * calculateCharge();
    }

    public String label() {
        return "Print";
    }

    public String summary() {
        return id + " | " + label() + " | " + calculateCharge();
    }
}

class MonoPrint extends PrintJob {
    public MonoPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        return pages * 500;
    }

    @Override
    public String label() {
        return "Mono";
    }
}

class ColourPrint extends PrintJob {
    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int charge = 2000;
        if (pages <= 10) {
            charge += pages * 1500;
        } else {
            charge += (10 * 1500) + ((pages - 10) * 1000);
        }
        return charge;
    }

    @Override
    public String label() {
        return "Colour";
    }
}

public class Main {
    public static void main(String[] args) {
        List<PrintJob> jobs = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("jobs.txt"));
            while (scanner.hasNext()) {
                String type = scanner.next();
                String id = scanner.next();
                int pages = scanner.nextInt();

                if (type.equals("MONO")) {
                    jobs.add(new MonoPrint(id, pages));
                } else if (type.equals("COLOUR")) {
                    jobs.add(new ColourPrint(id, pages));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File jobs.txt not found.");
        }

        for (PrintJob job : jobs) {
            System.out.println(job.summary());
        }
    }
}