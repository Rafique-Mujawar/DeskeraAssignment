package rafique.mujawar.deskera.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.listeners.DeskeraItemListener;
import rafique.mujawar.deskera.viewholders.ItemViewHolder;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

  private DeskeraItemListener mListener;
  private List<DeskeraItem> items;
  private Context context;

  public ItemAdapter(Context context, DeskeraItemListener listener) {
    this.context = context;
    this.mListener = listener;
  }

  public void setItems(List<DeskeraItem> items) {
    this.items = items;
  }

  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = ViewGroup.inflate(viewGroup.getContext(), R.layout.item_category, null);
    return new ItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
    final DeskeraItem item = items.get(position);
    holder.getTvName().setText(item.getTitle());
    holder.getTvDescription().setText(item.getDescription());
    holder.getTvCategory().setText(item.getCategory());
    holder.getIvIsFavourite().setImageResource(item.isFavourite() ? R.drawable
        .ic_is_favourite : R.drawable.ic_no_favorite);
    Glide.with(context).
        load(item.getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_local_florist)
        .transform(new CircleCrop())).into(holder.getIvItemImage());
    holder.getIvIsFavourite().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (item.isFavourite) {
          item.setFavourite(false);
          mListener.onDeskeraItemUnLiked(item);
        } else {
          item.setFavourite(true);
          mListener.onDeskeraItemLiked(item);
        }
        notifyItemChanged(holder.getAdapterPosition());
      }
    });
  }

  @Override
  public int getItemCount() {
    return null == items ? 0 : items.size();
  }
}
