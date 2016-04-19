package adi.sf1.targaryen.newyorktimes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class TwitterActivity extends AppCompatActivity {

  private static final String TWITTER_KEY = "PQd385fJYKJ3lhTGtpSuYe3Cy";
  private static final String TWITTER_SECRET = "1zQcUDzK5wFqgh2FalcXMjVwWYzXgacEO43JI9OjqOLe0cUjUi";

  private TwitterLoginButton loginButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_twitter);


    loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
    loginButton.setCallback(new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {
        // The TwitterSession is also available through:
        // Twitter.getInstance().core.getSessionManager().getActiveSession()
        TwitterSession session = result.data;
        // TODO: Remove toast and use the TwitterSession's userID
        // with your app's user model
        String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
      }

      @Override
      public void failure(TwitterException exception) {
        Log.d("TwitterKit", "Login with Twitter failure", exception);
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // Make sure that the loginButton hears the result from any
    // Activity that it triggered.
    loginButton.onActivityResult(requestCode, resultCode, data);
  }

}
