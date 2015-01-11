package com.fsck.Ertebat.activity.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.widget.Toast;

import com.fsck.Ertebat.Ertebat;
import com.fsck.Ertebat.Ertebat.NotificationHideSubject;
import com.fsck.Ertebat.Ertebat.NotificationQuickDelete;
import com.fsck.Ertebat.Ertebat.SplitViewMode;
import com.fsck.Ertebat.Preferences;
import com.top.Ertebat.mail.R;
import com.fsck.Ertebat.activity.ColorPickerDialog;
import com.fsck.Ertebat.activity.ErtebatPreferenceActivity;
import com.fsck.Ertebat.controller.MessagingController;
import com.fsck.Ertebat.helper.FileBrowserHelper;
import com.fsck.Ertebat.helper.FileBrowserHelper.FileBrowserFailOverCallback;
import com.fsck.Ertebat.preferences.CheckBoxListPreference;
import com.fsck.Ertebat.preferences.TimePickerPreference;

import com.fsck.Ertebat.service.MailService;


public class Prefs extends ErtebatPreferenceActivity {

    /**
     * Immutable empty {@link CharSequence} array
     */
    private static final CharSequence[] EMPTY_CHAR_SEQUENCE_ARRAY = new CharSequence[0];

    /*
     * Keys of the preferences defined in res/xml/global_preferences.xml
     */
    private static final String PREFERENCE_LANGUAGE = "language";
    private static final String PREFERENCE_THEME = "theme";
    private static final String PREFERENCE_MESSAGE_VIEW_THEME = "messageViewTheme";
    private static final String PREFERENCE_FIXED_MESSAGE_THEME = "fixedMessageViewTheme";
    private static final String PREFERENCE_COMPOSER_THEME = "messageComposeTheme";
    private static final String PREFERENCE_FONT_SIZE = "font_size";
    private static final String PREFERENCE_ANIMATIONS = "animations";
    private static final String PREFERENCE_GESTURES = "gestures";
    private static final String PREFERENCE_VOLUME_NAVIGATION = "volumeNavigation";
    private static final String PREFERENCE_START_INTEGRATED_INBOX = "start_integrated_inbox";
    private static final String PREFERENCE_CONFIRM_ACTIONS = "confirm_actions";
    private static final String PREFERENCE_NOTIFICATION_HIDE_SUBJECT = "notification_hide_subject";
    private static final String PREFERENCE_MEASURE_ACCOUNTS = "measure_accounts";
    private static final String PREFERENCE_COUNT_SEARCH = "count_search";
    private static final String PREFERENCE_HIDE_SPECIAL_ACCOUNTS = "hide_special_accounts";
    private static final String PREFERENCE_MESSAGELIST_CHECKBOXES = "messagelist_checkboxes";
    private static final String PREFERENCE_MESSAGELIST_PREVIEW_LINES = "messagelist_preview_lines";
    private static final String PREFERENCE_MESSAGELIST_SENDER_ABOVE_SUBJECT = "messagelist_sender_above_subject";
    private static final String PREFERENCE_MESSAGELIST_STARS = "messagelist_stars";
    private static final String PREFERENCE_MESSAGELIST_SHOW_CORRESPONDENT_NAMES = "messagelist_show_correspondent_names";
    private static final String PREFERENCE_MESSAGELIST_SHOW_CONTACT_NAME = "messagelist_show_contact_name";
    private static final String PREFERENCE_MESSAGELIST_CONTACT_NAME_COLOR = "messagelist_contact_name_color";
    private static final String PREFERENCE_MESSAGELIST_SHOW_CONTACT_PICTURE = "messagelist_show_contact_picture";
    private static final String PREFERENCE_MESSAGELIST_COLORIZE_MISSING_CONTACT_PICTURES =
            "messagelist_colorize_missing_contact_pictures";
    private static final String PREFERENCE_MESSAGEVIEW_FIXEDWIDTH = "messageview_fixedwidth_font";
    private static final String PREFERENCE_MESSAGEVIEW_VISIBLE_REFILE_ACTIONS = "messageview_visible_refile_actions";

