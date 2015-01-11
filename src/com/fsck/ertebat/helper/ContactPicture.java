package com.fsck.ertebat.helper;

import android.content.Context;
import android.util.TypedValue;

import com.fsck.ertebat.ertebat;
import com.top.ertebat.mail.R;
import com.fsck.ertebat.activity.misc.ContactPictureLoader;

public class ContactPicture {

    public static ContactPictureLoader getContactPictureLoader(Context context) {
        final int defaultBgColor;
        if (!ertebat.isColorizeMissingContactPictures()) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.contactPictureFallbackDefaultBackgroundColor,
                    outValue, true);
            defaultBgColor = outValue.data;
        } else {
            defaultBgColor = 0;
        }

        return new ContactPictureLoader(context, defaultBgColor);
    }
}
