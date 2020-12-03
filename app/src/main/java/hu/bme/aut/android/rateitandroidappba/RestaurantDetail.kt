package hu.bme.aut.android.rateitandroidappba

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import kotlinx.android.synthetic.main.nav_header_main.*


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

    fun onClickTitle(view: View) {
        val textView: TextView = view as TextView
        val str: String = textView.text.toString()
        //Toast.makeText(applicationContext,str,Toast.LENGTH_SHORT).show()
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, str)
        startActivity(intent)
    }

    fun onClickAddress(view: View) {
        //val intentUri = Uri.parse("geo:0,0?q=Budapest, Magyar Tudósok Körútja 2, 1117")
        val textView: TextView = view as TextView
        val str: String = textView.text.toString()
        val intentUri = Uri.parse("geo:0,0?q=${str}")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    fun onClickUrl(view: View) {
        //val intentUri = Uri.parse("http://www.stackoverflow.com")
        val textView: TextView = view as TextView
        val str: String = textView.text.toString()
        val intentUri = Uri.parse(str)
        val urlIntent = Intent(Intent.ACTION_VIEW, intentUri)
        startActivity(urlIntent)
    }
}