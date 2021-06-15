package com.wikimedia.assigment;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wikimedia.assigment.cards.Cards;
import com.wikimedia.assigment.cards.CategoryCard;
import com.wikimedia.assigment.cards.ImageCard;
import com.wikimedia.assigment.cards.RandomArticleCard;
import com.wikimedia.assigment.data.Query;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Cards> item = new ArrayList<>();
//    private ArrayList<ImageCard> ImageList  = new ArrayList<>();
//    private ArrayList<CategoryCard> CategoryList = new ArrayList<>();
//    private ArrayList<RandomArticleCard> ArticleList = new ArrayList<>();


//    public CardAdapter(ArrayList<ImageCard> imageList, ArrayList<CategoryCard> categoryList, ArrayList<RandomArticleCard> articleList) {
//        this.ImageList = imageList;
//        this.CategoryList = categoryList;
//        this.ArticleList = articleList;
//    }

    public CardAdapter(ArrayList<Cards> item) {
        this.item = item;
    }


    public CardAdapter() {
        // empty constructor
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("++++++++++++++++++++++inside oncreate view holder+++++++++++++++++++++++++");

        if(viewType == 0){
            //random Article
            System.out.println("///////////////////////////////////////////////////////////////////////////////////////////");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_article, parent,false);
            return new articleViewholder(view);
        } else if (viewType == 1){
            System.out.println("*******************************************************************************************");
            // image card
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent,false);
            return new imageViewholder(view);
        } else {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            // category list
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent,false);
            return new categoryViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        System.out.println("++++++++++++++++++++++onBind++++++++++++++++++++");

        if (getItemViewType(position) == 0){
            // binding random artical data
            RandomArticleCard card = (RandomArticleCard) item.get(position).getObject();
            ((articleViewholder) holder).setArticledata(card);
        } else if(getItemViewType(position) == 1){
            // binding image view type
            ImageCard card = (ImageCard) item.get(position).getObject();
            ((imageViewholder) holder).setImageData(card, (imageViewholder) holder,position);
        } else {
            CategoryCard card = (CategoryCard) item.get(position).getObject();
            ((categoryViewholder) holder).setCategorydata(card);
        }

    }

    @Override
    public int getItemCount() {
        System.out.println("+++++++++++++++++++" +item.size() + "++++++++++++++++++++++++++");
        return item.size();
    }

    @Override
    public int getItemViewType(int position) {
        return item.get(position).getViewType();
    }


    // to update and populate the recycler View
    public void  updateCards(){
//        Query query = new Query(ImageList,CategoryList,ArticleList);
//
//        ArrayList<Cards> items = new ArrayList<>();
//        for(int i = 0; i < query.getArticleList().size(); i++){
//            //adding articles to items of recyclerview
//            Cards articlecards = new Cards(0,query.getArticleList().get(i));
//            items.add(articlecards);
//
//            //adding images to items of recyclerview
//            Cards imagecards = new Cards(1,query.getImageList().get(i));
//            items.add(imagecards);
//
//            //adding category to items of recycleview
//            Cards categorycards = new Cards(2,query.getCategoryList().get(i));
//            items.add(categorycards);
//        }


//        // updating the recycler view
//        cardAdapter.updateCards(items);

//        item.addAll(items);

        //to re call the above three function i.e. to update the adapter we call bellow method which re call the adapter with new values
        notifyDataSetChanged();
    }




    // view holder classes
