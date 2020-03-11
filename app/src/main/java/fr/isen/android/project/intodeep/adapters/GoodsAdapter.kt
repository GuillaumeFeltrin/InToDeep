package fr.isen.android.project.intodeep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.android.project.intodeep.R
import kotlinx.android.synthetic.main.goods_item.view.*

class GoodsAdapter(val items : ArrayList<String>, val items2 : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.goods_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.adviceName?.text = items.get(position)
        holder?.adviceDescription?.text = items2.get(position)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val adviceName = view.adviceName
    val adviceDescription = view.adviceDescription
}