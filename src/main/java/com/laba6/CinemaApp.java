package com.laba6;
import java.util.Scanner;
import java.util.Random;

public class CinemaApp {
    private static int[][][] cinema;
    private static int numHalls = 5;
    private static int numRows = 10;
    private static int numSeats = 20;
    private static int[][][] ticketIDs;

    public static void main(String[] args) {
        initCinema();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Оберіть дію:");
            System.out.println("1. Бронювати місця");
            System.out.println("2. Скасувати бронювання за ID квитка");
            System.out.println("3. Перевірити наявність місць");
            System.out.println("4. Друк схеми розміщення");
            System.out.println("5. Вийти");
            int choice = scanner.nextInt();

            if (choice == 1) {
                bookSeats(scanner);
            } else if (choice == 2) {
                cancelBookingByID(scanner);
            } else if (choice == 3) {
                checkAvailability(scanner);
            } else if (choice == 4) {
                printSeatingArrangement(scanner);
            } else if (choice == 5) {
                System.out.println("До побачення!");
                break;
            }
        }
    }

    private static void initCinema() {
        cinema = new int[numHalls][numRows][numSeats];
        ticketIDs = new int[numHalls][numRows][numSeats];

        for (int hall = 0; hall < numHalls; hall++) {
            for (int row = 0; row < numRows; row++) {
                for (int seat = 0; seat < numSeats; seat++) {
                    cinema[hall][row][seat] = 0;
                    ticketIDs[hall][row][seat] = 0;
                }
            }
        }
    }

    private static void bookSeats(Scanner scanner) {
        System.out.println("Введіть номер залу:");
        int hallNumber = scanner.nextInt();

        if (hallNumber < 1 || hallNumber > numHalls) {
            System.out.println("Невірний номер залу.");
            return;
        }

        System.out.println("Введіть номер ряду:");
        int row = scanner.nextInt();

        if (row < 1 || row > numRows) {
            System.out.println("Невірний номер ряду.");
            return;
        }

        System.out.println("Введіть номер місця:");
        int seatNumber = scanner.nextInt();

        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Невірний номер місця.");
            return;
        }

        if (cinema[hallNumber - 1][row - 1][seatNumber - 1] == 0) {
            int ticketID = generateTicketID();
            cinema[hallNumber - 1][row - 1][seatNumber - 1] = 1;
            ticketIDs[hallNumber - 1][row - 1][seatNumber - 1] = ticketID;
            System.out.println("Ви успішно забронювали місце!");
            System.out.println("Ваш ID квитка: " + ticketID);
        } else {
            System.out.println("Це місце вже заброньоване.");
        }
    }

    private static int generateTicketID() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private static void cancelBookingByID(Scanner scanner) {
        System.out.println("Введіть номер залу:");
        int hallNumber = scanner.nextInt();

        if (hallNumber < 1 || hallNumber > numHalls) {
            System.out.println("Невірний номер залу.");
            return;
        }

        System.out.println("Введіть ID квитка для скасування бронювання:");
        int ticketID = scanner.nextInt();

        for (int row = 0; row < numRows; row++) {
            for (int seat = 0; seat < numSeats; seat++) {
                if (cinema[hallNumber - 1][row][seat] == 1 && ticketID == ticketIDs[hallNumber - 1][row][seat]) {
                    cinema[hallNumber - 1][row][seat] = 0;
                    ticketIDs[hallNumber - 1][row][seat] = 0;
                    System.out.println("Бронювання скасоване успішно.");
                    return;
                }
            }
        }

        System.out.println("Квиток з таким ID не знайдений.");
    }



    private static void checkAvailability(Scanner scanner) {
        System.out.println("Введіть номер залу:");
        int hallNumber = scanner.nextInt();

        if (hallNumber < 1 || hallNumber > numHalls) {
            System.out.println("Невірний номер залу.");
            return;
        }

        System.out.println("Введіть номер ряду:");
        int row = scanner.nextInt();

        if (row < 1 || row > numRows) {
            System.out.println("Невірний номер ряду.");
            return;
        }

        System.out.println("Введіть кількість послідовних місць, які ви хочете забронювати:");
        int numSeatsToBook = scanner.nextInt();

        if (numSeatsToBook < 1 || numSeatsToBook > numSeats) {
            System.out.println("Невірна кількість місць.");
            return;
        }

        int consecutiveAvailableSeats = 0;
        int maxConsecutiveAvailableSeats = 0;

        for (int seat = 0; seat < numSeats; seat++) {
            if (cinema[hallNumber - 1][row - 1][seat] == 0) {
                consecutiveAvailableSeats++;
                if (consecutiveAvailableSeats > maxConsecutiveAvailableSeats) {
                    maxConsecutiveAvailableSeats = consecutiveAvailableSeats;
                }
                if (consecutiveAvailableSeats == numSeatsToBook) {
                    break; // Знайдено достатньо послідовних місць
                }
            } else {
                consecutiveAvailableSeats = 0;
            }
        }

        if (maxConsecutiveAvailableSeats >= numSeatsToBook) {
            System.out.println("Доступно " + maxConsecutiveAvailableSeats + " послідовних місць в ряду " + row + " залу " + hallNumber + ".");
        } else {
            System.out.println("Доступних послідовних місць недостатньо.");
        }
    }


    private static void printSeatingArrangement(Scanner scanner) {
        System.out.println("Введіть номер залу:");
        int hallNumber = scanner.nextInt();

        if (hallNumber < 1 || hallNumber > numHalls) {
            System.out.println("Невірний номер залу.");
        } else {
            System.out.println("Зал №" + hallNumber);
            for (int seat = 1; seat <= numSeats; seat++) {
                if (seat < 10 & seat > 1) {
                    System.out.print("      " + seat);
                }
                else {
                    System.out.print("     " + seat);
                }
            }
            System.out.println();

            for (int row = 0; row < numRows; row++) {
                System.out.print((row + 1) + "    ");
                for (int seat = 0; seat < numSeats; seat++) {
                    if (cinema[hallNumber - 1][row][seat] == 0) {
                        System.out.print("O      ");
                    } else {
                        System.out.print("X      ");
                    }
                }
                System.out.println();
            }
        }
    }
}




