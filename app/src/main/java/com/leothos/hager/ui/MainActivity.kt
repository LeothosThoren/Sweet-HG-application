package com.leothos.hager.ui

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.leothos.hager.*
import com.leothos.hager.model.DataItem
import com.leothos.hager.view_models.ProductsViewModel
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
    //Default values if the user click directly on Ok button
    private var countryValue = DEFAULT_COUNTRY_VALUE
    private var lastSyncValue = DEFAULT_LAST_SYNC_VALUE

    private val api: Api by lazy {
        RetrofitClient.apiService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
     *
     * */
    private fun openCalendarWidget() {
        selectionDateListener = DatePickerDialog.OnDateSetListener { v, year, month, dayOfMonth ->

            calendar.set(year, month, dayOfMonth)

            lastSyncValue = dateFormatter(calendar)
            countryValue = spinner.selectedItem.toString()

            textInfo.text = getString(
                R.string.info_message, countryValue, dateFormatter2(calendar)
            )
        }
    }


    /**
     * This method checks two things, the values retrieve from the widget
     * It can launch an api call and load the data
     * if the data return null or is empty a notification (toast) will alert the user
     * */
    fun callWebService(view: View) {
        toast("CallWebService ok")
        fetchWebService(countryValue, lastSyncValue)
        Log.d(TAG, "country = $countryValue and last sync = $lastSyncValue")
    }

    // --------------
    // Config
    // --------------

    private fun configureSpinner() {
        spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.country_id,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        spinner.adapter = spinnerAdapter
    }


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

        dateDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dateDialog.show()
    }

    // Configure viewModel in order to fetch data once and prevent multiple call
    private fun configureViewModel() {
        val model = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        model.getProducts(countryValue, lastSyncValue).observe(this, Observer<DataItem> {
            Log.d(TAG, it.brand.toString())
        })
    }

    private fun fetchWebService(countryZone: String, lastSync: String) {
        api.getProducts(countryZone, lastSync).enqueue(object : Callback<DataItem> {
            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                toast("Error ${t.toString()}")
            }

            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "fetched response = ${response.body()}")
                } else {
                    Log.i(TAG, "Error ${response.errorBody()}")
                }
            }

        })
    }
}

