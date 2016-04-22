package adi.sf1.targaryen.newyorktimes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by emiliaaxen on 16-04-20.
 * This class holds a method to tell whether or not the user is connected to the internet.
 * The method is called before making every API call
 */
public class CheckInternetConnection {

  /**
   * Shows whether you have an internet connection
   * @param context
   * @return
   */
  public static boolean isOnline(Context context) {
    ConnectivityManager connMgr = (ConnectivityManager)
      context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected());
  }
}

