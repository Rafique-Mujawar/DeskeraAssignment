package rafique.mujawar.deskera.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Entity
public class TabletTabItem {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo
  private String name;

  public TabletTabItem() {
  }

  public TabletTabItem(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
