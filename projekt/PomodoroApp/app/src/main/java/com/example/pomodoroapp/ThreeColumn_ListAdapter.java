package com.example.pomodoroapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<SingleSession> {
    private LayoutInflater inflater;
    private ArrayList<SingleSession> sessions;
    private int viewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<SingleSession> sessions) {
        super(context, textViewResourceId, sessions);
        this.sessions = sessions;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceId;
    }

    public View getView(int position, View converView, ViewGroup parents) {
        converView = inflater.inflate(viewResourceId, null);

        SingleSession singleSession = sessions.get(position);

        if (singleSession != null) {
            TextView Date = (TextView) converView.findViewById(R.id.sessionDateColumn);
            TextView sessionLength = (TextView) converView.findViewById(R.id.sessionLengthColumn);
            TextView breakLength = (TextView) converView.findViewById(R.id.breakLengthColumn);

            if (Date != null) {
                Date.setText(singleSession.getDate());
            }
            if (sessionLength != null) {
                sessionLength.setText(singleSession.getSessionLength());
            }
            if (breakLength != null) {
                breakLength.setText(singleSession.getBreakLength());
            }
        }
        return converView;
    }
}
