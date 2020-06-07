package com.glaucustask.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glaucustask.Data;
import com.glaucustask.MainActivity;
import com.glaucustask.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {

    private ArrayList<Data> listItem;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public Adapter(ArrayList<Data> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mail_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        int count = position + 1;
        final Data currentItem = listItem.get(position);
        final String eMail = currentItem.getTableEmailEmailAddress();
        final int mailId = currentItem.getIdtableEmail();
        holder.mailId.setText(eMail);
        holder.serialNo.setText(String.valueOf(count));

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                View dialogView = View.inflate(context, R.layout.mail_delete_alert, null);
                alertDialog.setView(dialogView);

                Button btnYes = dialogView.findViewById(R.id.btn_yes_delete);
                Button btnNo = dialogView.findViewById(R.id.btn_no_delete);

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onDeleteClick(mailId, position);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                final View dialogView = View.inflate(context, R.layout.mail_update_alert, null);
                alertDialog.setView(dialogView);

                Button btnUpdate = dialogView.findViewById(R.id.btn_update);
                final EditText mailUpdateField = dialogView.findViewById(R.id.update_field);
                mailUpdateField.setText(eMail);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updateMail = mailUpdateField.getText().toString();
                        Data data = new Data(true, updateMail);
                        data.setIdtableEmail(currentItem.getIdtableEmail());
                        onItemClickListener.onEditClick(data);
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, mailId;
        ImageView imgEdit, imgDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            serialNo = itemView.findViewById(R.id.txt_serial_no);
            mailId = itemView.findViewById(R.id.email);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int id, int index);

        void onEditClick(Data data);
    }

}
