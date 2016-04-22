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

import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.result.MostPopular;
import adi.sf1.targaryen.newyorktimes.api.result.TopStories;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;
import retrofit2.Response;

/**
 * Created by Raiders on 4/20/16.
 * Service to call the api every 30 mins and search for
 * new articles for the sections the user follows
 */
public class JobSchedulerService extends JobService {

  private String title;
  private String snippet;
  private String urlForArticle;

  boolean topStoryCheck;
  boolean mostPopularCheck;
  boolean opinionCheck;
  boolean worldCheck;
  boolean usCheck;
  boolean businessDayCheck;
  boolean sportsCheck;
  boolean artsCheck;
  boolean nyCheck;
  boolean magazineCheck;

  private final static int TOP_STORY_ID = 2321;
  private final static int MOST_POPULAR_ID = 3422;
  private final static int OPINION_ID = 4523;
  private final static int WORLD_ID = 5624;
  private final static int US_ID = 1111;
  private final static int BUSINESS_DAY_ID = 6725;
  private final static int SPORTS_ID = 2222;
  private final static int NY_ID = 7825;
  private final static int MAGAZINE_ID = 8927;
  private final static int ARTS_ID = 9101;

  TopStories.Section topStory = TopStories.Section.HOME;
  TopStories.Section opinion = TopStories.Section.OPINION;
  TopStories.Section world = TopStories.Section.WORLD;
  TopStories.Section us = TopStories.Section.NATIONAL;
  TopStories.Section business = TopStories.Section.BUSINESS;
  TopStories.Section sports = TopStories.Section.SPORTS;
  TopStories.Section arts = TopStories.Section.ARTS;
  TopStories.Section ny = TopStories.Section.NYREGION;
  TopStories.Section magazine = TopStories.Section.MAGAZINE;

  /**
   * Get booleans from activity to tell if notifications are needed for the section
   * @param intent
   * @param flags
   * @param startId
   * @return
   */
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    boolean[] booleenArray = intent.getBooleanArrayExtra(NotificationPreferencesActivity.BOOLEAN_CODE);
    topStoryCheck = booleenArray[0];
    mostPopularCheck = booleenArray[1];
    opinionCheck = booleenArray[2];
    worldCheck = booleenArray[3];
    usCheck = booleenArray[4];
    businessDayCheck = booleenArray[5];
    sportsCheck = booleenArray[6];
    artsCheck = booleenArray[7];
    nyCheck = booleenArray[8];
    magazineCheck = booleenArray[9];

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
      checkIfTrueTopStory(topStoryCheck, topStory, TOP_STORY_ID);
      checkIfTrueTopStory(opinionCheck, opinion, OPINION_ID);
      checkIfTrueTopStory(worldCheck, world, WORLD_ID);
      checkIfTrueTopStory(usCheck, us, US_ID);
      checkIfTrueTopStory(businessDayCheck, business, BUSINESS_DAY_ID);
      checkIfTrueTopStory(sportsCheck, sports, SPORTS_ID);
      checkIfTrueTopStory(artsCheck, arts, ARTS_ID);
      checkIfTrueTopStory(nyCheck, ny, NY_ID);
      checkIfTrueTopStory(magazineCheck, magazine, MAGAZINE_ID);
      checkIfMostPopularTrue();
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

  /**
   * Checks whether or not a top story api notification has been requested by the user.
   * If it has, it makes the api call for the required section and creates the notification
   * @param checkedSection
   * @param section
   * @param id
   */
  private void checkIfTrueTopStory(boolean checkedSection, TopStories.Section section, final int id) {
    if (checkedSection) {
      NewYorkTimes.getInstance().getTopStories(section).enqueue(new Callback<TopStories>() {
        @Override
        public void onResponse(Call<TopStories> call, Response<TopStories> response) {
          title = response.body().getResults()[0].getTitle();
          snippet = response.body().getResults()[0].getSummary();
          urlForArticle = response.body().getResults()[0].getUrl();
          createNotifications(id);
        }

        @Override
        public void onFailure(Call<TopStories> call, Throwable t) {

        }
      });
    }
  }

  /**
   * Checks whether or not a most popular story api notification has been requested by the user.
   * If it has, it makes the api call for most popular story and creates the notification
   */
  private void checkIfMostPopularTrue() {
    if (mostPopularCheck) {
      NewYorkTimes.getInstance().getMostPopular(MostPopular.Type.EMAILED, MostPopular.Section.ALL, MostPopular.Time.DAY).enqueue(new Callback<MostPopular>() {
        @Override
        public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
          title = response.body().getResults()[0].getTitle();
          snippet = response.body().getResults()[0].getSummary();
          urlForArticle = response.body().getResults()[0].getUrl();
          createNotifications(MOST_POPULAR_ID);
        }

        @Override
        public void onFailure(Call<MostPopular> call, Throwable t) {

        }
      });
    }
  }
}
