package adi.sf1.targaryen.newyorktimes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.model.ArticleFeed;
import adi.sf1.targaryen.newyorktimes.recyclerAdapter.ArticleFeedAdapter;

/**
 * Created by Raiders on 4/18/16.
 */
public class ArticleFeedFragment extends Fragment{

  private List<ArticleFeed> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  private ArticleFeedAdapter articleFeedAdapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_feed, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    return view;
  }
  private void setArticleFeedAdapter() {
    articleFeedAdapter = new ArticleFeedAdapter(feedList);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(articleFeedAdapter);

  }

  private void setFeedList() {
    //Where the api calls happen
  }
}
