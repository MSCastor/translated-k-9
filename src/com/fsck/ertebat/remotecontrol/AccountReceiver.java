package com.fsck.ertebat.remotecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

class AccountReceiver extends BroadcastReceiver {
    ertebatAccountReceptor receptor = null;

    protected AccountReceiver(ertebatAccountReceptor nReceptor) {
        receptor = nReceptor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ertebatRemoteControl.ertebat_REQUEST_ACCOUNTS.equals(intent.getAction())) {
            Bundle bundle = getResultExtras(false);
            if (bundle == null) {
                Log.w(ertebatRemoteControl.LOG_TAG, "Response bundle is empty");
                return;
            }
            receptor.accounts(bundle.getStringArray(ertebatRemoteControl.ertebat_ACCOUNT_UUIDS), bundle.getStringArray(ertebatRemoteControl.ertebat_ACCOUNT_DESCRIPTIONS));
        }
    }

}
