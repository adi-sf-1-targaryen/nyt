package adi.sf1.targaryen.newyorktimes.recyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.model.ArticleFeed;

/**
 * Created by Raiders on 4/18/16.
 */
public class ArticleFeedAdapter extends RecyclerView.Adapter<ArticleFeedAdapter.ArticleFeedViewHolder> {

  private List<ArticleFeed> feedList;

  public class ArticleFeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title, author, date, snippet;

    public ArticleFeedViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image_article);
      title = (TextView) itemView.findViewById(R.id.title_text_article);
      author = (TextView) itemView.findViewById(R.id.author_text_article);
      date = (TextView) itemView.findViewById(R.id.date_text_article);
      snippet = (TextView) itemView.findViewById(R.id.content_text_article);
    }
  }

  public ArticleFeedAdapter(List<ArticleFeed> feedList) {
    this.feedList = feedList;
  }

  @Override
  public ArticleFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_article_feed, parent, false);
    return new ArticleFeedViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ArticleFeedViewHolder holder, int position) {
    ArticleFeed articleFeed = feedList.get(position);
    holder.image.setBackgroundResource(articleFeed.getImage());
    holder.title.setText(articleFeed.getTitle());
    holder.author.setText(articleFeed.getAuthor());
    holder.date.setText(articleFeed.getDate());
    holder.snippet.setText(articleFeed.getSnippet());
  }

  @Override
  public int getItemCount() {
    return feedList.size();
  }
}
