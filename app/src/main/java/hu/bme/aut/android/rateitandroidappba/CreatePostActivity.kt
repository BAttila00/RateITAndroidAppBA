package hu.bme.aut.android.rateitandroidappba

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.android.rateitandroidappba.data.Restaurant
import hu.bme.aut.android.rateitandroidappba.extensions.validateNonEmpty
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        btnSend.setOnClickListener { sendClick() }
    }

    private fun sendClick() {
        if (!validateForm()) {
            return
        }

        uploadPost()
    }

    private fun validateForm() = etTitle.validateNonEmpty() && etAddress.validateNonEmpty()

    private fun uploadPost(imageUrl: String? = null) {
        val key = FirebaseDatabase.getInstance().reference.child("restaurantPosts").push().key ?: return
        val newPost = Restaurant(key, uid, etTitle.text.toString(), etAddress.text.toString(),  etPageUrl.text.toString())

        FirebaseDatabase.getInstance().reference
            .child("restaurantPosts")
            .child(key)
            .setValue(newPost)
            .addOnCompleteListener {
                toast("Restaurant post created")
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
    }

}