package adi.sf1.targaryen.newyorktimes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;

import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;

/**
 * Created by Raiders on 4/20/16.
 * Service to call the api every 30 mins and search for
 * new articles for the sections the user follows
 */
public class JobSchedulerService extends JobService {

  private String title;
  private String snippet;
  private String urlForArticle;


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    boolean[] booleenArray = intent.getBooleanArrayExtra(NotificationPreferencesActivity.BOOLEAN_CODE);

    return START_STICKY;
  }

  /**
   * Starts the job scheduler
   * @param params
   * @return
   */
  @Override
  public boolean onStartJob(JobParameters params) {
    mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
    return true;
  }

  /**
   * Stops the job scheduler
   * @param params
   * @return
   */
  @Override
  public boolean onStopJob(JobParameters params) {
    mJobHandler.removeMessages( 1 );
    return false;
  }

  /**
   * Creates the job for the job scheduler
   */
  private Handler mJobHandler = new Handler( new Handler.Callback() {

    @Override
    public boolean handleMessage( Message msg ) {

      jobFinished( (JobParameters) msg.obj, false );
      return true;
    }

  } );

  /**
   * Creates notifications for top article of selected category
   * If the notification is clicked on, the user will be taken to the article
   */
  private void createNotifications(int notificationID) {
    Intent intent = new Intent(this, ArticleActivity.class);
    intent.putExtra(ArticleFeedFragment.URL_EXTRA_KEY, urlForArticle);
    PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
    mBuilder.setSmallIcon(R.drawable.ic_ny_times_notification);
    mBuilder.setContentTitle(title);
    mBuilder.setContentText(snippet);
    mBuilder.setContentIntent(pIntent);
    mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
    mBuilder.setAutoCancel(true);

    mNotificationManager.notify(notificationID, mBuilder.build());
  }
}
