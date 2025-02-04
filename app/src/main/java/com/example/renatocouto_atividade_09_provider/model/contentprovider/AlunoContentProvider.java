package com.example.renatocouto_atividade_09_provider.model.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.renatocouto_atividade_09_provider.model.dao.MyDatabase;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

public class AlunoContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.renatocouto_atividade_09_provider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/alunos");

    private MyDatabase database;

    @Override
    public boolean onCreate() {

        database = MyDatabase.getInstance(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = database.alunoDao().buscarTodosCursor();
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Aluno aluno = new Aluno();
        aluno.setNome(values.getAsString("nome"));
        aluno.setNota1(values.getAsDouble("nota1"));
        aluno.setNota2(values.getAsDouble("nota2"));

        long id = database.alunoDao().inserir(aluno);
        if (id != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.withAppendedPath(CONTENT_URI, String.valueOf(id));
        }
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        if (values == null) {
            return 0;
        }

        try {

            Aluno aluno = new Aluno();
            aluno.setId(Long.parseLong(uri.getLastPathSegment())); // Pega o ID da URI
            aluno.setNome(values.getAsString("nome"));
            aluno.setNota1(values.getAsDouble("nota1"));
            aluno.setNota2(values.getAsDouble("nota2"));

            int linhasAfetadas = database.alunoDao().atualizar(aluno);

            if (linhasAfetadas > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return linhasAfetadas;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        try {
            String idStr = uri.getLastPathSegment();
            long id = Long.parseLong(idStr);

            int linhasAfetadas = database.alunoDao().deletarPorId(id);

            if (linhasAfetadas > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return linhasAfetadas;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".alunos";
    }
}
