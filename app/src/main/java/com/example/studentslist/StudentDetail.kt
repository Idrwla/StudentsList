package com.example.studentslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class StudentDetail : AppCompatActivity() {
    companion object{
        val STUD ="Student object"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val student = intent.getSerializableExtra(STUD) as Student

        val studImage = findViewById<ImageView>(R.id.stud_img)
        studImage.setImageResource(student.imageId)

        val studName = findViewById<TextView>(R.id.stud_name)
        studName.text = student.name

        val studGroup = findViewById<TextView>(R.id.stud_group)
        studGroup.text = studGroup.text.toString() + student.group.toString()
    }
}