package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.BlogModel
import kotlinx.android.synthetic.main.card_donation.view.*

interface BlogListener {
    fun onDonationClick(donation: BlogModel)
}

class BlogAdapter constructor(var donations: ArrayList<BlogModel>,
                                  private val listener: BlogListener, blogAll: Boolean)
    : RecyclerView.Adapter<BlogAdapter.MainHolder>() {

    val blogAll = blogAll

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_donation,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = donations[holder.adapterPosition]
        holder.bind(donation,listener,blogAll)
    }

    override fun getItemCount(): Int = donations.size

    fun removeAt(position: Int) {
        donations.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(donation: BlogModel, listener: BlogListener, blogAll: Boolean) {
            itemView.tag = donation
            itemView.title.text = donation.title
            //itemView.post.text = donation.posttype.toString()
            //itemView.imageIcon.setImageResource(R.drawable.logo)
            if(!blogAll)
            itemView.setOnClickListener { listener.onDonationClick(donation) }
        }
    }
}