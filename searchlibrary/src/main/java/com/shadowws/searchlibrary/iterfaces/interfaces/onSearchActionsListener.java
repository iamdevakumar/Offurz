package com.shadowws.searchlibrary.iterfaces.interfaces;

/**
 * Created by Lenovo on 2/26/2018.
 */

public interface onSearchActionsListener {
    void onItemClicked(String item);
    void showProgress(boolean show);
    void listEmpty();
    void onScroll();
    void error(String localizedMessage);
}
