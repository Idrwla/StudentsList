package com.example.studentslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class StudentDetailFragment : Fragment() {
    companion object{
        const val STUD ="Student object"
    }

    private lateinit var communicator: FragmentCommunicator
    private lateinit var student: Student

    private var studImage: ImageView?  =null
    private var studName: TextView?  =null
    private var studGroup: TextView?  =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("detail frag on create","this is detail frag ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_student_detail, container, false)
        communicator = activity as FragmentCommunicator
        student = communicator.setFirstStudent()
        if (savedInstanceState != null){
            student = savedInstanceState.getSerializable("savedStudent") as Student
        }
        else if(arguments != null){
            student  = arguments?.getSerializable(STUD) as Student
        }
        Log.e("frag created view","this is detail  createdfrag ")
        return view
    }

    fun setStudent(student: Student){
        this.student = student
        studImage?.setImageResource(student.imageId)
        studName?.text = student.name
        studGroup?.text = studGroup?.text.toString() + student.group.toString()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        communicator.detailExit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("savedStudent", student)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studImage = getView()?.findViewById<ImageView>(R.id.stud_img)
        studImage?.setImageResource(student.imageId)

        studName = getView()?.findViewById<TextView>(R.id.stud_name)
        studName?.text = student.name

        studGroup = getView()?.findViewById<TextView>(R.id.stud_group)
        studGroup?.text = studGroup?.text.toString() + student.group.toString()
    }

}