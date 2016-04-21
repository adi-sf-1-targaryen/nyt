package adi.sf1.targaryen.newyorktimes.api.result;

abstract class AbstractStory implements StoryInterface {
  /**
   * This is overridden to allow this object to be used as a key in a HashMap.
   *
   * @return
   */
  @Override
  public int hashCode() {
    return getUrl().hashCode();
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
      StoryInterface story = (StoryInterface) o;

      return getUrl().equals(story.getUrl());
    }

    return false;
  }
}
