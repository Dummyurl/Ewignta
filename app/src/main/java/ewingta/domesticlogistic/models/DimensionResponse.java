package ewingta.domesticlogistic.models;

import java.util.List;

public class DimensionResponse {
    private List<Dimension> dimentionslist;
    private String status;

    public List<Dimension> getDimentionslist() {
        return dimentionslist;
    }

    public void setDimentionslist(List<Dimension> dimentionslist) {
        this.dimentionslist = dimentionslist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
