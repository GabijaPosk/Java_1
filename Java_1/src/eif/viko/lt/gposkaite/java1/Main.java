package eif.viko.lt.gposkaite.java1;

import eif.viko.lt.gposkaite.java1.Exceptions.BookLimitExceededException;
import eif.viko.lt.gposkaite.java1.Exceptions.BookNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        library.demonstrateReflection();

        while (true) {
            System.out.println("Pasirinkite savo vaidmenį:");
            System.out.println("1. Skaitytojas");
            System.out.println("2. Darbuotojas");
            System.out.println("3. Išeiti");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    readerMenu(library, scanner);
                    break;
                case 2:
                    employeeMenu(library, scanner);
                    break;
                case 3:
                    System.out.println("Programa baigia darbą.");
                    return;
                default:
                    System.out.println("Pasirinkimas neatpažintas, bandykite dar kartą.");
                    break;
            }
        }
    }

    private static void readerMenu(Library library, Scanner scanner) {
        while (true) {
            System.out.println("Skaitytojo meniu:");
            System.out.println("1. Peržiūrėti knygas");
            System.out.println("2. Skolinti knygą");
            System.out.println("3. Grąžinti knygą");
            System.out.println("4. Peržiūrėti savo knygas");
            System.out.println("5. Registruotis kaip skaitytojas");
            System.out.println("6. Ieškoti knygų pagal autorių");
            System.out.println("7. Peržiūrėti surūšiuotas knygas pagal pavadinimą");
            System.out.println("8. Grįžti atgal");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Visos bibliotekos knygos:");
                    for (Book book : library.getBooks()) {
                        System.out.println(book.getTitle());
                    }
                    break;
                case 2:
                    borrowBook(library, scanner);
                    break;
                case 3:
                    returnBook(library, scanner);
                    break;
                case 4:
                    System.out.println("Įveskite savo el. paštą:");
                    String email = scanner.nextLine();
                    library.viewReaderBooks(email);
                    break;
                case 5:
                    registerReader(library, scanner);
                    break;
                case 6:
                    searchBooksByAuthor(library, scanner);
                    break;
                case 7:
                    System.out.println("Surūšiuotos knygos pagal pavadinimą:");
                    List<Pair<String, String>> sortedBooks = library.getSortedBooksByTitle();
                    for (Pair<String, String> pair : sortedBooks) {
                        System.out.println("ID: " + pair.getFirst() + ", Pavadinimas: " + pair.getSecond());
                    }
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Pasirinkimas neatpažintas, bandykite dar kartą.");
                    break;
            }
        }
    }

    private static void employeeMenu(Library library, Scanner scanner) {
        while (true) {
            System.out.println("Darbuotojo meniu:");
            System.out.println("1. Peržiūrėti knygas");
            System.out.println("2. Pridėti naują knygą");
            System.out.println("3. Registruoti naują narį");
            System.out.println("4. Peržiūrėti pasiskolintas knygas");
            System.out.println("5. Generuoti ataskaitą apie populiariausius autorius");
            System.out.println("6. Peržiūrėti surūšiuotas knygas pagal pavadinimą");
            System.out.println("7. Grįžti atgal");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Visos bibliotekos knygos:");
                    for (Book book : library.getBooks()) {
                        System.out.println(book.getTitle());
                    }
                    break;
                case 2:
                    addBook(library, scanner);
                    break;
                case 3:
                    registerReader(library, scanner);
                    break;
                case 4:
                    System.out.println("Pasiskolintos knygos:");
                    library.viewBorrowedBooks();
                    break;
                case 5:
                    generatePopularBooksReport(library);
                    break;
                case 6:
                    System.out.println("Surūšiuotos knygos pagal pavadinimą:");
                    List<Pair<String, String>> sortedBooks = library.getSortedBooksByTitle();
                    for (Pair<String, String> pair : sortedBooks) {
                        System.out.println("ID: " + pair.getFirst() + ", Pavadinimas: " + pair.getSecond());
                    }
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Pasirinkimas neatpažintas, bandykite dar kartą.");
                    break;
            }
        }
    }

    private static void registerReader(Library library, Scanner scanner) {
        System.out.println("Skaitytojo registracija:");
        System.out.println("Įveskite el. paštą:");
        String email = scanner.nextLine();
        System.out.println("Įveskite slaptažodį:");
        String password = scanner.nextLine();
        library.registerReader(email, password);
        System.out.println("Skaitytojas sėkmingai užregistruotas.");
    }

    private static void borrowBook(Library library, Scanner scanner) {
        System.out.println("Įveskite knygos ID, kurią norite pasiskolinti:");
        String bookId = scanner.nextLine();
        System.out.println("Įveskite savo el. paštą:");
        String email = scanner.nextLine();
        System.out.println("Įveskite slaptažodį:");
        String password = scanner.nextLine();
        Reader reader = library.loginReader(email, password);
        if (reader != null) {
            try {
                library.borrowBook(bookId, reader);
            } catch (BookNotFoundException e) {
                System.out.println("Knyga nerasta.");
            } catch (BookLimitExceededException e) {
                System.out.println("Knyga jau pasiskolinta.");
            }
        } else {
            System.out.println("Neteisingas el. paštas arba slaptažodis.");
        }
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.println("Knygos pridėjimas:");
        System.out.println("Įveskite knygos pavadinimą:");
        String title = scanner.nextLine();
        System.out.println("Įveskite knygos autorių:");
        String author = scanner.nextLine();
        System.out.println("Įveskite knygos ID:");
        String id = scanner.nextLine();
        library.addBook(new Book(id, title, author));
        System.out.println("Knyga sėkmingai pridėta.");
    }

    private static void returnBook(Library library, Scanner scanner) {
        System.out.println("Knygos grąžinimas:");
        System.out.println("Įveskite knygos ID, kurią norite grąžinti:");
        String bookId = scanner.nextLine();
        System.out.println("Įveskite savo el. paštą:");
        String email = scanner.nextLine();
        System.out.println("Įveskite slaptažodį:");
        String password = scanner.nextLine();
        Reader reader = library.loginReader(email, password);
        if (reader != null) {
            try {
                library.returnBook(bookId, reader);
            } catch (BookNotFoundException e) {
                System.out.println("Knyga nerasta.");
            }
        } else {
            System.out.println("Neteisingas el. paštas arba slaptažodis.");
        }
    }

    private static void searchBooksByAuthor(Library library, Scanner scanner) {
        System.out.println("Įveskite autoriaus vardą:");
        String author = scanner.nextLine();
        List<Book> booksByAuthor = library.searchBooksByAuthor(author);
        if (booksByAuthor.isEmpty()) {
            System.out.println("Nerasta knygų šio autoriaus.");
        } else {
            System.out.println("Rastos knygos:");
            for (Book book : booksByAuthor) {
                System.out.println("Pavadinimas: " + book.getTitle());
            }
        }
    }

    private static void generatePopularBooksReport(Library library) {
        Map<String, Integer> popularBooks = library.generatePopularBooksReport();
        if (popularBooks.isEmpty()) {
            System.out.println("Nėra populiarių knygų.");
        } else {
            System.out.println("Populiariausios knygos:");
            for (Map.Entry<String, Integer> entry : popularBooks.entrySet()) {
                System.out.println("Knyga: " + entry.getKey() + ", Kiekis: " + entry.getValue());
            }
        }
    }
}
