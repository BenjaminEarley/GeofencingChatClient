package com.benjaminearley.hackproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benjaminearley.hackproject.util.Utils;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListProfileActivityFragment extends Fragment {

    private String name;
    private String age;
    private String gender;

    public ListProfileActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();

        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_profile, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setSharedElementEnterTransition(TransitionInflater.from(getActivity())
                    .inflateTransition(R.transition.curve));
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.profile_image);

        Picasso.with(getActivity()).load("http://www.gravatar.com/avatar/" + Utils.md5(getActivity().getIntent().getStringExtra("email")) + "?d=identicon&size=1024").into(imageView);

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView ageTextView = (TextView) view.findViewById(R.id.age);
        TextView genderTextView = (TextView) view.findViewById(R.id.gender);
        nameTextView.setText(name);
        if (age != null && !age.isEmpty()) {
            ageTextView.setText("Age: " + age);
        }
        if (gender != null && !gender.isEmpty()) {
            genderTextView.setText("Gender: " + gender);
        }

        return view;
    }
}
