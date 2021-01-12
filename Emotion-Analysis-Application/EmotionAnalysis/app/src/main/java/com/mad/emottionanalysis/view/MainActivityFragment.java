package com.mad.emottionanalysis.view;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.emottionanalysis.R;
import com.mad.emottionanalysis.activity.EmotionRecognitionActivity;
import com.mad.emottionanalysis.contracts.MainActivityContract;

import static android.app.Activity.RESULT_OK;

public class MainActivityFragment extends Fragment implements MainActivityContract.View {
    public static final int TAKE_PHOTO = 1;
    public static final int SELECTED_PHOTO = 2;
    TextView mAlbumTextView;
    TextView mCameraTextView;

    public MainActivityFragment() {
        // Requires empty public constructor
    }

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void setPresenter(@NonNull MainActivityContract.Presenter presenter) { }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == TAKE_PHOTO) {
            try {
                Intent intent = new Intent(getContext(), EmotionRecognitionActivity.class);
                Bitmap bitmap = data.getExtras().getParcelable("data");
                intent.putExtra("bitmap", bitmap);
                intent.putExtra("type", TAKE_PHOTO);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == SELECTED_PHOTO) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Intent intent = new Intent(getContext(), EmotionRecognitionActivity.class);
                intent.putExtra("path", picturePath);
                intent.putExtra("type", SELECTED_PHOTO);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);
        mAlbumTextView=(TextView)root.findViewById(R.id.textViewAlbum);
        mCameraTextView = (TextView) root.findViewById(R.id.textViewCamera);
        // this method used to call a system gallery to select a photo for analysis
        mAlbumTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECTED_PHOTO);
            }
        });

        // this method used to call a system camera to take a photo and put it in to an intent
        mCameraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        return root;
    }

}
