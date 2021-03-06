package com.example.studentslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(),FragmentCommunicator{

    private lateinit var studentsListFG: StudentsFragment
    private lateinit var detailFragment :StudentDetailFragment

    private var landListFragment:Fragment? = null
    private var landDetailFragment:Fragment? = null

    private var landscape:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        //  write code below
        landListFragment = supportFragmentManager.findFragmentById(R.id.StudList)
        landDetailFragment = supportFragmentManager.findFragmentById(R.id.studDetail)
        Log.e("activity frag on create","this is avtivity on create frag ")
        localFragmentManager()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        localFragmentManager()
        Log.e("activity on restore","this is restore act ")
    }

    // local Fragment manager

    private fun localFragmentManager(): Boolean{
        if(landDetailFragment?.isVisible==true
            && landListFragment?.isVisible==true
        ){
            studentsListFG = landListFragment as StudentsFragment
            detailFragment = landDetailFragment as StudentDetailFragment
            landscape = true
            isInDetail(false)
        }
        else{
            studentsListFG = StudentsFragment()
            detailFragment = StudentDetailFragment()
            landscape = false
        }
        val frameLView = findViewById<FrameLayout>(R.id.FlMain)
        if(frameLView != null){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.FlMain, studentsListFG,"StudentList")
                    .commit()
            }
        }
        return landscape

    }
    // saving state

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    // communicator

    override fun sendData(stud: Student) {

        if(localFragmentManager()){
            detailFragment.setStudent(stud)
        }else{

            val bundle= Bundle()
            bundle.putSerializable(StudentDetailFragment.STUD, stud)
            detailFragment.arguments = bundle
            isInDetail(true)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.FlMain, detailFragment, "StudentDetail")
                .addToBackStack(null)
                .commit()
        }
    }

    override fun detailExit() {
        isInDetail(false)
    }

    override fun setFirstStudent() :Student{
        return Student(0,0,"Student Name", 0)
    }

    // adding and removing
    fun insertStudent(view: View){
        studentsListFG.insertStudent(
            view
        )
    }

    fun removeStudent(view: View){
        studentsListFG.removeStudent(view)
    }

    // visibility of buttons

    private fun isInDetail(boolean: Boolean){
        val insertBtn = findViewById<View>(R.id.insert_btn)
        val removeBtn = findViewById<Button>(R.id.remove_btn)
        insertBtn.isVisible = !boolean
        removeBtn.isVisible =! boolean

    }

//    override fun onItemLongClick(position: Int) {
//        val stud = studentsList[position]
//        Toast.makeText(this, "Dragging ${stud.name}", Toast.LENGTH_SHORT).show()
//    }

}