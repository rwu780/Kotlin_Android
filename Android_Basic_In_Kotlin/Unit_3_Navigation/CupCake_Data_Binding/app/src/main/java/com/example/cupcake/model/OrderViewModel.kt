package com.example.cupcake.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00
private const val TAG = "OrderViewModel"

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity : LiveData<Int>
        get() = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String>
        get() = _flavor


    private val _pickupDate = MutableLiveData<String>()
    val pickUpDate : LiveData<String>
        get() = _pickupDate

    private val _price = MutableLiveData<Double>()
    val price : LiveData<String> = Transformations.map(_price){
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        
        _quantity.value = numberCupcakes
        updatePrice()
        Log.d(TAG, "setQuantity: ${_price.value}")
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor

    }

    fun setDate(pickUpDate : String) {
        _pickupDate.value = pickUpDate
        updatePrice()

    }

    fun hasNoFlavorSet() : Boolean {
        return _flavor.value.isNullOrEmpty()

    }

    private fun getPickupOptions() : List<String> {
        val options = mutableListOf<String>()

        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options

    }

    private fun updatePrice() {
        var calculatePrice = (quantity.value?: 0 ) * PRICE_PER_CUPCAKE

        if(pickUpDate.value == dateOptions[0])
            calculatePrice += PRICE_FOR_SAME_DAY_PICKUP

        _price.value = calculatePrice


    }

    fun resetOrder() {

        _quantity.value = 0
        _flavor.value = ""
        _pickupDate.value = dateOptions[0]
        _price.value = 0.0

    }




}