package adi.sf1.targaryen.newyorktimes;

import org.junit.Assert;
import org.junit.Test;

import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.result.ArticleSearch;
import adi.sf1.targaryen.newyorktimes.api.result.MostPopular;
import adi.sf1.targaryen.newyorktimes.api.result.TopStories;
import retrofit2.Response;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
  @Test
  public void testTopStoryApi() {
    NewYorkTimes.getInstance().getTopStories(TopStories.Section.HOME).enqueue(new Callback<TopStories>() {
      @Override
      public void onResponse(Call<TopStories> call, Response<TopStories> response) {
        String title = response.body().getResults()[0].getTitle();
        Assert.assertNotNull(title);
      }

      @Override
      public void onFailure(Call<TopStories> call, Throwable t) {

      }
    });
  }

  @Test
  public void testMostPopularApi() {
    NewYorkTimes.getInstance().getMostPopular(MostPopular.Type.EMAILED, MostPopular.Section.ALL, MostPopular.Time.DAY).enqueue(new Callback<MostPopular>() {
      @Override
      public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
        String title = response.body().getResults()[0].getTitle();
        Assert.assertNotNull(title);
      }

      @Override
      public void onFailure(Call<MostPopular> call, Throwable t) {

      }
    });
  }

  @Test
  public void testSearchApi(){
    NewYorkTimes.getInstance().articleSearch("taco").enqueue(new Callback<ArticleSearch>() {
      @Override
      public void onResponse(Call<ArticleSearch> call, Response<ArticleSearch> response) {
        String title = response.body().getResults()[0].getTitle();
        Assert.assertNotNull(title);
      }

      @Override
      public void onFailure(Call<ArticleSearch> call, Throwable t) {

      }
    });
  }

  
}
