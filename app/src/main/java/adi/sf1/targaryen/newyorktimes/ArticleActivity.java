package adi.sf1.targaryen.newyorktimes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;

import io.fabric.sdk.android.Fabric;

import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.Story;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;

/**
 * Created by Raiders on 4/19/16.
 * Creates activity for the clicked article.
 * Allows you to share the article on social media and email/text
 * Grabs article data from the article url passed in an intent from article feed
 */
public class ArticleActivity extends AppCompatActivity {

  ImageView articleImage;
  TextView articleTitle;
  TextView articleAuthor;
  TextView articleDate;
  TextView articleContent;
  ImageButton shareAcrossAllButton;

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
  Button twitterShareButton;
  TwitterLoginButton twitterLoginButton;


  private static final String TWITTER_KEY = "PQd385fJYKJ3lhTGtpSuYe3Cy";
  private static final String TWITTER_SECRET = "1zQcUDzK5wFqgh2FalcXMjVwWYzXgacEO43JI9OjqOLe0cUjUi";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Initialize Facebook SDK
    FacebookSdk.sdkInitialize(getApplicationContext());
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));
    Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
    setContentView(R.layout.activity_article);

    setViews();
    getIntentFromFeedFragment();
    setArticleObjects();
    fillViews();
    setShareAcrossAllButton();
    facebookIntegrationMethods();
    twitterIntergrationMethods();
  }

  private void setViews() {
    articleImage = (ImageView) findViewById(R.id.image_article);
    articleTitle = (TextView) findViewById(R.id.title_text_article);
    articleAuthor = (TextView) findViewById(R.id.author_text_article);
    articleDate = (TextView) findViewById(R.id.date_text_article);
    articleContent = (TextView) findViewById(R.id.content_text_article);
    shareButton = (ShareButton) findViewById(R.id.facebook_share_button);
    loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
    twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
    twitterShareButton = (Button) findViewById(R.id.twitter_share_button);
    shareAcrossAllButton = (ImageButton) findViewById(R.id.shareButton);
  }

  private void twitterIntergrationMethods() {
    twitterLoginButton.setCallback(new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {
        // The TwitterSession is also available through:
        // Twitter.getInstance().core.getSessionManager().getActiveSession()
        TwitterSession session = result.data;
        //
        // with your app's user model
        String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


      }

      @Override
      public void failure(TwitterException exception) {
        Log.d("TwitterKit", "Login with Twitter failure", exception);
      }
    });



    twitterShareButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);



        TweetComposer.Builder builder = null;
        try {
          builder = new TweetComposer.Builder(ArticleActivity.this)
            .text("Tweet this article:")
            .url(new URL(urlForArticle));
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
        builder.show();
      }
    });
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
     * Content to post to facebook wall
     */
    ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
      .setContentUrl(Uri.parse(urlForArticle))
      .setContentTitle(title)
      .setContentDescription(story.getSummary())
      .build();

    /**
     * Post to facebookwall
     */
    shareButton.setShareContent(shareLinkContent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

  /**
   * Grabs the intent from ArticleFeedFragment
   * Takes url of article and creates the story object with for the corresponding article
   */
  private void getIntentFromFeedFragment() {
    urlForArticle = getIntent().getStringExtra(ArticleFeedFragment.URL_EXTRA_KEY);
    story = NewYorkTimes.getInstance().getStory(urlForArticle);
  }

  /**
   * Sets article details from the story object for the article
   */
  private void setArticleObjects() {
    title = story.getTitle();
    author = story.getByLine();
    String fullDate = story.getPublished();
    date = fullDate.substring(0, 10);
    content = story.getSummary();
    Story.Media media = story.getFirstImage();
    urlForImage = media != null ? media.getUrl() : null;
  }

  /**
   * Places the article details in their views
   */
  private void fillViews() {
    articleTitle.setText(title);
    articleAuthor.setText(author);
    articleDate.setText(date);
    articleContent.setText(content);
  }

  /**
   * Sets up Share button to allow user to share article on various mediums
   */
  private void setShareAcrossAllButton() {
    shareAcrossAllButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, urlForArticle);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
        startActivity(Intent.createChooser(intent, "Share"));
      }
    });
  }
}
