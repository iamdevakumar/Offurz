package com.shadowws.searchlibrary.iterfaces.interfaces;

/**
 * Created by Lenovo on 2/26/2018.
 */

public interface onSearchListener {
    void onSearch(String query);
    void searchViewOpened();
    void searchViewClosed();
    void onCancelSearch();
}
