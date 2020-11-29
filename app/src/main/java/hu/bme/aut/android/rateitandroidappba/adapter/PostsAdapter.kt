package hu.bme.aut.android.rateitandroidappba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.rateitandroidappba.R
import hu.bme.aut.android.rateitandroidappba.data.Restaurant
import kotlinx.android.synthetic.main.restaurant_post.view.*

class PostsAdapter(private val context: Context) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private val postList: MutableList<Restaurant> = mutableListOf()
    private var lastPosition = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tvTitle
        val tvAdress: TextView = itemView.tvAdress
        val tvPageUrl: TextView = itemView.tvPageUrl
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.restaurant_post, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tmpPost = postList[position]
        viewHolder.tvTitle.text = tmpPost.title
        viewHolder.tvAdress.text = tmpPost.adress
        viewHolder.tvPageUrl.text = tmpPost.pageUrl

        setAnimation(viewHolder.itemView, position)
    }

    override fun getItemCount() = postList.size

    fun addPost(post: Restaurant?) {
        post ?: return

        postList.add(post)
        notifyDataSetChanged()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}