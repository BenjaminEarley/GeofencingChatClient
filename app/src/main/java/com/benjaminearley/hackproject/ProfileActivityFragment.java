package com.benjaminearley.hackproject;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

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
    private EditText genderText;


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

        if ( mayCaptureFromCamera() ) {
            Camera mCamera;
            // Create an instance of Camera
            mCamera = getCameraInstance();

            // Create our Preview view and set it as the content of our activity.
            CameraPreview mPreview = new CameraPreview(getContext(), mCamera);
            FrameLayout preview = (FrameLayout) getActivity().findViewById(R.id.profile_image_button);
            if ( preview != null ) {
                preview.addView(mPreview);
            }
        }

        firstNameText = (EditText) view.findViewById(R.id.firstName);
        lastNameText = (EditText) view.findViewById(R.id.lastName);
        ageText = (EditText) view.findViewById(R.id.Age);
        genderText = (EditText) view.findViewById(R.id.Gender);

        firstNameText.setText(SharedPreferencesUtil.getFirstName(getActivity()));
        lastNameText.setText(SharedPreferencesUtil.getLastName(getActivity()));
        ageText.setText(SharedPreferencesUtil.getAge(getActivity()));
        genderText.setText(SharedPreferencesUtil.getGender(getActivity()));

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

        genderText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferencesUtil.setGender(getActivity(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
