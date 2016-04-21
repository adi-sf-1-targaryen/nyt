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
import adi.sf1.targaryen.newyorktimes.api.result.MediaInterface;
import adi.sf1.targaryen.newyorktimes.api.result.StoryInterface;

/**
 * Created by Raiders on 4/18/16.
 * Adapter for the recycler view that shows the article feed.
 */
public class ArticleFeedAdapter extends RecyclerView.Adapter<ArticleFeedAdapter.ArticleFeedViewHolder> {

  private StoryInterface[] feedList = null;
  private OnItemClickListener listener;

  /**
   * View Holder for the custom views in the article feed
   */
  public class ArticleFeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title, author, date, snippet;

    /**
     * Sets the views for the article feed
     * @param itemView
     */
    public ArticleFeedViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image_article);
      title = (TextView) itemView.findViewById(R.id.title_text_article);
      author = (TextView) itemView.findViewById(R.id.author_text_article);
      date = (TextView) itemView.findViewById(R.id.date_text_article);
      snippet = (TextView) itemView.findViewById(R.id.content_text_article);
    }

    /**
     * Sets a click listener on the recycler view cards
     * @param story
     * @param listener
     */
    public void setOnClickListener(final StoryInterface story, final OnItemClickListener listener) {
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onItemClick(story);
        }
      });
    }
  }

  /**
   * Constructor for the adapter
   * Implements an onItemClickListener to allow user to click each article in the feed
   * @param listener
   */
  public ArticleFeedAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }


  public int getItemViewType(int position) {

    if (position == 0) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Creates the views in the view holder
   * @param parent
   * @param viewType
   * @return
   */
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

  /**
   * Places the appropriate data into the views for the feed
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(ArticleFeedViewHolder holder, int position) {

    StoryInterface story = feedList[position];
    MediaInterface media = story.getFirstImage();
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
    String largeDate = story.getPublished();
    String alteredDate = largeDate.substring(0, 10);
    holder.date.setText(alteredDate);
    holder.snippet.setText(story.getSummary());
    holder.setOnClickListener(feedList[position], listener);
  }

  /**
   * Tells the adapter how many items will need to be in the feed, if applicable
   * @return
   */
  @Override
  public int getItemCount() {
    return feedList == null? 0: feedList.length;
  }

  /**
   * Changes the data in the feed
   * @param feedList
   */
  public void changeDataSet(StoryInterface[] feedList) {
    this.feedList = feedList;
    notifyDataSetChanged();
  }

  /**
   * Creates public interface for item click listener
   */
  public interface OnItemClickListener {
    void onItemClick(StoryInterface story);
  }

}
