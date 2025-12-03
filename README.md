# AndroidX Preference Fork

A fork of the AndroidX Preference library for testing and debugging purposes.
It includes a demo application showcasing various preference features and usage patterns.

## Introduction

- **Purpose**: Testing and debugging AndroidX Preference v1.2.1
- **Source**: Based on [AndroidX Preference source code](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-main/preference/preference/) at commit `e0c0cab4fa744ed179ac45be46196a75765ae0e1`
- **Repository**: https://github.com/Slion/AndroidX.Preference
- **Note**: This is not a modified version of the library - it's the original source used for testing with a companion demo app

## Features

### XML Namespace Convention

In preference XML files, you can use a shorter namespace prefix like `a` instead of `android` to reduce verbosity:

```xml
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:a="http://schemas.android.com/apk/res/android">
    <Preference
        a:key="my_preference"
        a:title="My Preference" />
</PreferenceScreen>
```

This is equivalent to using `android:` but more concise. Both examples in this documentation use the `a` prefix for brevity.

### Fragment Navigation with `android:fragment`

The `android:fragment` attribute enables declarative navigation to other preference screens without writing any Java/Kotlin code. When a preference with this attribute is clicked, the AndroidX Preference library automatically handles the fragment transaction.

#### Basic Usage

```xml
<Preference
    a:key="notifications"
    a:title="Notifications"
    a:summary="Notification settings"
    a:fragment="slions.pref.demo.NotificationsFragment" />
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
    a:key="notifications"
    a:title="Notifications"
    a:summary="Notification settings"
    a:fragment="slions.pref.demo.NotificationsFragment">
    <extra
        a:name="message"
        a:value="Welcome to Notification Settings! 🔔" />
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
<Preference a:fragment="com.example.MyFragment">
    <extra a:name="message" a:value="Hello World!" />
    <extra a:name="userId" a:value="12345" />
    <extra a:name="isAdmin" a:value="true" />
</Preference>
```

#### Using String Resources

You can pass string resource IDs instead of hardcoded strings for better localization:

**In strings.xml:**
```xml
<string name="welcome_notifications">Welcome to Notification Settings! 🔔</string>
<string name="welcome_advanced">Advanced settings for power users ⚙️</string>
```

**In your preference XML:**
```xml
<Preference
    a:key="notifications"
    a:title="Notifications"
    a:fragment="slions.pref.demo.NotificationsFragment">
    <extra
        a:name="message"
        a:value="@string/welcome_notifications" />
</Preference>
```

The fragment receives the resolved string automatically - no additional code needed!

#### Using XML Resource IDs

You can pass XML resource references using `@xml/` syntax. **Important:** The `@xml/` reference gets resolved to a **resource path string** (like `res/xml-v22/pref_screen_advanced.xml`), not an integer ID. You need to extract the resource name and resolve it at runtime:

```xml
<Preference
    a:key="advanced"
    a:title="Advanced"
    a:fragment="slions.pref.demo.AdvancedFragment">
    <extra
        a:name="message"
        a:value="@string/welcome_advanced" />
    <extra
        a:name="screen"
        a:value="@xml/pref_screen_advanced" />
</Preference>
```

**In your fragment:**
```kotlin
class AdvancedFragment : PreferenceFragmentCompat() {
    companion object {
        private const val ARG_SCREEN = "screen"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Get screen from extras (comes as string path from @xml/ reference)
        val screenResId = arguments?.getString(ARG_SCREEN)?.let { screenValue ->
            resolveScreenResource(screenValue)
        } ?: R.xml.pref_screen_error
        
        setPreferencesFromResource(screenResId, rootKey)
    }
    
    @SuppressLint("DiscouragedApi")
    private fun resolveScreenResource(resourceValue: String): Int {
        // Handle different formats:
        // 1. "res/xml-v22/pref_screen_advanced.xml" (resolved from @xml/)
        // 2. "pref_screen_advanced" (plain resource name)
        
        val resourceName = when {
            // Extract name from resolved path
            resourceValue.startsWith("res/xml") -> {
                resourceValue.substringAfterLast("/").removeSuffix(".xml")
            }
            // Use as-is if plain name
            else -> resourceValue
        }

        // Get the resource ID using reflection
        val resId = resources.getIdentifier(resourceName, "xml", requireContext().packageName)
        
        return if (resId != 0) {
            resId
        } else {
            Toast.makeText(requireContext(), 
                "Screen not found: $resourceValue", 
                Toast.LENGTH_LONG).show()
            R.xml.pref_screen_error
        }
    }
}
```

**Error screen example (pref_screen_error.xml):**
```xml
<PreferenceScreen xmlns:a="http://schemas.android.com/apk/res/android">
    <PreferenceCategory a:title="Error">
        <Preference
            a:title="No Screen Specified"
            a:summary="This fragment requires a screen resource ID."
            a:enabled="false" />
    </PreferenceCategory>
</PreferenceScreen>
```

This allows you to reuse the same fragment class with different preference screens, with a helpful error message when no screen is specified!

#### How It Works

1. The AndroidX Preference library automatically bundles all `<extra>` tags into the fragment's arguments Bundle
2. The fragment can access these values via `arguments?.getString("key")`, `arguments?.getInt("key")`, etc.
3. No additional navigation code is required - the library handles everything automatically

This approach provides:
- **Declarative configuration** - All fragment parameters defined in one place
- **Type safety** - Use companion object constants for keys
- **Clean separation** - No need for manual fragment transactions or Bundle building

## References

- [Material Design Preference Library](https://github.com/Slion/Preference) - Material Design extension for AndroidX Preference
- [AndroidX Preference Source Code](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-main/preference/preference/)
- [AndroidX Preference Library - Official Documentation](https://developer.android.com/jetpack/androidx/releases/preference)
- [Settings Guide - Android Developers](https://developer.android.com/develop/ui/views/components/settings)



