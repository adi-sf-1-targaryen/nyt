package adi.sf1.targaryen.newyorktimes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
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

import java.net.URL;

import io.fabric.sdk.android.Fabric;

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
  String url;

  ShareButton shareButton;
  LoginButton loginButton;
  CallbackManager callbackManager;
  Button twitterShareButton;
  TwitterLoginButton twitterLoginButton;
  private static final String TWITTER_KEY = "PQd385fJYKJ3lhTGtpSuYe3Cy";
  private static final String TWITTER_SECRET = "1zQcUDzK5wFqgh2FalcXMjVwWYzXgacEO43JI9OjqOLe0cUjUi";

  @Override
  public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    //Initialize Facebook SDK
    FacebookSdk.sdkInitialize(getApplicationContext());
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));
    Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
    setContentView(R.layout.activity_article);

    setViews();
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

    TweetComposer.Builder builder = new TweetComposer.Builder(this)
      .text("Tweet this article:")
      .url(URL url = new URL(getString(R.string.))
      ;
    builder.show();
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
      .setContentUrl(Uri.parse("http://www.nfl.com/"))
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
    twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

}
