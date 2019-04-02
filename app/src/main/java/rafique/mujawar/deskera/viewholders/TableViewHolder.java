package rafique.mujawar.deskera.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import rafique.mujawar.deskera.R;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class TableViewHolder extends RecyclerView.ViewHolder {
  private TextView titleTextView;
  private CheckBox checkBox;

  public TableViewHolder(View itemView) {
    super(itemView);
    titleTextView = itemView.findViewById(R.id.tvName);
    checkBox = itemView.findViewById(R.id.chkBox);
  }

  public TextView getTitleTextView() {
    return titleTextView;
  }

  public CheckBox getCheckBox() {
    return checkBox;
  }
}
