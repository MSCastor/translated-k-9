
package com.fsck.ertebat.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fsck.ertebat.Account;
import com.fsck.ertebat.ertebat;
import com.fsck.ertebat.remotecontrol.ertebatRemoteControl;
import com.fsck.ertebat.Preferences;

import static com.fsck.ertebat.remotecontrol.ertebatRemoteControl.*;

public class RemoteControlReceiver extends CoreReceiver {
    @Override
    public Integer receive(Context context, Intent intent, Integer tmpWakeLockId) {
        if (ertebat.DEBUG)
            Log.i(ertebat.LOG_TAG, "RemoteControlReceiver.onReceive" + intent);

        if (ertebatRemoteControl.ertebat_SET.equals(intent.getAction())) {
            RemoteControlService.set(context, intent, tmpWakeLockId);
            tmpWakeLockId = null;
        } else if (ertebatRemoteControl.ertebat_REQUEST_ACCOUNTS.equals(intent.getAction())) {
            try {
                Preferences preferences = Preferences.getPreferences(context);
                Account[] accounts = preferences.getAccounts();
                String[] uuids = new String[accounts.length];
                String[] descriptions = new String[accounts.length];
                for (int i = 0; i < accounts.length; i++) {
                    //warning: account may not be isAvailable()
                    Account account = accounts[i];

                    uuids[i] = account.getUuid();
                    descriptions[i] = account.getDescription();
                }
                Bundle bundle = getResultExtras(true);
                bundle.putStringArray(ertebat_ACCOUNT_UUIDS, uuids);
                bundle.putStringArray(ertebat_ACCOUNT_DESCRIPTIONS, descriptions);
            } catch (Exception e) {
                Log.e(ertebat.LOG_TAG, "Could not handle ertebat_RESPONSE_INTENT", e);
            }

        }

        return tmpWakeLockId;
    }

}
