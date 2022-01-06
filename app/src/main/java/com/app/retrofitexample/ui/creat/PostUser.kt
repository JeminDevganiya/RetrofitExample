package com.app.retrofitexample.ui.creat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.app.retrofitexample.constant.Constant
import com.app.retrofitexample.databinding.ActivityPostuserBinding
import com.app.retrofitexample.ui.main.MainActivity
import com.app.retrofitexample.ui.main.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostUser : AppCompatActivity() {
    private lateinit var binding: ActivityPostuserBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val updateUser: User? = intent.extras?.getSerializable(Constant.USER) as User?
        if (updateUser != null) {
            binding.personName.setText(updateUser.firstName)
            binding.personJob.setText(updateUser.lastName)
        }

        binding.submitButton.setOnClickListener {
            val name = binding.personName.text.toString()
            val jobStates = binding.personJob.text.toString()

            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
                }
                jobStates.isEmpty() -> {
                    Toast.makeText(this, "Please Enter Job States", Toast.LENGTH_LONG).show()
                }
                else -> {
                    if (updateUser != null) {
                        viewModel.updateUser(name, jobStates, updateUser.id)
                    } else {
                        viewModel.postUser(name, jobStates)
                    }
                }
            }
        }

        viewModel.apiRes.observe(this, {
            binding.progressBarSubmit.isVisible = it
        })

        viewModel.success.observe(this, {
            finish()
        })

        viewModel.error.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
}