
/*
 * Create a Java program to build contact list with values contact name and
 * mobile number, Store the contact objects into a list and provide search
 * option to search contacts based on contact name. If the contact is not found
 * then display a message "Contact not found" and if there are multiple contacts
 * with the same name display the first occurrence of the contact
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author pratim
 */
public class Main {

  /**
   * @param args
   */
  public static void main(final String[] args) {
    try (final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(System.in))) {
      System.out.println("Enter number of contacts:");
      final int noOfContacts = Integer.parseInt(bufferedReader.readLine());
      if (noOfContacts > 0) {
        final List<Contact> contacts = new ArrayList<>();
        // Read all contacts
        IntStream.rangeClosed(1, noOfContacts).forEachOrdered(index -> {
          System.out.println(String.format("Enter Contact %s", index));
          try {
            System.out.println("Enter Contact Name");
            final String name = bufferedReader.readLine();
            System.out.println("Enter Mobile Number");
            final String mobileNumber = bufferedReader.readLine();
            // add new contact to contacts list
            contacts.add(new Contact(name, mobileNumber));
          } catch (final IOException e) {
            e.printStackTrace();
          }
        });
        System.out.println("Search contact by name:");
        final String searchName = bufferedReader.readLine();
        // Match and filter contact based on first name, will return the first
        // match even if duplicates exists
        final Optional<Contact> match =
            contacts.stream()
                .filter(contact -> contact.getName().equals(searchName))
                .findFirst();
        if (match.isPresent()) {
          // match found, print details
          System.out
              .println(String.format("Contact Name:%s", match.get().getName()));
          System.out
              .println(String.format("Contact Mobile Number:%s",
                  match.get().getMobileNumber()));
        } else
          System.out.println("Contact not found");

      }
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
