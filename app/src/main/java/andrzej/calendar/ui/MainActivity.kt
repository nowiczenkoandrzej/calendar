package andrzej.calendar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import andrzej.calendar.R

import andrzej.calendar.databinding.ActivityMainBinding
import andrzej.calendar.ui.calendar.CalendarFragment
import andrzej.calendar.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(CalendarFragment())

        binding.bottomMenu.setOnItemSelectedListener {

            when(it.itemId){
                R.id.calendar -> replaceFragment(CalendarFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                else -> {
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()

    }



}