package adi.sf1.targaryen.newyorktimes.api;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Response;

/**
 * Wrapper class for retrofit2.Call<T>.
 * Caches responses on success.
 */
public class Call<T> {
  private static final String TAG = "Call";

  // @todo Fix memory leak!
  private static Map<HttpUrl, Response> responseCache = new HashMap<>();

  private retrofit2.Call<T> parent;

  public Call(retrofit2.Call<T> parent) {
    this.parent = parent;
  }

  /**
   * Synchronously send the request and return its response.
   *
   * @throws IOException      if a problem occurred talking to the server.
   * @throws RuntimeException (and subclasses) if an unexpected error occurs creating the request
   *                          or decoding the response.
   */
  public Response<T> execute1() throws IOException {
    return null;
  }

  /**
   * Asynchronously send the request and notify {@code callback} of its response or if an error
   * occurred talking to the server, creating the request, or processing the response.
   *
   * @param callback
   */
  public void enqueue(final Callback<T> callback) {
    enqueue(callback, true);
  }

  /**
   * Asynchronously send the request and notify {@code callback} of its response or if an error
   * occurred talking to the server, creating the request, or processing the response.
   *
   * @param callback
   * @param cache    Whether or not to fetch the response from cache.
   */
  public void enqueue(final Callback<T> callback, boolean cache) {
    final HttpUrl url = parent.request().url();
    Response response = responseCache.get(url);

    if (cache && response != null) {
      Log.d(TAG, "enqueue: responseCache Cache HIT");

      callback.onResponse(this, response); // Our API wrapper should never mix URLs up.
    } else {
      Log.d(TAG, "enqueue: responseCache Cache MISS");

      parent.enqueue(new retrofit2.Callback<T>() {
        @Override
        public void onResponse(retrofit2.Call<T> call, Response<T> response) {
          if (response.isSuccessful()) {
            responseCache.put(url, response);
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

  /**
   * Returns true if this call has been either {@linkplain #execute() executed} or {@linkplain
   * #enqueue(Callback) enqueued}. It is an error to execute or enqueue a call more than once.
   */
  public boolean isExecuted() {
    return parent.isExecuted();
  }

  /**
   * Cancel this call. An attempt will be made to cancel in-flight calls, and if the call has not
   * yet been executed it never will be.
   */
  public void cancel() {
    parent.cancel();
  }

  /**
   * True if {@link #cancel()} was called.
   */
  public boolean isCanceled() {
    return false;
  }

  /**
   * The original HTTP request.
   */
  public Request request() {
    return parent.request();
  }
}
