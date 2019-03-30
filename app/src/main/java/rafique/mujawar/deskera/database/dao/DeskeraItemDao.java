package rafique.mujawar.deskera.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rafique.mujawar.deskera.database.entities.DeskeraItem;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Dao
public interface DeskeraItemDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertItem(DeskeraItem item);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAllItems(List<DeskeraItem> items);

  @Query("SELECT * FROM DeskeraItem")
  List<DeskeraItem> getAll();

  @Query("SELECT * FROM DeskeraItem WHERE category LIKE :category")
  List<DeskeraItem> getCategoryItems(String category);

  @Query("SELECT * FROM DeskeraItem WHERE isFavourite == 1")
  List<DeskeraItem> getFavourites();

  @Update(onConflict = OnConflictStrategy.IGNORE)
  void updateItem(DeskeraItem item);
}
