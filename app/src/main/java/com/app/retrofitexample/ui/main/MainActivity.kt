package com.app.retrofitexample.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.retrofitexample.data.local.UserDao
import com.app.retrofitexample.databinding.ActivityMainBinding
import com.app.retrofitexample.ui.creat.PostUser
import com.app.retrofitexample.viewmodels.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        binding.recyclerView.adapter = userAdapter
        viewModel.getUsersFromServer()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastCompletelyVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(this@MainActivity, "Last", Toast.LENGTH_LONG).show()
                }
                if (lastCompletelyVisibleItemPosition == userAdapter.itemCount - 1) {
                    viewModel.loadMoreUsers()
                }
            }
        })

        binding.addUser.setOnClickListener {
            val intent = Intent(this, PostUser::class.java)
            startActivity(intent)
        }

        userAdapter.click = {
            val intent = Intent(this, PostUser::class.java).apply {
                putExtra("user", it)
            }
            startActivity(intent)
        }

        userAdapter.deleteClick = {
            viewModel.deleteUser(it)
        }

        viewModel.allUser.observe(this, {
            binding.progressBar.visibility = View.GONE
            userAdapter.addUser(
                newUsers = it,
                hasMoreData = it.isNotEmpty()
            )
        })
    }
}