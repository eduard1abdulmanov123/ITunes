package abdulmanov.eduard.itunes.presentation.common

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "ITUNES_PREFS"
        private const val LAST_QUERY = "LAST_QUERY"
    }

    private val sharedPreference by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var lastQuery: String?
        get() = sharedPreference.getString(LAST_QUERY, null)
        set(lastQuery) {
            lastQuery?.let { sharedPreference.edit().putString(LAST_QUERY, it).commit() }
        }

}