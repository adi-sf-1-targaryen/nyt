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


  public class ArticleFeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title, author, date, snippet;

    public ArticleFeedViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image_recycler);
      title = (TextView) itemView.findViewById(R.id.title_text_recycler);
      author = (TextView) itemView.findViewById(R.id.author_text_recycler);
      date = (TextView) itemView.findViewById(R.id.date_text_recycler);
      snippet = (TextView) itemView.findViewById(R.id.snippet_text_recycler);
    }
  }

  public ArticleFeedAdapter() {

  }

  @Override
  public ArticleFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
