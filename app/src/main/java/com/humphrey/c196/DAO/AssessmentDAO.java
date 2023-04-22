package com.humphrey.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Term;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Assessment a);

    @Update
    void update(Assessment a);

    @Delete
    void delete(Assessment a);

    @Query("SELECT * FROM Assessments")
    List<Assessment> getAllAssessments();

}
