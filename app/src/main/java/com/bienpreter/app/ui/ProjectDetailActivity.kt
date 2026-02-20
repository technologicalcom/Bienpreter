package com.bienpreter.app.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bienpreter.app.R
import com.bienpreter.app.databinding.ActivityProjectDetailBinding
import com.bienpreter.app.ui.viewmodel.ProjectDetailViewModel

class ProjectDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PROJECT_ID = "extra_project_id"
        const val EXTRA_PROJECT_TITLE = "extra_project_title"
    }

    private lateinit var binding: ActivityProjectDetailBinding
    private lateinit var viewModel: ProjectDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val projectId = intent.getStringExtra(EXTRA_PROJECT_ID) ?: return finish()
        val projectTitle = intent.getStringExtra(EXTRA_PROJECT_TITLE)

        setupToolbar(projectTitle)

        viewModel = ViewModelProvider(this)[ProjectDetailViewModel::class.java]
        viewModel.loadProject(projectId)

        observeData()

        binding.btnLend.setOnClickListener {
            // Open login or lending flow
            startActivity(android.content.Intent(this, LoginActivity::class.java))
        }
    }

    private fun setupToolbar(title: String?) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title ?: "Détail du projet"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeData() {
        viewModel.project.observe(this) { project ->
            project?.let {
                binding.ivProjectImage.load(it.imageUrl) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(16f))
                    placeholder(R.drawable.placeholder_project)
                }

                binding.tvTitle.text = it.title
                binding.tvCategory.text = it.category
                binding.tvRate.text = "${it.rate}%"
                binding.tvProgress.text = "${it.progress}%"
                binding.progressBar.progress = it.progress.toInt()
                binding.tvStatus.text = it.status

                if (it.isAutoPilot) {
                    binding.chipAutoPilot.visibility = View.VISIBLE
                }

                binding.tvAmount.text = if (it.amount != null) {
                    "${String.format("%,.0f", it.amount)} €"
                } else {
                    "Connectez-vous pour voir"
                }

                binding.tvDuration.text = if (it.duration != null) {
                    "${it.duration} mois"
                } else {
                    "Connectez-vous pour voir"
                }

                binding.tvRating.text = it.rating ?: "—"
                binding.tvBorrower.text = it.borrower ?: "Masqué"
            }
        }
    }
}
