package hu.bme.aut.android.rateitandroidappba

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.android.rateitandroidappba.adapter.PostsAdapter
import hu.bme.aut.android.rateitandroidappba.data.Restaurant
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_main.*

//Az osztály implementálja a NavigationView.OnNavigationItemSelectedListener interfészt.
class PostsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    PostsAdapter.RestaurantItemClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        //add restaurant posts button listener
        fab.setOnClickListener {
            val createPostIntent = Intent(this, CreatePostActivity::class.java)
            startActivity(createPostIntent)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(this)     //onCreate() metódusban beregisztráljuk az eseménykezelőt.

        //connecting the layout with RecyclerView adapter
        postsAdapter = PostsAdapter(applicationContext)
        rvPosts.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }

        postsAdapter.itemClickListener = this
        rvPosts.adapter = postsAdapter

        initPostsListener()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.posts, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Az előírt metódusban elvégezzük a kiléptetést, és a MainActivity-re navigálunk.
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }



    //adds all elements from database to the recyclerView (and adds listener to ADD, every time we add a restaurant to the database the recycler view updates)
    private fun initPostsListener() {
        FirebaseDatabase.getInstance()
            .getReference("restaurantPosts")                  //get data from restaurantPosts brach of database
            .addChildEventListener(object : ChildEventListener {
                //get elements from firebase dataset
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val newPost = dataSnapshot.getValue<Restaurant>(Restaurant::class.java)
                    postsAdapter.addPost(newPost)       //add it to the recyclerView
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

    override fun onItemClick(restaurant: Restaurant) {
        Toast.makeText(applicationContext,"Navigate to restaurant layout",Toast.LENGTH_SHORT).show()
    }
}