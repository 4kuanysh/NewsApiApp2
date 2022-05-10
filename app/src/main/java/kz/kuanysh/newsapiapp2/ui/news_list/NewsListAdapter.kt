package kz.kuanysh.newsapiapp2.ui.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.kuanysh.newsapiapp2.databinding.ItemArticleBinding
import kz.kuanysh.newsapiapp2.domain.entety.Article

class NewsListAdapter(
    private val onArticleClicked: (Article) -> Unit,
    private val onListEndReached: () -> Unit,
) : ListAdapter<Article, NewsListAdapter.NewsViewHolder>(ARTICLE_COMPARATOR) {

    companion object {
        private const val END_OFFSET = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(ItemArticleBinding.inflate(parent.inflater, parent, false))

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position >= currentList.lastIndex - END_OFFSET) {
            onListEndReached()
        }
    }

    inner class NewsViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val glide = Glide.with(itemView.context)

        fun bind(article: Article): Unit = with(binding) {
            title.text = article.title
            description.text = article.description
            glide.load(article.urlToImage).into(image)
            root.setOnClickListener {
                onArticleClicked(article)
            }
        }
    }
}

private val ViewGroup.inflater get() = LayoutInflater.from(context)

private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean = oldItem == newItem
}