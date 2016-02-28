package com.benjaminearley.hackproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.benjaminearley.hackproject.models.ChatMessage;
import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.benjaminearley.hackproject.util.Utils;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView listView;
    private FirebaseListAdapter<ChatMessage> mListAdapter;
    private EditText textEdit;
    private ImageButton sendButton;

    private ChatMessage chatMessage;

    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String email;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        textEdit = (EditText) view.findViewById(R.id.text_edit);
        sendButton = (ImageButton) view.findViewById(R.id.send_button);

        listView.setDivider(null);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final String name = SharedPreferencesUtil.getLocation(getActivity(), "entered");
        if (name.equals("Hack Chat")) {

        } else {
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = textEdit.getText().toString();
                    if (!text.isEmpty()) {
                        chatMessage.setMessage(text);
                        App.getMessagesRef(name).push().setValue(chatMessage);
                        textEdit.setText("");
                        chatMessage.setMessage("");
                    }
                }
            });

            mListAdapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,
                    R.layout.chat_message, App.getMessagesRef(name)) {
                @Override
                protected void populateView(final View view, final ChatMessage chatMessage, int i) {

                    if (chatMessage.getUserUuid().equals(App.getAuthUuid())) {

                        view.findViewById(R.id.chat_right).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.chat_left).setVisibility(View.GONE);

                        ImageView imageView = (ImageView) view.findViewById(R.id.profile_icon_right);

                        Picasso.with(getActivity()).load("http://www.gravatar.com/avatar/" + Utils.md5(chatMessage.getEmail()) + "?&d=identicon").into(imageView);

                        ((TextView) view.findViewById(R.id.text1_right)).setText(chatMessage.getMessage());

                        view.findViewById(R.id.profile_icon_right).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                intent.putExtra("email", chatMessage.getEmail());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            getActivity(), v, v.getTransitionName()).toBundle());
                                } else {
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {

                        view.findViewById(R.id.chat_left).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.chat_right).setVisibility(View.GONE);

                        ((TextView) view.findViewById(R.id.text1_left)).setText(chatMessage.getMessage());

                        ImageView imageView = (ImageView) view.findViewById(R.id.profile_icon_left);

                        Picasso.with(getActivity()).load("http://www.gravatar.com/avatar/" + Utils.md5(chatMessage.getEmail()) + "?&d=identicon").into(imageView);

                        view.findViewById(R.id.profile_icon_left).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getActivity(), ListProfileActivity.class);
                                intent.putExtra("name", chatMessage.getFirstName() + " " + chatMessage.getLastName());
                                intent.putExtra("age", chatMessage.getAge());
                                intent.putExtra("gender", chatMessage.getGender());
                                intent.putExtra("email", chatMessage.getEmail());

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            getActivity(), v, v.getTransitionName()).toBundle());
                                } else {
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                }

            };

            listView.setAdapter(mListAdapter);
        }

        firstName = SharedPreferencesUtil.getFirstName(getActivity());
        lastName = SharedPreferencesUtil.getLastName(getActivity());
        age = SharedPreferencesUtil.getAge(getActivity());
        gender = SharedPreferencesUtil.getGender(getActivity());
        email = SharedPreferencesUtil.getUserEmail(getActivity());

        chatMessage = new ChatMessage("", firstName, lastName, age, gender, "", email);
        chatMessage.setUserUuid(App.getFirebaseRef().getAuth().getUid());

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void onUpdate(SharedPreferences sharedPreferences, String key) {
        if (key.equals("entered")) {
            final String name = sharedPreferences.getString("entered", "Hack Chat");
            if (name.equals("Hack Chat")) {

            } else {
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = textEdit.getText().toString();
                        if (!text.isEmpty()) {
                            chatMessage.setMessage(text);
                            App.getMessagesRef(name).push().setValue(chatMessage);
                            textEdit.setText("");
                            chatMessage.setMessage("");
                        }
                    }
                });

                mListAdapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,
                        R.layout.chat_message, App.getMessagesRef(name)) {
                    @Override
                    protected void populateView(final View view, final ChatMessage chatMessage, int i) {

                        if (chatMessage.getUserUuid().equals(App.getAuthUuid())) {

                            view.findViewById(R.id.chat_right).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.chat_left).setVisibility(View.GONE);

                            ImageView imageView = (ImageView) view.findViewById(R.id.profile_icon_right);

                            Picasso.with(getActivity()).load("http://www.gravatar.com/avatar/" + Utils.md5(chatMessage.getEmail()) + "?&d=identicon").into(imageView);

                            ((TextView) view.findViewById(R.id.text1_right)).setText(chatMessage.getMessage());

                            view.findViewById(R.id.profile_icon_right).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                    intent.putExtra("email", chatMessage.getEmail());
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                                                getActivity(), v, v.getTransitionName()).toBundle());
                                    } else {
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {

                            view.findViewById(R.id.chat_left).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.chat_right).setVisibility(View.GONE);

                            ((TextView) view.findViewById(R.id.text1_left)).setText(chatMessage.getMessage());

                            ImageView imageView = (ImageView) view.findViewById(R.id.profile_icon_left);

                            Picasso.with(getActivity()).load("http://www.gravatar.com/avatar/" + Utils.md5(chatMessage.getEmail()) + "?&d=identicon").into(imageView);

                            view.findViewById(R.id.profile_icon_left).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(), ListProfileActivity.class);
                                    intent.putExtra("name", chatMessage.getFirstName() + " " + chatMessage.getLastName());
                                    intent.putExtra("age", chatMessage.getAge());
                                    intent.putExtra("gender", chatMessage.getGender());
                                    intent.putExtra("email", chatMessage.getEmail());

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                                                getActivity(), v, v.getTransitionName()).toBundle());
                                    } else {
                                        startActivity(intent);
                                    }
                                }
                            });
                        }

                    }

                };

                listView.setAdapter(mListAdapter);
            }
        }
    }
}
