package adi.sf1.targaryen.newyorktimes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.TopStories;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;
import adi.sf1.targaryen.newyorktimes.recyclerAdapter.ArticleFeedAdapter;
import retrofit2.Response;

/**
 * Created by Raiders on 4/20/16.
 * Service to call the api every 30 mins and search for
 * new articles for the sections the user follows
 */
public class JobSchedulerService extends JobService {

  private Boolean topStoriesBoolean = true;
  private String title;
  private String snippet;
  private String urlForArticle;

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
