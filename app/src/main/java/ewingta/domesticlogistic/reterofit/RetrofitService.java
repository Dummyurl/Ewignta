package ewingta.domesticlogistic.reterofit;


import ewingta.domesticlogistic.models.AddAddressResponse;
import ewingta.domesticlogistic.models.AddressResponse;
import ewingta.domesticlogistic.models.AreaResponse;
import ewingta.domesticlogistic.models.CategoryResponse;
import ewingta.domesticlogistic.models.CityResponse;
import ewingta.domesticlogistic.models.DimensionResponse;
import ewingta.domesticlogistic.models.LoginResponse;
import ewingta.domesticlogistic.models.MyAddressResponse;
import ewingta.domesticlogistic.models.OrderResponse;
import ewingta.domesticlogistic.models.PriceResponse;
import ewingta.domesticlogistic.models.RegisterResponse;
import ewingta.domesticlogistic.models.RequestResponse;
import ewingta.domesticlogistic.models.ServiceResponse;
import ewingta.domesticlogistic.models.TimeResponse;
import ewingta.domesticlogistic.models.ValueResponse;
import ewingta.domesticlogistic.models.Weight;
import ewingta.domesticlogistic.models.WeightResponse;
import ewingta.domesticlogistic.utils.URLsUtil;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET(URLsUtil.LOGIN_URL)
    Call<LoginResponse> loginUser(@Query("username") String username, @Query("password") String password);

    @GET(URLsUtil.REGISTER_URL)
    Call<RegisterResponse> registerUser(@Query("name") String name, @Query("email") String email, @Query("mobile") String mobile, @Query("password") String password);

    @GET(URLsUtil.ORDERS_URL)
    Call<OrderResponse> getOrders(@Query("userid") String userid);

    @GET(URLsUtil.OTP_URL)
    Call<LoginResponse> otpActivation(@Query("userid") String userid, @Query("otp") String otp);

    @GET(URLsUtil.ADD_ADDRESS_URL)
    Call<AddAddressResponse> addAddress(@Query("userid") String userid, @Query("shortname") String shortname, @Query("location") String location);

    @GET(URLsUtil.LIST_ADDRESS_URL)
    Call<MyAddressResponse> listAddress(@Query("userid") String userid);

    @GET(URLsUtil.SERVICES_URL)
    Call<ServiceResponse> getServices();

    @GET(URLsUtil.VALUES_URL)
    Call<ValueResponse> getValues();

    @GET(URLsUtil.DIMENSIONS_URL)
    Call<DimensionResponse> getDimensions();

    @GET(URLsUtil.WEIGHT_URL)
    Call<WeightResponse> getWeights();

    @GET(URLsUtil.CATEGORIES_URL)
    Call<CategoryResponse> getCategories();

    @GET(URLsUtil.TIMES_URL)
    Call<TimeResponse> getTimes();

    @GET(URLsUtil.AREA_URL)
    Call<AreaResponse> getAreas(@Query("cityid") long cityid);

    @GET(URLsUtil.CITY_URL)
    Call<CityResponse> getCities();

    @GET(URLsUtil.ADDRESSES_URL)
    Call<AddressResponse> getUserAddresses(@Query("userid") String userid);

    @GET(URLsUtil.ADD_ORDER_URL)
    Call<RegisterResponse> addOrder(@Query("userid") String userid,
                                    @Query("city") long city,
                                    @Query("branch") long branch,
                                    @Query("dropname") String dropname,
                                    @Query("dropphone") String dropphone,
                                    @Query("dropaddress") long dropaddress,
                                    @Query("pickupname") String pickupname,
                                    @Query("pickupphone") String pickupphone,
                                    @Query("pickupaddress") long pickupaddress,
                                    @Query("time") long time,
                                    @Query("type") long type,
                                    @Query("delverytype") long delverytype,
                                    @Query("datepick") String datepick);

    @GET(URLsUtil.UPDATE_PASSWORD_URL)
    Call<RegisterResponse> updatePassword(@Query("userid") String userid, @Query("name") String name,
                                          @Query("email") String email, @Query("mobile") String mobile, @Query("pass") String pass);

    @GET(URLsUtil.PRICE_URL)
    Call<PriceResponse> getPrice(@Query("ordernumber") String ordernumber
                                 );

    @GET(URLsUtil.FORGOT_PASSWORD)
    Call<RequestResponse> forgotPassword(@Query("email") String email);

    @GET(URLsUtil.RESET_PASSWORD)
    Call<RequestResponse> resetPassword(@Query("email") String email, @Query("password") String password, @Query("otp") String otp);

    @GET(URLsUtil.DELETE_ADDRESS)
    Call<RequestResponse> deleteAddress(@Query("userid") String userid, @Query("addrid") String addrid);
}
