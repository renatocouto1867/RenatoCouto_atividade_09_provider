package com.example.renatocouto_atividade_09_provider.ui.listaraluno;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividade_09_provider.model.dao.AlunoDao;
import com.example.renatocouto_atividade_09_provider.model.dao.MyDatabase;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListarAlunoViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Aluno>> alunos;
    private final MutableLiveData<String> mensagemTexto;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ListarAlunoViewModel() {
        mensagemTexto = new MutableLiveData<>();
        alunos = new MutableLiveData<>();
    }

    public void buscaAlunos(Context context) {
        MyDatabase db = MyDatabase.getInstance(context);
        AlunoDao alunoDao = db.alunoDao();
        executor.execute(() -> {
            alunos.postValue((ArrayList<Aluno>) alunoDao.buscarTodos());
        });
    }

    public void deletarAluno(Context context, Aluno aluno) {
        MyDatabase db = MyDatabase.getInstance(context);
        AlunoDao alunoDao = db.alunoDao();
        executor.execute(() -> {
            try {
                alunoDao.deletar(aluno);
                mensagemTexto.postValue("sucesso");
            } catch (RuntimeException e) {
                mensagemTexto.postValue("erro");
            }

        });
    }

    public LiveData<ArrayList<Aluno>> getAllAluno(Context context) {
        buscaAlunos(context);
        return alunos;
    }

    public LiveData<String> getMensagem() {
        return mensagemTexto;
    }
}