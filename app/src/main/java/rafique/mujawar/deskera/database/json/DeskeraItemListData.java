package rafique.mujawar.deskera.database.json;

import android.os.Parcel;

import java.util.List;

import rafique.mujawar.deskera.database.entities.DeskeraItem;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
public class DeskeraItemListData {
  private List<DeskeraItem> items;

  public DeskeraItemListData() {
  }

  protected DeskeraItemListData(Parcel in) {
    items = in.createTypedArrayList(DeskeraItem.CREATOR);
  }

  public List<DeskeraItem> getItems() {
    return items;
  }
}
