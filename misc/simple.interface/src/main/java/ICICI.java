import java.util.stream.IntStream;

/**
 * @author pratim
 *
 */
public class ICICI implements BankTransfers {

  /*
   * (non-Javadoc)
   *
   * @see BankTransfers#encrypt(java.lang.String)
   *
   * add 1 with the ASCII value of the character and insert number '1' after
   * every character.
   *
   */
  @Override
  public String encrypt(String plainTxt) {
    StringBuilder encrypted = new StringBuilder();
    plainTxt.chars()
        .forEachOrdered(chr -> encrypted.append((char) (chr + 1)).append("1"));
    return encrypted.toString();
  }

  /*
   * (non-Javadoc)
   *
   * @see BankTransfers#decrypt(java.lang.String)
   *
   * parsing every even character, since every odd character is hardcoded to be
   * 1
   */
  @Override
  public String decrypt(String encTxt) {
    StringBuilder decrypted = new StringBuilder();
    char[] encArray = encTxt.toCharArray();
    IntStream.range(0, encTxt.length()).filter(n -> n % 2 == 0).boxed()
        .forEach(index -> decrypted.append((char) ((encArray[index]) - 1)));
    return decrypted.toString();
  }

}
