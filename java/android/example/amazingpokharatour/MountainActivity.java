package android.example.amazingpokharatour;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import java.util.ArrayList;

public class MountainActivity extends AppCompatActivity {
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

        words.add(new Word("Landruk", "Highlights:When the fog is lifted in the region, " +
                "you will experience a nature rendezvous that you wouldn’t have imagined before. " +
                "The small clearing, nestled with the forests and incredible mountains is a must have." +
                " The Landruk village itself has farming terraces that will instantly capture your heart.", R.raw.mountain_sarangot,R.raw.air_sound));
        words.add(new Word("Himchuli", "Himchuli peak 6,441m is located in the western part of Nepal Annapurna region connected to Annapurna" +
                " South forming a massive south facing wall.The Himchuli was first submitted by an American Peace " +
                "Corps Expedition under the leadership of Craig Anderson in October 1971 via its southeast face.", R.raw.mountain_himchuli,R.raw.air_sound));
        words.add(new Word("Begnas Hiking","Unique Begnas Lake Valley Short Hiking from Pokhara Nepal is best hiking option for" +
                " beginner and families with young kids. Place we will cover: Begnas lake," +
                " Thulakot, Vijayapur, Pokhara. Trip will begins and ends from your hotel in Pokhara to Pokhara.",R.raw.mountain_begnas,R.raw.air_sound));
        words.add(new Word("Langtang Ganjapass Trek","Langtang / Gonja La Pass Trek offers an opportunity for the trekkers " +
                "and adventurers to enjoy and discover the Himalayan passes, " +
                "pristine valley and diverse ethno- culture of the Langtang valley. " +
                "This is the nearest trek from the capital city and is easily accessible from Kathmandu. " +
                "This is the third popular trekking destination of Nepal. It has remained relatively unspoiled .",R.raw.mountain_langtan,R.raw.air_sound));
        words.add(new Word("Upper Mustang Trek","The Mustang trek is not particularly difficult, the highest point reached" +
                " being only 3,800 meters, but the conditions at times can be arduous. " +
                "Mustang is cold in winter and is always windy and dusty through the year." +
                " Winter treks are best avoided due to harsh weather.",R.raw.mountain_mustang,R.raw.air_sound));
        words.add(new Word("Pokhara Trek To Australia Base Camp","The 1-2 day tour will take you to the beautiful hill top" +
                " that has striking natural views of Nepal. The trail here is steep, but it is all rewarding. The majestic views make the physical discomfort feel better! Highlights:T" +
                "he trek goes beyond mountain beauty and scenic wonders.",R.raw.mountain_australia,R.raw.air_sound));
        words.add(new Word("Dhampus Peak Treak"," This peak provides you more than thirty mountains views like Dhaulagiri " +
                "I (8163m), Sita Chuchura (6611m), Nilgiri (6940m), Tukche Peak (6920m), Tilicho peak (7134m), Thorong Peak (6484m), Yakawa Kan (6482m), Puth Hiunchuli (7246m), Mukut Himal (6639m), Tangu Peak (6197m), " +
                "Hongde peak (6556m), Tsarste (6347m) and more snow capped mountains and as well as Dhaulagiri glacier and deepest gorge " +
                "of the world.",R.raw.mountain_dhampus,R.raw.air_sound));
        words.add(new Word("Sarangot","Sarangkot is known for panoramic Himalayan views of Dhaulagiri, Annapurna and Manaslu. " +
                "It also provides an expansive view of the city of Pokhara, from the extreme north to the south including the Phewa Lake.",R.raw.mountain_sarangot,R.raw.air_sound));


        WordAdapter adapter = new WordAdapter(this, words);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
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
               mMediaPlayer = MediaPlayer.create(MountainActivity.this, word.getAudioResourceId());

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
