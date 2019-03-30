package rafique.mujawar.deskera.database;

import android.arch.persistence.room.Room;

import rafique.mujawar.deskera.DeskeraApplication;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public class DatabaseManager {
  private static DatabaseManager mInstance = new DatabaseManager();
  private DeskeraDatabase database;

  private DatabaseManager() {
    if (null == database) {
      database = Room.databaseBuilder(DeskeraApplication.getAppContext(), DeskeraDatabase.class,
          "deskeradb").allowMainThreadQueries().build();
    }
  }

  public static DatabaseManager getInstance() {
    if (null == mInstance) {
      mInstance = new DatabaseManager();
    }
    return mInstance;
  }

  public DeskeraDatabase getDatabase() {
    return database;
  }
}
