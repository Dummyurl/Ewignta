package ewingta.domesticlogistic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import ewingta.domesticlogistic.MainActivity;
import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.adapter.DimensionAdapter;
import ewingta.domesticlogistic.adapter.ServiceAdapter;
import ewingta.domesticlogistic.adapter.ValueAdapter;
import ewingta.domesticlogistic.adapter.WeightAdapter;
import ewingta.domesticlogistic.models.DimensionResponse;
import ewingta.domesticlogistic.models.PriceResponse;
import ewingta.domesticlogistic.models.ServiceResponse;
import ewingta.domesticlogistic.models.Value;
import ewingta.domesticlogistic.models.ValueResponse;
import ewingta.domesticlogistic.models.Weight;
import ewingta.domesticlogistic.models.WeightResponse;
import ewingta.domesticlogistic.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.reterofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderFragment extends BaseFragment implements View.OnClickListener {

    private static final String ORDER_ID = "ORDER_ID";
    private RelativeLayout rl_progress;
    private String orderId;
    private AppCompatButton btn_submit,btn_calculate;
    private ProgressBar progress_submit,progress_calculate;
    private Spinner spinner_values, spinner_dimensions ;
    private RetrofitService service;
    private TextView tv_price,tv_baseprice,tv_extra_charges,tv_gst;
    private EditText spinner_weights;

    public static ConfirmOrderFragment newInstance(String orderId) {
        ConfirmOrderFragment confirmOrderFragment = new ConfirmOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);
        confirmOrderFragment.setArguments(bundle);
        return confirmOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            orderId = getArguments().getString(ORDER_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            service = RetrofitInstance.createService(RetrofitService.class);
            rl_progress = view.findViewById(R.id.rl_progress);
            rl_progress.setVisibility(View.VISIBLE);

            btn_submit = view.findViewById(R.id.btn_submit);
            btn_submit.setOnClickListener(this);
            btn_calculate = view.findViewById(R.id.btn_calculate);
            btn_calculate.setOnClickListener(this);
            progress_submit = view.findViewById(R.id.progress_submit);
            spinner_values = view.findViewById(R.id.spinner_values);
            spinner_dimensions = view.findViewById(R.id.spinner_dimensions);
            spinner_weights = view.findViewById(R.id.spinner_weights);

            tv_price = view.findViewById(R.id.tv_price);
            tv_baseprice = view.findViewById(R.id.tv_baseprice);
            tv_extra_charges = view.findViewById(R.id.tv_extra_charges);
            tv_gst = view.findViewById(R.id.tv_gst);



            service.getValues().enqueue(new Callback<ValueResponse>() {
                @Override
                public void onResponse(Call<ValueResponse> call, Response<ValueResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ValueResponse vr = response.body();

                        if (vr.getStatus().equals("ok")) {
                            ValueAdapter valueAdapter = new ValueAdapter(getContext(), vr.getValueslist());
                            spinner_values.setAdapter(valueAdapter);
                        }
                    }

                    rl_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ValueResponse> call, Throwable t) {
                    rl_progress.setVisibility(View.GONE);
                    showErrorToast(R.string.error_message);
                }
            });

            service.getDimensions().enqueue(new Callback<DimensionResponse>() {
                @Override
                public void onResponse(Call<DimensionResponse> call, Response<DimensionResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        DimensionResponse dimensionResponse = response.body();

                        if (dimensionResponse.getStatus().equals("ok")) {
                            DimensionAdapter dimensionAdapter = new DimensionAdapter(getContext(), dimensionResponse.getDimentionslist());
                            spinner_dimensions.setAdapter(dimensionAdapter);
                        }
                    }

                    rl_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<DimensionResponse> call, Throwable t) {
                    rl_progress.setVisibility(View.GONE);
                    showErrorToast(R.string.error_message);
                }
            });
            service.getWeights().enqueue(new Callback<WeightResponse>() {
                @Override
                public void onResponse(Call<WeightResponse> call, Response<WeightResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        WeightResponse weightResponse = response.body();

                        if (weightResponse.getStatus().equals("ok")) {
                            WeightAdapter weightAdapter = new WeightAdapter(getContext(), weightResponse.getProductweightlist());
                            spinner_weights.setText((CharSequence) weightAdapter);
                        }
                    }

                    rl_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<WeightResponse> call, Throwable t) {
                    rl_progress.setVisibility(View.GONE);
                    showErrorToast(R.string.error_message);
                }
            });
        } catch (Exception e) {
            showErrorToast(R.string.error_message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Activity activity = getActivity();

                if (activity != null && !activity.isFinishing()) {
                    MainActivity mainActivity = (MainActivity) activity;
                    mainActivity.showOrders();
                }
                break;

            case R.id.btn_calculate:

                final String weight = spinner_weights.getText().toString().trim();

                if (spinner_weights == null) {
                    showErrorToast(R.string.select_what_do_you_want_to_deliver);
                }
                else {
                    service.getPrice(orderId, weight).enqueue(new Callback<PriceResponse>() {
                        @Override
                        public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                PriceResponse priceResponse = response.body();

                                if (priceResponse.getStatus().equals("ok")) {
                                    tv_price.setText(priceResponse.getPrice());
                                    tv_baseprice.setText(priceResponse.getBaseprice());
                                    tv_extra_charges.setText(priceResponse.getExtraweightCharges());
                                    tv_gst.setText(priceResponse.getGst());
                                    spinner_weights.setText("");

                                } else {
                                    showErrorToast(R.string.error_message);
                                }

                            } else {
                                spinner_weights.setText("-");
                            }

                            rl_progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<PriceResponse> call, Throwable t) {
                            rl_progress.setVisibility(View.GONE);
                            showErrorToast(R.string.error_message);
                        }
                    });
                }
        }
    }
}
