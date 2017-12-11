package com.example.lxliang.catoncloud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.lxliang.catoncloud.R
import com.example.lxliang.catoncloud.model.GetNearbyCatResponse

/**
 * Created by lxliang on 02/11/2017.
 */
class NearbyCatAdapter(val context: Context,
                       var data: List<GetNearbyCatResponse.MomentsBean>?,
                       val onItemClickedListener: OnItemClickedListener) : BaseAdapter() {
    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        val holder: ViewHolder
        val item = data!!.get(position)

        if (convertView == null) {
            view = inflater.inflate(R.layout.nearby_cat_list_item, null, false)
            var content: RelativeLayout = view.findViewById<RelativeLayout>(R.id.content)

            var thumbViewList: MutableList<ImageView> = mutableListOf<ImageView>()

            for (thumb in item.thumbs!!.iterator()) {
                var iv: ImageView = ImageView(context)
                thumbViewList.add(iv)
                content.addView(iv)
            }

            holder = ViewHolder(view, position)
            holder.nameTextView = view.findViewById<TextView>(R.id.name)
            holder.descTextView = view.findViewById<TextView>(R.id.description)
            holder.avatarImageView = view.findViewById<ImageView>(R.id.avatar)
            holder.thumbViewList = thumbViewList

            view.setTag(holder)
        } else {
            view = convertView
            holder = convertView.getTag() as ViewHolder
        }

        (0 until item.thumbs!!.size).forEach { i ->
            Glide.with(context)
                    .load("http://10.0.2.2:8080/catnip" + item.thumbs!!.get(i)?.image)
                    .into(holder.thumbViewList[i])

            var layoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(100, 100)

            if (i == 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.name)
            } else {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, i)
                layoutParams.setMargins(20, 0, 0, 0)
            }
            layoutParams.addRule(RelativeLayout.BELOW, R.id.description)
            holder.thumbViewList[i].layoutParams = layoutParams

            holder.thumbViewList[i].scaleType = ImageView.ScaleType.FIT_XY
            holder.thumbViewList[i].id = i + 1
        }

        holder.nameTextView.setText(item.cat)
        holder.descTextView.setText(item.message)
        Glide.with(context).load("http://10.0.2.2:8080/catnip" + item.avatar?.image).into(holder.avatarImageView)

        return view
    }


    override fun getItem(position: Int): Any {
        return data!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data!!.size
    }

    inner class ViewHolder(itemView: View, position: Int) {
        lateinit var nameTextView: TextView
        lateinit var descTextView: TextView
        lateinit var avatarImageView: ImageView
        lateinit var thumbViewList: MutableList<ImageView>

        init {
            itemView.setOnClickListener {
                onItemClickedListener.onItemClicked(itemView, position)
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(view: View, position: Int)
    }
}