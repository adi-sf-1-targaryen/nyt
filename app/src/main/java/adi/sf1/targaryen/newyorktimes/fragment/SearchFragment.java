package adi.sf1.targaryen.newyorktimes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adi.sf1.targaryen.newyorktimes.R;

/**
 * Created by emiliaaxen on 16-04-21.
 */


public class SearchFragment extends ArticleFeedFragment {

   RecyclerView recyclerViewSearch;

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
    super.setFeedList(cache);
  }
}
