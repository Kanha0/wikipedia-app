package com.wikimedia.assigment.cards;

import com.wikimedia.assigment.data.Imageinfo;

import java.util.ArrayList;

/**
 * If this page was created with User#queryImageinfo() you can download the
 * image with this method. <br/> <b>Note:</b> this method doesn't close the
 * given output stream!
 *
 * param out put Stream
 *          the output stream where the image should be written to. For
 *          example, if you would save the image in a file, you can use
 *          <code>FileOutputStream</code>.
 */
//public void downloadImageUrl(OutputStream outputStream) {
//        downloadImageUrl(outputStream, imageUrl);

public class ImageCard {

    private String pageid;
    private String title;
    private Imageinfo imageinfo;

    public ImageCard(String pageid, String title, Imageinfo imageinfo) {
        this.pageid = pageid;
        this.title = title;
        this.imageinfo = imageinfo;
    }


    // getters and setters

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Imageinfo getImageinfo() {
        return imageinfo;
    }

    public void setImageinfo(Imageinfo imageinfo) {
        this.imageinfo = imageinfo;
    }
}
