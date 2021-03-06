package adi.sf1.targaryen.newyorktimes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.twitter.sdk.android.Twitter;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;

import io.fabric.sdk.android.Fabric;

import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.result.StoryInterface;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;

/**
 * Creates activity for the clicked article.
 * Allows you to share the article on social media and email/text
 * Grabs article data from the article url passed in an intent from article feed
 */
public class ArticleActivity extends AppCompatActivity {

  WebView articleBrowser;

  ImageButton shareAcrossAllButton;
  ImageButton twitterShareButton;
  ShareButton shareButton;

  String title;
  String urlForArticle;

  CallbackManager callbackManager;
  StoryInterface story;

  private static final String TWITTER_KEY = "PQd385fJYKJ3lhTGtpSuYe3Cy";
  private static final String TWITTER_SECRET = "1zQcUDzK5wFqgh2FalcXMjVwWYzXgacEO43JI9OjqOLe0cUjUi";

  /**
   * Method calls methods necessary for this activity to function.
   * This method initializes the facebook and twitter sharing functions
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Initialize Facebook SDK
    FacebookSdk.sdkInitialize(getApplicationContext());
    //Initialize Twitter and Fabric SDK
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));
    Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
    setContentView(R.layout.activity_article);

    setViews();
    getIntentFromFeedFragment();
    fillViews();
    setShareAcrossAllButton();
    facebookIntegrationMethods();
    twitterIntegrationMethods();
  }

  /**
   * This method sets the view
   */
  private void setViews() {
    articleBrowser = (WebView) findViewById(R.id.article_web_view);
    shareButton = (ShareButton) findViewById(R.id.facebook_share_button);
    twitterShareButton = (ImageButton) findViewById(R.id.twitter_share_button);
    shareAcrossAllButton = (ImageButton) findViewById(R.id.shareButton);
  }

  /**
   * This method allows the user to log in to his/hers twitter account, and to tweet article
   */
  private void twitterIntegrationMethods() {
    twitterShareButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
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
     * Methods for logging in to facebook
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
     * Post to facebook wall
     */
    shareButton.setShareContent(shareLinkContent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
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
   * This method tells the web view what url to load
   */
  private void fillViews() {
    articleBrowser.setWebViewClient(new WebViewClient());
    articleBrowser.loadUrl(urlForArticle);
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
