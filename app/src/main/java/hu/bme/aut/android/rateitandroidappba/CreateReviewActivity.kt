package hu.bme.aut.android.rateitandroidappba

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.android.rateitandroidappba.data.Review
import hu.bme.aut.android.rateitandroidappba.extensions.validateNonEmpty
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_create_review.*
import kotlinx.android.synthetic.main.activity_create_review.btnSend

class CreateReviewActivity : BaseActivity() {

    companion object {
        const val RESTAURANT_ID = "RESTAURANT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review)

        btnSend.setOnClickListener { sendClick() }
    }

    private fun sendClick() {
        if (!validateForm()) {
            return
        }

        uploadPost()
    }

    private fun validateForm() = etReviewText.validateNonEmpty()

    private fun uploadPost() {
        val key = FirebaseDatabase.getInstance().reference.child("reviews").push().key ?: return
        val newReview = Review(key, uid, userName, "restaurantID", etReviewText.text.toString())

        FirebaseDatabase.getInstance().reference
            .child("reviews")
            .child(key)
            .setValue(newReview)
            .addOnCompleteListener {
                toast("Your review was added")
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