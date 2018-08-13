package ewingta.domesticlogistic.models;

public class Weight {
    private String weight_key;

    private String weight_value;

    public String getWeight_key ()
    {
        return weight_key;
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

    @Override
    public String toString()
    {
        return "ClassPojo [weight_key = "+weight_key+", weight_value = "+weight_value+"]";
    }
}
