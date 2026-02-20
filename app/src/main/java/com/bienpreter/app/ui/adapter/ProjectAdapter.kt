package com.bienpreter.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bienpreter.app.R
import com.bienpreter.app.data.model.Project
import com.bienpreter.app.databinding.ItemProjectBinding

class ProjectAdapter(
    private val onProjectClick: (Project) -> Unit
) : ListAdapter<Project, ProjectAdapter.ProjectViewHolder>(ProjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProjectViewHolder(
        private val binding: ItemProjectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            binding.apply {
                tvTitle.text = project.title
                tvCategory.text = project.category
                tvRate.text = "${project.rate}%"
                tvProgress.text = "${project.progress}%"
                progressBar.progress = project.progress.toInt()
                tvStatus.text = project.status

                chipAutoPilot.visibility = if (project.isAutoPilot) View.VISIBLE else View.GONE

                ivProjectImage.load(project.imageUrl) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(12f))
                    placeholder(R.drawable.placeholder_project)
                    error(R.drawable.placeholder_project)
                }

                // Progress color based on completion
                val progressColor = when {
                    project.progress >= 100 -> root.context.getColor(R.color.bp_green)
                    project.progress >= 75 -> root.context.getColor(R.color.bp_green)
                    project.progress >= 50 -> root.context.getColor(R.color.bp_yellow)
                    else -> root.context.getColor(R.color.bp_orange)
                }
                progressBar.setIndicatorColor(progressColor)

                root.setOnClickListener { onProjectClick(project) }
            }
        }
    }

    class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Project, newItem: Project) = oldItem == newItem
    }
}
