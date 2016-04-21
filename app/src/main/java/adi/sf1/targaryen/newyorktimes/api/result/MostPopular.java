package adi.sf1.targaryen.newyorktimes.api.result;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;

/**
 * Created by moltendorf on 16/4/19.
 */
public class MostPopular implements ResultInterface {
  @JsonAdapter(StoryArrayTypeAdapter.class)
  private Story[] _results;

  private transient String updated = (new Date()).toString();

  public Story[] getResults() {
    return _results;
  }

  @Override
  public String getUpdated() {
    return updated;
  }

  public enum Type {
    EMAILED("mostemailed"),
    SHARED("mostshared"),
    VIEWED("mostviewed");

    private final String value;

    Type(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum Section {
    ALL("all-sections"),
    HOME("home"), // @todo Does this actually exist?
    WORLD("world"),
    NATIONAL("national"),
    POLITICS("politics"),
    NYREGION("nyregion"),
    BUSINESS("business"),
    OPINION("opinion"),
    TECHNOLOGY("technology"),
    SCIENCE("science"),
    HEALTH("health"),
    SPORTS("sports"),
    ARTS("arts"),
    FASHION("fashion"),
    DINING("dining"),
    TRAVEL("travel"),
    MAGAZINE("magazine"),
    REALESTATE("realestate");

    private final String value;

    Section(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum ShareType {
    EMAIL("email"),
    FACEBOOK("facebook"),
    TWITTER("twitter");

    private final String value;

    ShareType(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum Time {
    DAY(1),
    WEEK(7),
    MONTH(30);

    private final int value;

    Time(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  public static class StoryArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Story[]> {
    public StoryArrayTypeAdapter() {
      super(NewYorkTimes.getInstance().getGson().getAdapter(Story[].class), new Story[0]);
    }
  }

  public static class Story implements StoryInterface {
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
    private Media[] _media;

    @Override
    public int hashCode() {
      return _url.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (getClass() == o.getClass()) {
        Story story = (Story) o;

        return _url.equals(story._url);
      }

      return false;
    }

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
      return _media;
    }

    @Override
    public MediaInterface getFirstImage() {
      for (Media item : _media) {
        if (item._type.equals("image")) {
          return item;
        }
      }

      return null;
    }

    @Override
    public MediaInterface getLastImage() {
      Media[] _multimedia = getMedia();

      if (_multimedia == null) {
        return null;
      }

      for (int i = _multimedia.length - 1; i >= 0; --i) {
        final Media item = _multimedia[i];

        if (item._type.equals("image")) {
          final Media.Metadata meta = item.getLastMetadata();

          if (meta == null) {
            continue;
          }

          return new MediaInterface() {
            @Override
            public String getType() {
              return item._type;
            }

            @Override
            public String getSubType() {
              return item._subtype;
            }

            @Override
            public String getUrl() {
              return meta._url;
            }

            @Override
            public Integer getWidth() {
              return meta._width;
            }

            @Override
            public Integer getHeight() {
              return meta._height;
            }
          };
        }
      }

      return null;
    }

    public static class MediaArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Media[]> {
      public MediaArrayTypeAdapter() {
        super(NewYorkTimes.getInstance().getGson().getAdapter(Media[].class), new Media[0]);
      }
    }

    public static class Media implements MediaInterface {
      private String _type;
      private String _subtype;
      private String _caption;
      private String _copyright;

      @JsonAdapter(MetadataArrayTypeAdapter.class)
      @SerializedName("media-metadata")
      private Metadata[] _media_metadata;

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
        if (_media_metadata != null && _media_metadata.length > 0) {
          return _media_metadata[0].getUrl();
        }

        return null;
      }

      @Override
      public Integer getHeight() {
        if (_media_metadata != null && _media_metadata.length > 0) {
          return _media_metadata[0].getHeight();
        }

        return null;
      }

      @Override
      public Integer getWidth() {
        if (_media_metadata != null && _media_metadata.length > 0) {
          return _media_metadata[0].getWidth();
        }

        return null;
      }

      public Metadata getLastMetadata() {
        if (_media_metadata == null) {
          return null;
        }

        return _media_metadata[_media_metadata.length - 1];
      }

      public static class MetadataArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Metadata[]> {
        public MetadataArrayTypeAdapter() {
          super(NewYorkTimes.getInstance().getGson().getAdapter(Metadata[].class), new Metadata[0]);
        }
      }

      /**
       * Represents different formats, sizes, etc of the media.
       */
      public static class Metadata {
        private String _url;
        private String _format;
        private int _height;
        private int _width;

        public String getUrl() {
          return _url;
        }

        public String getFormat() {
          return _format;
        }

        public int getHeight() {
          return _height;
        }

        public int getWidth() {
          return _width;
        }
      }
    }
  }
}
