package adi.sf1.targaryen.newyorktimes.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.ArticleActivity;
import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.Story;
import adi.sf1.targaryen.newyorktimes.api.TopStories;
import adi.sf1.targaryen.newyorktimes.recyclerAdapter.ArticleFeedAdapter;
import retrofit2.Response;

/**
 * Created by Raiders on 4/18/16.
 */
public class ArticleFeedFragment extends Fragment implements ArticleFeedAdapter.OnItemClickListener{

  protected Context context;
//  private List<ArticleFeed> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  protected ArticleFeedAdapter articleFeedAdapter;
  public static final String EXTRA_SECTION = "section";
  public static final String URL_EXTRA_KEY = "urlExtraKey";
  private TopStories.Section section = TopStories.Section.HOME;
  private SwipeRefreshLayout swipeContainer;

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
    swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
    context = getContext();
    setArticleFeedAdapter();
    setFeedList();
//    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//      @Override
//      public void onRefresh() {
//        // Your code to refresh the list here.
//        // Make sure you call swipeContainer.setRefreshing(false)
//        // once the network request has completed successfully.
//        fetchTimelineAsync(0);
//      }
//    });
//    // Configure the refreshing colors
//    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//      android.R.color.holo_green_light,
//      android.R.color.holo_orange_light,
//      android.R.color.holo_red_light);

    return view;
  }
  private void setArticleFeedAdapter() {
    articleFeedAdapter = new ArticleFeedAdapter(this);
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

  @Override
  public void onItemClick(Story story) {
    Intent articleActivityIntent = new Intent(context, ArticleActivity.class);
    articleActivityIntent.putExtra(URL_EXTRA_KEY, story.getUrl());
    startActivity(articleActivityIntent);
  }

//  public void fetchTimelineAsync(int page) {
//    // Send the network request to fetch the updated data
//    // `client` here is an instance of Android Async HTTP
//    client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//      public void onSuccess(JSONArray json) {
//        // Remember to CLEAR OUT old items before appending in the new ones
//        adapter.clear();
//        // ...the data has come back, add new items to your adapter...
//        adapter.addAll(...);
//        // Now we call setRefreshing(false) to signal refresh has finished
//        swipeContainer.setRefreshing(false);
//      }
//
//      public void onFailure(Throwable e) {
//        Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//      }
//    });
//  }

}
