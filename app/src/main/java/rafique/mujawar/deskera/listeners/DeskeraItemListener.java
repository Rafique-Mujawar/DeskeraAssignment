package rafique.mujawar.deskera.listeners;

import rafique.mujawar.deskera.database.entities.DeskeraItem;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public interface DeskeraItemListener {
  void onDeskeraItemLiked(DeskeraItem item);
  void onDeskeraItemUnLiked(DeskeraItem item);
}
