package rafique.mujawar.deskera.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.listeners.ITableItemListener;
import rafique.mujawar.deskera.viewholders.TableViewHolder;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class TableItemsAdapter extends RecyclerView.Adapter<TableViewHolder> implements Filterable {
  private List<TabletTabItem> mSearchedTableItems;
  private List<TabletTabItem> mTableItemsAll;
  private ITableItemListener mListener;

  public TableItemsAdapter(ITableItemListener listener) {
    this.mListener = listener;
  }

  public void setTableItemsList(List<TabletTabItem> items) {
    this.mSearchedTableItems = items;
    this.mTableItemsAll = items;
    notifyDataSetChanged();
  }

  @Override
  @NonNull
  public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = ViewGroup.inflate(parent.getContext(), R.layout.holder_table_item, null);
    return new TableViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final TableViewHolder holder, final int position) {
    holder.getTitleTextView().setText(mSearchedTableItems.get(position).getName());
    holder.getCheckBox().setVisibility(View.GONE);
    holder.getCheckBox().setChecked(false);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != mListener) {
          mListener.onTableItemSelect(mSearchedTableItems.get(holder.getAdapterPosition()));
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return null != mSearchedTableItems ? mSearchedTableItems.size() : 0;
  }


  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        String charString = constraint.toString();
        List<TabletTabItem> filteredList = new ArrayList<>();
        if (charString.length() == 0) {
          filteredList = mTableItemsAll;
        } else {
          for (TabletTabItem deskeraItem : mTableItemsAll) {
            if (deskeraItem.getName()
                .toLowerCase()
                .contains(charString.toLowerCase())) {
              filteredList.add(deskeraItem);
            }
          }
        }
        results.values = filteredList;
        return results;
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        mSearchedTableItems = (ArrayList<TabletTabItem>) results.values;
        notifyDataSetChanged();
      }
    };
  }
}
