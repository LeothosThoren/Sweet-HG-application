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
import com.leothos.hager.FAVORITE_ITEM_POSITION
import com.leothos.hager.PICTURE_URL
import com.leothos.hager.R
import com.leothos.hager.model.entities.FavoriteProduct
import com.leothos.hager.ui.ItemDetailActivity
import com.leothos.hager.ui.ItemDetailFragment
import com.leothos.hager.ui.ItemListActivity
import kotlinx.android.synthetic.main.item_list_content.view.*

class FavoriteItemRecyclerViewAdapter(
    private val parentActivity: ItemListActivity,
    private val favoriteProduct: List<FavoriteProduct>,
    private val twoPane: Boolean,
    private val glide: RequestManager,
    private var onFavoriteItemSelectedListener: OnFavoriteItemSelectedListener? = null
) :
    RecyclerView.Adapter<FavoriteItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favoriteProduct[position]
        holder.reference.text = item.referenceId
        holder.shortDescription.text = item.description
        glide.load("$PICTURE_URL${item.referenceId}.webp")
            .apply(RequestOptions().centerCrop()).into(holder.picture)
        holder.favoriteItemPosition = position

    }

    override fun getItemCount() = favoriteProduct.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reference: TextView = view.reference
        val shortDescription: TextView = view.shortDescription
        val picture: ImageView = view.picture
        var favoriteItemPosition = 0

        init {
            view.setOnClickListener {
                // Default configuration to handle large tablet device or smart phone device
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_FAVORITE_POS, favoriteItemPosition)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    onFavoriteItemSelectedListener?.onFavoriteItemSelected(favoriteProduct[favoriteItemPosition])
                    val intent = Intent(parentActivity, ItemDetailActivity::class.java).apply {
                        putExtra(FAVORITE_ITEM_POSITION, favoriteItemPosition)
                    }
                    parentActivity.startActivity(intent)
                }
            }
        }
    }

    // Click handler interface
    interface OnFavoriteItemSelectedListener {
        fun onFavoriteItemSelected(favoriteProduct: FavoriteProduct)
    }
}