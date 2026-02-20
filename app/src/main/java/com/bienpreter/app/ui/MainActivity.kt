package com.bienpreter.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bienpreter.app.R
import com.bienpreter.app.data.model.Project
import com.bienpreter.app.databinding.ActivityMainBinding
import com.bienpreter.app.ui.adapter.ProjectAdapter
import com.bienpreter.app.ui.adapter.CategoryFilterAdapter
import com.bienpreter.app.ui.viewmodel.ProjectsViewModel
import com.bienpreter.app.ui.viewmodel.UiState

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProjectsViewModel
    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var categoryAdapter: CategoryFilterAdapter

    private val categories = listOf("Tous", "Produits & Services", "Immobilier", "Artisanat & BTP", "Agriculture")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProjectsViewModel::class.java]

        setupToolbar()
        setupRecyclerViews()
        setupSwipeRefresh()
        observeData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setupRecyclerViews() {
        // Category filters
        categoryAdapter = CategoryFilterAdapter(categories) { category ->
            viewModel.filterByCategory(category)
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // Projects
        projectAdapter = ProjectAdapter { project ->
            openProjectDetail(project)
        }
        binding.rvProjects.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = projectAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeResources(R.color.bp_green, R.color.bp_green_dark)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeData() {
        viewModel.stats.observe(this) { stats ->
            binding.tvInFunding.text = stats.inFunding.toString()
            binding.tvInRepayment.text = stats.inRepayment.toString()
            binding.tvRepaid.text = stats.fullyRepaid.toString()
        }

        viewModel.projects.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvProjects.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is UiState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                    if (state.data.isEmpty()) {
                        binding.rvProjects.visibility = View.GONE
                        binding.tvEmpty.visibility = View.VISIBLE
                    } else {
                        binding.rvProjects.visibility = View.VISIBLE
                        binding.tvEmpty.visibility = View.GONE
                        projectAdapter.submitList(state.data)
                    }
                }
                is UiState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openProjectDetail(project: Project) {
        val intent = Intent(this, ProjectDetailActivity::class.java).apply {
            putExtra(ProjectDetailActivity.EXTRA_PROJECT_ID, project.id)
            putExtra(ProjectDetailActivity.EXTRA_PROJECT_TITLE, project.title)
        }
        startActivity(intent)
    }
}
