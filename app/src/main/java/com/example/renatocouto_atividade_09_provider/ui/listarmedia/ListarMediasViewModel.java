package com.example.renatocouto_atividade_09_provider.ui.listarmedia;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividade_09_provider.model.dao.AlunoDao;
import com.example.renatocouto_atividade_09_provider.model.dao.MyDatabase;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListarMediasViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Aluno>> alunos;
    private final MutableLiveData<String> mensagemTexto;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Aluno> alunoEncontrado = new MutableLiveData<>();

    public ListarMediasViewModel() {
        mensagemTexto = new MutableLiveData<>();
        alunos = new MutableLiveData<>();
    }

    public void buscarAlunoPorNome(Context context, String nome ) {
        MyDatabase db = MyDatabase.getInstance(context);
        AlunoDao alunoDao = db.alunoDao();
        executor.execute(() -> {
            Aluno aluno = alunoDao.buscarPorNome(nome);
            alunoEncontrado.postValue(aluno);
        });
    }

    //aqui eu salvo sem dupliciade de nome
    public void salvarAluno(Context context, Aluno aluno) {
        MyDatabase db = MyDatabase.getInstance(context);
        AlunoDao alunoDao = db.alunoDao();
        executor.execute(() -> {
            Aluno alunoExistente = alunoDao.buscarPorNome(aluno.getNome());
            if (alunoExistente == null) {
                long status = alunoDao.inserir(aluno);
                if (status > 0) {
                    mensagemTexto.postValue("sucesso");
                } else {
                    mensagemTexto.postValue("erro");
                }
            }
        });
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

    public LiveData<String> getSalvo() {

        return mensagemTexto;
    }

    public LiveData<Aluno> getAlunoEncontrado() {

        return alunoEncontrado;
    }
}