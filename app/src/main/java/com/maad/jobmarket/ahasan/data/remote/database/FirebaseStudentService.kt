package com.maad.jobmarket.ahasan.data.remote.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.maad.jobmarket.ahasan.domain.model.Student
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseStudentService  @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val collectionPath: String = "students",
    private val resumePath: String = "resumes"
) {
    suspend fun getStudent(studentId: String): Student {
        val snapshot = firestore.collection(collectionPath)
            .document(studentId)
            .get()
            .await()
        return snapshot.toObject(Student::class.java)!!
    }

    suspend fun fetchResume(studentId: String): Triple<String, String, Uri> {
        val resumeRef = storage.reference.child("$resumePath/$studentId")
        val resumeUri = resumeRef.downloadUrl.await()
        val metaData = resumeRef.metadata.await()
        val fileName = metaData.getCustomMetadata("fileName") ?: ""
        val fileMetaData = metaData.getCustomMetadata("fileMetaData") ?: ""
        return Triple(fileName, fileMetaData, resumeUri)
    }

    suspend fun addStudent(student: Student) {
        firestore.collection(collectionPath)
            .document(student.uid ?: UUID.randomUUID().toString())
            .set(student)
            .await()
    }
}