package adi.sf1.targaryen.newyorktimes.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import adi.sf1.targaryen.newyorktimes.api.result.ArticleSearch;
import adi.sf1.targaryen.newyorktimes.api.result.MostPopular;
import adi.sf1.targaryen.newyorktimes.api.result.StoryInterface;
import adi.sf1.targaryen.newyorktimes.api.result.TopStories;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Main retrofit2 New York Times API wrapper.
 * <p>
 * Singleton.
 */
public class NewYorkTimes {
  private static final String TAG = "NewYorkTimes";

  /**
   * Main API wrapper instance.
   */
  private static NewYorkTimes instance;

  /**
   * Get the singleton's instance.
   *
   * @return
   */
  public static NewYorkTimes getInstance() {
    if (instance == null) {
      instance = new NewYorkTimes();
    }

    return instance;
  }

  /**
   * Main Gson instance.
   */
  private static Gson gson = new GsonBuilder()
    .setFieldNamingStrategy(new FieldNamingStrategy() {
      @Override
      public String translateName(Field f) {
        return f.getName().substring(1);
      }
    }).create();

  /**
   * Interface built by retrofit2.
   */
  private NewYorkTimesAPI service;

  /**
   * Constructor.
   */
  NewYorkTimes() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://api.nytimes.com/svc/")
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build();

