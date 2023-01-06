package com.example.cs203bw.main;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.example.cs203bw.R;

public class Prefs extends PreferenceFragmentCompat {
    private static final String PLAY_SOUNDTRACK = "PLAY_SOUNDTRACK";
    private static final String PLAY_SOUND = "PLAY_SOUND";
    private static final String RAPID_FIRE_MISSILE = "RAPID_FIRE_MISSILE";
    private static final String RAPID_FIRE_DEPTHCHARGE = "RAPID_FIRE_DEPTHCHARGE";
    private static final String PLANE_SPEED_PREF = "PLANE_SPEED_PREF";
    private static final String SUB_SPEED_PREF = "SUB_SPEED_PREF";
    private static final String PLANE_DIRECTION_PREF = "PLANE_DIRECTION_PREF";
    private static final String SUB_DIRECTION_PREF = "SUB_DIRECTION_PREF";
    private static final String PLANE_ALTITUDE_PREF = "PLANE_ALTITUDE_PREF";
    private static final String SUB_DEPTH_PREF = "SUB_DEPTH_PREF";
    private static final String FRUGALITY_PREF = "FRUGALITY_PREF";

    @Override
    public void onCreatePreferences(Bundle b, String s) {
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        //TODO add preference widgets here
        //preference for background music
        var musicPref = new SwitchPreference(context);
        musicPref.setTitle(R.string.music_pref);
        musicPref.setSummaryOff(R.string.music_summary_off);
        musicPref.setSummaryOn(R.string.music_summary_on);
        musicPref.setKey(PLAY_SOUND);
        screen.addPreference(musicPref);

        //preference for sound effects
        var soundPref = new SwitchPreference(context);
        soundPref.setTitle(R.string.sound_pref);
        soundPref.setSummaryOff(R.string.sound_summary_off);
        soundPref.setSummaryOn(R.string.sound_summary_on);
        soundPref.setKey(PLAY_SOUNDTRACK);
        screen.addPreference(soundPref);

        //preference for rapid missile
        var missilePref = new SwitchPreference(context);
        missilePref.setTitle(R.string.missile_pref);
        missilePref.setSummaryOff(R.string.missile_summary_off);
        missilePref.setSummaryOn(R.string.sound_summary_on);
        missilePref.setKey(RAPID_FIRE_MISSILE);
        screen.addPreference(missilePref);

        //preference for rapid depth charge
        var depthPref = new SwitchPreference(context);
        depthPref.setTitle(R.string.depth_pref);
        depthPref.setSummaryOff(R.string.depth_summary_off);
        depthPref.setSummaryOn(R.string.depth_summary_on);
        depthPref.setKey(RAPID_FIRE_DEPTHCHARGE);
        screen.addPreference(depthPref);

        //preference for plane altitude
        var planeAltitude = new SwitchPreference(context);
        planeAltitude.setTitle(R.string.plane_pref);
        planeAltitude.setSummaryOff(R.string.plane_summary_off);
        planeAltitude.setSummaryOn(R.string.plane_summary_on);
        planeAltitude.setKey(PLANE_ALTITUDE_PREF);
        screen.addPreference(planeAltitude);

        //preference for submarine depth
        var subDepth = new SwitchPreference(context);
        subDepth.setTitle(R.string.sub_pref);
        subDepth.setSummaryOff(R.string.sub_summary_off);
        subDepth.setSummaryOn(R.string.sub_summary_on);
        subDepth.setKey(SUB_DEPTH_PREF);
        screen.addPreference(subDepth);

        //preference for frugality
        var frugalityPref = new SwitchPreference(context);
        frugalityPref.setTitle(R.string.frug_pref);
        frugalityPref.setSummaryOff(R.string.frug_summary_off);
        frugalityPref.setSummaryOn(R.string.frug_summary_on);
        frugalityPref.setKey(FRUGALITY_PREF);
        screen.addPreference(frugalityPref);

        //preference for plane speed
        var planeSpeedPref = new ListPreference(context);
        planeSpeedPref.setTitle(R.string.plane_speed_pref);
        planeSpeedPref.setSummary(R.string.plane_speed_summary);
        planeSpeedPref.setKey(PLANE_SPEED_PREF);
        planeSpeedPref.setEntries(R.array.speed_labels);
        planeSpeedPref.setEntryValues(new String[] {"15", "9", "3"});
        screen.addPreference(planeSpeedPref);

        //preference for submarine speed
        var subSpeedPref = new ListPreference(context);
        subSpeedPref.setTitle(R.string.sub_speed_pref);
        subSpeedPref.setSummary(R.string.sub_speed_summary);
        subSpeedPref.setKey(SUB_SPEED_PREF);
        subSpeedPref.setEntries(R.array.speed_labels);
        subSpeedPref.setEntryValues(new String[] {"15", "9", "3"});
        screen.addPreference(subSpeedPref);

        //preference for plane direction
        var planeDirectionPref = new ListPreference(context);
        planeDirectionPref.setTitle(R.string.plane_direction_pref);
        planeDirectionPref.setSummary(R.string.plane_direction_summary);
        planeDirectionPref.setKey(PLANE_DIRECTION_PREF);
        planeDirectionPref.setEntries(R.array.direction_labels);
        planeDirectionPref.setEntryValues(new String[] {"left", "right", "both"});
        screen.addPreference(planeDirectionPref);

        //preference for submarine direction
        var subDirectionPref = new ListPreference(context);
        subDirectionPref.setTitle(R.string.sub_direction_pref);
        subDirectionPref.setSummary(R.string.sub_direction_summary);
        subDirectionPref.setKey(SUB_DIRECTION_PREF);
        subDirectionPref.setEntries(R.array.direction_labels);
        subDirectionPref.setEntryValues(new String[] {"left", "right", "both"});
        screen.addPreference(subDirectionPref);

        setPreferenceScreen(screen);
    }

