/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.cyanogenmod;

<<<<<<< HEAD
import static com.android.internal.util.cm.QSConstants.TILE_BLUETOOTH;
import static com.android.internal.util.cm.QSConstants.TILE_CAMERA;
import static com.android.internal.util.cm.QSConstants.TILE_MOBILEDATA;
import static com.android.internal.util.cm.QSConstants.TILE_NETWORKMODE;
import static com.android.internal.util.cm.QSConstants.TILE_NFC;
import static com.android.internal.util.cm.QSConstants.TILE_PROFILE;
import static com.android.internal.util.cm.QSConstants.TILE_WIFIAP;
import static com.android.internal.util.cm.QSConstants.TILE_LTE;
import static com.android.internal.util.cm.QSConstants.TILE_TORCH;
import static com.android.internal.util.cm.QSConstants.TILE_FCHARGE;
import static com.android.internal.util.cm.QSConstants.TILE_EXPANDEDDESKTOP;
import static com.android.internal.util.cm.QSConstants.TILE_HYBRID;
import static com.android.internal.util.cm.QSUtils.deviceSupportsBluetooth;
import static com.android.internal.util.cm.QSUtils.deviceSupportsDockBattery;
import static com.android.internal.util.cm.QSUtils.deviceSupportsImeSwitcher;
import static com.android.internal.util.cm.QSUtils.deviceSupportsLte;
import static com.android.internal.util.cm.QSUtils.deviceSupportsMobileData;
import static com.android.internal.util.cm.QSUtils.deviceSupportsNfc;
import static com.android.internal.util.cm.QSUtils.deviceSupportsUsbTether;
import static com.android.internal.util.cm.QSUtils.deviceSupportsWifiDisplay;
import static com.android.internal.util.cm.QSUtils.systemProfilesEnabled;
import static com.android.internal.util.cm.QSUtils.expandedDesktopEnabled;
=======
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
>>>>>>> upstream/cm-10.2

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.text.TextUtils;

<<<<<<< HEAD
import com.android.internal.telephony.Phone;
=======
import com.android.internal.util.cm.QSConstants;
>>>>>>> upstream/cm-10.2
import com.android.internal.util.cm.QSUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

