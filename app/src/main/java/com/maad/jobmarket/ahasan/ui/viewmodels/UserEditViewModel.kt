package com.maad.jobmarket.ahasan.ui.viewmodels

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.maad.jobmarket.ahasan.data.model.Academic
import com.maad.jobmarket.ahasan.data.model.Address
import com.maad.jobmarket.ahasan.data.model.Details
import com.maad.jobmarket.ahasan.data.model.Student
import com.maad.jobmarket.ahasan.data.model.Tpo
import com.maad.jobmarket.ahasan.utils.Constants.Companion.COLLECTION_PATH_STUDENT
import com.maad.jobmarket.ahasan.utils.Constants.Companion.COLLECTION_PATH_TPO
import com.maad.jobmarket.ahasan.utils.Constants.Companion.PROFILE_IMAGE_PATH
import com.maad.jobmarket.ahasan.utils.Constants.Companion.RESUME_PATH
import com.maad.jobmarket.core.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserEditViewModel : ViewModel() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val studentId: String by lazy { mAuth.currentUser?.uid.toString() }
    private val mStorage: StorageReference by lazy { FirebaseStorage.getInstance().reference }
    private val mFirestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val mRealtimeDb: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }
    private var tpoListener : ListenerRegistration? = null

    private var imageUri: Uri? = null

    fun setImageUri(imageUri: Uri) {
        this.imageUri = imageUri
    }

    fun getImageUri(): Uri? {
        return this.imageUri
    }

    private val _student: MutableLiveData<Resource<Student>> = MutableLiveData()
    val student: LiveData<Resource<Student>> = _student
    init {
        loadDummyStudent()
    }
    private val _tpoList: MutableLiveData<List<Tpo>> = MutableLiveData(emptyList())
    val tpoList: LiveData<List<Tpo>> = _tpoList

    private val _updateState: MutableLiveData<Resource<String>> = MutableLiveData()
    val updateState: LiveData<Resource<String>> = _updateState

    private val _resumeState: MutableLiveData<Resource<Triple<String, String, Uri>>> = MutableLiveData()
    val resumeState: LiveData<Resource<Triple<String, String, Uri>>> = _resumeState

    private val _deleteState: MutableLiveData<Resource<String>> = MutableLiveData()
    val deleteState: LiveData<Resource<String>> = _deleteState

    fun fetchStudent() {
        viewModelScope.launch(IO) {
            try {
                _student.postValue(Resource.Loading())
                val studentRef = mFirestore.collection(COLLECTION_PATH_STUDENT).document(studentId).get().await()
                val student = studentRef.toObject(Student::class.java)!!
                _student.postValue(Resource.Success(student))
            } catch (error: Exception) {
                Log.d(TAG, "Error: ${error.message}")
                _student.postValue(Resource.Error(error.message!!))
            }
        }
    }

    fun fetchResume() {
        viewModelScope.launch(IO) {
            try {
                _resumeState.postValue(Resource.Loading())
                val resumeRef = mStorage.child(RESUME_PATH).child(studentId)
                val resumeUri = resumeRef.downloadUrl.await()
                val metaData = resumeRef.metadata.await()
                val fileName = metaData.getCustomMetadata("fileName") ?: ""
                val fileMetaData = metaData.getCustomMetadata("fileMetaData") ?: ""
                val resumeData = Triple(fileName, fileMetaData, resumeUri)
                _resumeState.postValue(Resource.Success(resumeData))
            } catch (error: Exception) {
                val errorMessage = error.message!!
                _resumeState.postValue(Resource.Error(errorMessage))
            }
        }
    }

    fun fetchTpo() {
        viewModelScope.launch(IO) {
            tpoListener = mFirestore.collection(COLLECTION_PATH_TPO)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    val documents = value?.documents!!
                    val tpoList = documents.map {
                        it.toObject(Tpo::class.java)!!
                    }
                    _tpoList.postValue(tpoList)
                }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch(IO) {
            try {
                _updateState.postValue(Resource.Loading())
                val studentDetail = student.details!!
                val firebaseStorageImagePrefix = "https://firebasestorage.googleapis.com/"
                if (studentDetail.imageUrl.startsWith(firebaseStorageImagePrefix).not()){
                    val studentImagePath = "$PROFILE_IMAGE_PATH/$studentId"
                    val studentImageRef = mStorage.child(studentImagePath)
                    studentImageRef.putFile(Uri.parse(studentDetail.imageUrl)).await()
                    studentDetail.imageUrl = studentImageRef.downloadUrl.await().toString()
                    student.details = studentDetail
                }

                val userProfileBuilder = UserProfileChangeRequest.Builder()
                val userProfile = userProfileBuilder
                    .setDisplayName(studentDetail.username)
                    .setPhotoUri(Uri.parse(studentDetail.imageUrl))
                    .build()

                val currentUser = mAuth.currentUser!!
                currentUser.updateProfile(userProfile).await()
                val currentUserEmail = currentUser.email!!
                if ((currentUserEmail != student.details!!.email)){
                    currentUser.updateEmail(student.details!!.email).await()
                }
                val editStudentRef = mFirestore.collection(COLLECTION_PATH_STUDENT).document(studentId)
                editStudentRef.set(student).await()
                _updateState.postValue(Resource.Success("Student update success."))
            } catch (error : Exception) {
                val errorMessage = error.message!!
                _updateState.postValue(Resource.Error(errorMessage))
            }
        }
    }

    private fun loadDummyStudent() {
        val dummyStudent = Student(
            uid = "user_001",
            details = Details(
                username = "ahasan_dev",
                email = "ahasan@example.com",
                imageUrl = "https://dummyimage.com/200x200", // optional
                sapId = "1234567890",
                mobile = "01700000000",
                dob = "2000-01-01",
                gender = "Male"
            ),
            address = Address(
                address = "123, Test Street",
                city = "Dhaka",
                state = "Dhaka",
                zipCode = "1000"
            ),
            academic = Academic(
                sem1 = "3.5",
                sem2 = "3.7",
                sem3 = "3.9",
                sem4 = "4.0",
                avgScore = "3.78",
                resumeUrl = "https://example.com/resume.pdf"
            )
        )

        _student.value = Resource.Success(dummyStudent)
    }




    override fun onCleared() {
        tpoListener?.remove()
        super.onCleared()
    }
}