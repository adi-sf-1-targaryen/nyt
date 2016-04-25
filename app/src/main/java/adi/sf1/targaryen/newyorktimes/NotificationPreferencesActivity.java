package adi.sf1.targaryen.newyorktimes;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by Raiders on 4/20/16.
 * Allows a user to choose sections to get notifications about when a new article is published
 */
public class NotificationPreferencesActivity extends AppCompatActivity {

  //region Global Variables
  CheckBox topStories, mostPopular, opinion, world, us, businessDay, sports, arts, ny, magazine;
  SharedPreferences sharedPreferences;
  //endregion Global Variables

  //region Checked booleans
  private boolean topStoriesCheck = false;
  private boolean mostPopularCheck = false;
  private boolean opinionCheck = false;
  private boolean worldCheck = false;
  private boolean usCheck = false;
  private boolean businessDayCheck = false;
  private boolean sportsCheck = false;
  private boolean artsCheck = false;
  private boolean nyCheck = false;
  private boolean magazineCheck = false;
  //endregion Checked boeleans

  //region Shared Preferences Booleans Codes
  public static final String BOOLEAN_CODE = "booleanCode";
  private String TOP_STORIES_CODE = "topStories";
  private String MOST_POPULAR_CODE = "mostPopular";
  private String OPINION_CODE = "opinion";
  private String WORLD_CODE = "world";
  private String US_CODE = "US";
  private String BUSINESS_DAY_CODE = "businessDay";
  private String SPORTS_CODE = "sports";
  private String ARTS_CODE = "arts";
  private String NY_CODE = "NY";
  private String MAGAZINE_CODE = "magazine";
  //endregion Shared Preferences Booleans Codes

  /**
   * Creates the shared preferences and notification manager
   * Calls methods to make the activity functional
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preferences);

    setViews();
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    setCheckboxClicks();
    setJobHandler();
  }

  /**
   * Sets the views in the activity
   */
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
              topStoriesCheck = true;
            } else {
              topStoriesCheck =false;
            }
            break;
          case R.id.checkbox_most_popular:
            if (checked) {
              mostPopularCheck = true;
            } else {
              mostPopularCheck = false;
            }
            break;
          case R.id.checkbox_opinion:
            if (checked) {
              opinionCheck = true;
            } else {
              opinionCheck = false;
            }
            break;
          case R.id.checkbox_world:
            if (checked) {
              worldCheck = true;
            } else {
              worldCheck = false;
            }
            break;
          case R.id.checkbox_us:
            if (checked) {
              usCheck = true;
            } else {
              usCheck = false;
            }
            break;
          case R.id.checkbox_business_day:
            if (checked) {
              businessDayCheck = true;
            } else {
              businessDayCheck = false;
            }
            break;
          case R.id.checkbox_sports:
            if (checked) {
              sportsCheck = true;
            } else {
              sportsCheck = false;
            }
            break;
          case R.id.checkbox_arts:
            if (checked) {
              artsCheck = true;
            } else {
              artsCheck = false;
            }
            break;
          case R.id.checkbox_ny:
            if (checked) {
              nyCheck = true;
            } else {
              nyCheck = false;
            }
            break;
          case R.id.checkbox_magazine:
            if (checked) {
              magazineCheck = true;
            } else {
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
  @TargetApi(21)
  private void setJobHandler() {
    if (Integer.valueOf(Build.VERSION.SDK_INT) > 20) {
      JobScheduler mJobScheduler = (JobScheduler)
        getSystemService( Context.JOB_SCHEDULER_SERVICE );
      JobInfo.Builder builder = new JobInfo.Builder( 1,
        new ComponentName( getPackageName(),
          JobSchedulerService.class.getName() ) );
      builder.setPeriodic( 216000000 );
      if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
        //If something goes wrong
      }
    }
  }

  /**
   * Sends an array of booleans to the service to let the
   * service know which sections it needs to create notifications for
   */
  private void createIntentForJobScheduler() {
    Intent serviceIntent = new Intent(this,JobSchedulerService.class);
    boolean[] booleenArray = new boolean[10];
    booleenArray[0] = topStoriesCheck;
    booleenArray[1] = mostPopularCheck;
    booleenArray[2] = opinionCheck;
    booleenArray[3] = worldCheck;
    booleenArray[4] = usCheck;
    booleenArray[5] = businessDayCheck;
    booleenArray[6] = sportsCheck;
    booleenArray[7] = artsCheck;
    booleenArray[8] = nyCheck;
    booleenArray[9] = magazineCheck;
    serviceIntent.putExtra(BOOLEAN_CODE, booleenArray);
    getApplicationContext().startService(serviceIntent);
  }

  /**
   * Sends the array of booleans to the service
   */
  @Override
  protected void onStop() {
    super.onStop();
    createIntentForJobScheduler();
  }
}
