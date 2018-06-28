package ewingta.domesticlogistic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.models.Service;
import ewingta.domesticlogistic.models.Value;

public class ValueAdapter extends BaseAdapter {

    private List<Value> values;
    private Context context;

    public ValueAdapter(Context context, List<Value> values) {
        this.values = new ArrayList<>();
        this.values.addAll(values);
        this.context = context;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Value getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return values.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Value value = getItem(position);
        ValueViewHolder valueVH;
        View rowView = convertView;

        if (rowView == null) {

            valueVH = new ValueViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner, null, false);

                valueVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(valueVH);
            }
        } else {
            valueVH = (ValueViewHolder) rowView.getTag();
        }

        if (value != null) {
            valueVH.tv_title.setText(String.valueOf(value.getTitle()));
        }

        return rowView;
    }

    private class ValueViewHolder {
        TextView tv_title;
    }
}
