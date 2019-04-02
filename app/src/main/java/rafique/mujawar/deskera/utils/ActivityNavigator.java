package rafique.mujawar.deskera.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import rafique.mujawar.deskera.activities.MainActivity;
import rafique.mujawar.deskera.activities.SelectTemperatureUnitActivity;
import rafique.mujawar.deskera.activities.SettingDetailsActivity;
import rafique.mujawar.deskera.activities.TableEditActivity;
import rafique.mujawar.deskera.activities.TableItemDetailsActivity;
import rafique.mujawar.deskera.database.entities.TabletTabItem;

/**
 * @author Rafique Mujawar
 * Date 29-03-2019
 */
public class ActivityNavigator {
  public static void launchMainActivity(Context context) {
    context.startActivity(new Intent(context, MainActivity.class));
  }

  public static void launchTemperatureUnitActivity(Fragment fragment, int requestCode) {
    fragment.startActivityForResult(new Intent(fragment.getContext(), SelectTemperatureUnitActivity
        .class), requestCode);
  }

  public static void launchSettingDetailsActivity(Context context) {
    context.startActivity(new Intent(context, SettingDetailsActivity.class));
  }

  public static void launchEditTableActivity(Context context) {
    context.startActivity(new Intent(context, TableEditActivity.class));
  }

  public static void launchItemDetailsActivity(Context context, TabletTabItem item) {
    Intent intent = new Intent(context, TableItemDetailsActivity.class);
    intent.putExtra(DeskeraConstants.ARG_TABLE_ITEM, item);
    context.startActivity(intent);
  }

  public static void openCameraIntent(Fragment fragment, Uri profileImageUri) {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageUri);
    fragment.startActivityForResult(intent, DeskeraConstants.RequestCodes.REQUEST_CAMERA);
  }

  public static void openGalleryIntent(Fragment fragment) {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        DeskeraConstants.IMAGE_TYPE);
    if (null != intent.resolveActivity(fragment.getContext().getPackageManager())) {
      fragment.startActivityForResult(intent, DeskeraConstants.RequestCodes.REQUEST_GALLERY);
    } else {
      Toast.makeText(fragment.getContext(), "No Intent available to handle action", Toast
          .LENGTH_SHORT).show();
    }
  }
}