<<<<<<< HEAD
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class QuickSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {
    private static final String TAG = "QuickSettings";
=======
public class QuickSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "QuickSettingsPanel";
>>>>>>> upstream/cm-10.2

    private static final String SEPARATOR = "OV=I=XseparatorX=I=VO";
    private static final String EXP_RING_MODE = "pref_ring_mode";
    private static final String EXP_NETWORK_MODE = "pref_network_mode";
    private static final String EXP_SCREENTIMEOUT_MODE = "pref_screentimeout_mode";
    private static final String QUICK_PULLDOWN = "quick_pulldown";
    private static final String GENERAL_SETTINGS = "pref_general_settings";
    private static final String STATIC_TILES = "static_tiles";
    private static final String DYNAMIC_TILES = "pref_dynamic_tiles";
    private static final String PREF_FLIP_QS_TILES = "flip_qs_tiles";

    public static final String FAST_CHARGE_DIR = "/sys/kernel/fast_charge";
    public static final String FAST_CHARGE_FILE = "force_fast_charge"; 

<<<<<<< HEAD
    MultiSelectListPreference mRingMode;
    ListPreference mNetworkMode;
    ListPreference mScreenTimeoutMode;
    CheckBoxPreference mDynamicAlarm;
    CheckBoxPreference mDynamicBugReport;
    CheckBoxPreference mDynamicDockBattery;
    CheckBoxPreference mDynamicWifi;
    CheckBoxPreference mDynamicIme;
    CheckBoxPreference mDynamicUsbTether;
    CheckBoxPreference mCollapsePanel;
    CheckBoxPreference mFlipQsTiles;
    ListPreference mQuickPulldown;
    PreferenceCategory mGeneralSettings;
    PreferenceCategory mStaticTiles;
    PreferenceCategory mDynamicTiles;
=======
    private MultiSelectListPreference mRingMode;
    private ListPreference mNetworkMode;
    private ListPreference mScreenTimeoutMode;
    private ListPreference mQuickPulldown;
    private PreferenceCategory mGeneralSettings;
    private PreferenceCategory mStaticTiles;
    private PreferenceCategory mDynamicTiles;
>>>>>>> upstream/cm-10.2

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.quick_settings_panel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();
        mGeneralSettings = (PreferenceCategory) prefSet.findPreference(GENERAL_SETTINGS);
        mStaticTiles = (PreferenceCategory) prefSet.findPreference(STATIC_TILES);
        mDynamicTiles = (PreferenceCategory) prefSet.findPreference(DYNAMIC_TILES);
        mQuickPulldown = (ListPreference) prefSet.findPreference(QUICK_PULLDOWN);

        if (!Utils.isPhone(getActivity())) {
            if (mQuickPulldown != null) {
                mGeneralSettings.removePreference(mQuickPulldown);
            }
        } else {
            mQuickPulldown.setOnPreferenceChangeListener(this);
            int quickPulldownValue = Settings.System.getInt(resolver,
                    Settings.System.QS_QUICK_PULLDOWN, 0);
            mQuickPulldown.setValue(String.valueOf(quickPulldownValue));
            updatePulldownSummary(quickPulldownValue);
        }

        // Add the sound mode
        mRingMode = (MultiSelectListPreference) prefSet.findPreference(EXP_RING_MODE);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            String storedRingMode = Settings.System.getString(resolver,
                    Settings.System.EXPANDED_RING_MODE);
            if (storedRingMode != null) {
                String[] ringModeArray = TextUtils.split(storedRingMode, SEPARATOR);
                mRingMode.setValues(new HashSet<String>(Arrays.asList(ringModeArray)));
                updateSummary(storedRingMode, mRingMode, R.string.pref_ring_mode_summary);
            }
            mRingMode.setOnPreferenceChangeListener(this);
        } else {
            mStaticTiles.removePreference(mRingMode);
        }

        mFlipQsTiles = (CheckBoxPreference) findPreference(PREF_FLIP_QS_TILES);
        mFlipQsTiles.setChecked(Settings.System.getInt(resolver,
                Settings.System.QUICK_SETTINGS_TILES_FLIP, 1) == 1);

        // Add the network mode preference
        mNetworkMode = (ListPreference) prefSet.findPreference(EXP_NETWORK_MODE);
        if (mNetworkMode != null) {
            mNetworkMode.setSummary(mNetworkMode.getEntry());
            mNetworkMode.setOnPreferenceChangeListener(this);
        }

        // Screen timeout mode
        mScreenTimeoutMode = (ListPreference) prefSet.findPreference(EXP_SCREENTIMEOUT_MODE);
        mScreenTimeoutMode.setSummary(mScreenTimeoutMode.getEntry());
        mScreenTimeoutMode.setOnPreferenceChangeListener(this);

