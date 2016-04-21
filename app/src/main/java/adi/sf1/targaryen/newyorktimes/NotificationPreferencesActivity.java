package adi.sf1.targaryen.newyorktimes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.MostPopular;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.TopStories;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;
import retrofit2.Response;

/**
 * Created by Raiders on 4/20/16.
 * Allows a user to choose sections to get notifications about when a new article is published
 */
public class NotificationPreferencesActivity extends AppCompatActivity {

  CheckBox topStories, mostPopular, opinion, world, us, businessDay, sports, arts, ny, magazine;
  private String title;
  private String snippet;
  private String urlForArticle;
  NotificationManager mNotificationManager;
  SharedPreferences sharedPreferences;

  public static boolean topStoriesCheck = false;
  private boolean mostPopularCheck = false;
  private boolean opinionCheck = false;
  private boolean worldCheck = false;
  private boolean usCheck = false;
  private boolean businessDayCheck = false;
  private boolean sportsCheck = false;
  private boolean artsCheck = false;
  private boolean nyCheck = false;
  private boolean magazineCheck = false;

  public static final String TOP_STORIES_CODE = "topStories";
  private String MOST_POPULAR_CODE = "mostPopular";
  private String OPINION_CODE = "opinion";
  private String WORLD_CODE = "world";
  private String US_CODE = "US";
  private String BUSINESS_DAY_CODE = "businessDay";
  private String SPORTS_CODE = "sports";
  private String ARTS_CODE = "arts";
  private String NY_CODE = "NY";
  private String MAGAZINE_CODE = "magazine";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preferences);

    setViews();
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    setCheckboxClicks();
    setJobHandler();
  }

  private void setViews() {
    topStories = (CheckBox) findViewById(R.id.checkbox_top_stories);
    mostPopular = (CheckBox) findViewById(R.id.checkbox_most_popular);
    opinion = (CheckBox) findViewById(R.id.checkbox_opinion);
    world = (CheckBox) findViewById(R.id.checkbox_world);
    us = (CheckBox) findViewById(R.id.checkbox_us);
    businessDay = (CheckBox) findViewById(R.id.checkbox_business_day);
    sports = (CheckBox) findViewById(R.id.checkbox_sports);
    arts = (CheckBox) findViewById(R.id.checkbox_arts);
    ny = (CheckBox) findViewById(R.id.checkbox_ny);
    magazine = (CheckBox) findViewById(R.id.checkbox_magazine);
  }

  /**
   * method for checking if checkboxes are checked and making notifications for
   * the sections if they are checked
   * @param view
   */
  private void onCheckboxClicked(final View view) {

    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
          case R.id.checkbox_top_stories:
            int NOTIFICATION_ID1 = 1;
            if (checked) {
              TopStories.Section section = TopStories.Section.HOME;
              getTopArticleForSection(section, NOTIFICATION_ID1);
              topStoriesCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID1);
              topStoriesCheck =false;
            }
            break;
          case R.id.checkbox_most_popular:
            int NOTIFICATION_ID2 = 2;
            if (checked) {
              getMostPopularArticles(NOTIFICATION_ID2);
              mostPopularCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID2);
              mostPopularCheck = false;
            }
            break;
          case R.id.checkbox_opinion:
            int NOTIFICATION_ID3 = 3;
            if (checked) {
              TopStories.Section section = TopStories.Section.OPINION;
              getTopArticleForSection(section, NOTIFICATION_ID3);
              opinionCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID3);
              opinionCheck = false;
            }
            break;
          case R.id.checkbox_world:
            int NOTIFICATION_ID4 = 4;
            if (checked) {
              TopStories.Section section = TopStories.Section.WORLD;
              getTopArticleForSection(section, NOTIFICATION_ID4);
              worldCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID4);
              worldCheck = false;
            }
            break;
          case R.id.checkbox_us:
            int NOTIFICATION_ID5 = 5;
            if (checked) {
              TopStories.Section section = TopStories.Section.NATIONAL;
              getTopArticleForSection(section, NOTIFICATION_ID5);
              usCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID5);
              usCheck = false;
            }
            break;
          case R.id.checkbox_business_day:
            int NOTIFICATION_ID6 = 6;
            if (checked) {
              TopStories.Section section = TopStories.Section.BUSINESS;
              getTopArticleForSection(section, NOTIFICATION_ID6);
              businessDayCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID6);
              businessDayCheck = false;
            }
            break;
          case R.id.checkbox_sports:
            int NOTIFICATION_ID7 = 7;
            if (checked) {
              TopStories.Section section = TopStories.Section.SPORTS;
              getTopArticleForSection(section, NOTIFICATION_ID7);
              sportsCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID7);
              sportsCheck = false;
            }
            break;
          case R.id.checkbox_arts:
            int NOTIFICATION_ID8 = 8;
            if (checked) {
              TopStories.Section section = TopStories.Section.ARTS;
              getTopArticleForSection(section, NOTIFICATION_ID8);
              artsCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID8);
              artsCheck = false;
            }
            break;
          case R.id.checkbox_ny:
            int NOTIFICATION_ID9 = 9;
            if (checked) {
              TopStories.Section section = TopStories.Section.NYREGION;
              getTopArticleForSection(section, NOTIFICATION_ID9);
              nyCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID9);
              nyCheck = false;
            }
            break;
          case R.id.checkbox_magazine:
            int NOTIFICATION_ID10 = 10;
            if (checked) {
              TopStories.Section section = TopStories.Section.MAGAZINE;
              getTopArticleForSection(section, NOTIFICATION_ID10);
              magazineCheck = true;
            } else {
              mNotificationManager.cancel(NOTIFICATION_ID10);
              magazineCheck = false;
            }
            break;
          default:
        }
      }
    });
  }

  /**
   * Adds each checkbox to see if they have been checked
   */
  private void setCheckboxClicks() {
    onCheckboxClicked(topStories);
    onCheckboxClicked(mostPopular);
    onCheckboxClicked(opinion);
    onCheckboxClicked(world);
    onCheckboxClicked(us);
    onCheckboxClicked(businessDay);
    onCheckboxClicked(sports);
    onCheckboxClicked(arts);
    onCheckboxClicked(ny);
    onCheckboxClicked(magazine);
  }

  /**
   * Queries top stories api for first article in the selected category
   * @param section
   */
  private void getTopArticleForSection(TopStories.Section section, final int notificationID) {
    NewYorkTimes.getInstance().getTopStories(section).enqueue(new Callback<TopStories>() {
      @Override
      public void onResponse(Call<TopStories> call, Response<TopStories> response) {
        title = response.body().getResults()[0].getTitle();
        snippet = response.body().getResults()[0].getSummary();
        urlForArticle = response.body().getResults()[0].getUrl();
        createNotifications(notificationID);
      }

      @Override
      public void onFailure(Call<TopStories> call, Throwable t) {
        Toast.makeText(NotificationPreferencesActivity.this, "Could not retrieve Top Stories", Toast.LENGTH_SHORT)
          .show();
      }
    });
  }

  /**
   * Queries most popular api for first article
   */
  private void getMostPopularArticles(final int notificationID) {
    NewYorkTimes.getInstance().getMostPopular(MostPopular.Type.EMAILED, MostPopular.Section.ALL, MostPopular.Time.DAY)
      .enqueue(new Callback<MostPopular>() {
        @Override
        public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
          title = response.body().getResults()[0].getTitle();
          snippet = response.body().getResults()[0].getSummary();
          urlForArticle = response.body().getResults()[0].getUrl();
          createNotifications(notificationID);
        }

        @Override
        public void onFailure(Call<MostPopular> call, Throwable t) {
          Toast.makeText(NotificationPreferencesActivity.this, "Could not retrieve Most Popular Stories", Toast.LENGTH_SHORT)
            .show();
        }
      });
  }

  /**
   * Creates notifications for top article of selected category
   * If the notification is clicked on, the user will be taken to the article
   */
  private void createNotifications(int notificationID) {
    Intent intent = new Intent(this, ArticleActivity.class);
    intent.putExtra(ArticleFeedFragment.URL_EXTRA_KEY, urlForArticle);
    PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

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
   * Saves the state of the checkboxes in the shared preferences
   */
  @Override
  protected void onPause() {
    super.onPause();
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(TOP_STORIES_CODE, topStoriesCheck);
    editor.putBoolean(MOST_POPULAR_CODE, mostPopularCheck);
    editor.putBoolean(OPINION_CODE, opinionCheck);
    editor.putBoolean(WORLD_CODE, worldCheck);
    editor.putBoolean(US_CODE, usCheck);
    editor.putBoolean(BUSINESS_DAY_CODE, businessDayCheck);
    editor.putBoolean(SPORTS_CODE, sportsCheck);
    editor.putBoolean(ARTS_CODE, artsCheck);
    editor.putBoolean(NY_CODE, nyCheck);
    editor.putBoolean(MAGAZINE_CODE, magazineCheck);
    editor.commit();
  }

  /**
   * Retrieves the state of the checkboxes from the shared preferences
   */
  @Override
  protected void onResume() {
    super.onResume();
    topStoriesCheck = sharedPreferences.getBoolean(TOP_STORIES_CODE, topStoriesCheck);
    topStories.setChecked(topStoriesCheck);
    mostPopularCheck = sharedPreferences.getBoolean(MOST_POPULAR_CODE, mostPopularCheck);
    mostPopular.setChecked(mostPopularCheck);
    opinionCheck = sharedPreferences.getBoolean(OPINION_CODE, opinionCheck);
    opinion.setChecked(opinionCheck);
    worldCheck = sharedPreferences.getBoolean(WORLD_CODE, worldCheck);
    world.setChecked(worldCheck);
    usCheck = sharedPreferences.getBoolean(US_CODE, usCheck);
    us.setChecked(usCheck);
    businessDayCheck = sharedPreferences.getBoolean(BUSINESS_DAY_CODE, businessDayCheck);
    businessDay.setChecked(businessDayCheck);
    sportsCheck = sharedPreferences.getBoolean(SPORTS_CODE, sportsCheck);
    sports.setChecked(sportsCheck);
    artsCheck = sharedPreferences.getBoolean(ARTS_CODE, artsCheck);
    arts.setChecked(artsCheck);
    nyCheck = sharedPreferences.getBoolean(NY_CODE, nyCheck);
    ny.setChecked(nyCheck);
    magazineCheck = sharedPreferences.getBoolean(MAGAZINE_CODE, magazineCheck);
    magazine.setChecked(magazineCheck);
  }

  /**
   * Builds the JobScheduler in this activity.
   * Sets the time for it to call the api every 60 mins
   */
  private void setJobHandler() {
    JobScheduler mJobScheduler = (JobScheduler)
      getSystemService( Context.JOB_SCHEDULER_SERVICE );

    JobInfo.Builder builder = new JobInfo.Builder( 1,
      new ComponentName( getPackageName(),
        JobSchedulerService.class.getName() ) );

    builder.setPeriodic( 3000 );

    if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
      //If something goes wrong
    }
  }
}
