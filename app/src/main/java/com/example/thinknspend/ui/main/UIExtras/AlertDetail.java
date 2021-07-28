package com.example.thinknspend.ui.main.UIExtras;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlertDetail extends Dialog implements DialogInterface {
    public AlertDetail(@NonNull Context context) {
        super(context);
    }

    public AlertDetail(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AlertDetail(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
