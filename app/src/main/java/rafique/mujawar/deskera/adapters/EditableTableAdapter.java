package rafique.mujawar.deskera.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.listeners.AddCheckBoxCheckdListener;
import rafique.mujawar.deskera.viewholders.TableViewHolder;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class EditableTableAdapter extends RecyclerView.Adapter<TableViewHolder> {
  private List<TabletTabItem> tableDataList;
  private List<TabletTabItem> mDeleteDataList;
  private AddCheckBoxCheckdListener checkdListener;

  public EditableTableAdapter(AddCheckBoxCheckdListener checkdListener) {
    this.checkdListener = checkdListener;
  }

  public void setTableDataList(List<TabletTabItem> mtableDataList) {
    this.tableDataList = mtableDataList;
    this.mDeleteDataList = new ArrayList<>();
    notifyDataSetChanged();
  }

  @Override
  @NonNull
  public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = ViewGroup.inflate(parent.getContext(), R.layout.holder_table_item, null);
    return new TableViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final TableViewHolder holder, int position) {
    holder.getTitleTextView().setText(tableDataList.get(position).getName());
    holder.getCheckBox().setVisibility(View.VISIBLE);
    holder.getCheckBox().setChecked(mDeleteDataList.contains(tableDataList.get(position)));
    holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.isPressed()) {
          if (isChecked) {
            mDeleteDataList.add(tableDataList.get(holder.getAdapterPosition()));
          } else {
            mDeleteDataList.remove(tableDataList.get(holder.getAdapterPosition()));
          }
          checkdListener.checkBoxChanged(isChecked, mDeleteDataList.size());
        }
      }
    });

  }

  @Override
  public int getItemCount() {
    return null != tableDataList ? tableDataList.size() : 0;
  }

  public List<TabletTabItem> getDeleteItemList() {
    return mDeleteDataList;
  }
}
