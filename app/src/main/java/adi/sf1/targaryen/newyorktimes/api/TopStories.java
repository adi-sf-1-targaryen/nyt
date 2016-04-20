package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moltendorf on 16/4/17.
 */
public class TopStories {
  @SerializedName("copyright")
  private String copyright;

  @SerializedName("section")
  private Section section;

  @SerializedName("last_updated")
  private String updated;

  @SerializedName("results")
  private Story[] results;

  public String getCopyright() {
    return copyright;
  }

  public Section getSection() {
    return section;
  }

  public String getUpdated() {
    return updated;
  }

  public Story[] getResults() {
    return results;
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
}
