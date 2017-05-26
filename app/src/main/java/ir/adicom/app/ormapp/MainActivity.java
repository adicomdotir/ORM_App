package ir.adicom.app.ormapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static android.R.attr.action;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Adicom";
    private static final int MAX_NUM_TO_CREATE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        RuntimeExceptionDao<Account, String> simpleDao = db.getSimpleDataDao();
        // query for all of the data objects in the database
        List<Account> list = simpleDao.queryForAll();
        // our string builder for building the content-view
        StringBuilder sb = new StringBuilder();
        sb.append("Found ").append(list.size()).append(" entries in DB in ").append(action).append("()\n");

        // if we already have items in the database
        int simpleC = 1;
        for (Account simple : list) {
            sb.append('#').append(simpleC).append(": ").append(simple).append('\n');
            simpleC++;
        }
        sb.append("------------------------------------------\n");
        sb.append("Deleted ids:");
        for (Account simple : list) {
            simpleDao.delete(simple);
            sb.append(' ').append(simple.getName());
            Log.i(LOG_TAG, "deleting simple(" + simple.getName() + ")");
            simpleC++;
        }
        sb.append('\n');
        sb.append("------------------------------------------\n");

        int createNum;
        do {
            createNum = new Random().nextInt(MAX_NUM_TO_CREATE) + 1;
        } while (createNum == list.size());
        sb.append("Creating ").append(createNum).append(" new entries:\n");
        for (int i = 0; i < createNum; i++) {
            // create a new simple object
            long millis = System.currentTimeMillis();
            Account simple = new Account("acc" + i, "123");
            // store it in the database
            simpleDao.create(simple);
            Log.i(LOG_TAG, "created simple(" + millis + ")");
            // output it
            sb.append('#').append(i + 1).append(": ");
            sb.append(simple).append('\n');
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // ignore
            }
            Log.e(LOG_TAG, sb.toString());
        }
    }
}
