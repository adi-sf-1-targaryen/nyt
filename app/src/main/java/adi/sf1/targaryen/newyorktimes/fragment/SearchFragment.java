package adi.sf1.targaryen.newyorktimes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.MainActivity;
import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.result.ArticleSearch;
import retrofit2.Response;

/**
 * Created by emiliaaxen on 16-04-21.
 */


public class SearchFragment extends ArticleFeedFragment {
  private static final String TAG = "SearchFragment";

  RecyclerView recyclerViewSearch;
  String searchArticleQuery;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      searchArticleQuery = bundle.getString(MainActivity.SEARCH_KEY);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, container, false);
    recyclerViewSearch = (RecyclerView) view.findViewById(R.id.search_recycler_view);
    swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
    context = getContext();
    setArticleFeedAdapter();
    setFeedList();
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        setFeedList(false);
      }
    });
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
      android.R.color.holo_green_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_red_light);
    return view;
  }

  @Override
  protected void setFeedList(boolean cache) {
    NewYorkTimes.getInstance().articleSearch(searchArticleQuery).enqueue(new Callback<ArticleSearch>() {
      @Override
      public void onResponse(Call<ArticleSearch> call, Response<ArticleSearch> response) {
        articleFeedAdapter.changeDataSet(response.body().getResults());
        swipeContainer.setRefreshing(false);
      }

      @Override
      public void onFailure(Call<ArticleSearch> call, Throwable t) {
        Toast.makeText(context, "Could not retrive Search Result", Toast.LENGTH_LONG).show();
        Log.w(TAG, "onFailure: ", t);
      }
    }, cache);
  }

}
