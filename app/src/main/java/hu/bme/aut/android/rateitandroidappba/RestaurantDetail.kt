package hu.bme.aut.android.rateitandroidappba

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.android.rateitandroidappba.adapter.ReviewsAdapter
import hu.bme.aut.android.rateitandroidappba.data.Review
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*


class RestaurantDetail : AppCompatActivity() {

    companion object {
        const val KEY_TITTLE = "KEY_TITTLE"
        const val KEY_ADDRESS = "KEY_ADDRESS"
        const val KEY_URL = "KEY_URL"
        const val KEY_RESTAURANT_ID = "RESTAURANT_ID"
    }

    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        tvRestaurantTitle.text = intent.getStringExtra(KEY_TITTLE)
        tvRestaurantAddress.text = intent.getStringExtra(KEY_ADDRESS)
        tvRestaurantURL.text = intent.getStringExtra(KEY_URL)

        val fab: FloatingActionButton = findViewById(R.id.fabResDetail)

        //add restaurant posts button listener
        fab.setOnClickListener {
            //Toast.makeText(applicationContext,"fab pushed",Toast.LENGTH_SHORT).show()

            val intentval = Intent(this, CreateReviewActivity::class.java)
            intentval.putExtra(CreateReviewActivity.RESTAURANT_ID, intent.getStringExtra(KEY_RESTAURANT_ID))
            startActivity(intentval)

        }

        reviewsAdapter = ReviewsAdapter(applicationContext)
        rvReviews.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        rvReviews.adapter = reviewsAdapter
        initPostsListener()
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

    private fun initPostsListener() {
        FirebaseDatabase.getInstance()
            .getReference("reviews")                  //get data from reviews brach of database
            .addChildEventListener(object : ChildEventListener {
                //get elements from firebase dataset
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val newReview = dataSnapshot.getValue<Review>(Review::class.java)
                    //Log.d("TAG", "------------------------${newPost?.title}---------------------------")
                    if(newReview?.restaurantID!! == intent.getStringExtra(KEY_RESTAURANT_ID))       //add review to recyclerView list only if it belongs to the current restaurant
                        reviewsAdapter.addReview(newReview)       //add it to the recyclerView

                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }
}