package hu.bme.aut.android.rateitandroidappba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.rateitandroidappba.R
import hu.bme.aut.android.rateitandroidappba.data.Review
import kotlinx.android.synthetic.main.review_post.view.*

class ReviewsAdapter(private val context: Context) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private val reviewList: MutableList<Review> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.tvUserName
        val tvReview: TextView = itemView.tvReview
    }


        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.review_post, viewGroup, false)
            return ViewHolder(view)
        }

    override fun onBindViewHolder(viewHolder: ReviewsAdapter.ViewHolder, position: Int) {

        val tmpReview = reviewList[position]

        viewHolder.tvUserName.text = tmpReview.userName
        viewHolder.tvReview.text = tmpReview.review
    }

    override fun getItemCount() = reviewList.size

    fun addReview(review: Review?) {
        review ?: return

        reviewList.add(review)
        notifyDataSetChanged()
    }

}