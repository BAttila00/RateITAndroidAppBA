//adapter of recyclerView (id=rvPosts)

package hu.bme.aut.android.rateitandroidappba.adapter

import android.content.Context
import android.util.Log
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

    var itemClickListener: RestaurantItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tvTitle
        val tvAdress: TextView = itemView.tvAddress
        val tvPageUrl: TextView = itemView.tvPageUrl

        //dont forget to initalize it in onBindViewHolder with viewHolder.restaurant = tmpPost
        var restaurant: Restaurant? = null
        //var restaurant: Restaurant = Restaurant()

        init {
            itemView.setOnClickListener {
                if (restaurant != null) Log.d("TAG", "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg")
                else Log.d("TAG", "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
                restaurant?.let { restaurant -> itemClickListener?.onItemClick(restaurant) }
                //itemClickListener?.onItemClick(restaurant)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.restaurant_post, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val tmpPost = postList[position]
        viewHolder.restaurant = tmpPost             //dont forget this, otherwise var restaurant: Restaurant? = null will remain null and u wont have a clickListener (it's in "class ViewHolder(itemView: View)")
        viewHolder.tvTitle.text = tmpPost.title
        viewHolder.tvAdress.text = tmpPost.address
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

    interface RestaurantItemClickListener {
        fun onItemClick(restaurant: Restaurant)
    }

}