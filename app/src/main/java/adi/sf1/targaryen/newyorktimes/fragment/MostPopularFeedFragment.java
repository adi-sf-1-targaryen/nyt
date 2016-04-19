package adi.sf1.targaryen.newyorktimes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.api.MostPopular;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nicolassaad on 4/19/16.
 */
public class MostPopularFeedFragment extends ArticleFeedFragment {


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: 4/19/16 DO STUFF HERE MAYBE (MAYBE NOT)
  }

  @Override
  protected void setFeedList() {
    NewYorkTimes.getInstance().getMostPopular(MostPopular.Type.SHARED, MostPopular.Section.ALL, MostPopular.Time.DAY).enqueue(new Callback<MostPopular>() {
      @Override
      public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
        articleFeedAdapter.changeDataSet(response.body().getResults());
      }

      @Override
      public void onFailure(Call<MostPopular> call, Throwable t) {
        Toast.makeText(context, "Could not retrieve Most Popular Stories", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
