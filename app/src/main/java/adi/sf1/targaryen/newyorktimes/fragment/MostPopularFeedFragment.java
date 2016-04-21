package adi.sf1.targaryen.newyorktimes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.CheckInternetConnection;
import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.MostPopular;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import retrofit2.Response;


public class MostPopularFeedFragment extends ArticleFeedFragment {
  private static final String TAG = "MostPopularFeedFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: 4/19/16 DO STUFF HERE MAYBE (MAYBE NOT)
  }

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
