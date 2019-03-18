package com.leothos.hager.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.leothos.hager.FAVORITE_ITEM_POSITION
import com.leothos.hager.ITEM_POSITION
import com.leothos.hager.POSITION_NOT_SET
import com.leothos.hager.R
import com.leothos.hager.ui.ItemDetailFragment.Companion.ARG_ITEM_POS
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragmentP = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        ItemDetailFragment.ARG_ITEM_POS,
                        intent.getIntExtra(ITEM_POSITION, POSITION_NOT_SET)
                    )
                }
            }

            val fragmentF = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        ItemDetailFragment.ARG_ITEM_FAVORITE_POS,
                        intent.getIntExtra(FAVORITE_ITEM_POSITION, POSITION_NOT_SET)
                    )
                }
            }

            Log.d("CHECK", "fragment1 = ${fragmentP.arguments}")
            Log.d("CHECK", "fragment2 = ${fragmentF.arguments}")


            if (fragmentP.arguments?.get(ARG_ITEM_POS) != POSITION_NOT_SET) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.item_detail_container, fragmentP)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.item_detail_container, fragmentF)
                    .commit()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            //Another argument here
            else -> super.onOptionsItemSelected(item)
        }
}
