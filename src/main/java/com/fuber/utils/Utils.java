package com.fuber.utils;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;


/**
 * Utility class
 * Created by Jaskaranjit on 11/30/17.
 */
public class Utils
{
    public static final Gson GSON;

    static {
        JsonDeserializer<ObjectId> des = new JsonDeserializer<ObjectId>()
        {
            @Override public ObjectId deserialize( JsonElement je, Type type, JsonDeserializationContext jdc )
            {
                return new ObjectId( je.getAsJsonObject().get( "$oid" ).getAsString() );
            }
        };
        JsonSerializer<ObjectId> ser = new JsonSerializer<ObjectId>()
        {

            @SuppressWarnings ("deprecation") @Override public JsonElement serialize( ObjectId src, Type typeOfSrc,
                JsonSerializationContext context )
            {
                JsonObject jo = new JsonObject();
                jo.addProperty( "$oid", src.toStringMongod() );
                return jo;
            }
        };
        GSON = new GsonBuilder().registerTypeAdapter( ObjectId.class, des ).registerTypeAdapter( ObjectId.class, ser ).create();
    }

    /**
     * private constructor
     */
    private Utils()
    {

    }
}
