package rafique.mujawar.deskera.database;

import android.arch.persistence.room.Room;

import rafique.mujawar.deskera.DeskeraApplication;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public class DatabaseManager {
  private static DeskeraDatabase database;

  public static DeskeraDatabase getDatabase() {
    if (null == database) {
      database = Room.databaseBuilder(DeskeraApplication.getAppContext(), DeskeraDatabase.class,
          "deskeradb").allowMainThreadQueries().build();
    }
    return database;
  }
}
