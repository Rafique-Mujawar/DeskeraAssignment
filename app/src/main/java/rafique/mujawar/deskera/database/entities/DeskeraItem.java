package rafique.mujawar.deskera.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
@Entity
public class DeskeraItem implements Parcelable {
  @PrimaryKey(autoGenerate = true)
  public long id;
  @ColumnInfo
  public String imageHref;
  @ColumnInfo
  public String title;
  @ColumnInfo
  public String description;
  @ColumnInfo
  public String category;
  @ColumnInfo
  public boolean isFavourite;

  public DeskeraItem() {
  }

  protected DeskeraItem(Parcel in) {
    imageHref = in.readString();
    title = in.readString();
    description = in.readString();
    category = in.readString();
    isFavourite = in.readByte() != 0;
  }

  public static final Creator<DeskeraItem> CREATOR = new Creator<DeskeraItem>() {
    @Override
    public DeskeraItem createFromParcel(Parcel in) {
      return new DeskeraItem(in);
    }

    @Override
    public DeskeraItem[] newArray(int size) {
      return new DeskeraItem[size];
    }
  };

  public String getImage() {
    return imageHref;
  }

  public void setImage(String image) {
    this.imageHref = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean isFavourite() {
    return isFavourite;
  }

  public void setFavourite(boolean favourite) {
    isFavourite = favourite;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Describe the kinds of special objects contained in this Parcelable
   * instance's marshaled representation. For example, if the object will
   * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
   * the return value of this method must include the
   * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
   *
   * @return a bitmask indicating the set of special object types marshaled
   * by this Parcelable object instance.
   */
  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Flatten this object in to a Parcel.
   *
   * @param dest  The Parcel in which the object should be written.
   * @param flags Additional flags about how the object should be written.
   *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
   */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(imageHref);
    dest.writeString(title);
    dest.writeString(description);
    dest.writeString(category);
    dest.writeByte((byte) (isFavourite ? 1 : 0));
  }
}
