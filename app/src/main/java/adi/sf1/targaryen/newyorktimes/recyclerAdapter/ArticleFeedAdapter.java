package adi.sf1.targaryen.newyorktimes.recyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import adi.sf1.targaryen.newyorktimes.R;
import adi.sf1.targaryen.newyorktimes.api.Story;

/**
 * Created by Raiders on 4/18/16.
 */
public class ArticleFeedAdapter extends RecyclerView.Adapter<ArticleFeedAdapter.ArticleFeedViewHolder> {

  private Story[] feedList = null;
  private OnItemClickListener listener;


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

    public void setOnClickListener(final Story story, final OnItemClickListener listener) {
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onItemClick(story);
        }
      });
    }
  }

  public ArticleFeedAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }

  @Override
  public ArticleFeedViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_article_feed, parent, false);
    return new ArticleFeedViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ArticleFeedViewHolder holder, int position) {

    Story story = feedList[position];
    Story.Media[] media = story.getMedia();
    if (media != null && media.length > 0 ) {
      // TODO: 4/19/16 THIS IS FOR GETTING THE IMAGE FOR THE FEED - USE PICASSO
//        holder.image.setBackgroundResource(media[0].getUrl());
    }
    holder.title.setText(story.getTitle());
    holder.author.setText(story.getByLine());
    holder.date.setText(story.getPublished());
    holder.snippet.setText(story.getSummary());
    holder.setOnClickListener(feedList[position], listener);
  }

  @Override
  public int getItemCount() {
    return feedList == null? 0: feedList.length;
  }

  public void changeDataSet(Story[] feedList) {
    this.feedList = feedList;
    notifyDataSetChanged();
  }

  public interface OnItemClickListener {
    void onItemClick(Story story);
  }

}
