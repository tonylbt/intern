package com.android.tony.kotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }


//    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
//    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
//        @Override
//        public String apply(Integer input) {
//            return "Hello world from section: " + input;
//        }
//    });
//
//    public void setIndex(int index) {
//        mIndex.setValue(index);
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
}