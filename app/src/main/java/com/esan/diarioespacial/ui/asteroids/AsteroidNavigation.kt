package com.esan.diarioespacial.ui.asteroids

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.esan.diarioespacial.ui.navigation.Routes

/** Navegación interna de la opción Asteroides (reemplaza el stub de la base común). */
fun NavGraphBuilder.asteroidGraph(navController: NavHostController) {
    navigation(startDestination = Routes.NEO_LIST, route = Routes.ASTEROIDES) {

        composable(Routes.NEO_LIST) { entry ->
            AsteroidListScreen(
                viewModel = entry.asteroidViewModel(navController),
                onOpenDetail = { id -> navController.navigate(Routes.neoDetail(id)) },
                onOpenFavorites = { navController.navigate(Routes.NEO_FAVORITES) }
            )
        }

        composable(Routes.NEO_DETAIL) { entry ->
            AsteroidDetailScreen(
                viewModel = entry.asteroidViewModel(navController),
                id = entry.arguments?.getString("id").orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.NEO_FAVORITES) { entry ->
            AsteroidFavoritesScreen(
                viewModel = entry.asteroidViewModel(navController),
                onOpenDetail = { id -> navController.navigate(Routes.neoDetail(id)) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/** Un solo ViewModel compartido por las 3 pantallas (vive mientras viva el grafo "asteroides"). */
@Composable
private fun NavBackStackEntry.asteroidViewModel(navController: NavHostController): AsteroidViewModel {
    val parent = remember(this) { navController.getBackStackEntry(Routes.ASTEROIDES) }
    return viewModel(viewModelStoreOwner = parent, factory = AsteroidViewModel.Factory)
}
