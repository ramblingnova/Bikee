package com.example.tacademy.bikee.common.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;

public class ChattingRoomListFragment extends Fragment {

    ListView lv;
    ChattingRoomAdapter adapter;

    public ChattingRoomListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chatting_room_list, container, false);
        lv = (ListView)v.findViewById(R.id.view_chatting_room_item_list_view);
        adapter = new ChattingRoomAdapter();
        lv.setAdapter(adapter);
        initData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChattingRoomItem item = (ChattingRoomItem)lv.getItemAtPosition(position);
                Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ChattingRoomActivity.class);
                // TODO
                getActivity().startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, "" + i);
        }
    }
}
