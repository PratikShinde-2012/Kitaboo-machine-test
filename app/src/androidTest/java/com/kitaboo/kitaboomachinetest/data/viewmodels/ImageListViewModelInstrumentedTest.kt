package com.kitaboo.kitaboomachinetest.data.viewmodels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kitaboo.kitaboomachinetest.data.models.await
import com.kitaboo.kitaboomachinetest.data.rest.ApiConstants
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageListViewModelInstrumentedTest : TestCase() {
    private lateinit var viewModel: ImageListViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = ImageListViewModel()
    }

    @Test
    fun testImageUrlListSize() {
        runBlocking {
            viewModel.getImageList()
            val data = viewModel.imageUrlListLiveData.await()
            assertTrue(data.size == 10)
        }
    }

    @Test
    fun testImageUrlListNotEmpty() {
        runBlocking {
            viewModel.getImageList()
            val data = viewModel.imageUrlListLiveData.await()
            assertTrue(data.isNotEmpty())
        }
    }

    @Test
    fun testImageUrlValidity() {
        runBlocking {
            viewModel.getImageList()
            val data = viewModel.imageUrlListLiveData.await()
            data.map {
                assertTrue(it.imgUrl.contains(ApiConstants.BASE_URL))
            }

        }
    }
}