package com.example.studentslist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class StudentsFragment : Fragment() , StudentsAdapter.OnItemClickListener{
    private  lateinit var studAdapter: StudentsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var communicator: FragmentCommunicator
    private var studentsList  = generateStudentList(5)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_students, container, false)
        if( savedInstanceState != null){
            studentsList = savedInstanceState.getSerializable("savedStudList") as ArrayList<Student>
        }
        studAdapter = StudentsAdapter(studentsList,this)
        communicator = activity as FragmentCommunicator
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = getView()!!.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = studAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        touchHelper.attachToRecyclerView(recyclerView)

    }

    // is data saved

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("savedStudList",studentsList)
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
    fun getFirstStudent():Student{
        return studentsList[0]
    }

    // listeners
    override fun onItemClick(position: Int) {
        val stud = studentsList[position]
        communicator.sendData(stud)
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