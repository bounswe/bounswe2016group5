package digest.digestandroid;

/**
 * Created by Burki on 20.11.2016.
 */
public class Cache {
    private static Cache ourInstance = new Cache();

    public static Cache getInstance() {
        return ourInstance;
    }

    private Cache() {
    }
}
