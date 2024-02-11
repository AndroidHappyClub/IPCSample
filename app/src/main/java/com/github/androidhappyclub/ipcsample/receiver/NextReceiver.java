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

package com.github.androidhappyclub.ipcsample.receiver;

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/2/6

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NextReceiver extends BroadcastReceiver {

    /**
     * 接收到来自其他应用的权限广播。
     */
    public static final String ACTION =
            "com.github.androidhappyclub.broadcasesample.ORDER_BROADCAST";
    public static final String BROADCAST_PERMISSION =
            "com.github.androidhappyclub.broadcasesample.BROADCAST_RECEIVER";
    public static final String TAG = "NextReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent.getAction()) return;
        if (intent.getAction().equals(ACTION)) {
            Log.d(TAG, "接收到来自其他应用的权限广播");
        }
    }

}