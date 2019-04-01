package rafique.mujawar.deskera;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public enum Temperature {
  FAHRENHEIT("Fahrenheit"), CELSIUS("Celsius");
  private final String value;

  Temperature(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
