package dev.tci.registroactividades;

import android.view.View;

public interface RecyclerViewClick {
    void onClick(View v, int position);
    void onLongClick(View v, int position);
}
