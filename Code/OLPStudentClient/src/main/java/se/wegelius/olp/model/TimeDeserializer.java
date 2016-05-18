/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.slf4j.LoggerFactory;

/**
 *
 * @author asawe
 */
public class TimeDeserializer implements JsonDeserializer<Time> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimeDeserializer.class);
    private static final String TIME_FORMAT = "HH:mm:ss";

    @Override
    public Time deserialize(JsonElement jsonElement, Type typeOF,
            JsonDeserializationContext context) throws JsonParseException {
        try {
            String s = jsonElement.getAsString();
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
            try {
                sdf.parse(s);
            } catch (ParseException ex) {
                logger.error(ex.getLocalizedMessage());
            }
            long ms = sdf.parse(s).getTime();
            Time t = new Time(ms);
            return t;
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }
}
