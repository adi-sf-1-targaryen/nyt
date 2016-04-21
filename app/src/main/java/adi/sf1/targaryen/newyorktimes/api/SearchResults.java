package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moltendorf on 16/4/17.
 */
public class SearchResults {
  @SerializedName("response")
  private Response response;

  @SerializedName("copyright")
  private String copyright;

  public String getCopyright() {
    return copyright;
  }

  public int getHits() {
    return response.meta.hits;
  }

  public Story[] getResults() {
    return response.results;
  }

  public static class Story {
    @SerializedName("web_url")
    private String url;

    @SerializedName("snippet")
    private String snippet;

    @SerializedName("lead_paragraph")
    private String leadParagraph;

    @SerializedName("abstract")
    private String summary;

    @SerializedName("print_page")
    private String printPage; // It's a string, but it contains an integer?

//    @SerializedName("blog")
//    private Blog[] blogs;

    @SerializedName("source")
    private String source;

    @SerializedName("multimedia")
    private Media[] media;

    @SerializedName("headline")
    private Headline headline;

    @SerializedName("keywords")
    private Keyword[] keywords;

    @SerializedName("pub_date")
    private String pubDate;

    @SerializedName("document_type")
    private String documentType;

    @SerializedName("news_desk")
    private String newsDesk;

    @SerializedName("section_name")
    private String sectionName;

    @SerializedName("subsection_name")
    private String subsectionName;

    @SerializedName("byline")
    private Byline byline;

    @SerializedName("type_of_material")
    private String typeOfMaterial;

    @SerializedName("_id")
    private String id;

    @SerializedName("word_count")
    private String wordCount;

//    @SerializedName("slideshow_credits")
//    private String slideshowCredits;

    public static class Media extends adi.sf1.targaryen.newyorktimes.api.Story.Media.TopStory {
      public Media(MostPopular base, MostPopular.Metadata meta) {
        super(base, meta);
      }
    }

    public static class Headline {
      @SerializedName("main")
      private String main;

      @SerializedName("content_kicker")
      private String contentKicker;

      @SerializedName("kicker")
      private String kicker;

      @SerializedName("print_headline")
      private String printHeadline;
    }

    public static class Keyword {
      @SerializedName("rank")
      private String rank;

      @SerializedName("is_major")
      private String isMajor;

      @SerializedName("name")
      private String name;

      @SerializedName("value")
      private String value;
    }

    public static class Byline {
      @SerializedName("person")
      private Person[] persons;

      @SerializedName("original")
      private String original;

      public static class Person {
        @SerializedName("organization")
        private String organization;

        @SerializedName("role")
        private String role;

        @SerializedName("firstname")
        private String firstname;

        @SerializedName("rank")
        private Integer rank;

        @SerializedName("lastname")
        private String lastname;
      }
    }
  }

  private static class Response {
    @SerializedName("meta")
    private Meta meta;

    @SerializedName("docs")
    private Story[] results;

    private static class Meta {
      private int hits;
    }
  }
}
