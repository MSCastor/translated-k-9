package com.top.Ertebat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.top.Ertebat.Account;
import com.top.Ertebat.BaseAccount;
import com.top.Ertebat.search.SearchAccount;
import com.top.Ertebat.R;

public class LauncherShortcuts extends AccountList {
    @Override
    public void onCreate(Bundle icicle) {
        // finish() immediately if we aren't supposed to be here
        if (!Intent.ACTION_CREATE_SHORTCUT.equals(getIntent().getAction())) {
            finish();
            return;
        }

        super.onCreate(icicle);
    }

    @Override
    protected boolean displaySpecialAccounts() {
        return true;
    }

    @Override
    protected void onAccountSelected(BaseAccount account) {
        Intent shortcutIntent = null;

        if (account instanceof SearchAccount) {
            SearchAccount searchAccount = (SearchAccount) account;
            shortcutIntent = MessageList.shortcutIntent(this, searchAccount.getId());
        } else {
            shortcutIntent = FolderList.actionHandleAccountIntent(this, (Account) account, true);
        }

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        String description = account.getDescription();
        if (description == null || description.isEmpty()) {
            description = account.getEmail();
        }
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, description);
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

        setResult(RESULT_OK, intent);
        finish();
    }
}