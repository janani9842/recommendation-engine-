package com.codtech;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecommenderEngine engine = new RecommenderEngine();
        
        System.out.println("CODTECH Recommendation System");
        System.out.println("=============================");
        
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Get recommendations for a user");
            System.out.println("2. Add new user preference");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter user ID: ");
                    long userId = scanner.nextLong();
                    engine.recommendForUser(userId);
                    break;
                case 2:
                    System.out.print("Enter user ID: ");
                    long newUserId = scanner.nextLong();
                    System.out.print("Enter item ID: ");
                    long itemId = scanner.nextLong();
                    System.out.print("Enter preference (1-5): ");
                    float preference = scanner.nextFloat();
                    engine.addPreference(newUserId, itemId, preference);
                    System.out.println("Preference added successfully!");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
