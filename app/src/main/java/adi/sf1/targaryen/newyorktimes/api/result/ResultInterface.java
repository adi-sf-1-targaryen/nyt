package adi.sf1.targaryen.newyorktimes.api.result;

/**
 * Represents an API result.
 */
public interface ResultInterface {
  /**
   * Get results for this request.
   * @return
   */
  StoryInterface[] getResults();

  /**
   * Get the time when this result was last updated.
   * @return
   */
  String getUpdated();
}
