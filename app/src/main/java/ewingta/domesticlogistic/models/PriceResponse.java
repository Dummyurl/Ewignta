package ewingta.domesticlogistic.models;

import java.util.List;

public class PriceResponse {
    private String price;
    private String status;
    private String baseprice;
    private String Gst;
    private String ExtraweightCharges;

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getBaseprice ()
    {
        return baseprice;
    }

    public void setBaseprice (String baseprice)
    {
        this.baseprice = baseprice;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getGst ()
    {
        return Gst;
    }

    public void setGst (String Gst)
    {
        this.Gst = Gst;
    }

    public String getExtraweightCharges ()
    {
        return ExtraweightCharges;
    }

    public void setExtraweightCharges (String ExtraweightCharges)
    {
        this.ExtraweightCharges = ExtraweightCharges;
    }

    @Override
    public String toString() {
        return "PriceResponse{" +
                "price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
