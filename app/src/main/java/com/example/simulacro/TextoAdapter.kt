package com.example.simulacro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simulacro.databinding.ItemUserBinding


class TextoAdapter (var listaUsers : List<Result>) : RecyclerView.Adapter <TextoAdapter.TextoViewHolder>(){

    class TextoViewHolder (var itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemUserBinding.userNombre.text = listaUsers[position].name.first
        holder.itemUserBinding.userApellido.text = listaUsers[position].name.last

        holder.itemUserBinding.userNombre.setOnClickListener(){

        }
    }

    override fun getItemCount(): Int {
        return listaUsers.size
    }


}