    private static final String PREFERENCE_MESSAGEVIEW_RETURN_TO_LIST = "messageview_return_to_list";
    private static final String PREFERENCE_MESSAGEVIEW_SHOW_NEXT = "messageview_show_next";
    private static final String PREFERENCE_QUIET_TIME_ENABLED = "quiet_time_enabled";
    private static final String PREFERENCE_QUIET_TIME_STARTS = "quiet_time_starts";
    private static final String PREFERENCE_QUIET_TIME_ENDS = "quiet_time_ends";
    private static final String PREFERENCE_NOTIF_QUICK_DELETE = "notification_quick_delete";
    private static final String PREFERENCE_HIDE_USERAGENT = "privacy_hide_useragent";
    private static final String PREFERENCE_HIDE_TIMEZONE = "privacy_hide_timezone";

    private static final String PREFERENCE_AUTOFIT_WIDTH = "messageview_autofit_width";
    private static final String PREFERENCE_BACKGROUND_OPS = "background_ops";
    private static final String PREFERENCE_DEBUG_LOGGING = "debug_logging";
    private static final String PREFERENCE_SENSITIVE_LOGGING = "sensitive_logging";

    private static final String PREFERENCE_ATTACHMENT_DEF_PATH = "attachment_default_path";
    private static final String PREFERENCE_BACKGROUND_AS_UNREAD_INDICATOR = "messagelist_background_as_unread_indicator";
    private static final String PREFERENCE_THREADED_VIEW = "threaded_view";
    private static final String PREFERENCE_FOLDERLIST_WRAP_NAME = "folderlist_wrap_folder_name";
    private static final String PREFERENCE_SPLITVIEW_MODE = "splitview_mode";

    private static final int ACTIVITY_CHOOSE_FOLDER = 1;

    // Named indices for the mVisibleRefileActions field
    private static final int VISIBLE_REFILE_ACTIONS_DELETE = 0;
    private static final int VISIBLE_REFILE_ACTIONS_ARCHIVE = 1;
    private static final int VISIBLE_REFILE_ACTIONS_MOVE = 2;
    private static final int VISIBLE_REFILE_ACTIONS_COPY = 3;
    private static final int VISIBLE_REFILE_ACTIONS_SPAM = 4;

    private ListPreference mLanguage;
    private ListPreference mTheme;
    private CheckBoxPreference mFixedMessageTheme;
    private ListPreference mMessageTheme;
    private ListPreference mComposerTheme;
//    private CheckBoxPreference mAnimations;
//    private CheckBoxPreference mGestures;
//    private CheckBoxListPreference mVolumeNavigation;
//    private CheckBoxPreference mStartIntegratedInbox;
//    private CheckBoxListPreference mConfirmActions;
//    private ListPreference mNotificationHideSubject;
    private CheckBoxPreference mMeasureAccounts;
    private CheckBoxPreference mCountSearch;
    private CheckBoxPreference mHideSpecialAccounts;
    private ListPreference mPreviewLines;
    private CheckBoxPreference mSenderAboveSubject;
    private CheckBoxPreference mCheckboxes;
    private CheckBoxPreference mStars;
    private CheckBoxPreference mShowCorrespondentNames;
    private CheckBoxPreference mShowContactName;
    private CheckBoxPreference mChangeContactNameColor;
    private CheckBoxPreference mShowContactPicture;
    private CheckBoxPreference mColorizeMissingContactPictures;
    private CheckBoxPreference mFixedWidth;
//    private CheckBoxPreference mReturnToList;
//    private CheckBoxPreference mShowNext;
    private CheckBoxPreference mAutofitWidth;
//    private ListPreference mBackgroundOps;
//    private CheckBoxPreference mDebugLogging;
//    private CheckBoxPreference mSensitiveLogging;
//    private CheckBoxPreference mHideUserAgent;
//    private CheckBoxPreference mHideTimeZone;
    private CheckBoxPreference mWrapFolderNames;
    private CheckBoxListPreference mVisibleRefileActions;

    private CheckBoxPreference mQuietTimeEnabled;
    private com.fsck.Ertebat.preferences.TimePickerPreference mQuietTimeStarts;
    private com.fsck.Ertebat.preferences.TimePickerPreference mQuietTimeEnds;
    private ListPreference mNotificationQuickDelete;
//    private Preference mAttachmentPathPreference;

    private CheckBoxPreference mBackgroundAsUnreadIndicator;
//    private CheckBoxPreference mThreadedView;
//    private ListPreference mSplitViewMode;


