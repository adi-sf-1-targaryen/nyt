package adi.sf1.targaryen.newyorktimes.recyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.api.Story;

/**
 * Created by Raiders on 4/18/16.
 */
public class ArticleFeedAdapter extends RecyclerView.Adapter<ArticleFeedAdapter.ArticleFeedViewHolder> {

  private Story[] feedList = null;


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

  public ArticleFeedAdapter() {

  }

  @Override
  public int getItemViewType(int position) {

    if (position == 0) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public ArticleFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    if (viewType == 0) {
      itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_article_feed, parent, false);
    } else {
      itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_article_feed_first, parent, false);
    }
    return new ArticleFeedViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ArticleFeedViewHolder holder, int position) {

    Story story = feedList[position];
    Story.Media media = story.getFirstPicture();
    if (media != null) {
      Context context = holder.image.getContext();
      Picasso.with(context).load(media.getUrl())
        .into(holder.image);
      holder.image.setVisibility(View.VISIBLE);
//      holder.image.getLayoutParams().height
    } else {
      holder.image.setImageResource(android.R.color.transparent);
      holder.image.setVisibility(View.GONE);
    }
    holder.title.setText(story.getTitle());
    holder.author.setText(story.getByLine());
    holder.date.setText(story.getPublished());
    holder.snippet.setText(story.getSummary());

  }

  @Override
  public int getItemCount() {
    return feedList == null? 0: feedList.length;
  }

  public void changeDataSet(Story[] feedList) {
    this.feedList = feedList;
    notifyDataSetChanged();
  }
}
