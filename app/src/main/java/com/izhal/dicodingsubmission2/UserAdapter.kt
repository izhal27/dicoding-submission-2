package com.izhal.dicodingsubmission2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
  companion object {
    const val EXTRA_LOGIN = "extra_login"
  }

  private var users = ArrayList<User>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    return UserViewHolder(view)
  }

  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    holder.bind(users[position])
  }

  override fun getItemCount(): Int = users.size

  fun setData(listUser: ArrayList<User>) {
    users.clear()
    users.addAll(listUser)
    notifyDataSetChanged()
  }

  fun clearData() {
    users.clear()
    notifyDataSetChanged()
  }

  inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
      Glide.with(itemView.context)
        .load(user.avatar_url)
        .apply(RequestOptions().override(500, 500))
        .centerCrop()
        .into(itemView.imgAvatarDetail)

      val detailClickListener = View.OnClickListener {
        val intent = Intent(itemView.context, DetailUserActivity::class.java)
        intent.putExtra(EXTRA_LOGIN, user.login)
        itemView.context.startActivity(
          intent
        )
      }

      itemView.txtName.text = user.login
      itemView.btnOpenDetail.setOnClickListener(detailClickListener)
      itemView.btnOpenRepo.setOnClickListener {
        val intent = Intent(itemView.context, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.EXTRA_REPO_URL, user.html_url)
        itemView.context.startActivity(intent)
      }
    }
  }
}