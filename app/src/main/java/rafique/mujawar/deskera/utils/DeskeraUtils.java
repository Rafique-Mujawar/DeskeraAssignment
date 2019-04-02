package rafique.mujawar.deskera.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import rafique.mujawar.deskera.BuildConfig;
import rafique.mujawar.deskera.DeskeraApplication;
import rafique.mujawar.deskera.R;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
public class DeskeraUtils {

  public static Uri generateTimeStampPhotoFileUri(Context context) {
    File directory = getPhotoDirectory();
    Uri photoFileUri = null;
    if (directory != null) {
      File photoFile =
          new File(directory, System.currentTimeMillis() + DeskeraConstants.JPG_EXTENSION);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        photoFileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID +
            DeskeraConstants.PROVIDER, photoFile);
      } else {
        photoFileUri = Uri.fromFile(photoFile);
      }
    }
    return photoFileUri;
  }

  private static File getPhotoDirectory() {
    File outputDir = null;
    String externalStorageStagte = Environment.getExternalStorageState();
    if (externalStorageStagte.equals(Environment.MEDIA_MOUNTED)) {
      String path = Environment.getExternalStorageDirectory() + DeskeraConstants.IMAGE_FOLDER;
      outputDir = new File(path);
      if (!outputDir.exists()) {
        outputDir = new File(Environment.getExternalStorageDirectory().getPath()
            + DeskeraConstants.IMAGE_FOLDER);
        outputDir.mkdirs();
      }
    }
    return outputDir;
  }

  public static String loadJSONFromAsset(Activity activity, String file) {
    String json = null;
    try {
      InputStream is = activity.getAssets().open(file);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      json = new String(buffer, "UTF-8");
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
    return json;
  }


  public static String getDateDifference(long start, long end) {

    Calendar startCalendar = new GregorianCalendar();
    startCalendar.setTimeInMillis(start);
    Calendar endCalendar = new GregorianCalendar();
    endCalendar.setTimeInMillis(end);

    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    int diffMonth =
        diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    int days = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
    return DeskeraApplication.getAppContext()
        .getString(R.string.probation_duration, diffMonth, days);
  }

  public static String getProbationLength(long start, long end) {
    Calendar startCalendar = new GregorianCalendar();
    startCalendar.setTimeInMillis(start);
    Calendar endCalendar = new GregorianCalendar();
    endCalendar.setTimeInMillis(end);

    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    int diffMonth =
        diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    int days = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);

    if (diffMonth > 6 || diffMonth == 6 && days > 0) {
      return DeskeraApplication.getAppContext().getString(R.string.more_than_six_months);
    } else if (diffMonth == 6 && days == 0) {
      return DeskeraApplication.getAppContext().getString(R.string.six_months);
    } else {
      return DeskeraApplication.getAppContext().getString(R.string.less_than_six_months);
    }
  }

  public static String getDateFromMillis(long millis) {
    SimpleDateFormat formatter =
        new SimpleDateFormat(DeskeraConstants.DESKERA_DATE_FORMAT, Locale.getDefault());
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    return formatter.format(calendar.getTime());
  }

  public static String random() {
    Random generator = new Random();
    StringBuilder randomStringBuilder = new StringBuilder();
    int randomLength = generator.nextInt(20);
    char tempChar;
    for (int i = 0; i < randomLength; i++) {
      tempChar = (char) (generator.nextInt(96) + 32);
      randomStringBuilder.append(tempChar);
    }
    return randomStringBuilder.toString();
  }
}
