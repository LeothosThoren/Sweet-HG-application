package com.leothos.hager.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leothos.hager.FAVORITE_AVAILABLE
import com.leothos.hager.R
import com.leothos.hager.adapters.FavoriteItemRecyclerViewAdapter
import com.leothos.hager.adapters.ItemRecyclerViewAdapter
import com.leothos.hager.data.DataManager
import com.leothos.hager.injections.Injection
import com.leothos.hager.isVisible
import com.leothos.hager.model.api.ApiProductItem
import com.leothos.hager.model.entities.FavoriteProduct
import com.leothos.hager.toast
import com.leothos.hager.view_models.FavoriteProductViewModel
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.toolbar.*

class ItemListActivity : AppCompatActivity(),
    ItemRecyclerViewAdapter.OnItemSelectedListener,
    FavoriteItemRecyclerViewAdapter.OnFavoriteItemSelectedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private val TAG = this::class.java.simpleName
    private var twoPane: Boolean = false
    private var dataItem = DataManager.dataItems
    private var favoriteProductViewModel: FavoriteProductViewModel? = null
    private var isFavoriteListEnabled = false
    private var areFavoritesAvailable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        //First update of the toolbar title
        updateTitle(getString(R.string.toolbar_title_list_of_products))
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (item_detail_container != null) {
            twoPane = true
        }


        /**
         * Retrieve the user choice from main activity
         * */
        areFavoritesAvailable = intent.getBooleanExtra(FAVORITE_AVAILABLE, false)

        init()
    }


    override fun onResume() {
        super.onResume()
        item_list.adapter?.notifyDataSetChanged()

        //OnResume allow the app to configure and display the list of data available
        displayFavoriteItemFromMainActivity(areFavoritesAvailable)
    }

    private fun init() {
        /**
         * Depends on the user action the views are adapted whether the list of product is displayed
         * or the favorites. The toolbar title, the fab icon and off course the data
         * */
        listFab.setOnClickListener { view ->
            isFavoriteListEnabled = if (isFavoriteListEnabled) {
                toolbar.title = getString(R.string.toolbar_title_list_of_products)
                listFab.setImageResource(R.drawable.ic_star_24dp)
                setupRecyclerViewForProductList(item_list)
                false
            } else {
                toolbar.title = getString(R.string.toolbar_title_favorites)
                listFab.setImageResource(R.drawable.ic_filter_list_white_24dp)
                getFavoriteProductFromDB()
                true
            }
        }
        //Setup
        setupRecyclerViewForProductList(item_list)
        configureViewModel()

    }

    // ----------
    // Config
    // ----------

    /**
     * This method handle the configuration of all the product fetch from the web service
     * @param recyclerView is useful here to handle the two pane feature for tablet device
     * */
    private fun setupRecyclerViewForProductList(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ItemRecyclerViewAdapter(this, dataItem, twoPane, this)
    }


    /**
     * This method allow the access to database and configure ViewModel instance
     * */
    private fun configureViewModel() {
        val modelFactory = Injection.viewModelFactory(this)
        favoriteProductViewModel = ViewModelProviders.of(this, modelFactory)
            .get(FavoriteProductViewModel::class.java)
    }


    // --------
    // UI
    // --------


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

        if (favoriteList.isEmpty()) toast("No favorites recorded yet!")
        item_list.adapter =
            FavoriteItemRecyclerViewAdapter(
                this, favoriteList, twoPane, this
            )
        item_list.adapter?.notifyDataSetChanged()
    }

    /**
     * The objective is to adapt the views every time the user perform an action
     * A list of product is set, but he can check his favorites too, so it's
     * necessary to update the views in order to prevent bad user experience
     * */
    private fun updateTitle(text: String) {
        setSupportActionBar(toolbar).apply {
            title = text
        }
    }

    private fun displayFavoriteItemFromMainActivity(bool: Boolean) {
        if (bool) {
            listFab.isVisible(false)
            toolbar.title = getString(R.string.toolbar_title_favorites)
            getFavoriteProductFromDB()
        }
    }

    // Interface
    override fun onItemSelected(productItem: ApiProductItem) {
        Log.d(TAG, "The product with the reference ${productItem.reference} is selected")
    }

    override fun onFavoriteItemSelected(favoriteProduct: FavoriteProduct) {
        Log.d(TAG, "The favorite product with the reference ${favoriteProduct.referenceId} is selected")
    }
}
