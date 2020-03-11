package fr.isen.android.project.intodeep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.android.project.intodeep.R
import kotlinx.android.synthetic.main.basics_item.view.*

class BasicsAdapter(val items : ArrayList<String>, val items2 : ArrayList<String>, val context: Context) : RecyclerView.Adapter<BasicsViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicsViewHolder {
        return BasicsViewHolder(LayoutInflater.from(context).inflate(R.layout.basics_item, parent, false))
    }

    override fun onBindViewHolder(holder: BasicsViewHolder, position: Int) {
        holder?.basicsName?.text = items.get(position)
        holder?.basicsDescription?.text = items2.get(position)
    }
}

class BasicsViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val basicsName = view.basicsName
    val basicsDescription = view.basicsDescription
}