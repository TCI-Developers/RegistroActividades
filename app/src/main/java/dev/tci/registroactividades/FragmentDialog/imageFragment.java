package dev.tci.registroactividades.FragmentDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dev.tci.registroactividades.R;
import dev.tci.registroactividades.register;
import uk.co.senab.photoview.PhotoViewAttacher;

import static dev.tci.registroactividades.register.mCurrentPhotoPath;

public class imageFragment extends DialogFragment {
    public interface OnImageFragmentListener{
        void onImageListener();
    }

    OnImageFragmentListener listerner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogo = inflater.inflate(R.layout.image_fragment, null);
        PhotoViewAttacher photoViewAttacher;
        ImageView imagen = dialogo.findViewById(R.id.imagePhoto);
        ImageButton btn_close = dialogo.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Glide.with(dialogo.getContext())
                .load(mCurrentPhotoPath)
                .into(imagen);

        photoViewAttacher = new PhotoViewAttacher(imagen);
        builder.setCancelable(false);
        builder.setView(dialogo);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listerner = (OnImageFragmentListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "no se implemento la interfas");
        }
    }
}
