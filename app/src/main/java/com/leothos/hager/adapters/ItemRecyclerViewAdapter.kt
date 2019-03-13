package com.leothos.hager.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.leothos.hager.PICTURE_URL
import com.leothos.hager.R
import com.leothos.hager.model.DataItem
import com.leothos.hager.model.Response
import com.leothos.hager.ui.ItemDetailActivity
import com.leothos.hager.ui.ItemDetailFragment
import com.leothos.hager.ui.ItemListActivity
import kotlinx.android.synthetic.main.item_list_content.view.*

class ItemRecyclerViewAdapter(
    private val parentActivity: ItemListActivity,
    private val productValues: List<DataItem>,
    private val twoPane: Boolean,
    private val glide: RequestManager
) :
    RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Response
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.data?.get(0)?.reference)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.data?.get(0)?.reference)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productValues[position]
        holder.let {
            holder.reference.text = item.reference
            holder.shortDescription.text = item.shortDescriptions?.get(0)?.value
            glide.load("$PICTURE_URL${item.reference}.webp")
                .apply(RequestOptions().centerCrop()).into(holder.picture)
        }

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = productValues.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reference: TextView = view.reference
        val shortDescription: TextView = view.shortDescription
        val picture: ImageView = view.picture
    }
}