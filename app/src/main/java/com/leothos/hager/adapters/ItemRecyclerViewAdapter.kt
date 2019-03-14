package com.leothos.hager.adapters

import android.content.Intent
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
import com.leothos.hager.model.api.ApiProductItem
import com.leothos.hager.ui.ItemDetailActivity
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

//    private val onClickListener: View.OnClickListener
//
//    init {
//        onClickListener = View.OnClickListener { v ->
//            val item = v.tag as ApiProductsResponse
//            if (twoPane) {
//                val fragment = ItemDetailFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ItemDetailFragment.ARG_ITEM_ID, item.data?.get(0)?.reference)
//                    }
//                }
//                parentActivity.supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.item_detail_container, fragment)
//                    .commit()
//            } else {
//                val intent = Intent(v.context, ItemDetailActivity::class.java)/*.apply {
////                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.data?.get(0)?.reference)
//                }*/
//                v.context.startActivity(intent)
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productValues[position]
        holder.reference.text = item.reference
        holder.shortDescription.text = item.shortDescriptions?.get(0)?.value
        glide.load("$PICTURE_URL${item.reference}.webp")
            .apply(RequestOptions().centerCrop()).into(holder.picture)
        holder.itemPosition = position

//        with(holder.itemView) {
//            tag = item
//            setOnClickListener(onClickListener)
//        }
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
                onItemSelectedListener?.onItemSelected(productValues[itemPosition])
                val intent = Intent(parentActivity, ItemDetailActivity::class.java).apply {
                    putExtra(ITEM_POSITION, itemPosition)
                }
                parentActivity.startActivity(intent)

            }
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(productItem: ApiProductItem)
    }
}