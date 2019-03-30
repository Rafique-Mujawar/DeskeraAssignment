package rafique.mujawar.deskera.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Entity
public class UserAccount {
  @PrimaryKey
  public long id;

  @ColumnInfo
  public String email;

  @ColumnInfo
  public String dateOfJoining;

  @ColumnInfo
  public String temperatureUnit;

  @ColumnInfo
  public String hobbies;

  @ColumnInfo
  public boolean isSoundOn;

  @ColumnInfo
  public boolean isNotificationOn;

  @ColumnInfo
  public String probationEndDate;

  @ColumnInfo
  public String duration;

  @ColumnInfo
  public String probationLength;

  @ColumnInfo
  public String dateOfPermanent;
}
