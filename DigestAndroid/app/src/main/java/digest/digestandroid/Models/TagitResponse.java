package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by Burki on 21.12.2016.
 */

public class TagitResponse {
    String name = null;
    String entity = null;
    boolean selected = false;
    Map map;

    public TagitResponse(String name, boolean selected, String entity) {
        this.name = name;
        this.selected = selected;
        this.entity = entity;
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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public class Map{
        Entities entities;

        public Map() {
        }

        public Entities getEntities() {
            return entities;
        }

        public void setEntities(Entities entities) {
            this.entities = entities;
        }

        public class Entities{
            ArrayList<String> myArrayList; //for entities

            public Entities() {
            }

            public ArrayList<String> getMyArrayList() {
                return myArrayList;
            }

            public void setMyArrayList(ArrayList<String> myArrayList) {
                this.myArrayList = myArrayList;
            }
        }
    }
}




// {"map":{"entities":{"myArrayList":["canada","acronym for personal computer","personal computer","bq"]}}}