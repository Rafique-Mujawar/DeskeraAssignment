package rafique.mujawar.deskera.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rafique.mujawar.deskera.R;

public class ItemsFragment extends Fragment {

  private ACategoryItemFragment mACategoryItemFragment;
  private BCategoryItemFragment mBCategoryItemFragment;
  private AllItemsFragment mAllItemsFragment;

  public ItemsFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mACategoryItemFragment = new ACategoryItemFragment();
    mBCategoryItemFragment = new BCategoryItemFragment();
    mAllItemsFragment = new AllItemsFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_items, container, false);
    ItemPagerAdapter itemPagerAdapter = new ItemPagerAdapter(getActivity()
        .getSupportFragmentManager());

    TabLayout tabLayout = view.findViewById(R.id.item_tabs);
    ViewPager viewPager = view.findViewById(R.id.item_container);
    viewPager.setAdapter(itemPagerAdapter);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    return view;
  }

  class ItemPagerAdapter extends FragmentPagerAdapter {

    public ItemPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int i) {
      switch (i) {
        case 0:
          return mAllItemsFragment;
        case 1:
          return mACategoryItemFragment;
        case 2:
          return mBCategoryItemFragment;
      }
      return null;
    }

    @Override
    public int getCount() {
      return 3;
    }
  }
}
