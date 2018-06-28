package com.adulgr.audio.audiopop.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.adulgr.audio.audiopop.dao.ResultsDao;
import com.adulgr.audio.audiopop.dao.SetupDao;
import com.adulgr.audio.audiopop.dao.TestDao;
import com.adulgr.audio.audiopop.db.TestResults.Converters;
import com.adulgr.audio.audiopop.entities.Results;
import com.adulgr.audio.audiopop.entities.Setup;
import com.adulgr.audio.audiopop.entities.Test;
import java.util.Date;

@Database(entities = {Setup.class, Test.class, Results.class}, version = 1, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class TestResults extends RoomDatabase {

  private static final String DATABASE_NAME = "test_results_db";

  private static TestResults instance = null;

  public abstract SetupDao getSetupDao();

  public abstract TestDao getTestDao();

  public abstract ResultsDao getResultsDao();

  public static TestResults getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(
          context.getApplicationContext(),
          TestResults.class, DATABASE_NAME).addCallback(new Callback(context))
          .build();
    }
    return instance;
  }

  public static void forgetInstance(Context context) {
    instance = null;
  }

  private static class Callback extends RoomDatabase.Callback {

    private Context context;

    private Callback(Context context) {
      this.context = context;
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PrepopulateTask().execute(context); // Call a task to pre-populate database.
    }

  }

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
      TestResults db = getInstance(contexts[0]);
      Setup setup = new Setup();
      setup.setName("Nicholas");
      setup.setGear("Stock");
      long setupId = db.getSetupDao().insert(setup);
      Test test = new Test();
      test.setSetupId(setupId);
      test.setTestType("Stereo");
      test.setNotes("Blow it up and buy a new one");
      test.setTestResult(false);
      test.setTimestamp(new Date());
      db.getTestDao().insert(test);
//      db.getStudentDao().insert(Student.testData());
//      db.getAbsenceDao().insert(Absence.testData());
       forgetInstance(contexts[0]);
      return null;
    }


  }


  public static class Converters {

    @TypeConverter
    public static Date dateFromLong(Long time) {
      return (time != null) ? new Date(time) : null;
    }

    @TypeConverter
    public static Long longFromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }
  }
}
