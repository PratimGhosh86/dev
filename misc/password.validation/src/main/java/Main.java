import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
      // read username from console
      final String username = bufferedReader.readLine();
      // read password from console
      final String defaultPassword = bufferedReader.readLine();
      // validate default password and display result
      System.out
          .println(
              UserMainCode.validatePassword(username, defaultPassword) ? "Valid"
                  : "Invalid");
    } catch (final IOException e) {
      e.printStackTrace();
    }

  }
}
