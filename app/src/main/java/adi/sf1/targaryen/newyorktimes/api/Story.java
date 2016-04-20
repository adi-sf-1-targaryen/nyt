package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a story on the New York Times.
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

  /**
   * This is overridden to allow this object to be used as a key in a HashMap.
   *
   * @return
   */
  @Override
  public int hashCode() {
    return url.hashCode();
  }

  /**
   * This is overridden to allow this object to be used as a key in a HashMap.
   *
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (getClass() == o.getClass()) {
      Story story = (Story) o;

      return url.equals(story.url);
    }

    return false;
  }

  /**
   * Get the section this story is found in.
   *
   * @return section
   */
  public String getSection() {
    return section;
  }

  /**
   * Get the sub section this story is found in.
   *
   * @return sub section
   */
  public String getSubSection() {
    return subSection;
  }

  /**
   * Get the title of the story.
   *
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Get the summary of the story.
   * <p/>
   * This item is known as "abstract" in the API. But abstract is a reserved Java keyword.
   *
   * @return summary
   */
  public String getSummary() {
    return summary;
  }

  /**
   * Get the story's web url.
   *
   * @return web url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Get the line detailing who the story was authored by.
   *
   * @return by line
   */
  public String getByLine() {
    return byLine;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String getItemType() {
    return itemType;
  }

  /**
   * Get the date this article was last updated on the web.
   *
   * @return date updated
   */
  public String getUpdated() {
    return updated;
  }

  /**
   * Get the date this article was created on the web.
   *
   * @return date created
   */
  public String getCreated() {
    return created;
  }

  /**
   * Get the date this article was (will be) published in the news paper.
   *
   * @return date published
   */
  public String getPublished() {
    return published;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String getMaterialTypeFacet() {
    return materialTypeFacet;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String getKicker() {
    return kicker;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String[] getDesFacet() {
    return desFacet;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String[] getOrgFacet() {
    return orgFacet;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String[] getPerFacet() {
    return perFacet;
  }

  /**
   * @todo Documentation.
   *
   * @return
   */
  public String[] getGeoFacet() {
    return geoFacet;
  }

  /**
   * Gets the media array for this story.
   *
   * @return media array
   */
  public abstract Media[] getMedia();

  /**
   * Gets the first usable image or returns null.
   * <p/>
   * The New York Times API orders images from smallest to largest it seems. If this is shown to be false, we will provide
   * getSmallestImage and getLargestImage instead.
   *
   * @return first usable image or null
   */
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

  /**
   * Gets the last usable image or returns null.
   * <p/>
   * The New York Times API orders images from smallest to largest it seems. If this is shown to be false, we will provide
   * getSmallestImage and getLargestImage instead.
   *
   * @return last usable image or null
   */
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

  /**
   * Special subclass of Story specific to the Top Stories API.
   */
  public static class TopStory extends Story {
    @SerializedName("multimedia")
    private Media.TopStory[] media;

    @Override
    public Media.TopStory[] getMedia() {
      return media;
    }
  }

  /**
   * Special subclass of Story specific to the Most Popular API.
   */
  public static class MostPopular extends Story {
    @SerializedName("media")
    private Media.MostPopular[] media;

    @Override
    public Media.MostPopular[] getMedia() {
      return media;
    }
  }

  /**
   * Represents some media relating to a story. Can be an image, video, etc.
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

    /**
     * The url to fetch the media from.
     *
     * @return
     */
    public abstract String getUrl();

    /**
     * The format of the media.
     *
     * @return
     */
    public abstract String getFormat();

    /**
     * Height of the media.
     *
     * @return
     */
    public abstract Integer getHeight();

    /**
     * Width of the media.
     *
     * @return
     */
    public abstract Integer getWidth();

    /**
     * Type of the media.
     * <p/>
     * Usually image.
     *
     * @return
     */
    public String getType() {
      return type;
    }

    /**
     * Sub type of the media.
     * <p/>
     * Usually photo.
     *
     * @return
     */
    public String getSubType() {
      return subType;
    }

    /**
     * Caption to go along with the media.
     *
     * @return
     */
    public String getCaption() {
      return caption;
    }

    /**
     * Copyright holder of the media.
     *
     * @return
     */
    public String getCopyright() {
      return copyright;
    }

    /**
     * Special subclass of Media for Top Stories API.
     */
    public static class TopStory extends Media {
      @SerializedName("url")
      private String url;

      @SerializedName("format")
      private String format;

      @SerializedName("height")
      private int height;

      @SerializedName("width")
      private int width;

      /**
       * Special constructor to used to convert a Most Popular subclass of Media to a Top Stories subclass of Media.
       *
       * @param base The instance of the Most Popular Media subclass.
       * @param meta The Metadata to use.
       */
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

    /**
     * Special subclass of Media for Most Popular API.
     */
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

      /**
       * Represents different formats, sizes, etc of the media.
       */
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
