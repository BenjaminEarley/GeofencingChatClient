package com.benjaminearley.hackproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.firebase.ui.FirebaseListAdapter;

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

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        textEdit = (EditText) view.findViewById(R.id.text_edit);
        sendButton = (ImageButton) view.findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textEdit.getText().toString();
                if (!text.isEmpty()) {
                    chatMessage.setMessage(text);
                    App.getFirebaseRef().push().setValue(chatMessage);
                    textEdit.setText("");
                    chatMessage.setMessage("");
                }
            }
        });

        mListAdapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,
                R.layout.chat_message, App.getFirebaseRef()) {
            @Override
            protected void populateView(View view, final ChatMessage chatMessage, int i) {

                if (chatMessage.getUserUuid().equals(App.getFirebaseRef().getAuth().getUid())) {

                    view.findViewById(R.id.chat_right).setVisibility(View.VISIBLE);

                    ((TextView) view.findViewById(R.id.text1_right)).setText(chatMessage.getMessage());

                    view.findViewById(R.id.profile_icon_right).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {

                    view.findViewById(R.id.chat_left).setVisibility(View.VISIBLE);

                    ((TextView) view.findViewById(R.id.text1_left)).setText(chatMessage.getMessage());

                    view.findViewById(R.id.profile_icon_left).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }

        };

        listView.setDivider(null);

        listView.setAdapter(mListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        firstName = SharedPreferencesUtil.getFirstName(getActivity());
        lastName = SharedPreferencesUtil.getLastName(getActivity());
        age = SharedPreferencesUtil.getAge(getActivity());
        gender = SharedPreferencesUtil.getGender(getActivity());

        chatMessage = new ChatMessage("", firstName, lastName, age, gender, "");
        chatMessage.setUserUuid(App.getFirebaseRef().getAuth().getUid());


    }
}
