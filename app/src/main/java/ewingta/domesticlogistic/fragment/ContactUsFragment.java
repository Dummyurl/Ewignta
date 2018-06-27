package ewingta.domesticlogistic.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.adapter.OrdersAdapter;
import ewingta.domesticlogistic.models.LoginResponse;
import ewingta.domesticlogistic.models.OrderResponse;
import ewingta.domesticlogistic.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.reterofit.RetrofitService;
import ewingta.domesticlogistic.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            view.findViewById(R.id.tv_phone_number).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + getString(R.string.phone_number)));
                    startActivity(intent);
                }
            });

            view.findViewById(R.id.tv_email).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",getString(R.string.email_address), null));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });

        } catch (Exception e) {
            showErrorToast(R.string.error_message);
        }
    }
}
