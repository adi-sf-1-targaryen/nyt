package adi.sf1.targaryen.newyorktimes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import adi.sf1.targaryen.newyorktimes.api.Call;
import adi.sf1.targaryen.newyorktimes.api.Callback;
import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;
import adi.sf1.targaryen.newyorktimes.api.TopStories;
import adi.sf1.targaryen.newyorktimes.fragment.ArticleFeedFragment;
import retrofit2.Response;

/**
 * Created by Raiders on 4/20/16.
 */
public class NotificationPreferencesActivity extends AppCompatActivity {

  CheckBox topStories, mostPopular, opinion, world, us, businessDay, sports, arts, ny, magazine;
  private static final String TAG = "Notification Preferences Activity";
  private static final int NOTIFICATION_ID = 98;
  private String title;
  private String snippet;
  private String urlForArticle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preferences);

    setViews();
    setCheckboxClicks();
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
            if (checked) {
              TopStories.Section section = TopStories.Section.HOME;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_most_popular:
            if (checked) {

            }
            break;
          case R.id.checkbox_opinion:
            if (checked) {
              TopStories.Section section = TopStories.Section.OPINION;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_world:
            if (checked) {
              TopStories.Section section = TopStories.Section.WORLD;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_us:
            if (checked) {
              TopStories.Section section = TopStories.Section.NATIONAL;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_business_day:
            if (checked) {
              TopStories.Section section = TopStories.Section.BUSINESS;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_sports:
            if (checked) {
              TopStories.Section section = TopStories.Section.SPORTS;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_arts:
            if (checked) {
              TopStories.Section section = TopStories.Section.ARTS;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_ny:
            if (checked) {
              TopStories.Section section = TopStories.Section.NYREGION;
              getTopArticleForSection(section);
              createNotifications();
            }
            break;
          case R.id.checkbox_magazine:
            if (checked) {
              TopStories.Section section = TopStories.Section.MAGAZINE;
              getTopArticleForSection(section);
              createNotifications();
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
  private void getTopArticleForSection(TopStories.Section section) {
    NewYorkTimes.getInstance().getTopStories(section).enqueue(new Callback<TopStories>() {
      @Override
      public void onResponse(Call<TopStories> call, Response<TopStories> response) {
        title = response.body().getResults()[0].getTitle();
        snippet = response.body().getResults()[0].getSummary();
        urlForArticle = response.body().getResults()[0].getUrl();
      }

      @Override
      public void onFailure(Call<TopStories> call, Throwable t) {
        Toast.makeText(NotificationPreferencesActivity.this, "Could not retrieve Top Stories", Toast.LENGTH_SHORT)
          .show();
      }
    });
  }

  /**
   * Creates notifications for top article of selected category
   * If the notification is clicked on, the user will be taken to the article
   */
  private void createNotifications() {
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

    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
  }
}
