package ewingta.domesticlogistic.models;

import java.util.List;

public class ValueResponse {
    private String status;

    private List<Value> valueslist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Value> getValueslist() {
        return valueslist;
    }

    public void setValueslist(List<Value> valueslist) {
        this.valueslist = valueslist;
    }

    @Override
    public String toString() {
        return "ValueResponse{" +
                "status='" + status + '\'' +
                ", valueslist=" + valueslist +
                '}';
    }
}
