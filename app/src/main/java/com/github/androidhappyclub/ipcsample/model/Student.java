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

package com.github.androidhappyclub.ipcsample.model;

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/2/9

import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

/**
 * 用于通过 ContentProvider 进行跨进程访问。
 */
public class Student implements BaseColumns {
    public static final String TABLE_NAME = "student";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_ID = _ID;
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.github.androidhappyclub.datasample.studentprovider";
    public static final Uri AUTHORITY_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);

    private final int id;
    private final String name;
    private final int age;
    private final String sex;

    public Student(int id,String name,int age,String sex){
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student(" +
                "id = " + getId() + "," +
                "name = " + getName() + "," +
                "age = " + getAge() + "," +
                "sex = " + getSex() + ")";
    }
}