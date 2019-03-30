package rafique.mujawar.deskera.eventbus;

/**
 * @author Rafique Mujawar
 * Date 30-03-2019
 */
public class FavouriteListChangedEvent {
  private final String TAG;

  public FavouriteListChangedEvent(String TAG) {
    this.TAG = TAG;
  }

  public String getTAG() {
    return TAG;
  }
}