    public static void actionPrefs(Context context) {
        Intent i = new Intent(context, Prefs.class);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.global_preferences);

        mLanguage = (ListPreference) findPreference(PREFERENCE_LANGUAGE);
        List<CharSequence> entryVector = new ArrayList<CharSequence>(Arrays.asList(mLanguage.getEntries()));
        List<CharSequence> entryValueVector = new ArrayList<CharSequence>(Arrays.asList(mLanguage.getEntryValues()));
        String supportedLanguages[] = getResources().getStringArray(R.array.supported_languages);
        Set<String> supportedLanguageSet = new HashSet<String>(Arrays.asList(supportedLanguages));
        for (int i = entryVector.size() - 1; i > -1; --i) {
            if (!supportedLanguageSet.contains(entryValueVector.get(i))) {
                entryVector.remove(i);
                entryValueVector.remove(i);
            }
        }
        initListPreference(mLanguage, Ertebat.getErtebatLanguage(),
                           entryVector.toArray(EMPTY_CHAR_SEQUENCE_ARRAY),
                           entryValueVector.toArray(EMPTY_CHAR_SEQUENCE_ARRAY));

        mTheme = setupListPreference(PREFERENCE_THEME, themeIdToName(Ertebat.getErtebatTheme()));
        mFixedMessageTheme = (CheckBoxPreference) findPreference(PREFERENCE_FIXED_MESSAGE_THEME);
        mFixedMessageTheme.setChecked(Ertebat.useFixedMessageViewTheme());
        mMessageTheme = setupListPreference(PREFERENCE_MESSAGE_VIEW_THEME,
                themeIdToName(Ertebat.getErtebatMessageViewThemeSetting()));
        mComposerTheme = setupListPreference(PREFERENCE_COMPOSER_THEME,
                themeIdToName(Ertebat.getErtebatComposerThemeSetting()));

        findPreference(PREFERENCE_FONT_SIZE).setOnPreferenceClickListener(
        new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                onFontSizeSettings();
                return true;
            }
        });

//        mAnimations = (CheckBoxPreference)findPreference(PREFERENCE_ANIMATIONS);
//        mAnimations.setChecked(Ertebat.showAnimations());

//        mGestures = (CheckBoxPreference)findPreference(PREFERENCE_GESTURES);
//        mGestures.setChecked(Ertebat.gesturesEnabled());

//        mVolumeNavigation = (CheckBoxListPreference)findPreference(PREFERENCE_VOLUME_NAVIGATION);
//        mVolumeNavigation.setItems(new CharSequence[] {getString(R.string.volume_navigation_message), getString(R.string.volume_navigation_list)});
//        mVolumeNavigation.setCheckedItems(new boolean[] {Ertebat.useVolumeKeysForNavigationEnabled(), Ertebat.useVolumeKeysForListNavigationEnabled()});

//        mStartIntegratedInbox = (CheckBoxPreference)findPreference(PREFERENCE_START_INTEGRATED_INBOX);
//        mStartIntegratedInbox.setChecked(Ertebat.startIntegratedInbox());

//        mConfirmActions = (CheckBoxListPreference) findPreference(PREFERENCE_CONFIRM_ACTIONS);

        boolean canDeleteFromNotification = MessagingController.platformSupportsExtendedNotifications();
        CharSequence[] confirmActionEntries = new CharSequence[canDeleteFromNotification ? 4 : 3];
        boolean[] confirmActionValues = new boolean[canDeleteFromNotification ? 4 : 3];
        int index = 0;

        confirmActionEntries[index] = getString(R.string.global_settings_confirm_action_delete);
        confirmActionValues[index++] = Ertebat.confirmDelete();
        confirmActionEntries[index] = getString(R.string.global_settings_confirm_action_delete_starred);
        confirmActionValues[index++] = Ertebat.confirmDeleteStarred();
        if (canDeleteFromNotification) {
            confirmActionEntries[index] = getString(R.string.global_settings_confirm_action_delete_notif);
            confirmActionValues[index++] = Ertebat.confirmDeleteFromNotification();
        }
        confirmActionEntries[index] = getString(R.string.global_settings_confirm_action_spam);
        confirmActionValues[index++] = Ertebat.confirmSpam();

//        mConfirmActions.setItems(confirmActionEntries);
//        mConfirmActions.setCheckedItems(confirmActionValues);

