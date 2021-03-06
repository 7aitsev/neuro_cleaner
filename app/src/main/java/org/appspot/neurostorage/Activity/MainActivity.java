package org.appspot.neurostorage.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import org.appspot.neurostorage.R;
import org.appspot.neurostorage.Servise.MemoryOfficer;
import org.appspot.neurostorage.Fragment.TabControl;
import org.appspot.neurostorage.Fragment.TabExclusions;

public class MainActivity extends AppCompatActivity {
  // singleton
  private static MainActivity instance = null;
  public static MainActivity getInstance(){
    return instance;
  }

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  private FloatingActionButton fab;
  private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
      switch(position) {
        case 0 :
          fab.show();
          break;
        default : fab.hide();
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    instance = this;

    //    startService(new Intent(this, MemoryOfficer.class));

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);
    mViewPager.addOnPageChangeListener(pageChangeListener);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);

    fab = (FloatingActionButton) findViewById(R.id.fab_main);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        switch(mViewPager.getCurrentItem()) {
//          // If we are in a first tab {@link TabControl}, let users add new items to control
//          case 0 :
//            // TODO addNewRecord() to let users to control a chosen directory
//          break;
//        }
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if(id == R.id.action_settings) {
      // todo open an activity with a settings
      return true;
    } else if(id == R.id.action_about) {
      // todo open an activity with a description
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
   * one of the sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      // getItem is called to instantiate the fragment for the given page.
      switch(position) {
        case 0:
          TabControl tabControl = new TabControl();
          return tabControl;
        case 1:
          TabExclusions tabExclusions = new TabExclusions();
          return tabExclusions;
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      // Show 2 total pages.
      return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      switch(position) {
        case 0:
          return getString(R.string.tab_control);
        case 1:
          return getString(R.string.tab_exclusions);
      }
      return null;
    }
  }
}