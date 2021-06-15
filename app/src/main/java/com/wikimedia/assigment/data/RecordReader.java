package com.wikimedia.assigment.data;

import com.wikimedia.assigment.cards.CategoryCard;
import com.wikimedia.assigment.cards.ImageCard;
import com.wikimedia.assigment.cards.RandomArticleCard;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordReader {

    DataInputStream dataInput;

    public RecordReader(String inputFile) throws IOException {
        dataInput = new DataInputStream(new FileInputStream(inputFile));
    }

    public ArrayList<RandomArticleCard> readarticle() throws IOException {
        ArrayList<RandomArticleCard> list = new ArrayList<>();

        try {
            while (true) {

                boolean seprator;
                String pageid = dataInput.readUTF();
                seprator = dataInput.readBoolean();
                String title = dataInput.readUTF();
                seprator = dataInput.readBoolean();
                String revision = dataInput.readUTF();

                RandomArticleCard student = new RandomArticleCard(pageid, title, revision);
                list.add(student);
            }
        } catch (EOFException ex) {
            // reach end of file
        }

        dataInput.close();

        return list;
    }

    public ArrayList<ImageCard> readImage() throws IOException {
        ArrayList<ImageCard> list = new ArrayList<>();

        try {
            while (true) {
//                dataOutput.writeUTF(card.getPageid());
//                dataOutput.writeBoolean(true);
//                dataOutput.writeUTF(card.getTitle());
//                dataOutput.writeBoolean(true);
//                dataOutput.writeUTF(card.getImageinfo().getTimestamp());
//                dataOutput.writeBoolean(true);
//                dataOutput.writeUTF(card.getImageinfo().getUser());
//                dataOutput.writeBoolean(true);
//                dataOutput.writeUTF(card.getImageinfo().getImageUrl());

                boolean seprator;
                String pageid = dataInput.readUTF();
                seprator = dataInput.readBoolean();
                String title = dataInput.readUTF();
                seprator = dataInput.readBoolean();
                String time = dataInput.readUTF();
                seprator = dataInput.readBoolean();
                String user = dataInput.readUTF();
                seprator = dataInput.readBoolean();
                String imageurl = dataInput.readUTF();

                ImageCard student = new ImageCard(pageid, title, new Imageinfo(time,user,imageurl));
                list.add(student);
            }
        } catch (EOFException ex) {
            // reach end of file
        }

        dataInput.close();

        return list;
    }

    public ArrayList<CategoryCard> readCategory() throws IOException {
        ArrayList<CategoryCard> list = new ArrayList<>();

        ArrayList<String> category = new ArrayList<>();
        try {
            while (true) {
//                for(int i = 0 ; i < card.getCategory().size(); i++){
//                    dataOutput.writeUTF(card.getCategory().get(i));
//                    dataOutput.writeBoolean(true);
//                }

                boolean seprator;
                String c;
                for(int i = 0 ; i <10 ; i++){
                    c = dataInput.readUTF();
                    seprator = dataInput.readBoolean();
                    category.add(c);
                }



                CategoryCard student = new CategoryCard(category);
                list.add(student);
            }
        } catch (EOFException ex) {
            // reach end of file
        }

        dataInput.close();

        return list;
    }
}
