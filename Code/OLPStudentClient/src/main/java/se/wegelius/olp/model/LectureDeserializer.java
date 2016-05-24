/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.sql.Time;

/**
 *
 * @author asawe
 */
public class LectureDeserializer implements JsonDeserializer<Lecture> {

    @Override
    public Lecture deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final JsonObject jsonObject = je.getAsJsonObject();
        final int lectureId = jsonObject.get("lectureId").getAsInt();
        final int courseId = jsonObject.get("courseId").getAsInt();
        final String lectureName = jsonObject.get("lectureName").getAsString();
        final String video = jsonObject.get("video").getAsString();
        final String durationString = jsonObject.get("duration").getAsString();
        final Time duration = getTime(durationString);
        final String description = jsonObject.get("description").getAsString();

        final Lecture lecture = new Lecture();
        lecture.setLectureId(lectureId);
        lecture.setCourseId(courseId);
        lecture.setLectureName(lectureName);
        lecture.setVideo(video);
        lecture.setDuration(duration);
        lecture.setDescription(description);
        return lecture;
    }

    private Time getTime(String timeString) {
        if (timeString.endsWith("AM") || timeString.endsWith("PM")) {
            timeString = timeString.substring(0, timeString.indexOf(" ")).trim();
        }
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int sec = Integer.parseInt(parts[2]);
        long ms = hours * 60 * 60 + minutes * 60 + sec;
        return new Time(ms);
    }
}
