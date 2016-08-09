package example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Andrew Messing
 * @version %I%
 */
public class MessageListAdapter extends BaseAdapter {

    private final String TAG = "MessageListAdapter";

    private Context context;
    private ArrayList<Message> messages;

    public  MessageListAdapter(Context context, ArrayList<Message> messages)
    {
        this.context = context;
        this.messages = messages;
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(messages.get(position).sender);
        text2.setText(messages.get(position).message);

        return twoLineListItem;
    }

    @Override
    public void notifyDataSetChanged()
    {
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message lhs, Message rhs) {
                return Long.compare(lhs.timestamp, rhs.timestamp);
            }
        });
        super.notifyDataSetChanged();
    }
}
