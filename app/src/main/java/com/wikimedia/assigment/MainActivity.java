package com.wikimedia.assigment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wikimedia.assigment.cards.Cards;
import com.wikimedia.assigment.cards.CategoryCard;
import com.wikimedia.assigment.cards.ImageCard;
import com.wikimedia.assigment.cards.RandomArticleCard;
import com.wikimedia.assigment.data.Continue;
import com.wikimedia.assigment.data.Imageinfo;
import com.wikimedia.assigment.data.Query;
import com.wikimedia.assigment.data.RecordReader;
import com.wikimedia.assigment.data.RecordWriter;
import com.wikimedia.assigment.volley.IVolleyJsonRespondsListener;
import com.wikimedia.assigment.volley.PostVolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

//int visiblePosition = manager.findLastCompletelyVisibleItemPosition();
public class MainActivity extends AppCompatActivity implements IVolleyJsonRespondsListener {

    private RecyclerView recyclerView;
    private ArrayList<Cards> items;
    private Continue mContinue;
    private CardAdapter cardAdapter;
    private ArrayList<ImageCard> ImageList;
    private ArrayList<CategoryCard> CategoryList;
    private ArrayList<RandomArticleCard> ArticleList;
    private int IMAGE_CARD_VIEW;
    private int ARTICLE_CARD_VIEW;
    private int CATEGORY_CARD_VIEW;
    private String ARICLEFILE;
    private String IMAGEFILE ;
    private String CATEGORYFILE ;

    // we will pass this in thread pool
    private static final int number_of_threads = 4;
    // we will define ExecitorService to execute th i\o function of database at background thread
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(number_of_threads);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        findViewById();

        //
//        databaseWriterExecutor.execute(() -> {

            fetchdata(mContinue);
//        });
        //setting recycler View
//        fetchdata(mContinue);
//
       setRecyclerView();


    }

    //initialization of variables
    private void findViewById() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        items = new ArrayList<>();
        mContinue = new Continue();
        ArticleList = new ArrayList<>();
        CategoryList = new ArrayList<>();
        ImageList = new ArrayList<>();
        ARTICLE_CARD_VIEW = 0;
        IMAGE_CARD_VIEW = 1;
        CATEGORY_CARD_VIEW = 2;
        ARICLEFILE = "article.txt";
        IMAGEFILE = "image.txt";
        CATEGORYFILE = "category.txt";

        try {
            RecordReader articlereader = new RecordReader(ARICLEFILE);
            ArticleList =  articlereader.readarticle();

            RecordReader imagereader = new RecordReader(IMAGEFILE);
            ImageList =  imagereader.readImage();

            RecordReader reader = new RecordReader(CATEGORYFILE);
            CategoryList =  reader.readCategory();

        } catch (IOException ex) {
            ex.printStackTrace();
        }



        System.out.println("*********************inside find******************************");
    }

    public void setImageList(ArrayList<ImageCard> imageList) {
        this.ImageList = imageList;
    }

    public void setCategoryList(ArrayList<CategoryCard> categoryList) {
        this.CategoryList = categoryList;
    }

    public void setArticleList(ArrayList<RandomArticleCard> articleList) {
        this.ArticleList = articleList;
    }

    // Recycle Setting
    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
//        cardAdapter = new CardAdapter(ImageList,CategoryList,ArticleList);

        Query query = new Query(ImageList, CategoryList, ArticleList);
//        Query query = new Query(imageList,categoryList,articleList);

