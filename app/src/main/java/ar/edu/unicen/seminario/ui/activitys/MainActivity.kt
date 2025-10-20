package ar.edu.unicen.seminario.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.media.session.MediaButtonReceiver.handleIntent
import androidx.paging.LoadState
import androidx.paging.PagingData
import ar.edu.unicen.seminario.constants.Constants
import ar.edu.unicen.seminario.databinding.ActivityMainBinding
import ar.edu.unicen.seminario.ui.GameAdapter
import ar.edu.unicen.seminario.ui.view.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    // Declarar el adapter como propiedad de la clase
    private lateinit var gameAdapter: GameAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Inicializar el adapter antes de configurar la UI
        gameAdapter = GameAdapter { game ->
            val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
            intent.putExtra("id", game.id)
            startActivity(intent)
        }

        suscribreToUi()
        suscribeToViewModel()
    }

    private fun suscribreToUi(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar el adapter al RecyclerView
        binding.gamesList.adapter = gameAdapter

        binding.buttonFiltrar.setOnClickListener {
           val intent = Intent(this, FilterActivity::class.java)
           startActivity(intent)

        }

       binding.buttonGamesAgain?.setOnClickListener {
           gameAdapter.retry()
       }
    }

    private fun suscribeToViewModel(){


        // Observa el Flow de PagingData y envía los datos al adapter
        lifecycleScope.launch {
            viewModel.gamesPagingData.collectLatest { pagingData ->
                gameAdapter.submitData(pagingData)
            }
        }
        //maneja los estados de carga usando loadStateFlow
        lifecycleScope.launch {
            //Loading state
            gameAdapter.loadStateFlow.collect { loadStates ->
                val isLoading = loadStates.refresh is LoadState.Loading
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

                //Error state
                val errorState = loadStates.refresh as? LoadState.Error
                    ?: loadStates.append as? LoadState.Error
                    ?: loadStates.prepend as? LoadState.Error
                errorState?.let {
                   showError(it.error)
                } ?: hideError()
            }
        }

    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val platforms = intent.getStringExtra("platforms")
        val genres = intent.getStringExtra("genres")
        val ordering = intent.getStringExtra("ordering")
        viewModel.applyFilters(platforms,genres,ordering)


    }

    private fun showError(error: Throwable){
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text= when {
            error.message?.contains("timeout", ignoreCase = true) == true ->
                "La conexión tardó demasiado. Verificá tu internet."
            error.message?.contains("Unable to resolve host", ignoreCase = true) == true ->
                "No se pudo conectar al servidor. ¿Estás conectado?"
            else -> "Ocurrió un error inesperado. Intentá nuevamente."
        }
        binding.buttonGamesAgain?.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
        binding.buttonGamesAgain?.visibility = View.GONE
    }


}