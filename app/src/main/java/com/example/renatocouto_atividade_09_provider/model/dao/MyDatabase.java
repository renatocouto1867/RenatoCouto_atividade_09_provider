package com.example.renatocouto_atividade_09_provider.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

/**
 * orientação do site Android developer
 * caso o app seja executado em um único processo, siga o padrão singleton
 * ao instanciar um objeto AppDatabase. Cada instância RoomDatabase é bastante cara
 * do ponto de vista computacional e raramente é necessário ter acesso a
 * várias instâncias em um único processo.
 * https://developer.android.com/training/data-storage/room?hl=pt-br
 * */

@Database(entities = {Aluno.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MyDatabase.class,
                            "my_dados"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AlunoDao alunoDao();
}
