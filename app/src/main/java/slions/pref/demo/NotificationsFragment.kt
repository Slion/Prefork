package slions.pref.demo

import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class NotificationsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String? = null) = NotificationsFragment().apply {
            arguments = Bundle().apply {
                message?.let { putString(ARG_MESSAGE, it) }
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_screen_notifications, rootKey)

        // Display welcome message if provided
        arguments?.getString(ARG_MESSAGE)?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }

        // Set up listeners for notification preferences
        setupNotificationPreferences()
        setupDoNotDisturbPreferences()
    }

    private fun setupNotificationPreferences() {
        findPreference<SwitchPreferenceCompat>("push_notifications")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            val message = if (enabled) "Push notifications enabled" else "Push notifications disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<SwitchPreferenceCompat>("notification_sound")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            val message = if (enabled) "Notification sound enabled" else "Notification sound disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<SwitchPreferenceCompat>("notification_vibrate")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            val message = if (enabled) "Notification vibration enabled" else "Notification vibration disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<Preference>("notification_ringtone")?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(requireContext(), "Ringtone changed to: $newValue", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun setupDoNotDisturbPreferences() {
        findPreference<SwitchPreferenceCompat>("dnd_enabled")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            val message = if (enabled) "Do Not Disturb enabled" else "Do Not Disturb disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<EditTextPreference>("dnd_start_time")?.setOnPreferenceChangeListener { preference, newValue ->
            val time = newValue as String
            if (isValidTime(time)) {
                preference.summary = time
                Toast.makeText(requireContext(), "Start time set to: $time", Toast.LENGTH_SHORT).show()
                true
            } else {
                Toast.makeText(requireContext(), "Invalid time format. Use HH:MM", Toast.LENGTH_SHORT).show()
                false
            }
        }

        findPreference<EditTextPreference>("dnd_end_time")?.setOnPreferenceChangeListener { preference, newValue ->
            val time = newValue as String
            if (isValidTime(time)) {
                preference.summary = time
                Toast.makeText(requireContext(), "End time set to: $time", Toast.LENGTH_SHORT).show()
                true
            } else {
                Toast.makeText(requireContext(), "Invalid time format. Use HH:MM", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    private fun isValidTime(time: String): Boolean {
        val regex = Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
        return regex.matches(time)
    }
}

