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
  private static final int LAYOUT_LARGE_IMAGE = 0;
  private static final int LAYOUT_SMALL_IMAGE = 1;
  private static final int LAYOUT_NOIMAGE = 2;

  private StoryInterface[] feedList = null;
  private OnItemClickListener listener;

  /**
   * View Holder for the custom views in the article feed
   */
  public class ArticleFeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title, author, date, snippet;

    private int viewType;

    /**
     * Sets the views for the article feed
     * @param itemView
     */
    public ArticleFeedViewHolder(View itemView, int viewType) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image_article);
      title = (TextView) itemView.findViewById(R.id.title_text_article);
      author = (TextView) itemView.findViewById(R.id.author_text_article);
      date = (TextView) itemView.findViewById(R.id.date_text_article);
      snippet = (TextView) itemView.findViewById(R.id.content_text_article);
      this.viewType = viewType;
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
    if (feedList[position].getFirstImage() != null) {
      int large = (feedList[position].hashCode() % 5 == 0) ? LAYOUT_LARGE_IMAGE : LAYOUT_SMALL_IMAGE;

      return position == 0 ? LAYOUT_LARGE_IMAGE : large;
    } else {
      return LAYOUT_NOIMAGE;
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
    int layout;

    switch (viewType) {
      case LAYOUT_LARGE_IMAGE:
        layout = R.layout.card_view_article_feed_first;
        break;

      case LAYOUT_SMALL_IMAGE:
        layout = R.layout.card_view_article_feed;
        break;

      case LAYOUT_NOIMAGE:
      default:
        layout = R.layout.card_view_article_feed_noimage;
        break;
    }

    return new ArticleFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false), viewType);
  }

  /**
   * Places the appropriate data into the views for the feed
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(ArticleFeedViewHolder holder, int position) {

    StoryInterface story = feedList[position];

    if (holder.image != null) {
      MediaInterface media;

      if (holder.viewType == LAYOUT_LARGE_IMAGE) {
        media = story.getLastImage(); // big
      } else {
        media = story.getFirstImage(); // small
      }
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
    }
    holder.title.setText(story.getTitle());
    holder.author.setText(story.getByLine());
    String largeDate = story.getPublished();
    if (largeDate != null) {
      String alteredDate = largeDate.substring(0, 10);

    }

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
