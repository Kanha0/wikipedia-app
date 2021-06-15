package com.wikimedia.assigment.data;

public class Continue {
//    https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8
//    https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix=List%20of&formatversion=2
//    https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&grncontinue=0.607849862682|0.607849862682|0|0&prop=revisions|images&imcontinue=25129657|Wiktionary-logo-en-v2.svg&continue=grncontinue||revisions&rvprop=content&grnlimit=10


    // json urls
    private String articleUrl;
    private String imageUrl;
    private String categoryUrl;

    private articleContinue articlecontinue;
    private imageContinue imagecontinue;
    private categoryContinue categorycontinue;

    public Continue() {
        this.articleUrl = "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions|images&rvprop=content&grnlimit=10";
        this.imageUrl = "https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8";
        this.categoryUrl = "https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix=List%20of&formatversion=2";
    }

    // getters and setters


    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public articleContinue getArticlecontinue() {
        return articlecontinue;
    }

    public void setArticlecontinue(articleContinue articleC) {
        this.articlecontinue = articleC;

        String updateUrl = "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions|images&rvprop=content&grnlimit=10";
        setArticleUrl(updateUrl);
    }

    public imageContinue getImagecontinue() {
        return imagecontinue;
    }

    public void setImagecontinue(imageContinue imagecontinue) {
        this.imagecontinue = imagecontinue;

        String udateUrl = "https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&"+imagecontinue.getGcmcontinue()+"&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&"+imagecontinue.getIcontinue()+"&format=json&utf8";
        setImageUrl(udateUrl);
    }

    public categoryContinue getCategorycontinue() {
        return categorycontinue;


    }

    public void setCategorycontinue(categoryContinue categorycontinue) {
        this.categorycontinue = categorycontinue;

        String updateUrl = "https://en.wikipedia.org/w/api.php?action=query&list=allcategories&"+categorycontinue.getAccontinue()+"&acprefix=List%20of&"+categorycontinue.getCcontinue()+"&formatversion=2";
        setCategoryUrl(updateUrl);
    }

    // sub continue classes
    //article continue
    public static class  articleContinue{

    private String imcontinue;
    private String grncontinue;
    private String Acontinue;

        public articleContinue(String imcontinue, String grncontinue, String acontinue) {
            this.imcontinue = imcontinue;
            this.grncontinue = grncontinue;
            Acontinue = acontinue;
        }

        public articleContinue() {
        }

        //getters & setters

        public String getImcontinue() {
            return imcontinue;
        }

        public String getGrncontinue() {
            return grncontinue;
        }

        public String getAcontinue() {
            return Acontinue;
        }

        public void setImcontinue(String imcontinue) {
            this.imcontinue = imcontinue;
        }

        public void setGrncontinue(String grncontinue) {
            this.grncontinue = grncontinue;
        }

        public void setAcontinue(String acontinue) {
            Acontinue = acontinue;
        }
    }

    //image continue
    public static class imageContinue{

        private String gcmcontinue;
        private String icontinue;

        public imageContinue(String gcmcontinue, String icontinue) {
            this.gcmcontinue = gcmcontinue;
            this.icontinue = icontinue;
        }

        public imageContinue() {
        }

        //getters and setters

        public String getGcmcontinue() {
            return gcmcontinue;
        }

        public void setGcmcontinue(String gcmcontinue) {
            this.gcmcontinue = gcmcontinue;
        }

        public String getIcontinue() {
            return icontinue;
        }

        public void setIcontinue(String icontinue) {
            this.icontinue = icontinue;
        }
    }


    // category continue
    public static class categoryContinue{

        private String accontinue;
        private String ccontinue;

        public categoryContinue(String accontinue, String ccontinue) {
            this.accontinue = accontinue;
            this.ccontinue = ccontinue;
        }

        public categoryContinue() {
        }

        // getters and setters

        public String getAccontinue() {
            return accontinue;
        }

        public void setAccontinue(String accontinue) {
            this.accontinue = accontinue;
        }

        public String getCcontinue() {
            return ccontinue;
        }

        public void setCcontinue(String ccontinue) {
            this.ccontinue = ccontinue;
        }
    }
}
