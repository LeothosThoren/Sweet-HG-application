package com.leothos.hager.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.leothos.hager.PICTURE_URL
import com.leothos.hager.POSITION_NOT_SET
import com.leothos.hager.R
import com.leothos.hager.data.DataManager
import com.leothos.hager.injections.Injection
import com.leothos.hager.model.api.ApiProductItem
import com.leothos.hager.model.entities.FavoriteProduct
import com.leothos.hager.view_models.FavoriteProductViewModel
import kotlinx.android.synthetic.main.item_detail.*


class ItemDetailFragment : Fragment() {

    private var productItem: ApiProductItem? = null
    private val TAG = this::class.java.simpleName
    private var favoriteProductViewModel: FavoriteProductViewModel? = null
    private var isFavoriteExist = false
    private var position = POSITION_NOT_SET

    companion object {
        /**
         * The fragment argument representing the item Position that this fragment
         * represents.
         */
        const val ARG_ITEM_POS = "item_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Retrieve data position from activity
        arguments?.let {
            if (it.containsKey(ARG_ITEM_POS)) {
                position = it.getInt(ARG_ITEM_POS)
                productItem = DataManager.dataItems[position]
            }
        }
        Log.d(TAG, "productItem = ${productItem?.reference}")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        // Methods
        configureViewModel()
        getFavoriteProductFromDB()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        updateUI()
        init()
    }

    // --------
    // Init
    // --------

    private fun init() {
        listFab.setOnClickListener {
            isFavoriteExist = if (isFavoriteExist) {
                deleteFromFavorite()
                Snackbar.make(it, "Favorite deleted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                false
            } else {
                addToFavorite()
                Snackbar.make(it, "Favorite added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                true
            }
        }
    }

    // ----------
    // UI
    // ----------

    /**
     * This method bind the data to the detail view in order to display information from the data
     * */
    private fun updateUI() {
        detailBrand.text = productItem?.brand
        detailPrice.text =
            getString(R.string.detail_price, productItem?.price.toString(), "${productItem?.priceCurrency}")
        detailReference.text = productItem?.reference
        detailEAN.text = productItem?.eAN
        detailLongDescription.text = productItem?.descriptions?.get(0)?.value
        Glide.with(this)
            .load("$PICTURE_URL${productItem?.reference}.webp")
            .into(detailImage)
    }

    // ----------
    // Config
    // ----------

    /**
     * This method allow the access to database and configure ViewModel instance
     * */
    private fun configureViewModel() {
        val modelFactory = Injection.viewModelFactory(context!!)
        favoriteProductViewModel = ViewModelProviders.of(this, modelFactory)
            .get(FavoriteProductViewModel::class.java)
    }

    /**
     * Thanks to ViewModel and Room it's easy to retrieve data that user selected as favorite
     * */
    private fun getFavoriteProductFromDB() {
        favoriteProductViewModel?.getAllFavoriteProducts()?.observe(this,
            Observer { updateFavoriteProductList(it) })
    }

    /**
     * This method combine with the live data provided through the ViewModel
     * allow to update the recyclerView instantaneously with data provided by Room
     * */
    private fun updateFavoriteProductList(favoriteList: List<FavoriteProduct>) {
        compareProductReference(favoriteList)
    }

    // ----------
    // Action
    // ----------

    /**
     * This method allow the user to add his favorite product inside the room database.
     *
     * */
    private fun addToFavorite() {
        val favoriteProduct = FavoriteProduct(
            productItem?.reference!!,
            productItem?.brand,
            productItem?.descriptions?.get(0)?.value,
            productItem?.price,
            productItem?.priceCurrency,
            productItem?.eAN
        )

        favoriteProductViewModel?.insertFavoriteProduct(favoriteProduct)
        listFab.setImageResource(R.drawable.ic_delete_white_24dp)
    }

    /**
     * This method delete the current favorite product from the database
     * */
    private fun deleteFromFavorite() {
        favoriteProductViewModel?.deleteFavoriteProduct(productItem?.reference!!)
        listFab.setImageResource(R.drawable.ic_add_white_24dp)
    }


    /**
     * This method compare the reference of the product both inside database and in the object
     * If it matches the Ui is updated and the user will understand when he will be able to add
     * Or to delete the object from the favorite list
     * */
    private fun compareProductReference(favoriteProduct: List<FavoriteProduct?>?) {
        for (i in 0 until (favoriteProduct?.size ?: 0)) {
            if (favoriteProduct?.get(i)?.referenceId == productItem?.reference) {
                Log.d(TAG, "Compare reference = true")
                isFavoriteExist = true
                break
            }
        }
        if (isFavoriteExist) listFab.setImageResource(R.drawable.ic_delete_white_24dp)
    }
}
