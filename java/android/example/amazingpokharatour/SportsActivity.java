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

public class SportsActivity extends AppCompatActivity {
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
        words.add(new Word("Paragliding","Sarangkot in Pokhara is the paragliding destination from where the flight takes off. The spot is 1,592m from sea level. In this flight of 30-40 minutes, you will share the sky with vultures, eagles, and kites." +
                " It is an ecstatic feeling to see villages, temples, and lakes from the sky.",R.raw.paragliding,R.raw.air_sound));
        words.add(new Word("Mountain Biking","This is one of the best mountain biking trips for novice to intermediate riders that Pokhara has to offer. You will start or ride from Hallan chock leaving behind lakeside and the ride will be along the lake, paddy field, beautiful authentic villages," +
                " grazing land and suspension bridges.",R.raw.biking,R.raw.air_sound));
        words.add(new Word("Ultaligth Flight","This hour-long ultralight flight will take you over the Annapurna Massif to an altitude of 3,500 metres (12,000 feet) above sea level. Gliding over the breathtakingly beautiful mountain landscapes, you'll enjoy unparalleled views of the " +
                "peaks of Machhapuchhre, Dhaulagiri and Manasalu.",R.raw.ultralight,R.raw.air_sound));
        words.add(new Word("Trekking","Ghorepani Poonhill Trek\n" +
                "Most popular trek in Pokhara for both teahouse and organized. It offers an exotic view of Annapurna region in short and easy trek. ... It is a 4-5 days hike in the foothills of " +
                "Annapurna mountain in Nepal. The elevation is 3,210 meters from the sea level.",R.raw.traking,R.raw.air_sound));
        words.add(new Word("Hand Gliding","Hang gliding is an air sport or recreational activity in which a pilot flies a light, non-motorised foot-launched heavier-than-air aircraft called a hang glider. Most modern hang gliders are made of an aluminium alloy or" +
                " composite frame covered with synthetic sailcloth to form a wing.",R.raw.handgliding,R.raw.air_sound));
        words.add(new Word("Seti Giding","Paramotor – is a power unit that pilot wears on his back. It designed to convert paraglider wings to powered paragliders (PPG's). Many disillusioned paraglider pilots are now turning to paramotoring to " +
                "increase their air time and take part in new adventures",R.raw.setigliding,R.raw.air_sound));
        words.add(new Word("Skydving","Skydiving is an extreme adventure sport for thrill seekers. Backed up by the well experience and professional skydive crew, skydive will be held at Pame Danda drop zone west of beautiful Phewa lake where skydivers will leap " +
                "from helicopter or small twin otter aircraft at the height of 12,000ft/3,658m",R.raw.skydiving,R.raw.air_sound));
        words.add(new Word("Paramotor","Paramotor – is a power unit that pilot wears on his back. It designed to convert paraglider wings to powered paragliders (PPG's). Many disillusioned paraglider pilots are now turning to paramotoring to " +
                "increase their air time and take part in new adventures",R.raw.paramotors,R.raw.air_sound));
        words.add(new Word("Standup Paddke Barding","Fewa Lake in Pokhara offers you the second-best option with Stand Up Paddle Boarding (SUP). The sport lets you explore the lake freely on a paddleboard. ... SUP is an experience that is completely different from other " +
                "sports and will leave you completely relaxed in the end.",R.raw.standup,R.raw.air_sound));
        words.add(new Word("Bunging jumping","Pokhara Bungee Jumping is the highest water touch Bungee in the world. This bungee jumping is made from a tower which is at the height of 80 meters" +
                " from where you dive to the pond 18/20 meter deep.",R.raw.bungee,R.raw.air_sound));


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
                mMediaPlayer = MediaPlayer.create(SportsActivity.this, word.getAudioResourceId());

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