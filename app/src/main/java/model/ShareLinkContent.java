package model;

import android.net.Uri;

/**
 * Created by Raiders on 4/18/16.
 */
public class ShareLinkContent {

  private String contentDescription;
  private String contentTitle;
  private Uri imageUrl;
  private String quote;

  public String getContentDescription() {
    return contentDescription;
  }

  public String getContentTitle() {
    return contentTitle;
  }

  public Uri getImageUrl() {
    return imageUrl;
  }

  public String getQuote() {
    return quote;
  }
}