//        mNotificationHideSubject = setupListPreference(PREFERENCE_NOTIFICATION_HIDE_SUBJECT,
//                Ertebat.getNotificationHideSubject().toString());

        mMeasureAccounts = (CheckBoxPreference)findPreference(PREFERENCE_MEASURE_ACCOUNTS);
        mMeasureAccounts.setChecked(Ertebat.measureAccounts());

        mCountSearch = (CheckBoxPreference)findPreference(PREFERENCE_COUNT_SEARCH);
        mCountSearch.setChecked(Ertebat.countSearchMessages());

        mHideSpecialAccounts = (CheckBoxPreference)findPreference(PREFERENCE_HIDE_SPECIAL_ACCOUNTS);
        mHideSpecialAccounts.setChecked(Ertebat.isHideSpecialAccounts());


        mPreviewLines = setupListPreference(PREFERENCE_MESSAGELIST_PREVIEW_LINES,
                                            Integer.toString(Ertebat.messageListPreviewLines()));

        mSenderAboveSubject = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_SENDER_ABOVE_SUBJECT);
        mSenderAboveSubject.setChecked(Ertebat.messageListSenderAboveSubject());
        mCheckboxes = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_CHECKBOXES);
        mCheckboxes.setChecked(Ertebat.messageListCheckboxes());

        mStars = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_STARS);
        mStars.setChecked(Ertebat.messageListStars());

        mShowCorrespondentNames = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_SHOW_CORRESPONDENT_NAMES);
        mShowCorrespondentNames.setChecked(Ertebat.showCorrespondentNames());

        mShowContactName = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_SHOW_CONTACT_NAME);
        mShowContactName.setChecked(Ertebat.showContactName());

        mShowContactPicture = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_SHOW_CONTACT_PICTURE);
        mShowContactPicture.setChecked(Ertebat.showContactPicture());

        mColorizeMissingContactPictures = (CheckBoxPreference)findPreference(
                PREFERENCE_MESSAGELIST_COLORIZE_MISSING_CONTACT_PICTURES);
        mColorizeMissingContactPictures.setChecked(Ertebat.isColorizeMissingContactPictures());

        mBackgroundAsUnreadIndicator = (CheckBoxPreference)findPreference(PREFERENCE_BACKGROUND_AS_UNREAD_INDICATOR);
        mBackgroundAsUnreadIndicator.setChecked(Ertebat.useBackgroundAsUnreadIndicator());

        mChangeContactNameColor = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGELIST_CONTACT_NAME_COLOR);
        mChangeContactNameColor.setChecked(Ertebat.changeContactNameColor());

//        mThreadedView = (CheckBoxPreference) findPreference(PREFERENCE_THREADED_VIEW);
//        mThreadedView.setChecked(Ertebat.isThreadedViewEnabled());

        if (Ertebat.changeContactNameColor()) {
            mChangeContactNameColor.setSummary(R.string.global_settings_registered_name_color_changed);
        } else {
            mChangeContactNameColor.setSummary(R.string.global_settings_registered_name_color_default);
        }
        mChangeContactNameColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final Boolean checked = (Boolean) newValue;
                if (checked) {
                    onChooseContactNameColor();
                    mChangeContactNameColor.setSummary(R.string.global_settings_registered_name_color_changed);
                } else {
                    mChangeContactNameColor.setSummary(R.string.global_settings_registered_name_color_default);
                }
                mChangeContactNameColor.setChecked(checked);
                return false;
            }
        });

        mFixedWidth = (CheckBoxPreference)findPreference(PREFERENCE_MESSAGEVIEW_FIXEDWIDTH);
        mFixedWidth.setChecked(Ertebat.messageViewFixedWidthFont());

//        mReturnToList = (CheckBoxPreference) findPreference(PREFERENCE_MESSAGEVIEW_RETURN_TO_LIST);
//        mReturnToList.setChecked(Ertebat.messageViewReturnToList());

