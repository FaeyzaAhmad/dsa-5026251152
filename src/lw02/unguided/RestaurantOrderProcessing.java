package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class RestaurantOrderProcessing {
    public static void main(String[] args) {

        LinkedList<String[]> orderList = new LinkedList<>();
        LinkedList<String[]> foodStock = new LinkedList<>();
        LinkedList<String[]> drinkStock = new LinkedList<>();
        LinkedList<String[]> successfulOrders = new LinkedList<>();
        Queue<String[]> queue = new LinkedList<>();
        Stack<String[]> failedStack = new Stack<>();

        // menambahkan stok 
        foodStock.add(new String[]{"Bakso", "2"});
        foodStock.add(new String[]{"Sate", "1"});
        foodStock.add(new String[]{"Soto", "2"});

        drinkStock.add(new String[]{"EsTeh", "4"});
        drinkStock.add(new String[]{"EsJeruk", "2"});

        // Membaca pesanan dari orders.txt
        try {
            Scanner scanner = new Scanner(new File("src/lw02/unguided/orders.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        orderList.add(parts);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File orders.txt tidak ditemukan.");
            return;
        }

        // Memindahkan pesanan ke queue dengan fifo
        for (String[] order : orderList) {
            queue.add(order);
        }

        // Memproses pesanan dari queue
        while (!queue.isEmpty()) {
            String[] currentOrder = queue.poll();
            String foodOrdered = currentOrder[1];
            String drinkOrdered = currentOrder[2];

            boolean foodAvailable = true;
            boolean drinkAvailable = true;

            // cek persediaan makanan
            if (!foodOrdered.equals("-")) {
                foodAvailable = false;
                for (String[] food : foodStock) {
                    if (food[0].equals(foodOrdered)) {
                        if (Integer.parseInt(food[1]) > 0) {
                            foodAvailable = true;
                        }
                        break;
                    }
                }
            }

            // cek persediaan minuman
            if (!drinkOrdered.equals("-")) {
                drinkAvailable = false;
                for (String[] drink : drinkStock) {
                    if (drink[0].equals(drinkOrdered)) {
                        if (Integer.parseInt(drink[1]) > 0) {
                            drinkAvailable = true;
                        }
                        break;
                    }
                }
            }

            // Jika semua item yang dipesan tersedia
            if (foodAvailable && drinkAvailable) {
                // Kurangi stok makanan
                if (!foodOrdered.equals("-")) {
                    for (String[] food : foodStock) {
                        if (food[0].equals(foodOrdered)) {
                            food[1] = String.valueOf(Integer.parseInt(food[1]) - 1);
                            break;
                        }
                    }
                }
                // Kurangi stok minuman
                if (!drinkOrdered.equals("-")) {
                    for (String[] drink : drinkStock) {
                        if (drink[0].equals(drinkOrdered)) {
                            drink[1] = String.valueOf(Integer.parseInt(drink[1]) - 1);
                            break;
                        }
                    }
                }
                successfulOrders.add(currentOrder);
            } else {
                // Jika ada item yang tidak tersedia, masukkan pesanan ke Stack
                failedStack.push(currentOrder);
            }
        }

        // Menampilkan pesanan yang berhasil diproses
        System.out.println("=== Successfully Processed Orders ===");
        for (String[] order : successfulOrders) {
            System.out.println(String.join(" ", order));
        }

        // Menampilkan sisa stok makanan
        System.out.println("=== Remaining Food Stock ===");
        for (String[] food : foodStock) {
            System.out.println(food[0] + ": " + food[1]);
        }

        // Menampilkan sisa stok minuman
        System.out.println("=== Remaining Drink Stock ===");
        for (String[] drink : drinkStock) {
            System.out.println(drink[0] + ": " + drink[1]);
        }

        // Menampilkan pesanan yang gagal diproses
        System.out.println("=== Failed Orders ===");
        while (!failedStack.isEmpty()) {
            String[] failedOrder = failedStack.pop();
            System.out.println(String.join(" ", failedOrder));
        }
    }
}
