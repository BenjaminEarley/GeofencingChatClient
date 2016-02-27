package com.benjaminearley.hackproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView listView;
    private FirebaseListAdapter<ChatMessage> mListAdapter;
    private EditText textEdit;
    private Button sendButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        textEdit = (EditText) view.findViewById(R.id.text_edit);
        sendButton = (Button) view.findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textEdit.getText().toString();
                ChatMessage message = new ChatMessage(text);
                App.getFirebaseRef().push().setValue(message);
                textEdit.setText("");
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
}
