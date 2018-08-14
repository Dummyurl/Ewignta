package ewingta.domesticlogistic.models;

public class Weight {
    private String weight_key;
    private long id;
    private String weight_value;
    private String published;
    private String cat_name;

    public String getWeight_key ()
    {
        return weight_key;
    }
    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setWeight_key (String weight_key)
    {
        this.weight_key = weight_key;
    }

    public String getWeight_value ()
    {
        return weight_value;
    }

    public void setWeight_value (String weight_value)
    {
        this.weight_value = weight_value;
    }
    public String getCat_name() {
        return cat_name;
    }
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [weight_key = "+weight_key+", weight_value = "+weight_value+"]";
    }
}
