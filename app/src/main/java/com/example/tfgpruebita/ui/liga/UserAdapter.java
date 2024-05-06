package com.example.tfgpruebita.ui.liga;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgpruebita.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<String> userList;

    public UserAdapter(List<String> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUserEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail); // El id debe coincidir con el TextView en item_user.xml
        }

        public void bind(String userEmail) {
            textViewUserEmail.setText(userEmail);
        }
    }
}
