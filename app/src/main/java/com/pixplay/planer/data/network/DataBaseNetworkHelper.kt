package com.pixplay.planer.data.network

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.rpc.Code
import com.pixplay.planer.data.models.firebase.UserModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Flowable.create
import io.reactivex.FlowableEmitter
import westroom.checkbook2.data.models.adapter.ModelTask
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


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
                    firebaseFirestore.collection("users").add(UserModel(it.user?.uid!!, email))
                        .addOnSuccessListener {
                            subscribe.onNext(CODE.SUCCESS)
                        }
                        .addOnFailureListener {
                            subscribe.onNext(CODE.FAILURE)
                            subscribe.onError(it)
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
                .addOnSuccessListener {
                    subscribe.onNext(CODE.SUCCESS)
                }
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

    override fun addNewTask(modelTask: ModelTask): Flowable<CODE> {
        return create({ subscribe: FlowableEmitter<CODE> ->
           firebaseAuth.currentUser?.let {
               firebaseFirestore.collection("users").document(it.uid).collection("tasks").add(modelTask)
                   .addOnSuccessListener { subscribe.onNext(CODE.SUCCESS) }
                   .addOnFailureListener {
                       subscribe.onNext(CODE.FAILURE)
                       subscribe.onError(it)
                   }
           }
        }, BackpressureStrategy.BUFFER)
    }

}