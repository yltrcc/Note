package note.neusoft.com.note;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import note.neusoft.com.note.activity.BaseActivity;
import note.neusoft.com.note.activity.EditActivity;
import note.neusoft.com.note.activity.PersonActivity;
import note.neusoft.com.note.activity.SettingActivity;
import note.neusoft.com.note.activity.SkinActivity;
import note.neusoft.com.note.adapter.ContentAdapter;
import note.neusoft.com.note.db.NoteDatabase;
import note.neusoft.com.note.domain.NoteInfo;
import note.neusoft.com.note.utils.PrefUtils;
import note.neusoft.com.note.utils.Utility;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FloatingActionButton fab;
    private Toolbar toolbar;
    private NavigationView nav_view;
    private LinearLayout ll_head;

    private Context context;
    private String password;

    private  RecyclerView review;
    private ContentAdapter adapter;
    private ArrayList<NoteInfo> noteInfos;
    private NoteDatabase db;

    private boolean isCoulm;


    private static RelativeLayout content_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissions();
        password = Utility.getPatternPassword(this);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;


        content_main= (RelativeLayout) findViewById(R.id.content_main);

        if(PrefUtils.GetBg(context)!=-1){
            ChangeBG(PrefUtils.GetBg(context));
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        nav_view= (NavigationView) findViewById(R.id.nav_view);

        View headerView = nav_view.getHeaderView(0);
        ll_head= (LinearLayout) headerView.findViewById(R.id.ll_head);
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PersonActivity.class));
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }
        });


        review = (RecyclerView) findViewById(R.id.review);
        isCoulm = true;

        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, EditActivity.class));
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        db = new NoteDatabase(context);
        noteInfos = db.finAll();

        adapter = new ContentAdapter(context, noteInfos, password);
        review.setLayoutManager(new GridLayoutManager(context, 2));
        review.setAdapter(adapter);
        review.setItemAnimator(null);
        if(PrefUtils.getAlphaBg(this)){
            review.setAlpha(0.55f);
        }else{
            review.setAlpha(1);
        }


        review.setOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();







                if(layoutManager instanceof GridLayoutManager){
                    GridLayoutManager gridManager= (GridLayoutManager) layoutManager;
                    int first=gridManager.findFirstVisibleItemPosition();
                    if(first!=0){
                        fab.hide();
                    }else{
                        fab.show();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }


        });





        ItemTouchHelper helper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                
                int dragFlags;
                if(isCoulm){
                     dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                }else{
                    dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(noteInfos,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }




        });

        helper.attachToRecyclerView(review);
    }

    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
                }

                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (noteInfos != null) {
            noteInfos.clear();
        }
        noteInfos = db.finAll();
        if (adapter != null) {
            adapter = null;
        }
        password = Utility.getPatternPassword(this);
        adapter = new ContentAdapter(context, noteInfos, password);
        review.setAdapter(adapter);

        if(PrefUtils.getAlphaBg(this)){
            review.setAlpha(0.55f);
        }else{
            review.setAlpha(1);
        }

        if(PrefUtils.GetBg(context)!=-1){
            ChangeBG(PrefUtils.GetBg(context));
        }

    }


    private boolean isExit=false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (isExit) {
                super.onBackPressed();
                ((NApplacation)this.getApplication()).destoryAllActivity();
                finish();
                
            } else {
                isExit = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit=false;
                    }
                },2000);
                Toast.makeText(getApplicationContext(), "再点击一次退出程序", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        
        
        int id = item.getItemId();

        





        if (id == R.id.action_colunm) {

            if (isCoulm) {
                review.setLayoutManager(new GridLayoutManager(context, 1));
                adapter.notifyDataSetChanged();
                isCoulm = false;
                item.setTitle("thumbnail");
            } else {
                review.setLayoutManager(new GridLayoutManager(context, 2));
                adapter.notifyDataSetChanged();
                isCoulm = true;
                item.setTitle("list");
            }
            return true;
        } else if (id == R.id.action_sort) {




            Collections.reverse(noteInfos);





            adapter.notifyDataSetChanged();

            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(context, "search", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_skin) {
            startActivity(new Intent(context, SkinActivity.class));
            overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(context, SettingActivity.class));
            overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        } else if (id == R.id.nav_share) {

        } /*else if (id == R.id.nav_about) {
            startActivity(new Intent(context, AboutActivity.class));
            overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
        }*/ else if (id == R.id.goout) {
            ((NApplacation) this.getApplication()).destoryAllActivity();
        }

        return true;
    }


    /**
     * changeBackground
     */
    public static void ChangeBG(int bg){
        content_main.setBackgroundResource(bg);
    }

}
