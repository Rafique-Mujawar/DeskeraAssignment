package rafique.mujawar.deskera.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rafique.mujawar.deskera.R;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
  private ImageView ivItemImage, ivIsFavourite;
  private TextView tvName, tvDescription, tvCategory;

  public ItemViewHolder(@NonNull View itemView) {
    super(itemView);
    ivIsFavourite = itemView.findViewById(R.id.is_favourite_image);
    ivItemImage = itemView.findViewById(R.id.item_image);
    tvName = itemView.findViewById(R.id.item_name);
    tvDescription = itemView.findViewById(R.id.item_description);
    tvCategory = itemView.findViewById(R.id.item_category);
  }

  public ImageView getIvItemImage() {
    return ivItemImage;
  }

  public ImageView getIvIsFavourite() {
    return ivIsFavourite;
  }

  public TextView getTvName() {
    return tvName;
  }

  public TextView getTvDescription() {
    return tvDescription;
  }

  public TextView getTvCategory() {
    return tvCategory;
  }
}
