package adi.sf1.targaryen.newyorktimes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.TopStories;
import adi.sf1.targaryen.newyorktimes.recyclerAdapter.ArticleFeedAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raiders on 4/18/16.
 */
public class ArticleFeedFragment extends Fragment{

  protected Context context;
//  private List<ArticleFeed> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  protected ArticleFeedAdapter articleFeedAdapter;
  public static final String EXTRA_SECTION = "section";
  private TopStories.Section section = TopStories.Section.HOME;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      String section = bundle.getString(EXTRA_SECTION);
      if (section != null) {
        this.section = TopStories.Section.valueOf(section);
      }
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_feed, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    context = getContext();
    setArticleFeedAdapter();
    setFeedList();
    return view;
  }
  private void setArticleFeedAdapter() {
    articleFeedAdapter = new ArticleFeedAdapter(/*feedList*/);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(articleFeedAdapter);

  }

  protected void setFeedList() {
    NewYorkTimes.getInstance().getTopStories(section).enqueue(new Callback<TopStories>() {
      @Override
      public void onResponse(Call<TopStories> call, Response<TopStories> response) {
        articleFeedAdapter.changeDataSet(response.body().getResults());
      }

      @Override
      public void onFailure(Call<TopStories> call, Throwable t) {
        Toast.makeText(context, "Could not retrieve Top Stories", Toast.LENGTH_SHORT).show();
      }
    });


  }
}
