package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by sahin on 17.12.2016.
 */

public class Channel {

    private int id;
    private int uid;
    private String name;
    private int status;


    public Channel(){}
    public Channel( int theId , int theuserid , String thename,int thestatus){ status = thestatus; name =thename; id =theId; uid =theuserid;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Channel{" +
                ", id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

}
