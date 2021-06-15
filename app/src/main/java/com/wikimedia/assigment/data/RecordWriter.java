package com.wikimedia.assigment.data;

import com.wikimedia.assigment.cards.CategoryCard;
import com.wikimedia.assigment.cards.ImageCard;
import com.wikimedia.assigment.cards.RandomArticleCard;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecordWriter {

    DataOutputStream dataOutput;

    public RecordWriter(String outputFile) throws IOException {
        dataOutput = new DataOutputStream(new FileOutputStream(outputFile ));
    }

    public void writeArticle(RandomArticleCard article) throws IOException {
        dataOutput.writeUTF(article.getPageid());
        dataOutput.writeBoolean(true);
        dataOutput.writeUTF(article.getTitle());
        dataOutput.writeBoolean(true);
        dataOutput.writeUTF(article.getRevisions());
    }

    public void writeImage(ImageCard card) throws IOException {
        dataOutput.writeUTF(card.getPageid());
        dataOutput.writeBoolean(true);
        dataOutput.writeUTF(card.getTitle());
        dataOutput.writeBoolean(true);
        dataOutput.writeUTF(card.getImageinfo().getTimestamp());
        dataOutput.writeBoolean(true);
        dataOutput.writeUTF(card.getImageinfo().getUser());
        dataOutput.writeBoolean(true);
        dataOutput.writeUTF(card.getImageinfo().getImageUrl());
    }

    public void writeCategory(CategoryCard card) throws IOException {
        for(int i = 0 ; i < card.getCategory().size(); i++){
            dataOutput.writeUTF(card.getCategory().get(i));
            dataOutput.writeBoolean(true);
        }
    }



    public void save() throws IOException {
        dataOutput.close();
    }
}