//image card holder
    static class imageViewholder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView titleImage , userImage, timeImage;
        private Button downloadImage;
        private ProgressBar progressBar;

        public imageViewholder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            titleImage = (TextView) itemView.findViewById(R.id.image_title);
            userImage = (TextView) itemView.findViewById(R.id.image_user);
            timeImage = (TextView) itemView.findViewById(R.id.image_time);
            downloadImage = (Button) itemView.findViewById(R.id.image_download);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);

        }

        public void setImageData(ImageCard card , RecyclerView.ViewHolder holder, int position){
            titleImage.setText(card.getTitle());

            Glide.with(holder.itemView.getContext()).load(card.getImageinfo().getImageUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(image);

            userImage.setText(card.getImageinfo().getUser());
            timeImage.setText(card.getImageinfo().getTimestamp());
        }
    }

    //Random Article card holder
    static class articleViewholder extends RecyclerView.ViewHolder{
        private TextView titleArticle , article;
        private Button downloadArticle;



        public articleViewholder(@NonNull View itemView) {
            super(itemView);
            titleArticle = (TextView) itemView.findViewById(R.id.article_title);
            article = (TextView) itemView.findViewById(R.id.article);
            downloadArticle = (Button) itemView.findViewById(R.id.article_download);
        }


        public void setArticledata(RandomArticleCard card){
            titleArticle.setText(card.getTitle());
            article.setText(card.getRevisions());
        }
    }

    //Category card holder
    static class categoryViewholder extends RecyclerView.ViewHolder{

        private TextView category1,category2,category3,category4,category5,category6,category7,category8,category9,category10;
        private Button downloadCategory;

        public categoryViewholder(@NonNull View itemView) {
            super(itemView);
            downloadCategory = (Button) itemView.findViewById(R.id.article_download);
            category1 = (TextView) itemView.findViewById(R.id.category1);
            category2 = (TextView) itemView.findViewById(R.id.category2);
            category3 = (TextView) itemView.findViewById(R.id.category3);
            category4 = (TextView) itemView.findViewById(R.id.category4);
            category5 = (TextView) itemView.findViewById(R.id.category5);
            category6 = (TextView) itemView.findViewById(R.id.category6);
            category7 = (TextView) itemView.findViewById(R.id.category7);
            category8 = (TextView) itemView.findViewById(R.id.category8);
            category9 = (TextView) itemView.findViewById(R.id.category9);
            category10 = (TextView) itemView.findViewById(R.id.category10);
        }

        public void setCategorydata(CategoryCard card){
            category1.setText(card.getCategory().get(0));
            category2.setText(card.getCategory().get(1));
            category3.setText(card.getCategory().get(2));
            category4.setText(card.getCategory().get(3));
            category5.setText(card.getCategory().get(4));
            category6.setText(card.getCategory().get(5));
            category7.setText(card.getCategory().get(6));
            category8.setText(card.getCategory().get(7));
            category9.setText(card.getCategory().get(8));
            category10.setText(card.getCategory().get(9));
        }

    }

/*   private void addList() {

        View itemView = getLayoutInflater().inflate(R.layout.productrecyclerview, null, false);


        Spinner particular = (Spinner) itemView.findViewById(R.id.Lparticulars);
        EditText hsncode = (EditText) itemView.findViewById(R.id.Lhsncode);
        EditText rate = (EditText) itemView.findViewById(R.id.Lrate);
        EditText quantity = (EditText) itemView.findViewById(R.id.Lquantity);
        ImageView remove = (ImageView) itemView.findViewById(R.id.remove);

        //spinner addapter
//        ArrayAdapter<CharSequence> padapter = ArrayAdapter.createFromResource(Bill.this,R.array.praticular_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> padapter = ArrayAdapter.createFromResource(Bill.this,R.array.praticular_array, R.layout.custom_spinner_item);
//        padapter.setDropDownViewResource(R.layout.dropdown);
        padapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        particular.setAdapter(padapter);
////Setting Errors if the hsncode or rate or Quantity is empty
//        if (hsncode.getText().toString().isEmpty()){
//            hsncode.setError("HSN Code is Empty");
//        }
//        if( rate.getText().toString().isEmpty()){
//            rate.setError("Rate is Empty");
//        }
//        if (quantity.getText().toString().isEmpty()){
//            quantity.setError("Quantity is Empty");
//        }

        // remove button function
        remove.setOnClickListener((v) -> {
            removelist(itemView);
        });

        layoutlist.addView(itemView);
    }*/
}
