A fork of AndroidX Preference library

## Features

### Fragment Navigation with `android:fragment`

The `android:fragment` attribute enables declarative navigation to other preference screens without writing any Java/Kotlin code. When a preference with this attribute is clicked, the AndroidX Preference library automatically handles the fragment transaction.

#### Basic Usage

```xml
<Preference
    android:key="notifications"
    android:title="Notifications"
    android:summary="Notification settings"
    android:fragment="slions.pref.demo.NotificationsFragment" />
```

When the user taps this preference, the library will:
1. Automatically create an instance of `NotificationsFragment`
2. Replace the current fragment with the new one
3. Add the transaction to the back stack
4. Handle the back button navigation

#### Benefits

- **No code required** - Navigation is configured purely in XML
- **Automatic back stack management** - The library handles back navigation
- **Consistent behavior** - Standard navigation patterns applied automatically
- **Less boilerplate** - No need to write `OnPreferenceClickListener` or fragment transactions

### Passing Arguments to Fragments Using Extras

You can pass arguments to preference fragments directly in XML using the `<extra>` tag. This is useful for customizing fragment behavior without writing additional navigation code.

#### Example

In your preference XML file:

```xml
<Preference
    android:key="notifications"
    android:title="Notifications"
    android:summary="Notification settings"
    android:fragment="slions.pref.demo.NotificationsFragment">
    <extra
        android:name="message"
        android:value="Welcome to Notification Settings! 🔔" />
</Preference>
```

In your fragment:

```kotlin
class NotificationsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val ARG_MESSAGE = "message"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_screen_notifications, rootKey)

        // Read the extra from arguments
        arguments?.getString(ARG_MESSAGE)?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }
}
```

#### Multiple Extras

You can pass multiple extras to a fragment:

```xml
<Preference android:fragment="com.example.MyFragment">
    <extra android:name="message" android:value="Hello World!" />
    <extra android:name="userId" android:value="12345" />
    <extra android:name="isAdmin" android:value="true" />
</Preference>
```

#### How It Works

1. The AndroidX Preference library automatically bundles all `<extra>` tags into the fragment's arguments Bundle
2. The fragment can access these values via `arguments?.getString("key")`, `arguments?.getInt("key")`, etc.
3. No additional navigation code is required - the library handles everything automatically

This approach provides:
- **Declarative configuration** - All fragment parameters defined in one place
- **Type safety** - Use companion object constants for keys
- **Clean separation** - No need for manual fragment transactions or Bundle building

## References

- [AndroidX Preference Library - Official Documentation](https://developer.android.com/jetpack/androidx/releases/preference)
- [Settings Guide - Android Developers](https://developer.android.com/develop/ui/views/components/settings)



