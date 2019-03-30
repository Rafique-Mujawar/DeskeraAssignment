package rafique.mujawar.deskera.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rafique.mujawar.deskera.database.entities.TabletTabItem;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Dao
public interface TabletTabItemDao {

  @Query("SELECT * FROM TabletTabItem")
  List<TabletTabItem> getAll();

  @Query("SELECT * FROM TabletTabItem WHERE id == :id")
  TabletTabItem getItemById(long id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertItem(TabletTabItem item);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMultiples(List<TabletTabItem> items);

  @Delete
  void deleteItem(TabletTabItem item);

  @Delete
  void deleteMultiples(List<TabletTabItem> items);

  @Update
  void updateItem(TabletTabItem item);
}