    service = retrofit.create(NewYorkTimesAPI.class);
  }

  /**
   * Get stories from the Article Search API.
   *
   * @param query search query
   * @return
   */
  public Call<ArticleSearch> articleSearch(String query) {
    Log.d(TAG, "articleSearch: Performing search for " + query);

    return new Call<>(service.articleSearch(query, APIKeys.NYT_ARTICLE_SEARCH));
  }

  /**
   * Get stories from the Most Popular API.
   *
   * @param type    Type of most popular stories; most emailed, most shared, most viewed.
   * @param section Section to find stories within.
   * @param time    Max age of the stories.
   * @return
   */
  public Call<MostPopular> getMostPopular(MostPopular.Type type, MostPopular.Section section, MostPopular.Time time) {
    if (type == MostPopular.Type.SHARED) {
      MostPopular.ShareType[] shareTypes = new MostPopular.ShareType[]{
        MostPopular.ShareType.FACEBOOK,
        MostPopular.ShareType.TWITTER
      };

      return getMostShared(section, shareTypes, time);
    } else {
      return new Call<>(service.getMostPopular(type.getValue(), section.getValue(), time.getValue(), APIKeys.NYT_MOST_POPULAR));
    }
  }

  /**
   * Get most shared stories from the Most Popular API.
   *
   * @param section    Section to find stories within.
   * @param shareTypes Only return articles shared in specific ways (email, facebook, twitter)
   * @param time       Max age of the stories.
   * @return
   */
  public Call<MostPopular> getMostShared(MostPopular.Section section, MostPopular.ShareType[] shareTypes, MostPopular.Time time) {
    if (shareTypes.length == 0) {
      return getMostPopular(MostPopular.Type.SHARED, section, time);
    }

    String[] shareValues = new String[shareTypes.length];

    for (int i = 0; i < shareValues.length; ++i) {
      shareValues[i] = shareTypes[i].getValue();
    }

    String share = TextUtils.join(";", shareValues);

    return new Call<>(service.getMostPopular(
      MostPopular.Type.SHARED.getValue(),
      section.getValue(),
      share,
      time.getValue(),
      APIKeys.NYT_MOST_POPULAR)
    );
  }

  /**
   * Get stories from the Top Stories API.
   *
   * @param section Section to find stories within.
   * @return
   */
  public Call<TopStories> getTopStories(TopStories.Section section) {
    return new Call<>(service.getTopStores(section.getValue(), APIKeys.NYT_TOP_STORIES));
  }

  /**
   * @param url
   * @return
   * @todo Use callback mechanism to allow for asynchronous request when the story does not exist.
   */
  public StoryInterface getStory(final String url) {
    AbstractStoryKey key = AbstractStoryKey.getInstance();
    key.setKey(url);

    return (StoryInterface) CacheArrayTypeAdapter.objectCache.get(key);
  }

  /**
   * Interface used for retrofit2.
   */
  private interface NewYorkTimesAPI {
    @GET("search/v2/articlesearch.json")
    retrofit2.Call<ArticleSearch> articleSearch(
      @Query("q") String query,
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

    @GET("topstories/v1/{section}.json")
    retrofit2.Call<TopStories> getTopStores(
      @Path("section") String section,
      @Query("api-key") String APIKey
    );
  }

  /**
   * Allow arrays of String to be invalid; converts invalid values to empty array.
   */
  public static class StringArrayTypeAdapter extends ArrayTypeAdapter<String[]> {
    public StringArrayTypeAdapter() {
      super(new String[0]);
    }
  }

  /**
   * TypeAdapter implementation that caches all objects and discards duplicates.
   *
   * @param <T>
   */
  public static class CacheArrayTypeAdapter<T> extends ArrayTypeAdapter<T> {
    // @todo Fix memory leak!
    private static Map objectCache = new HashMap();

    public CacheArrayTypeAdapter(T defaultValue) {
      super(defaultValue);
    }

    /**
     * Uses base Gson to parse value. Caches the value or gets the canonical object.
     * Return the defaultValue if the value cannot be parsed.
     *
     * @param in
     * @return
     * @throws IOException
     */
    @Override
    public T read(JsonReader in) throws IOException {
      Object[] objects = (Object[]) super.read(in);

      for (int i = 0; i < objects.length; ++i) {
        Object object = objects[i];
        Object cached = objectCache.get(object);

        if (cached != null) {
          objects[i] = cached;
          Log.d(TAG, "read: objectCache Cache HIT");
        } else {
          Log.d(TAG, "read: objectCache Cache MISS");
          objectCache.put(object, object);
        }
      }

      return (T) objects;
    }
  }

  /**
   * TypeAdapter that returns null when a value is not an array.
   *
   * @param <T> An array type.
   */
  public static class ArrayTypeAdapter<T> extends TypeAdapter<T> {
    private TypeAdapter<T> base;
    private T defaultValue;

    /**
     * Constructor.
     *
     * @param defaultValue Default value (usually an empty array) used when parsing fails.
     */
    protected ArrayTypeAdapter(T defaultValue) {
      base = (TypeAdapter<T>) gson.getAdapter(defaultValue.getClass());
      this.defaultValue = defaultValue;
    }

    /**
     * Doesn't do anything special; just delegates the responsibility back to Gson.
     *
     * @param out
     * @param value
     * @throws IOException
     */
    @Override
    public void write(JsonWriter out, T value) throws IOException {
      base.write(out, value);
    }

    /**
     * Return the defaultValue if the value cannot be parsed. Otherwise delegates responsibility back to Gson.
     *
     * @param in
     * @return
     * @throws IOException
     */
    @Override
    public T read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.BEGIN_ARRAY) {
        return base.read(in);
      } else {
        in.skipValue();

        return defaultValue;
      }
    }
  }

  /**
   * Used entirely to lookup objects in the object cache.
   */
  private static class AbstractStoryKey {
    /**
     * Main instance.
     */
    private static AbstractStoryKey instance = new AbstractStoryKey();

    /**
     * Get the main instance (not thread safe).
     *
     * @return
     */
    public static AbstractStoryKey getInstance() {
      return instance;
    }

    /**
     * Key used for lookup.
     */
    private String key = "";

    /**
     * Provided to match String against AbstractStory.
     *
     * @return
     */
    @Override
    public int hashCode() {
      return key.hashCode();
    }

    /**
     * Provided to match String against AbstractStory.
     *
     * @return
     */
    @Override
    public boolean equals(Object o) {
      if (o instanceof StoryInterface) {
        StoryInterface story = (StoryInterface) o;

        return key.equals(story.getUrl());
      }

      return false;
    }

    /**
     * Provide a new key for object re-use.
     *
     * @param key
     */
    public void setKey(String key) {
      this.key = key;
    }
  }
}

