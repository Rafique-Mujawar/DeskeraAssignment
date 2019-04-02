package rafique.mujawar.deskera.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
@Entity
public class TabletTabItem implements Parcelable {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo
  private String name;

  public TabletTabItem() {
  }

  public TabletTabItem(String name) {
    this.name = name;
  }

  protected TabletTabItem(Parcel in) {
    id = in.readLong();
    name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<TabletTabItem> CREATOR = new Creator<TabletTabItem>() {
    @Override
    public TabletTabItem createFromParcel(Parcel in) {
      return new TabletTabItem(in);
    }

    @Override
    public TabletTabItem[] newArray(int size) {
      return new TabletTabItem[size];
    }
  };

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
