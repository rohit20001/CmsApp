package com.example.cmsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class home2 extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST=1;
    private Button mButtonChooseImage,mButtonUplaod;
    private TextView mTextHome;
    private EditText mTitle;
    private ImageView mImageView;
    private ProgressBar mprogressBar;
    private Uri mImageUri;
    private StorageReference mStorageref;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        mButtonChooseImage=findViewById(R.id.btn_image);
        mButtonUplaod=findViewById(R.id.btn_upload);
        mTextHome=findViewById(R.id.textHome);
        mTitle=findViewById(R.id.title);
        mImageView=findViewById(R.id.image_view);
        mprogressBar=findViewById(R.id.progressbar);
        mStorageref= FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openFileChooser();
            }
        });
        mButtonUplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadFile();
            }
        });
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data!=null && data.getData()!=null){
            mImageUri=data.getData();
            mImageView.setImageURI(mImageUri);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if(mImageUri !=null){
            StorageReference filereference=mStorageref.child(System.currentTimeMillis()
                    +"."+ getFileExtension(mImageUri));
            filereference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Handler handler=new Handler();
                           handler.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   mprogressBar.setProgress(0);
                               }
                           },50);
                            Toast.makeText(home2.this, "Upload successfull", Toast.LENGTH_SHORT).show();
                            upload Upload= new upload(mTitle.getText().toString().trim(),
                                    taskSnapshot.getStorage().getDownloadUrl().toString());
                            String uploadId=mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(Upload);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(home2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                    mprogressBar.setProgress((int) progress);
                }
            });
        }else{
            Toast.makeText(this,"No file Selected",Toast.LENGTH_SHORT).show();
        }
    }
}