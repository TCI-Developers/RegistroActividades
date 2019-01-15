package dev.tci.registroactividades.FragmentDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dev.tci.registroactividades.R;
import uk.co.senab.photoview.PhotoViewAttacher;

import static dev.tci.registroactividades.register.mCurrentPhotoPath;

public class ayudaFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogo = inflater.inflate(R.layout.menu_ayuda, null);

        ImageButton btn_close = dialogo.findViewById(R.id.btnCancel);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setCancelable(false);
        builder.setView(dialogo);
        return builder.create();
    }
}
