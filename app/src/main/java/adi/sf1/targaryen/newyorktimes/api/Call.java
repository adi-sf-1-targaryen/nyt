package adi.sf1.targaryen.newyorktimes.api;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Response;

/**
 * Created by moltendorf on 16/4/19.
 */
public class Call<T> {
  private static final String TAG = "Call";

  private static Map<HttpUrl, Response> responses = new HashMap<>();

  private retrofit2.Call<T> parent;

  public Call(retrofit2.Call<T> parent) {
    this.parent = parent;
  }

  public Response<T> execute() throws IOException {
    return null;
  }

  public void enqueue(final Callback<T> callback, boolean cache) {
    final HttpUrl url = parent.request().url();
    Response response = responses.get(url);

    if (cache && response != null) {
      Log.d(TAG, "enqueue: Cache MISS");

      callback.onResponse(this, response); // Our API wrapper should never mix URLs up.
    } else {
      Log.d(TAG, "enqueue: Cache HIT");

      parent.enqueue(new retrofit2.Callback<T>() {
        @Override
        public void onResponse(retrofit2.Call<T> call, Response<T> response) {
          if (response.isSuccessful()) {
            responses.put(url, response);
          }

          callback.onResponse(Call.this, response);
        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
          callback.onFailure(Call.this, t);
        }
      });
    }
  }

  public boolean isExecuted() {
    return parent.isExecuted();
  }

  public void cancel() {
    parent.cancel();
  }

  public boolean isCanceled() {
    return false;
  }

  public Request request() {
    return parent.request();
  }
}
