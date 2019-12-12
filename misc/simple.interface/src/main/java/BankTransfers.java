
/**
 * @author pratim
 *
 */
public interface BankTransfers {

  /**
   * @param plainTxt
   * @return String
   */
  public String encrypt(String plainTxt);

  /**
   * @param encTxt
   * @return String
   */
  public String decrypt(String encTxt);

}
