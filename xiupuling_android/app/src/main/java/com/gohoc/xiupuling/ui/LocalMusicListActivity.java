package com.gohoc.xiupuling.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.LocalMusicListAdapter;
import com.gohoc.xiupuling.bean.LocalMusicBean;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 音乐列表
* */
public class LocalMusicListActivity extends BasicActivity {


    public static ContentResolver mContentResolver;
    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listView)
    ListView mListView;

    private LocalMusicListAdapter mLocalMusicListAdapter;
    private ArrayList<LocalMusicBean> mylist = new ArrayList<LocalMusicBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music_list_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        initDatas();
    }

    /*加载媒体库里的音频*/
    public void scanAllAudioFiles() {
        //生成动态数组，并且转载数据

        /*查询媒体数据库
        参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
        视频：MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        图片;MediaStore.Images.Media.EXTERNAL_CONTENT_URI
         */
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //遍历媒体数据库
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //歌曲编号
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲标题
                String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));


                if (size > 1024 * 800) {//大于800K
                    LocalMusicBean musicMedia = new LocalMusicBean();
                    musicMedia.setId(id);
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setName(tilte);
                    musicMedia.setDuration(duration);
                    musicMedia.setPath(url);
                    musicMedia.setAlbum(album);
                    mylist.add(musicMedia);
                    LogUtil.d(url + ":加载音乐:" + tilte);
                }
                cursor.moveToNext();
            }
        }
    }


    private void initView() {
        mToolbarTitle.setText("选择音频文件");
        mLocalMusicListAdapter=new LocalMusicListAdapter(mContext);
        mListView.setAdapter(mLocalMusicListAdapter);
        mLocalMusicListAdapter.setCallback(new Callback1() {
            @Override
            public void callBack(String string) {
                if(!TextUtils.isEmpty(string)){
                    Intent intent = new Intent();
                    intent.putExtra("audio", mylist.get(Integer.parseInt(string)));
                    setResult(BaseConstant.AUDIO_RESULT, intent);  //多选不允许裁剪裁剪，返回数据
                    finish();
                }
            }
        });
    }

    private void initDatas() {
        scanAllAudioFiles();
        mLocalMusicListAdapter.setmLists(mylist);
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