    /**
     * This method returns true if the user choose to play the background music
     * @param c
     * @return boolean
     */
    public static boolean soundFX(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(PLAY_SOUNDTRACK, true);
    }

    /**
     * This method returns true if the user choose to play the sound effects
     * @param c
     * @return boolean
     */
    public static boolean soundsFX(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(PLAY_SOUND, true);
    }

    /**
     * This method returns true if the user choose to shoot missiles rapidly
     * @param c
     * @return boolean
     */
    public static boolean rapidMissile(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(RAPID_FIRE_MISSILE, true);
    }

    /**
     * This method returns true if the user choose to shoot depth charges rapidly
     * @param c
     * @return boolean
     */
    public static boolean rapidDepth(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(RAPID_FIRE_DEPTHCHARGE, true);
    }

    /**
     * This method returns true if the user choose the plane to move up and down
     * @param c
     * @return boolean
     */
    public static boolean planeAltitude(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(PLANE_ALTITUDE_PREF, true);
    }

    /**
     * This method returns true if the user choose the submarine to move up and down
     * @param c
     * @return boolean
     */
    public static boolean subDepth(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(SUB_DEPTH_PREF, true);
    }

    /**
     * This method returns true if the user choose to loose point when they use missile or depth charge
     * @param c
     * @return boolean
     */
    public static boolean frugality(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(FRUGALITY_PREF, true);
    }

    /**
     * This method returns the chosen value for plane speed
     * @param c
     * @return int
     */
    public static int planeSpeed(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(PLANE_SPEED_PREF, "5");
        return Integer.parseInt(tmp);
    }

    /**
     * This method returns the chosen value for submarine speed
     * @param c
     * @return int
     */
    public static int subSpeed(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(SUB_SPEED_PREF, "5");
        return Integer.parseInt(tmp);
    }

    /**
     * This method returns the chosen value for plane direction
     * @param c
     * @return String
     */
    public static String planeDirection(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(PLANE_DIRECTION_PREF, "right");
        return tmp;
    }

    /**
     * This method returns the chosen value for submarine direction
     * @param c
     * @return String
     */
    public static String subDirection(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(SUB_DIRECTION_PREF, "right");
        return tmp;
    }
}