// IMyAidlActivity.aidl
package com.xpf.aidl_example;

// Declare any non-default types here with import statements
import com.xpf.aidl_example.RectBean;

interface IMyAidlActivity {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void performAction(in RectBean rect);
}
