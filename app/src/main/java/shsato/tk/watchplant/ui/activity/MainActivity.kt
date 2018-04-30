package shsato.tk.watchplant

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import shsato.tk.watchplant.ui.fragment.PlantsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        if (savedInstanceState == null) {
            val f = PlantsFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, f)
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun initViews() {
        camera.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }
    }
}
