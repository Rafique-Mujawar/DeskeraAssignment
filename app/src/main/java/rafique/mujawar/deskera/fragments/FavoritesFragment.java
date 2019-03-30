package rafique.mujawar.deskera.fragments;

import com.squareup.otto.Subscribe;

import java.util.List;

import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.eventbus.FavouriteListChangedEvent;


public class FavoritesFragment extends BaseItemFragment {

  public FavoritesFragment() {
  }

  @Override
  public void onViewCreateCompleted() {
    loadData();
  }

  @Subscribe
  public void onSubscribeFavouriteChanged(FavouriteListChangedEvent event) {
    loadData();
  }

  private void loadData() {
    List<DeskeraItem> items = DatabaseManager.getDatabase().getDeskeraItemDao()
        .getFavourites();
    updateAdapter(items);
  }
}
