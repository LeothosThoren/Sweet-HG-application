package com.leothos.hager.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.leothos.hager.R
import com.leothos.hager.adapters.ItemRecyclerViewAdapter
import com.leothos.hager.data.DataManager
import com.leothos.hager.model.api.ApiProductItem
import com.leothos.hager.toast
import com.leothos.hager.view_models.FavoriteProductViewModel
import com.leothos.hager.view_models.ProductsViewModel
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity(),
ItemRecyclerViewAdapter.OnItemSelectedListener{

        /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private val TAG = this::class.java.simpleName
    private var twoPane: Boolean = false
    private var dataItem = DataManager.dataItems
    private lateinit var model: ProductsViewModel
    private lateinit var favoriteProductViewModel: FavoriteProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        configureViewModel()
        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.access_to_favorites), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ItemRecyclerViewAdapter(this, dataItem, twoPane, Glide.with(this),this)
    }

    private fun configureViewModel() {
        model = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        item_list.adapter?.notifyDataSetChanged()
    }

    override fun onItemSelected(productItem: ApiProductItem) {
        toast("The product with the reference ${productItem.reference} is selected")
    }
}
