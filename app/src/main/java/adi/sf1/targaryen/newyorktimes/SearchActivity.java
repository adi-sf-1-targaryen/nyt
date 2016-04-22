package adi.sf1.targaryen.newyorktimes;

import android.app.SearchManager;
import android.content.Intent;
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

    handleIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {

    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);

      SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search);

      fragment.performSearch(query);
    }
  }

}
