package org.blockartistry.mod.DynSurround.data;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.util.INBTSerialization;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

public final class DimensionEffectData implements INBTSerialization {
   private static final XorShiftRandom random = new XorShiftRandom();
   private static final DecimalFormat FORMATTER = new DecimalFormat("0");
   public static final float MIN_INTENSITY = 0.0F;
   public static final float MAX_INTENSITY = 1.0F;
   private int dimensionId = 0;
   private float intensity = 0.0F;
   private float minIntensity;
   private float maxIntensity;
   private final Set<AuroraData> auroras;

   public DimensionEffectData() {
      this.minIntensity = ModOptions.defaultMinRainStrength;
      this.maxIntensity = ModOptions.defaultMaxRainStrength;
      this.auroras = new HashSet();
   }

   public DimensionEffectData(int dimensionId) {
      this.minIntensity = ModOptions.defaultMinRainStrength;
      this.maxIntensity = ModOptions.defaultMaxRainStrength;
      this.auroras = new HashSet();
      this.dimensionId = dimensionId;
   }

   public int getDimensionId() {
      return this.dimensionId;
   }

   public float getRainIntensity() {
      return this.intensity;
   }

   public void setRainIntensity(float intensity) {
      this.intensity = MathHelper.clamp_float(intensity, 0.0F, 1.0F);
   }

   public float getMinRainIntensity() {
      return this.minIntensity;
   }

   public void setMinRainIntensity(float intensity) {
      this.minIntensity = MathHelper.clamp_float(intensity, 0.0F, this.maxIntensity);
   }

   public float getMaxRainIntensity() {
      return this.maxIntensity;
   }

   public void setMaxRainIntensity(float intensity) {
      this.maxIntensity = MathHelper.clamp_float(intensity, this.minIntensity, 1.0F);
   }

   public Set<AuroraData> getAuroraList() {
      return this.auroras;
   }

   public void randomizeRain() {
      float result = 0.0F;
      float delta = this.maxIntensity - this.minIntensity;
      if (delta <= 0.0F) {
         result = this.minIntensity;
      } else {
         float mid = delta / 2.0F;
         result = random.nextFloat() * mid + random.nextFloat() * mid;
      }

      this.setRainIntensity(MathHelper.clamp_float(result, 0.01F, 1.0F));
   }

   public void readFromNBT(NBTTagCompound nbt) {
      this.dimensionId = nbt.getInteger("d");
      this.intensity = MathHelper.clamp_float(nbt.getFloat("s"), 0.0F, 1.0F);
      if (nbt.hasKey("min")) {
         this.minIntensity = MathHelper.clamp_float(nbt.getFloat("min"), 0.0F, 1.0F);
      }

      if (nbt.hasKey("max")) {
         this.maxIntensity = MathHelper.clamp_float(nbt.getFloat("max"), this.minIntensity, 1.0F);
      }

      NBTTagList list = nbt.getTagList("al", 10);

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound tag = list.getCompoundTagAt(i);
         AuroraData data = new AuroraData();
         data.readFromNBT(tag);
         this.auroras.add(data);
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      nbt.setInteger("d", this.dimensionId);
      nbt.setFloat("s", this.intensity);
      nbt.setFloat("min", this.minIntensity);
      nbt.setFloat("max", this.maxIntensity);
      NBTTagList list = new NBTTagList();

      for(AuroraData data : this.auroras) {
         NBTTagCompound tag = new NBTTagCompound();
         data.writeToNBT(tag);
         list.appendTag(tag);
      }

      nbt.setTag("al", list);
   }

   public static DimensionEffectData get(World world) {
      return DimensionEffectDataFile.get(world);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dim ").append(this.dimensionId).append(": ");
      builder.append("intensity: ").append(FORMATTER.format((double)(this.intensity * 100.0F)));
      builder.append(" [").append(FORMATTER.format((double)(this.minIntensity * 100.0F)));
      builder.append(",").append(FORMATTER.format((double)(this.maxIntensity * 100.0F)));
      builder.append("]");
      builder.append(", auroras: ").append(this.auroras.size());
      return builder.toString();
   }

   private final class NBT {
      public static final String DIMENSION = "d";
      public static final String INTENSITY = "s";
      public static final String MIN_INTENSITY = "min";
      public static final String MAX_INTENSITY = "max";
      public static final String AURORA_LIST = "al";
   }
}
