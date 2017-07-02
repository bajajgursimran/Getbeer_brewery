package com.news.getbeer;

/**
 * Created by BAJAJ on 11-06-2017.
 */

public class GetBeer {
    private String name;
    private String description;
    private String imageurl;
    private String firstbrewed;

    public GetBeer(String name,String description,String imageurl,String firstbrewed){
        this.name = name;
        this.description=description;
        this.imageurl=imageurl;
        this.firstbrewed=firstbrewed;
    }
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.imageurl;
    }
    public String getFirstBrewed() {
        return this.firstbrewed;
    }
}
