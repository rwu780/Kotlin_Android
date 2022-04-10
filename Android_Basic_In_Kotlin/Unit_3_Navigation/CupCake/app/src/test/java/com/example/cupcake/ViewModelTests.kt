package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTests {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever {}
        viewModel.price.observeForever {}

        viewModel.setQuantity(12)

        assertEquals(12, viewModel.quantity.value)
        assertEquals("$27.00", viewModel.price.value)

    }
}