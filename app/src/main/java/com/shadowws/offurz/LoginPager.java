package com.shadowws.offurz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shadowws.offurz.Fragments.LoginBuyerFragment;
import com.shadowws.offurz.Fragments.LoginSellerFragment;

/**
 * Created by Lenovo on 2/23/2018.
 */

public class LoginPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public LoginPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                LoginSellerFragment tab1 = new LoginSellerFragment();
                return tab1;
            case 1:
                LoginBuyerFragment tab2 = new LoginBuyerFragment();
                return tab2;
//            case 2:
//                Tab3 tab3 = new Tab3();
//                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
