import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author pratim
 *
 */
public class CryptoUtility {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // displaying the available options
    System.out.println("Select the Bank Name");
    System.out.println("1.ICICI");
    System.out.println("2.HDFC");
    // read the selected option
    try (final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(System.in))) {
      final String bank = bufferedReader.readLine();
      // declaring variables to store input, encrypted and decrypted message
      String message = "", encMsg = "", decMsg = "";
      switch (bank) {
        case "1":
          // selected ICICI
          System.out.println("Enter the Message to Transfer");
          message = bufferedReader.readLine();
          ICICI icici = new ICICI();
          encMsg = icici.encrypt(message);
          decMsg = icici.decrypt(encMsg);
          break;
        case "2":
          // selected HDFC
          System.out.println("Enter the Message to Transfer");
          message = bufferedReader.readLine();
          HDFC hdfc = new HDFC();
          encMsg = hdfc.encrypt(message);
          decMsg = hdfc.decrypt(encMsg);
          break;
        default:
          // invalid option selected
          System.err.println("Invalid Type");
          break;
      }
      // display encrypted and decrypted message if input message was valid
      if (message.length() > 0) {
        System.out
            .println(String.format("The Encrypted message is : %s", encMsg));
        System.out
            .println(String.format("The Decrypted message is : %s", decMsg));
      }
    } catch (IOException e) {
      System.err.println("Invalid input");
    }

  }

}
