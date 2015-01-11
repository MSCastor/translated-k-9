package com.fsck.Ertebat.remotecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

class AccountReceiver extends BroadcastReceiver {
    ErtebatAccountReceptor receptor = null;

    protected AccountReceiver(ErtebatAccountReceptor nReceptor) {
        receptor = nReceptor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ErtebatRemoteControl.Ertebat_REQUEST_ACCOUNTS.equals(intent.getAction())) {
            Bundle bundle = getResultExtras(false);
            if (bundle == null) {
                Log.w(ErtebatRemoteControl.LOG_TAG, "Response bundle is empty");
                return;
            }
            receptor.accounts(bundle.getStringArray(ErtebatRemoteControl.Ertebat_ACCOUNT_UUIDS), bundle.getStringArray(ErtebatRemoteControl.Ertebat_ACCOUNT_DESCRIPTIONS));
        }
    }

}
