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
import com.leothos.hager.ITEM_POSITION
import com.leothos.hager.PICTURE_URL
import com.leothos.hager.R
import com.leothos.hager.loadUrl
import com.leothos.hager.model.api.ApiProductItem
import com.leothos.hager.ui.ItemDetailActivity
import com.leothos.hager.ui.ItemDetailFragment
import com.leothos.hager.ui.ItemListActivity
import kotlinx.android.synthetic.main.item_list_content.view.*

class ItemRecyclerViewAdapter(
    private val parentActivity: ItemListActivity,
    private val productValues: List<ApiProductItem>,
    private val twoPane: Boolean,
    private val glide: RequestManager,
    private var onItemSelectedListener: OnItemSelectedListener? = null
) :
    RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productValues[position]
        holder.reference.text = item.reference
        holder.shortDescription.text = item.shortDescriptions?.get(0)?.value
        holder.picture.loadUrl("$PICTURE_URL${item.reference}.webp")
        holder.itemPosition = position

    }

    fun setOnselectedListener(listener: OnItemSelectedListener) {
        onItemSelectedListener = listener
    }

    override fun getItemCount() = productValues.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reference: TextView = view.reference
        val shortDescription: TextView = view.shortDescription
        val picture: ImageView = view.picture
        var itemPosition = 0

        init {
            view.setOnClickListener {
                // Default configuration to handle large tablet device or smart phone device
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_POS, itemPosition)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    onItemSelectedListener?.onItemSelected(productValues[itemPosition])
                    val intent = Intent(parentActivity, ItemDetailActivity::class.java).apply {
                        putExtra(ITEM_POSITION, itemPosition)
                    }
                    parentActivity.startActivity(intent)
                }
            }
        }
    }

    // Click handler interface
    interface OnItemSelectedListener {
        fun onItemSelected(productItem: ApiProductItem)
    }
}