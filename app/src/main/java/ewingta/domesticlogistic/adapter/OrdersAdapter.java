package ewingta.domesticlogistic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.models.Order;

public class OrdersAdapter extends CommonRecyclerAdapter<Order> {
    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder viewHolder = (OrderViewHolder) holder;
        viewHolder.bindData(position);
    }

    private class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_order_id, tv_order_type, tv_customer_name, tv_customer_phone, tv_status;

        private OrderViewHolder(View view) {
            super(view);
            tv_order_id = view.findViewById(R.id.tv_order_id);
            tv_order_type = view.findViewById(R.id.tv_order_type);
            tv_customer_name = view.findViewById(R.id.tv_customer_name);
            tv_customer_phone = view.findViewById(R.id.tv_customer_phone);
            tv_status = view.findViewById(R.id.tv_status);
            tv_status.setOnClickListener(this);
        }


        private void bindData(int position) {
            Order order = getItem(position);

            tv_order_id.setText(String.format("#%s", order.getOrder_num()));
            tv_status.setText(order.getStatus());
            tv_customer_name.setText(order.getCustomer_name());
            tv_customer_phone.setText(order.getCustomer_phone());
            tv_order_type.setText(order.getTime());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
