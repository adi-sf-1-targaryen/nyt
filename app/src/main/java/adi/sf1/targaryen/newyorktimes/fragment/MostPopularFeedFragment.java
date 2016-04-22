package adi.sf1.targaryen.newyorktimes.fragment;

import android.util.Log;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.CheckInternetConnection;
import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.result.MostPopular;
import retrofit2.Response;

/**
 * This fragment shows the most popular articles which are grabbed from the New York Times most popular API
 */
public class MostPopularFeedFragment extends ArticleFeedFragment {
  private static final String TAG = "MostPopularFeedFragment";

  /**
   * Makes the api call for the the most popular stories on the NY times
   */
  @Override
  protected void setFeedList(boolean cache) {
    if (CheckInternetConnection.isOnline(this.context)) {
      NewYorkTimes.getInstance().getMostPopular(MostPopular.Type.EMAILED, MostPopular.Section.ALL, MostPopular.Time.DAY)
        .enqueue(new Callback<MostPopular>() {
          @Override
          public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
            articleFeedAdapter.changeDataSet(response.body().getResults());
            swipeContainer.setRefreshing(false);
          }

          @Override
          public void onFailure(Call<MostPopular> call, Throwable t) {
            Toast.makeText(context, "Could not retrieve Most Popular Stories", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "onFailure: ", t);
          }
        }, cache);
    } else {
      Toast.makeText(context, "Sorry, No Internet Connection", Toast.LENGTH_SHORT).show();
    }
  }
}
