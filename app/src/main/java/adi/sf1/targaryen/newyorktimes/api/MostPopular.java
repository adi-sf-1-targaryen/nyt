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
}
