package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;

import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Tools;

import static android.R.attr.bitmap;

public class PhotoActivity extends Activity implements View.OnClickListener{

    @ViewInject(R.id.btPicture)
    private Button btPicture;
    @ViewInject(R.id.btPhoto)
    private Button btPhoto;


    private Bitmap bitmap,bitmap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);
        ViewUtils.inject(this);
        Init();
    }

    private void  Init(){
        btPicture.setOnClickListener(this);
        btPhoto.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btPicture:
                getImage();
                break;
            case R.id.btPhoto:
                getPhoto();
                break;
            default:
                break;
        }
    }

    public void getImage() {
        Intent intent = new Intent();
		/* Open the Pictures screen and set the Type to image */
        intent.setType("image/*");
		/* Use Intent.ACTION GET CONTENT this Action */
		/*
		 * There is a "ACTION_GET_CONTENT" string constant in Activity Action,
		 * This constant allows the user to select a specific type of data and returns the URI of that data.
		 */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* Return to this screen after getting the photo*/
        startActivityForResult(intent, 1);
    }

    public void getPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"note.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null&&requestCode==1) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { 
                return;
            }
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                bitmap1= Tools.getThumbnails(PhotoActivity.this,bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (resultCode == RESULT_OK&&requestCode==2) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { 
                return;
            }
            bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/note.jpg");
            bitmap1= Tools.getThumbnails(PhotoActivity.this,bitmap);
        }

        if(bitmap1!=null&&resultCode==RESULT_OK){
            PersonActivity.headimage=bitmap1;
            setResult(2,data);
        }
        finish();
    }


    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