<<<<<<< HEAD
        // Add the dynamic tiles checkboxes
        mDynamicAlarm = (CheckBoxPreference) prefSet.findPreference(DYNAMIC_ALARM);
        mDynamicAlarm.setChecked(Settings.System.getInt(resolver, Settings.System.QS_DYNAMIC_ALARM, 1) == 1);
        mDynamicBugReport = (CheckBoxPreference) prefSet.findPreference(DYNAMIC_BUGREPORT);
        mDynamicBugReport.setChecked(Settings.System.getInt(resolver, Settings.System.QS_DYNAMIC_BUGREPORT, 1) == 1);
        mDynamicDockBattery = (CheckBoxPreference) prefSet.findPreference(DYNAMIC_DOCK_BATTERY);
        if (mDynamicDockBattery != null) {
            if (QSUtils.deviceSupportsDockBattery(getActivity())) {
                mDynamicDockBattery.setChecked(Settings.System.getInt(resolver, Settings.System.QS_DYNAMIC_DOCK_BATTERY, 1) == 1);
            } else {
                mDynamicTiles.removePreference(mDynamicDockBattery);
                mDynamicDockBattery = null;
            }
        }
        mDynamicIme = (CheckBoxPreference) prefSet.findPreference(DYNAMIC_IME);
        if (mDynamicIme != null) {
            if (QSUtils.deviceSupportsImeSwitcher(getActivity())) {
                mDynamicIme.setChecked(Settings.System.getInt(resolver, Settings.System.QS_DYNAMIC_IME, 1) == 1);
            } else {
                mDynamicTiles.removePreference(mDynamicIme);
                mDynamicIme = null;
            }
        }
        mDynamicUsbTether = (CheckBoxPreference) prefSet.findPreference(DYNAMIC_USBTETHER);
        if (mDynamicUsbTether != null) {
            if (QSUtils.deviceSupportsUsbTether(getActivity())) {
                mDynamicUsbTether.setChecked(Settings.System.getInt(resolver, Settings.System.QS_DYNAMIC_USBTETHER, 1) == 1);
            } else {
                mDynamicTiles.removePreference(mDynamicUsbTether);
                mDynamicUsbTether = null;
            }
        }
        mDynamicWifi = (CheckBoxPreference) prefSet.findPreference(DYNAMIC_WIFI);
        if (mDynamicWifi != null) {
            if (QSUtils.deviceSupportsWifiDisplay(getActivity())) {
                mDynamicWifi.setChecked(Settings.System.getInt(resolver, Settings.System.QS_DYNAMIC_WIFI, 1) == 1);
            } else {
                mDynamicTiles.removePreference(mDynamicWifi);
                mDynamicWifi = null;
=======
        // Remove unsupported options
        if (!QSUtils.deviceSupportsDockBattery(getActivity())) {
            Preference pref = findPreference(Settings.System.QS_DYNAMIC_DOCK_BATTERY);
            if (pref != null) {
                mDynamicTiles.removePreference(pref);
            }
        }
        if (!QSUtils.deviceSupportsImeSwitcher(getActivity())) {
            Preference pref = findPreference(Settings.System.QS_DYNAMIC_IME);
            if (pref != null) {
                mDynamicTiles.removePreference(pref);
            }
        }
        if (!QSUtils.deviceSupportsUsbTether(getActivity())) {
            Preference pref = findPreference(Settings.System.QS_DYNAMIC_USBTETHER);
            if (pref != null) {
                mDynamicTiles.removePreference(pref);
            }
        }
        if (!QSUtils.deviceSupportsWifiDisplay(getActivity())) {
            Preference pref = findPreference(Settings.System.QS_DYNAMIC_WIFI);
            if (pref != null) {
                mDynamicTiles.removePreference(pref);
>>>>>>> upstream/cm-10.2
            }
        }
    }

<<<<<<< HEAD
        // Don't show mobile data options if not supported
        if (!QSUtils.deviceSupportsMobileData(getActivity())) {
            QuickSettingsUtil.TILES.remove(TILE_MOBILEDATA);
            QuickSettingsUtil.TILES.remove(TILE_WIFIAP);
            QuickSettingsUtil.TILES.remove(TILE_NETWORKMODE);
            if(mNetworkMode != null) {
                mStaticTiles.removePreference(mNetworkMode);
            }
        } else {
            // We have telephony support however, some phones run on networks not supported
            // by the networkmode tile so remove both it and the associated options list
            int network_state = -99;
            try {
                network_state = Settings.Global.getInt(resolver,
                        Settings.Global.PREFERRED_NETWORK_MODE);
            } catch (Settings.SettingNotFoundException e) {
                Log.e(TAG, "Unable to retrieve PREFERRED_NETWORK_MODE", e);
            }
=======
    @Override
    public void onResume() {
        super.onResume();
        QuickSettingsUtil.updateAvailableTiles(getActivity());
>>>>>>> upstream/cm-10.2

        if (mNetworkMode != null) {
            if (QuickSettingsUtil.isTileAvailable(QSConstants.TILE_NETWORKMODE)) {
                mStaticTiles.addPreference(mNetworkMode);
            } else {
                mStaticTiles.removePreference(mNetworkMode);
            }
        }
<<<<<<< HEAD

        // Don't show the bluetooth options if not supported
        if (!QSUtils.deviceSupportsBluetooth()) {
            QuickSettingsUtil.TILES.remove(TILE_BLUETOOTH);
        }

        // Don't show the profiles tile if profiles are disabled
        if (!QSUtils.systemProfilesEnabled(resolver)) {
            QuickSettingsUtil.TILES.remove(TILE_PROFILE);
        }

        // Don't show the NFC tile if not supported
        if (!QSUtils.deviceSupportsNfc(getActivity())) {
            QuickSettingsUtil.TILES.remove(TILE_NFC);
        }

        // Don't show the LTE tile if not supported
        if (!QSUtils.deviceSupportsLte(getActivity())) {
            QuickSettingsUtil.TILES.remove(TILE_LTE);
        }

        // Don't show the Torch tile if not supported
        if (!getResources().getBoolean(R.bool.has_led_flash)) {
            QuickSettingsUtil.TILES.remove(TILE_TORCH);
        }

        // Dont show fast charge tile if not supported
        File fastcharge = new File(FAST_CHARGE_DIR, FAST_CHARGE_FILE);
        if (!fastcharge.exists()) {
            QuickSettingsUtil.TILES.remove(TILE_FCHARGE);
        }

        // Don't show the Expanded desktop tile if expanded desktop is disabled
        if (!QSUtils.expandedDesktopEnabled(resolver)) {
            QuickSettingsUtil.TILES.remove(TILE_EXPANDEDDESKTOP);
        }

        // Don't show the Camera tile if the device has no cameras
        if (!QSUtils.deviceSupportsCamera()) {
            QuickSettingsUtil.TILES.remove(TILE_CAMERA);
        }
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mDynamicAlarm) {
            Settings.System.putInt(resolver, Settings.System.QS_DYNAMIC_ALARM,
                    mDynamicAlarm.isChecked() ? 1 : 0);
            return true;
        } else if (preference == mDynamicBugReport) {
            Settings.System.putInt(resolver, Settings.System.QS_DYNAMIC_BUGREPORT,
                    mDynamicBugReport.isChecked() ? 1 : 0);
            return true;
        } else if (mDynamicDockBattery != null && preference == mDynamicDockBattery) {
            Settings.System.putInt(resolver, Settings.System.QS_DYNAMIC_DOCK_BATTERY,
                    mDynamicDockBattery.isChecked() ? 1 : 0);
            return true;
        } else if (mDynamicIme != null && preference == mDynamicIme) {
            Settings.System.putInt(resolver, Settings.System.QS_DYNAMIC_IME,
                    mDynamicIme.isChecked() ? 1 : 0);
            return true;
        } else if (mDynamicUsbTether != null && preference == mDynamicUsbTether) {
            Settings.System.putInt(resolver, Settings.System.QS_DYNAMIC_USBTETHER,
                    mDynamicUsbTether.isChecked() ? 1 : 0);
            return true;
        } else if (mDynamicWifi != null && preference == mDynamicWifi) {
            Settings.System.putInt(resolver, Settings.System.QS_DYNAMIC_WIFI,
                    mDynamicWifi.isChecked() ? 1 : 0);
            return true;
        } else if (preference == mCollapsePanel) {
            Settings.System.putInt(resolver, Settings.System.QS_COLLAPSE_PANEL,
                    mCollapsePanel.isChecked() ? 1 : 0);
            return true;
        } else if (preference == mFlipQsTiles) {
            Settings.System.putInt(resolver,
                    Settings.System.QUICK_SETTINGS_TILES_FLIP,
                    ((CheckBoxPreference) preference).isChecked() ? 1 : 0);
            return true; 
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
=======
>>>>>>> upstream/cm-10.2
    }

    private class MultiSelectListPreferenceComparator implements Comparator<String> {
        private MultiSelectListPreference pref;

        MultiSelectListPreferenceComparator(MultiSelectListPreference p) {
            pref = p;
        }

        @Override
        public int compare(String lhs, String rhs) {
            return Integer.compare(pref.findIndexOfValue(lhs),
                    pref.findIndexOfValue(rhs));
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getContentResolver();
        if (preference == mRingMode) {
            ArrayList<String> arrValue = new ArrayList<String>((Set<String>) newValue);
            Collections.sort(arrValue, new MultiSelectListPreferenceComparator(mRingMode));
            String value = TextUtils.join(SEPARATOR, arrValue);
            Settings.System.putString(resolver, Settings.System.EXPANDED_RING_MODE, value);
            updateSummary(value, mRingMode, R.string.pref_ring_mode_summary);
            return true;
        } else if (preference == mNetworkMode) {
            int value = Integer.valueOf((String) newValue);
            int index = mNetworkMode.findIndexOfValue((String) newValue);
            Settings.System.putInt(resolver, Settings.System.EXPANDED_NETWORK_MODE, value);
            mNetworkMode.setSummary(mNetworkMode.getEntries()[index]);
            return true;
        } else if (preference == mQuickPulldown) {
            int quickPulldownValue = Integer.valueOf((String) newValue);
            Settings.System.putInt(resolver, Settings.System.QS_QUICK_PULLDOWN,
                    quickPulldownValue);
            updatePulldownSummary(quickPulldownValue);
            return true;
        } else if (preference == mScreenTimeoutMode) {
            int value = Integer.valueOf((String) newValue);
            int index = mScreenTimeoutMode.findIndexOfValue((String) newValue);
            Settings.System.putInt(resolver, Settings.System.EXPANDED_SCREENTIMEOUT_MODE, value);
            mScreenTimeoutMode.setSummary(mScreenTimeoutMode.getEntries()[index]);
            return true;
        }
        return false;
    }

    private void updateSummary(String val, MultiSelectListPreference pref, int defSummary) {
        // Update summary message with current values
        final String[] values = parseStoredValue(val);
        if (values != null) {
            final int length = values.length;
            final CharSequence[] entries = pref.getEntries();
            StringBuilder summary = new StringBuilder();
            for (int i = 0; i < length; i++) {
                CharSequence entry = entries[Integer.parseInt(values[i])];
                if (i != 0) {
                    summary.append(" | ");
                }
                summary.append(entry);
            }
            pref.setSummary(summary);
        } else {
            pref.setSummary(defSummary);
        }
    }

    private void updatePulldownSummary(int value) {
        Resources res = getResources();

        if (value == 0) {
            /* quick pulldown deactivated */
            mQuickPulldown.setSummary(res.getString(R.string.quick_pulldown_off));
        } else if (value == 3) {
            mQuickPulldown.setSummary(res.getString(R.string.quick_pulldown_summary_always));
        } else {
            String direction = res.getString(value == 2
                    ? R.string.quick_pulldown_summary_left
                    : R.string.quick_pulldown_summary_right);
            mQuickPulldown.setSummary(res.getString(R.string.summary_quick_pulldown, direction));
        }
    }

    public static String[] parseStoredValue(CharSequence val) {
        if (TextUtils.isEmpty(val)) {
            return null;
        } else {
            return val.toString().split(SEPARATOR);
        }
    }
}
