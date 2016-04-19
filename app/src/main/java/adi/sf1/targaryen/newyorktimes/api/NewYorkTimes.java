package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by moltendorf on 16/4/15.
 */
public class NewYorkTimes {
  private static final String TAG = "NewYorkTimes";

  private static NewYorkTimes instance;

  public static NewYorkTimes getInstance() {
    if (instance == null) {
      instance = new NewYorkTimes();
    }

    return instance;
  }

  private NewYorkTimesAPI service;

  NewYorkTimes() {
    Gson base = new Gson();

    Gson story = new GsonBuilder()
      .registerTypeHierarchyAdapter(Story.Media[].class, new MediaArrayTypeAdapter(base))
      .registerTypeHierarchyAdapter(String[].class, new StringArrayTypeAdapter(base))
      .create();

    Gson root = new GsonBuilder()
      .registerTypeAdapter(Story[].class, new StoryArrayTypeAdapter(story))
      .create();

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://api.nytimes.com/svc/")
      .addConverterFactory(GsonConverterFactory.create(root))
      .build();

    service = retrofit.create(NewYorkTimesAPI.class);
  }

  public Call<TopStories> getTopStories(TopStories.Section section) {
    return service.getTopStores(section.getValue(), APIKeys.NYT_TOP_STORIES);
  }

  private interface NewYorkTimesAPI {
    @GET("topstories/v1/{section}.json")
    Call<TopStories> getTopStores(@Path("section") String section, @Query("api-key") String APIKey);
  }

  // @todo Find better way to compact all of our ArrayTypeAdapters.
  private static class StoryArrayTypeAdapter extends TypeAdapter<Story[]> {
    TypeAdapter<Story[]> base;

    public StoryArrayTypeAdapter(Gson gson) {
      base = gson.getAdapter(Story[].class);
    }

    @Override
    public void write(JsonWriter out, Story[] value) throws IOException {
      base.write(out, value);
    }

    @Override
    public Story[] read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.BEGIN_ARRAY) {
        return base.read(in);
      } else {
        in.skipValue();

        return null;
      }
    }
  }

  // @todo Find better way to compact all of our ArrayTypeAdapters.
  private static class MediaArrayTypeAdapter extends TypeAdapter<Story.Media[]> {
    TypeAdapter<Story.Media[]> base;

    public MediaArrayTypeAdapter(Gson gson) {
      base = gson.getAdapter(Story.Media[].class);
    }

    @Override
    public void write(JsonWriter out, Story.Media[] value) throws IOException {
      base.write(out, value);
    }

    @Override
    public Story.Media[] read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.BEGIN_ARRAY) {
        return base.read(in);
      } else {
        in.skipValue();

        return null;
      }
    }
  }

  // @todo Find better way to compact all of our ArrayTypeAdapters.
  private static class StringArrayTypeAdapter extends TypeAdapter<String[]> {
    TypeAdapter<String[]> base;

    public StringArrayTypeAdapter(Gson gson) {
      base = gson.getAdapter(String[].class);
    }

    @Override
    public void write(JsonWriter out, String[] value) throws IOException {
      base.write(out, value);
    }

    @Override
    public String[] read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.BEGIN_ARRAY) {
        return base.read(in);
      } else {
        in.skipValue();

        return null;
      }
    }
  }
}
