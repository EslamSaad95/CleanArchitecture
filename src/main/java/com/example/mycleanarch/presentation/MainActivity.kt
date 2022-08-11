package com.example.mycleanarch.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mycleanarch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.getRecipeDetails()

        viewModel.loading.observe(this) {
            if (it)
                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
        }
        viewModel.recipeData.observe(this) {
            Toast.makeText(this, it.title, Toast.LENGTH_LONG).show()
        }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.btnValidate.setOnClickListener {
            viewModel.validateEmailInputs(binding.etEmail.text.toString())
        }
    }


}