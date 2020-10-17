package com.izhal.dicodingsubmission2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_follower.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
  private var followers = ArrayList<User>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
    return FollowersViewHolder(view)
  }

  override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
    holder.bind(followers[position])
  }

  override fun getItemCount(): Int = followers.size

  fun setData(listUser: ArrayList<User>) {
    followers.clear()
    followers.addAll(listUser)
    notifyDataSetChanged()
  }

  inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
      Glide.with(itemView.context)
        .load(user.avatar_url)
        .apply(RequestOptions().override(500, 500))
        .centerCrop()
        .into(itemView.imgFollower)

      itemView.txtName.text = user.login
      itemView.setOnClickListener {
        val intent = Intent(itemView.context, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.EXTRA_REPO_URL, user.html_url)
        itemView.context.startActivity(intent)
      }
    }
  }
}