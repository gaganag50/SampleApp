package com.example.android.sampleapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songList;
    private ListView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songView = (ListView)findViewById(R.id.song_list);

        songList = new ArrayList<Song>();
        getSongList();
    }
    public void getSongList() {

        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
    }
}
