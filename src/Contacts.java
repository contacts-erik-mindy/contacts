import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contacts {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String directory = "./src/data";
    String filename = "contacts.txt";

    Path contactDirectory = Paths.get(directory);
    Path contactFile = Paths.get(directory, filename);

    try {
      if (Files.notExists(contactDirectory)) {
        Files.createDirectories(contactDirectory);
      }

      if (Files.notExists(contactFile)) {
        Files.createFile(contactFile);
      }

    } catch (IOException ioe) {
      System.out.println(ioe);

    }

    ArrayList<Contact> contacts = new ArrayList<>(loadContacts());

    Boolean exit = false;

    System.out.println("Welcome to Contacts Manager!");
//    searchContacts(contacts);
//    viewContacts(contacts);
//    System.out.println(contacts.size());
////
//    String supposedContact = addContact();
//    System.out.println(supposedContact);
//    searchContacts();

    do {
      System.out.printf("" +
          "\nMenu:" +
          "\n1. View Contacts" +
          "\n2. Add a new contact" +
          "\n3. Search a contact by name" +
          "\n4. Remove a contact" +
          "\n5. Exit\n> "
      );

      String response = sc.nextLine();

      switch (response) {
        case "1":
          viewContacts(contacts);
          break;
        case "2":
          addContact(contacts);
          break;
        case "3":
          searchContacts(contacts);
          break;
        case "4":
          System.out.println(removeContact());
          break;
        case "5":
          exit = true;
      }

    } while (!exit);

    System.out.println("Goodbye...\n");
  }

  public static ArrayList<Contact> loadContacts() {
    ArrayList<Contact> contacts = new ArrayList<>();
    try {
      Path contactsPath = Paths.get("./src/data", "contacts.txt");
      List<String> contactsList = Files.readAllLines(contactsPath);
      for (int i = 0; i < contactsList.size(); i++) {
        String newContactStr = contactsList.get(i);
        String[] newDetails = newContactStr.split(" ");
        Contact newContact = new Contact(newDetails[0], newDetails[1], newDetails[2]);
        contacts.add(newContact);
      }
    } catch (Exception e) {
      System.out.println("viewContacts Exception: " + e);
    }

    return contacts;
  }

  public static void viewContacts(ArrayList<Contact> contacts) {
    for (Contact contact : contacts) {
      System.out.println(contact.getFirstName() + " " + contact.getLastName() + ", " + contact.getPhoneNumber());
    }
  }

  public static ArrayList<Contact> addContact(ArrayList<Contact> contacts) {
    Scanner scFirst = new Scanner(System.in);
    Scanner scLast = new Scanner(System.in);
    Scanner scPhone = new Scanner(System.in);
    Scanner scConfirm = new Scanner(System.in);
    String firstName, lastName, phoneNum, response;
    boolean confirmNew = false;

    do {
      System.out.println("What is the first name?");
      firstName = scFirst.nextLine();

      System.out.println("What is the last name?");
      lastName = scLast.nextLine();

      System.out.println("What is the phone number?");
      phoneNum = scPhone.nextLine();

      System.out.printf("You entered:\nFirst name: %s\nLast name: %s" +
              "\nPhone number: %s\n\nAdd contact? (y / n)\n> ",
          firstName, lastName, phoneNum);
      response = scConfirm.next();
      System.out.println(response);
      System.out.println(response.toLowerCase());
      if (response.toLowerCase().equals("y"))
        confirmNew = true;
      System.out.println(confirmNew);
    } while (!confirmNew);

    Contact newContact = new Contact(firstName, lastName, phoneNum);
    contacts.add(newContact);
    try {
      Files.write(
          Paths.get("./src/data", "contacts.txt"),
          Arrays.asList(firstName + " " + lastName + " " + phoneNum),
          StandardOpenOption.APPEND
      );
    } catch (Exception e) {
      System.out.println("addContacts Exception: " + e);
    }

    loadContacts();

    return contacts;
  }

  public static void searchContacts(ArrayList<Contact> contacts) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Search by first name: ");
    String searchFName = sc.next();
    boolean found = false;
    for (Contact contact : contacts) {
      if (contact.getFirstName().contains(searchFName)) {
        System.out.println("Person found");
        found = true;
        System.out.println(contact.getFirstName() + " " + contact.getLastName() + ", " + contact.getPhoneNumber());      }
    }

    if (!found) System.out.println("Person not found");

//    try {
//      Path contactsPath = Paths.get("./src/data", "contacts.txt");
//      List<String> contactsList = Files.readAllLines(contactsPath);
//      System.out.println(contactsList);
//      System.out.println("Who would you like to search for? ");
//      String searchContact = sc.nextLine();
//      System.out.println(contactsList.indexOf(searchContact));
//    } catch (Exception e) {
//        System.out.println("searchContacts Exception: " + e);
//    }
//    return "Selected: Search Contact";
  }

  public static String removeContact() {
    return "Selected: Remove Contact";
  }

}
