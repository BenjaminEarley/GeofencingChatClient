package com.benjaminearley.hackproject;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.benjaminearley.hackproject.util.CameraPreview;

import static android.Manifest.permission.CAMERA;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {

    private static final int REQUEST_CAMERA = 0;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText ageText;
    private Spinner genderSpinner;


    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setSharedElementEnterTransition(TransitionInflater.from(getActivity())
                    .inflateTransition(R.transition.curve));
        }


        final FrameLayout profile_image_button = (FrameLayout) view.findViewById(R.id.profile_image_button);
        profile_image_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( mayCaptureFromCamera() ) {
                    Camera mCamera;
                    // Create an instance of Camera
                    mCamera = getCameraInstance();

                    // Create our Preview view and set it as the content of our activity.
                    CameraPreview mPreview = new CameraPreview(getContext(), getActivity(), mCamera);
                    FrameLayout preview = (FrameLayout) profile_image_button;
                    preview.addView(mPreview);

                }

                Camera.PictureCallback mPicture = new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {}
                };
            }
        });


        firstNameText = (EditText) view.findViewById(R.id.firstName);
        lastNameText = (EditText) view.findViewById(R.id.lastName);
        ageText = (EditText) view.findViewById(R.id.Age);
        genderSpinner = (Spinner) view.findViewById(R.id.Gender);


        String[] items = new String[]{"Unknown", "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        genderSpinner.setAdapter(adapter);

        firstNameText.setText(SharedPreferencesUtil.getFirstName(getActivity()));
        lastNameText.setText(SharedPreferencesUtil.getLastName(getActivity()));
        ageText.setText(SharedPreferencesUtil.getAge(getActivity()));
        String gender = SharedPreferencesUtil.getGender(getActivity());
        if ( gender == "Male" ) {
            genderSpinner.setSelection(1);
        } else if ( gender == "Female" ) {
            genderSpinner.setSelection(2);
        } else {
            genderSpinner.setSelection(0);
        }

        firstNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferencesUtil.setFirstName(getActivity(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferencesUtil.setLastName(getActivity(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferencesUtil.setAge(getActivity(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        genderSpinner.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ( keyCode == 0 ) {
                    SharedPreferencesUtil.setGender(getActivity(), "Unknown");
                } else if ( keyCode == 1 ) {
                    SharedPreferencesUtil.setGender(getActivity(), "Male");
                } else if ( keyCode == 2 ) {
                    SharedPreferencesUtil.setGender(getActivity(), "Female");
                }
                return false;
            }
        });

        return view;
    }

    private boolean mayCaptureFromCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(CAMERA)) {
            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
        } else {
            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
        }
        return false;
    }

    public static Camera getCameraInstance(){
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("CAM", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }
        return cam;
    }
}
