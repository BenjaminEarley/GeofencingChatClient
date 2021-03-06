package com.benjaminearley.hackproject;

import android.content.pm.PackageManager;
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
import android.widget.ImageView;

import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.benjaminearley.hackproject.util.Utils;
import com.squareup.picasso.Picasso;

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

    private ImageView imageView;


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

            }
        });


        firstNameText = (EditText) view.findViewById(R.id.firstName);
        lastNameText = (EditText) view.findViewById(R.id.lastName);
        ageText = (EditText) view.findViewById(R.id.Age);
        genderText = (EditText) view.findViewById(R.id.Gender);
        imageView = (ImageView) view.findViewById(R.id.profile_image);

        firstNameText.setText(SharedPreferencesUtil.getFirstName(getActivity()));
        lastNameText.setText(SharedPreferencesUtil.getLastName(getActivity()));
        ageText.setText(SharedPreferencesUtil.getAge(getActivity()));
        genderText.setText(SharedPreferencesUtil.getGender(getActivity()));

        Picasso.with(getActivity()).load("http://www.gravatar.com/avatar/" + Utils.md5(getActivity().getIntent().getStringExtra("email")) + "?d=identicon&size=1024").into(imageView);


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
}
