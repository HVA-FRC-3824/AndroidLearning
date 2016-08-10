package example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    public final static String CHATROOM_NAME = "chatroomName";
    public final static String USERNAME = "username";
    public final static String INCOGNITO = "incognito";

    public static FirebaseDatabase database;
    public static DatabaseReference ref;
    public static DatabaseReference chatroomRef;

    private ArrayList<String> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rooms = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        chatroomRef = ref.child("chatrooms");
        chatroomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded" + dataSnapshot.getKey());
                rooms.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved" + dataSnapshot.getKey());
                rooms.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, rooms);
        AutoCompleteTextView chatroomField = (AutoCompleteTextView)findViewById(R.id.chatroomNameEditText);
        chatroomField.setAdapter(dropdownAdapter);
    }

    public void enterChat(View view)
    {
        Intent intent = new Intent(this, ChatActivity.class);

        EditText chatroomNameEditText = (EditText)findViewById(R.id.chatroomNameEditText);
        String chatroomName = chatroomNameEditText.getText().toString();

        if(chatroomName.equals(""))
        {
            return;
        }

        EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        String username = usernameEditText.getText().toString();

        Switch incognitoSwitch = (Switch)findViewById(R.id.incognitoSwitch);
        Boolean incognito = incognitoSwitch.isChecked();

        if(!incognito && username.equals(""))
        {
            return;
        }

        if(!rooms.contains(chatroomName))
        {
            Log.d(TAG, "Adding chatroom: " + chatroomName);
            chatroomRef.child(chatroomName).setValue("");
        }

        intent.putExtra(CHATROOM_NAME, chatroomName);
        intent.putExtra(USERNAME, username);
        intent.putExtra(INCOGNITO, incognito);

        startActivity(intent);
    }
}