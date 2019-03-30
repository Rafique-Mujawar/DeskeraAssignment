package rafique.mujawar.deskera.fragments;


import com.squareup.otto.Subscribe;

import java.util.List;

import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.eventbus.FavouriteListChangedEvent;

/**
 * A simple {@link BaseItemFragment} subclass.
 */
public class BCategoryItemFragment extends BaseItemFragment {


  public BCategoryItemFragment() {
    // Required empty public constructor
  }

  @Override
  public void onViewCreateCompleted() {
    loadData();
  }

  @Subscribe
  public void onSubscribeFavuriteChanged(FavouriteListChangedEvent event) {
    if(!event.getTAG().equalsIgnoreCase(TAG)) {
      loadData();
    }
  }

  private void loadData() {
    List<DeskeraItem> items = DatabaseManager.getDatabase().getDeskeraItemDao()
        .getCategoryItems("B");
    updateAdapter(items);
  }
}
