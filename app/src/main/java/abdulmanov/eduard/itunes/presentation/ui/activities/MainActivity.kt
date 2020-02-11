package abdulmanov.eduard.itunes.presentation.ui.activities

import abdulmanov.eduard.itunes.R
import abdulmanov.eduard.itunes.presentation.ui.fragments.search.SearchFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private val backStack = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //При перевороте fragmentManager сохраняет добавленные фрагменты, поэтому необходимо восстановить, чтобы не было утечки памяти
        if (savedInstanceState == null) {
            showFragment(SearchFragment())
        } else {
            supportFragmentManager.fragments.forEach {
                backStack.add(it)
            }
        }
    }

    override fun onBackPressed() {
        if (backStack.size > 1) {
            supportFragmentManager.beginTransaction().apply {
                remove(backStack.last())
                backStack.remove(backStack.last())
                show(backStack.last())
            }.commit()
        } else {
            super.onBackPressed()
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            if (backStack.isNotEmpty()) {
                hide(backStack.last())
            }
            add(R.id.main_container, fragment)
            backStack.add(fragment)
        }.commit()
    }

}
