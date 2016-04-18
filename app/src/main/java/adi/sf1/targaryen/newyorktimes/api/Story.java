package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moltendorf on 16/4/17.
 */
public class Story {
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
  private String perFacet; // @todo Find better name.

  @SerializedName("geo_facet")
  private String geoFacet; // @todo Find better name.

  @SerializedName("multimedia")
  private Media[] media;

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

  public String getPerFacet() {
    return perFacet;
  }

  public String getGeoFacet() {
    return geoFacet;
  }

  public Media[] getMedia() {
    return media;
  }

  /**
   * Created by moltendorf on 16/4/17.
   */
  public static class Media {
    @SerializedName("url")
    private String url;

    @SerializedName("format")
    private String format;

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @SerializedName("type")
    private String type;

    @SerializedName("subtype")
    private String subType;

    @SerializedName("caption")
    private String caption;

    @SerializedName("copyright")
    private String copyright;

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
  }
}
