package note.neusoft.com.note.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.PrefUtils;

/**
 * author：xxx
 * Creation date： by 2016/12/26 on 11:43.
 * description：
 */

public class BgpicAdapter extends RecyclerView.Adapter<BgpicAdapter.MyViewHolder> {

    private List<Integer> imageid;
    private Context context;
    private View view;

    private int index=0;

    public BgpicAdapter(Context context, List<Integer> imageid,View view){
        this.context=context;
        this.imageid=imageid;
        this.view=view;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.bg_pic_grid_item,parent,false));
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(index==position){
            holder.gridview_item_checked_iv.setVisibility(View.VISIBLE);
        }else{
            holder.gridview_item_checked_iv.setVisibility(View.INVISIBLE);
        }
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),imageid.get(position));
        Drawable drawable=new BitmapDrawable(bitmap);
        holder.gridview_item_iv.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return imageid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView gridview_item_iv;
        private ImageView gridview_item_checked_iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            gridview_item_iv= (ImageView)itemView.findViewById(R.id.gridview_item_iv);
            gridview_item_checked_iv=(ImageView)itemView.findViewById(R.id.gridview_item_checked_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int postion=this.getLayoutPosition();
            index=postion;
            view.setBackgroundResource(imageid.get(postion));
            PrefUtils.SaveBg(context,imageid.get(postion));

            notifyDataSetChanged();
        }
    }

}