//        mShowNext = (CheckBoxPreference) findPreference(PREFERENCE_MESSAGEVIEW_SHOW_NEXT);
//        mShowNext.setChecked(Ertebat.messageViewShowNext());

        mAutofitWidth = (CheckBoxPreference) findPreference(PREFERENCE_AUTOFIT_WIDTH);
        mAutofitWidth.setChecked(Ertebat.autofitWidth());

        mQuietTimeEnabled = (CheckBoxPreference) findPreference(PREFERENCE_QUIET_TIME_ENABLED);
        mQuietTimeEnabled.setChecked(Ertebat.getQuietTimeEnabled());

        mQuietTimeStarts = (TimePickerPreference) findPreference(PREFERENCE_QUIET_TIME_STARTS);
        mQuietTimeStarts.setDefaultValue(Ertebat.getQuietTimeStarts());
        mQuietTimeStarts.setSummary(Ertebat.getQuietTimeStarts());
        mQuietTimeStarts.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final String time = (String) newValue;
                mQuietTimeStarts.setSummary(time);
                return false;
            }
        });

        mQuietTimeEnds = (TimePickerPreference) findPreference(PREFERENCE_QUIET_TIME_ENDS);
        mQuietTimeEnds.setSummary(Ertebat.getQuietTimeEnds());
        mQuietTimeEnds.setDefaultValue(Ertebat.getQuietTimeEnds());
        mQuietTimeEnds.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final String time = (String) newValue;
                mQuietTimeEnds.setSummary(time);
                return false;
            }
        });

        mNotificationQuickDelete = setupListPreference(PREFERENCE_NOTIF_QUICK_DELETE,
                Ertebat.getNotificationQuickDeleteBehaviour().toString());
        if (!MessagingController.platformSupportsExtendedNotifications()) {
            PreferenceScreen prefs = (PreferenceScreen) findPreference("notification_preferences");
            prefs.removePreference(mNotificationQuickDelete);
            mNotificationQuickDelete = null;
        }

//        mBackgroundOps = setupListPreference(PREFERENCE_BACKGROUND_OPS, Ertebat.getBackgroundOps().name());

//        mDebugLogging = (CheckBoxPreference)findPreference(PREFERENCE_DEBUG_LOGGING);
//        mSensitiveLogging = (CheckBoxPreference)findPreference(PREFERENCE_SENSITIVE_LOGGING);
//        mHideUserAgent = (CheckBoxPreference)findPreference(PREFERENCE_HIDE_USERAGENT);
//        mHideTimeZone = (CheckBoxPreference)findPreference(PREFERENCE_HIDE_TIMEZONE);

//        mDebugLogging.setChecked(Ertebat.DEBUG);
//        mSensitiveLogging.setChecked(Ertebat.DEBUG_SENSITIVE);
//        mHideUserAgent.setChecked(Ertebat.hideUserAgent());
//        mHideTimeZone.setChecked(Ertebat.hideTimeZone());

//        mAttachmentPathPreference = findPreference(PREFERENCE_ATTACHMENT_DEF_PATH);
//        mAttachmentPathPreference.setSummary(Ertebat.getAttachmentDefaultPath());
//        mAttachmentPathPreference
//        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                FileBrowserHelper
//                .getInstance()
//                .showFileBrowserActivity(Prefs.this,
//                                         new File(Ertebat.getAttachmentDefaultPath()),
//                                         ACTIVITY_CHOOSE_FOLDER, callback);
//
//                return true;
//            }
//
//            FileBrowserFailOverCallback callback = new FileBrowserFailOverCallback() {
//
//                @Override
//                public void onPathEntered(String path) {
//                    mAttachmentPathPreference.setSummary(path);
//                    Ertebat.setAttachmentDefaultPath(path);
//                }
//
//                @Override
//                public void onCancel() {
//                    // canceled, do nothing
//                }
//            };
//        });

        mWrapFolderNames = (CheckBoxPreference)findPreference(PREFERENCE_FOLDERLIST_WRAP_NAME);
        mWrapFolderNames.setChecked(Ertebat.wrapFolderNames());

        mVisibleRefileActions = (CheckBoxListPreference) findPreference(PREFERENCE_MESSAGEVIEW_VISIBLE_REFILE_ACTIONS);
        CharSequence[] visibleRefileActionsEntries = new CharSequence[5];
        visibleRefileActionsEntries[VISIBLE_REFILE_ACTIONS_DELETE] = getString(R.string.delete_action);
        visibleRefileActionsEntries[VISIBLE_REFILE_ACTIONS_ARCHIVE] = getString(R.string.archive_action);
        visibleRefileActionsEntries[VISIBLE_REFILE_ACTIONS_MOVE] = getString(R.string.move_action);
        visibleRefileActionsEntries[VISIBLE_REFILE_ACTIONS_COPY] = getString(R.string.copy_action);
        visibleRefileActionsEntries[VISIBLE_REFILE_ACTIONS_SPAM] = getString(R.string.spam_action);

        boolean[] visibleRefileActionsValues = new boolean[5];
        visibleRefileActionsValues[VISIBLE_REFILE_ACTIONS_DELETE] = Ertebat.isMessageViewDeleteActionVisible();
        visibleRefileActionsValues[VISIBLE_REFILE_ACTIONS_ARCHIVE] = Ertebat.isMessageViewArchiveActionVisible();
        visibleRefileActionsValues[VISIBLE_REFILE_ACTIONS_MOVE] = Ertebat.isMessageViewMoveActionVisible();
        visibleRefileActionsValues[VISIBLE_REFILE_ACTIONS_COPY] = Ertebat.isMessageViewCopyActionVisible();
        visibleRefileActionsValues[VISIBLE_REFILE_ACTIONS_SPAM] = Ertebat.isMessageViewSpamActionVisible();

        mVisibleRefileActions.setItems(visibleRefileActionsEntries);
        mVisibleRefileActions.setCheckedItems(visibleRefileActionsValues);

