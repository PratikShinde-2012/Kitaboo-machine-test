package com.kitaboo.kitaboomachinetest.ui.activities

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kitaboo.kitaboomachinetest.R
import com.kitaboo.kitaboomachinetest.data.models.ImageModel
import com.kitaboo.kitaboomachinetest.data.viewmodels.ImageListViewModel
import com.kitaboo.kitaboomachinetest.databinding.ActivityMainBinding
import com.kitaboo.kitaboomachinetest.ui.adapters.ImageAdapter
import com.kitaboo.kitaboomachinetest.utils.AppConstants
import com.kitaboo.kitaboomachinetest.utils.CommonUtils
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2
import dagger.hilt.android.AndroidEntryPoint


/*
Create a sample project ;
 Load 10 images in viewpager and on swipe of image provide page curl effect on each swipe.
for landscape mode load 2 images at a time for tablet devices.
 */

@AndroidEntryPoint
class ImageListActivity : AppCompatActivity() {
    private var orientation: Int = -1
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ImageListViewModel by viewModels()
    private lateinit var adapter: ImageAdapter
    private val imagesList = ArrayList<ImageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        initObservers()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Update the orientation
        orientation = newConfig.orientation
        // Notify the adapter about the orientation change
        adapter.updateOrientation(getIfShowLandscapeView())
    }

    //setting common views and instantiating adapters
    private fun initViews() {
        val type = getIfShowLandscapeView()
        adapter = ImageAdapter(imagesList, type)
        val bookFlipPageTransformer = BookFlipPageTransformer2()
        bookFlipPageTransformer.isEnableScale = true

        bookFlipPageTransformer.scaleAmountPercent = 10f
        binding.viewPager.setPageTransformer(bookFlipPageTransformer)
        binding.viewPager.adapter = adapter
    }

    //methods checks if device used is a tablet and if landscape mode is toggled returns true then
    //false otherwise
    private fun getIfShowLandscapeView(): Int {
        return if (CommonUtils.isTabletDevice(this) && CommonUtils.isLandscapeViewToggled(this)) AppConstants.DeviceOrientation.LANDSCAPE.orientation
        else AppConstants.DeviceOrientation.PORTRAIT.orientation
    }

    //method observes livedata objects in corresponding viewModel
    private fun initObservers() {
        viewModel.getImageList()
        viewModel.imageUrlListLiveData.observe(this) {
            resetDataset(it)
        }
        viewModel.loadingState.observe(this) {
            manageProgressBarVisibility(it)
        }
    }

    //Generic method to handle progress bar visibility based on loading state
    private fun manageProgressBarVisibility(isLoading: Boolean) {
        if (isLoading)
            binding.pbMain.visibility = View.VISIBLE
        else
            binding.pbMain.visibility = View.GONE
    }

    //once data is fetched, this method is triggered it updates viewpager dataset with fresh data
    @SuppressLint("NotifyDataSetChanged")
    private fun resetDataset(imageList: List<ImageModel>) {
        imagesList.clear()
        imagesList.addAll(imageList)
        adapter.notifyDataSetChanged()
    }
}