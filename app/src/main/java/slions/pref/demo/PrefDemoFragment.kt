package slions.pref.demo

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class PrefDemoFragment : PreferenceFragmentCompat() {

    companion object {
        private const val ARG_XML_RES = "arg_xml_res"

        fun newInstance(xmlRes: Int = R.xml.pref_screen_root) = PrefDemoFragment().apply {
            arguments = bundleOf(ARG_XML_RES to xmlRes)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val xmlRes = arguments?.getInt(ARG_XML_RES) ?: R.xml.pref_screen_root
        setPreferencesFromResource(xmlRes, rootKey)

        // Set up navigation to other preference screens

        findPreference<Preference>("advanced")?.setOnPreferenceClickListener {
            navigateToScreen(R.xml.pref_screen_advanced, "Advanced")
            true
        }

        // Set up click handlers for action preferences
        findPreference<Preference>("clear_cache")?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Cache cleared!", Toast.LENGTH_SHORT).show()
            true
        }

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

    private fun navigateToScreen(xmlRes: Int, title: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, newInstance(xmlRes))
            .addToBackStack(title)
            .commit()
    }
}

