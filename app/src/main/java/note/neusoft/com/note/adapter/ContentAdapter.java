package note.neusoft.com.note.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.gesturelibray.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import note.neusoft.com.note.R;
import note.neusoft.com.note.activity.PatternCheckActivity;
import note.neusoft.com.note.activity.PatternCreateActivity;
import note.neusoft.com.note.db.NoteDatabase;
import note.neusoft.com.note.domain.NoteInfo;


/**
 * author：xxx
 * Creation date： by 2016/12/20 on 16:24.
 * description：
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NoteInfo> noteInfos;
    private String password;
    NoteDatabase db;

    public ContentAdapter(Context context, ArrayList<NoteInfo> noteinfos, String password){
        this.context=context;
        this.noteInfos=noteinfos;
        this.password = password;

        db = new NoteDatabase(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.content_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NoteInfo noteInfo=noteInfos.get(position);
        holder.tv_note_detail.setText(noteInfo.getContent());
        holder.tv_note_detail.setBackgroundColor(noteInfo.getColor());

        holder.note_detail_titlebar.setBackgroundColor(noteInfo.getTitleColor());
        holder.note_detail_tv_date.setText(noteInfo.getDate());
        int[] editcolor = new int[]{0xffe5fce8,
                0xffccf2fd,
                0xfff7f5f6,
                0xfffffdd7,
                0xffffddde,
        };

        if(noteInfo.getColor()==editcolor[0]){
            holder.iv_color.setImageResource(R.drawable.green);
        }else if(noteInfo.getColor()==editcolor[1]){
            holder.iv_color.setImageResource(R.drawable.blue);
        }else if(noteInfo.getColor()==editcolor[2]){
            holder.iv_color.setImageResource(R.drawable.purple);
        }else if(noteInfo.getColor()==editcolor[3]){
            holder.iv_color.setImageResource(R.drawable.yellow);
        }else{
            holder.iv_color.setImageResource(R.drawable.red);
        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Iterator iter = noteInfos.iterator();
                while (iter.hasNext()) {
                    NoteInfo note = (NoteInfo) iter.next();
                    if (note.getTimeId().equals(noteInfo.getTimeId())) {
                        iter.remove();
                        db.delete(note.getTimeId());
                    }
                }
                Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
                ContentAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteInfos.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tv_note_detail;
        private RelativeLayout note_detail_titlebar;
        private TextView note_detail_tv_date;
        private ImageView iv_color;
        private ImageView iv_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_note_detail= (TextView) itemView.findViewById(R.id.tv_note_detail);
            note_detail_titlebar= (RelativeLayout) itemView.findViewById(R.id.note_detail_titlebar);
            note_detail_tv_date= (TextView) itemView.findViewById(R.id.note_detail_tv_date);
            iv_color= (ImageView) itemView.findViewById(R.id.iv_color);
            iv_delete= (ImageView) itemView.findViewById(R.id.iv_delete);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int postion=this.getLayoutPosition();
            if (StringUtils.isEmpty(password)) {
                Intent intent=new Intent(context, PatternCreateActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("noteinfo",noteInfos.get(postion));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }else {
                Intent intent=new Intent(context, PatternCheckActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("noteinfo",noteInfos.get(postion));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
            ((Activity)context).overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        }
    }
}
