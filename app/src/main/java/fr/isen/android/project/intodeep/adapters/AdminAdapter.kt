package fr.isen.android.project.intodeep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.android.project.intodeep.R
import kotlinx.android.synthetic.main.admin_item.view.*

class AdminAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<AdminViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(LayoutInflater.from(context).inflate(R.layout.admin_item, parent, false))
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder?.adminName?.text = items.get(position)
    }
}

class AdminViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val adminName = view.adminName
}