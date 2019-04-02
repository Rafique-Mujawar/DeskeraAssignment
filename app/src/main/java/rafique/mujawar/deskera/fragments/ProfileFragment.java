package rafique.mujawar.deskera.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.UserAccount;
import rafique.mujawar.deskera.utils.ActivityNavigator;
import rafique.mujawar.deskera.utils.DeskeraConstants;
import rafique.mujawar.deskera.utils.DeskeraUtils;

public class ProfileFragment extends Fragment implements View.OnClickListener,
    TextView.OnEditorActionListener, DatePickerDialog.OnDateSetListener {

  private static final String TAG = ProfileFragment.class.getName();

  public ProfileFragment() {
  }

  private ImageView mIvProfileImage;
  private EditText mEtUserName, mEtEmail, mEtHobby, mEtDOJ;
  private Uri mCameraImageUti;
  private UserAccount mUserAccount;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    initViews(view);
    setListeners();
    fetchData();
    return view;
  }

  private void initViews(View view) {
    mIvProfileImage = view.findViewById(R.id.iv_profile);
    mEtUserName = view.findViewById(R.id.et_user);
    mEtEmail = view.findViewById(R.id.et_email);
    mEtHobby = view.findViewById(R.id.et_hobby);
    mEtDOJ = view.findViewById(R.id.et_doj);
  }

  private void setListeners() {
    mIvProfileImage.setOnClickListener(this);
    mEtUserName.setOnEditorActionListener(this);
    mEtEmail.setOnEditorActionListener(this);
    mEtDOJ.setOnClickListener(this);
    mEtHobby.setOnEditorActionListener(this);
  }

  private void fetchData() {
    mUserAccount = DatabaseManager.getDatabase().getUserAccountDao().getUserAccount(0);
    if (null != mUserAccount) {
      mEtUserName.setText(mUserAccount.getName());
      mEtEmail.setText(mUserAccount.getEmail());
      mEtHobby.setText(mUserAccount.getHobbies());
      if (0 != mUserAccount.getDateOfJoining()) {
        mEtDOJ.setText(DeskeraUtils.getDateFromMillis(mUserAccount.getDateOfJoining()));
      }
      if (!TextUtils.isEmpty(mUserAccount.getImageUri())) {
        setProfileImage(Uri.parse(mUserAccount.getImageUri()));
      }
    }
  }

  private void setProfileImage(Uri uri) {
    Glide.with(getContext()).
        load(uri).apply(new RequestOptions()
        .transform(new CircleCrop())).into(mIvProfileImage);
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_profile:
        selectImageDialog();
        break;
      case R.id.et_doj:
        DatePickerFragment datePickerFragment =
            DatePickerFragment.getInstance(this, mUserAccount
                .getProbationEndDate());
        datePickerFragment.show(getActivity().getSupportFragmentManager(), DatePickerFragment
            .TAG);
        break;

    }
  }

  /**
   * Called when an action is being performed.
   *
   * @param v        The view that was clicked.
   * @param actionId Identifier of the action.  This will be either the
   *                 identifier you supplied, or {@link EditorInfo#IME_NULL
   *                 EditorInfo.IME_NULL} if being called due to the enter key
   *                 being pressed.
   * @param event    If triggered by an enter key, this is the event;
   *                 otherwise, this is null.
   * @return Return true if you have consumed the action, else false.
   */
  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {

      switch (v.getId()) {
        case R.id.et_user:
          mUserAccount.setName(v.getText().toString().trim());
          updateAccountDetails();
          break;

        case R.id.et_email:
          //TODO: verify email
          if (Patterns.EMAIL_ADDRESS.matcher(v.getText().toString().trim()).matches()) {
            mUserAccount.setEmail(v.getText().toString().trim());
            updateAccountDetails();
            v.clearFocus();
          } else {
            mEtEmail.setError("Invalid email format.");
          }
          break;

        case R.id.et_hobby:
          mUserAccount.setHobbies(v.getText().toString().trim());
          updateAccountDetails();
          v.clearFocus();
          break;
      }
      return true;
    }
    return false;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    // Do something with the date chosen by the user
    Log.i(TAG, "onDateSet: ");
    //TODO: check for probation data
    final Calendar c = Calendar.getInstance();
    c.set(year, month, day);
    long newDate = c.getTimeInMillis();
    mUserAccount.setDateOfJoining(newDate);
    mEtDOJ.setText(DeskeraUtils.getDateFromMillis(newDate));
    updateAccountDetails();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (Activity.RESULT_OK == resultCode) {
      switch (requestCode) {
        case DeskeraConstants.RequestCodes.REQUEST_CAMERA:
          if (null != mCameraImageUti) {
            setProfileImage(mCameraImageUti);
            mUserAccount.setImageUri(mCameraImageUti.toString());
            updateAccountDetails();
          }
          break;
        case DeskeraConstants.RequestCodes.REQUEST_GALLERY:
          if (null != data && null != data.getData()) {
            mUserAccount.setImageUri(data.getData().toString());
            setProfileImage(data.getData());
            updateAccountDetails();
          }
          break;
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case DeskeraConstants.RequestCodes.PERMISSIONS_REQUEST_WRITE_STORAGE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // permission was granted, yay! Do the
          selectImageDialog();
        } else {
          // permission denied, boo! Disable the
          Toast.makeText(getContext(), R.string.info_storage_permission_denied, Toast.LENGTH_SHORT)
              .show();
        }
        break;
    }
  }

  /**
   * Open Chooser to select Camera,Gallery
   */
  private void selectImageDialog() {
    if (ContextCompat.checkSelfPermission(getContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
          Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.info_storage_permission);
        builder.setTitle(R.string.title_permission_required);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                DeskeraConstants.RequestCodes.PERMISSIONS_REQUEST_WRITE_STORAGE);
          }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getContext(), R.string.info_storage_permission_denied,
                Toast.LENGTH_SHORT).show();
          }
        });
        builder.create().show();
      } else {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            },
            DeskeraConstants.RequestCodes.PERMISSIONS_REQUEST_WRITE_STORAGE);
      }
    } else {
      openChooser();
    }
  }

  private void openChooser() {
    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
          case R.id.tv_camera:
            bottomSheetDialog.dismiss();
            mCameraImageUti =
                DeskeraUtils.generateTimeStampPhotoFileUri(getContext().getApplicationContext());
            ActivityNavigator.openCameraIntent(ProfileFragment.this, mCameraImageUti);
            break;
          case R.id.tv_gallery:
            bottomSheetDialog.dismiss();
            ActivityNavigator.openGalleryIntent(ProfileFragment.this);
            break;
          case R.id.closeBtn:
            bottomSheetDialog.dismiss();
            break;
        }
      }
    };

    bottomSheetDialog.setContentView(R.layout.bottom_dialog_view);
    bottomSheetDialog.show();
    bottomSheetDialog.findViewById(R.id.tv_camera).setOnClickListener(listener);
    bottomSheetDialog.findViewById(R.id.tv_gallery).setOnClickListener(listener);
    bottomSheetDialog.findViewById(R.id.closeBtn).setOnClickListener(listener);
  }

  private void updateAccountDetails() {
    DatabaseManager.getDatabase().getUserAccountDao().updateUserAccount(mUserAccount);
  }
}
