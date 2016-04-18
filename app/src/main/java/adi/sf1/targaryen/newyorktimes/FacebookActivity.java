package adi.sf1.targaryen.newyorktimes;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;

/**
 * This activity uses Facebook sdk tools to get content, pictures or videos from a url or specified location.
 * These methods are called in an activity or fragment and then can be posted on facebook.
 * Created by Raiders on 4/18/16.
 */
public class FacebookActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //Initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
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

    //Set multimedia function

  /**
   * Creates the share dialog interface that publishes to facebook.
   * This will be in an activity or fragment it is called from.
   * https://developers.facebook.com/docs/sharing/android#share_dialog
   * for reference
   */
//    private void setShareDialog() {
//        ShareDialog shareDialog = new ShareDialog(MainActivity.class);
//        shareDialog.show(contentFromThisActivity, mode);
//    }

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
