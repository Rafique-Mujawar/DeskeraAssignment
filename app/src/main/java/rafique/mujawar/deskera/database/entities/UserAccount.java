package rafique.mujawar.deskera.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Entity
public class UserAccount {
  @PrimaryKey
  public long id;

  @ColumnInfo
  public String name;

  @ColumnInfo
  public String email;

  @ColumnInfo
  public long dateOfJoining;

  @ColumnInfo
  public String temperatureUnit;

  @ColumnInfo
  public String hobbies;

  @ColumnInfo
  public boolean isSoundOn;

  @ColumnInfo
  public boolean isNotificationOn;

  @ColumnInfo
  public long probationEndDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getDateOfJoining() {
    return dateOfJoining;
  }

  public void setDateOfJoining(long dateOfJoining) {
    this.dateOfJoining = dateOfJoining;
  }

  public String getTemperatureUnit() {
    return temperatureUnit;
  }

  public void setTemperatureUnit(String temperatureUnit) {
    this.temperatureUnit = temperatureUnit;
  }

  public String getHobbies() {
    return hobbies;
  }

  public void setHobbies(String hobbies) {
    this.hobbies = hobbies;
  }

  public boolean isSoundOn() {
    return isSoundOn;
  }

  public void setSoundOn(boolean soundOn) {
    isSoundOn = soundOn;
  }

  public boolean isNotificationOn() {
    return isNotificationOn;
  }

  public void setNotificationOn(boolean notificationOn) {
    isNotificationOn = notificationOn;
  }

  public long getProbationEndDate() {
    return probationEndDate;
  }

  public void setProbationEndDate(long probationEndDate) {
    this.probationEndDate = probationEndDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
