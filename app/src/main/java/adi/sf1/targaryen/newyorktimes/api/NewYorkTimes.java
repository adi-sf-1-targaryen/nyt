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

    Gson media = new GsonBuilder()
      .registerTypeHierarchyAdapter(Story.Media.MostPopular.Metadata[].class,
        new ArrayTypeAdapter<>(base.getAdapter(Story.Media.MostPopular.Metadata[].class)))
      .create();

    Gson story = new GsonBuilder()
      .registerTypeHierarchyAdapter(Story.Media.TopStory[].class,
        new ArrayTypeAdapter<>(media.getAdapter(Story.Media.TopStory[].class)))
      .registerTypeHierarchyAdapter(Story.Media.MostPopular[].class,
        new ArrayTypeAdapter<>(media.getAdapter(Story.Media.MostPopular[].class)))
      .registerTypeHierarchyAdapter(String[].class,
        new ArrayTypeAdapter(media.getAdapter(String[].class)))
      .create();

    Gson storyCache = new GsonBuilder()
      .registerTypeHierarchyAdapter(Story.TopStory.class,
        new CacheTypeAdapter<>(story.getAdapter(Story.TopStory.class)))
      .registerTypeHierarchyAdapter(Story.MostPopular.class,
        new CacheTypeAdapter<>(story.getAdapter(Story.MostPopular.class)))
      .create();

    Gson root = new GsonBuilder()
      .registerTypeHierarchyAdapter(Story.TopStory[].class,
        new ArrayTypeAdapter<>(storyCache.getAdapter(Story.TopStory[].class)))
      .registerTypeHierarchyAdapter(Story.MostPopular[].class,
        new ArrayTypeAdapter<>(storyCache.getAdapter(Story.MostPopular[].class)))
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
    return (Story) CacheTypeAdapter.objectCache.get(url);
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

  private static class CacheTypeAdapter<T> extends TypeAdapter<T> {
    // @todo Fix memory leak!
    private static Map objectCache = new HashMap();

    private TypeAdapter<T> base;

    public CacheTypeAdapter(TypeAdapter<T> base) {
      this.base = base;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
      base.write(out, value);
    }

    @Override
    public T read(JsonReader in) throws IOException {
      T result = base.read(in);

      Object cached = objectCache.get(result);

      if (cached == null) {
        Log.d(TAG, "read: Cache MISS");
        objectCache.put(result, result);

        return result;
      }

      Log.d(TAG, "read: Cache HIT");

      return (T) cached;
    }
  }

  private static class ArrayTypeAdapter<T> extends TypeAdapter<T> {
    TypeAdapter<T> base;

    public ArrayTypeAdapter(TypeAdapter<T> base) {
      this.base = base;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
      base.write(out, value);
    }

    @Override
    public T read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.BEGIN_ARRAY) {
        return base.read(in);
      } else {
        in.skipValue();

        return null;
      }
    }
  }
}

