package com.wtm.anshime.ui.main.chat;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wtm.anshime.R;
import com.wtm.anshime.model.ChatMessage;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_ME = 1000;
    private static final int VIEW_TYPE_OTHER = 2000;
    private List<ChatMessage> chatMessages;
    private String userName;

    public ChatMessageAdapter(
            List<ChatMessage> chatMessages,
            String userName
    ){
        this.chatMessages = chatMessages;
        this.userName = userName;
    }

    public void updateChatMessages(List<ChatMessage> chatMessages){
        final ChatDiffUtilCallback diffUtilCallback = new ChatDiffUtilCallback(this.chatMessages, chatMessages);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffUtilCallback);

        this.chatMessages = chatMessages;
        this.notifyDataSetChanged();
        result.dispatchUpdatesTo(this);
    }

    private void configureMyChatViewHolder(final MyChatMessageViewHolder myChatMessageViewHolder,
                                           int position){
        ChatMessage msg = chatMessages.get(position);
        myChatMessageViewHolder.content.setText(msg.getContent());
        myChatMessageViewHolder.timeStamp.setText(msg.getTimeStamp());
        myChatMessageViewHolder.userName.setText(msg.getUserName());
    }

    private void configureOtherChatViewHolder(final OtherChatMessageViewHolder otherChatMessageViewHolder,
                                              int position){
        ChatMessage msg = chatMessages.get(position);
        otherChatMessageViewHolder.content.setText(msg.getContent());
        otherChatMessageViewHolder.timeStamp.setText(msg.getTimeStamp());
        otherChatMessageViewHolder.userName.setText(msg.getUserName());
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: 10/23/2020 사용자의 UID 와 비교하도록 변경하기
        if(TextUtils.equals(chatMessages.get(position).getUserName(),
                userName)){
            return VIEW_TYPE_ME;
        }else{
            return VIEW_TYPE_OTHER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VIEW_TYPE_ME:
                View view = inflater.inflate(R.layout.chat_message_view_holder, parent, false);
                viewHolder = new MyChatMessageViewHolder(view);
                break;
            case VIEW_TYPE_OTHER:
                View otherView = inflater.inflate(R.layout.other_chat_messages_view_holder, parent, false);
                viewHolder = new OtherChatMessageViewHolder(otherView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(TextUtils.equals(chatMessages.get(position).getUserName(),
                userName)){
            configureMyChatViewHolder((MyChatMessageViewHolder) holder, position);
        }else{
            configureOtherChatViewHolder((OtherChatMessageViewHolder) holder, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return chatMessages != null ? chatMessages.size() : 0;
    }


    private static class MyChatMessageViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView content;
        private TextView timeStamp;

        public MyChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.chat_sender_id);
            content = itemView.findViewById(R.id.chat_content);
            timeStamp = itemView.findViewById(R.id.chat_timestamp);
        }
    }

    private static class OtherChatMessageViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView content;
        private TextView timeStamp;

        public OtherChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.other_chat_sender_id);
            content = itemView.findViewById(R.id.other_chat_content);
            timeStamp = itemView.findViewById(R.id.other_chat_timestamp);
        }
    }

    private static final String TAG = "ChatMessageAdapter";
}

