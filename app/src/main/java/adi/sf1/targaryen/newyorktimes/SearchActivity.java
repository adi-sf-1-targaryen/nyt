package adi.sf1.targaryen.newyorktimes;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import adi.sf1.targaryen.newyorktimes.fragment.SearchFragment;

/**
 * This activity holds the fragment that will show the result of the users search query
 */
public class SearchActivity extends AppCompatActivity {

  /**
   * Gets the user search query from shared preferences
   * Sends the search query to fragment containing the search feel
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_PREF_KEY,0);
    String search = sharedPreferences.getString(MainActivity.SEARCH_KEY, "");

    Bundle bundle = new Bundle();
    bundle.putString(MainActivity.SEARCH_KEY,search);
    SearchFragment searchFragment = new SearchFragment();
    searchFragment.setArguments(bundle);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {

    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
    }
  }

}
