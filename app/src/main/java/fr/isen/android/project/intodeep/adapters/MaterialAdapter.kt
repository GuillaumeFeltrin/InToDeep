package fr.isen.android.project.intodeep.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.android.project.intodeep.R
import kotlinx.android.synthetic.main.material_item.view.*

class MaterialAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<MaterialViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        return MaterialViewHolder(LayoutInflater.from(context).inflate(R.layout.material_item, parent, false))
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        holder?.materialName?.text = items.get(position)
    }
}

class MaterialViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val materialName = view.materialName
}