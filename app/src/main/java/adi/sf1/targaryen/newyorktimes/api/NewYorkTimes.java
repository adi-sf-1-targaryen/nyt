package adi.sf1.targaryen.newyorktimes.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by moltendorf on 16/4/15.
 */
public class NewYorkTimes {
  private static final String TAG = "NewYorkTimes";

  private static NewYorkTimes instance;

  public static NewYorkTimes getInstance() {
    if (instance == null) {
      instance = new NewYorkTimes();
    }

    return instance;
  }

  private NewYorkTimesAPI service;

  NewYorkTimes() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://api.nytimes.com/svc/")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    service = retrofit.create(NewYorkTimesAPI.class);
  }

  public Call<TopStories> getTopStories(TopStories.Section section) {
    return service.getTopStores(section.getValue(), APIKeys.NYT_TOP_STORIES);
  }

  private interface NewYorkTimesAPI {
    @GET("topstories/v1/{section}.json")
    Call<TopStories> getTopStores(@Path("section") String section, @Query("api-key") String APIKey);
  }
}

