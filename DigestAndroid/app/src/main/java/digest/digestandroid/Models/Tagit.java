package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by Burki on 19.12.2016.
 */

public class Tagit {
    String name = null;
    String entity = null;
    boolean selected = false;
    ArrayList<String> myArrayList; //for entities

    public Tagit(String entity, String name, boolean selected) {
        super();
        this.entity = entity;
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<String> getMyArrayList() {
        return myArrayList;
    }

    public void setMyArrayList(ArrayList<String> myArrayList) {
        this.myArrayList = myArrayList;
    }
}
