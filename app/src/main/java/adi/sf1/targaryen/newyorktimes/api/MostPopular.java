package adi.sf1.targaryen.newyorktimes.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moltendorf on 16/4/19.
 */
public class MostPopular {
  @SerializedName("results")
  private Story[] results;

  public Story[] getResults() {
    return results;
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
    HOME("home"),
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
}
