package com.pixplay.planer.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.pixplay.planer.data.models.firebase.UserModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Flowable.create
import io.reactivex.FlowableEmitter
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

}