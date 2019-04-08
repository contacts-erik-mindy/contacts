import java.util.ArrayList;

public class Contact {
  private static ArrayList<Contact> allContacts = new ArrayList<>();
  private static Integer contactNum = allContacts.size();

  private String firstName;
  private String lastName;
  private String phoneNumber;

  public Contact(String firstName, String lastName, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
  }

  public Contact() {

  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}