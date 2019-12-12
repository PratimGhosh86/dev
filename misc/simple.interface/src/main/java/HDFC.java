import java.util.stream.IntStream;

/**
 * @author pratim
 *
 */
public class HDFC implements BankTransfers {

  /*
   * (non-Javadoc)
   *
   * @see BankTransfers#encrypt(java.lang.String)
   *
   * add 1 with the ASCII value of the character in the even Index and subtract
   * 1 with the ASCII value of the character in the odd Index. It does not
   * encrypt the space
   *
   */
  @Override
  public String encrypt(String plainTxt) {
    StringBuilder encrypted = new StringBuilder();
    char[] plainArray = plainTxt.toCharArray();
    IntStream.range(0, plainTxt.length()).forEach(index -> {
      if (plainArray[index] == ' ')
        encrypted.append(plainArray[index]);
      else
        switch (index % 2) {
          case 0:
            encrypted.append((char) (plainArray[index] + 1));
            break;
          case 1:
            encrypted.append((char) (plainArray[index] - 1));
            break;
          default:
            break;
        }
    });
    return encrypted.toString();
  }

  /*
   * (non-Javadoc)
   *
   * @see BankTransfers#decrypt(java.lang.String)
   */
  @Override
  public String decrypt(String encTxt) {
    StringBuilder decrypted = new StringBuilder();
    char[] encArray = encTxt.toCharArray();
    IntStream.range(0, encTxt.length()).forEach(index -> {
      if (encArray[index] == ' ')
        decrypted.append(encArray[index]);
      else
        switch (index % 2) {
          case 0:
            decrypted.append((char) (encArray[index] - 1));
            break;
          case 1:
            decrypted.append((char) (encArray[index] + 1));
            break;
          default:
            break;
        }
    });
    return decrypted.toString();
  }

}
