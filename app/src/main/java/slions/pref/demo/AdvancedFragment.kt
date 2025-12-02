package slions.pref.demo

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class AdvancedFragment : PreferenceFragmentCompat() {

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String? = null) = AdvancedFragment().apply {
            arguments = Bundle().apply {
                message?.let { putString(ARG_MESSAGE, it) }
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_screen_advanced, rootKey)

        // Display welcome message if provided
        arguments?.getString(ARG_MESSAGE)?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }

        // Set up listeners for developer preferences
        setupDeveloperPreferences()
        setupDataPreferences()
    }

    private fun setupDeveloperPreferences() {
        findPreference<SwitchPreferenceCompat>("dev_mode")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            val message = if (enabled) "Developer mode enabled" else "Developer mode disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<SwitchPreferenceCompat>("debug_logging")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            val message = if (enabled) "Debug logging enabled" else "Debug logging disabled"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<Preference>("clear_cache")?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Cache cleared!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun setupDataPreferences() {
        findPreference<Preference>("export_settings")?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Settings exported!", Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<Preference>("import_settings")?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Settings imported!", Toast.LENGTH_SHORT).show()
            true
        }

        findPreference<Preference>("reset_settings")?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Settings reset to defaults!", Toast.LENGTH_SHORT).show()
            true
        }
    }
}

