package com.example.studentslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StudentsAdapter(private val students_list: List<Student>,
                      private val listener: OnItemClickListener?

                      ) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>(){
    private var studentsToRemove: ArrayList<Int> = ArrayList<Int>()
    inner class StudentViewHolder(itemView:View): RecyclerView.ViewHolder(itemView), View.OnClickListener//,View.OnLongClickListener
    {
        val image: ImageView  = itemView.findViewById(R.id.acc_image)
        val name: TextView  = itemView.findViewById(R.id.student_name_txt)
        val group: TextView  = itemView.findViewById(R.id.student_group)
        var forDelete: CheckBox = itemView.findViewById(R.id.checkbox_cheese)

        init {
            itemView.setOnClickListener(this)
            //itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            if(adapterPosition!=RecyclerView.NO_POSITION){
                listener?.onItemClick(adapterPosition)
            }
        }

//        override fun onLongClick(v: View?): Boolean {
//            if(adapterPosition!=RecyclerView.NO_POSITION){
//                listener.onItemClick(adapterPosition)
//            }
//            return true
//        }

    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
        //fun onItemLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = students_list[position]

        holder.image.setImageResource(currentItem.imageId)
        holder.name.text = currentItem.name.toString()
        holder.group.text = currentItem.group.toString()

        holder.forDelete.isChecked = false
        holder.forDelete.setOnClickListener{onCheckboxClicked(holder.forDelete,currentItem.id)}

    }

    override fun getItemCount(): Int {
        return students_list.size
    }
    companion object{

    }

    fun getStudentsToRemove():ArrayList<Int>{
        return studentsToRemove
    }
    fun zeroOutStudToRemove(){
        studentsToRemove = ArrayList<Int>()
    }
    private fun onCheckboxClicked(view: View,id: Int ){
        if ( view is CheckBox){
            if(view.isChecked){
                studentsToRemove.add(id)
            }else{
                studentsToRemove.remove(id)
            }

        }
    }
}