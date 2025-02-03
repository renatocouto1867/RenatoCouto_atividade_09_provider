package com.example.renatocouto_atividade_09_provider.ui.cadastrar;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividade_09_provider.model.dao.AlunoDao;
import com.example.renatocouto_atividade_09_provider.model.dao.MyDatabase;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastrarViewModel extends ViewModel {

    private final MutableLiveData<String> mensagemText;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public CadastrarViewModel() {
        mensagemText = new MutableLiveData<>();

    }

    public void salvarAluno(Aluno aluno, Context context) {
        MyDatabase db = MyDatabase.getInstance(context);
        AlunoDao alunoDao = db.alunoDao();
        executor.execute(() -> {
            long status = alunoDao.inserir(aluno);
            if (status > 0) {
                mensagemText.postValue("sucesso");
            } else {
                mensagemText.postValue("erro");
            }
        });
    }

    public void atualizarAluno(Aluno aluno, Context context) {
        MyDatabase db = MyDatabase.getInstance(context);
        AlunoDao alunoDao = db.alunoDao();
        executor.execute(() -> {
            long status = alunoDao.atualizar(aluno);
            if (status > 0) {
                mensagemText.postValue("sucesso");
            } else {
                mensagemText.postValue("erro");
            }
        });
    }


    public LiveData<String> getMensagem() {
        return mensagemText;
    }
}