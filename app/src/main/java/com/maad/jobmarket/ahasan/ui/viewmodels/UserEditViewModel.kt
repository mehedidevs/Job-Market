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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.maad.jobmarket.ahasan.domain.model.Academic
import com.maad.jobmarket.ahasan.domain.model.Address
import com.maad.jobmarket.ahasan.domain.model.Details
import com.maad.jobmarket.ahasan.domain.model.Job
import com.maad.jobmarket.ahasan.domain.model.Student
import com.maad.jobmarket.ahasan.domain.model.Tpo
import com.maad.jobmarket.ahasan.utils.Constants.Companion.COLLECTION_PATH_STUDENT
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
    private var tpoListener : ListenerRegistration? = null





    val _jobs = MutableLiveData<List<Job>>()
    val jobs: LiveData<List<Job>> = _jobs


    private val _tpoList: MutableLiveData<List<Tpo>> = MutableLiveData(emptyList())
    val tpoList: LiveData<List<Tpo>> = _tpoList

    private val _updateState: MutableLiveData<Resource<String>> = MutableLiveData()
    val updateState: LiveData<Resource<String>> = _updateState

    private val _resumeState: MutableLiveData<Resource<Triple<String, String, Uri>>> = MutableLiveData()
    val resumeState: LiveData<Resource<Triple<String, String, Uri>>> = _resumeState

    private val _deleteState: MutableLiveData<Resource<String>> = MutableLiveData()
    val deleteState: LiveData<Resource<String>> = _deleteState




    fun fetchTpo() {

        viewModelScope.launch {
            val dummyTpos = listOf(
                Tpo(
                    uid = "tpo001",
                    email = "tpo1@college.edu",
                    username = "Mr. Anik Rahman",
                    mobile = "01711111111",
                    dob = "1985-08-15",
                    gender = "Male",
                    imageUri = "https://dummyimage.com/200x200/000/fff&text=TPO+1",
                    stream = "CSE",
                    qualification = "MSc in CS",
                    experience = "10 years",
                    biography = "Passionate TPO who connects students with top companies."
                ),
                Tpo(
                    uid = "tpo002",
                    email = "tpo2@college.edu",
                    username = "Ms. Nusrat Jahan",
                    mobile = "01822222222",
                    dob = "1988-03-22",
                    gender = "Female",
                    imageUri = "https://dummyimage.com/200x200/111/eee&text=TPO+2",
                    stream = "EEE",
                    qualification = "PhD in EE",
                    experience = "8 years",
                    biography = "Helping engineers land their dream jobs!"
                )
            )

            _tpoList.postValue(dummyTpos)
        }
//        viewModelScope.launch(IO) {
//            tpoListener = mFirestore.collection(COLLECTION_PATH_TPO)
//                .addSnapshotListener { value, error ->
//                    if (error != null) {
//                        return@addSnapshotListener
//                    }
//                    val documents = value?.documents!!
//                    val tpoList = documents.map {
//                        it.toObject(Tpo::class.java)!!
//                    }
//                    _tpoList.postValue(tpoList)
//                }
//        }
    }


    fun fetchjobs() {
        viewModelScope.launch {
            val dummyJobs = listOf(
                Job(
                    authorUid = "user1",
                    imageUrl = "https://i.imgur.com/ZcLLrkY.png", // Replace with actual logo URLs if needed
                    role = "UI/UX Designer",
                    name = "Google Inc.",
                    city = "Mountain View, CA",
                    salary = "$12k/Mo",
                    workType = "Remote",
                    designation = "Senior",
                    description = "Design user-friendly interfaces for mobile and web apps.",
                    responsibility = "Wireframing, prototyping, testing.",
                    skillSet = listOf("Figma", "Adobe XD", "Sketch")
                ),
                Job(
                    authorUid = "user2",
                    imageUrl = "https://i.imgur.com/BoN9kdC.png",
                    role = "Android Developer",
                    name = "Meta",
                    city = "Menlo Park, CA",
                    salary = "$15k/Mo",
                    workType = "On-site",
                    designation = "Mid-Level",
                    description = "Build and maintain Android apps with cutting-edge features.",
                    responsibility = "Developing UI, integrating APIs, fixing bugs.",
                    skillSet = listOf("Kotlin", "MVVM", "Firebase", "Jetpack Compose")
                ),
                Job(
                    authorUid = "user3",
                    imageUrl = "https://i.imgur.com/0y8Ftya.png",
                    role = "Data Scientist",
                    name = "Netflix",
                    city = "Los Gatos, CA",
                    salary = "$18k/Mo",
                    workType = "Hybrid",
                    designation = "Lead",
                    description = "Analyze user behavior to improve recommendation systems.",
                    responsibility = "Data modeling, algorithm design, A/B testing.",
                    skillSet = listOf("Python", "TensorFlow", "SQL", "Pandas")
                ),
                Job(
                    authorUid = "user4",
                    imageUrl = "https://i.imgur.com/yhR7U1D.png",
                    role = "Frontend Engineer",
                    name = "Airbnb",
                    city = "San Francisco, CA",
                    salary = "$14k/Mo",
                    workType = "Remote",
                    designation = "Junior",
                    description = "Develop responsive and high-performing UI.",
                    responsibility = "React development, UI testing, collaboration with backend.",
                    skillSet = listOf("React", "TypeScript", "Redux", "Next.js")
                ),
                Job(
                    authorUid = "user5",
                    imageUrl = "https://i.imgur.com/FpX2MAv.png",
                    role = "Backend Developer",
                    name = "Spotify",
                    city = "Stockholm, Sweden",
                    salary = "$13k/Mo",
                    workType = "On-site",
                    designation = "Mid-Level",
                    description = "Create scalable backend APIs for streaming service.",
                    responsibility = "API development, database management, server optimization.",
                    skillSet = listOf("Node.js", "MongoDB", "Docker", "Kubernetes")
                )
            )
            _jobs.postValue(dummyJobs)
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




    override fun onCleared() {
        tpoListener?.remove()
        super.onCleared()
    }
}