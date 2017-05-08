/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.camera2basic;

import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class CameraActivity extends Activity {

    private static final String TAG = CameraActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        CameraManager cameraManager = (CameraManager)getSystemService( CAMERA_SERVICE );
        try
        {
            String cameraList[] = cameraManager.getCameraIdList();
            for ( String cameraId : cameraList )
            {
                Log.e( TAG, "Camera id " + cameraId + " has device level:" );
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics( cameraId );
                Integer deviceLevel = cameraCharacteristics.get( CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL );
                if ( null != deviceLevel )
                {
                    switch ( deviceLevel )
                    {
                    case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3:
                        Log.e( TAG, "INFO_SUPPORTED_HARDWARE_LEVEL_3" );
                        break;
                    case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL:
                        Log.e( TAG, "INFO_SUPPORTED_HARDWARE_LEVEL_FULL" );
                        break;
                    case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED:
                        Log.e( TAG, "INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED" );
                        break;
                    case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY:
                        Log.e( TAG, "INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY" );
                        break;
                    }
                }
                else
                {
                    Log.e( TAG, "null" );
                }

                Log.e( TAG, "Camera id " + cameraId + " has the following request keys available:" );
                List<CaptureRequest.Key<?>> captureRequestKeys = cameraCharacteristics.getAvailableCaptureRequestKeys();
                for ( CaptureRequest.Key<?> key : captureRequestKeys )
                {
                    Log.e( TAG, key.getName() );
                }

                Log.e( TAG, "Camera id " + cameraId + " has the following capabilities available:" );
                int availableCapabilities[] = cameraCharacteristics.get( CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES );
                if ( null != availableCapabilities )
                {
                    for ( int availableCapability : availableCapabilities )
                    {
                        Log.e( TAG, String.valueOf( availableCapability ) );
                    }
                }
                else
                {
                    Log.e( TAG, "none." );
                }
            }
        }
        catch ( CameraAccessException e )
        {
            e.printStackTrace();
        }

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

}
