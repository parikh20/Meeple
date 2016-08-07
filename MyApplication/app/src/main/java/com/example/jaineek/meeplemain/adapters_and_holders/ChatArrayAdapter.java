package com.example.jaineek.meeplemain.adapters_and_holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Chat;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.appcompat.R.id.add;

/**
 * Created by Jaineek on 8/6/2016.
 */

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView chatText;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;

    @Override
    public void add(Object object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public Object getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chat_sent, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        Object chatMessageObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(chatMessageObj.toString());
        chatText.setBackgroundResource(R.drawable.patch);
        singleMessageContainer.setGravity(Gravity.RIGHT);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
