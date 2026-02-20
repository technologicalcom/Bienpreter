package com.bienpreter.app.data.repository

import com.bienpreter.app.data.model.Project
import com.bienpreter.app.data.model.ProjectStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository {

    // Mock data based on the live site
    private val mockProjects = listOf(
        Project(
            id = "BP5129",
            title = "Commande Enertag",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 68.3,
            status = "Collecte en cours",
            category = "Produits & Services",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5129/5a2f7feeeae9cd1a3df80131e91a45a633d2b402.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP4824",
            title = "Saison UCS 2026 - Prototype 2",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 97.9,
            status = "Collecte en cours",
            category = "Produits & Services",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP4824/170ec4b4535cd5ac9f9d1e983c358dab91905b95.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP5131",
            title = "Commande Alten Pays-Bas",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 47.1,
            status = "Collecte en cours",
            category = "Produits & Services",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5131/4bb2bab86d696e1d2e2b70a389afa40b900886ed.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP5147",
            title = "Commande Vanwonen",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 38.9,
            status = "Collecte en cours",
            category = "Artisanat & BTP",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5147/e94acfb078947173ec419884ee6458322bcfe644.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP5144",
            title = "Commande Unieuro",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 47.2,
            status = "Collecte en cours",
            category = "Produits & Services",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5144/fc79da692d82a0e550adbe0aaf09d4e5e6332a02.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP5100",
            title = "Travaux Aveiro",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 38.4,
            status = "Collecte en cours",
            category = "Artisanat & BTP",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5100/5f9691749d3c0330362e1961a9034c4e6cf9b7e3.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP5128",
            title = "Acquisition, viabilisation et revente de terrains constructibles",
            borrower = null,
            amount = null,
            duration = null,
            rate = 13.50,
            rating = null,
            progress = 31.6,
            status = "Collecte en cours",
            category = "Immobilier",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5128/53cccdb93809f4c4d01db4ad4c52b5b0b159818b.jpg?width=600&quality=80",
            isAutoPilot = true
        ),
        Project(
            id = "BP5146",
            title = "Commande Tecnimont",
            borrower = null,
            amount = null,
            duration = null,
            rate = 15.00,
            rating = null,
            progress = 73.3,
            status = "Collecte en cours",
            category = "Produits & Services",
            imageUrl = "https://img.bprt.eu/uploads/pictures/BP5146/3ea27f41421d809c51935333.jpg?width=600&quality=80",
            isAutoPilot = true
        )
    )

    val stats = ProjectStats(
        upcoming = 0,
        inFunding = 8,
        inRepayment = 706,
        fullyRepaid = 3906
    )

    suspend fun getProjects(
        category: String? = null,
        status: String? = null,
        orderBy: String? = null
    ): Result<List<Project>> = withContext(Dispatchers.IO) {
        try {
            // In production, this would call the API
            // For now, return mock data filtered
            var result = mockProjects
            if (category != null && category != "Tous") {
                result = result.filter { it.category == category }
            }
            if (status != null) {
                result = result.filter { it.status.contains(status, ignoreCase = true) }
            }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProjectById(id: String): Result<Project> = withContext(Dispatchers.IO) {
        try {
            val project = mockProjects.find { it.id == id }
                ?: return@withContext Result.failure(Exception("Projet non trouvé"))
            Result.success(project)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
