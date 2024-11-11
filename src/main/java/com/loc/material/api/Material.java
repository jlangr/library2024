package com.loc.material.api;

public class Material {
   private String sourceId;
   private String title;
   private String author;
   private String year;
   private int format;
   private String classification;

   public Material() {
   }

   public Material(String sourceId,
                   String author,
                   String title,
                   String classification,
                   int format,
                   String year) {
      this.sourceId = sourceId;
      this.author = author;
      this.title = title;
      this.classification = classification;
      this.format = format;
      this.year = year;
   }

   public Material(String sourceId, String author, String title, String classification,
                   String year) {
      this(sourceId, author, title, classification, MaterialType.BOOK, year);
   }

   public String getSourceId() {
      return sourceId;
   }

   public void setSourceId(String sourceId) {
      this.sourceId = sourceId;
   }

   public void setClassification(String classification) {
      this.classification = classification;
   }

   public String getClassification() {
      return classification;
   }

   public String getTitle() {
      return title;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public String getAuthor() {
      return author;
   }

   public String getYear() {
      return year;
   }

   public int getFormat() {
      return format;
   }

   public void setFormat(int format) {
      this.format = format;
   }

   public void setYear(String year) {
      this.year = year;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   @Override
   public String toString() {
      return getFormat() + ": " + getClassification() + " " + getSourceId() + " " + getTitle() + " (" + getAuthor()
         + ")";
   }

   public int getFine(int daysLate) {
      var fineBasis = MaterialType.dailyFine(getFormat());

      var fine = 0;
      switch (getFormat()) {
         case MaterialType.BOOK, MaterialType.NEW_RELEASE_DVD:
            fine = fineBasis * daysLate;
            break;
         case MaterialType.AUDIO_CASSETTE, MaterialType.VINYL_RECORDING, MaterialType.MICRO_FICHE, MaterialType.AUDIO_CD, MaterialType.SOFTWARE_CD, MaterialType.DVD, MaterialType.BLU_RAY, MaterialType.VIDEO_CASSETTE:
            fine = Math.min(1000, 100 + fineBasis * daysLate);
            break;
         default:
            break;
      }
      return fine;
   }
}
