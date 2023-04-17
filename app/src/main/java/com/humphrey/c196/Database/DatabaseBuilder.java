package com.humphrey.c196.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.humphrey.c196.DAO.AssessmentDAO;
import com.humphrey.c196.DAO.CourseDAO;
import com.humphrey.c196.DAO.TermDAO;
/*import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;*/
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;

@Database(entities={Term.class, Assessment.class, Course.class}, version=6, exportSchema = false)


public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract AssessmentDAO assessmentDAO();

    public abstract CourseDAO courseDAO();
    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "C196Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
