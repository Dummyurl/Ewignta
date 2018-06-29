package ewingta.domesticlogistic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.models.Weight;

public class WeightAdapter extends BaseAdapter {

    private List<Weight> weights;
    private Context context;

    public WeightAdapter(Context context, List<Weight> weight) {
        this.weights = new ArrayList<>();
        this.weights.addAll(weight);
        this.context = context;
    }

    @Override
    public int getCount() {
        return weights.size();
    }

    @Override
    public Weight getItem(int position) {
        return weights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(weights.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Weight value = getItem(position);
        WeightViewHolder valueVH;
        View rowView = convertView;

        if (rowView == null) {

            valueVH = new WeightViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner, null, false);

                valueVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(valueVH);
            }
        } else {
            valueVH = (WeightViewHolder) rowView.getTag();
        }

        if (value != null) {
            valueVH.tv_title.setText(String.valueOf(value.getTitle()));
        }

        return rowView;
    }

    private class WeightViewHolder {
        TextView tv_title;
    }
}