package adi.sf1.targaryen.newyorktimes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import adi.sf1.targaryen.newyorktimes.api.result.TopStories;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;
import adi.sf1.targaryen.newyorktimes.fragment.MostPopularFeedFragment;

/**
 * The Main Activity is the first activity that is launched when the user opens the app.
 * Within the main activity is the tool bar and the tab layout for the different sections.
 * The activity also contains a fragment that populates the pager adapter with a feed of articles
 * depending on what section the user is in
 */
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {


  SectionsPagerAdapter mSectionsPagerAdapter;
  ViewPager mViewPager;
  private SlidingTabLayout mSlidingTabLayout;

  public final static String  SEARCH_KEY = "searchKey";
  public final static String MY_PREF_KEY = "myPrefKey";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /**
     * Defining the Toolbar. The toolbar implements the swipe tab view
     */
    Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(topToolBar);
    topToolBar.setLogo(R.drawable.nyt_logo);
    topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
    mSlidingTabLayout.setViewPager(mViewPager);

  }

  /**
   * Creates the menu bar
   * Creates functionality for the search feature
   * @param menu
   * @return
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);

    // Associate searchable configuration with the SearchView
    SearchManager searchManager =
      (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    android.support.v7.widget.SearchView searchView =
      ( android.support.v7.widget.SearchView) menu.findItem(R.id.search_articles).getActionView();
    searchView.setSearchableInfo(
      searchManager.getSearchableInfo(getComponentName()));

    return true;
  }

  /**
   * Method that creates the actions for when a user selects an option in the menu bar
   * @param item
   * @return
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.

    switch (item.getItemId()) {
      case R.id.preferences:
        Intent preferencesIntent = new Intent(MainActivity.this, NotificationPreferencesActivity.class);
        startActivity(preferencesIntent);
        return true;
      case R.id.search_articles:
        Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(searchIntent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }

  }

  /**
   * After implementing ActionBar.TabListener we need to override these three methods
   * @param tab
   * @param ft
   */
  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
    mViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
  }

  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
      super(fm);
    }


    @Override
    public Fragment getItem(int position) {
      Bundle bundle = new Bundle();
      TopStories.Section section = TopStories.Section.HOME;

      Fragment fragment = null;
      switch (position) {
        case 0:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.HOME;
          break;
        case 1:
          fragment = new MostPopularFeedFragment();
          break;
        case 2:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.OPINION;
          break;
        case 3:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.WORLD;
          break;
        case 4:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.NATIONAL;
          break;
        case 5:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.BUSINESS;
          break;
        case 6:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.SPORTS;
          break;
        case 7:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.ARTS;
          break;
        case 8:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.NYREGION;
          break;
        case 9:
          fragment = new ArticleFeedFragment();
          section = TopStories.Section.MAGAZINE;
          break;
      }
      bundle.putString(ArticleFeedFragment.EXTRA_SECTION, section.name());
      fragment.setArguments(bundle);
      return fragment;
    }

    @Override
    public int getCount() {
      return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      Locale l = Locale.getDefault();
      switch (position) {
        case 0:
          return getString(R.string.top_stories).toUpperCase(l);
        case 1:
          return getString(R.string.most_popular).toUpperCase(l);
        case 2:
          return getString(R.string.opinion).toUpperCase(l);
        case 3:
          return getString(R.string.world).toUpperCase(l);
        case 4:
          return getString(R.string.us).toUpperCase(l);
        case 5:
          return getString(R.string.business_day).toUpperCase(l);
        case 6:
          return getString(R.string.sports).toUpperCase(l);
        case 7:
          return getString(R.string.arts).toUpperCase(l);
        case 8:
          return getString(R.string.new_york).toUpperCase(l);
        case 9:
          return getString(R.string.magazine).toUpperCase(l);
      }
      return null;
    }
  }


}
  

