package lastfm.models;

import java.io.Serializable;

/**
 * Created by Adhu on 6/5/2018.
 */

public class Artist implements Serializable {

    private static final long id = 3L;

    private String name;
    private String url;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
