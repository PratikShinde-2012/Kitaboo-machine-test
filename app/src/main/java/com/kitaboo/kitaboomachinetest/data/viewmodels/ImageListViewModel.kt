package com.kitaboo.kitaboomachinetest.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitaboo.kitaboomachinetest.data.models.ImageModel
import com.kitaboo.kitaboomachinetest.data.rest.ApiConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


//ViewModel class for image list activity
@HiltViewModel
class ImageListViewModel @Inject constructor() : ViewModel() {
    //Livedata objects for image url list
    //only exposing immutable livedata so only containing class can update its value
    private val _imageUrlListLiveData = MutableLiveData<List<ImageModel>>()
    val imageUrlListLiveData: LiveData<List<ImageModel>> = _imageUrlListLiveData

    //livedata object used to manage loading state
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    //function sets image urls in livedata
    fun getImageList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            delay(1200)
            _imageUrlListLiveData.postValue(
                ArrayList<ImageModel>().also {
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_1))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_2))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_3))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_4))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_1))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_2))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_3))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_4))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_1))
                    it.add(ImageModel(ApiConstants.BASE_URL + ApiConstants.ENDPT_PAGE_2))
                }
            )
            _loadingState.postValue(false)
        }

    }
}