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
                android.R.layout.simple_list_item_1, App.getFirebaseRef()) {
            @Override
            protected void populateView(View view, ChatMessage chatMessage, int i) {
                ((TextView) view.findViewById(android.R.id.text1)).setText(chatMessage.getMessage());
            }

        };

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


    }
}
