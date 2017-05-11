package com.developer.sangbarca.bkdictionary.Rest;

import com.developer.sangbarca.bkdictionary.Helper.BKRepo;

/**
 * Created by nhat on 08/05/2017.
 */

public interface BKCallBack {
    void onUpdated(BKRepo bkRepo);
    void onFailed(String message);
}
