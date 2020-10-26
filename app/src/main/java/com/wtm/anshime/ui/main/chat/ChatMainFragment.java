package com.wtm.anshime.ui.main.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wtm.anshime.R;
import com.wtm.anshime.model.ChatMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.wtm.anshime.utils.Constants.MESSAGES;

public class ChatMainFragment extends Fragment {

    private EditText inputChatMsg;
    private Button btnSendChat;
    private String chatMsg = "No content";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;

    private String siInfo = "unknown"; //무슨 시에 속해있는지
    private String guInfo = "unknown"; //무슨 구에 속해있는지
    private String dongInfo = "unknown"; //무슨 동에 속해있는지
    private String databasePath = "";

    private ChatMessageAdapter chatMessageAdapter;
    private RecyclerView chatList;
    private LinearLayoutManager linearLayoutManager;

    // TODO: 10/23/2020 move this object to view model later.
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();

    public ChatMainFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //본 화면에 진입하기 전에 사용자가 귀가 메시지를 작성했는지 확인 필요

        //현재 목적지가 같은 유저들이 몇 명 있는지 확인하려면 서버와 통신 필요

        //같은 목적지에 있는 사람들끼리 소통하기

        //메시지 작성하기 -> 전송버튼 클릭 -> Firebase 데이터베이스에 메시지 저장 (유저네임, 시간, 메시지 내용 보내야 함.)
        //메시지 불러오기 -> 리스트에 디스플레이

        initPlaceInfo("서울특별시", "용산구", "동자동");

        inputChatMsg = view.findViewById(R.id.chat_msg_input);
        btnSendChat = view.findViewById(R.id.btn_send_chat);
        chatList = view.findViewById(R.id.chat_list);


        inputChatMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null){
                    chatMsg = s.toString();
                }
            }
        });

        btnSendChat.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            // TODO: 10/26/2020 Get user info from database
            ChatMessage chatMessage = new ChatMessage("user" + ((int) (Math.random() * 100) + 1), chatMsg, date.toString());

            DatabaseReference reference = database.getReference(databasePath);
            reference.push().setValue(
                chatMessage
            );
            //들어가 있는 텍스트를 비워줍니다.
            inputChatMsg.setText("");
        });

        chatMessageAdapter = new ChatMessageAdapter(chatMessages);

        reference = database.getReference(databasePath);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d(TAG, "onDataChange: " + "reading database ... ");
                chatMessages.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    if(child.exists()) {
                        ChatMessage chatMessage = child.getValue(ChatMessage.class);
                        chatMessages.add(chatMessage);
                    }
                }
                chatMessageAdapter.updateChatMessages(chatMessages);
                chatList.scrollToPosition(chatMessages.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "failed to read values from database!", Toast.LENGTH_SHORT).show();
            }
        });

        chatList.setAdapter(chatMessageAdapter);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setStackFromEnd(true);
        chatList.setLayoutManager(linearLayoutManager);
    }

    private void initPlaceInfo(String si, String gu, String dong) {
        siInfo = si;
        guInfo = gu;
        dongInfo = dong;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(siInfo).append("/")
                .append(guInfo).append("/")
                .append(dongInfo).append("/")
                .append(MESSAGES);
        databasePath = stringBuilder.toString();
    }

    private static final String TAG = "ChatMainFragment";
}
