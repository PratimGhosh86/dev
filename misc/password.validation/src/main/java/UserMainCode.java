import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pratim
 */
public class UserMainCode {

  /*
   * The default password is set from the characters in the user name. The last
   * 3 characters in the input string is repeated 3 times and the string
   * obtained is set as the default password
   */
  /**
   * @param username
   * @param password
   * @return {@link Boolean}
   */
  public static boolean validatePassword(final String username,
      final String password) {
    int count = 0;
    // check if username length is equal or more than 3
    if (username.length() >= 3) {
      // use last 3 characters of username to create expression for pattern
      // matching
      final Matcher m =
          Pattern.compile(username.substring(username.length() - 3))
              .matcher(password);
      // find count of matches
      while (m.find())
        count++;
    }
    // in valid scenario, count of matching pattern should be exactly 3
    return count == 3;
  }

}
