
package com.iit.RealtimeTicketing;

import com.iit.RealtimeTicketing.CLI.Main; // Import the Main class
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RealtimeTicketingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeTicketingApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("\nChoose an option:");
			System.out.println("1. Run Main Simulation");
			System.out.println("2. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
				case 1:
					System.out.println("\nStarting Main Simulation...");
					Main.main(new String[0]); // Call the Main class's main method
					break;

				case 2:
					System.out.println("Exiting the system. Goodbye!");
					running = false;
					System.exit(0);
					break;

				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}

		scanner.close();
	}
}