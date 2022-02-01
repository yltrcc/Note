package note.neusoft.com.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import note.neusoft.com.note.R;
import note.neusoft.com.note.adapter.BgpicAdapter;

public class SkinActivity extends Activity {

    @ViewInject(R.id.img_back)
    private ImageButton img_back;
    @ViewInject(R.id.review)
    private RecyclerView review;


    private List<Integer> imageid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ViewUtils.inject(this);
        InitData();
        Init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
    }

    private void InitData(){
        imageid=new ArrayList<>();
        for(int i=1;i<=12;i++){
            if(i==1)
                imageid.add(R.drawable.bg1);
            if(i==2)
                imageid.add(R.drawable.bg2);
            if(i==3)
                imageid.add(R.drawable.bg3);
            if(i==4)
                imageid.add(R.drawable.bg4);
            if(i==5)
                imageid.add(R.drawable.bg5);
            if(i==6)
                imageid.add(R.drawable.bg6);
            if(i==7)
                imageid.add(R.drawable.bg7);
            if(i==8)
                imageid.add(R.drawable.bg8);
            if(i==9)
                imageid.add(R.drawable.bg9);
            if(i==10)
                imageid.add(R.drawable.bg10);
            /*if(i==11)
                imageid.add(R.drawable.bg11);*/
            /*if(i==12)
                imageid.add(R.drawable.bg12);*/
        }
    }

    private void Init(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        });
        BgpicAdapter adapter=new BgpicAdapter(this,imageid,review);
        review.setLayoutManager(new GridLayoutManager(this, 3));
        review.setAdapter(adapter);
        review.setItemAnimator(null);
        review.setBackgroundResource(imageid.get(1));
    }
}
