package rafique.mujawar.deskera.utils;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
public interface DeskeraConstants {
  long SPLASH_DURATION_MILLIS = 500;
  String JPG_EXTENSION = ".jpg";
  String PROVIDER = ".provider";
  String IMAGE_FOLDER = "/DeskeraProfile/";
  String IMAGE_TYPE = "image/*";
  String ARG_UNIT = "arg_unit";

  interface RequestCodes {
    int REQUEST_CAMERA = 1234;
    int REQUEST_GALLERY = 9162;
    int PERMISSIONS_REQUEST_WRITE_STORAGE = 778;
    int REQUEST_TEMPERATURE_UNIT = 323;
  }
}
