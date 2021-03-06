/*
 * Copyright (C) 2020 The exTHmUI Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.exthmui.game.qs;

import static android.view.WindowManager.ScreenshotSource.SCREENSHOT_GLOBAL_ACTIONS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.internal.util.ScreenshotHelper;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.exthmui.game.R;
import org.exthmui.game.misc.Constants;

public class ScreenCaptureTile extends TileBase {

    private static final String TAG = "ScreenCaptureTile";

    private static final Intent hideMenuIntent = new Intent(Constants.Broadcasts.BROADCAST_GAMING_MENU_CONTROL).putExtra("cmd", "hide");
    
    private Handler mHandler;
    private ScreenshotHelper mScreenshotHelper;

    public ScreenCaptureTile(Context context) {
        super(context, context.getString(R.string.qs_screen_capture), "", R.drawable.ic_qs_screenshot);
        qsIcon.setSelected(true);
        mHandler = new Handler(Looper.getMainLooper());
        mScreenshotHelper = new ScreenshotHelper(context);
    }

    @Override
    public void setConfig(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        LocalBroadcastManager.getInstance(mContext).sendBroadcastSync(hideMenuIntent);
        mHandler.postDelayed(() -> {
            try {
                mScreenshotHelper.takeScreenshot(WindowManager.TAKE_SCREENSHOT_FULLSCREEN, true, true,
                    SCREENSHOT_GLOBAL_ACTIONS, mHandler, null);
            } catch (Exception e) {
                Log.e(TAG, "Error while trying to take screenshot.", e);
            }
        }, 500);
    }
}
