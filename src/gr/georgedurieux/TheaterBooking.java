package gr.georgedurieux;

import java.util.Objects;
import java.util.Scanner;

public class TheaterBooking {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        boolean[][] seats = new boolean[12][30];

        while (true) {
            System.out.println("Press 1 to book seat, 2 to cancel, and 3 to see a diagram of the theater. Enter 'exit' to quit.");
            choice = scanner.nextLine();

            if (Objects.equals(choice, "1")) {
                bookSeat(seats, scanner);
            } else if (Objects.equals(choice, "2")) {
                cancelSeat(seats, scanner);
            } else if (Objects.equals(choice, "3")) {
                printTheater(seats);
            } else if (Objects.equals(choice.toLowerCase(), "exit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please select one of the given options.");
            }
        }
    }

    private static void bookSeat(boolean[][] seats, Scanner scanner) {
        int[] seatIndex = askForSeat(seats, scanner);

        int row = seatIndex[0];
        int col = seatIndex[1];

        if (seats[row][col]) {
            System.out.println("Seat is already booked. Please choose another.");
        } else {
            seats[row][col] = true;
            System.out.println("Seat successfully booked.");
        }
    }

    private static void cancelSeat(boolean[][] seats, Scanner scanner) {
        int[] seatIndex = askForSeat(seats, scanner);

        int row = seatIndex[0];
        int col = seatIndex[1];

        if (seats[row][col]) {
            seats[row][col] = false;
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Seat is not booked.");
        }
    }

    private static int[] askForSeat(boolean[][] seats, Scanner scanner) {
        while (true) {
            System.out.println("Enter the seat code (e.g., A1):");
            String input = scanner.nextLine();
            int[] seatIndex = getSeatIndex(seats, input);

            if (seatIndex == null) {
                System.out.println("Invalid input code, please try again.");
            } else {
                return seatIndex;
            }
        }
    }

    private static void printTheater(boolean[][] seats) {
        System.out.println("Theater Seating Diagram:");
        System.out.print("   ");
        for (int col = 1; col <= seats[0].length; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        for (int row = 0; row < seats.length; row++) {
            System.out.printf("%c: ", (char) ('A' + row));
            for (int col = 0; col < seats[row].length; col++) {
                System.out.print(seats[row][col] ? "[X]" : "[ ]");
            }
            System.out.println();
        }
    }

    public static int[] getSeatIndex(boolean[][] seats, String input) {
        if (input == null || input.length() < 2) {
            System.out.println("Invalid input format.");
            return null;
        }

        char rowChar = Character.toUpperCase(input.charAt(0));
        String columnPart = input.substring(1);

        int row = rowChar - 'A';

        if (row < 0 || row >= seats.length) {
            System.out.println("Row must correspond to a valid letter within the theater's size.");
            return null;
        }

        int column;
        try {
            column = Integer.parseInt(columnPart);
        } catch (NumberFormatException e) {
            System.out.println("Column must be a number.");
            return null;
        }

        if (column < 1 || column > seats[0].length) {
            System.out.println("Column must be between 1 and " + seats[0].length + ".");
            return null;
        }

        column -= 1;

        return new int[]{row, column};
    }
}

