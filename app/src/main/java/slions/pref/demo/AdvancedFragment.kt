package slions.pref.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class AdvancedFragment : PreferenceFragmentCompat() {

    companion object {
        private const val ARG_MESSAGE = "message"
        private const val ARG_SCREEN = "screen"

        fun newInstance(message: String? = null, screenResId: Int = R.xml.pref_screen_error) = AdvancedFragment().apply {
            arguments = Bundle().apply {
                message?.let { putString(ARG_MESSAGE, it) }
                putInt(ARG_SCREEN, screenResId)
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Debug: Log what's in the arguments
        arguments?.let { args ->
            Toast.makeText(requireContext(),
                "Screen arg: ${args.getString(ARG_SCREEN)}",
                Toast.LENGTH_LONG).show()
        }

        // Get screen from extras (comes as string, either plain name or resolved path)
        val screenResId = arguments?.getString(ARG_SCREEN)?.let { screenValue ->
            resolveScreenResource(screenValue)
        } ?: R.xml.pref_screen_error

        setPreferencesFromResource(screenResId, rootKey)

        // Display welcome message if provided
        arguments?.getString(ARG_MESSAGE)?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }

        // Set up listeners for developer preferences
        setupDeveloperPreferences()
        setupDataPreferences()
    }

    @SuppressLint("DiscouragedApi")
    private fun resolveScreenResource(resourceValue: String): Int {
        // Handle different formats:
        // 1. "res/xml-v22/pref_screen_advanced.xml" (resolved path from @xml/ reference)
        // 2. "pref_screen_advanced" (plain resource name)

        val resourceName = when {
            // Extract name from resolved path: res/xml-v22/pref_screen_advanced.xml -> pref_screen_advanced
            resourceValue.startsWith("res/xml") -> {
                resourceValue.substringAfterLast("/").removeSuffix(".xml")
            }
            // Use as-is if it's just a plain name
            else -> resourceValue
        }

        // Get the resource ID from the resource name
        val resId = resources.getIdentifier(resourceName, "xml", requireContext().packageName)

        return if (resId != 0) {
            resId
        } else {
            Toast.makeText(requireContext(),
                "Screen resource not found: $resourceValue (tried: $resourceName)",
                Toast.LENGTH_LONG).show()
            R.xml.pref_screen_error
        }
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

