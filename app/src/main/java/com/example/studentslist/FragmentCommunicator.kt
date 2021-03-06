package com.example.studentslist

interface FragmentCommunicator {

    fun sendData(stud:Student)
    fun detailExit()
    fun setFirstStudent():Student
}