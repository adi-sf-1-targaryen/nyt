package adi.sf1.targaryen.newyorktimes.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import adi.sf1.targaryen.newyorktimes.api.MostPopular.Section;
import adi.sf1.targaryen.newyorktimes.api.MostPopular.ShareType;
import adi.sf1.targaryen.newyorktimes.api.MostPopular.Time;
import adi.sf1.targaryen.newyorktimes.api.MostPopular.Type;
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

    Gson storyCache = new GsonBuilder()
      .registerTypeHierarchyAdapter(Story.class, new StoryTypeAdapter(story))
      .create();

    Gson root = new GsonBuilder()
      .registerTypeAdapter(Story[].class, new StoryArrayTypeAdapter(storyCache))
      .create();

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://api.nytimes.com/svc/")
      .addConverterFactory(GsonConverterFactory.create(root))
      .build();

    service = retrofit.create(NewYorkTimesAPI.class);
  }

  public Call<MostPopular> getMostPopular(Type type, Section section, Time time) {
    return getMostPopular(type, section, time, true);
  }

  public Call<MostPopular> getMostPopular(Type type, Section section, Time time, boolean cache) {
    if (type == Type.SHARED) {
      ShareType[] shareTypes = new ShareType[]{
        ShareType.FACEBOOK,
        ShareType.TWITTER
      };

      return getMostPopular(type, section, shareTypes, time, cache);
    } else {
      return new Call<>(service.getMostPopular(type.getValue(), section.getValue(), time.getValue(), APIKeys.NYT_MOST_POPULAR));
    }
  }

  public Call<MostPopular> getMostPopular(Type type, Section section, ShareType[] shareTypes, Time time) {
    return getMostPopular(type, section, shareTypes, time, true);
  }

  public Call<MostPopular> getMostPopular(Type type, Section section, ShareType[] shareTypes, Time time, boolean cache) {
    if (shareTypes.length == 0) {
      return getMostPopular(type, section, time, cache);
    }

    String[] shareValues = new String[shareTypes.length];

    for (int i = 0; i < shareValues.length; ++i) {
      shareValues[i] = shareTypes[i].getValue();
    }

    String share = TextUtils.join(";", shareValues);

    return new Call<>(service.getMostPopular(
      type.getValue(),
      section.getValue(),
      share,
      time.getValue(),
      APIKeys.NYT_MOST_POPULAR)
    );
  }

  public Call<TopStories> getTopStories(TopStories.Section section) {
    return new Call<>(service.getTopStores(section.getValue(), APIKeys.NYT_TOP_STORIES));
  }

  // @todo Use callback mechanism to allow for asynchronous request when the story does not exist.
  public Story getStory(String url) {
    return StoryTypeAdapter.stories.get(url);
  }

  private interface NewYorkTimesAPI {
    @GET("topstories/v1/{section}.json")
    retrofit2.Call<TopStories> getTopStores(
      @Path("section") String section,
      @Query("api-key") String APIKey
    );

    @GET("mostpopular/v2/{type}/{section}/{time}.json")
    retrofit2.Call<MostPopular> getMostPopular(
      @Path("type") String type,
      @Path("section") String section,
      @Path("time") int time,
      @Query("api-key") String APIKey
    );

    @GET("mostpopular/v2/{type}/{section}/{share}/{time}.json")
    retrofit2.Call<MostPopular> getMostPopular(
      @Path("type") String type,
      @Path("section") String section,
      @Path("share") String share,
      @Path("time") int time,
      @Query("api-key") String APIKey);
  }

  private static class StoryTypeAdapter extends TypeAdapter<Story> {
    // @todo Fix memory leak!
    private static Map<String, Story> stories = new HashMap<>();

    private TypeAdapter<Story> base;

    public StoryTypeAdapter(Gson gson) {
      base = gson.getAdapter(Story.class);
    }

    @Override
    public void write(JsonWriter out, Story value) throws IOException {
      base.write(out, value);
    }

    @Override
    public Story read(JsonReader in) throws IOException {
      Story story = base.read(in);
      String url = story.getUrl();

      Story cached = stories.get(url);

      if (cached == null) {
        Log.d(TAG, "read: Cache MISS");
        stories.put(url, story);

        return story;
      }

      Log.d(TAG, "read: Cache HIT");

      return cached;
    }
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