//        mSplitViewMode = (ListPreference) findPreference(PREFERENCE_SPLITVIEW_MODE);
//        initListPreference(mSplitViewMode, Ertebat.getSplitViewMode().name(),
//                mSplitViewMode.getEntries(), mSplitViewMode.getEntryValues());
    }

    private static String themeIdToName(Ertebat.Theme theme) {
        switch (theme) {
            case DARK: return "dark";
            case USE_GLOBAL: return "global";
            default: return "light";
        }
    }

    private static Ertebat.Theme themeNameToId(String theme) {
        if (TextUtils.equals(theme, "dark")) {
            return Ertebat.Theme.DARK;
        } else if (TextUtils.equals(theme, "global")) {
            return Ertebat.Theme.USE_GLOBAL;
        } else {
            return Ertebat.Theme.LIGHT;
        }
    }

    private void saveSettings() {
        SharedPreferences preferences = Preferences.getPreferences(this).getPreferences();

        Ertebat.setErtebatLanguage(mLanguage.getValue());

        Ertebat.setErtebatTheme(themeNameToId(mTheme.getValue()));
        Ertebat.setUseFixedMessageViewTheme(mFixedMessageTheme.isChecked());
        Ertebat.setErtebatMessageViewThemeSetting(themeNameToId(mMessageTheme.getValue()));
        Ertebat.setErtebatComposerThemeSetting(themeNameToId(mComposerTheme.getValue()));

//        Ertebat.setAnimations(mAnimations.isChecked());
//        Ertebat.setGesturesEnabled(mGestures.isChecked());
//        Ertebat.setUseVolumeKeysForNavigation(mVolumeNavigation.getCheckedItems()[0]);
//        Ertebat.setUseVolumeKeysForListNavigation(mVolumeNavigation.getCheckedItems()[1]);
//        Ertebat.setStartIntegratedInbox(!mHideSpecialAccounts.isChecked() && mStartIntegratedInbox.isChecked());
//        Ertebat.setNotificationHideSubject(NotificationHideSubject.valueOf(mNotificationHideSubject.getValue()));

//        int index = 0;
//        Ertebat.setConfirmDelete(mConfirmActions.getCheckedItems()[index++]);
//        Ertebat.setConfirmDeleteStarred(mConfirmActions.getCheckedItems()[index++]);
//        if (MessagingController.platformSupportsExtendedNotifications()) {
//            Ertebat.setConfirmDeleteFromNotification(mConfirmActions.getCheckedItems()[index++]);
//        }
//        Ertebat.setConfirmSpam(mConfirmActions.getCheckedItems()[index++]);

        Ertebat.setMeasureAccounts(mMeasureAccounts.isChecked());
        Ertebat.setCountSearchMessages(mCountSearch.isChecked());
        Ertebat.setHideSpecialAccounts(mHideSpecialAccounts.isChecked());
        Ertebat.setMessageListPreviewLines(Integer.parseInt(mPreviewLines.getValue()));
        Ertebat.setMessageListCheckboxes(mCheckboxes.isChecked());
        Ertebat.setMessageListStars(mStars.isChecked());
        Ertebat.setShowCorrespondentNames(mShowCorrespondentNames.isChecked());
        Ertebat.setMessageListSenderAboveSubject(mSenderAboveSubject.isChecked());
        Ertebat.setShowContactName(mShowContactName.isChecked());
        Ertebat.setShowContactPicture(mShowContactPicture.isChecked());
        Ertebat.setColorizeMissingContactPictures(mColorizeMissingContactPictures.isChecked());
        Ertebat.setUseBackgroundAsUnreadIndicator(mBackgroundAsUnreadIndicator.isChecked());
//        Ertebat.setThreadedViewEnabled(mThreadedView.isChecked());
        Ertebat.setChangeContactNameColor(mChangeContactNameColor.isChecked());
        Ertebat.setMessageViewFixedWidthFont(mFixedWidth.isChecked());
//        Ertebat.setMessageViewReturnToList(mReturnToList.isChecked());
//        Ertebat.setMessageViewShowNext(mShowNext.isChecked());
        Ertebat.setAutofitWidth(mAutofitWidth.isChecked());
        Ertebat.setQuietTimeEnabled(mQuietTimeEnabled.isChecked());

        boolean[] enabledRefileActions = mVisibleRefileActions.getCheckedItems();
        Ertebat.setMessageViewDeleteActionVisible(enabledRefileActions[VISIBLE_REFILE_ACTIONS_DELETE]);
        Ertebat.setMessageViewArchiveActionVisible(enabledRefileActions[VISIBLE_REFILE_ACTIONS_ARCHIVE]);
        Ertebat.setMessageViewMoveActionVisible(enabledRefileActions[VISIBLE_REFILE_ACTIONS_MOVE]);
        Ertebat.setMessageViewCopyActionVisible(enabledRefileActions[VISIBLE_REFILE_ACTIONS_COPY]);
        Ertebat.setMessageViewSpamActionVisible(enabledRefileActions[VISIBLE_REFILE_ACTIONS_SPAM]);

        Ertebat.setQuietTimeStarts(mQuietTimeStarts.getTime());
        Ertebat.setQuietTimeEnds(mQuietTimeEnds.getTime());
        Ertebat.setWrapFolderNames(mWrapFolderNames.isChecked());

        if (mNotificationQuickDelete != null) {
            Ertebat.setNotificationQuickDeleteBehaviour(
                    NotificationQuickDelete.valueOf(mNotificationQuickDelete.getValue()));
        }

//        Ertebat.setSplitViewMode(SplitViewMode.valueOf(mSplitViewMode.getValue()));
//        Ertebat.setAttachmentDefaultPath(mAttachmentPathPreference.getSummary().toString());
//        boolean needsRefresh = Ertebat.setBackgroundOps(mBackgroundOps.getValue());

//        if (!Ertebat.DEBUG && mDebugLogging.isChecked()) {
//            Toast.makeText(this, R.string.debug_logging_enabled, Toast.LENGTH_LONG).show();
//        }
//        Ertebat.DEBUG = mDebugLogging.isChecked();
//        Ertebat.DEBUG_SENSITIVE = mSensitiveLogging.isChecked();
//        Ertebat.setHideUserAgent(mHideUserAgent.isChecked());
//        Ertebat.setHideTimeZone(mHideTimeZone.isChecked());

        Editor editor = preferences.edit();
        Ertebat.save(editor);
        editor.commit();

//        if (needsRefresh) {
//            MailService.actionReset(this, null);
//        }
    }

    @Override
    protected void onPause() {
        saveSettings();
        super.onPause();
    }

    private void onFontSizeSettings() {
        FontSizeSettings.actionEditSettings(this);
    }

    private void onChooseContactNameColor() {
        new ColorPickerDialog(this, new ColorPickerDialog.OnColorChangedListener() {
            public void colorChanged(int color) {
                Ertebat.setContactNameColor(color);
            }
        },
        Ertebat.getContactNameColor()).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case ACTIVITY_CHOOSE_FOLDER:
            if (resultCode == RESULT_OK && data != null) {
                // obtain the filename
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    String filePath = fileUri.getPath();
                    if (filePath != null) {
//                        mAttachmentPathPreference.setSummary(filePath.toString());
                        Ertebat.setAttachmentDefaultPath(filePath.toString());
                    }
                }
            }
            break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