//        System.out.println("Article   " + query.getArticleList().size() + "    &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        System.out.println("image     " + query.getImageList().size() + "      &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        System.out.println("category  " + query.getCategoryList().size() + "   &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("**************************************************************************************************");
        System.out.println("Article   " + ArticleList.size() + "    &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("image     " + ImageList.size() + "      &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("category  " + CategoryList.size() + "   &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        ArrayList<Cards> items = new ArrayList<>();
        for (int i = 0; i < query.getArticleList().size(); i++) {
            //adding articles to items of recyclerview
            Cards articlecards = new Cards(0, query.getArticleList().get(i));
            items.add(articlecards);
//
//            //adding images to items of recyclerview
//            Cards imagecards = new Cards(1, query.getImageList().get(i));
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + query.getImageList().get(1).getTitle() + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            items.add(imagecards);
//
//            //adding category to items of recycleview
//            Cards categorycards = new Cards(2, query.getCategoryList().get(i));
//            items.add(categorycards);
        }

        cardAdapter = new CardAdapter(items);
//        cardAdapter.updateCards();
        recyclerView.setAdapter(cardAdapter);
        System.out.println("******************INSIDE SETRECYCLE**********************");
    }

    //convert wiki text to plain text
    public String wikitextToText(String s) throws IOException {
        String result;

        WikiModel wikiModel = new WikiModel(
                "http://en.wikipedia.org/wiki/${image}", "http://en.wikipedia.org/wiki/${title}");
        result = wikiModel.render(new PlainTextConverter(), s);

        return result;
    }

    private void fetchArticledata(Continue c) {
        String articleUrl = c.getArticleUrl();
//        String imageUrl = c.getImageUrl();
//         ArrayList<ImageCard> Image = new ArrayList<>();
//         ArrayList<CategoryCard> Category = new ArrayList<>();
        ArrayList<RandomArticleCard> Article = new ArrayList<>();
        System.out.println("-------------------------inside fetch-------------------------------");
        JsonObjectRequest jsonArticleRequest = new JsonObjectRequest(Request.Method.GET, articleUrl, null, (Response.Listener<JSONObject>) response -> {
            try {
                // getting Query JsonObject
                JSONObject queryJson = response.getJSONObject("query");
                // getting pages jsonobject
                JSONObject pagesJson = queryJson.getJSONObject("pages");

                // getting all page from pages object
                Iterator iterator = pagesJson.keys();
                String key = null;
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    JSONObject page = pagesJson.getJSONObject(key);

                    // getting decription
                    JSONArray revisionArray = page.getJSONArray("revisions");
                    RandomArticleCard randomArticleCard = new RandomArticleCard(page.getString("pageid"), wikitextToText(page.getString("title"))
                            , wikitextToText(revisionArray.getJSONObject(0).getString("*")));

                    // adding data to array list of Articles
                    Article.add(randomArticleCard);
                }
//              // updating continue
                JSONObject continueJson = response.getJSONObject("continue");

                Continue.articleContinue articleContinue = new Continue.articleContinue(continueJson.getString("imcontinue"), continueJson.getString("imcontinue"),
                        continueJson.getString("continue"));
                c.setArticlecontinue(articleContinue);
                setArticleList(Article);
                fetchImagedata(c, ArticleList);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("unable to load article");
                Toast.makeText(MainActivity.this, "unable to load article", Toast.LENGTH_LONG).show();
            }
        }, (Response.ErrorListener) error -> {
            System.out.println("unable to load data");
            Toast.makeText(MainActivity.this, "unable to load data", Toast.LENGTH_LONG).show();
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonArticleRequest);


    }


// getting list of all cards
//updating recycler view
//        cardAdapter.updateCards();


    private void fetchImagedata(Continue c, ArrayList<RandomArticleCard> ArticleList) {
        String imageUrl = c.getImageUrl();
        ArrayList<ImageCard> Image = new ArrayList<>();
        // fetching data for image card
        JsonObjectRequest jsonImageRequest = new JsonObjectRequest(Request.Method.GET, imageUrl, null, (Response.Listener<JSONObject>) response -> {
            try {
                // getting Query JsonObject
                JSONObject queryJson = response.getJSONObject("query");
                // getting pages jsonobject
                JSONObject pagesJson = queryJson.getJSONObject("pages");

                // getting all page from pages object
                Iterator iterator = pagesJson.keys();
                String key = null;
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    JSONObject page = pagesJson.getJSONObject(key);
                    // array of image info
                    JSONArray imageinfoarray = page.getJSONArray("imageinfo");
                    // object of image info
                    Imageinfo imageinfo = new Imageinfo(wikitextToText(imageinfoarray.getJSONObject(0).getString("timestamp")),
                            wikitextToText(imageinfoarray.getJSONObject(0).getString("user")),
                            wikitextToText(imageinfoarray.getJSONObject(0).getString("url")));

                    ImageCard imageCard = new ImageCard(wikitextToText(page.getString("pageid")), wikitextToText(page.getString("title")), imageinfo);
                    // adding to list
                    Image.add(imageCard);
                }

                // updating continue
                JSONObject continueJson = response.getJSONObject("continue");

                Continue.imageContinue imageContinue = new Continue.imageContinue(continueJson.getString("continue"), continueJson.getString("continue"));
                c.setImagecontinue(imageContinue);

                setImageList(Image);
                fetchCategorydata(c, ArticleList, ImageList);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("unable to load data");
                Toast.makeText(MainActivity.this, "unable to load image", Toast.LENGTH_LONG).show();
            }
        }, (Response.ErrorListener) error -> {

        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonImageRequest);

    }

    private void fetchCategorydata(Continue c, ArrayList<RandomArticleCard> ArticleList, ArrayList<ImageCard> ImageList) {
        // fetching data for category card
//    for(int i = 0 ; i < Article.size(); i++) {
        String categoryUrl = c.getCategoryUrl();
        ArrayList<CategoryCard> Category = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, categoryUrl, null, (Response.Listener<JSONObject>) response -> {
            try {
                // getting Query JsonObject
                JSONObject queryJson = response.getJSONObject("query");
                // getting all category array
                ArrayList<String> categories = new ArrayList<>();
                JSONArray allCategories = queryJson.getJSONArray("allcategories");
                for (int j = 0; j < allCategories.length(); j++) {
                    categories.add(wikitextToText(allCategories.getJSONObject(j).getString("category")));
                }
                // category card
                CategoryCard categoryCard = new CategoryCard(categories);
                // adding to ist
                Category.add(categoryCard);

                // updating continue
                JSONObject continueJson = response.getJSONObject("continue");

                // calling sub class category continue
                Continue.categoryContinue categoryContinue = new Continue.categoryContinue(continueJson.getString("accontinue"), continueJson.getString("continue"));
                // updating category url
                c.setCategorycontinue(categoryContinue);

                setCategoryList(Category);
                setImageList(ImageList);
                setArticleList(ArticleList);
//                setRecyclerView(ImageList,Category,ArticleList);
//                setRecyclerView();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("unable to load data");
                Toast.makeText(MainActivity.this, "unable to load data", Toast.LENGTH_LONG).show();
            }
        }, (Response.ErrorListener) error -> {

        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        System.out.println("---------------------------------------------------end of fetch----------------------------------");

    }


    void fetchdata(Continue c) {
        try {
            //parameters
            //Context,Interface,Type(to indentify your responds),URL,parameter for your request

            //request 1
            new PostVolleyJsonRequest(MainActivity.this, MainActivity.this, "0", c.getArticleUrl(), null);

//            //request 2
            new PostVolleyJsonRequest(MainActivity.this, MainActivity.this, "1", c.getImageUrl(), null);
//
//            // request 3
            new PostVolleyJsonRequest(MainActivity.this, MainActivity.this, "2", c.getCategoryUrl(), null);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
        //Methods from Interface

//        @Override
//        public void onSuccessJson(JSONObject result, String type) {
//
//            //Based on the Type you send get the responds and parse it
//            switch (type) {
//                case "Submit":
//                    try {
//                        parseSubmit(result);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                case "AccessData":
//                    try {
//                        parseAccessData(result);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//

//        }


        // to update the recyclerview
//    private void updateRecyclerview(@NonNull Query query){
////        ARTICLE_CARD_VIEW = 0;
////        IMAGE_CARD_VIEW = 1;
////        CATEGORY_CARD_VIEW = 2;
//        for(int i = 0; i < query.getArticleList().size(); i++){
//            //adding articles to items of recyclerview
//            Cards articlecards = new Cards(ARTICLE_CARD_VIEW,query.getArticleList().get(i));
//            items.add(articlecards);
//
//            //adding images to items of recyclerview
//            Cards imagecards = new Cards(IMAGE_CARD_VIEW,query.getImageList().get(i));
//            items.add(imagecards);
//
//            //adding category to items of recycleview
//            Cards categorycards = new Cards(CATEGORY_CARD_VIEW,query.getCategoryList().get(i));
//            items.add(categorycards);
//        }
//
//
//        // updating the recycler view
//        cardAdapter.updateCards(items);
//
//    }



    @Override
    public void onSuccessJson(JSONObject result, String type) {
        //Based on the Type you send get the responds and parse it
            switch (type) {
                case "0":
                    try {
                        System.out.println("-------------------------inside fetch-------------------------------");

                        ArrayList<RandomArticleCard> Article = new ArrayList<>();

                        JSONObject queryJson = result.getJSONObject("query");
                        // getting pages jsonobject
                        JSONObject pagesJson = queryJson.getJSONObject("pages");

                        // getting all page from pages object
                        Iterator iterator = pagesJson.keys();
                        String key = null;
                        while (iterator.hasNext()) {
                            key = (String) iterator.next();
                            JSONObject page = pagesJson.getJSONObject(key);

                            // getting decription
                            JSONArray revisionArray = page.getJSONArray("revisions");
                            RandomArticleCard randomArticleCard = new RandomArticleCard(page.getString("pageid"), wikitextToText(page.getString("title"))
                                    , wikitextToText(revisionArray.getJSONObject(0).getString("*")));

                            // adding data to array list of Articles
                            Article.add(randomArticleCard);
                        }
//              // updating continue
//                        JSONObject continueJson = result.getJSONObject("continue");
//
//                        Continue.articleContinue articleContinue = new Continue.articleContinue(continueJson.getString("imcontinue"), continueJson.getString("imcontinue"),
//                                continueJson.getString("continue"));
//                        mContinue.setArticlecontinue(articleContinue);

//                        try {
                            RecordWriter writer = new RecordWriter(ARICLEFILE);

                            for (RandomArticleCard card : Article) {
                                writer.writeArticle(card);
                            }

                            writer.save();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }

//                        setArticleList(Article);
//                        setRecyclerView();


                    } catch (Exception e) {


                        e.printStackTrace();
                    }
                    break;

                case "1":
                    try {
                        ArrayList<ImageCard> image = new ArrayList<>();

                        // getting Query JsonObject
                        JSONObject queryJson = result.getJSONObject("query");
                        // getting pages jsonobject
                        JSONObject pagesJson = queryJson.getJSONObject("pages");

                        // getting all page from pages object
                        Iterator iterator = pagesJson.keys();
                        String key = null;
                        while (iterator.hasNext()) {
                            key = (String) iterator.next();
                            JSONObject page = pagesJson.getJSONObject(key);
                            // array of image info
                            JSONArray imageinfoarray = page.getJSONArray("imageinfo");
                            // object of image info
                            Imageinfo imageinfo = new Imageinfo(wikitextToText(imageinfoarray.getJSONObject(0).getString("timestamp")),
                                    wikitextToText(imageinfoarray.getJSONObject(0).getString("user")),
                                    wikitextToText(imageinfoarray.getJSONObject(0).getString("url")));

                            ImageCard imageCard = new ImageCard(wikitextToText(page.getString("pageid")), wikitextToText(page.getString("title")), imageinfo);
                            // adding to list
                            image.add(imageCard);
                        }

//                        // updating continue
//                        JSONObject continueJson = result.getJSONObject("continue");
//
//                        Continue.imageContinue imageContinue = new Continue.imageContinue(continueJson.getString("continue"), continueJson.getString("continue"));
//                        c.setImagecontinue(imageContinue);



//                        try {
                        RecordWriter writer = new RecordWriter(IMAGEFILE);

                        for (ImageCard card : image) {
                            writer.writeImage(card);
                        }

                        writer.save();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }


//                        setImageList(image);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "2":
                    try {
                        ArrayList<CategoryCard> category = new ArrayList<>();

                        // getting Query JsonObject
                        JSONObject queryJson = result.getJSONObject("query");
                        // getting all category array
                        ArrayList<String> categories = new ArrayList<>();
                        JSONArray allCategories = queryJson.getJSONArray("allcategories");
                        for (int j = 0; j < allCategories.length(); j++) {
                            categories.add(wikitextToText(allCategories.getJSONObject(j).getString("category")));
                        }
                        // category card
                        CategoryCard categoryCard = new CategoryCard(categories);
                        // adding to ist
                        category.add(categoryCard);

                        // updating continue
//                        JSONObject continueJson = result.getJSONObject("continue");

//                        // calling sub class category continue
//                        Continue.categoryContinue categoryContinue = new Continue.categoryContinue(continueJson.getString("accontinue"), continueJson.getString("continue"));
//                        // updating category url
//                        c.setCategorycontinue(categoryContinue);


//                        try {
                        RecordWriter writer = new RecordWriter(CATEGORYFILE);

                        for (CategoryCard card : CategoryList) {
                            writer.writeCategory(card);
                        }

                        writer.save();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }


//                        setCategoryList(category);
                        System.out.println("-------------------------end fetch-------------------------------");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
    }

    @Override
    public void onFailureJson(int responseCode, String responseMessage) {

    }

}