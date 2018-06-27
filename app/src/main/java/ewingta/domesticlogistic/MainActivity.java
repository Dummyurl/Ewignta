package ewingta.domesticlogistic;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ewingta.domesticlogistic.activities.BaseActivity;
import ewingta.domesticlogistic.activities.CityActivity;
import ewingta.domesticlogistic.activities.LoginActivity;
import ewingta.domesticlogistic.fragment.AddAddressFragment;
import ewingta.domesticlogistic.fragment.AddOrderFragment;
import ewingta.domesticlogistic.fragment.AddressesFragment;
import ewingta.domesticlogistic.fragment.ConfirmOrderFragment;
import ewingta.domesticlogistic.fragment.EditProfileFragment;
import ewingta.domesticlogistic.fragment.OrdersFragment;
import ewingta.domesticlogistic.models.City;
import ewingta.domesticlogistic.models.LoginResponse;
import ewingta.domesticlogistic.utils.BNUtil;
import ewingta.domesticlogistic.utils.PreferenceUtil;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static ewingta.domesticlogistic.fragment.AddAddressFragment.REQUEST_LOCATION;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        EasyPermissions.PermissionCallbacks, BottomNavigationView.OnNavigationItemSelectedListener {

    private final static int RC_LOCATION = 1254;
    private DrawerLayout drawer_layout;
    private boolean doubleBackToExit;
    private TextView tv_area;
    public static final int CITY_CHANGE = 1454;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawer_layout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tv_name = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        TextView tv_email = navigationView.getHeaderView(0).findViewById(R.id.tv_email);

        LoginResponse loginResponse = PreferenceUtil.getUserDetails(this);

        if (loginResponse != null) {
            tv_email.setText(loginResponse.getEmail());
            tv_name.setText(loginResponse.getName());
        }

        City city = PreferenceUtil.getCity(this);

        tv_area = findViewById(R.id.tv_area);
        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                intent.putExtra(CityActivity.FROM_LOGIN, true);
                startActivityForResult(intent, CITY_CHANGE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        if (city != null) {
            tv_area.setText(String.valueOf(city.getLocation_name()));
        }

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        BNUtil.removeShiftMode(bottom_navigation);

        showAddOrderFragment();
    }

    public void showAddOrderFragment() {
        Fragment fragment = new AddOrderFragment();
        FragmentManager fM = getSupportFragmentManager();
        fM.beginTransaction()
                .replace(R.id.frame_layout, fragment, AddOrderFragment.class.getSimpleName())
                .addToBackStack(AddOrderFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fM = getSupportFragmentManager();

            if (!(fM.findFragmentById(R.id.frame_layout) instanceof AddOrderFragment) && fM.getBackStackEntryCount() > 1) {
                fM.popBackStackImmediate();
            } else if (!doubleBackToExit) {
                doubleBackToExit = true;
                showInfoToast(R.string.press_again_warn);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExit = false;
                    }
                }, 2000);
            } else {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fT = fM.beginTransaction();


        switch (item.getItemId()) {
            case R.id.action_home:
                if (fM.findFragmentByTag(AddOrderFragment.class.getSimpleName()) == null) {
                    Fragment fm_add_order = new AddOrderFragment();
                    fT.add(R.id.frame_layout, fm_add_order, AddOrderFragment.class.getSimpleName())
                            .addToBackStack(AddOrderFragment.class.getSimpleName())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                } else {
                    Fragment addOrderFragment = fM.findFragmentByTag(AddOrderFragment.class.getSimpleName());

                    if (addOrderFragment != null && addOrderFragment instanceof AddOrderFragment) {
                        fT.replace(R.id.frame_layout, addOrderFragment, AddOrderFragment.class.getSimpleName())
                                .addToBackStack(AddOrderFragment.class.getSimpleName())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }
                }

                return true;
            case R.id.action_orders:
                if (fM.findFragmentByTag(OrdersFragment.class.getSimpleName()) == null) {
                    Fragment fm_orders = new OrdersFragment();
                    fT.add(R.id.frame_layout, fm_orders, OrdersFragment.class.getSimpleName())
                            .addToBackStack(OrdersFragment.class.getSimpleName())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                } else {
                    Fragment ordersFragment = fM.findFragmentByTag(OrdersFragment.class.getSimpleName());

                    if (ordersFragment != null && ordersFragment instanceof OrdersFragment) {
                        fT.replace(R.id.frame_layout, ordersFragment, OrdersFragment.class.getSimpleName())
                                .addToBackStack(OrdersFragment.class.getSimpleName())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }
                }
                return true;
            case R.id.action_profile:
                if (fM.findFragmentByTag(EditProfileFragment.class.getSimpleName()) == null) {
                    Fragment fm_profile = new EditProfileFragment();
                    fT.add(R.id.frame_layout, fm_profile, EditProfileFragment.class.getSimpleName())
                            .addToBackStack(EditProfileFragment.class.getSimpleName())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                } else {
                    Fragment profileFragment = fM.findFragmentByTag(EditProfileFragment.class.getSimpleName());

                    if (profileFragment != null && profileFragment instanceof EditProfileFragment) {
                        fT.replace(R.id.frame_layout, profileFragment, EditProfileFragment.class.getSimpleName())
                                .addToBackStack(EditProfileFragment.class.getSimpleName())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }
                }
                return true;
            case R.id.action_payment:
                return true;
            case R.id.action_add_address:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawer_layout.closeDrawer(Gravity.START);
                    }
                }, 1000);

                actionAddAddress();
                return true;
            case R.id.action_address:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawer_layout.closeDrawer(Gravity.START);
                    }
                }, 1000);

                showAddressListFragment(fM);
                return true;
            case R.id.action_share:
                try {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text));
                    shareIntent.setType("text/*");
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.invite_friends)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_facebook:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
                startActivity(facebookIntent);
                return true;
            case R.id.action_twitter:
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
                startActivity(twitterIntent);
                return true;
            case R.id.action_instagram:
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com"));
                startActivity(instagramIntent);
                return true;
            case R.id.action_contact_us:
                return true;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(R.string.logout_warning)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                PreferenceUtil.setUserDetails(MainActivity.this, null);

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.create().show();
                return true;
        }
        return false;
    }

    private void showAddressListFragment(FragmentManager fM) {
        FragmentTransaction fT = fM.beginTransaction();

        if (fM.findFragmentByTag(AddressesFragment.class.getSimpleName()) == null) {
            Fragment fm_address = new AddressesFragment();
            fT.add(R.id.frame_layout, fm_address, AddressesFragment.class.getSimpleName())
                    .addToBackStack(AddressesFragment.class.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } else {
            Fragment addressFragment = fM.findFragmentByTag(AddressesFragment.class.getSimpleName());

            if (addressFragment != null && addressFragment instanceof AddressesFragment) {
                fT.replace(R.id.frame_layout, addressFragment, AddressesFragment.class.getSimpleName())
                        .addToBackStack(AddressesFragment.class.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }
    }

    private void showAddressFragment(FragmentManager fM) {
        FragmentTransaction fT = fM.beginTransaction();

        if (fM.findFragmentByTag(AddAddressFragment.class.getSimpleName()) == null) {
            Fragment fm_address = new AddAddressFragment();
            fT.add(R.id.frame_layout, fm_address, AddAddressFragment.class.getSimpleName())
                    .addToBackStack(AddAddressFragment.class.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } else {
            Fragment addressFragment = fM.findFragmentByTag(AddAddressFragment.class.getSimpleName());

            if (addressFragment != null && addressFragment instanceof AddAddressFragment) {
                fT.replace(R.id.frame_layout, addressFragment, AddAddressFragment.class.getSimpleName())
                        .addToBackStack(AddAddressFragment.class.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }
    }

    @AfterPermissionGranted(RC_LOCATION)
    public void actionAddAddress() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                showAddressFragment(fragmentManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.storage_location), RC_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                break;
            case REQUEST_LOCATION:

                Fragment fragment = getSupportFragmentManager().findFragmentByTag(AddAddressFragment.class.getSimpleName());

                if (fragment != null && fragment instanceof AddAddressFragment) {
                    AddAddressFragment addAddressFragment = (AddAddressFragment) fragment;
                    addAddressFragment.enableLocationListener();
                }
                break;
            case CITY_CHANGE:
                City city = PreferenceUtil.getCity(this);

                if (city != null) {
                    tv_area.setText(String.valueOf(city.getLocation_name()));
                }
                break;
        }
    }

    public void showConfirmOrder(String ordernumber) {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fT = fM.beginTransaction();
        ConfirmOrderFragment confirmOrderFragment = ConfirmOrderFragment.newInstance(ordernumber);
        fT.add(R.id.frame_layout, confirmOrderFragment, ConfirmOrderFragment.class.getSimpleName())
                .addToBackStack(ConfirmOrderFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void showOrders() {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fT = fM.beginTransaction();
        Fragment fm_orders = new OrdersFragment();
        fT.add(R.id.frame_layout, fm_orders, OrdersFragment.class.getSimpleName())
                .addToBackStack(OrdersFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
