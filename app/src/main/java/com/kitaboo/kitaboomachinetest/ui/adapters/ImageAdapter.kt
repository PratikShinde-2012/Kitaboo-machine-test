package com.kitaboo.kitaboomachinetest.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kitaboo.kitaboomachinetest.data.models.ImageModel
import com.kitaboo.kitaboomachinetest.databinding.ImageItemLayout2Binding
import com.kitaboo.kitaboomachinetest.databinding.ImageItemLayoutBinding
import com.kitaboo.kitaboomachinetest.utils.AppConstants

//Pager adapter class
class ImageAdapter(private val images: ArrayList<ImageModel>, private var viewType: Int) :
    RecyclerView.Adapter<ViewHolder>() {
    private lateinit var binding: ImageItemLayoutBinding
    private lateinit var binding2: ImageItemLayout2Binding

    //Simple ViewHolder class for portrait view when showing one image at a time
    inner class PortraitImageViewHolder(binding: ImageItemLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(imageModel: ImageModel) {
            binding.ivPage.let {
                Glide.with(it)
                    .load(imageModel.imgUrl)
                    .into(it)
            }
        }
    }

    //Simple ViewHolder class for landscape view when showing two images at a time
    inner class LandscapeImageViewHolder(binding: ImageItemLayout2Binding) :
        ViewHolder(binding.root) {
        fun bind(imageModel: ImageModel, imageModel1: ImageModel) {
            binding2.let {
                Glide.with(it.ivPage1)
                    .load(imageModel.imgUrl)
                    .into(it.ivPage1)

                Glide.with(it.ivPage2)
                    .load(imageModel1.imgUrl)
                    .into(it.ivPage2)
            }
        }
    }

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            AppConstants.DeviceOrientation.PORTRAIT.orientation -> {
                binding = ImageItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PortraitImageViewHolder(binding)
            }
            AppConstants.DeviceOrientation.LANDSCAPE.orientation -> {
                binding2 = ImageItemLayout2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LandscapeImageViewHolder(binding2)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]

        when (holder.itemViewType) {
            AppConstants.DeviceOrientation.PORTRAIT.orientation -> {
                (holder as PortraitImageViewHolder).bind(item)
            }
            AppConstants.DeviceOrientation.LANDSCAPE.orientation -> {
                (holder as LandscapeImageViewHolder).bind(item, images[position + 1])
            }
        }
    }

    //method checks if landscape mode is on and returns half the size of list since showing 2 items at a time
    //returns original list size otherwise
    override fun getItemCount(): Int {
        return if (viewType == AppConstants.DeviceOrientation.PORTRAIT.orientation) images.size else images.size / 2
    }

    //method triggered when orientation change event is detected for tablet device only
    @SuppressLint("NotifyDataSetChanged")
    fun updateOrientation(viewType: Int) {
        this.viewType = viewType
        notifyDataSetChanged()
    }
}