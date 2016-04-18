package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
  private Date updated;

  @SerializedName("created_date")
  private Date created;

  @SerializedName("published_date")
  private Date published;

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
  }
}
