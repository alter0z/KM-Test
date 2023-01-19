package com.ansori.kmtest.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ansori.kmtest.adapters.UserListAdapter
import com.ansori.kmtest.databinding.ActivityThirdScreenBinding
import com.ansori.kmtest.listeners.OnUserClickListener
import com.ansori.kmtest.models.service.Resource
import com.ansori.kmtest.viewmodels.UserViewModel
import java.util.jar.Attributes.Name

class ThirdScreen : AppCompatActivity() {
    private var _binding: ActivityThirdScreenBinding? = null
    private val binding get() = _binding!!
    private val userListViewModel: UserViewModel by viewModels()
    private var adapter: UserListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pullRefresh.setOnRefreshListener {
            userListViewModel.getUserList(10)
            binding.pullRefresh.isRefreshing =  false
        }

        binding.back.setOnClickListener { finish() }

        val name = intent.getStringExtra(NAME)

        userListViewModel.getUserList(10)
        observeAnyChangesPopularMovie()
        setupRecyclerView()

        adapter?.onUserClickListener(object: OnUserClickListener{
            override fun onUserClick(username: String) {
                SecondScreen.secondScreen?.finish()
                val intent = Intent(this@ThirdScreen, SecondScreen::class.java)
                intent.putExtra(SecondScreen.USERNAME, username)
                intent.putExtra(SecondScreen.NAME, name)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun observeAnyChangesPopularMovie() {
        userListViewModel.response.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        hideLoading()
                        it.data?.data?.let { data -> adapter?.differ?.submitList(data.toList()) }
                        if (it.data?.data?.size == 0) binding.emptyState.visibility = View.VISIBLE else binding.emptyState.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter()
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.userList.adapter = adapter
        binding.userList.layoutManager = layoutManager

        binding.userList.addOnScrollListener(scrollListener)
    }

    private fun showLoading() {
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.loading.hide()
    }


    private val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                userListViewModel.getUserList(10)
            }
        }
    }

    companion object {
        const val NAME = "name"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}