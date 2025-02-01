package com.example.renatocouto_atividade_09_provider.ui.cadastrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastrarViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CadastrarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}