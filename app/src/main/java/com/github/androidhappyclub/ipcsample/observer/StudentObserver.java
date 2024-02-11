/*
 * MIT License
 *
 * Copyright (c) 2024 AndroidHappyClub
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.androidhappyclub.ipcsample.observer;

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/2/10 13:05
// Description: 用于跨进程通信监听学生的信息变化

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.androidhappyclub.ipcsample.ContentObserverActivity;
import com.github.androidhappyclub.ipcsample.model.Student;

public class StudentObserver extends ContentObserver {

    public static final int DATA_CHANGED = 0x01;
    private final Context mContext;
    private final Handler mHandler;

    public StudentObserver(@NonNull Context context, @NonNull Handler handler) {
        super(handler);
        mHandler = handler;
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        if (null == uri) {
            return;
        }
        Cursor cursor = mContext.getContentResolver()
                .query(uri, ContentObserverActivity.COLUMNS, null, null, null);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(Student.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_NAME));
            String sex = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_SEX));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(Student.COLUMN_AGE));
            Student student = new Student(id, name, age, sex);
            mHandler.obtainMessage(DATA_CHANGED, student).sendToTarget();
        }
        cursor.close();
    }
}
