package com.izza.daftartemaan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.LauncherActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val listdata_teman: ArrayList<data_teman>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private val context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView
        val Alamat: TextView
        val NoHp: TextView
        val ListItem: LinearLayout

        init {
            Nama = itemView.findViewById(R.id.nama)
            Alamat = itemView.findViewById(R.id.alamat)
            NoHp = itemView.findViewById(R.id.no_hp)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design,
        parent, false)
        return ViewHolder(V)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val Nama: String? = listdata_teman.get(position).nama
        val Alamat: String? = listdata_teman.get(position).alamat
        val NoHp: String? = listdata_teman.get(position).no_hp

        holder.Nama.text = "Nama: $Nama"
        holder.Alamat.text = "Alamat: $Alamat"
        holder.NoHp.text = "NoHp: $NoHp"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when (i) { 0 -> {
                           val bundle = Bundle()
                            bundle.putString("dataNama", listdata_teman[position].nama)
                            bundle.putString("dataAlamat", listdata_teman[position].alamat)
                            bundle.putString("dataNoHP", listdata_teman[position].no_hp)
                            bundle.putString("getPrimaryKey", listdata_teman[position].key)
                            val intent = Intent(view.context, UpdateData::class.java)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        } 1 -> {
                            listener?.onDeleteData(listdata_teman.get(position), position)
                        }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return listdata_teman.size
    }

    var listener: dataListener? = null

    init {
        this.context = context
        this.listener = context as MyListData
    }

    interface dataListener {
        fun onDeleteData(data: data_teman?, position: Int)
    }
}