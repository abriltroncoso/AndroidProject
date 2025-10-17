package ar.edu.unicen.seminario.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.databinding.FiltersBinding
import ar.edu.unicen.seminario.ui.view.FilterViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.apply


@AndroidEntryPoint
class FilterActivity  : AppCompatActivity(){
    lateinit var binding: FiltersBinding
    private val viewModel by viewModels<FilterViewModel>()
    private val selectedPlatforms = mutableSetOf<String>()
    private val selectedGenres = mutableSetOf<String>()
    private var selectedOrdering: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suscribreToUi()

        setUpUi()
        observeViewModel()
        viewModel.loadFiltersData()

    }

    private fun suscribreToUi(){
        binding = FiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpUi(){
        binding.btnApplyFilters.setOnClickListener {
            applyFilters()
        }
        binding.btnClearFilters.setOnClickListener {
            clearFilters()
        }
        binding.btnClose.setOnClickListener {
            finish()
        }
        binding.buttonFiltersAgain.setOnClickListener {
            viewModel.loadFiltersData()
        }

        binding.chipGroupOrdering.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedOrdering = when {
                checkedIds.contains(binding.chipOrderName.id) -> "name"
                checkedIds.contains(binding.chipOrderNameDesc.id) -> "-name"
                checkedIds.contains(binding.chipOrderReleased.id) -> "released"
                checkedIds.contains(binding.chipOrderReleasedDesc.id) -> "-released"
                checkedIds.contains(binding.chipOrderRating.id) -> "-rating"
                else -> null
            }
        }

    }

    private fun observeViewModel() {
        viewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            binding.tvError.visibility = if (error != null) View.VISIBLE else View.GONE
            binding.tvError.text = error
            binding.buttonFiltersAgain.visibility = if (error != null) View.VISIBLE else View.GONE
        }.launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.platforms.collect { platforms ->
                binding.chipGroupPlatforms.removeAllViews()
                platforms.forEach { platform ->
                    val chip = layoutInflater.inflate(R.layout.item_filter_chip, binding.chipGroupPlatforms, false) as Chip
                    chip.apply {
                        text = platform.name
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                selectedPlatforms.add(platform.id.toString())
                            } else {
                                selectedPlatforms.remove(platform.id.toString())
                            }
                        }
                    }
                    binding.chipGroupPlatforms.addView(chip)

                }
            }
        }


        lifecycleScope.launch {
            viewModel.genres.collect { genres ->
                binding.chipGroupGenres.removeAllViews()
                genres.forEach { genre ->
                    val chip = layoutInflater.inflate(R.layout.item_filter_chip, binding.chipGroupGenres, false) as Chip
                    chip.apply {
                        text = genre.name
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                selectedGenres.add(genre.id.toString())
                            } else {
                                selectedGenres.remove(genre.id.toString())
                            }
                        }
                    }
                    binding.chipGroupGenres.addView(chip)
                }
            }
        }

    }

    private fun applyFilters(){
       val intent = Intent(this, MainActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("platforms", selectedPlatforms.joinToString(","))
        intent.putExtra("genres", selectedGenres.joinToString(","))
        intent.putExtra("ordering", selectedOrdering)
        startActivity(intent)

    }

    private fun clearFilters(){
        selectedPlatforms.clear()
        selectedGenres.clear()
        selectedOrdering = null

        for (i in 0 until binding.chipGroupPlatforms.childCount) {
            (binding.chipGroupPlatforms.getChildAt(i) as? Chip)?.isChecked = false
        }
        for (i in 0 until binding.chipGroupGenres.childCount) {
            (binding.chipGroupGenres.getChildAt(i) as? Chip)?.isChecked = false
        }
        binding.chipGroupOrdering.clearCheck()
    }




}





