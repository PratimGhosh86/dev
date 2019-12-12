
/**
 * @author pratim
 */
public class Contact {

  private String name;
  private String mobileNumber;

  /**
   * @param name
   * @param mobileNumber
   */
  public Contact(final String name, final String mobileNumber) {
    super();
    this.name = name;
    this.mobileNumber = mobileNumber;
  }

  /**
   * @return the name
   */
  public final String getName() {
    return this.name;
  }

  /**
   * @param name the name to set
   */
  public final void setName(final String name) {
    this.name = name;
  }

  /**
   * @return the mobileNumber
   */
  public final String getMobileNumber() {
    return this.mobileNumber;
  }

  /**
   * @param mobileNumber the mobileNumber to set
   */
  public final void setMobileNumber(final String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

}
