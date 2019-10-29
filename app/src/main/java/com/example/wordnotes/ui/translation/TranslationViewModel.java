package com.example.wordnotes.ui.translation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TranslationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TranslationViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Translation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}