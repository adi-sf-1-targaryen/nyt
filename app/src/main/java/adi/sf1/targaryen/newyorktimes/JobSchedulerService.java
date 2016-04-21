package adi.sf1.targaryen.newyorktimes;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Raiders on 4/20/16.
 * Service to call the api every 30 mins and search for
 * new articles for the sections the user follows
 */
public class JobSchedulerService extends JobService {

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
      Toast.makeText( getApplicationContext(),
        "JobService task running", Toast.LENGTH_SHORT )
        .show();
      jobFinished( (JobParameters) msg.obj, false );
      return true;
    }

  } );
}
