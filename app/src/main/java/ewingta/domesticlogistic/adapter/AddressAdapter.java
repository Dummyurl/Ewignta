package ewingta.domesticlogistic.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.models.Address;
import ewingta.domesticlogistic.models.RequestResponse;
import ewingta.domesticlogistic.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.reterofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressAdapter extends CommonRecyclerAdapter<Address> {

    public Context context;

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);

        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        AddressViewHolder viewHolder = (AddressViewHolder) holder;
        viewHolder.bindData(position);
    }

    private class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_short_name, tv_address;
        private ImageView iv_delete;

        private AddressViewHolder(View view) {
            super(view);
            tv_short_name = view.findViewById(R.id.tv_short_name);
            tv_address = view.findViewById(R.id.tv_address);
            iv_delete = view.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(this);
        }


        private void bindData(int position) {
            Address address = getItem(position);
            tv_short_name.setText(address.getShortname());
            tv_address.setText(address.getLocation());
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.delete_address)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            final int position = getAdapterPosition();

                            Address address = getItem(position);

                            RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                            service.deleteAddress(address.getUserid(), String.valueOf(address.getId())).enqueue(new Callback<RequestResponse>() {
                                @Override
                                public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                                    if (response.body() != null && response.isSuccessful()) {
                                        removeItem(position);
                                        Toast.makeText(context, R.string.address_deleted_successfully, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RequestResponse> call, Throwable t) {
                                    Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).show();
        }
    }
}