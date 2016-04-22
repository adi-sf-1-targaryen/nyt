package adi.sf1.targaryen.newyorktimes.api.result;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;

public class TopStories implements ResultInterface {
  private String _last_updated;
  private Section _section;
  private String _copyright;

  @JsonAdapter(StoryArrayTypeAdapter.class)
  private Story[] _results;

  @Override
  public Story[] getResults() {
    return _results;
  }

  @Override
  public String getUpdated() {
    return _last_updated;
  }

  public Section getSection() {
    return _section;
  }

  /**
   * Gets the copyright for these results.
   * @return
   */
  public String getCopyright() {
    return _copyright;
  }

  public enum Section {
    @SerializedName("home")
    HOME("home"),

    @SerializedName("world")
    WORLD("world"),

    @SerializedName("national")
    NATIONAL("national"),

    @SerializedName("politics")
    POLITICS("politics"),

    @SerializedName("nyregion")
    NYREGION("nyregion"),

    @SerializedName("business")
    BUSINESS("business"),

    @SerializedName("opinion")
    OPINION("opinion"),

    @SerializedName("technology")
    TECHNOLOGY("technology"),

    @SerializedName("science")
    SCIENCE("science"),

    @SerializedName("health")
    HEALTH("health"),

    @SerializedName("sports")
    SPORTS("sports"),

    @SerializedName("arts")
    ARTS("arts"),

    @SerializedName("fashion")
    FASHION("fashion"),

    @SerializedName("dining")
    DINING("dining"),

    @SerializedName("travel")
    TRAVEL("travel"),

    @SerializedName("magazine")
    MAGAZINE("magazine"),

    @SerializedName("realestate")
    REALESTATE("realestate");

    private final String value;

    Section(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  /**
   * Allow arrays of Story to be invalid; converts invalid values to empty array.
   */
  public static class StoryArrayTypeAdapter extends NewYorkTimes.CacheArrayTypeAdapter<Story[]> {
    public StoryArrayTypeAdapter() {
      super(new Story[0]);
    }
  }

  /**
   * Implementation of StoryInterface for Top Stories API.
   *
   * AbstractStory includes the StoryInterface.
   */
  public static class Story extends AbstractStory {
    private String _section;
    private String _subsection;
    private String _title;
    private String _abstract;
    private String _url;
    private String _byline;
    private String _item_type;
    private String _updated_date;
    private String _created_date;
    private String _published_date;
    private String _material_type_facet; // @todo Find better name.
    private String _kicker; // @todo What is this for?

    @JsonAdapter(NewYorkTimes.StringArrayTypeAdapter.class)
    private String[] _des_facet; // @todo Find better name.

    @JsonAdapter(NewYorkTimes.StringArrayTypeAdapter.class)
    private String[] _org_facet; // @todo Find better name.

    @JsonAdapter(NewYorkTimes.StringArrayTypeAdapter.class)
    private String[] _per_facet; // @todo Find better name.

    @JsonAdapter(NewYorkTimes.StringArrayTypeAdapter.class)
    private String[] _geo_facet; // @todo Find better name.

    @JsonAdapter(MediaArrayTypeAdapter.class)
    private Media[] _multimedia;

    @Override
    public String getSection() {
      return _section;
    }

    @Override
    public String getSubSection() {
      return _subsection;
    }

    @Override
    public String getTitle() {
      return _title;
    }

    @Override
    public String getSummary() {
      return _abstract;
    }

    @Override
    public String getUrl() {
      return _url;
    }

    @Override
    public String getByLine() {
      return _byline;
    }

    @Override
    public String getItemType() {
      return _item_type;
    }

    @Override
    public String getUpdated() {
      return _updated_date;
    }

    @Override
    public String getCreated() {
      return _created_date;
    }

    @Override
    public String getPublished() {
      return _published_date;
    }

    @Override
    public String getMaterialTypeFacet() {
      return _material_type_facet;
    }

    @Override
    public String getKicker() {
      return _kicker;
    }

    @Override
    public Media[] getMedia() {
      return _multimedia;
    }

    @Override
    public MediaInterface getFirstImage() {
      for (Media item : _multimedia) {
        if (item.getType().equals("image")) {
          return item;
        }
      }

      return null;
    }

    @Override
    public MediaInterface getLastImage() {
      for (int i = _multimedia.length - 1; i >= 0; --i) {
        Media item = _multimedia[i];

        if (item._type.equals("image")) {
          return item;
        }
      }

      return null;
    }

    /**
     * Allow arrays of Media to be invalid; converts invalid values to empty array.
     */
    public static class MediaArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Media[]> {
      public MediaArrayTypeAdapter() {
        super(new Media[0]);
      }
    }

    /**
     * Implementation of MediaInterface for the Top Stories API.
     */
    public static class Media implements MediaInterface {
      private String _type;
      private String _subtype;
      private String _caption;
      private String _copyright;
      private String _url;
      private String _format;
      private int _height;
      private int _width;

      @Override
      public String getType() {
        return _type;
      }

      @Override
      public String getSubType() {
        return _subtype;
      }

      @Override
      public String getUrl() {
        return _url;
      }

      @Override
      public Integer getWidth() {
        return _width;
      }

      @Override
      public Integer getHeight() {
        return _height;
      }
    }
  }
}
