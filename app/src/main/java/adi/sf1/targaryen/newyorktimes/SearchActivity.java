package adi.sf1.targaryen.newyorktimes;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {


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

    /*android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    SearchFragment searchFragment = (SearchFragment) fragmentManager.findFragmentById(R.id.fragment_search);


    //FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction()

*/
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
