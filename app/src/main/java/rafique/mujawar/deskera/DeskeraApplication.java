package rafique.mujawar.deskera;

import android.app.Application;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public class DeskeraApplication extends Application {

  private static DeskeraApplication appContext;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = this;
  }

  public static DeskeraApplication getAppContext() {
    return appContext;
  }
}
