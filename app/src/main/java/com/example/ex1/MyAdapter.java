package com.example.ex1;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    public MyAdapter(List<Message> bookList, FirebaseFirestore db) {
        this.messageList = bookList;
        this.db = db;
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
                    AlertDialog.Builder alertbuilder= new AlertDialog.Builder(v.getContext());
                    alertbuilder.setTitle("Confirmation");
                    alertbuilder.setMessage("Are you sure you want to delete this message?");
                    alertbuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int postion = getAdapterPosition();
                                    db.collection("messages")
                                            .document(messageList.get(postion).getId())
                                            .delete();
                                    messageList.remove(postion);
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyItemRangeChanged(getAdapterPosition(),messageList.size());
                                }
                            });
                    alertbuilder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog alertDialog = alertbuilder.create();
                    alertDialog.show();
                    return false;
                }
            });
        }
    }
}
