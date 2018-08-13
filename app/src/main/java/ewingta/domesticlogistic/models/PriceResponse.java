package ewingta.domesticlogistic.models;

import java.util.List;

public class PriceResponse {
    private String price;
    private String status;


    private List<Weight> productweightlist;


    public List<Weight> getProductweightlist ()
    {
        return productweightlist;
    }

    public void setProductweightlist (List<Weight> productweightlist)
    {
        this.productweightlist = productweightlist;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PriceResponse{" +
                "price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
