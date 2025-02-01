package com.example.renatocouto_atividade_09_provider.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

@Database(entities = {Aluno.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static volatile MyDatabase INSTANCE;

    public abstract AlunoDao alunoDao();

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
}
