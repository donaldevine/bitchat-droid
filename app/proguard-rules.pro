# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Room entities
-keep class com.bitchat.android.data.model.** { *; }

# Keep cryptography classes
-keep class com.bitchat.android.crypto.** { *; }

# Keep Bluetooth classes
-keep class com.bitchat.android.bluetooth.** { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Lazysodium
-keep class com.goterl.lazysodium.** { *; }
-keep class net.java.dev.jna.** { *; }

# Keep Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.bitchat.android.**$$serializer { *; }
-keepclassmembers class com.bitchat.android.** {
    *** Companion;
}
-keepclasseswithmembers class com.bitchat.android.** {
    kotlinx.serialization.KSerializer serializer(...);
}
