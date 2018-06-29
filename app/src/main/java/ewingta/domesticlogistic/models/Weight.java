package ewingta.domesticlogistic.models;

public class Weight {
    private String service_id;

    private String id;

    private String title;

    private String published;

    public String getService_id ()
    {
        return service_id;
    }

    public void setService_id (String service_id)
    {
        this.service_id = service_id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getPublished ()
    {
        return published;
    }

    public void setPublished (String published)
    {
        this.published = published;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [service_id = "+service_id+", id = "+id+", title = "+title+", published = "+published+"]";
    }

}
