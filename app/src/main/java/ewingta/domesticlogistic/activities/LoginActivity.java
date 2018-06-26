package ewingta.domesticlogistic.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import ewingta.domesticlogistic.MainActivity;
import ewingta.domesticlogistic.R;
import ewingta.domesticlogistic.models.City;
import ewingta.domesticlogistic.models.LoginResponse;
import ewingta.domesticlogistic.models.RequestResponse;
import ewingta.domesticlogistic.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.reterofit.RetrofitService;
import ewingta.domesticlogistic.utils.PreferenceUtil;
import ewingta.domesticlogistic.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone_number, et_password;
    private Button btn_login;
    private ProgressBar progress_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_phone_number = findViewById(R.id.et_mobile_number);
        et_password = findViewById(R.id.et_password);

        findViewById(R.id.tv_register).setOnClickListener(this);
        progress_login = findViewById(R.id.progress_login);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        findViewById(R.id.tv_forgot_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegistrationActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.btn_login:
                String phoneNumber = et_phone_number.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
                    showErrorToast(R.string.valid_mobile_number);
                } else if (password.isEmpty()) {
                    showErrorToast(R.string.valid_password);
                } else {
                    progress_login.setVisibility(View.VISIBLE);
                    btn_login.setVisibility(View.GONE);

                    RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                    service.loginUser(phoneNumber, password).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginResponse lr = response.body();

                                if (lr != null && lr.getStatus().equals("ok")) {
                                    PreferenceUtil.setUserDetails(LoginActivity.this, lr);

                                    City city = PreferenceUtil.getCity(LoginActivity.this);

                                    if (city == null) {
                                        Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        finish();
                                    }
                                } else {
                                    progress_login.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);

                                    if (lr.getError_description() != null) {
                                        showErrorToast(lr.getError_description());
                                    } else if (lr.getMessage() != null) {
                                        showErrorToast(lr.getMessage());
                                    } else {
                                        showErrorToast(R.string.error_message);
                                    }
                                }
                            } else {
                                showErrorToast(R.string.error_message);
                                progress_login.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            showErrorToast(R.string.error_message);
                            progress_login.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    });
                }

                break;
            case R.id.tv_forgot_password:

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_forget_password, null);
                final EditText et_email = dialogView.findViewById(R.id.et_email);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.forgot_password)
                        .setView(dialogView)
                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                String email = et_email.getText().toString().trim();

                                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    ToastUtil.showError(LoginActivity.this, R.string.enter_valid_email);
                                } else {
                                    RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                                    service.forgotPassword(email).enqueue(new Callback<RequestResponse>() {
                                        @Override
                                        public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                                            if (response.body() != null && response.isSuccessful()) {
                                                RequestResponse requestResponse = response.body();

                                                if (requestResponse.getStatus() != null && requestResponse.getStatus().equals("ok")) {
                                                    ToastUtil.showSuccess(LoginActivity.this, requestResponse.getMessage());
                                                    showResetPasswordDialog();
                                                } else {
                                                    ToastUtil.showError(LoginActivity.this, requestResponse.getMessage());
                                                }
                                                dialogInterface.dismiss();
                                            } else {
                                                ToastUtil.showError(LoginActivity.this, R.string.error_message);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<RequestResponse> call, Throwable t) {
                                            ToastUtil.showError(LoginActivity.this, R.string.error_message);
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();

                break;
        }
    }

    private void showResetPasswordDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_reset_password, null);
        final EditText et_email = dialogView.findViewById(R.id.et_email);
        final EditText et_password = dialogView.findViewById(R.id.et_password);
        final EditText et_otp = dialogView.findViewById(R.id.et_otp);

        new AlertDialog.Builder(this)
                .setTitle(R.string.reset_password)
                .setView(dialogView)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        String email = et_email.getText().toString().trim();
                        String password = et_password.getText().toString().trim();
                        String otp = et_otp.getText().toString().trim();

                        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            ToastUtil.showError(LoginActivity.this, R.string.enter_valid_email);
                        } else if (password.isEmpty()) {
                            ToastUtil.showError(LoginActivity.this, R.string.enter_valid_password);
                        } else if (otp.isEmpty()) {
                            ToastUtil.showError(LoginActivity.this, R.string.enter_otp);
                        } else {
                            RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                            service.resetPassword(email, password, otp).enqueue(new Callback<RequestResponse>() {
                                @Override
                                public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                                    if (response.body() != null && response.isSuccessful()) {
                                        RequestResponse requestResponse = response.body();

                                        if (requestResponse.getStatus() != null && requestResponse.getStatus().equals("ok")) {
                                            ToastUtil.showSuccess(LoginActivity.this, requestResponse.getMessage());
                                        } else {
                                            ToastUtil.showError(LoginActivity.this, requestResponse.getMessage());
                                        }
                                        dialogInterface.dismiss();
                                    } else {
                                        ToastUtil.showError(LoginActivity.this, R.string.error_message);
                                    }
                                }

                                @Override
                                public void onFailure(Call<RequestResponse> call, Throwable t) {
                                    ToastUtil.showError(LoginActivity.this, R.string.error_message);
                                }
                            });
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
