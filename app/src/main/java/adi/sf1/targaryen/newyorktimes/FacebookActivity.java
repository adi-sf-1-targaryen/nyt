package adi.sf1.targaryen.newyorktimes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareButton;

/**
 * This activity uses Facebook sdk tools to get content, pictures or videos from a urlForArticle or specified location.
 * These methods are called in an activity or fragment and then can be posted on facebook.
 * Created by Raiders on 4/18/16.
 */
public class FacebookActivity extends AppCompatActivity {

  ShareButton shareButton;
  LoginButton loginButton;
  CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
      //Initialize Facebook SDK
      FacebookSdk.sdkInitialize(getApplicationContext());
      super.onCreate(savedInstanceState, persistentState);

    }

  /**
   * Get content from a site to post on fb
   * @param url
   * @return
   */
    public ShareLinkContent setFacebookContent(String url) {
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
          .setContentUrl(Uri.parse(url))
          .build();
        return shareLinkContent;
    }

  /**
   * Get image to post on fb
   * @param image
   * @return
   */
    public SharePhotoContent setFacebookImage(Bitmap image) {
        SharePhoto photo = new SharePhoto.Builder()
          .setBitmap(image)
          .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
          .addPhoto(photo)
          .build();
        return content;
    }

  /**
   * Get video to post on fb
   * @param videoUrl
   * @return
   */
    public ShareVideoContent setFacebookVideo(Uri videoUrl) {
        ShareVideo video = new ShareVideo.Builder()
          .setLocalUrl(videoUrl)
          .build();
        ShareVideoContent content = new ShareVideoContent.Builder()
          .setVideo(video)
          .build();
        return content;
    }

  private void facebookIntegrationMethods() {
//    shareButton = (ShareButton) findViewById(R.id.fb_share_button);
//    loginButton = (LoginButton) findViewById(R.id.fb_login_button);

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
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

  //        shareDialog = new ShareDialog(this);
//        if(shareButton != null) {
//            if(ShareDialog.canShow(ShareLinkContent.class)) {
//                ShareLinkContent content = new ShareLinkContent.Builder()
//                  .setContentTitle("Testing")
//                  .setContentDescription("This is my test share from app")
//                  .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                  .build();
//                shareDialog.show(content);
//            }
//        }
}
