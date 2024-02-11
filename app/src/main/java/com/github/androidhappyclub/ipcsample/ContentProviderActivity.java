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

package com.github.androidhappyclub.ipcsample;

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/2/10 19:01
// Description: 用来演示内容提供者权限
// Documentation: https://www.yuque.com/mashangxiayu/gne1e3/lyp5f7rkie8rc0i3

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.github.androidhappyclub.ipcsample.databinding.ActivityContentProviderBinding;
import com.github.androidhappyclub.ipcsample.model.Student;

/**
 * ContentProviderActivity.
 *
 * @see <a href="https://www.yuque.com/mashangxiayu/gne1e3/lyp5f7rkie8rc0i3?singleDoc# 《进程间数据共享》">进程间数据共享</a>
 */
public class ContentProviderActivity extends LifecycleActivity {

    private static final String PACKAGE = "com.github.androidhappyclub.intentsample";
    private static final String TAG = "ContentProviderActivity";
    private ActivityContentProviderBinding mBinding;
    private final ActivityResultLauncher<Intent> jumpIntentSample =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (Activity.RESULT_OK == result.getResultCode()) {
                    Log.d(TAG,"已经返回");
                }
            });

    public ContentProviderActivity() {
        super(R.layout.activity_content_provider);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityContentProviderBinding.bind(
                ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)
        );

        mBinding.jumpIntent.setOnClickListener(v -> {
            ComponentName name = new ComponentName(
                    PACKAGE,
                    PACKAGE + ".ComplexDataActivity"
            );
            Intent intent = new Intent();
            intent.setComponent(name);
            Uri uri = Student.CONTENT_URI;
            intent.setDataAndType(uri, getContentResolver().getType(uri));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            jumpIntentSample.launch(intent);
        });
//        test();
//        int count = writeStudent();
//        Log.d(TAG, "修改的行数" + count);
//        readStudent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    /**
     * 从内容提供者内读取数据。
     */
    private void readStudent() {
        Uri uri = ContentUris.withAppendedId(Student.CONTENT_URI, 1);
        Cursor cursor = getContentResolver()
                .query(uri, ContentObserverActivity.COLUMNS, null, null, null);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(Student.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_NAME));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(Student.COLUMN_AGE));
            String sex = cursor.getString(cursor.getColumnIndexOrThrow(Student.COLUMN_SEX));
            Student student = new Student(id, name, age, sex);
            Log.d(TAG, String.valueOf(student));
        }
        cursor.close();
    }

    /**
     * 更新 id 为 1 的学生的性别。
     */
    private int writeStudent() {
        Uri uri = ContentUris.withAppendedId(Student.CONTENT_URI, 1);
        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_SEX, "女");
        return getContentResolver()
                .update(uri, values, null, null);
    }

    /**
     * 测试路径读取权限优先级。
     *
     * @see <a href="https://www.yuque.com/mashangxiayu/gne1e3/lyp5f7rkie8rc0i3#LrEjc">优先级说明</a>
     */
    private void test() {
        Uri uri = Uri.withAppendedPath(Student.AUTHORITY_URI, "test");
        int count = getContentResolver().update(
                uri, new ContentValues(), null, null);
        Log.d(TAG, "test 修改了" + count + "行数据");
    }
}
