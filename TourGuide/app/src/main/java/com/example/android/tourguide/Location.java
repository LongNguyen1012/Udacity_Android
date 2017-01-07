package com.example.android.tourguide;

import android.content.Context;
import android.location.Address;

/**
 * Created by Long Nguyen on 1/2/2017.
 */

public class Location {
    private int mName;
    private int mAddress;
    private int mPhoneNumber;
    private int mWebsite;
    private int mImageResourceId;
    private static final int NO_WEB_RESOURCE_ID = -1;

    public Location(int NameId, int AddressId, int PhoneNumberId, int WebsiteId, int ImageResourceId) {
        mName = NameId;
        mAddress = AddressId;
        mPhoneNumber = PhoneNumberId;
        mWebsite = WebsiteId;
        mImageResourceId = ImageResourceId;
    }

    public Location(int NameId, int AddressId, int PhoneNumberId, int ImageResourceId) {
        mName = NameId;
        mAddress = AddressId;
        mPhoneNumber = PhoneNumberId;
        mWebsite = NO_WEB_RESOURCE_ID;
        mImageResourceId = ImageResourceId;
    }

    public int getName () {
        return mName;
    }

    public int getAddress (){
        return mAddress;
    }

    public int getPhoneNumber () { return mPhoneNumber; }

    public int getWebsite () { return mWebsite; }

    public int getImageResourceId(int image) {
        return mImageResourceId;
    }

    public boolean hasWeb() {
        return mWebsite != NO_WEB_RESOURCE_ID;
    }

}
