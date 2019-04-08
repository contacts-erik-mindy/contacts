import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contacts {

  //   M A I N   ///////////////////////////////////////////////////////

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    // sets file contacts are stored in and opens it

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

    // create ArrayList of all the contacts in loadContacts

    ArrayList<Contact> contacts = new ArrayList<>(loadContacts());


    //   M A I N   M E N U   //////

    Boolean exit = false;

    System.out.printf("" +
          "\n   ====================================" +
          "\n  |    Welcome to Contacts Manager!    |" +
          "\n   ====================================");

    do {
      System.out.printf("\n" +
          "\n  +------------------------------------+" +
          "\n  |     MENU:                          |" +
          "\n  +------------------------------------+" +
          "\n  |                                    |" +
          "\n  |  1. View Contacts                  |" +
          "\n  |  2. Add a new contact              |" +
          "\n  |  3. Search a contact by name       |" +
          "\n  |  4. Remove a contact               |" +
          "\n  |  5. Exit                           |" +
          "\n  |                                    |" +
          "\n  +------------------------------------+" +
          "\n\n  Enter selection: \n  > "
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
          contacts = removeContact(contactFile, contacts);
          break;
        case "5":
          exit = true;
      }

    } while (!exit);

    System.out.printf("" +
        "\n   ====================================" +
        "\n  |              Goodbye!              |" +
        "\n   ====================================");
  }

  //   L O A D   C O N T A C T S   /////////////////////////////////////

  public static ArrayList<Contact> loadContacts() {

    // initializes ArrayList of Contact objects

    ArrayList<Contact> contacts = new ArrayList<>();

    // read contact file

    try {
      Path contactsPath = Paths.get("./src/data", "contacts.txt");
      List<String> contactsList = Files.readAllLines(contactsPath);

    // iterate each line of the contacts file and create a new Contact
    // object, add each object into ArrayList

      for (int i = 0; i < contactsList.size(); i++) {
        String newContactStr = contactsList.get(i);
        String[] newDetails = newContactStr.split(" ");
        Contact newContact = new Contact(newDetails[0], newDetails[1], newDetails[2]);
        contacts.add(newContact);
      }

    } catch (Exception e) {
      System.out.println("viewContacts Exception: " + e);
    }

    //return ArrayList to main

    return contacts;
  }

  //   V I E W   C O N T A C T S   /////////////////////////////////////

  public static void viewContacts(ArrayList<Contact> contacts) {

    for (int i = 0; i < 10; ++i) System.out.println();

      System.out.printf("\n" +
        "\n  +------------------------------------+" +
        "\n  |     View Contacts                  |" +
        "\n  +------------------------------------+\n");

    // iterate through contacts ArrayList and print
    System.out.print("\n  Name                   |  Phone Number" +
                     "\n  --------------------------------------");

    for (Contact contact : contacts) {
      System.out.printf("\n  %-10s %-10s  |  %s", contact.getLastName(),
          contact.getFirstName(), contact.getPhoneNumber());
    }

    for (int i = 0; i < 3; ++i) System.out.println();

  }

  //   A D D   C O N T A C T   /////////////////////////////////////////

  public static ArrayList<Contact> addContact(ArrayList<Contact> contacts) {

    // Creating multiple scanners was easier than dealing with
    // micromanaging one scanner

    Scanner scFirst = new Scanner(System.in);
    Scanner scLast = new Scanner(System.in);
    Scanner scPhone = new Scanner(System.in);
    Scanner scConfirm = new Scanner(System.in);
    Scanner scExit = new Scanner(System.in);
    String firstName, lastName, phoneNum, response;
    boolean confirmNew = false;

    // Asks for contact details and confirmation before adding new contact

    for (int i = 0; i < 10; ++i) System.out.println();

    do {
      System.out.printf("\n" +
          "\n  +------------------------------------+" +
          "\n  |     Add New Contact                |" +
          "\n  +------------------------------------+");

      System.out.print("\n" +
          "\n  What is the first name?\n  > ");
      firstName = scFirst.next();

      System.out.print("\n  What is the last name?\n  > ");
      lastName = scLast.next();

      System.out.print("\n  What is the phone number?\n  > ");
      phoneNum = scPhone.next();

      System.out.printf("\n\n\n" +
              "  You entered:" +
              "\n\n\tFirst Name:   %s" +
                "\n\tLast Name:    %s" +
                "\n\tPhone Number: %s" +
              "\n\n  Add contact? (y / n)\n  > ",
          firstName, lastName, phoneNum);

      response = scConfirm.next();

      if (response.toLowerCase().equals("y")) {
        confirmNew = true;
      } else if (response.toLowerCase().equals("n")) {
        return contacts;
      }

    } while (!confirmNew);

    // Adds new contacts to array
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

    for (int i = 0; i < 10; ++i) System.out.println();

    return contacts;
  }

  //   S E A R C H   C O N T A C T S   /////////////////////////////////

  public static void searchContacts(ArrayList<Contact> contacts) {
    for (int i = 0; i < 10; ++i) System.out.println();

    System.out.printf("\n" +
        "\n  +------------------------------------+" +
        "\n  |     Search Contacts                |" +
        "\n  +------------------------------------+");

    Scanner sc = new Scanner(System.in);
    System.out.println("\n\n  Search by first name: ");
    String searchFName = sc.next();
    boolean found = false;
    for (Contact contact : contacts) {
      if (contact.getFirstName().contains(searchFName)) {
        System.out.println("  Person found");
        found = true;
        System.out.println(contact.getFirstName() + " " + contact.getLastName() + ", " + contact.getPhoneNumber());      }
    }

    if (!found) System.out.println("  Person not found");
    for (int i = 0; i < 10; ++i) System.out.println();

  }

  //   R E M O V E   C O N T A C T   ///////////////////////////////////

  public static ArrayList<Contact> removeContact(Path contactFile, ArrayList<Contact> contacts) {

    for (int i = 0; i < 10; ++i) System.out.println();

    System.out.printf("\n" +
        "\n  +------------------------------------+" +
        "\n  |     Remove Contact                 |" +
        "\n  +------------------------------------+");

    // Selects contact to remove and finds it in contacts ArrayList

    Scanner sc = new Scanner(System.in);
    Scanner scConfirm = new Scanner(System.in);

    System.out.print("\n" +
        "\n  Name                   |  Phone Number" +
        "\n  --------------------------------------");

    for (Contact contact : contacts) {
      System.out.printf("\n  %-10s %-10s  |  %s", contact.getLastName(),
          contact.getFirstName(), contact.getPhoneNumber());
    }

    System.out.print(
        "\n\n  Select a person to remove (enter first name):\n  > ");
    String searchFName = sc.next();
    searchFName = searchFName.toLowerCase();

    boolean contactFound = false;
    Contact contactToRemove = new Contact();

    for (Contact contact : contacts) {
      if (contact.getFirstName().toLowerCase().equals(searchFName)) {
        contactFound = true;
        contactToRemove = contact;
      }
    }

    if (contactFound) {
      System.out.printf("\n  %s, %s found.  Delete? (y/n)\n  > ",
          contactToRemove.getLastName(), contactToRemove.getFirstName());
      String confirmRemoval = scConfirm.next();

      if(confirmRemoval.equals("y") || confirmRemoval.equals("Y")) {

        // Copies the contacts ArrayList (except for the contact to be
        // removed) into new a ArrayList, newContacts

        ArrayList<Contact> newContacts = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
          if (!contacts.get(i).getFirstName().toLowerCase().contains(searchFName)) {
            newContacts.add(contacts.get(i));
          }
        }

        // Iterates through newContacts and creates a new ArrayList
        // called newContactStrings (containing all contacts as strings
        // instead of Contact objects) to write over contacts.txt

        List<String> newContactStrings = new ArrayList<>();

        for (Contact contact : newContacts) {
          newContactStrings.add(contact.getFirstName() + " " +
              contact.getLastName() + " " + contact.getPhoneNumber());
        }

        // Overwrites contacts.txt without the contact to be removed

        try {
          Files.write(contactFile, newContactStrings);
        } catch (Exception e) {
          System.out.println("addContacts Exception: " + e);
        }

        // Return newContacts to overwrite contacts in main since a contact
        // was removed

        System.out.printf("\n  %s, %s was removed from contacts.",
            contactToRemove.getLastName(), contactToRemove.getFirstName());

        for (int i = 0; i < 10; ++i) System.out.println();

        return newContacts;

        //  You may ask why we didn't invoke loadContacts in this function
        //  like we did in addContact.  We decided to return newContacts to
        //  main instead of writing an overloaded version of loadContacts,
        //  which is what we would have had to do to send the new ArrayList
        //  from here to loadContacts and back to main
      }
    }
      return contacts;
  }

}

//   S P E C I A L   T H A N K S:   M I K E   F L O R E S