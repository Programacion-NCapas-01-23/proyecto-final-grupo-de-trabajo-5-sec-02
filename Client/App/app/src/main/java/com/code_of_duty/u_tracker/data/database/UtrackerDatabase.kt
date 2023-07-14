package com.code_of_duty.u_tracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.code_of_duty.u_tracker.data.database.dao.CycleDao
import com.code_of_duty.u_tracker.data.database.dao.GradeDao
import com.code_of_duty.u_tracker.data.database.dao.PersonalTermDao
import com.code_of_duty.u_tracker.data.database.dao.PersonalTermXSubjectDao
import com.code_of_duty.u_tracker.data.database.dao.PrerequisiteDao
import com.code_of_duty.u_tracker.data.database.dao.SubjectDao
import com.code_of_duty.u_tracker.data.database.dao.TokenDao
import com.code_of_duty.u_tracker.data.database.dao.UserDao
import com.code_of_duty.u_tracker.data.database.entities.CurrentTerm
import com.code_of_duty.u_tracker.data.database.entities.Cycle
import com.code_of_duty.u_tracker.data.database.entities.Grade
import com.code_of_duty.u_tracker.data.database.entities.MainTerm
import com.code_of_duty.u_tracker.data.database.entities.PersonalTerm
import com.code_of_duty.u_tracker.data.database.entities.PersonalTermSubjectCrossRef
import com.code_of_duty.u_tracker.data.database.entities.Prerequisite
import com.code_of_duty.u_tracker.data.database.entities.Subject
import com.code_of_duty.u_tracker.data.database.entities.User
import com.code_of_duty.u_tracker.data.database.entities.UserToken

@Database(
    entities = [
        User::class,
        UserToken::class,
        Cycle::class,
        Subject::class,
        Prerequisite::class,
        Grade::class,
        PersonalTerm::class,
        PersonalTermSubjectCrossRef::class,
        MainTerm::class,
        CurrentTerm::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UtrackerDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun userTokenDao(): TokenDao
    abstract fun cycleDao(): CycleDao
    abstract fun subjectDao(): SubjectDao
    abstract fun prerequisiteDao(): PrerequisiteDao
    abstract fun gradeDao(): GradeDao
    abstract fun PersonalTermDao(): PersonalTermDao
    abstract fun PersonalTermXSubjectDao(): PersonalTermXSubjectDao
    //abstract fun assesmentDao(): AssesmentDao
}
