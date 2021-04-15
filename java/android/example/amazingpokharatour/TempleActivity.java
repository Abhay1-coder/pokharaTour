package android.example.amazingpokharatour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TempleActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private  AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }


        }
    };
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Bindyabasni Temple","The Bindhyabasini temple (Nepali: बिन्ध्यबासिनी मन्दिर) is the oldest temple " +
                "in the city of Pokhara, Nepal and is located in Ward No. ... The main temple is devoted to goddess Bindhyabasini, " +
                "a Bhagawati who is the incarnation of Kali." +
                " There are smaller temples of goddess Saraswati, Shiva, Hanuman, Ganesha in the premises.",R.raw.temple_bindyabasni,R.raw.temple_sound));
        words.add(new Word("Shanti Stupa ","Pokhara Shanti Stupa is a Buddhist pagoda-style monument on Anadu Hill of the former Pumdi " +
                "Bhumdi Village Development Committee, in the district of Kaski, Nepal. ",R.raw.temple_santi,R.raw.temple_sound));
        words.add(new Word("Tal Varahi Temple","Tal Barahi Temple, also known as Lake Temple or Barahi Temple is a two-story " +
                "pagoda temple located in the Kaski District of the Gandaki Zone in western Nepal. It is a hindu temple of the Goddess Durga, the protector of gods. " +
                "It is located in a small island on the south east section of Phewa Lake in Pokhara.",R.raw.temple_tal,R.raw.temple_sound));
        words.add(new Word("Barahi Temple","Tal Barahi Temple, also known as Lake Temple or Barahi Temple is a two-story " +
                "pagoda temple located in the Kaski District of the Gandaki Zone in western Nepal. It is a hindu temple of the Goddess Durga, the protector of gods. " +
                "It is located in a small island on the south east section of Phewa Lake in Pokhara.",R.raw.temple_barahi,R.raw.temple_sound));

        WordAdapter adapter = new WordAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                mMediaPlayer = MediaPlayer.create(TempleActivity.this, word.getAudioResourceId());

                mMediaPlayer.start();



            }
        });
    }
    @Override
    protected void onStop() {
        // when the activity is stop , release the mediaPlayer resourese , because we dont want be playing anymore
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}