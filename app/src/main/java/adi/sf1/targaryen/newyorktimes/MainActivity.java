package adi.sf1.targaryen.newyorktimes;

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

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    static final String LOG_TAG = "SlidingTabsBasicFragment";
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new GeneralFragment();
                    break;
                case 1:
                    fragment = new GeneralFragment();
                    break;
                case 2:
                    fragment = new GeneralFragment();
                    break;
                case 3:
                    fragment = new GeneralFragment();
                    break;
                case 4:
                    fragment = new GeneralFragment();
                    break;
                case 5:
                    fragment = new GeneralFragment();
                    break;
                case 6:
                    fragment = new GeneralFragment();
                    break;
                case 7:
                    fragment = new GeneralFragment();
                    break;
                case 8:
                    fragment = new GeneralFragment();
                    break;
                case 9:
                    fragment = new GeneralFragment();
                    break;
                case 10:
                    fragment = new GeneralFragment();
                    break;
            }
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
                    return "Top Stories".toUpperCase(l);
                case 1:
                    return "Most Popular".toUpperCase(l);
                case 2:
                    return "Opinion".toUpperCase(l);
                case 3:
                    return "World".toUpperCase(l);
                case 4:
                    return "U.S.".toUpperCase(l);
                case 5:
                    return "Business Day".toUpperCase(l);
                case 6:
                    return "Sports".toUpperCase(l);
                case 7:
                    return "Arts".toUpperCase(l);
                case 8:
                    return "New York".toUpperCase(l);
                case 9:
                    return "Magazine".toUpperCase(l);
            }
            return null;
        }
    }

}
