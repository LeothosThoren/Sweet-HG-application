package com.leothos.hager.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leothos.hager.PICTURE_URL
import com.leothos.hager.R
import com.leothos.hager.data.DataManager
import com.leothos.hager.model.api.ApiProductItem
import kotlinx.android.synthetic.main.item_detail.*


class ItemDetailFragment : Fragment() {

    private var productItem: ApiProductItem? = null
    private val TAG = this::class.java.simpleName

    companion object {
        /**
         * The fragment argument representing the item Position that this fragment
         * represents.
         */
        const val ARG_ITEM_POS = "item_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Retrieve data from activity
        arguments?.let {
            if (it.containsKey(ARG_ITEM_POS)) {
                val pos = it.getInt(ARG_ITEM_POS)
                productItem = DataManager.dataItems[pos]
            }
        }
        Log.d(TAG, productItem?.reference)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    // ----------
    // UI
    // ----------

    /**
     * This method bind the data to the detail view in order to display information from the data
     *
     * */
    private fun updateUI() {
        detailBrand.text = productItem?.brand
        detailPrice.text = "${productItem?.price} ${productItem?.priceCurrency}"
        detailReference.text = productItem?.reference
        detailEAN.text = productItem?.eAN
        detailLongDescription.text = productItem?.descriptions?.get(0)?.value
        Glide.with(this)
            .load("$PICTURE_URL${productItem?.reference}.webp")
            .apply(RequestOptions.centerInsideTransform())
            .into(detailImage)
    }
}
