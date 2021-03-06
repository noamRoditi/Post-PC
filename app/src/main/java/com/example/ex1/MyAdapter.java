package com.example.ex1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MessageViewHolder>{
    private FirebaseFirestore db;
    private List<Message> messageList;
    private Context context;

    public MyAdapter(List<Message> bookList, FirebaseFirestore db, Context context) {
        this.messageList = bookList;
        this.db = db;
        this.context = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_message, parent, false);

        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.message.setText(messageList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView message;

        public MessageViewHolder(View view) {
            super(view);
            message = (TextView) view.findViewById(R.id.message);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    Message message = messageList.get(position);
                    Intent intent = new Intent(context, MessageDetailsActivity.class);
                    intent.putExtra("message",message.getMessage());
                    intent.putExtra("id",message.getId());
                    intent.putExtra("timeStamp",message.getTimeStamp());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                }
            });
        }
    }
}
