package adi.sf1.targaryen.newyorktimes.api;

import retrofit2.Response;

/**
 * Created by moltendorf on 16/4/19.
 */
public interface Callback<T> {
  void onResponse(Call<T> call, Response<T> response);

  void onFailure(Call<T> call, Throwable t);
}
