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
// Date: 2024/2/9

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.androidhappyclub.ipcsample.databinding.ActivityContentObserverBinding;
import com.github.androidhappyclub.ipcsample.model.Student;
import com.github.androidhappyclub.ipcsample.observer.StudentObserver;

import java.lang.ref.WeakReference;

public class ContentObserverActivity extends LifecycleActivity {

    /**
     * 需要通过 ContentProvider 查询的字段。
     */
    public static final String[] COLUMNS = {
            Student.COLUMN_ID, Student.COLUMN_NAME, Student.COLUMN_SEX, Student.COLUMN_AGE
    };
    /**
     * 日志标签
     */
    private final String TAG = "ContentObserverActivity";
    public ActivityContentObserverBinding mBinding;
    private StudentObserver mStudentObserver;

    public ContentObserverActivity() {
        super(R.layout.activity_content_observer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityContentObserverBinding.bind(
                ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)
        );
        mStudentObserver = new StudentObserver(this, new StudentHandler(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(Student.CONTENT_URI, true, mStudentObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mStudentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    private static class StudentHandler extends Handler {

        private final WeakReference<ContentObserverActivity> mWr;

        public StudentHandler(ContentObserverActivity activity) {
            super(Looper.getMainLooper());
            mWr = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (StudentObserver.DATA_CHANGED == msg.what) {
                Student student = (Student) msg.obj;
                ContentObserverActivity activity = mWr.get();
                Log.d(activity.TAG, "获取到的学生对象: " + student);
                Log.d(activity.TAG, "当前是否在主线程" + (Looper.myLooper() == Looper.getMainLooper()));
                activity.mBinding.getContent.setText("获取到的学生信息：" + student);
            }
        }
    }
}