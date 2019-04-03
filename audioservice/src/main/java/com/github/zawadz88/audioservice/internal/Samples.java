package com.github.zawadz88.audioservice.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.github.zawadz88.audioservice.R;

import androidx.annotation.DrawableRes;

final class Samples {

    public static final class Sample {
        public final Uri uri;
        public final String mediaId;
        public final String title;
        public final String description;
        public final int bitmapResource;

        public Sample(
                String uri, String mediaId, String title, String description, int bitmapResource) {
            this.uri = Uri.parse(uri);
            this.mediaId = mediaId;
            this.title = title;
            this.description = description;
            this.bitmapResource = bitmapResource;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    static final Sample[] SAMPLES = new Sample[]{
            new Sample(
                    "https://storage.googleapis.com/automotive-media/Jazz_In_Paris.mp3",
                    "audio_1",
                    "Jazz in Paris",
                    "Jazz for the masses",
                    R.drawable.album_art_1),
            new Sample(
                    "https://storage.googleapis.com/automotive-media/The_Messenger.mp3",
                    "audio_2",
                    "The messenger",
                    "Hipster guide to London",
                    R.drawable.album_art_2),
            new Sample(
                    "https://storage.googleapis.com/automotive-media/Talkies.mp3",
                    "audio_3",
                    "Talkies",
                    "If it talks like a duck and walks like a duck.",
                    R.drawable.album_art_3),
    };


    static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        return ((BitmapDrawable) context.getResources().getDrawable(bitmapResource)).getBitmap();
    }

}
