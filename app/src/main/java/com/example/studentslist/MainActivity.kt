package com.example.studentslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() , StudentsAdapter.OnItemClickListener{
    private lateinit var recyclerView: RecyclerView
    private val studentsList = generateStudentList(5)
    private val studAdapter = StudentsAdapter(studentsList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = studAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        touchHelper.attachToRecyclerView(recyclerView)
    }


    // inserting and removing
    fun insertStudent(view: View){
        val stud = Student(setId(),R.drawable.ic_baseline_account,fakeName()+' '+fakeName(),fakeGroup())
        studentsList.add(stud)
        studAdapter.notifyItemInserted(studentsList.size)
    }
    fun removeStudent(view: View){
        val listToRem = ArrayList<Student>()
        val removeIds = studAdapter.getStudentsToRemove()
        if(removeIds.size!=0){
            studentsList.filter { it.id in removeIds }.forEach{listToRem.add(it)}
            studentsList.removeAll(listToRem)
            studAdapter.notifyDataSetChanged()
        }
        studAdapter.zeroOutStudToRemove()

    }

    // Listeners

    override fun onItemClick(position: Int) {
        val stud = studentsList[position]
        val intent = Intent(this, StudentDetail::class.java)
        intent.putExtra(StudentDetail.STUD, stud)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int) {
        val stud = studentsList[position]
        Toast.makeText(this, "Dragging ${stud.name}", Toast.LENGTH_SHORT).show()
    }

    // drag and drop
    private val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,0
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            Collections.swap(studentsList,viewHolder.adapterPosition,target.adapterPosition)
            studAdapter.notifyItemMoved(viewHolder.adapterPosition,target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }

    })


    // additional functions
    private fun randInt(len: Int) = (1..len).random()
    private fun fakeName(): String{
        var name = ('A'..'Z').random().toString()
        for(i in 0..randInt(10)){
            name+= ('a'..'z').random().toString()
        }
        return name
    }
    private fun fakeGroup() = randInt(10)
    private fun generateStudentList(len: Int):ArrayList<Student>{
        val students = ArrayList<Student>()
        students += Student(setId(students),R.drawable.ic_baseline_account,fakeName()+' '+fakeName(),fakeGroup())
        for(i in 0 until len-1){
            students += Student(setId(students),R.drawable.ic_baseline_account,fakeName()+' '+fakeName(),fakeGroup())
        }
        return students
    }

    private fun setId(list: ArrayList<Student>? = null): Int{
        var max = 0
        var _list = studentsList
        list?.let {
            _list = list
        }
        if(_list.size==0){
            return 0
        }
        for(i in _list){
            if(i.id>=max) {
                max = i.id
            }
        }
        return max+1
    }

}