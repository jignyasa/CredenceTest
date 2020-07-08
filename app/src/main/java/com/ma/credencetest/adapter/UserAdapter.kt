package com.ma.credencetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ma.credencetest.R
import com.ma.credencetest.helper.DateUtility
import com.ma.credencetest.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter(val context:Context) : RecyclerView.Adapter<UserAdapter.VHolder>()
{
    var list=ArrayList<ListItem>()
    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun addData(alList:ArrayList<ListItem>)
    {
        list=alList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item=list.get(position)
        var str="http://credencetech.in/mobiledevelopertechnicaltest/ProfilePictures/"
        Glide.with(context).load(str.plus(item.user_profile_picture_url)).error(R.mipmap.ic_launcher).into(holder.itemView.ivUser)
        holder.itemView.tvName.text=item.user_firstname
        holder.itemView.tvEmail.text=item.user_email
        holder.itemView.tvBirthdate.text=item.user_birthdate

    }

}