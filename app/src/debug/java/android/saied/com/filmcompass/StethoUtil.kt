package android.saied.com.filmcompass

import android.content.Context
import com.facebook.stetho.Stetho

object StethoUtil {
    fun init(context: Context) {
        Stetho.initialize(
            Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                .build()
        )

    }
}