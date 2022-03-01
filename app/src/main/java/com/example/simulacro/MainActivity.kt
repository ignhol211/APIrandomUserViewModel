package com.example.simulacro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simulacro.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TextoAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.downloadInfo()

        binding.descargarTodos.setOnClickListener {
            viewModel.getAllUsers()
        }

        binding.descargarChicos.setOnClickListener {
            viewModel.getMaleUsers()
        }

        binding.descargarChicas.setOnClickListener {
            viewModel.getFemaleUsers()
        }

        initObserver()

    }

    private fun initObserver(){
        viewModel.responseUser.observe(this){userResponse ->
            adapter = TextoAdapter(userResponse.results)
            binding.recyclerView.adapter = adapter
        }

        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this){responseText ->
            showSnackbar(responseText)
        }
    }
    private fun setVisible(){
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone(){
        binding.pbDownloading.visibility = View.INVISIBLE
    }

    private fun showSnackbar(text : String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }
}