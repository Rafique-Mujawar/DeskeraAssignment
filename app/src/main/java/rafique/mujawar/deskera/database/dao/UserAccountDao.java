package rafique.mujawar.deskera.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import rafique.mujawar.deskera.database.entities.UserAccount;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Dao
public interface UserAccountDao {

  @Insert
  void addUserAccount(UserAccount account);

  @Query("SELECT * FROM UserAccount WHERE id =:id")
  UserAccount getUserAccount(long id);

  @Update
  void updateUserAccount(UserAccount account);
}
