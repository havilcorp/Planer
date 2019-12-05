package com.pixplay.planer.data.network

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.pixplay.planer.data.models.firebase.ModelUser
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Flowable.create
import io.reactivex.FlowableEmitter
import com.pixplay.planer.data.models.adapter.ModelTask
import westroom.checkbook2.data.models.adapter.ModelTaskFromFB
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList


/**
 * Created by havil on 19.03.2018.
 */

@Singleton open class DataBaseNetworkHelper
@Inject constructor(
        private val firebaseFirestore: FirebaseFirestore,
        private val firebaseAuth : FirebaseAuth,
        private val phoneAuthProvider: PhoneAuthProvider,
        private val firebaseStorage: FirebaseStorage
) : IDataBaseNetworkHelper {

    override fun signUp(email: String, password: String): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.let { user ->
                        firebaseFirestore.collection("users").document(user.uid).set(ModelUser(user.uid, email))
                            .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                            .addOnFailureListener {
                                subscribe.onNext(CODE.FAILURE)
                                subscribe.onError(it)
                            }
                    }
                }
                .addOnFailureListener {
                    subscribe.onNext(CODE.FAILURE)
                    subscribe.onError(it)
                }
        }, BackpressureStrategy.BUFFER)
    }

    override fun signIn(email: String, password: String): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                .addOnFailureListener {
                    subscribe.onNext(CODE.FAILURE)
                    subscribe.onError(it)
                }
        }, BackpressureStrategy.BUFFER)
    }

    override fun isAuth() = firebaseAuth.currentUser != null

    override fun signOut(): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
            firebaseAuth.signOut()
            subscribe.onNext(CODE.SUCCESS)
        }, BackpressureStrategy.BUFFER)
    }

    /////

    override fun loadPhotoAndGetUrlName(bitmap: Bitmap): Flowable<String> {
        return create({ subscribe: FlowableEmitter<String> ->
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()
            val bitmapName = Date().time.toString()
            val storageRef = firebaseStorage.reference
            val mountainsRef = storageRef.child("images/$bitmapName.png")
            mountainsRef.putBytes(data)
                .addOnSuccessListener {
                    it.metadata?.reference?.downloadUrl
                        ?.addOnSuccessListener { subscribe.onNext(it.toString()) }
                        ?.addOnFailureListener { subscribe.onError(it) }
                }.addOnFailureListener { subscribe.onError(it) }
        }, BackpressureStrategy.BUFFER)
    }

    //

    override fun startTaskListener(): Flowable<ArrayList<ModelTaskFromFB>> {
        return create({ subscribe: FlowableEmitter<ArrayList<ModelTaskFromFB>> ->
            firebaseAuth.currentUser?.let { user ->
                firebaseFirestore.collection("users").document(user.uid).collection("tasks").addSnapshotListener { qs, e ->
                    val list = ArrayList<ModelTaskFromFB>()
                    qs!!.documentChanges.forEach {
                        list.add((ModelTaskFromFB(it.type, it.document.toObject(ModelTask::class.java))))
                    }
                    subscribe.onNext(list)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun addNewTask(modelTask: ModelTask): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
           firebaseAuth.currentUser?.let {
               val doc = firebaseFirestore.collection("users").document(it.uid).collection("tasks").document()
               modelTask.id = doc.id
               doc.set(modelTask)
                   .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                   .addOnFailureListener {
                       subscribe.onNext(CODE.FAILURE)
                       subscribe.onError(it)
                   }
           }
        }, BackpressureStrategy.BUFFER)
    }

    override fun modiferStatusTask(id: String, status: String): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
            firebaseAuth.currentUser?.let {
                firebaseFirestore.collection("users").document(it.uid)
                    .collection("tasks").document(id)
                    .update("status", status)
                    .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                    .addOnFailureListener {
                        subscribe.onNext(CODE.FAILURE)
                        subscribe.onError(it)
                    }
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun deleteTask(id: String): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
            firebaseAuth.currentUser?.let {
                firebaseFirestore.collection("users").document(it.uid)
                    .collection("tasks").document(id)
                    .delete()
                    .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                    .addOnFailureListener {
                        subscribe.onNext(CODE.FAILURE)
                        subscribe.onError(it)
                    }
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun getTask(id: String): Flowable<ModelTask> {
        return create({ subscribe: FlowableEmitter<ModelTask> ->
            firebaseAuth.currentUser?.let {
                firebaseFirestore.collection("users").document(it.uid)
                    .collection("tasks").document(id)
                    .get()
                    .addOnSuccessListener { subscribe.onNext(it.toObject(ModelTask::class.java)!!) }
                    .addOnFailureListener {
                        subscribe.onError(it)
                    }
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun clearAllTasks(): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
            firebaseAuth.currentUser?.let {
                val collectTasks = firebaseFirestore.collection("users").document(it.uid).collection("tasks")
                collectTasks.get().addOnSuccessListener {
                    val batch = firebaseFirestore.batch()
                    it.documents.forEach {
                        it?.let {
                            batch.delete(collectTasks.document(it.id))
                        }
                    }
                    batch.commit()
                        .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                        .addOnFailureListener {
                            subscribe.onNext(CODE.FAILURE)
                            subscribe.onError(it)
                        }
                }
            }
        }, BackpressureStrategy.BUFFER)
    }



}