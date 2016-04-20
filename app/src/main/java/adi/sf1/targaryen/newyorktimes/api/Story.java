package adi.sf1.targaryen.newyorktimes.api;

import android.webkit.WebView;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moltendorf on 16/4/17.
 */
abstract public class Story {
  @SerializedName("section")
  private String section;

  @SerializedName("subsection")
  private String subSection;

  @SerializedName("title")
  private String title;

  @SerializedName("abstract")
  private String summary;

  @SerializedName("url")
  private String url;

  @SerializedName("byline")
  private String byLine;

  @SerializedName("item_type")
  private String itemType;

  @SerializedName("updated_date")
  private String updated;

  @SerializedName("created_date")
  private String created;

  @SerializedName("published_date")
  private String published;

  @SerializedName("material_type_facet")
  private String materialTypeFacet; // @todo Find better name.

  @SerializedName("kicker")
  private String kicker; // @todo What is this for?

  @SerializedName("des_facet")
  private String[] desFacet; // @todo Find better name.

  @SerializedName("org_facet")
  private String[] orgFacet; // @todo Find better name.

  @SerializedName("per_facet")
  private String[] perFacet; // @todo Find better name.

  @SerializedName("geo_facet")
  private String[] geoFacet; // @todo Find better name.

  @Override
  public int hashCode() {
    return url.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (getClass() == o.getClass()) {
      Story story = (Story) o;

      return url.equals(story.url);
    }

    return false;
  }

  public String getSection() {
    return section;
  }

  public String getSubSection() {
    return subSection;
  }

  public String getTitle() {
    return title;
  }

  public String getSummary() {
    return summary;
  }

  public String getUrl() {
    return url;
  }

  public String getByLine() {
    return byLine;
  }

  public String getItemType() {
    return itemType;
  }

  public String getUpdated() {
    return updated;
  }

  public String getCreated() {
    return created;
  }

  public String getPublished() {
    return published;
  }

  public String getMaterialTypeFacet() {
    return materialTypeFacet;
  }

  public String getKicker() {
    return kicker;
  }

  public String[] getDesFacet() {
    return desFacet;
  }

  public String[] getOrgFacet() {
    return orgFacet;
  }

  public String[] getPerFacet() {
    return perFacet;
  }

  public String[] getGeoFacet() {
    return geoFacet;
  }

  public abstract Media[] getMedia();

  public Media getFirstImage() {
    Media[] media = getMedia();

    if (media == null) {
      return null;
    }

    for (Media item : media) {
      if (item.type.equals("image")) {
        return item;
      }
    }

    return null;
  }

  public Media getLastImage() {
    Media[] media = getMedia();

    if (media == null) {
      return null;
    }

    for (int i = media.length - 1; i >= 0; --i) {
      Media item = media[i];

      if (item.type.equals("image")) {
        if (item instanceof Media.MostPopular) {
          Media.MostPopular.Metadata meta = ((Media.MostPopular) item).getLastMetadata();

          if (meta == null) {
            continue;
          }

          return new Media.TopStory((Media.MostPopular) item, meta);
        }

        return item;
      }
    }

    return null;
  }

  public static class TopStory extends Story {
    @SerializedName("multimedia")
    private Media.TopStory[] media;

    @Override
    public Media.TopStory[] getMedia() {
      return media;
    }
  }

  public static class MostPopular extends Story {
    @SerializedName("media")
    private Media.MostPopular[] media;

    @Override
    public Media.MostPopular[] getMedia() {
      return media;
    }
  }

  /**
   * Created by moltendorf on 16/4/17.
   */
  abstract public static class Media {
    @SerializedName("type")
    protected String type;

    @SerializedName("subtype")
    protected String subType;

    @SerializedName("caption")
    protected String caption;

    @SerializedName("copyright")
    protected String copyright;

    public abstract String getUrl();

    public abstract String getFormat();

    public abstract Integer getHeight();

    public abstract Integer getWidth();

    public String getType() {
      return type;
    }

    public String getSubType() {
      return subType;
    }

    public String getCaption() {
      return caption;
    }

    public String getCopyright() {
      return copyright;
    }

    public static class TopStory extends Media {
      @SerializedName("url")
      private String url;

      @SerializedName("format")
      private String format;

      @SerializedName("height")
      private int height;

      @SerializedName("width")
      private int width;

      TopStory(MostPopular base, MostPopular.Metadata meta) {
        type = base.getType();
        subType = base.getSubType();
        caption = base.getSubType();
        copyright = base.getSubType();
        url = meta.getUrl();
        format = meta.getFormat();
        height = meta.getHeight();
        width = meta.getWidth();
      }

      @Override
      public String getUrl() {
        return url;
      }

      @Override
      public String getFormat() {
        return format;
      }

      @Override
      public Integer getHeight() {
        return height;
      }

      @Override
      public Integer getWidth() {
        return width;
      }
    }

    public static class MostPopular extends Media {
      @SerializedName("media-metadata")
      private Metadata[] metadata;

      @Override
      public String getUrl() {
        if (metadata != null && metadata.length > 0) {
          return metadata[0].getUrl();
        }

        return null;
      }

      @Override
      public String getFormat() {
        if (metadata != null && metadata.length > 0) {
          return metadata[0].getFormat();
        }

        return null;
      }

      @Override
      public Integer getHeight() {
        if (metadata != null && metadata.length > 0) {
          return metadata[0].getHeight();
        }

        return null;
      }

      @Override
      public Integer getWidth() {
        if (metadata != null && metadata.length > 0) {
          return metadata[0].getWidth();
        }

        return null;
      }

      public Metadata getLastMetadata() {
        if (metadata == null) {
          return null;
        }

        return metadata[metadata.length - 1];
      }

      public static class Metadata {
        @SerializedName("url")
        private String url;

        @SerializedName("format")
        private String format;

        @SerializedName("height")
        private int height;

        @SerializedName("width")
        private int width;

        public String getUrl() {
          return url;
        }

        public String getFormat() {
          return format;
        }

        public int getHeight() {
          return height;
        }

        public int getWidth() {
          return width;
        }
      }
    }
  }
}
