package rafique.mujawar.deskera;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public enum Temperature {
  FARENHEIT("Farenheit"), CELCIUS("Celcius");
  private final String value;

  Temperature(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
