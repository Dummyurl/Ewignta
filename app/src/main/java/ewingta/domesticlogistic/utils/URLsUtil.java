package ewingta.domesticlogistic.utils;

public class URLsUtil {
    public static final String BASE_URL = "http://domesticlogistics.in/beta/";

    private static final String INDEX_POST_URL = BASE_URL + "index.php?option=com_jbackend&view=request&module=user&action=post";

    private static final String INDEX_GET_URL = BASE_URL + "index.php?option=com_jbackend&view=request&module=user&action=get";

    public static final String LOGIN_URL = INDEX_POST_URL + "&resource=login";

    public static final String REGISTER_URL = INDEX_POST_URL + "&resource=register";

    public static final String OTP_URL = INDEX_GET_URL + "&resource=otpactivation&usertype=U";

    public static final String ORDERS_URL = INDEX_GET_URL + "&resource=orderlist";

    public static final String ADD_ADDRESS_URL = INDEX_POST_URL + "&resource=addressadd";

    public static final String LIST_ADDRESS_URL = INDEX_GET_URL + "&resource=useraddress";

    public static final String SERVICES_URL = INDEX_GET_URL + "&resource=services";

    public static final String VALUES_URL = INDEX_GET_URL + "&resource=values";

    public static final String DIMENSIONS_URL = INDEX_GET_URL + "&resource=dimentions";

    public static final String WEIGHT_URL = INDEX_GET_URL + "&resource=weight";

    public static final String CATEGORIES_URL = INDEX_GET_URL + "&resource=category";

    public static final String TIMES_URL = INDEX_GET_URL + "&resource=times";

    public static final String AREA_URL = INDEX_GET_URL + "&resource=location&countryid=1&stateid=3";

    public static final String CITY_URL = INDEX_GET_URL + "&resource=city&countryid=1&stateid=3";

    public static final String ADDRESSES_URL = INDEX_GET_URL + "&resource=useraddress";

    public static final String ADD_ORDER_URL = INDEX_POST_URL + "&resource=ordergenarate&country=1&state=3";

    public static final String UPDATE_PASSWORD_URL = INDEX_GET_URL + "&resource=updateprofile";

    public static final String PRICE_URL = INDEX_GET_URL + "&resource=getprice&weight&paymenttype";

    public static final String FORGOT_PASSWORD = INDEX_GET_URL + "&resource=forgotpassword";

    public static final String RESET_PASSWORD = INDEX_GET_URL + "&resource=resetpassword";

    public static final String DELETE_ADDRESS = INDEX_GET_URL + "&resource=removeuseraddress";

    //public static final String PDF_INVOICE = INDEX_GET_URL + "&resource=pdfdownload";

}

