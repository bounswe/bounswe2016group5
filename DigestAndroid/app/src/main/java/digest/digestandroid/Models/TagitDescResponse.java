package digest.digestandroid.Models;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Burki on 21.12.2016.
 */

public class TagitDescResponse {
    Map map;

    public TagitDescResponse() {
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public class Map{
        Suggestions suggestions;

        public Map() {
        }

        public Suggestions getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(Suggestions suggestions) {
            this.suggestions = suggestions;
        }

        public class Suggestions{
            ArrayList<MyType> myArrayList;

            public Suggestions() {
            }

            public ArrayList<MyType> getMyArrayList() {
                return myArrayList;
            }

            public void setMyArrayList(ArrayList<MyType> myArrayList) {
                this.myArrayList = myArrayList;
            }

            public class MyType{
                java.util.Map<String , MyType2> map;

                public MyType() {
                }

                public java.util.Map<String, MyType2> getMap() {
                    return map;
                }

                public void setMap(java.util.Map<String, MyType2> map) {
                    this.map = map;
                }

                public class MyType2{
                    ArrayList<String> myArrayList;

                    public MyType2() {
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
    }
}
