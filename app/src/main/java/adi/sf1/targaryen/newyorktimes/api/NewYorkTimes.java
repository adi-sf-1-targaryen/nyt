package adi.sf1.targaryen.newyorktimes.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    return service.getTopStores(APIKeys.NYT_TOP_STORIES, section.getValue());
  }

  private interface NewYorkTimesAPI {
    @GET("topstories/v1/{section}.json?api-key={APIKey}")
    Call<TopStories> getTopStores(@Path("APIKey") String APIKey, @Path("section") String section);
  }
}

