package ar.edu.unicen.seminario.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminario.databinding.ListItemGameBinding
import ar.edu.unicen.seminario.ddl.models.Game
import com.bumptech.glide.Glide

class GameAdapter(
    private val onGameClick: (Game) -> Unit
) : PagingDataAdapter<Game, GameAdapter.GameViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ListItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        if (game != null) {
            holder.bind(game)
        }
    }

    inner class GameViewHolder(
        private val binding: ListItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.tvGameName.text = game.name
            binding.tvRating.text = game.rating.toString()
            binding.tvReleaseDate.text = game.released
            binding.tvPlatforms.text = game.platform?.joinToString(", ") { it.platform.name }

            Glide.with(binding.ivGameImage.context)
                .load(game.backgroundImg)
                .centerCrop()
                .into(binding.ivGameImage)

            binding.root.setOnClickListener {
                onGameClick(game)
            }
        }
    }

    class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}