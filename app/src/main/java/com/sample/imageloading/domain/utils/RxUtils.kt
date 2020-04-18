package com.sample.imageloading.domain.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

object RxUtils {
    fun textChangeObservable(searchEditText: EditText): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, count: Int) {
                subject.onNext(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        return subject.debounce(300, TimeUnit.MILLISECONDS)
    }
}