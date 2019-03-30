package rafique.mujawar.deskera.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.database.json.DeskeraItemListData;
import rafique.mujawar.deskera.database.json.FruitsData;
import rafique.mujawar.deskera.fragments.FavoritesFragment;
import rafique.mujawar.deskera.fragments.ItemsFragment;
import rafique.mujawar.deskera.fragments.ProfileFragment;
import rafique.mujawar.deskera.fragments.SettingsFragment;
import rafique.mujawar.deskera.fragments.TableFragment;
import rafique.mujawar.deskera.utils.DeskeraUtils;

public class MainActivity extends AppCompatActivity {

  private Fragment mProfileFragment;
  private Fragment mSettingFragment;
  private Fragment mItemsFragment;
  private Fragment mTableFragment;
  private Fragment mFavoritesFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loadJsonToDatabase();

    MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

    TabLayout tabLayout = findViewById(R.id.tabs);
    ViewPager viewPager = findViewById(R.id.container);
    viewPager.setAdapter(mainPagerAdapter);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    mSettingFragment = new SettingsFragment();
    mItemsFragment = new ItemsFragment();
    mTableFragment = new TableFragment();
    mFavoritesFragment = new FavoritesFragment();
    mProfileFragment = new ProfileFragment();
  }

  private void loadJsonToDatabase() {
    List<DeskeraItem> items = DatabaseManager.getInstance().getDatabase().getDeskeraItemDao()
        .getAll();
    if (null == items || items.isEmpty()) {
      Gson gson = new Gson();
      String json = DeskeraUtils.loadJSONFromAsset(this, "sample_item.json");
      DeskeraItemListData data = gson.fromJson(json, DeskeraItemListData.class);
      json = DeskeraUtils.loadJSONFromAsset(this, "fruits.json");
      FruitsData fruitsData = gson.fromJson(json, FruitsData.class);
      DatabaseManager.getInstance()
          .getDatabase()
          .getDeskeraItemDao()
          .insertAllItems(data.getItems());
      DatabaseManager.getInstance().getDatabase().getTabletTabItemDao().insertMultiples(fruitsData
          .getFruits());
    }
  }

  public class MainPagerAdapter extends FragmentPagerAdapter {

    MainPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      if (position == 0) {
        return mProfileFragment;
      } else if (position == 1) {
        return mItemsFragment;
      } else if (position == 2) {
        return mFavoritesFragment;
      } else if (position == 3) {
        return mTableFragment;
      } else if (position == 4) {
        return mSettingFragment;
      }
      return null;
    }

    @Override
    public int getCount() {
      return 5;
    }
  }
}
