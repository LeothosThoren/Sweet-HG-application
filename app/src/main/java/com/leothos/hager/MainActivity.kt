package com.leothos.hager

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    private lateinit var selectionDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var calendar: Calendar
    //Default values if the user click directly on Ok button
    private var countryValue = DEFAULT_COUNTRY_VALUE
    private var lastSyncValue = DEFAULT_LAST_SYNC_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Methods
        configureSpinner()
        datePicker.setOnClickListener { configureDatePickerDialog() }
        openCalendarWidget()
    }


    // --------------
    // Action
    // --------------

    fun openCalendarWidget() {
        selectionDateListener = DatePickerDialog.OnDateSetListener { v, year, month, dayOfMonth ->
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            calendar.set(year, month, dayOfMonth)

            lastSyncValue = sdf.format(calendar.time)
            countryValue = spinner.selectedItem.toString()

            Log.d(TAG, "current date and time = $lastSyncValue")
            textInfo.text = getString(R.string.info_message, countryValue, lastSyncValue)
        }
    }


    fun callWebService(view: View) {
        toast("CallWebService ok")
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

}

