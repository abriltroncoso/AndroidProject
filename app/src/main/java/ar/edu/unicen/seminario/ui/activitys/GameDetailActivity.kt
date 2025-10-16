package ar.edu.unicen.seminario.ui.activitys

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminario.constants.Constants
import ar.edu.unicen.seminario.databinding.GameDetailBinding
import ar.edu.unicen.seminario.ui.view.GameDetailView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity() {
    lateinit var binding: GameDetailBinding
    val viewModel by viewModels<GameDetailView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        suscribeToUi()
        setupToolbar()
        suscribreToViewModel()
        val param = intent.extras?.getInt("id")
        if (param != null){
            viewModel.getGame(param, Constants.API_KEY)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun suscribeToUi(){
        binding = GameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun suscribreToViewModel(){
        viewModel.loading.onEach { loading ->
            if (loading){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        }.launchIn(lifecycleScope)

        viewModel.game.onEach { game ->
            binding.tvGameTitle.text = game?.name
            binding.tvReleaseDate.text = game?.released
            binding.tvMetacritic.text = game?.metaCritic.toString()
            binding.tvPlaytime.text = game?.playTime.toString()
            binding.tvRatingsCount.text = game?.ratingsCount.toString()
            binding.tvEsrbRating.text = game?.esrbRating?.name
            binding.tvPlatforms.text = game?.platform?.joinToString(", ") { it.platform.name }
            val descriptionHtml = game?.description ?: "" // tu HTML
            binding.tvDescription.text = Html.fromHtml(descriptionHtml, Html.FROM_HTML_MODE_LEGACY)
            val imageUrl = game?.backgroundImg
            if (!imageUrl.isNullOrBlank()) {
                // Recomendado: ligar Glide al Fragment para respetar el lifecycle
                Glide.with(binding.ivGameBackground.context)
                    .load(imageUrl) // reemplazá por tu drawable
                    .centerCrop()
                    .into(binding.ivGameBackground)              // reemplazá por el id real del ImageView
            } else {
                // Manejar el caso en que imageUrl es nulo o vacío
            }





        }.launchIn(lifecycleScope)


    }

}