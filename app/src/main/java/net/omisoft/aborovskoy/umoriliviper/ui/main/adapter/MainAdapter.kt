package net.omisoft.aborovskoy.umoriliviper.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_trending_repo.view.*
import net.omisoft.aborovskoy.umoriliviper.R
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingResult

class MainAdapter(private val trendingResults: TrendingResult, private val listener: RepoListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trending_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val trendingRepo = trendingResults.items?.get(position)
        holder.name.text = trendingRepo?.name
        holder.site.text = trendingRepo?.cloneUrl
        holder.desc.text = trendingRepo?.description

        holder.itemView.setOnClickListener { trendingRepo?.let { it1 -> listener.onItemClick(it1) } }
    }

    override fun getItemCount(): Int {
        return trendingResults.items?.size ?:0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.name!!
        val desc = itemView.desc!!
        val site = itemView.site!!
    }

    interface RepoListener {
        fun onItemClick(trendingRepo: TrendingRepo)
    }
}
