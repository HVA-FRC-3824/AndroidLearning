package example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private final static String TAG = "ChatActivity";

    private String username;
    private String chatroom;

    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        chatroom = intent.getStringExtra(MainActivity.CHATROOM_NAME);
        username = intent.getStringExtra(MainActivity.USERNAME);
        if(intent.getBooleanExtra(MainActivity.INCOGNITO, false))
        {
            username = "Anonymous";
        }

        messages = new ArrayList<>();

        ListView messagesListView = (ListView)findViewById(R.id.messagesListView);

        MessageListAdapter messageListAdapter = new MessageListAdapter(this, messages);

        MainActivity.ref.child("chatrooms").child(chatroom).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded" + dataSnapshot.getKey());
                messages.add(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved" + dataSnapshot.getKey());
                messages.remove(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messagesListView.setAdapter(messageListAdapter);
    }

    public void sendMessage(View view)
    {
        EditText editText = (EditText)findViewById(R.id.messagesEditText);
        String messageString = editText.getText().toString();
        if(!messageString.equals(""))
        {
            Message message = new Message(username, messageString);
            MainActivity.ref.child("chatrooms").child(chatroom).child(String.valueOf(message.timestamp)).setValue(message);
        }
        editText.setText("");
    }

}
