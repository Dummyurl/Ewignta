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
import ewingta.domesticlogistic.models.Dimension;
import ewingta.domesticlogistic.models.Value;

public class DimensionAdapter extends BaseAdapter {

    private List<Dimension> dimensions;
    private Context context;

    public DimensionAdapter(Context context, List<Dimension> dimensions) {
        this.dimensions = new ArrayList<>();
        this.dimensions.addAll(dimensions);
        this.context = context;
    }

    @Override
    public int getCount() {
        return dimensions.size();
    }

    @Override
    public Dimension getItem(int position) {
        return dimensions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dimensions.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Dimension value = getItem(position);
        DimensionViewHolder valueVH;
        View rowView = convertView;

        if (rowView == null) {

            valueVH = new DimensionViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner, null, false);

                valueVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(valueVH);
            }
        } else {
            valueVH = (DimensionViewHolder) rowView.getTag();
        }

        if (value != null) {
            valueVH.tv_title.setText(String.valueOf(value.getTitle()));
        }

        return rowView;
    }

    private class DimensionViewHolder {
        TextView tv_title;
    }
}

