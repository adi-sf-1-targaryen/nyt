package adi.sf1.targaryen.newyorktimes.api.result;

/**
 * Created by moltendorf on 16/4/21.
 */
public interface StoryInterface {
  /**
   * This is overridden to allow this object to be used as a key in a HashMap.
   *
   * @return
   */
  @Override
  int hashCode();

  /**
   * This is overridden to allow this object to be used as a key in a HashMap.
   *
   * @param o
   * @return
   */
  @Override
  boolean equals(Object o);

  /**
   * Get the section this story is found in.
   *
   * @return section
   */
  String getSection();

  /**
   * Get the sub section this story is found in.
   *
   * @return sub section
   */
  String getSubSection();

  /**
   * Get the title of the story.
   *
   * @return title
   */
  String getTitle();

  /**
   * Get the summary of the story.
   * <p>
   * This item is known as "abstract" in the API. But abstract is a reserved Java keyword.
   *
   * @return summary
   */
  String getSummary();

  /**
   * Get the story's web url.
   *
   * @return web url
   */
  String getUrl();

  /**
   * Get the line detailing who the story was authored by.
   *
   * @return by line
   */
  String getByLine();

  /**
   * @return
   * @todo Documentation.
   */
  String getItemType();

  /**
   * Get the date this article was last updated on the web.
   *
   * @return date updated
   */
  String getUpdated();

  /**
   * Get the date this article was created on the web.
   *
   * @return date created
   */
  String getCreated();

  /**
   * Get the date this article was (will be) published in the news paper.
   *
   * @return date published
   */
  String getPublished();

  /**
   * @return
   * @todo Documentation.
   */
  String getMaterialTypeFacet();

  /**
   * @return
   * @todo Documentation.
   */
  String getKicker();

  /**
   * Gets the media array for this story.
   *
   * @return media array
   */
  abstract MediaInterface[] getMedia();

  /**
   * Gets the first usable image or returns null.
   * <p>
   * The New York Times API orders images from smallest to largest it seems. If this is shown to be false, we will provide
   * getSmallestImage and getLargestImage instead.
   *
   * @return first usable image or null
   */
  MediaInterface getFirstImage();

  /**
   * Gets the last usable image or returns null.
   * <p>
   * The New York Times API orders images from smallest to largest it seems. If this is shown to be false, we will provide
   * getSmallestImage and getLargestImage instead.
   *
   * @return last usable image or null
   */
  MediaInterface getLastImage();
}
