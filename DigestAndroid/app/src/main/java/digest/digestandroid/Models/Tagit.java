package digest.digestandroid.Models;

/**
 * Created by Burki on 19.12.2016.
 */

public class Tagit {
    String name = null;
    boolean selected = false;

    public Tagit(String code, String name, boolean selected) {
        super();
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
