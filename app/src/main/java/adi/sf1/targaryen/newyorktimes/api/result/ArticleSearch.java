package adi.sf1.targaryen.newyorktimes.api.result;

import com.google.gson.annotations.JsonAdapter;

import java.util.Date;

import adi.sf1.targaryen.newyorktimes.api.NewYorkTimes;

/**
 * Created by moltendorf on 16/4/17.
 */
public class ArticleSearch implements ResultInterface {
  private Response _response;
  private String _copyright;

  private transient String updated = (new Date()).toString();

  @Override
  public Story[] getResults() {
    return _response._docs;
  }

  @Override
  public String getUpdated() {
    return updated;
  }

  public String getCopyright() {
    return _copyright;
  }

  public int getHits() {
    return _response._meta._hits;
  }

  private static class Response {
    private Meta _meta;

    @JsonAdapter(StoryArrayTypeAdapter.class)
    private Story[] _docs;

    private static class Meta {
      private Integer _hits;
    }
  }

  public static class StoryArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Story[]> {
    public StoryArrayTypeAdapter() {
      super(NewYorkTimes.getInstance().getGson().getAdapter(Story[].class), new Story[0]);
    }
  }

  public static class Story implements StoryInterface {
    private String _web_url;
    private String _snippet;
    private String _lead_paragraph;
    private String _abstract;
    private String _print_page; // It's a string, but it contains an integer?
    private String _source;
    private Headline _headline;
    private String _pub_date;
    private String _document_type;
    private String _news_desk;
    private String _section_name;
    private String _subsection_name;
    private Byline _byline;
    private String _type_of_material;
    private String __id;
    private String _word_count;
//    private String _slideshow_credits;

//    @JsonAdapter(BlogArrayTypeAdapter.class)
//    private Blog[] _blog;

    @JsonAdapter(MediaArrayTypeAdapter.class)
    private Media[] _multimedia;

    @JsonAdapter(KeywordArrayTypeAdapter.class)
    private Keyword[] _keywords;

    @Override
    public String getSection() {
      return _section_name;
    }

    @Override
    public String getSubSection() {
      return _subsection_name;
    }

    @Override
    public String getTitle() {
      return _headline != null ? _headline.getMain() : null;
    }

    @Override
    public String getSummary() {
      return _abstract;
    }

    @Override
    public String getUrl() {
      return _web_url;
    }

    @Override
    public String getByLine() {
      return _byline != null ? _byline.getOriginal() : null;
    }

    @Override
    public String getItemType() {
      return _document_type;
    }

    @Override
    public String getUpdated() {
      return null;
    }

    @Override
    public String getCreated() {
      return null;
    }

    @Override
    public String getPublished() {
      return _pub_date;
    }

    @Override
    public String getMaterialTypeFacet() {
      return _type_of_material;
    }

    @Override
    public String getKicker() {
      return _headline != null ? _headline.getKicker() : null;
    }

    @Override
    public Media[] getMedia() {
      return _multimedia;
    }

    @Override
    public MediaInterface getFirstImage() {
      for (Media item : _multimedia) {
        if (item.getType().equals("image")) {
          return item;
        }
      }

      return null;
    }

    @Override
    public MediaInterface getLastImage() {
      for (int i = _multimedia.length - 1; i >= 0; --i) {
        Media item = _multimedia[i];

        if (item.getType().equals("image")) {
          return item;
        }
      }

      return null;
    }

    public static class MediaArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Media[]> {
      public MediaArrayTypeAdapter() {
        super(NewYorkTimes.getInstance().getGson().getAdapter(Media[].class), new Media[0]);
      }
    }

    public static class Media implements MediaInterface {
      private String _type;
      private String _subtype;
      private String _url;
      private Integer _width;
      private Integer _height;

      @Override
      public String getType() {
        return _type;
      }

      @Override
      public String getSubType() {
        return _subtype;
      }

      @Override
      public String getUrl() {
        return _url;
      }

      @Override
      public Integer getWidth() {
        return _width;
      }

      @Override
      public Integer getHeight() {
        return _height;
      }
    }

    public static class Headline {
      private String _main;
      private String _content_kicker;
      private String _kicker;
      private String _print_headline;

      public String getMain() {
        return _main != null ? _main : _print_headline;
      }

      public String getKicker() {
        return _kicker != null ? _kicker : _content_kicker;
      }
    }

    public static class KeywordArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Keyword[]> {
      public KeywordArrayTypeAdapter() {
        super(NewYorkTimes.getInstance().getGson().getAdapter(Keyword[].class), new Keyword[0]);
      }
    }

    public static class Keyword {
      private String _rank;
      private String _is_major;
      private String _name;
      private String _value;
    }

    public static class Byline {
      private String _original;

      @JsonAdapter(PersonArrayTypeAdapter.class)
      private Person[] _person;

      public String getOriginal() {
        return _original;
      }

      public static class PersonArrayTypeAdapter extends NewYorkTimes.ArrayTypeAdapter<Person[]> {
        public PersonArrayTypeAdapter() {
          super(NewYorkTimes.getInstance().getGson().getAdapter(Person[].class), new Person[0]);
        }
      }

      public static class Person {
        private String _organization;
        private String _role;
        private String _firstname;
        private Integer _rank;
        private String _lastname;
      }
    }
  }
}
