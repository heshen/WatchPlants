package shsato.tk.watchplant

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import shsato.tk.watchplant.ui.fragment.PlantsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val f = PlantsFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, f)
                    .addToBackStack(null)
                    .commit()
        }
    }
}
