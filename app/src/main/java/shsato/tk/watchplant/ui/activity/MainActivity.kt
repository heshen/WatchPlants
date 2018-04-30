package shsato.tk.watchplant.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import shsato.tk.watchplant.Constants
import shsato.tk.watchplant.R
import shsato.tk.watchplant.ui.fragment.PlantsFragment
import shsato.tk.watchplant.ui.viewmodel.MainViewModel
import shsato.tk.watchplant.util.OpenUtil

class MainActivity : AppCompatActivity() {

    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initViews()
        if (savedInstanceState == null) {
            val f = PlantsFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, f)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        when (requestCode) {
            Constants.REQUEST_CAMERA -> {
                if (data.extras == null) {
                    return
                } else {
                    val img = data.extras.get("data") as Bitmap
                }
            }
        }
    }

    private fun initViews() {
        mModel.title?.observe(this, Observer {
            title ->
            supportActionBar?.title = title
        })
        camera.setOnClickListener {
            OpenUtil.openCamera(this@MainActivity, Constants.REQUEST_CAMERA)
        }
        mModel.title?.value = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
