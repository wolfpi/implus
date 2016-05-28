package com.baidu.imc.impl.im.store;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by gerald on 4/12/16.
 */
public class UserSQLHelperTest extends InstrumentationTestCase {

    public void testDatabaseUpgrade() throws Exception {
        Context context = getInstrumentation().getTargetContext();
        String testDbName = "test_db_ver3";
        File testDbFile = context.getDatabasePath(testDbName);
        if (testDbFile.exists()) {
            testDbFile.delete();
        }
        testDbFile.getParentFile().mkdirs();
        // copy to file
        InputStream is = getInstrumentation().getContext().getAssets().open("databases/test_db_ver3.sqlite");
        ReadableByteChannel rc = Channels.newChannel(is);
        WritableByteChannel wc = Channels.newChannel(new FileOutputStream(testDbFile, false));
        ByteBuffer buffer = ByteBuffer.allocate(500 * 1024);
        while (rc.read(buffer) > 0) {
            buffer.flip();
            wc.write(buffer);
            buffer.clear();
        }
        wc.close();
        rc.close();

        UserSQLHelper sqlHelper = new UserSQLHelper(context, testDbName);
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        assertEquals("version shall be newest: " + UserSQLHelper.DATABASE_VERSION, UserSQLHelper.DATABASE_VERSION, db.getVersion());
        assertTrue("shall raise flag justRecreated", sqlHelper.isJustRecreated());
        Cursor c = db.query(IMMessageMetaData.TABLE_NAME, null, null, null, null, null, null);
        assertEquals("shall have no entry", 0, c.getCount());
        c.close();
    }
}
