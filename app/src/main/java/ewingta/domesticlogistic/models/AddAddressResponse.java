package ewingta.domesticlogistic.models;

import java.util.ArrayList;
import java.util.List;

public class AddAddressResponse {
    private String Statuss;
    private String status;
    private List<Address> addressid;

    public List<Address> getAddressid() {
        if (addressid == null) {
            addressid = new ArrayList<>();
        }

        return addressid;
    }

    public void setAddressid(List<Address> addressid) {
        this.addressid = addressid;
    }

    public String getStatuss() {
        return Statuss;
    }

    public void setStatuss(String statuss) {
        Statuss = statuss;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddAddressResponse{" +
                "Statuss='" + Statuss + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
