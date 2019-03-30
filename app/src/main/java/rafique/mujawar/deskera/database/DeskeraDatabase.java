package rafique.mujawar.deskera.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import rafique.mujawar.deskera.database.dao.DeskeraItemDao;
import rafique.mujawar.deskera.database.dao.TabletTabItemDao;
import rafique.mujawar.deskera.database.dao.UserAccountDao;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.database.entities.UserAccount;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Database(entities = {UserAccount.class, TabletTabItem.class, DeskeraItem.class}, version = 1)
public abstract class DeskeraDatabase extends RoomDatabase {
  public abstract UserAccountDao getUserAccountDao();

  public abstract TabletTabItemDao getTabletTabItemDao();

  public abstract DeskeraItemDao getDeskeraItemDao();
}
