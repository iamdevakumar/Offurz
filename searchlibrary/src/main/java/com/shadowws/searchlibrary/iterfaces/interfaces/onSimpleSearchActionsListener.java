package com.shadowws.searchlibrary.iterfaces.interfaces;

/**
 * Created by Lenovo on 2/26/2018.
 */

public interface onSimpleSearchActionsListener {
    void onItemClicked(String item);
    void onScroll();
    void error(String localizedMessage);
}
