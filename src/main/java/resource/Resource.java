package resource;

/**
 * Highly sensitive THREAD linked resource<br>
 * It can be a security context , a transaction context , a database transaction context
 * or any kind of specific thread resource needed for a certain workflow
 */
public class Resource {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}