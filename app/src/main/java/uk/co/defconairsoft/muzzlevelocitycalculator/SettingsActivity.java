package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.Settings;
import uk.co.defconairsoft.muzzlevelocitycalculator.utils.PreferencesHelper;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen() {

        addPreferencesFromResource(R.xml.pref_general);
        PreferenceCategory fakeHeader;

        fakeHeader = new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_target);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_target);

        fakeHeader = new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_pellet);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_pellet);

        fakeHeader = new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_gun);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_gun);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences to
        // their values. When their values change, their summaries are updated
        // to reflect the new value, per the Android Design guidelines.
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_gun_correction)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_gun_barrel_length)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_pellet_diameter)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_pellet_mass)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_target_distance)));

    }

    @Override
    public boolean onIsMultiPane() {
        return false;
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    public static Settings getSettings(Context context){
        Settings settings = new Settings();
        PreferencesHelper helper = new PreferencesHelper(context);
        double diameter = helper.getDouble(R.string.pref_key_pellet_diameter, 6D);
        diameter = diameter/1000D;
        double mass = helper.getDouble(R.string.pref_key_pellet_mass,0.25D);
        mass = mass/1000D;
        double distance = helper.getDouble(R.string.pref_key_target_distance,10D);
        double barrel = helper.getDouble(R.string.pref_key_gun_barrel_length,480D);
        barrel = barrel/1000D;
        double correction = helper.getDouble(R.string.pref_key_gun_correction,20D);
        int threshold = helper.getInt(R.string.pref_key_threshold,5000);
        settings.pelletDiameter = diameter;
        settings.pelletMass = mass;
        settings.barrelLength = barrel;
        settings.correction = correction;
        settings.targetDistance = distance;
        settings.threshold = threshold;
        return settings;
    }

    public static void storeThreshold(Context context, int threshold){
        PreferencesHelper helper = new PreferencesHelper(context);
        helper.setInt(R.string.pref_key_threshold,threshold);
    }

}
