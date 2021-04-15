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

public class LakeActivity extends AppCompatActivity {
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
        words.add(new Word("Begnas Lake","Begnas Lake is a freshwater lake in Pokhara Lekhnath Metropolis of Kaski district of Nepal located in the south-east of the Pokhara Valley. " +
                "The lake is the third largest lake of Nepal and " +
                "second largest, after Phewa Lake, among the eight lakes in Pokhara Valley.",R.raw.begnas,R.raw.waterfall_sound));
        words.add(new Word("Devis Fall","Devi's Falls is a waterfall located at Pokhara in Kaski District, Nepal. The water forms a tunnel after reaching the bottom. This tunnel is approximately 500 feet long and runs 100 feet below ground level." +
                " On 31 July 1961, a Swiss couple went swimming but the woman drowned in a pit because of the overflow.",R.raw.devis,R.raw.waterfall_sound));
        words.add(new Word("Phewa Lake","Phewa Lake, Phewa Tal or Fewa Lake is a freshwater lake in Nepal formerly called Baidam Tal located in the south of the Pokhara Valley that includes Pokhara city; " +
                "parts of Sarangkot and Kaskikot.",R.raw.phewa,R.raw.waterfall_sound));
        words.add(new Word("Rupa Tal","Rupa Lake or Rupa Tal is a freshwater lake in Nepal located in the Rupa Municipal of Kaski District. It is the third biggest lake in Pokhara valley " +
                "of Nepal and at an altitude of 600 m covering area about 1.35 km² " +
                "with an average water depth 3 m and maximum depth 6 m.",R.raw.rupa,R.raw.waterfall_sound));

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
                mMediaPlayer = MediaPlayer.create(LakeActivity.this, word.getAudioResourceId());

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