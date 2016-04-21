package adi.sf1.targaryen.newyorktimes.api.result;

/**
 * Created by moltendorf on 16/4/21.
 */
public interface MediaInterface {
  /**
   * Type of the media.
   * Usually image.
   *
   * @return
   */
  String getType();

  /**
   * Sub type of the media.
   * Usually photo.
   *
   * @return
   */
  String getSubType();

  /**
   * The url to fetch the media from.
   *
   * @return
   */
  String getUrl();

  /**
   * Width of the media.
   *
   * @return
   */
  Integer getWidth();

  /**
   * Height of the media.
   *
   * @return
   */
  Integer getHeight();
}
