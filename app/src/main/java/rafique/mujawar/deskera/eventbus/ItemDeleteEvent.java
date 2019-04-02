package rafique.mujawar.deskera.eventbus;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class ItemDeleteEvent {
  private final String TAG;

  public ItemDeleteEvent(String TAG) {
    this.TAG = TAG;
  }

  public String getTAG() {
    return TAG;
  }
}
