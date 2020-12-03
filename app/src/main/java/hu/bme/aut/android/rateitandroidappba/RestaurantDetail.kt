package hu.bme.aut.android.rateitandroidappba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_restaurant_detail.*

class RestaurantDetail : AppCompatActivity() {

    companion object {
        const val KEY_TITTLE = "KEY_TITTLE"
        const val KEY_ADDRESS = "KEY_ADDRESS"
        const val KEY_URL = "KEY_URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        tvRestaurantTitle.text = intent.getStringExtra(KEY_TITTLE)
        tvRestaurantAddress.text = intent.getStringExtra(KEY_ADDRESS)
        tvRestaurantURL.text = intent.getStringExtra(KEY_URL)

        val fab: FloatingActionButton = findViewById(R.id.fabResDetail)

        //add restaurant posts button listener
        fab.setOnClickListener {
            Toast.makeText(applicationContext,"fab pushed",Toast.LENGTH_SHORT).show()
        }
    }
}