package com.ansori.kmtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ansori.kmtest.models.repositories.UserRepository
import com.ansori.kmtest.models.responses.DataItem
import com.ansori.kmtest.models.responses.UserListResponse
import com.ansori.kmtest.models.service.Resource
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val repository = UserRepository()
    private var page = 1
    private var userListResponse: UserListResponse? = null
    private var _response = MutableLiveData<Resource<UserListResponse?>>()
    var response: LiveData<Resource<UserListResponse?>> = _response

    fun getUserList(perPage: Int) {
        viewModelScope.launch {
            _response.postValue(Resource.Loading)
            val response = repository.getUserList(page, perPage)
            _response.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<UserListResponse?>): Resource<UserListResponse?> {
        var oldData: MutableList<DataItem>?
        if (response.isSuccessful) {
            response.body()?.let {
                page++
                if (userListResponse == null) userListResponse = it else {
                    oldData = userListResponse?.data
                    val newData = it.data
                    if (newData != null) {
                        oldData?.addAll(newData)
                    }
                }
            }
            return Resource.Success(userListResponse ?: response.body())
            oldData?.clear()
        } else {
            return Resource.Error(try { response.errorBody()?.string()?.let { JSONObject(it).get("message") } } catch (e: JSONException) { e.localizedMessage } as String)
        }
    }
}