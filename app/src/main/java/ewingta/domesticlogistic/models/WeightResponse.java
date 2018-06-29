package ewingta.domesticlogistic.models;

import java.util.List;

public class WeightResponse {

    private String status;

    private List<Weight> productweightlist;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public List<Weight> getProductweightlist ()
    {
        return productweightlist;
    }

    public void setProductweightlist (List<Weight> productweightlist)
    {
        this.productweightlist = productweightlist;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", productweightlist = "+productweightlist+"]";
    }
}
