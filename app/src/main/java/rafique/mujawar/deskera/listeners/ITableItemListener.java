package rafique.mujawar.deskera.listeners;

import rafique.mujawar.deskera.database.entities.TabletTabItem;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public interface ITableItemListener {
  /**
   * Listener method for Table item click
   *
   * @param item {@link TabletTabItem}
   */
  void onTableItemSelect(TabletTabItem item);
}
