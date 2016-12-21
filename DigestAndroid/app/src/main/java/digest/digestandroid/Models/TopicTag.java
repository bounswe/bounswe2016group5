package digest.digestandroid.Models;

/**
 * Created by oykuyilmaz on 16/11/2016.
 */

public class TopicTag {

    private String tag;


    public TopicTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TopicTag{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
