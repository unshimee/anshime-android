package com.wtm.anshime.ui.main.chat;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.wtm.anshime.model.ChatMessage;

import java.util.List;

public class ChatDiffUtilCallback extends DiffUtil.Callback {

    private final List<ChatMessage> oldMessages;
    private final List<ChatMessage> newMessages;

    public ChatDiffUtilCallback(List<ChatMessage> oldMessages,
                                List<ChatMessage> newMessages) {
        this.oldMessages = oldMessages;
        this.newMessages = newMessages;
    }

    @Override
    public int getOldListSize() {
        return oldMessages.size();
    }

    @Override
    public int getNewListSize() {
        return newMessages.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessages.get(oldItemPosition).getTimeStamp() == newMessages.get(newItemPosition).getTimeStamp();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessages.get(oldItemPosition).getContent().equals(
                newMessages.get(newItemPosition).getContent()
        );
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
