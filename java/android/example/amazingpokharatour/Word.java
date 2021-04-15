package android.example.amazingpokharatour;

public class Word {
    private String mPlace;
    private  String mDescription;
    private  int mImage;
    private  int mAudioResourceId;

    public Word (String place, String description, int image, int audioResourceId)
    {
        mPlace = place;
        mDescription = description;
        mImage = image;
        mAudioResourceId = audioResourceId;
    }

    public String getplace() {
        return mPlace;
    }
    public String getdescription()
    {
        return mDescription;
    }
    public  int getimage(){
        return  mImage;
    }
    public int getAudioResourceId(){
        return mAudioResourceId;
    }
}
