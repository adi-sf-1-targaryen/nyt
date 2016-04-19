package adi.sf1.targaryen.newyorktimes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.Story;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;

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
  String urlForArticle;
  String urlForImage;

  ShareButton shareButton;
  LoginButton loginButton;
  CallbackManager callbackManager;
  Story story;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Initialize Facebook SDK
    FacebookSdk.sdkInitialize(getApplicationContext());
    setContentView(R.layout.activity_article);

    setViews();
    getIntentFromFeedFragment();
    setArticleObjects();
    fillViews();
    facebookIntegrationMethods();
  }

  private void setViews() {
    articleImage = (ImageView) findViewById(R.id.image_article);
    articleTitle = (TextView) findViewById(R.id.title_text_article);
    articleAuthor = (TextView) findViewById(R.id.author_text_article);
    articleDate = (TextView) findViewById(R.id.date_text_article);
    articleContent = (TextView) findViewById(R.id.content_text_article);
    shareButton = (ShareButton) findViewById(R.id.facebook_share_button);
    loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
  }

  /**
   * Methods taken from Facebook SDK to be able to log in or out of facebook and then post this article
   * to a facebook wall.
   */
  private void facebookIntegrationMethods() {
    /**
     * Methods for logging in
     */
    callbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        Toast toast = Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_SHORT);
        toast.show();
      }

      @Override
      public void onCancel() {

      }

      @Override
      public void onError(FacebookException error) {
        Toast toast = Toast.makeText(getApplicationContext(), "Successful Unsuccessful", Toast.LENGTH_SHORT);
        toast.show();
      }
    });

    /**
     * Dummy content to post to facebook wall
     */
    ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
      .setContentUrl(Uri.parse(urlForArticle))
      .setContentTitle("NFL")
      .setContentDescription("This is the football")
      .build();

    /**
     * Post to facebookwall
     */
    shareButton.setShareContent(shareLinkContent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

  private void getIntentFromFeedFragment() {
    urlForArticle = getIntent().getStringExtra(ArticleFeedFragment.URL_EXTRA_KEY);
    story = NewYorkTimes.getInstance().getStory(urlForArticle);
  }

  private void setArticleObjects() {
    title = story.getTitle();
    author = story.getByLine();
    date = story.getPublished();
    content = story.getSummary();
  }

  private void fillViews() {
    articleTitle.setText(title);
    articleAuthor.setText(author);
    articleDate.setText(date);
    articleContent.setText(content);
  }
}
