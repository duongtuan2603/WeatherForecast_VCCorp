package com.example.weatherforecastmvvm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherforecastmvvm.getapilocation.SavedLocation;

@Database(entities = {SavedLocation.class}, version = 3)
public abstract class SavedLocationRoomDatabase extends RoomDatabase {
    public abstract SavedLocationDao savedLocationDao();
    private static SavedLocationRoomDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized SavedLocationRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (SavedLocationRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SavedLocationRoomDatabase.class, "savedlocation_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//        }
//    };
}
