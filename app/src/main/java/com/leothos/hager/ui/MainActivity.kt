package com.leothos.hager.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.leothos.hager.*
import com.leothos.hager.data.DataManager
import com.leothos.hager.model.api.ApiProductItem
import com.leothos.hager.model.api.ApiProductsResponse
import com.leothos.hager.view_models.ProductsViewModel
import com.leothos.hager.web_services.Api
import com.leothos.hager.web_services.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    private lateinit var selectionDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var calendar: Calendar
    private lateinit var model: ProductsViewModel
    //Default values if the user click directly on Ok button
    private var countryValue = DEFAULT_COUNTRY_VALUE
    private var lastSyncValue = DEFAULT_LAST_SYNC_VALUE

    private val api: Api by lazy {
        RetrofitClient.apiService
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        //Methods
        configureSpinner()
        datePicker.setOnClickListener { configureDatePickerDialog() }
        openCalendarWidget()
        configureViewModel()
    }


    // --------------
    // Action
    // --------------

    /**
     * openCalendarWidget method allows the user to select a date in the DatePickerDialog widget
     * */
    private fun openCalendarWidget() {
        selectionDateListener = DatePickerDialog.OnDateSetListener { v, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            lastSyncValue = dateFormatterEn(calendar)

            //To show data after the selection
            updateTextView()
        }
    }

    /**
     * This method allow to perform a call after checking the permissions on the device
     * */
    private fun makeAPhoneCall() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel: $HOT_LINE_NUMBER")
            startActivity(intent)
        }
    }


    /**
     * This method checks two things, the values retrieve from the widget
     * It can launch an api call and load the data
     * if the data return null or is empty a notification (toast) will alert the user
     * And open a naw activity to display a list of product
     * */
    fun callWebService(view: View) {
        Log.d(TAG, "country = $countryValue and last sync = $lastSyncValue")
        updateTextView()
        handleProgressBar()
        fetchWebService(countryValue, lastSyncValue)
    }

    private fun startActivity() {
        val i = Intent(this, ItemListActivity::class.java)
        startActivity(i)
    }

    // --------------
    // UI
    // --------------

    //Set up TextView
    private fun updateTextView() {
        countryValue = DataManager.countryMap.getValue(spinner.selectedItem.toString())
        textDateInfo.text = getString(
            R.string.info_message, dateFormatterFR(lastSyncValue)
        )
    }

    //Set up Progressbar
    private fun handleProgressBar() {
        progressBar.visibility = View.VISIBLE
        textDateInfo.visibility = View.VISIBLE
        progressBar.indeterminateDrawable
            .setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
    }

    //Set up ToolBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_call_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.action_call_hotline -> {
                makeAPhoneCall()
                return true
            }
            R.id.action_show_favorite -> {
                val i = Intent(this, ItemListActivity::class.java)
                i.putExtra(FAVORITE_AVAILABLE, true)
                startActivity(i)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // --------------
    // Config
    // --------------

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeAPhoneCall()
            } else {
                toast(getString(R.string.permission_denied))
            }
        }
    }

    /**
     * A simple configuration of a spinner object in order to help the user to select an item
     * */
    private fun configureSpinner() {
        spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.country_id,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        spinner.adapter = spinnerAdapter
    }


    /**
     * This method create an instance of Calendar and DatePickerDialog objects
     * The goal is to prepare and prompt a widget to the user who can select a date within
     * */
    private fun configureDatePickerDialog() {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateDialog = DatePickerDialog(
            this,
            android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
            selectionDateListener, year, month, day
        )
        dateDialog.show()
    }

    // Configure viewModel in order to fetch data once and prevent multiple call
    private fun configureViewModel() {
        model = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        model.getProducts(countryValue, lastSyncValue).observe(this, Observer<ApiProductsResponse> {
            Log.d(TAG, it.data.toString())
        })
    }

    // --------------
    // Retrofit
    // --------------

    /**
     * fetchWebService method fetch data from web service, it takes two parameters
     * which correspond to the url query.
     * It handles some cases like a null body or a connectivity problem, the user is warned in each cases
     *
     * @param countryZone
     * @param lastSync
     * */
    private fun fetchWebService(countryZone: String, lastSync: String) {
        api.getProducts(countryZone, lastSync).enqueue(object : Callback<ApiProductsResponse> {
            override fun onFailure(call: Call<ApiProductsResponse>, t: Throwable) {
                Log.i(TAG, "Error ${t.message}")
                toast(getString(R.string.connectivity_problem))
            }

            override fun onResponse(
                call: Call<ApiProductsResponse>,
                apiProductsResponse: Response<ApiProductsResponse>
            ) {
                when {
                    apiProductsResponse.isSuccessful -> {
                        DataManager.dataItems = apiProductsResponse.body()?.data as ArrayList<ApiProductItem>
                        if (DataManager.dataItems.isEmpty()) toast(getString(R.string.no_data_found))
                        else {
                            Log.d(TAG, "fetched response = ${apiProductsResponse.body()?.data?.get(0)}")
                            startActivity()
                        }
                    }
                }
                progressBar.visibility = View.GONE
            }

        })
    }
}

