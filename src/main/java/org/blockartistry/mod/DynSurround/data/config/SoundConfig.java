package org.blockartistry.mod.DynSurround.data.config;

import com.google.gson.annotations.SerializedName;

public class SoundConfig {
   @SerializedName("sound")
   public String sound = null;
   @SerializedName("conditions")
   public String conditions = ".*";
   @SerializedName("soundType")
   public String soundType = null;
   @SerializedName("volume")
   public Float volume = null;
   @SerializedName("pitch")
   public Float pitch = null;
   @SerializedName("weight")
   public Integer weight = null;
   @SerializedName("variable")
   public Boolean variable = null;
   @SerializedName("repeatDelayRandom")
   public Integer repeatDelayRandom = null;
   @SerializedName("repeatDelay")
   public Integer repeatDelay = null;
   @SerializedName("skipFade")
   public Boolean skipFade = null;
   @SerializedName("spot")
   public Boolean spotSound = null;
   @SerializedName("step")
   public Boolean step = null;
}
