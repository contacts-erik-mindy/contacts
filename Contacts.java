package ContactsManager;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Contacts {

  public static void main(String[] args) {
    String directory = "./";
    String filename = "contacts.txt";

    Path contactDirectory = Paths.get(directory);
    Path contactFile = Paths.get(directory, filename);

    try {
      if (Files.notExists(contactDirectory)) {
        Files.createDirectories(contactDirectory);
      }

      if (Files.notExists(contactFile)) {
        Files.createDirectories(contactFile);
      }

    } catch (IOException ioe) {
      System.out.println(ioe);

    }

    Boolean exit = false;
    Scanner sc = new Scanner(System.in);

    System.out.println("Welcome to Contacts Manager!");

    do {
      System.out.printf("" +
          "\nMenu:" +
          "\n1. View Contacts" +
          "\n2. Add a new contact" +
          "\n3. Search a contact by name" +
          "\n4. Remove a contact"
      );

      String response = sc.nextLine();

      switch (response) {
        case "1":
          System.out.println(viewContacts());
          break;
        case "2":
          System.out.println(addContact());
          break;
        case "3":
          System.out.println(searchContacts());
          break;
        case "4":
          System.out.println(removeContact());
          break;
        case "5":
          exit = true;
      }

    } while (!exit);
  }

  public static String viewContacts() {
    return "Selected: View Contacts";
  }

  public static String addContact() {
    return "Selected: Add Contact";
  }

  public static String searchContacts() {
    return "Selected: Search Contact";
  }

  public static String removeContact() {
    return "Selected: Remove Contact";
  }

}
