package com.leeeeo.mydict.apps;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.leeeeo.mydict.models.DaoMaster;
import com.leeeeo.mydict.models.EasyDictWords;
import com.leeeeo.mydict.models.EasyDictWordsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 16/5/16.
 * Email: Jacob.Deng@about-bob.com
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppEngine.getInstance().init(this);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "easy_dict.db", null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        AppEngine.getInstance().setDaoSession(new DaoMaster(db).newSession());
        new LoadAsyncTack().execute("basedict.txt", null, null);
        new LoadAsyncTack().execute("cet4.txt", null, null);
    }

    class LoadAsyncTack extends AsyncTask<String, Integer, Void> {


        @Override
        protected Void doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets().open(params[0]), "UTF-8"));

                // do reading, usually loop until end of file reading
                String mLine;
                List<EasyDictWords> easyDictWordsList = new ArrayList<>();
                while ((mLine = reader.readLine()) != null) {
                    try {
                        String words[] = mLine.split(":");

                        if (TextUtils.isEmpty(words[0]) || TextUtils.isEmpty(words[1])) {
                            continue;
                        }


                        EasyDictWords easyDictWords1 = new EasyDictWords();
                        easyDictWords1.setName_words(words[0]);
                        easyDictWords1.setExplains(words[1]);

                        if (params[0].contains("cet4")) {
                            easyDictWords1.setName_lib(AppEngine.dictLibNames[0]);
                        } else {
                            easyDictWords1.setName_lib(AppEngine.dictLibNames[1]);
                        }

                        //考研词库
                        EasyDictWords easyDictWords2 = new EasyDictWords();
                        easyDictWords2.setName_words(words[0]);
                        easyDictWords2.setExplains(words[1]);
                        easyDictWords2.setName_lib(AppEngine.dictLibNames[2]);

                        easyDictWordsList.add(easyDictWords2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.e("load", easyDictWordsList.size() + "start");
                EasyDictWordsManager.getInstance().create(easyDictWordsList);
                Log.e("load", easyDictWordsList.size() + "done");
            } catch (IOException e) {
                //log the exception
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }
            return null;
        }
    }
}
