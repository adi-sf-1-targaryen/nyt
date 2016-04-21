package adi.sf1.targaryen.newyorktimes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by emiliaaxen on 16-04-20.
 */
public class CheckInternetConnection {

  public static boolean isOnline(Context context) {
    ConnectivityManager connMgr = (ConnectivityManager)
      context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected());
  }
}

  /*private Context _context;

  public CheckInternetConnection(Context context){
    this._context = context;
  }

  public boolean isConnectingToInternet(){
    ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null)
    {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null)
        for (int i = 0; i < info.length; i++)
          if (info[i].getState() == NetworkInfo.State.CONNECTED)
          {
            return true;
          }

    }
    return false;
  }
}*/

  /*public static isConnected() {

    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      // the connection is available
      } else {
      // the connection is not available }
    }
    ConnectivityManager cm =
      (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
      activeNetwork.isConnectedOrConnecting();*/



