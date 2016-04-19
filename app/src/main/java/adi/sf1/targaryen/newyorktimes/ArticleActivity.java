package adi.sf1.targaryen.newyorktimes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Raiders on 4/19/16.
 */
public class ArticleActivity extends AppCompatActivity {

  ImageView articleImage;
  TextView articleTitle;
  TextView articleAuthor;
  TextView articleDate;
  TextView articleContent;

  String title;
  String author;
  String date;
  String content;

  @Override
  public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setContentView(R.layout.activity_article);

    setViews();
  }

  private void setViews() {
    articleImage = (ImageView) findViewById(R.id.image_article);
    articleTitle = (TextView) findViewById(R.id.title_text_article);
    articleAuthor = (TextView) findViewById(R.id.author_text_article);
    articleDate = (TextView) findViewById(R.id.date_text_article);
    articleContent = (TextView) findViewById(R.id.content_text_article);
  }


}
